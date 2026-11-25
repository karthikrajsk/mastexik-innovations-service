package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeePerformanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformanceRecord, Long> {
    List<EmployeePerformanceRecord> findByEmployeeId(Long employeeId);
}
