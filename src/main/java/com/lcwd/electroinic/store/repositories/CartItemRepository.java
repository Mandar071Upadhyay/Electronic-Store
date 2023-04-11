package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
