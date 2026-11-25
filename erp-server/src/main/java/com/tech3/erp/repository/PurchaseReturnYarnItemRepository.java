package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseReturnYarnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseReturnYarnItemRepository extends JpaRepository<PurchaseReturnYarnItem, Long> {
}