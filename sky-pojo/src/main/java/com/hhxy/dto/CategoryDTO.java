package com.hhxy.dto;



public record CategoryDTO(
        Long id,
        Integer type, // 类型
        String name,  // 名称
        Integer sort  // 排序
) {}