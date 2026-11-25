package com.tech3.erp.repository;

import com.tech3.erp.entity.FactoryBeamInward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryBeamInwardRepository extends JpaRepository<FactoryBeamInward, Long> {
    List<FactoryBeamInward> findByFactoryCompanyId(Long factoryCompanyId);
    boolean existsBySizingPlanId(Long sizingPlanId);
    boolean existsByWeavingContractId(String weavingContractId);
    boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);
    boolean existsByBeamInwardId(Long beamInwardId);
    Optional<FactoryBeamInward> findFirstByBeamInwardId(Long beamInwardId);
    List<FactoryBeamInward> findByBeamInwardId(Long beamInwardId);
    Optional<FactoryBeamInward> findFirstByBeamOutwardId(Long beamOutwardId);
    List<FactoryBeamInward> findByBeamOutwardId(Long beamOutwardId);
    Optional<FactoryBeamInward> findFirstByBeamOutwardIdAndWeavingContractId(Long beamOutwardId, String weavingContractId);
    List<FactoryBeamInward> findBySizingPlanId(Long sizingPlanId);
}

