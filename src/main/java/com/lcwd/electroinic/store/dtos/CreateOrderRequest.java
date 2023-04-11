package com.lcwd.electroinic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
  @NotBlank(message = "User Id should not be blank !! ")
private String userId;
    @NotBlank(message = "cart Id should not be blank !! ")
private String cartId;
@NotBlank(message = "Order Status should not be blank !! ")

    private String orderStatus;
    @NotBlank(message = "Payment Status should not be blank !! ")

    private String paymentStatus;
    @NotBlank(message = "Billing Name should not be blank !! ")

    private String billingName;

    @NotBlank(message = "Billing Phone should not be blank !! ")

    private String billingPhone;
    @NotBlank(message = "Billing Address should not be blank !! ")

    private String billingAddress;


    private Date orderedDate;
    private Date deliveredDate;
}
