package com.tech3.erp.repository;

import com.tech3.erp.entity.GenerateInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenerateInvoiceRepository extends JpaRepository<GenerateInvoice, Long> {
        boolean existsBySalesOrderId(Long salesOrderId);
        boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);
        boolean existsByWoNo(String woNo);

    List<GenerateInvoice> findByCompanyId(Long companyId);
    Optional<GenerateInvoice> findByIdAndCompanyId(Long id, Long companyId);
    boolean existsByYarnSalesOrderId(Long yarnSalesOrderId);

    boolean existsByYarnSalesOrderIdAndActiveFlagTrue(Long yarnSalesOrderId);

    boolean existsByInvoiceNoAndActiveFlagTrue(String invoiceNo);
}
