package com.lcwd.electroinic.store.repositories;

import com.lcwd.electroinic.store.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
