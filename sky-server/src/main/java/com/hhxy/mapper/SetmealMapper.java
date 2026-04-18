package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.entity.Setmeal;
import com.hhxy.vo.SetmealVO;
import com.hhxy.dto.SetmealPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    Page<SetmealVO> pageQuery(Page<SetmealVO> page, SetmealPageQueryDTO setmealPageQueryDTO);
}
