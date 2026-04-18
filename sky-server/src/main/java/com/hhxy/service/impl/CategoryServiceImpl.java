package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.constant.MessageConstant;
import com.hhxy.constant.StatusConstant;
import com.hhxy.dto.CategoryDTO;
import com.hhxy.dto.CategoryPageQueryDTO;
import com.hhxy.entity.Category;
import com.hhxy.entity.Dish;
import com.hhxy.entity.Setmeal;
import com.hhxy.exception.DeletionNotAllowedException;
import com.hhxy.mapper.CategoryMapper;
import com.hhxy.mapper.DishMapper;
import com.hhxy.mapper.SetmealMapper;
import com.hhxy.result.PageResult;
import com.hhxy.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper; // 需要新建 DishMapper 接口
    @Autowired
    private SetmealMapper setmealMapper; // 需要新建 SetmealMapper 接口

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        // 属性拷贝
        BeanUtils.copyProperties(categoryDTO, category);

        // 设置默认状态为禁用(0)或启用(1)，根据业务定
        category.setStatus(StatusConstant.DISABLE);

        // 插入数据库（自动填充会处理时间、人）
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO dto) {
        Page<Category> page = new Page<>(dto.page(), dto.pageSize());
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        // 条件查询：按名称模糊查，按类型查
        wrapper.like(dto.name() != null, Category::getName, dto.name())
                .eq(dto.type() != null, Category::getType, dto.type())
                .orderByAsc(Category::getSort) // 按排序字段升序
                .orderByDesc(Category::getCreateTime);

        categoryMapper.selectPage(page, wrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }


    @Override
    public void deleteById(Long id) {
        // 统计该分类下的菜品数量
        Long dishCount = dishMapper.selectCount(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, id));
        if (dishCount > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        // 统计该分类下的套餐数量
        Long setmealCount = setmealMapper.selectCount(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId, id));
        if (setmealCount > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.deleteById(id);
    }
    // 1. 修改分类
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // updateById 会自动触发 MyMetaObjectHandler 的 updateFill 填充修改时间/人
        categoryMapper.updateById(category);
    }

    // 2. 启用、禁用分类
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.updateById(category);
    }

    // 3. 根据类型查询分类（用于后续新增菜品时下拉框展示）
    @Override
    public List<Category> list(Integer type) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(type != null, Category::getType, type)
                .eq(Category::getStatus, StatusConstant.ENABLE) // 只查启用的
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getCreateTime);
        return categoryMapper.selectList(queryWrapper);
    }
}