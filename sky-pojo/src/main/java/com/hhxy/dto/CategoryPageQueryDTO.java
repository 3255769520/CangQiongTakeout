
package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * 分类分页查询 DTO
 */
@Schema(description = "分类分页查询传递的数据模型")
public record CategoryPageQueryDTO(
        @Schema(description = "页码")
        int page,

        @Schema(description = "每页记录数")
        int pageSize,

        @Schema(description = "分类名称")
        String name,

        @Schema(description = "分类类型：1 菜品分类 2 套餐分类")
        Integer type
) implements Serializable {}