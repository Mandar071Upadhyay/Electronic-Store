package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
