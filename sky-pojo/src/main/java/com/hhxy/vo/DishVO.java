package com.hhxy.vo;

import com.hhxy.entity.DishFlavor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DishVO(
    Long id,
    String name,
    Long categoryId,
    BigDecimal price,
    String image,
    String description,
    Integer status,
    LocalDateTime updateTime,
    String categoryName,
    List<DishFlavor> flavors
) implements Serializable {}
