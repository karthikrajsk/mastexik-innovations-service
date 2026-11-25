package com.tech3.erp.repository;

import com.tech3.erp.entity.GeneratePacking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneratePackingRepository extends JpaRepository<GeneratePacking, Long> {
        boolean existsBySalesOrderId(Long salesOrderId);
        boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);
    List<GeneratePacking> findByCompanyId(Long companyId);
    Optional<GeneratePacking> findByIdAndCompanyId(Long id, Long companyId);
    Optional<GeneratePacking> findByPackingSlipNo(String packingSlipNo);
    List<GeneratePacking> findBySalesOrderId(Long salesOrderId);
    List<GeneratePacking> findByWeavingWorkOrderId(Long weavingWorkOrderId);
    void deleteByIdAndCompanyId(Long id, Long companyId);

    @Transactional
    @Modifying
    @Query("UPDATE GeneratePacking p SET p.isInvoice = 1 WHERE p.packingSlipNo IN :slipNos")
    void markInvoicedBySlipNumbers(@Param("slipNos") List<String> slipNos);

    @Transactional
    @Modifying
    @Query("UPDATE GeneratePacking p SET p.isInvoice = 0, p.invoiceNo = null, p.invoiceDate = null WHERE p.packingSlipNo IN (SELECT itm.packingListNo FROM InvoiceItem itm WHERE itm.invoiceId = :invoiceId)")
    void resetInvoicedStatus(@Param("invoiceId") Long invoiceId);
}