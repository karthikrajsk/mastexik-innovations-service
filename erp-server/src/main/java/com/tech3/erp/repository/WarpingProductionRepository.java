package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.WarpingProduction;

public interface WarpingProductionRepository extends JpaRepository<WarpingProduction, Long> {
    List<WarpingProduction> findAllByOrderByIdDesc();

    List<WarpingProduction> findByCompanyIdOrderByIdDesc(Long companyId);

    Optional<WarpingProduction> findByIdAndCompanyId(Long id, Long companyId);

    boolean existsBySizingOrderId(Long sizingOrderId);
}
