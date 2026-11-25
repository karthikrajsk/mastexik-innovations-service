package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseReturnFabricItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseReturnFabricItemRepository extends JpaRepository<PurchaseReturnFabricItem, Long> {
}