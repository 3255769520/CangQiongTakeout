package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.constant.MessageConstant;
import com.hhxy.constant.StatusConstant;
import com.hhxy.dto.SetmealDTO;
import com.hhxy.dto.SetmealPageQueryDTO;
import com.hhxy.entity.Dish;
import com.hhxy.entity.Setmeal;
import com.hhxy.entity.SetmealDish;
import com.hhxy.exception.DeletionNotAllowedException;
import com.hhxy.exception.SetmealEnableFailedException;
import com.hhxy.mapper.DishMapper;
import com.hhxy.mapper.SetmealDishMapper;
import com.hhxy.mapper.SetmealMapper;
import com.hhxy.result.PageResult;
import com.hhxy.service.SetmealService;
import com.hhxy.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(setmealDTO.categoryId())
                .name(setmealDTO.name())
                .price(setmealDTO.price())
                .status(setmealDTO.status() != null ? setmealDTO.status() : 0)
                .description(setmealDTO.description())
                .image(setmealDTO.image())
                .build();

        setmealMapper.insert(setmeal);

        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.setmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(sd -> {
                sd.setSetmealId(setmealId);
                setmealDishMapper.insert(sd);
            });
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        if (status.equals(StatusConstant.ENABLE)) {
            List<Dish> dishes = dishMapper.getBySetmealId(id);
            if (dishes != null && !dishes.isEmpty()) {
                dishes.forEach(dish -> {
                    if (dish.getStatus().equals(StatusConstant.DISABLE)) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }

        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.updateById(setmeal);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        Page<SetmealVO> page = new Page<>(setmealPageQueryDTO.page(), setmealPageQueryDTO.pageSize());

        Page<SetmealVO> resultPage = setmealMapper.pageQuery(page, setmealPageQueryDTO);

        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    @Override
    @Transactional
    public void updateWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = Setmeal.builder()
                .id(setmealDTO.id())
                .categoryId(setmealDTO.categoryId())
                .name(setmealDTO.name())
                .price(setmealDTO.price())
                .status(setmealDTO.status())
                .description(setmealDTO.description())
                .image(setmealDTO.image())
                .build();
        setmealMapper.updateById(setmeal);

        setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>()
                .eq(SetmealDish::getSetmealId, setmealDTO.id()));

        List<SetmealDish> setmealDishes = setmealDTO.setmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(sd -> {
                sd.setSetmealId(setmealDTO.id());
                setmealDishMapper.insert(sd);
            });
        }
        log.info("套餐修改成功，ID: {}", setmealDTO.id());
    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);

        List<SetmealDish> setmealDishes = setmealDishMapper.selectList(
                new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id));

        return new SetmealVO(
                setmeal.getId(),
                setmeal.getCategoryId(),
                setmeal.getName(),
                setmeal.getPrice(),
                setmeal.getStatus(),
                setmeal.getDescription(),
                setmeal.getImage(),
                setmeal.getUpdateTime(),
                null,
                setmealDishes
        );
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.selectById(id);
            if (StatusConstant.ENABLE.equals(setmeal.getStatus())) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>()
                .in(SetmealDish::getSetmealId, ids));

        setmealMapper.delete(new LambdaQueryWrapper<Setmeal>().in(Setmeal::getId, ids));

        log.info("套餐批量删除完成，共删除 {} 条记录", ids.size());
    }
}
