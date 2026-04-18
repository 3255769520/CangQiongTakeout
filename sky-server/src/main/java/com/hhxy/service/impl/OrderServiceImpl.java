package com.hhxy.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.context.BaseContext;
import com.hhxy.constant.MessageConstant;
import com.hhxy.dto.*;
import com.hhxy.entity.AddressBook;
import com.hhxy.entity.Dish;
import com.hhxy.entity.OrderDetail;
import com.hhxy.entity.Orders;
import com.hhxy.entity.ShoppingCart;
import com.hhxy.exception.AddressBookBusinessException;
import com.hhxy.exception.OrderBusinessException;
import com.hhxy.exception.ShoppingCartBusinessException;
import com.hhxy.mapper.AddressBookMapper;
import com.hhxy.mapper.DishMapper;
import com.hhxy.mapper.OrderDetailMapper;
import com.hhxy.mapper.OrdersMapper;
import com.hhxy.mapper.ShoppingCartMapper;
import com.hhxy.result.PageResult;
import com.hhxy.service.OrderService;
import com.hhxy.vo.OrderPaymentVO;
import com.hhxy.vo.OrderSubmitVO;
import com.hhxy.vo.OrderVO;
import com.hhxy.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DishMapper dishMapper;

    private static final String STOCK_ROLLBACK_LUA =
        "if (redis.call('exists', KEYS[1]) == 1) then " +
        "    return redis.call('incrby', KEYS[1], ARGV[1]) " +
        "else " +
        "    return -1 " +
        "end";

    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        AddressBook addressBook = addressBookMapper.selectById(ordersSubmitDTO.addressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException("地址簿为空");
        }

        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectList(
                new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, userId));

        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            throw new ShoppingCartBusinessException("购物车为空");
        }

        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(0);
        orders.setStatus(1);
        orders.setNumber(String.valueOf(System.currentTimeMillis()) + userId);
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);
        orders.setAddress(addressBook.getDetail());
        
        ordersMapper.insert(orders);

        List<OrderDetail> orderDetailList = shoppingCartList.stream().map(cart -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            return orderDetail;
        }).collect(Collectors.toList());

        orderDetailMapper.insertBatch(orderDetailList);

        shoppingCartMapper.delete(new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, userId));

        return new OrderSubmitVO(orders.getId(), orders.getNumber(), orders.getAmount(), orders.getOrderTime());
    }

    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        String orderNumber = ordersPaymentDTO.orderNumber();
        paySuccess(orderNumber);
        return new OrderPaymentVO(1);
    }

    @Override
    @Transactional
    public void paySuccess(String orderNumber) {
        Orders ordersDB = ordersMapper.selectOne(new LambdaQueryWrapper<Orders>()
                .eq(Orders::getNumber, orderNumber));

        if (ordersDB != null) {
            Orders orders = Orders.builder()
                    .id(ordersDB.getId())
                    .status(Orders.TO_BE_CONFIRMED)
                    .payStatus(Orders.PAID)
                    .checkoutTime(LocalDateTime.now())
                    .build();

            ordersMapper.updateById(orders);

            Map<String, Object> map = new HashMap<>();
            map.put("type", 1);
            map.put("orderId", ordersDB.getId());
            map.put("content", "订单号：" + orderNumber);

            String json = JSON.toJSONString(map);
            webSocketServer.sendToAllClient(json);
            
            log.info("订单支付成功：{}", orderNumber);
        }
    }

    @Override
    public PageResult pageQueryUser(int page, int pageSize, Integer status) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Long userId = BaseContext.getCurrentId();

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId);
        if (status != null) queryWrapper.eq(Orders::getStatus, status);
        queryWrapper.orderByDesc(Orders::getOrderTime);

        ordersMapper.selectPage(pageInfo, queryWrapper);

        List<OrderVO> list = pageInfo.getRecords().stream().map(orders -> {
            List<OrderDetail> details = orderDetailMapper.selectList(
                new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, orders.getId()));
            
            return OrderVO.builder()
                    .id(orders.getId())
                    .number(orders.getNumber())
                    .amount(orders.getAmount())
                    .status(orders.getStatus())
                    .orderTime(orders.getOrderTime())
                    .address(orders.getAddress())
                    .consignee(orders.getConsignee())
                    .phone(orders.getPhone())
                    .orderDetailList(details)
                    .build();
        }).collect(Collectors.toList());

        return new PageResult(pageInfo.getTotal(), list);
    }

    @Override
    public OrderVO getDetails(Long id) {
        Orders orders = ordersMapper.selectById(id);
        List<OrderDetail> details = orderDetailMapper.selectList(
                new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, id));

        return OrderVO.builder()
                .id(orders.getId())
                .number(orders.getNumber())
                .amount(orders.getAmount())
                .status(orders.getStatus())
                .orderTime(orders.getOrderTime())
                .address(orders.getAddress())
                .consignee(orders.getConsignee())
                .phone(orders.getPhone())
                .orderDetailList(details)
                .build();
    }

    @Override
    public OrderVO getById(Long id) {
        Orders orders = ordersMapper.selectById(id);
        if (orders == null) {
            return null;
        }

        List<OrderDetail> orderDetailList = orderDetailMapper.selectList(
            new LambdaQueryWrapper<OrderDetail>()
                .eq(OrderDetail::getOrderId, id)
        );

        return OrderVO.builder()
                .id(orders.getId())
                .number(orders.getNumber())
                .status(orders.getStatus())
                .userId(orders.getUserId())
                .addressBookId(orders.getAddressBookId())
                .orderTime(orders.getOrderTime())
                .checkoutTime(orders.getCheckoutTime())
                .payMethod(orders.getPayMethod())
                .payStatus(orders.getPayStatus())
                .amount(orders.getAmount())
                .remark(orders.getRemark())
                .phone(orders.getPhone())
                .address(orders.getAddress())
                .consignee(orders.getConsignee())
                .cancelReason(orders.getCancelReason())
                .rejectionReason(orders.getRejectionReason())
                .cancelTime(orders.getCancelTime())
                .estimatedArrivalTime(orders.getEstimatedArrivalTime())
                .deliveryStatus(orders.getDeliveryStatus())
                .deliveryTime(orders.getDeliveryTime())
                .packAmount(orders.getPackAmount())
                .tablewareNumber(orders.getTablewareNumber())
                .tablewareStatus(orders.getTablewareStatus())
                .orderDetailList(orderDetailList)
                .build();
    }

    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO dto) {
        Page<Orders> page = new Page<>(dto.page(), dto.pageSize());

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
            .like(StringUtils.isNotEmpty(dto.number()), Orders::getNumber, dto.number())
            .like(StringUtils.isNotEmpty(dto.phone()), Orders::getPhone, dto.phone())
            .eq(dto.status() != null, Orders::getStatus, dto.status())
            .eq(dto.userId() != null, Orders::getUserId, dto.userId())
            .ge(dto.beginTime() != null, Orders::getOrderTime, dto.beginTime())
            .le(dto.endTime() != null, Orders::getOrderTime, dto.endTime())
            .orderByDesc(Orders::getOrderTime);

        ordersMapper.selectPage(page, queryWrapper);

        List<OrderVO> orderVOList = new ArrayList<>();
        List<Orders> records = page.getRecords();

        if (records != null && !records.isEmpty()) {
            for (Orders orders : records) {
                List<OrderDetail> details = orderDetailMapper.selectList(
                    new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, orders.getId())
                );

                String orderDishes = details.stream()
                    .map(d -> d.getName() + "*" + d.getNumber())
                    .collect(Collectors.joining("; "));

                OrderVO vo = OrderVO.builder()
                    .id(orders.getId())
                    .number(orders.getNumber())
                    .status(orders.getStatus())
                    .userId(orders.getUserId())
                    .address(orders.getAddress())
                    .amount(orders.getAmount())
                    .phone(orders.getPhone())
                    .consignee(orders.getConsignee())
                    .orderTime(orders.getOrderTime())
                    .orderDetailList(details)
                    .build();

                orderVOList.add(vo);
            }
        }

        return new PageResult(page.getTotal(), orderVOList);
    }

    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(Orders.CONFIRMED)
                .build();
        ordersMapper.updateById(orders);
    }

    @Override
    public void rejection(OrdersRejectionDTO dto) {
        Orders ordersDB = ordersMapper.selectById(dto.id());
        if (ordersDB == null || !ordersDB.getStatus().equals(2)) {
            throw new OrderBusinessException("订单状态错误，无法拒单");
        }

        log.info("模拟微信退款成功...");

        Orders orders = Orders.builder()
                .id(dto.id())
                .status(6)
                .rejectionReason(dto.rejectionReason())
                .cancelTime(LocalDateTime.now())
                .build();
        ordersMapper.updateById(orders);
    }

    @Override
    public void delivery(Long id) {
        Orders ordersDB = ordersMapper.selectById(id);
        if (ordersDB == null || !ordersDB.getStatus().equals(3)) {
            throw new OrderBusinessException("订单未接单，无法派送");
        }
        
        Orders orders = Orders.builder()
                .id(id)
                .status(4)
                .build();
        ordersMapper.updateById(orders);
    }

    @Override
    public void complete(Long id) {
        Orders ordersDB = ordersMapper.selectById(id);
        if (ordersDB == null || !ordersDB.getStatus().equals(4)) {
            throw new OrderBusinessException("订单尚未派送，无法完成");
        }

        Orders orders = Orders.builder()
                .id(id)
                .status(5)
                .deliveryTime(LocalDateTime.now())
                .build();
        ordersMapper.updateById(orders);
    }

    @Override
    public void reminder(Long id) {
        Orders ordersDB = ordersMapper.selectById(id);

        if (ordersDB == null) {
            throw new OrderBusinessException("订单不存在");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("orderId", id);
        map.put("content", "订单号：" + ordersDB.getNumber());

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
        
        log.info("已向管理端推送催单提醒，订单号：{}", ordersDB.getNumber());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void repetition(Long id) {
        Long userId = BaseContext.getCurrentId();

        List<OrderDetail> orderDetailList = orderDetailMapper.selectList(
            new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, id)
        );

        if (orderDetailList == null || orderDetailList.isEmpty()) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        for (ShoppingCart cart : shoppingCartList) {
            shoppingCartMapper.insert(cart);
        }

        log.info("用户 {} 触发再来一单，订单ID：{}，成功加入购物车商品数：{}", userId, id, shoppingCartList.size());
    }

    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        return page.getRecords().stream().map(orders -> {
            List<OrderDetail> details = orderDetailMapper.selectList(
                new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, orders.getId()));

            return OrderVO.builder()
                    .id(orders.getId())
                    .number(orders.getNumber())
                    .amount(orders.getAmount())
                    .status(orders.getStatus())
                    .orderTime(orders.getOrderTime())
                    .address(orders.getAddress())
                    .consignee(orders.getConsignee())
                    .phone(orders.getPhone())
                    .orderDetailList(details)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long id) {
        Orders ordersDB = ordersMapper.selectById(id);
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        Long userId = BaseContext.getCurrentId();
        if (!ordersDB.getUserId().equals(userId)) {
            throw new OrderBusinessException("无权操作他人订单");
        }

        if (ordersDB.getStatus() > 2) {
            throw new OrderBusinessException("订单已在处理中，无法直接取消，请联系商家");
        }

        if (ordersDB.getPayStatus().equals(Orders.PAID)) {
            log.info("订单已支付，执行退款逻辑，金额：{}", ordersDB.getAmount());
        }

        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.CANCELLED)
                .cancelReason("用户主动取消")
                .cancelTime(LocalDateTime.now())
                .payStatus(2)
                .build();
        ordersMapper.updateById(orders);

        List<OrderDetail> details = orderDetailMapper.selectList(
            new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, id)
        );

        for (OrderDetail detail : details) {
            dishMapper.update(null, new LambdaUpdateWrapper<Dish>()
                    .eq(Dish::getId, detail.getDishId())
                    .setSql("stock = stock + " + detail.getNumber()));

            String stockKey = "seckill:stock:" + detail.getDishId();
            redisTemplate.execute(
                new DefaultRedisScript<>(STOCK_ROLLBACK_LUA, Long.class),
                Collections.singletonList(stockKey),
                detail.getNumber().toString()
            );
        }

        log.info("订单 {} 取消成功，已回退 {} 种商品的库存", id, details.size());
    }
}