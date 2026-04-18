package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "菜品分页查询时传递的数据模型")
public record DishPageQueryDTO(
    @Schema(description = "页码")
    int page,
    
    @Schema(description = "每页记录数")
    int pageSize,
    
    @Schema(description = "菜品名称")
    String name,
    
    @Schema(description = "分类id")
    Integer categoryId,
    
    @Schema(description = "状态 0停售 1起售")
    Integer status
) implements Serializable {}
