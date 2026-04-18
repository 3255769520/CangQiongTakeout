package com.hhxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "营业数据")
public record BusinessDataVO(
    @Schema(description = "营业额")
    BigDecimal turnover,
    @Schema(description = "订单完成率")
    Double orderCompletionRate,
    @Schema(description = "新增用户数")
    Integer newUsers,
    @Schema(description = "有效订单数")
    Integer validOrderCount,
    @Schema(description = "总订单数")
    Integer totalOrderCount
) implements Serializable {}