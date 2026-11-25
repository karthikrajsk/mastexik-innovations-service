package com.tech3.erp.repository;

import com.tech3.erp.entity.AddDiscItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddDiscItemRepository extends JpaRepository<AddDiscItem, Long> {
    List<AddDiscItem> findByInvoiceId(Long invoiceId);
    List<AddDiscItem> findByInvoiceIdAndActiveFlagTrue(Long invoiceId);
    List<AddDiscItem> findByInvoiceIdAndIsDraftAndActiveFlagTrue(Long invoiceId, Boolean isDraft);

    @Modifying
    @Query("DELETE FROM AddDiscItem a WHERE a.invoiceId = :invoiceId")
    void deleteByInvoiceId(@Param("invoiceId") Long invoiceId);

    @Query("SELECT a FROM AddDiscItem a WHERE a.invoiceId = :invoiceId AND a.activeFlag = true AND (a.isDraft = false OR a.isDraft IS NULL)")
    List<AddDiscItem> findActiveNonDraftByInvoiceId(@Param("invoiceId") Long invoiceId);
}
