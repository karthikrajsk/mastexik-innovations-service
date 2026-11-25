package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamOutward;
import com.tech3.erp.entity.SizingBeamDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizingBeamDetailsRepository extends JpaRepository<SizingBeamDetails, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);
    boolean existsBySalesOrderId(Long salesOrderId);
    List<SizingBeamDetails> findBySizingPlanId(Long sizingPlanId);
    void deleteBySizingPlanId(Long sizingPlanId);
    List<SizingBeamDetails> findBySizingPlanIdAndWeavingContractIdIn(Long sizingPlanId, List<Long> weavingContractIds);

    
    // Add this method to get beams with status 0 for a specific sizing plan
    List<SizingBeamDetails> findBySizingPlanIdAndStatus(Long sizingPlanId, Integer status);
}
