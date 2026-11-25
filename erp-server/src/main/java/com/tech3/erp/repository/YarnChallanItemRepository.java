package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnChallanItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface YarnChallanItemRepository extends JpaRepository<YarnChallanItem, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE YarnChallanItem y SET y.invoiceId = :invoiceId, y.isInvoice = 1 WHERE y.yarnChallan.id IN :yarnChallanIds")
    void markInvoicedByChallanIds(@Param("yarnChallanIds") List<Long> yarnChallanIds, @Param("invoiceId") Long invoiceId);

    @Transactional
    @Modifying
    @Query("UPDATE YarnChallanItem y SET y.invoiceId = NULL, y.isInvoice = 0 WHERE y.invoiceId = :invoiceId")
    void resetInvoiceStatus(@Param("invoiceId") Long invoiceId);
}
