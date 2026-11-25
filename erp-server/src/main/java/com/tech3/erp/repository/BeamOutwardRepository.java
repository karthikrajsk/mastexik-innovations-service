package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.BeamOutward;

public interface BeamOutwardRepository extends JpaRepository<BeamOutward, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);
    boolean existsByWeavingContractIdAndCompanyId(Long weavingContractId, Long companyId);
    boolean existsByWeavingContractIdAndOrderTypeId(Long weavingContractId, Long orderTypeId);
    boolean existsByWeavingContractIdAndCompanyIdAndOrderTypeId(Long weavingContractId, Long companyId, Long orderTypeId);
    boolean existsBySalesOrderId(Long salesOrderId);
    boolean existsBySizingPlanId(Long sizingPlanId);
    boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);
    boolean existsByBeamInwardId(Long beamInwardId);
    boolean existsByBeamInwardNo(String beamInwardNo);
    boolean existsByOutwardNo(String outwardNo);

    List<BeamOutward> findByBeamInwardId(Long beamInwardId);
    List<BeamOutward> findBySizingPlanId(Long sizingPlanId);
    List<BeamOutward> findByCompanyId(Long companyId);
    Page<BeamOutward> findByCompanyId(Long companyId, Pageable pageable);
    Optional<BeamOutward> findByIdAndCompanyId(Long id, Long companyId);
    boolean existsByIdAndCompanyId(Long id, Long companyId);
    void deleteByIdAndCompanyId(Long id, Long companyId);
    Optional<BeamOutward> findFirstBySizingPlanIdAndWeavingVendorId(Long sizingPlanId, Long weavingVendorId);
    Optional<BeamOutward> findFirstBySizingBeamOutwardIdAndWeavingVendorId(Long sizingBeamOutwardId, Long weavingVendorId);
    List<BeamOutward> findBySizingBeamOutwardId(Long sizingBeamOutwardId);
    
    List<BeamOutward> findBySizedBeamNo(String sizedBeamNo);
        List<BeamOutward> findByWeavingContractIdAndSizedBeamNo(Long weavingContractId, String sizedBeamNo);
    }
