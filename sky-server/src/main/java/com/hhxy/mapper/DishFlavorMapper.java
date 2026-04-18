package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhxy.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    // 继承 BaseMapper 后，自动获得 insert/update/deleteById 等能力
}