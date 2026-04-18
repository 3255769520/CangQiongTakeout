package com.hhxy.service;

import com.hhxy.dto.SetmealDTO;
import com.hhxy.dto.SetmealPageQueryDTO;
import com.hhxy.result.PageResult;
import com.hhxy.vo.SetmealVO;
import java.util.List;

public interface SetmealService {
    void saveWithDish(SetmealDTO setmealDTO);

    void startOrStop(Integer status, Long id);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateWithDish(SetmealDTO setmealDTO);

    SetmealVO getByIdWithDish(Long id);

    void deleteBatch(List<Long> ids);
}
