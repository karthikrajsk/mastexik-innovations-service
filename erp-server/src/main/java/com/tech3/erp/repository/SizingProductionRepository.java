package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.SizingProduction;

public interface SizingProductionRepository extends JpaRepository<SizingProduction, Long> {
    List<SizingProduction> findAllByOrderByIdDesc();

    List<SizingProduction> findByCompanyIdOrderByIdDesc(Long companyId);

    boolean existsBySizingPlanId(Long sizingPlanId);
    boolean existsBySizingPlanNo(String sizingPlanNo);
    boolean existsBySizingOrderId(Long sizingOrderId);

    Optional<SizingProduction> findByIdAndCompanyId(Long id, Long companyId);
    
    List<SizingProduction> findBySizingPlanId(Long sizingPlanId);
}
