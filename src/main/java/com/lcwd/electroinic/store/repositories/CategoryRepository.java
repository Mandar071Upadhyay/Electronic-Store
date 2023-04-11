package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
