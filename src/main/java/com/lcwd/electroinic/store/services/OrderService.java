package com.lcwd.electroinic.store.services;

import com.lcwd.electroinic.store.dtos.CreateOrderRequest;
import com.lcwd.electroinic.store.dtos.OrderDTO;
import com.lcwd.electroinic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {
public OrderDTO createOrder(CreateOrderRequest createOrderRequest);
public void removeOrder(String orderId);
public List<OrderDTO> getOrderOfUser(String userId);
public PageableResponse<OrderDTO> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

}
