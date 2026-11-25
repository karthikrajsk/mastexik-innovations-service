package com.tech3.erp.repository;

import com.tech3.erp.entity.PieceEntry;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceEntryRepository extends JpaRepository<PieceEntry, Long> {
	
	@Query("SELECT COALESCE(MAX(p.pieceNo), 0) FROM PieceEntry p WHERE p.inspectionId = :inspectionId")
	Long findMaxPieceNoByInspectionId(@Param("inspectionId") Long inspectionId);

	@Query("SELECT p FROM PieceEntry p WHERE p.pieceNumber IN :pieceNumbers")
	List<PieceEntry> findByPieceNumberIn(@Param("pieceNumbers") List<String> pieceNumbers);

	@Query("SELECT p FROM PieceEntry p WHERE p.packingId IN :packingIds")
	List<PieceEntry> findByPackingIdIn(@Param("packingIds") List<Long> packingIds);
	
    @Modifying
    @Transactional
    @Query("UPDATE PieceEntry p SET p.isPacked = 1, p.packingId = COALESCE(p.packingId, :dispatchId), p.isDispatched = 1, p.dispatchId = :dispatchId WHERE p.pieceNumber IN :pieceNumbers")
    void markDispatchedByPieceNumbers(@Param("pieceNumbers") List<String> pieceNumbers, @Param("dispatchId") Long dispatchId);

    @Modifying
    @Transactional
    @Query("UPDATE PieceEntry p SET p.isDispatched = 1, p.dispatchId = :dispatchId WHERE p.packingId IN :packingIds")
    void markDispatchedByPackingIds(@Param("packingIds") List<Long> packingIds, @Param("dispatchId") Long dispatchId);

    @Modifying
    @Transactional
    @Query("UPDATE PieceEntry p SET p.isInvoice = 1, p.invoiceId = :invoiceId WHERE p.pieceNumber IN :pieceNumbers")
    void markInvoicedByPieceNumbers(@Param("pieceNumbers") List<String> pieceNumbers, @Param("invoiceId") Long invoiceId);

    @Modifying
    @Transactional
    @Query("UPDATE PieceEntry p SET p.isInvoice = 1, p.invoiceId = :invoiceId WHERE p.packingId IN :packingIds")
    void markInvoicedByPackingIds(@Param("packingIds") List<Long> packingIds, @Param("invoiceId") Long invoiceId);

    @Modifying
    @Query("UPDATE PieceEntry p SET p.isPacked = 0, p.packingId = NULL WHERE p.packingId = :packingId")
    void resetPackingStatus(@Param("packingId") Long packingId);
    
    // NEW: Reset dispatch status when packing is deleted
    @Modifying
    @Query("UPDATE PieceEntry p SET p.isDispatched = 0, p.dispatchId = NULL WHERE p.dispatchId = :dispatchId")
    void resetDispatchStatus(@Param("dispatchId") Long dispatchId);
    
    // NEW: Reset invoice status when invoice is deleted
    @Modifying
    @Query("UPDATE PieceEntry p SET p.isInvoice = 0, p.invoiceId = NULL WHERE p.invoiceId = :invoiceId")
    void resetInvoiceStatus(@Param("invoiceId") Long invoiceId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PieceEntry p WHERE p.finishFabricReceiveItem.id IN :itemIds")
    void deleteByFinishFabricReceiveItemIdIn(@Param("itemIds") List<Long> itemIds);

    @Query("SELECT p FROM PieceEntry p WHERE p.finishFabricReceiveItem.id IN :itemIds")
    List<PieceEntry> findByFinishFabricReceiveItemIdIn(@Param("itemIds") List<Long> itemIds);
    
    List<PieceEntry> findByInspectionId(Long inspectionId);
    
    List<PieceEntry> findByCompanyId(Long companyId);
    
    List<PieceEntry> findByCompanyIdAndIsFactoryFabric(Long companyId, Integer isFactoryFabric);

    Page<PieceEntry> findByCompanyIdAndIsFactoryFabricOrderByIdDesc(Long companyId, Integer isFactoryFabric, Pageable pageable);

    Page<PieceEntry> findByIsFactoryFabricOrderByIdDesc(Integer isFactoryFabric, Pageable pageable);

    @Query("SELECT p FROM PieceEntry p WHERE p.isFactoryFabric = 1 " +
           "AND (:companyId IS NULL OR p.companyId = :companyId) " +
           "AND (:searchPattern IS NULL OR " +
           "LOWER(COALESCE(p.pieceNumber, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.doffingNo, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.beamNo, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.flangeNo, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.doffingDate, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.pieceMeter, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.doffingPicks, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.warpSetNo, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.warpCount, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.warpYarnMillName, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.warpYarnLotNo, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.weftCount, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.weftYarnMillName, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.weftYarnLotNo, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(p.orderNo, '')) LIKE :searchPattern OR " +
           "p.warehouse.id IN (SELECT w.id FROM Warehouse w WHERE LOWER(COALESCE(w.warehouseName, '')) LIKE :searchPattern) OR " +
           "LOWER(str(p.loomId)) LIKE :searchPattern OR " +
           "p.loomId IN (SELECT l.id FROM LoomMaster l WHERE LOWER(COALESCE(l.loomNo, '')) LIKE :searchPattern) OR " +
           "p.fabricMasterId IN (SELECT f.id FROM WovenFabricMaster f WHERE " +
           "LOWER(COALESCE(f.fabricCode, '')) LIKE :searchPattern OR " +
           "LOWER(COALESCE(f.fabricQuality, '')) LIKE :searchPattern OR " +
           "LOWER(CONCAT(CONCAT(COALESCE(f.fabricCode, ''), ' - '), COALESCE(f.fabricQuality, ''))) LIKE :searchPattern)) " +
           "ORDER BY p.id DESC")
    Page<PieceEntry> searchDoffingEntries(@Param("companyId") Long companyId,
                                          @Param("searchPattern") String searchPattern,
                                          Pageable pageable);

    @Query("SELECT new com.tech3.erp.dto.DoffingEntryHistoryDTO(p.id, p.pieceNumber, p.pieceNo, p.loomId, p.beamId, p.beamNo, p.pieceMeter, p.remainingMtr, p.isFactoryFabric, p.isLastPiece) " +
           "FROM PieceEntry p WHERE p.companyId = :companyId AND p.isFactoryFabric = 1 ORDER BY p.id DESC")
    List<com.tech3.erp.dto.DoffingEntryHistoryDTO> findDoffingHistoryByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT new com.tech3.erp.dto.DoffingEntryHistoryDTO(p.id, p.pieceNumber, p.pieceNo, p.loomId, p.beamId, p.beamNo, p.pieceMeter, p.remainingMtr, p.isFactoryFabric, p.isLastPiece) " +
           "FROM PieceEntry p WHERE p.isFactoryFabric = 1 ORDER BY p.id DESC")
    List<com.tech3.erp.dto.DoffingEntryHistoryDTO> findDoffingHistory();
    
    List<PieceEntry> findByCompanyIdAndSourceType(Long companyId, String sourceType);
    
    List<PieceEntry> findByCompanyIdAndIsFactoryFabricAndSourceType(Long companyId, Integer isFactoryFabric, String sourceType);

    @Query("SELECT DISTINCT p FROM PieceEntry p WHERE p.companyId = :companyId " +
           "AND (:isFactoryFabric IS NULL OR p.isFactoryFabric = :isFactoryFabric) " +
           "AND (:sourceType IS NULL OR p.sourceType = :sourceType) " +
           "AND p.orderNo IN (SELECT w.weavingWorkOrderNo FROM WeavingWorkOrder w WHERE w.weavingContractId = :weavingContractId)")
    List<PieceEntry> findByCompanyIdAndIsFactoryFabricAndSourceTypeAndWeavingContractIdViaWorkOrder(
            @Param("companyId") Long companyId,
            @Param("isFactoryFabric") Integer isFactoryFabric,
            @Param("sourceType") String sourceType,
            @Param("weavingContractId") Long weavingContractId);
    
    Optional<PieceEntry> findByIdAndCompanyId(Long id, Long companyId);

    Optional<PieceEntry> findTopByPieceNumberOrderByIdDesc(String pieceNumber);
    
    void deleteByIdAndCompanyId(Long id, Long companyId);
    
    @Modifying
    @Transactional
    int deleteByOpeningBalanceId(Long openingBalanceId);
    
    Optional<PieceEntry> findByPieceNumberAndOpeningBalanceId(String pieceNumber, Long openingBalanceId);
    List<PieceEntry> findByOpeningBalanceId(Long openingBalanceId);
    List<PieceEntry> findByInwardId(Long inwardId);

    //	List<PieceEntry> findByPieceNumberIn(List<String> pieceNumbers);
 List<PieceEntry> findByPieceNumber(String pieceNumber);
    
    List<PieceEntry> findByPieceNumberAndCompanyId(String pieceNumber, Long companyId);
    
    List<PieceEntry> findByPieceNumberIgnoreCaseAndCompanyId(String pieceNumber, Long companyId);
    
    List<PieceEntry> findByPieceNumberIgnoreCase(String pieceNumber);
    
    List<PieceEntry> findByPieceNoAndCompanyId(Integer pieceNo, Long companyId);

    boolean existsByParentPieceId(Long parentPieceId);

    boolean existsByJoinedPieceId(Long joinedPieceId);
    
    List<PieceEntry> findByStockTransferId(Long stockTransferId);
    
    List<PieceEntry> findByJobworkFabricReceiveItem(com.tech3.erp.entity.JobworkFabricReceiveItem jobworkFabricReceiveItem);

    @Query("SELECT COUNT(pe) FROM PieceEntry pe WHERE pe.purchaseInwardItem.id = :purchaseInwardItemId")
    Long countByPurchaseInwardItemId(@Param("purchaseInwardItemId") Long purchaseInwardItemId);
	

    @Query("SELECT pe FROM PieceEntry pe WHERE pe.purchaseInwardItem.id = :purchaseInwardItemId " +
           "AND (pe.isInspected = 1 OR pe.isPacked = 1 OR pe.isDispatched = 1 OR pe.invoiceId IS NOT NULL)")
    List<PieceEntry> findUsedPiecesByPurchaseInwardItemId(@Param("purchaseInwardItemId") Long purchaseInwardItemId);

    List<PieceEntry> findByPurchaseInwardItemId(Long purchaseInwardItemId);

    @Query("SELECT pe FROM PieceEntry pe WHERE pe.finishFabricReceiveItem.id = :itemId " +
           "AND (pe.isInspected = 1 OR pe.isPacked = 1 OR pe.isDispatched = 1 OR pe.invoiceId IS NOT NULL)")
    List<PieceEntry> findUsedPiecesByFinishFabricReceiveItemId(@Param("itemId") Long itemId);

    @Query("SELECT pe FROM PieceEntry pe WHERE pe.openingBalanceId = :openingBalanceId " +
           "AND (pe.isInspected = 1 OR pe.isPacked = 1 OR pe.isDispatched = 1 OR pe.invoiceId IS NOT NULL)")
    List<PieceEntry> findUsedPiecesByOpeningBalanceId(@Param("openingBalanceId") Long openingBalanceId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PieceEntry pe WHERE pe.finishFabricReceiveItem.id = :itemId")
    void deleteByFinishFabricReceiveItemId(@Param("itemId") Long itemId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PieceEntry pe WHERE pe.purchaseInwardItem.id = :purchaseInwardItemId")
    void deleteByPurchaseInwardItemId(@Param("purchaseInwardItemId") Long purchaseInwardItemId);
    
    
}