package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhxy.entity.Orders;
import com.hhxy.mapper.OrdersMapper;
import com.hhxy.mapper.UserMapper;
import com.hhxy.service.WorkspaceService;
import com.hhxy.vo.BusinessDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        BigDecimal turnover = ordersMapper.sumTurnoverByDate(begin, end);
        if (turnover == null) {
            turnover = BigDecimal.ZERO;
        }

        Integer validOrderCount = Math.toIntExact(ordersMapper.selectCount(new LambdaQueryWrapper<Orders>()
                .eq(Orders::getStatus, Orders.COMPLETED)
                .ge(Orders::getOrderTime, begin)
                .le(Orders::getOrderTime, end)));

        Integer totalOrderCount = Math.toIntExact(ordersMapper.selectCount(new LambdaQueryWrapper<Orders>()
                .ge(Orders::getOrderTime, begin)
                .le(Orders::getOrderTime, end)));

        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = (double) validOrderCount / totalOrderCount;
        }

        Integer newUsers = userMapper.countByCreateTime(begin, end);

        return new BusinessDataVO(turnover, orderCompletionRate, newUsers, validOrderCount, totalOrderCount);
    }
}