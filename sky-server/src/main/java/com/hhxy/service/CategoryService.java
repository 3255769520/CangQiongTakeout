package com.hhxy.service;

import com.hhxy.dto.CategoryDTO;
import com.hhxy.dto.CategoryPageQueryDTO;
import com.hhxy.entity.Category;
import com.hhxy.result.PageResult;

import java.util.List;

public interface CategoryService {
    void save(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO dto);
    void deleteById(Long id);

    // 1. 修改分类
    void update(CategoryDTO categoryDTO);

    // 2. 启用、禁用分类
    void startOrStop(Integer status, Long id);

    // 3. 根据类型查询分类（用于后续新增菜品时下拉框展示）
    List<Category> list(Integer type);
}
