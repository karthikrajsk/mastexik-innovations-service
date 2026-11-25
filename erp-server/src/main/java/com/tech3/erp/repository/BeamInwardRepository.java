package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamInward;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeamInwardRepository extends JpaRepository<BeamInward, Long> {
    List<BeamInward> findByCompanyIdOrderByIdDesc(Long companyId);
    Page<BeamInward> findByCompanyId(Long companyId, Pageable pageable);
    Optional<BeamInward> findByIdAndCompanyId(Long id, Long companyId);
    List<BeamInward> findBySizingPlanId(Long sizingPlanId);
    boolean existsBySizingPlanId(Long sizingPlanId);
    Optional<BeamInward> findFirstBySizingPlanIdAndCompanyId(Long sizingPlanId, Long companyId);
    Optional<BeamInward> findFirstBySizingBeamOutwardIdAndCompanyId(Long sizingBeamOutwardId, Long companyId);
    List<BeamInward> findBySizingBeamOutwardId(Long sizingBeamOutwardId);
}
