package com.tech3.erp.repository;

import com.tech3.erp.entity.FabricDispatchYarnIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricDispatchYarnIssueRepository extends JpaRepository<FabricDispatchYarnIssue, Long> {
    
    @Modifying
    @Query("DELETE FROM FabricDispatchYarnIssue y WHERE y.fabricDispatch.id = :fabricDispatchId")
    void deleteByFabricDispatchId(@Param("fabricDispatchId") Long fabricDispatchId);

    @Query("SELECT COALESCE(SUM(y.issueQty), 0) FROM FabricDispatchYarnIssue y WHERE y.yarnInwardStockDetailsId = :stockDetailsId")
    Double sumIssueQtyByStockDetailsId(@Param("stockDetailsId") Long stockDetailsId);

    @Query("SELECT COALESCE(SUM(y.noOfBags), 0) FROM FabricDispatchYarnIssue y WHERE y.yarnInwardStockDetailsId = :stockDetailsId")
    Integer sumNoOfBagsByStockDetailsId(@Param("stockDetailsId") Long stockDetailsId);

    @Query("SELECT COALESCE(SUM(y.issueQty), 0) FROM FabricDispatchYarnIssue y "
         + "WHERE (:stockDetailsId IS NOT NULL AND y.yarnInwardStockDetailsId = :stockDetailsId) "
         + "OR (:itemId IS NOT NULL AND y.greigeYarnId = :itemId)")
    Double sumIssueQtyByStockDetailsOrItem(@Param("stockDetailsId") Long stockDetailsId,
                                           @Param("itemId") Long itemId);

    @Query("SELECT COALESCE(SUM(y.noOfBags), 0) FROM FabricDispatchYarnIssue y "
         + "WHERE (:stockDetailsId IS NOT NULL AND y.yarnInwardStockDetailsId = :stockDetailsId) "
         + "OR (:itemId IS NOT NULL AND y.greigeYarnId = :itemId)")
    Integer sumNoOfBagsByStockDetailsOrItem(@Param("stockDetailsId") Long stockDetailsId,
                                             @Param("itemId") Long itemId);
}
