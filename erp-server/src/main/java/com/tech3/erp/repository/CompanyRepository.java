package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.entity.Company;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyCode(String companyCode);
    Optional<Company> findByGstNo(String gstNo);
    Optional<Company> findFirstByCompanyNameIgnoreCase(String companyName);
    boolean existsByCompanyCode(String companyCode);
    boolean existsByCompanyCodeIgnoreCase(String companyCode);
    boolean existsByGstNo(String gstNo);
    boolean existsByCompanyNameIgnoreCase(String companyName);

    @Modifying
    @Transactional
    @Query(value = "CALL masters.seed_default_reports(CAST(:companyId AS BIGINT))", nativeQuery = true)
    void seedDefaultReports(@Param("companyId") Long companyId);

    @Modifying
    @Transactional
    @Query(value = "CALL masters.seed_number_format_configurations(CAST(:companyId AS BIGINT))", nativeQuery = true)
    void seedNumberFormatConfigurations(@Param("companyId") Long companyId);
}
