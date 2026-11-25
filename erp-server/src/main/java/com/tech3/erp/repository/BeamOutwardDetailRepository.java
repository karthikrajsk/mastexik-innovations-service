package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamOutwardDetail;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BeamOutwardDetailRepository extends JpaRepository<BeamOutwardDetail, Long> {
    
    List<BeamOutwardDetail> findBySizedBeamNo(String sizedBeamNo);

    List<BeamOutwardDetail> findByReturnNo(String returnNo);
    
    List<BeamOutwardDetail> findBySizedBeamNoAndReturnNo(String sizedBeamNo, String returnNo);
    
    List<BeamOutwardDetail> findByBeamOutwardId(Long beamOutwardId);

    @Query("SELECT d FROM BeamOutwardDetail d WHERE d.sizedBeamNo = :sizedBeamNo AND (d.isReturn = 0 OR d.isReturn IS NULL)")
    List<BeamOutwardDetail> findBySizedBeamNoAndIsReturn(@Param("sizedBeamNo") String sizedBeamNo);

    @Query("SELECT d FROM BeamOutwardDetail d WHERE d.beamOutward.id = :beamOutwardId AND (d.isReturn = 0 OR d.isReturn IS NULL)")
    List<BeamOutwardDetail> findByBeamOutwardIdAndIsReturn(@Param("beamOutwardId") Long beamOutwardId);

    @Query("SELECT d FROM BeamOutwardDetail d WHERE d.beamOutward.id = :beamOutwardId AND d.sizedBeamNo = :sizedBeamNo AND (d.isReturn = 0 OR d.isReturn IS NULL)")
    List<BeamOutwardDetail> findByBeamOutwardIdAndSizedBeamNoAndIsReturn(@Param("beamOutwardId") Long beamOutwardId, @Param("sizedBeamNo") String sizedBeamNo);
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamOutwardDetail d SET d.changedFabricMasterId = :changedFabricMasterId, d.changedFabricCodeQuality = :changedFabricCodeQuality WHERE d.sizedBeamNo = :sizedBeamNo")
    int updateChangedFabricDataBySizedBeamNo(@Param("sizedBeamNo") String sizedBeamNo, @Param("changedFabricMasterId") Long changedFabricMasterId, @Param("changedFabricCodeQuality") String changedFabricCodeQuality);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE BeamOutwardDetail d SET d.isBeamGated = :value WHERE d.sizedBeamNo IN :beamNos")
    int updateIsBeamGatedBySizedBeamNoIn(@Param("beamNos") Collection<String> beamNos, @Param("value") Integer value);
}
