package com.hhxy.dto;

import com.hhxy.entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "新增菜品时传递的数据模型")
public record DishDTO(
        @Schema(description = "主键值")
        Long id,
        @Schema(description = "菜品名称")
        String name,
        @Schema(description = "菜品分类ID")
        Long categoryId,
        @Schema(description = "菜品价格")
        BigDecimal price,
        @Schema(description = "图片路径")
        String image,
        @Schema(description = "描述信息")
        String description,
        @Schema(description = "状态: 0停售 1起售")
        Integer status,
        @Schema(description = "口味列表")
        List<DishFlavor> flavors
) implements Serializable {}