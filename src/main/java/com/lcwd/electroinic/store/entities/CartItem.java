package com.lcwd.electroinic.store.entities;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Cart_Items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int totalPrice;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
