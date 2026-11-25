package com.tech3.erp.repository;


import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.BeamInwardBeamDetails;

@Repository
public interface BeamInwardBeamDetailsRepository extends JpaRepository<BeamInwardBeamDetails, Long> {
  boolean existsByWeavingContractId(Long weavingContractId);
    boolean existsBySalesOrderId(Long salesOrderId);
    boolean existsBySizingPlanId(Long sizingPlanId);
	List<BeamInwardBeamDetails> findByBeamInwardId(Long beamInwardId);
	void deleteByBeamInwardId(Long beamInwardId);
	List<BeamInwardBeamDetails> findByBeamInwardIdAndSizedBeamNo(Long beamInwardId, String sizedBeamNo);
	List<BeamInwardBeamDetails> findByBeamInwardIdAndSizedBeamNoAndFlangeNo(Long beamInwardId, String sizedBeamNo, String flangeNo);
	List<BeamInwardBeamDetails> findBySizedBeamNoIn(Collection<String> sizedBeamNos);
	List<BeamInwardBeamDetails> findBySizedBeamNoAndReturnNo(String sizedBeamNo, String returnNo);

	@Query("SELECT d FROM BeamInwardBeamDetails d WHERE d.beamInward.id = :beamInwardId AND d.sizedBeamNo = :sizedBeamNo AND (d.isReturn = 0 OR d.isReturn IS NULL)")
	List<BeamInwardBeamDetails> findByBeamInwardIdAndSizedBeamNoAndIsReturn(@Param("beamInwardId") Long beamInwardId, @Param("sizedBeamNo") String sizedBeamNo);

	@Query("SELECT d FROM BeamInwardBeamDetails d WHERE d.sizedBeamNo = :sizedBeamNo AND (d.isReturn = 0 OR d.isReturn IS NULL)")
	List<BeamInwardBeamDetails> findBySizedBeamNoAndIsReturn(@Param("sizedBeamNo") String sizedBeamNo);

	@Query("SELECT d FROM BeamInwardBeamDetails d WHERE d.weavingContractId = :weavingContractId AND d.sizedBeamNo = :sizedBeamNo AND (d.isReturn = 0 OR d.isReturn IS NULL)")
	List<BeamInwardBeamDetails> findByWeavingContractIdAndSizedBeamNoAndIsReturn(@Param("weavingContractId") Long weavingContractId, @Param("sizedBeamNo") String sizedBeamNo);
    List<BeamInwardBeamDetails> findByReturnNo(String returnNo);
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamInwardBeamDetails d SET d.changedFabricMasterId = :changedFabricMasterId, d.changedFabricCodeQuality = :changedFabricCodeQuality WHERE d.sizedBeamNo = :sizedBeamNo")
    int updateChangedFabricDataBySizedBeamNo(@Param("sizedBeamNo") String sizedBeamNo, @Param("changedFabricMasterId") Long changedFabricMasterId, @Param("changedFabricCodeQuality") String changedFabricCodeQuality);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE BeamInwardBeamDetails d SET d.isBeamGated = :value WHERE d.sizedBeamNo IN :beamNos")
    int updateIsBeamGatedBySizedBeamNoIn(@Param("beamNos") Collection<String> beamNos, @Param("value") Integer value);
}
