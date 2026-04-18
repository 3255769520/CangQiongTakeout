package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "拒单DTO")
public record OrdersRejectionDTO(
    @Schema(description = "订单id")
    Long id,
    @Schema(description = "拒单原因")
    String rejectionReason
) implements Serializable {}