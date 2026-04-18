package com.hhxy.controller.user;

import com.hhxy.dto.OrdersPaymentDTO;
import com.hhxy.dto.OrdersSubmitDTO;
import com.hhxy.result.Result;
import com.hhxy.service.OrderService;
import com.hhxy.vo.OrderSubmitVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Tag(name = "C端-订单接口")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/submit")
    @Operation(summary = "用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单，参数为：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    @Operation(summary = "订单支付（模拟）")
    public Result<String> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        log.info("订单支付：{}", ordersPaymentDTO);
        orderService.paySuccess(ordersPaymentDTO.orderNumber());
        return Result.success();
    }

    @GetMapping("/reminder/{id}")
    @Operation(summary = "用户催单")
    public Result<String> reminder(@PathVariable Long id) {
        log.info("用户催单，订单ID：{}", id);
        orderService.reminder(id);
        return Result.success();
    }

    @GetMapping("/status/{orderId}")
    @Operation(summary = "查询秒杀订单状态")
    public Result<String> checkSeckillStatus(@PathVariable Long orderId) {
        String statusKey = "order:status:" + orderId;
        Object status = redisTemplate.opsForValue().get(statusKey);

        if (status == null) {
            return Result.error("订单不存在或已过期");
        }

        return Result.success(status.toString());
    }

    @PostMapping("/repetition/{id}")
    @Operation(summary = "再来一单")
    public Result<String> repetition(@PathVariable Long id) {
        log.info("再来一单，订单ID：{}", id);
        orderService.repetition(id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "用户取消订单")
    public Result<String> cancel(@PathVariable("id") Long id) {
        log.info("用户尝试取消订单：{}", id);
        orderService.cancelOrder(id);
        return Result.success();
    }
}