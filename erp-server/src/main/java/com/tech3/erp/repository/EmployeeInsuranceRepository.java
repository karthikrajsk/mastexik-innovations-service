package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeInsuranceRepository extends JpaRepository<EmployeeInsurance, Long> {
    List<EmployeeInsurance> findByEmployeeId(Long employeeId);
}
