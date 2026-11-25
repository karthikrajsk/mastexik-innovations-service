package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamGatingDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BeamGatingDetailsRepository extends JpaRepository<BeamGatingDetails, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);
    boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);
    boolean existsByWeavingWorkOrderNo(String weavingWorkOrderNo);
    boolean existsByBeamId(Long beamId);
    boolean existsByBeamNo(String beamNo);
    List<BeamGatingDetails> findByBeamNo(String beamNo);

    List<BeamGatingDetails> findByBeamGatingId(Long beamGatingId);

    @Query("SELECT d FROM BeamGatingDetails d WHERE " +
           "(:beamId IS NOT NULL AND d.beamId = :beamId) OR " +
           "(:flangeId IS NOT NULL AND d.flangeId = :flangeId) OR " +
           "(:beamNo IS NOT NULL AND d.beamNo = :beamNo) OR " +
           "(:flangeNo IS NOT NULL AND d.flangeNo = :flangeNo)")
    List<BeamGatingDetails> findMatchingDetails(@Param("beamId") Long beamId,
                                                @Param("flangeId") Long flangeId,
                                                @Param("beamNo") String beamNo,
                                                @Param("flangeNo") String flangeNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isProductionCompleted = 1 WHERE d.flangeId = :flangeId")
    int markProductionCompletedByFlangeId(@Param("flangeId") Long flangeId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isProductionCompleted = 1 WHERE " +
           "(:beamId IS NOT NULL AND d.beamId = :beamId) OR " +
           "(:flangeId IS NOT NULL AND d.flangeId = :flangeId) OR " +
           "(:beamNo IS NOT NULL AND d.beamNo = :beamNo AND d.beamNo <> '') OR " +
           "(:flangeNo IS NOT NULL AND d.flangeNo = :flangeNo AND d.flangeNo <> '')")
    int markProductionCompleted(@Param("beamId") Long beamId,
                                @Param("flangeId") Long flangeId,
                                @Param("beamNo") String beamNo,
                                @Param("flangeNo") String flangeNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isProductionCompleted = 0 WHERE " +
           "(:beamId IS NOT NULL AND d.beamId = :beamId) OR " +
           "(:flangeId IS NOT NULL AND d.flangeId = :flangeId) OR " +
           "(:beamNo IS NOT NULL AND d.beamNo = :beamNo AND d.beamNo <> '') OR " +
           "(:flangeNo IS NOT NULL AND d.flangeNo = :flangeNo AND d.flangeNo <> '')")
    int resetProductionCompleted(@Param("beamId") Long beamId,
                                 @Param("flangeId") Long flangeId,
                                 @Param("beamNo") String beamNo,
                                 @Param("flangeNo") String flangeNo);

    @Query("SELECT d FROM BeamGatingDetails d WHERE d.beamGating.loomId = :loomId AND (d.isProductionCompleted IS NULL OR d.isProductionCompleted = 0)")
    List<BeamGatingDetails> findActiveDetailsByLoomId(@Param("loomId") Long loomId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isBeamCut = 1 WHERE (:beamId IS NOT NULL AND d.beamId = :beamId) OR (:beamNo IS NOT NULL AND d.beamNo = :beamNo)")
    int markBeamCutByBeamIdOrBeamNo(@Param("beamId") Long beamId, @Param("beamNo") String beamNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.remainingMeters = :remainingMeters WHERE (:beamId IS NOT NULL AND d.beamId = :beamId) OR (:beamNo IS NOT NULL AND d.beamNo = :beamNo)")
    int updateRemainingMetersByBeamIdOrBeamNo(@Param("beamId") Long beamId,
                                               @Param("beamNo") String beamNo,
                                               @Param("remainingMeters") Double remainingMeters);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isDoffedBeam = 1 WHERE (:beamId IS NOT NULL AND d.beamId = :beamId) OR (:beamNo IS NOT NULL AND d.beamNo = :beamNo)")
    int markDoffedBeamByBeamIdOrBeamNo(@Param("beamId") Long beamId,
                                       @Param("beamNo") String beamNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isBeamCut = 0 WHERE (:beamId IS NOT NULL AND d.beamId = :beamId) OR (:beamNo IS NOT NULL AND d.beamNo = :beamNo)")
    int resetBeamCutByBeamIdOrBeamNo(@Param("beamId") Long beamId, @Param("beamNo") String beamNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE BeamGatingDetails d SET d.isDoffedBeam = 0 WHERE (:beamId IS NOT NULL AND d.beamId = :beamId) OR (:beamNo IS NOT NULL AND d.beamNo = :beamNo)")
    int resetDoffedBeamByBeamIdOrBeamNo(@Param("beamId") Long beamId, @Param("beamNo") String beamNo);
}
