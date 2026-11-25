package com.tech3.erp.repository;

import com.tech3.erp.entity.SalaryReportOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface SalaryReportOverrideRepository extends JpaRepository<SalaryReportOverride, Long> {
    Optional<SalaryReportOverride> findByEmployeeIdAndMonthAndYearAndCompanyId(Long employeeId, Integer month, Integer year, Long companyId);
    List<SalaryReportOverride> findByMonthAndYearAndCompanyId(Integer month, Integer year, Long companyId);
    List<SalaryReportOverride> findByMonthAndYear(Integer month, Integer year);
    List<SalaryReportOverride> findByMonthAndYearAndIsSalaryGenerated(Integer month, Integer year, Integer isSalaryGenerated);
    
    // Method to check existence by employeeId, month, and year (ignoring companyId for flexibility)
    boolean existsByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year);
}
