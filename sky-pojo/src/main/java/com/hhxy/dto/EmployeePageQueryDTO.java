package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 使用 Java 21 Record 重构：简洁、不可变、原生支持
 */
@Schema(description = "员工分页查询模型")
public record EmployeePageQueryDTO(
        @Schema(description = "员工姓名")
        String name,

        @Schema(description = "页码", example = "1")
        int page,

        @Schema(description = "每页条数", example = "10")
        int pageSize
) {}