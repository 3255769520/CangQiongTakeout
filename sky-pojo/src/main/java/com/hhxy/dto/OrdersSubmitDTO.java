package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "下单请求模型")
public record OrdersSubmitDTO(
    @Schema(description = "地址簿id")
    Long addressBookId,
    @Schema(description = "支付方式 1微信 2支付宝")
    int payMethod,
    @Schema(description = "备注")
    String remark,
    @Schema(description = "总金额")
    BigDecimal amount,
    @Schema(description = "打包费")
    Integer packAmount,
    @Schema(description = "餐具数量")
    Integer tablewareNumber,
    @Schema(description = "餐具状态 0:按餐具数量 1:提供餐具")
    Integer tablewareStatus
) implements Serializable {}