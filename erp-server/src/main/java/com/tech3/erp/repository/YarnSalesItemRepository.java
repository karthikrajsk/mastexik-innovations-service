package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnSalesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YarnSalesItemRepository extends JpaRepository<YarnSalesItem, Long> {
}
