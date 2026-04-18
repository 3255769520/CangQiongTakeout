package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhxy.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
