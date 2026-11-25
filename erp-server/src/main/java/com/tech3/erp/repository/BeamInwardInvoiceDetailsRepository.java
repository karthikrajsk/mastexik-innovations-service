package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamInwardInvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeamInwardInvoiceDetailsRepository extends JpaRepository<BeamInwardInvoiceDetails, Long> {
    List<BeamInwardInvoiceDetails> findByBeamInwardId(Long beamInwardId);
    void deleteByBeamInwardId(Long beamInwardId);
}
