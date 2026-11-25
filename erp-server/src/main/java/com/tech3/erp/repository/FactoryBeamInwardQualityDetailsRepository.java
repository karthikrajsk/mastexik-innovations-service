package com.tech3.erp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.FactoryBeamInwardQualityDetails;

@Repository
public interface FactoryBeamInwardQualityDetailsRepository extends JpaRepository<FactoryBeamInwardQualityDetails, Long> {
    List<FactoryBeamInwardQualityDetails> findByBeamInwardId(Long beamInwardId);
    void deleteByBeamInwardId(Long beamInwardId);
}
