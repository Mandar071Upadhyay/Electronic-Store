package com.lcwd.electroinic.store.dtos;

import com.lcwd.electroinic.store.entities.CartItem;
import com.lcwd.electroinic.store.entities.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private String cartId;
    private UserDTO user;
    private Date createdAt;
    private List<CartItemDTO> items=new ArrayList<>();
}
