package com.tech3.erp.repository;

import com.tech3.erp.entity.Level1MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Level1MenuItemRepository extends JpaRepository<Level1MenuItem, Long> {
    List<Level1MenuItem> findByNameContainingIgnoreCase(String name);
}
