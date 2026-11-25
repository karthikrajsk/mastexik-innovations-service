package com.tech3.erp.repository;

import com.tech3.erp.entity.NumberFormatConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NumberFormatConfigurationRepository extends JpaRepository<NumberFormatConfiguration, Long> {

    Optional<NumberFormatConfiguration> findByScreenNameAndTabName(String screenName, String tabName);

    Optional<NumberFormatConfiguration> findByScreenNameAndTabNameAndCompanyId(String screenName, String tabName, Long companyId);

    boolean existsByScreenNameAndTabNameAndCompanyId(String screenName, String tabName, Long companyId);

    List<NumberFormatConfiguration> findByScreenName(String screenName);

    List<NumberFormatConfiguration> findByCompanyId(Long companyId);

    Optional<NumberFormatConfiguration> findByIdAndCompanyId(Long id, Long companyId);

    boolean existsByScreenNameAndTabName(String screenName, String tabName);
}