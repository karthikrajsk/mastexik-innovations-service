package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YarnReturnItemRepository extends JpaRepository<YarnReturnItem, Long> {
}