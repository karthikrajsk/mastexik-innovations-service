package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesReturnBeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesReturnBeamRepository extends JpaRepository<SalesReturnBeamEntity, Long> {
    boolean existsByWeavingContractNo(String weavingContractNo);
    boolean existsBySizingPlanNo(String sizingPlanNo);

    List<SalesReturnBeamEntity> findBySalesReturnId(Long salesReturnId);
    
    List<SalesReturnBeamEntity> findByBeamOutwardId(Long beamOutwardId);
    
    List<SalesReturnBeamEntity> findByBeamInwardId(Long beamInwardId);

    List<SalesReturnBeamEntity> findBySizedBeamNoAndReturnNo(String sizedBeamNo, String returnNo);
    
    @Query("SELECT srb FROM SalesReturnBeamEntity srb WHERE (srb.sizedBeamNo = :sizedBeamNo OR srb.sizedBeamNo = :sizedBeamNoNoSuffix) AND srb.salesReturn.companyId = :companyId")
    List<SalesReturnBeamEntity> findMatchingBeams(@Param("sizedBeamNo") String sizedBeamNo, @Param("sizedBeamNoNoSuffix") String sizedBeamNoNoSuffix, @Param("companyId") Long companyId);

    @Modifying
    @Transactional
    void deleteBySalesReturnId(Long salesReturnId);
}
