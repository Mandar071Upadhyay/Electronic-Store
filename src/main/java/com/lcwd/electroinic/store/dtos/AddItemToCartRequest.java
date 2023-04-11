package com.lcwd.electroinic.store.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartRequest {
private String productId;
private int quantity;

}
