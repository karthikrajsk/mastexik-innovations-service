package com.tech3.erp.repository;

import com.tech3.erp.entity.AppSubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppSubMenuRepository extends JpaRepository<AppSubMenu, Long> {
    List<AppSubMenu> findByMenuActiveTrueAndActiveTrueOrderByOrderAsc();
}
