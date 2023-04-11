package com.lcwd.electroinic.store.controllers;

import com.lcwd.electroinic.store.dtos.ApiResponseMessage;
import com.lcwd.electroinic.store.dtos.CreateOrderRequest;
import com.lcwd.electroinic.store.dtos.OrderDTO;
import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping

    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest)
    {
        OrderDTO orderDTO = this.orderService.createOrder(createOrderRequest);
    return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId)
    {
        this.orderService.removeOrder(orderId);
        ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("Order Removed !! ").success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
@GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrderByUser(@PathVariable String userId)
    {
        List<OrderDTO> orders = this.orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<OrderDTO>> getOrders(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber, @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize, @RequestParam(value = "sortBy",defaultValue = "orderedDate",required = false) String sortBy, @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<OrderDTO> orders = this.orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
