package com.lcwd.electroinic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name="productes")
public class Product {
    @Id
    private String productId;
    private String title;
    @Column(length = 10000)
    private  String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private Boolean live;
    private Boolean stock;
    private String productImageName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
