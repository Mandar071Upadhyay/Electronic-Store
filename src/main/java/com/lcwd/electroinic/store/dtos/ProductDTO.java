package com.lcwd.electroinic.store.dtos;

import com.lcwd.electroinic.store.entities.Category;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
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
    private CategoryDTO category;

}
