package com.hhxy.listener;

import com.alibaba.fastjson2.JSON;
import com.hhxy.constant.OrderAsyncStatus;
import com.hhxy.entity.OrderDetail;
import com.hhxy.entity.Orders;
import com.hhxy.mapper.OrderDetailMapper;
import com.hhxy.mapper.OrdersMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OrderMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private static final String QUEUE_NAME = "order:queue";

    @PostConstruct
    public void init() {
        taskExecutor.execute(this::consumeOrders);
    }

    private void consumeOrders() {
        while (true) {
            try {
                Object message = redisTemplate.opsForList().rightPop(QUEUE_NAME, 10, TimeUnit.SECONDS);
                if (message != null) {
                    Map<String, Object> orderTask = JSON.parseObject((String) message, Map.class);
                    Long orderId = Long.valueOf(orderTask.get("orderId").toString());
                    String statusKey = "order:status:" + orderId;

                    try {
                        transactionTemplate.execute(status -> {
                            processOrder((String) message);
                            return true;
                        });
                        redisTemplate.opsForValue().set(statusKey, OrderAsyncStatus.SUCCESS, 15, TimeUnit.MINUTES);
                        log.info("订单 {} 异步落库成功", orderId);

                    } catch (Exception e) {
                        redisTemplate.opsForValue().set(statusKey, OrderAsyncStatus.FAILED, 15, TimeUnit.MINUTES);
                        log.error("订单 {} 异步落库失败，已更新状态位", orderId);
                        rollbackStock(Long.valueOf(orderTask.get("dishId").toString()));
                    }
                }
            } catch (Exception e) {
                log.error("消息队列消费异常：", e);
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException ignored) {}
            }
        }
    }

    private void processOrder(String message) {
        Map<String, Object> orderTask = JSON.parseObject(message, Map.class);
        Long orderId = Long.valueOf(orderTask.get("orderId").toString());
        Long dishId = Long.valueOf(orderTask.get("dishId").toString());

        Orders orders = Orders.builder()
                .id(orderId)
                .number(String.valueOf(orderTask.get("orderNumber")))
                .userId(Long.valueOf(orderTask.get("userId").toString()))
                .status(2)
                .payStatus(1)
                .orderTime(LocalDateTime.now())
                .build();
        ordersMapper.insert(orders);

        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(orderId)
                .dishId(dishId)
                .number(1)
                .build();
        orderDetailMapper.insert(orderDetail);
    }

    private void rollbackStock(Long dishId) {
        String stockKey = "seckill:stock:" + dishId;
        redisTemplate.opsForValue().increment(stockKey);
    }
}
