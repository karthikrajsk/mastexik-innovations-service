package com.tech3.erp.repository;

import com.tech3.erp.entity.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {
    
    Optional<ItemMaster> findByItemCode(String itemCode);
    
    boolean existsByItemCode(String itemCode);
}

