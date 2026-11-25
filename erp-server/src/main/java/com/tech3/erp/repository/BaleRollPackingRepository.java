package com.tech3.erp.repository;

import com.tech3.erp.entity.BaleRollPacking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaleRollPackingRepository extends JpaRepository<BaleRollPacking, Long> {
        boolean existsBySalesOrderId(Long salesOrderId);
        boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);

    java.util.List<BaleRollPacking> findAllByCompanyId(Long companyId);
    java.util.Optional<BaleRollPacking> findByIdAndCompanyId(Long id, Long companyId);
    @Query(value = "SELECT * FROM masters.bale_roll_packing WHERE roll_or_bale_no = :rollOrBaleNo ORDER BY id DESC LIMIT 1", nativeQuery = true)
    java.util.Optional<BaleRollPacking> findByRollOrBaleNo(@Param("rollOrBaleNo") String rollOrBaleNo);

    java.util.Optional<BaleRollPacking> findByPackingNo(String packingNo);

    @Query("SELECT b FROM BaleRollPacking b WHERE b.rollOrBaleNo IN :rollNos")
    java.util.List<BaleRollPacking> findByRollOrBaleNoIn(@Param("rollNos") java.util.List<String> rollNos);

    boolean existsByIdAndCompanyId(Long id, Long companyId);
    void deleteByIdAndCompanyId(Long id, Long companyId);

    @Modifying
    @Transactional
    @Query("UPDATE BaleRollPacking b SET b.isDispatched = 1 WHERE b.rollOrBaleNo IN :rollNos OR b.id IN (SELECT bi.baleRollPacking.id FROM BaleRollPackingItem bi WHERE bi.pieceNo IN :pieceNos)")
    void updateIsDispatched(@Param("rollNos") List<String> rollNos, @Param("pieceNos") List<String> pieceNos);

    @Modifying
    @Transactional
    @Query("UPDATE BaleRollPacking b SET b.isDispatched = 1 WHERE (b.rollOrBaleNo IN :rollNos OR b.id IN (SELECT bi.baleRollPacking.id FROM BaleRollPackingItem bi WHERE bi.pieceNo IN :pieceNos)) AND b.salesOrderId = :salesOrderId")
    void updateIsDispatchedForSalesOrder(@Param("rollNos") List<String> rollNos, @Param("pieceNos") List<String> pieceNos, @Param("salesOrderId") Long salesOrderId);

    @Modifying
    @Transactional
    @Query("UPDATE BaleRollPacking b SET b.isDispatched = 0 WHERE b.rollOrBaleNo IN :rollNos OR b.id IN (SELECT bi.baleRollPacking.id FROM BaleRollPackingItem bi WHERE bi.pieceNo IN :pieceNos)")
    void resetIsDispatched(@Param("rollNos") List<String> rollNos, @Param("pieceNos") List<String> pieceNos);

    @Modifying
    @Transactional
    @Query("UPDATE BaleRollPacking b SET b.isDispatched = 0 WHERE (b.rollOrBaleNo IN :rollNos OR b.id IN (SELECT bi.baleRollPacking.id FROM BaleRollPackingItem bi WHERE bi.pieceNo IN :pieceNos)) AND b.salesOrderId = :salesOrderId")
    void resetIsDispatchedForSalesOrder(@Param("rollNos") List<String> rollNos, @Param("pieceNos") List<String> pieceNos, @Param("salesOrderId") Long salesOrderId);

    @Modifying
    @Transactional
    @Query("UPDATE BaleRollPacking b SET b.isRollScanned = 1 WHERE b.rollOrBaleNo IN :rollNos")
    void markRollScannedByRollNumbers(@Param("rollNos") List<String> rollNos);

    @Modifying
    @Transactional
    @Query("UPDATE BaleRollPacking b SET b.isRollScanned = 0 WHERE b.rollOrBaleNo IN :rollNos")
    void unmarkRollScannedByRollNumbers(@Param("rollNos") List<String> rollNos);
}