package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeamCardRepository extends JpaRepository<BeamCard, Long> {

    Optional<BeamCard> findByBeamNo(String beamNo);

    boolean existsByBeamNo(String beamNo);
}
