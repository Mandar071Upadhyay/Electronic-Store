package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.Cart;
import com.lcwd.electroinic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
    Optional<Cart> findByUser(User user);
}
