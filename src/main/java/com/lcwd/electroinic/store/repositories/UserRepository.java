package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    public Optional<User> findByEmail(String email);
    public List<User> findByNameContaining(String keywords);
}
