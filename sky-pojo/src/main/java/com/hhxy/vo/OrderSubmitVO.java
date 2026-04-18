package com.hhxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "下单返回结果")
public record OrderSubmitVO(
    @Schema(description = "订单id")
    Long id,
    @Schema(description = "订单号")
    String orderNumber,
    @Schema(description = "订单金额")
    BigDecimal orderAmount,
    @Schema(description = "下单时间")
    LocalDateTime orderTime
) implements Serializable {}