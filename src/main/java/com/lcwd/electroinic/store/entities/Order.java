package com.lcwd.electroinic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private int orderAmount;

    private String billingName;

    private String billingPhone;
    private String billingAddress;

    private Date orderedDate;
    private Date deliveredDate;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id")
    private User user;
   @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
private List<OrderItem> orderItems=new ArrayList<>();

}
