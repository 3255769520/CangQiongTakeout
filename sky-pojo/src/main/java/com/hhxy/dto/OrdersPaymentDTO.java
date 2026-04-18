package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "订单支付请求模型")
public record OrdersPaymentDTO(
    @Schema(description = "订单号")
    String orderNumber
) implements Serializable {}