package com.hhxy.dto;

import java.io.Serializable;

public record SetmealPageQueryDTO(
    int page,
    int pageSize,
    String name,
    Integer categoryId,
    Integer status
) implements Serializable {}
