package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseInwardItem;
import com.tech3.erp.entity.YarnMaster;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInwardItemRepository extends JpaRepository<PurchaseInwardItem, Long> {
    
    @Query("SELECT pii FROM PurchaseInwardItem pii WHERE pii.purchaseInward.id = :purchaseInwardId")
    List<PurchaseInwardItem> findByPurchaseInwardId(@Param("purchaseInwardId") Long purchaseInwardId);

    @Query("SELECT COALESCE(SUM(pii.quantityReceived), 0) FROM PurchaseInwardItem pii " +
       "WHERE pii.purchaseOrderItemId = :poItemId AND pii.activeFlag = true")
BigDecimal sumQuantityReceivedByPoItemId(@Param("poItemId") Long poItemId);

@Query("SELECT COALESCE(SUM(pii.quantityReceived), 0) FROM PurchaseInwardItem pii " +
       "WHERE pii.purchaseOrderItemId = :poItemId AND pii.purchaseInward.id != :excludeInwardId " +
       "AND pii.activeFlag = true")
BigDecimal sumQuantityReceivedByPoItemIdExcludingInward(@Param("poItemId") Long poItemId, @Param("excludeInwardId") Long excludeInwardId);

    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM PurchaseInwardItem pii WHERE pii.purchaseInward.id = :purchaseInwardId")
    void deleteByPurchaseInwardId(@Param("purchaseInwardId") Long purchaseInwardId);





}
