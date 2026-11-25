package com.tech3.erp.repository;

import com.tech3.erp.entity.SizingBeamOutward;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizingBeamOutwardRepository extends JpaRepository<SizingBeamOutward, Long> {
    List<SizingBeamOutward> findAllByOrderByIdDesc();
    List<SizingBeamOutward> findByCompanyIdOrderByIdDesc(Long companyId);
    boolean existsBySizingPlanId(Long sizingPlanId);
    boolean existsBySizingPlanNo(String sizingPlanNo);
    boolean existsBySizingOrderId(Long sizingOrderId);
    boolean existsBySizingOrderNo(String sizingOrderNo);
    
    Optional<SizingBeamOutward> findByIdAndCompanyId(Long id, Long companyId);
    List<SizingBeamOutward> findBySizingPlanId(Long sizingPlanId);
    List<SizingBeamOutward> findByReturnNo(String returnNo);
    boolean existsBySizingBeamOutwardNo(String sizingBeamOutwardNo);
}