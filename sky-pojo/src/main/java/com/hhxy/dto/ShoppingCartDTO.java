package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "添加购物车时传递的数据模型")
public record ShoppingCartDTO(
    @Schema(description = "菜品id")
    Long dishId,
    @Schema(description = "套餐id")
    Long setmealId,
    @Schema(description = "口味")
    String dishFlavor
) implements Serializable {}