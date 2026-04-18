package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Schema(description = "接单DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersConfirmDTO implements Serializable {
    @Schema(description = "订单id")
    private Long id;
}
