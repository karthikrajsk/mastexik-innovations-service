package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeAllowanceAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeAllowanceAdvanceRepository extends JpaRepository<EmployeeAllowanceAdvance, Long> {
    List<EmployeeAllowanceAdvance> findByEmployeeIdAndMonth(Long employeeId, String month);
    List<EmployeeAllowanceAdvance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
}
