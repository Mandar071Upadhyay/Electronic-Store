package com.lcwd.electroinic.store.dtos;

import com.lcwd.electroinic.store.entities.OrderItem;
import com.lcwd.electroinic.store.entities.User;
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
public class OrderDTO {
    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private int orderAmount;

    private String billingName;

    private String billingPhone;
    private String billingAddress;

    private Date orderedDate;
    private Date deliveredDate;
    private UserDTO user;
    private List<OrderItemDTO> orderItems=new ArrayList<>();

}
