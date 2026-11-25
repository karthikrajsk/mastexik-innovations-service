package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import com.tech3.erp.entity.SizingPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizingPlanRepository extends JpaRepository<SizingPlan, Long> {
    List<SizingPlan> findByCompanyIdOrderByIdDesc(Long companyId);
    Page<SizingPlan> findByCompanyId(Long companyId, Pageable pageable);
    Optional<SizingPlan> findByIdAndCompanyId(Long id, Long companyId);
}
