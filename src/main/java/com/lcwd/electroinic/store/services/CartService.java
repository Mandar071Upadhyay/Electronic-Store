package com.lcwd.electroinic.store.services;

import com.lcwd.electroinic.store.dtos.AddItemToCartRequest;
import com.lcwd.electroinic.store.dtos.CartDTO;
import com.lcwd.electroinic.store.entities.Cart;

public interface CartService {
public CartDTO addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest);
public void deleteItemFromCart(String userId,int cartItemId);
public void clearCart(String userId);
public CartDTO getCartByUser(String userId);
}
