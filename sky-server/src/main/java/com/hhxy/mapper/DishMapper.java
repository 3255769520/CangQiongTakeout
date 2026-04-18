package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.dto.DishPageQueryDTO;
import com.hhxy.entity.Dish;
import com.hhxy.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    Long selectCount(LambdaQueryWrapper<Dish> eq);

    Page<DishVO> pageQuery(Page<DishVO> page, DishPageQueryDTO dishPageQueryDTO);

    @Select("select d.* from dish d left join setmeal_dish sd on d.id = sd.dish_id where sd.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    List<DishVO> listWithFlavor(Long categoryId);
}
