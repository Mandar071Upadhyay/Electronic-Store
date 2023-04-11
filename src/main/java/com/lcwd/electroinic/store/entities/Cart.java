package com.lcwd.electroinic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    private String cartId;
    @OneToOne
    private User user;
    private Date createdAt;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<CartItem> items=new ArrayList<>();

}
