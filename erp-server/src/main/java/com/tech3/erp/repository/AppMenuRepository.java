package com.tech3.erp.repository;

import com.tech3.erp.entity.AppMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppMenuRepository extends JpaRepository<AppMenu, Long> {
    List<AppMenu> findByActiveTrueOrderByOrderAsc();
}
