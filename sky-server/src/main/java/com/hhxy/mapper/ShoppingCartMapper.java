package com.hhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhxy.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    void insertBatch(List<ShoppingCart> shoppingCartList);
}