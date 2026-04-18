package com.hhxy.dto;

import com.hhxy.entity.SetmealDish;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "新增或修改套餐时传递的数据模型")
public record SetmealDTO(
    @Schema(description = "主键ID（修改时必填）")
    Long id,

    @Schema(description = "分类id")
    Long categoryId,

    @Schema(description = "套餐名称")
    String name,

    @Schema(description = "套餐价格")
    BigDecimal price,

    @Schema(description = "状态 0:停售 1:起售")
    Integer status,

    @Schema(description = "描述信息")
    String description,

    @Schema(description = "图片路径")
    String image,

    @Schema(description = "套餐关联的菜品列表")
    List<SetmealDish> setmealDishes
) implements Serializable {
    private static final long serialVersionUID = 1L;
}
