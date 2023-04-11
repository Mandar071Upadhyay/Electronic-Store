package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.Category;
import com.lcwd.electroinic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    public Page<Product> findByTitleContaining(String keyword, Pageable pageable);
    public Page<Product> findByLiveTrue(Pageable pageable);

    public Page<Product> findByCategory(Category category,Pageable pageable);
}
