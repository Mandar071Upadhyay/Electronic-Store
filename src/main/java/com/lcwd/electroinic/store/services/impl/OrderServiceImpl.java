package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.dtos.*;
import com.lcwd.electroinic.store.entities.*;
import com.lcwd.electroinic.store.exceptions.BadApiRequestException;
import com.lcwd.electroinic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electroinic.store.repositories.CartRepository;
import com.lcwd.electroinic.store.repositories.OrderItemRepository;
import com.lcwd.electroinic.store.repositories.OrderRepository;
import com.lcwd.electroinic.store.repositories.UserRepository;
import com.lcwd.electroinic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService {
    Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        String userId=createOrderRequest.getUserId();
        String cartId=createOrderRequest.getCartId();
        User user=this.userRepository.findById(userId).orElseThrow(()->{
            throw new ResourceNotFoundException("Invalid User Id : "+userId);
        });
        Cart cart=this.cartRepository.findById(cartId).orElseThrow(()->{
           throw new ResourceNotFoundException("Invalid Cart Id : "+cartId);
        });
        List<CartItem> items = cart.getItems();
        if(items.size()<=0)
        {
            throw new BadApiRequestException("There is no items in cart !!");
        }
        Order order=Order.builder().orderId(UUID.randomUUID().toString())
                .orderStatus(createOrderRequest.getOrderStatus())
                .paymentStatus(createOrderRequest.getPaymentStatus())
                .orderedDate(new Date())
                .billingAddress(createOrderRequest.getBillingAddress())
                .billingName(createOrderRequest.getBillingName())
                .billingPhone(createOrderRequest.getBillingPhone())
                .deliveredDate(null)
                .user(user)
                .build();
        AtomicReference<Integer> totalAmount=new AtomicReference<>(0);

        List<OrderItem> orderItems = items.stream().map((item) -> {
            OrderItem orderItem = OrderItem.builder().order(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            totalAmount.set(totalAmount.get()+item.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderAmount(totalAmount.get());
        order.setOrderItems(orderItems);
        cart.getItems().clear();
        this.cartRepository.save(cart);
        Order save = this.orderRepository.save(order);


        return this.modelMapper.map(save,OrderDTO.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid Order Id : " + orderId);
        });
        this.orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> getOrderOfUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid User Id : " + userId);
        });

        List<Order> orders = this.orderRepository.findByUser(user);



        List<OrderDTO> orderDTOS = orders.stream().map((order) -> {
            return this.modelMapper.map(order, OrderDTO.class);
        }).collect(Collectors.toList());
        return orderDTOS;
    }

    @Override
    public PageableResponse<OrderDTO> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = this.orderRepository.findAll(pageable);
        PageableResponse<OrderDTO> pageableResponse = Helper.getPageableResponse(page, OrderDTO.class);
          return pageableResponse;
    }
}
