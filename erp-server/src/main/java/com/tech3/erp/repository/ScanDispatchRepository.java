package com.tech3.erp.repository;

import com.tech3.erp.entity.ScanDispatch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanDispatchRepository extends JpaRepository<ScanDispatch, Long> {

    @Query("SELECT s.id FROM ScanDispatch s ORDER BY s.id DESC")
    List<Long> findAllIdsDesc();

    @Query("SELECT s FROM ScanDispatch s WHERE s.id IN :ids ORDER BY s.id DESC")
    List<ScanDispatch> findByIds(@Param("ids") List<Long> ids);

    @Query("SELECT s.id FROM ScanDispatch s WHERE s.companyId = :companyId ORDER BY s.id DESC")
    List<Long> findIdsByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT s FROM ScanDispatch s WHERE s.companyId = :companyId AND s.id IN :ids ORDER BY s.id DESC")
    List<ScanDispatch> findByCompanyIdAndIds(@Param("companyId") Long companyId, @Param("ids") List<Long> ids);

    Optional<ScanDispatch> findByIdAndCompanyId(Long id, Long companyId);

    boolean existsByInvoiceId(Long invoiceId);
}
