package com.hhxy.service;

import com.hhxy.dto.ShoppingCartDTO;
import com.hhxy.entity.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
    List<ShoppingCart> showShoppingCart();
    void cleanShoppingCart();
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}