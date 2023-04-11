package com.lcwd.electroinic.store.dtos;

import com.lcwd.electroinic.store.entities.Cart;
import com.lcwd.electroinic.store.entities.Product;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartItemDTO {

    private int cartItemId;
    private ProductDTO product;

    private int totalPrice;
    private int quantity;

}
