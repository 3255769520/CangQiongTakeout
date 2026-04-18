package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhxy.constant.MessageConstant;
import com.hhxy.constant.StatusConstant;
import com.hhxy.dto.DishDTO;
import com.hhxy.dto.DishPageQueryDTO;
import com.hhxy.entity.Dish;
import com.hhxy.entity.DishFlavor;
import com.hhxy.exception.DeletionNotAllowedException;
import com.hhxy.mapper.DishFlavorMapper;
import com.hhxy.mapper.DishMapper;
import com.hhxy.mapper.SetmealDishMapper;
import com.hhxy.result.PageResult;
import com.hhxy.service.DishService;
import com.hhxy.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = Dish.builder()
                .name(dishDTO.name())
                .categoryId(dishDTO.categoryId())
                .price(dishDTO.price())
                .image(dishDTO.image())
                .description(dishDTO.description())
                .status(dishDTO.status() != null ? dishDTO.status() : 1)
                .build();

        dishMapper.insert(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.flavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
                dishFlavorMapper.insert(flavor);
            });
        }

        String key = "dish_" + dishDTO.categoryId();
        redisTemplate.delete(key);
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        Page<DishVO> page = new Page<>(dishPageQueryDTO.page(), dishPageQueryDTO.pageSize());

        Page<DishVO> resultPage = dishMapper.pageQuery(page, dishPageQueryDTO);

        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.selectById(id);

        List<DishFlavor> flavors = dishFlavorMapper.selectList(
            new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id)
        );

        return new DishVO(
            dish.getId(),
            dish.getName(),
            dish.getCategoryId(),
            dish.getPrice(),
            dish.getImage(),
            dish.getDescription(),
            dish.getStatus(),
            dish.getUpdateTime(),
            null,
            flavors
        );
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = Dish.builder()
                .id(dishDTO.id())
                .name(dishDTO.name())
                .categoryId(dishDTO.categoryId())
                .price(dishDTO.price())
                .image(dishDTO.image())
                .description(dishDTO.description())
                .status(dishDTO.status())
                .build();
        dishMapper.updateById(dish);

        dishFlavorMapper.delete(
            new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, dishDTO.id())
        );

        List<DishFlavor> flavors = dishDTO.flavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dishDTO.id());
                dishFlavorMapper.insert(flavor);
            });
        }
        log.info("菜品修改成功，ID: {}", dishDTO.id());

        String key = "dish_" + dishDTO.categoryId();
        redisTemplate.delete(key);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.updateById(dish);

        Set<String> keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            var dish = dishMapper.selectById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavor>()
                    .eq(DishFlavor::getDishId, id));
        }

        Set<String> keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        log.info("批量删除菜品成功，IDs: {}", ids);
    }

    @Override
    public List<DishVO> listWithFlavor(Long categoryId) {
        String key = "dish_" + categoryId;

        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);

        if (dishVOList != null && !dishVOList.isEmpty()) {
            return dishVOList;
        }

        log.info("查询数据库，分类ID：{}", categoryId);
        List<DishVO> dbList = dishMapper.listWithFlavor(categoryId);

        if (dbList != null && !dbList.isEmpty()) {
            redisTemplate.opsForValue().set(key, dbList, 1, TimeUnit.HOURS);
        }

        return dbList;
    }
}
