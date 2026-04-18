package com.hhxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "订单搜索DTO")
public record OrdersPageQueryDTO(
    @Schema(description = "页码")
    int page,
    @Schema(description = "每页记录数")
    int pageSize,
    @Schema(description = "订单号")
    String number,
    @Schema(description = "手机号")
    String phone,
    @Schema(description = "订单状态")
    Integer status,
    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime beginTime,
    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime,
    @Schema(description = "用户id")
    Long userId
) implements Serializable {}