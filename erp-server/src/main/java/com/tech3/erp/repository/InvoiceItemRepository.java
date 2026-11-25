package com.tech3.erp.repository;

import com.tech3.erp.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    
    List<InvoiceItem> findByInvoiceId(Long invoiceId);
    List<InvoiceItem> findByInvoiceIdAndActiveFlagTrue(Long invoiceId);
    boolean existsByPackingListNoAndActiveFlagTrue(String packingListNo);

    @Modifying
    @Query("DELETE FROM InvoiceItem ii WHERE ii.invoiceId = :invoiceId")
    void deleteByInvoiceId(@Param("invoiceId") Long invoiceId);
    
    @Query("SELECT ii.fabricMasterId, SUM(ii.quantity) FROM InvoiceItem ii " +
           "WHERE ii.invoiceId IN (SELECT gi.id FROM GenerateInvoice gi WHERE gi.salesOrderId = :salesOrderId) " +
           "AND ii.activeFlag = true GROUP BY ii.fabricMasterId")
    List<Object[]> findDispatchedQuantityBySalesOrder(@Param("salesOrderId") Long salesOrderId);

    @Transactional
    @Modifying
    @Query("UPDATE InvoiceItem ii SET ii.isScannedPacking = 1 WHERE ii.invoiceId = :invoiceId AND ii.packingListNo IN :packingListNos")
    void markPackingScanned(@Param("invoiceId") Long invoiceId, @Param("packingListNos") List<String> packingListNos);

    @Transactional
    @Modifying
    @Query("UPDATE InvoiceItem ii SET ii.isScannedPacking = 0 WHERE ii.invoiceId = :invoiceId AND ii.packingListNo IN :packingListNos")
    void unmarkPackingScanned(@Param("invoiceId") Long invoiceId, @Param("packingListNos") List<String> packingListNos);
}
