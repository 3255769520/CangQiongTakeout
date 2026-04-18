package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "取消订单DTO")
public record OrdersCancelDTO(
    @Schema(description = "订单id")
    Long id,
    @Schema(description = "取消原因")
    String cancelReason
) implements Serializable {}