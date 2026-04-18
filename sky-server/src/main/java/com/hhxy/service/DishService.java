package com.hhxy.service;

import com.hhxy.dto.DishDTO;
import com.hhxy.dto.DishPageQueryDTO;
import com.hhxy.entity.Dish;
import com.hhxy.result.PageResult;
import com.hhxy.vo.DishVO;
import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    void startOrStop(Integer status, Long id);

    List<DishVO> listWithFlavor(Long categoryId);
}
