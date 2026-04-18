package com.hhxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "订单支付返回结果")
public record OrderPaymentVO(
    @Schema(description = "支付状态")
    Integer payStatus
) implements Serializable {}