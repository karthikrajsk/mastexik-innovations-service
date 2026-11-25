package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamCutBeamDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeamCutBeamDetailsRepository extends JpaRepository<BeamCutBeamDetails, Long> {
    boolean existsByBeamId(Long beamId);
    boolean existsByBeamNo(String beamNo);
}
