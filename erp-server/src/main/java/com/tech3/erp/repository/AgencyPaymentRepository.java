package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.AgencyPayment;
import java.util.List;

@Repository
public interface AgencyPaymentRepository extends JpaRepository<AgencyPayment, Long> {
    List<AgencyPayment> findByMillInvoiceNo(String millInvoiceNo);
    List<AgencyPayment> findBySeller(String seller);
    List<AgencyPayment> findByCompanyId(Long companyId);
}
