package com.hhxy.vo;

import com.hhxy.entity.SetmealDish;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SetmealVO(
    Long id,
    Long categoryId,
    String name,
    BigDecimal price,
    Integer status,
    String description,
    String image,
    LocalDateTime updateTime,
    String categoryName,
    List<SetmealDish> setmealDishes
) implements Serializable {}
