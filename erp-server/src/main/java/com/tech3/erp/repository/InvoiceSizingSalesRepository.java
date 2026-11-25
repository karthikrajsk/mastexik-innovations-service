package com.tech3.erp.repository;

import com.tech3.erp.entity.InvoiceSizingSalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceSizingSalesRepository extends JpaRepository<InvoiceSizingSalesEntity, Long> {
    List<InvoiceSizingSalesEntity> findByInvoiceIdAndActiveFlagTrue(Long invoiceId);
    List<InvoiceSizingSalesEntity> findByInvoiceId(Long invoiceId);
    void deleteByInvoiceId(Long invoiceId);
}
