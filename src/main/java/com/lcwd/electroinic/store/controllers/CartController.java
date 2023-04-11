package com.lcwd.electroinic.store.controllers;

import com.lcwd.electroinic.store.dtos.AddItemToCartRequest;
import com.lcwd.electroinic.store.dtos.ApiResponseMessage;
import com.lcwd.electroinic.store.dtos.CartDTO;
import com.lcwd.electroinic.store.entities.Cart;
import com.lcwd.electroinic.store.entities.CartItem;
import com.lcwd.electroinic.store.entities.Category;
import com.lcwd.electroinic.store.entities.Product;
import com.lcwd.electroinic.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request)
    {
        System.out.println("User Id : "+userId+" || quantity : "+request.getQuantity()+" || product Id "+request.getProductId());

        CartDTO cartDTO = this.cartService.addItemToCart(userId, request);
      return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int cartItemId)
    {
        this.cartService.deleteItemFromCart(userId,cartItemId);
    ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("delete success fully !!").success(true).httpStatus(HttpStatus.OK).build();
    return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearAll(@PathVariable String userId)
    {
        this.cartService.clearCart(userId);
        ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("clear success fully !!").success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable String userId)
    {
        CartDTO cartDTO = this.cartService.getCartByUser(userId);
return new ResponseEntity<>(cartDTO,HttpStatus.OK);

    }

}
