package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.Order;
import com.lcwd.electroinic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUser(User user);
}
