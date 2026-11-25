package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeSalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSalaryHistoryRepository extends JpaRepository<EmployeeSalaryHistory, Long> {
    List<EmployeeSalaryHistory> findByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);
}
