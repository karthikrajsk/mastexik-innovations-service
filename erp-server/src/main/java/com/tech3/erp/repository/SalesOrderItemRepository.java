package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
        @Query("SELECT COALESCE(SUM(soi.orderQty), 0) FROM SalesOrderItem soi WHERE soi.salesOrder.id = :salesOrderId AND (soi.activeFlag IS NULL OR soi.activeFlag = true)")
    Long sumOrderQuantityBySalesOrderId(@Param("salesOrderId") Long salesOrderId);
}
