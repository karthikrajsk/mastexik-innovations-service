package com.tech3.erp.repository;

import com.tech3.erp.entity.MenuMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuMasterRepository extends JpaRepository<MenuMaster, Long> {
    Optional<MenuMaster> findByScreenNameIgnoreCase(String screenName);
    Optional<MenuMaster> findByMenuUrlIgnoreCase(String menuUrl);
}
