
package com.tech3.erp.repository;

import com.tech3.erp.entity.ReportConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportConfigurationRepository extends JpaRepository<ReportConfiguration, Long> {
    Optional<ReportConfiguration> findByReportNameAndCompanyId(String reportName, Long companyId);
    boolean existsByReportNameAndCompanyId(String reportName, Long companyId);
    List<ReportConfiguration> findByCompanyId(Long companyId);
    List<ReportConfiguration> findByCompanyIdAndActiveFlagTrue(Long companyId);
    List<ReportConfiguration> findAllByActiveFlagTrue();
    Optional<ReportConfiguration> findByIdAndCompanyId(Long id, Long companyId);
}
