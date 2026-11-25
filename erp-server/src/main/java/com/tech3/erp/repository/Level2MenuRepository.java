package com.tech3.erp.repository;

import com.tech3.erp.entity.Level2Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Level2MenuRepository extends JpaRepository<Level2Menu, Long> {
    List<Level2Menu> findByUserId(Long userId);
}