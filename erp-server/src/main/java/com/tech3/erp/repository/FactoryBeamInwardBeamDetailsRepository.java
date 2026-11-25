package com.tech3.erp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tech3.erp.entity.FactoryBeamInwardBeamDetails;

@Repository
public interface FactoryBeamInwardBeamDetailsRepository extends JpaRepository<FactoryBeamInwardBeamDetails, Long> {
    List<FactoryBeamInwardBeamDetails> findByBeamInwardId(Long beamInwardId);
    void deleteByBeamInwardId(Long beamInwardId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FactoryBeamInwardBeamDetails d SET d.isProductionCompleted = 1 WHERE d.flangeId = :flangeId")
    int markProductionCompletedByFlangeId(@Param("flangeId") Long flangeId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FactoryBeamInwardBeamDetails d SET d.isProductionCompleted = 1 WHERE " +
           "(:flangeId IS NOT NULL AND d.flangeId = :flangeId) OR " +
           "(:beamId IS NOT NULL AND d.id = :beamId) OR " +
           "(:sizedBeamNo IS NOT NULL AND d.sizedBeamNo = :sizedBeamNo AND d.sizedBeamNo <> '')")
    int markProductionCompleted(@Param("flangeId") Long flangeId,
                                @Param("beamId") Long beamId,
                                @Param("sizedBeamNo") String sizedBeamNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FactoryBeamInwardBeamDetails d SET d.isProductionCompleted = 0 WHERE " +
           "(:flangeId IS NOT NULL AND d.flangeId = :flangeId) OR " +
           "(:beamId IS NOT NULL AND d.id = :beamId) OR " +
           "(:sizedBeamNo IS NOT NULL AND d.sizedBeamNo = :sizedBeamNo AND d.sizedBeamNo <> '')")
    int resetProductionCompleted(@Param("flangeId") Long flangeId,
                                 @Param("beamId") Long beamId,
                                 @Param("sizedBeamNo") String sizedBeamNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FactoryBeamInwardBeamDetails d SET d.isBeamCut = :status WHERE d.id = :beamId")
    int updateBeamCutStatus(@Param("beamId") Long beamId, @Param("status") Integer status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE FactoryBeamInwardBeamDetails d SET d.changedFabricMasterId = :changedFabricMasterId, d.changedFabricCodeQuality = :changedFabricCodeQuality WHERE d.sizedBeamNo = :sizedBeamNo")
    int updateChangedFabricDataBySizedBeamNo(@Param("sizedBeamNo") String sizedBeamNo, @Param("changedFabricMasterId") Long changedFabricMasterId, @Param("changedFabricCodeQuality") String changedFabricCodeQuality);
}
