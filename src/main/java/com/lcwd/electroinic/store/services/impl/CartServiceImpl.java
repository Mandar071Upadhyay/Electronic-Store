package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.dtos.AddItemToCartRequest;
import com.lcwd.electroinic.store.dtos.CartDTO;
import com.lcwd.electroinic.store.dtos.CartItemDTO;
import com.lcwd.electroinic.store.dtos.UserDTO;
import com.lcwd.electroinic.store.entities.Cart;
import com.lcwd.electroinic.store.entities.CartItem;
import com.lcwd.electroinic.store.entities.Product;
import com.lcwd.electroinic.store.entities.User;
import com.lcwd.electroinic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electroinic.store.repositories.CartItemRepository;
import com.lcwd.electroinic.store.repositories.CartRepository;
import com.lcwd.electroinic.store.repositories.ProductRepository;
import com.lcwd.electroinic.store.repositories.UserRepository;
import com.lcwd.electroinic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest) {
        String productId=addItemToCartRequest.getProductId();
        int quantity=addItemToCartRequest.getQuantity();
        Product product = this.productRepository.findById(productId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid Product Id : " + productId);

        });


        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid User Id : " + userId);
        });
        Cart cart=null;
try{
    cart = this.cartRepository.findByUser(user).get();

}catch (NoSuchElementException noSuchElementException)
{
   cart=new Cart();
   cart.setCartId(UUID.randomUUID().toString());
   cart.setCreatedAt(new Date());

}
AtomicReference<Boolean> updated= new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items= items.stream().map((object) -> {
            if (object.getProduct().getProductId().equals(productId)) {
 updated.set(true);
                object.setQuantity(quantity);
                object.setTotalPrice(quantity * product.getDiscountedPrice());
            }
            return object;
        }).collect(Collectors.toList());


    if(!updated.get())
    {
        CartItem cartItem=CartItem.builder().cart(cart).quantity(quantity).product(product).totalPrice(quantity*product.getDiscountedPrice()).build();
        cart.getItems().add(cartItem);
    }
    cart.setUser(user);
        Cart save = this.cartRepository.save(cart);
     return this.modelMapper.map(save,CartDTO.class);

    }

    @Override
    public void deleteItemFromCart(String userId, int cartItemId) {
        CartItem cartItem = this.cartItemRepository.findById(cartItemId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid CartItemId : " + cartItemId);
        });
        this.cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid UserId : " + userId);
        });
        Cart cart = this.cartRepository.findByUser(user).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid User : !!");
        });
        cart.getItems().clear();
        this.cartRepository.save(cart);

    }

    @Override
    public CartDTO getCartByUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid UserId : " + userId);
        });
        Cart cart = this.cartRepository.findByUser(user).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid User : !!");
        });

        return this.modelMapper.map(cart,CartDTO.class);
    }
}
