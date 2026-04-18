package com.hhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhxy.context.BaseContext;
import com.hhxy.dto.ShoppingCartDTO;
import com.hhxy.entity.Dish;
import com.hhxy.entity.Setmeal;
import com.hhxy.entity.ShoppingCart;
import com.hhxy.mapper.DishMapper;
import com.hhxy.mapper.SetmealMapper;
import com.hhxy.mapper.ShoppingCartMapper;
import com.hhxy.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.getCurrentId();

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        
        if (shoppingCartDTO.dishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCartDTO.dishId());
            if (shoppingCartDTO.dishFlavor() != null) {
                queryWrapper.eq(ShoppingCart::getDishFlavor, shoppingCartDTO.dishFlavor());
            }
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCartDTO.setmealId());
        }

        ShoppingCart cartResult = shoppingCartMapper.selectOne(queryWrapper);

        if (cartResult != null) {
            cartResult.setNumber(cartResult.getNumber() + 1);
            shoppingCartMapper.updateById(cartResult);
        } else {
            if (shoppingCartDTO.dishId() != null) {
                Dish dish = dishMapper.selectById(shoppingCartDTO.dishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealMapper.selectById(shoppingCartDTO.setmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();

        return shoppingCartMapper.selectList(
                new LambdaQueryWrapper<ShoppingCart>()
                        .eq(ShoppingCart::getUserId, userId)
                        .orderByDesc(ShoppingCart::getCreateTime)
        );
    }

    @Override
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();

        shoppingCartMapper.delete(
                new LambdaQueryWrapper<ShoppingCart>()
                        .eq(ShoppingCart::getUserId, userId)
        );
        log.info("用户 {} 的购物车已清空", userId);
    }

    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        
        if (shoppingCartDTO.dishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCartDTO.dishId());
            if (shoppingCartDTO.dishFlavor() != null) {
                queryWrapper.eq(ShoppingCart::getDishFlavor, shoppingCartDTO.dishFlavor());
            }
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCartDTO.setmealId());
        }

        ShoppingCart cartInDb = shoppingCartMapper.selectOne(queryWrapper);

        if (cartInDb != null) {
            if (cartInDb.getNumber() > 1) {
                cartInDb.setNumber(cartInDb.getNumber() - 1);
                shoppingCartMapper.updateById(cartInDb);
            } else {
                shoppingCartMapper.deleteById(cartInDb.getId());
            }
        }
    }
}