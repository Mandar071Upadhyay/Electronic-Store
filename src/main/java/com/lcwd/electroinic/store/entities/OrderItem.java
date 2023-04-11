package com.lcwd.electroinic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int orderItemId;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private int totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;
}
