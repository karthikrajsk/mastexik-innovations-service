package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.WeavingProduction;

public interface WeavingProductionRepository extends JpaRepository<WeavingProduction, Long> {
    List<WeavingProduction> findAllByOrderByIdDesc();

    List<WeavingProduction> findByCompanyIdOrderByIdDesc(Long companyId);

    Optional<WeavingProduction> findByIdAndCompanyId(Long id, Long companyId);

    boolean existsByBeamId(Long beamId);

    boolean existsByBeamNo(String beamNo);

    boolean existsByBeamNoContainingIgnoreCase(String beamNo);
}
