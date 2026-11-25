package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeePfWageCeilingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePfWageCeilingHistoryRepository extends JpaRepository<EmployeePfWageCeilingHistory, Long> {
    List<EmployeePfWageCeilingHistory> findByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);
}
