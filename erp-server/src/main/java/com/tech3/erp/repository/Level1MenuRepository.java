package com.tech3.erp.repository;

import com.tech3.erp.entity.Level1Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Level1MenuRepository extends JpaRepository<Level1Menu, Long> {
    List<Level1Menu> findByUserId(Long userId);
}