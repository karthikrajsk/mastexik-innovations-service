package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.AgencyDispatch;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyDispatchRepository extends JpaRepository<AgencyDispatch, Long> {
    Optional<AgencyDispatch> findByMillInvoiceNo(String millInvoiceNo);

    @Query("SELECT d FROM AgencyDispatch d WHERE d.millInvoiceNo = :invoiceNo")
    Optional<AgencyDispatch> findByInvoiceNo(@Param("invoiceNo") String invoiceNo);

    List<AgencyDispatch> findByContractNo(String contractNo);
    List<AgencyDispatch> findBySeller(String seller);
    List<AgencyDispatch> findByCompanyId(Long companyId);
}
