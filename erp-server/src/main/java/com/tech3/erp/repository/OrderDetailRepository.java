package com.tech3.erp.repository;

import com.tech3.erp.entity.OrderDetail;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT COALESCE(SUM(od.plannedQuantity), 0) FROM OrderDetail od WHERE od.salesOrderId = :salesOrderId AND (od.activeFlag IS NULL OR od.activeFlag = true)")
    BigDecimal sumPlannedQuantityBySalesOrderId(@Param("salesOrderId") Long salesOrderId);

    @Query("SELECT COALESCE(SUM(od.plannedQuantity), 0) FROM OrderDetail od WHERE od.salesOrderId = :salesOrderId AND (od.activeFlag IS NULL OR od.activeFlag = true) AND od.weavingContract.id <> :excludeContractId")
    BigDecimal sumPlannedQuantityBySalesOrderIdExcludingContract(@Param("salesOrderId") Long salesOrderId, @Param("excludeContractId") Long excludeContractId);
}