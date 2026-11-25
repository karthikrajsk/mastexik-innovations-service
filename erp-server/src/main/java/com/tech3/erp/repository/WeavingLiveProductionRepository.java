package com.tech3.erp.repository;

import com.tech3.erp.entity.WeavingLiveProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeavingLiveProductionRepository extends JpaRepository<WeavingLiveProduction, Long> {

    List<WeavingLiveProduction> findByCompanyId(Long companyId);

    void deleteByCompanyId(Long companyId);
}
