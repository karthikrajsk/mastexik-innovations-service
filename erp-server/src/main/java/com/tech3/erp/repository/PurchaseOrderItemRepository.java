package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseOrderItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
    boolean existsBySalesOrderId(Long salesOrderId);
    boolean existsByItemId(Long itemId);
    @Query("SELECT p.fabricCode FROM PurchaseOrderItem p WHERE p.id = :id")
    Optional<String> findFabricCodeById(@Param("id") Long id);
    
    Optional<PurchaseOrderItem> findById(Long id);

    @Query("SELECT poi FROM PurchaseOrderItem poi " +
           "JOIN FETCH poi.purchaseOrder po " +
           "WHERE poi.itemId = :itemId " +
           "AND (:companyId IS NULL OR po.companyId = :companyId) " +
           "AND (po.activeFlag = true OR po.activeFlag IS NULL) " +
           "AND (poi.activeFlag = true OR poi.activeFlag IS NULL) " +
           "AND (po.isApproved IS NULL OR po.isApproved != 2) " +
           "ORDER BY po.poDate DESC, po.id DESC, poi.id DESC")
    List<PurchaseOrderItem> findPurchaseHistoryByItemId(
            @Param("itemId") Long itemId,
            @Param("companyId") Long companyId,
            Pageable pageable);

    @Query("SELECT COUNT(poi) FROM PurchaseOrderItem poi " +
           "JOIN poi.purchaseOrder po " +
           "WHERE poi.itemId = :itemId " +
           "AND (:companyId IS NULL OR po.companyId = :companyId) " +
           "AND (po.activeFlag = true OR po.activeFlag IS NULL) " +
           "AND (poi.activeFlag = true OR poi.activeFlag IS NULL) " +
           "AND (po.isApproved IS NULL OR po.isApproved != 2)")
    long countPurchaseHistoryByItemId(
            @Param("itemId") Long itemId,
            @Param("companyId") Long companyId);
}
