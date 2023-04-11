package com.lcwd.electroinic.store.dtos;

import com.lcwd.electroinic.store.entities.Order;
import com.lcwd.electroinic.store.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private int orderItemId;
    private ProductDTO product;

    private int quantity;
    private int totalPrice;


}
