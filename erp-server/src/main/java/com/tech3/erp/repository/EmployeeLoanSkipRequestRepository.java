package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeLoanSkipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeLoanSkipRequestRepository extends JpaRepository<EmployeeLoanSkipRequest, Long> {
    List<EmployeeLoanSkipRequest> findByEmployeeLoanId(Long loanId);
    List<EmployeeLoanSkipRequest> findByEmployeeLoanIdAndStatus(Long loanId, String status);
}
