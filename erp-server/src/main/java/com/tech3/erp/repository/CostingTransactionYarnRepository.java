package com.tech3.erp.repository;

import com.tech3.erp.entity.CostingTransactionYarn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostingTransactionYarnRepository extends JpaRepository<CostingTransactionYarn, Long> {

    List<CostingTransactionYarn> findByCostingTransaction_IdOrderById(Long costingTransactionId);

    List<CostingTransactionYarn> findByCostingTransaction_IdAndActiveFlagOrderById(Long costingTransactionId, boolean activeFlag);

    @Modifying
    @Query("DELETE FROM CostingTransactionYarn cty WHERE cty.costingTransaction.id = :costingTransactionId")
    void deleteByCostingTransactionId(@Param("costingTransactionId") Long costingTransactionId);

//    @Query("SELECT SUM(cty.yarnProcessingCost) FROM CostingTransactionYarn cty WHERE cty.costingTransaction.id = :costingTransactionId AND cty.activeFlag = true")
//    java.math.BigDecimal sumAmountByCostingTransactionId(@Param("costingTransactionId") Long costingTransactionId);
//
//    @Query("SELECT SUM(cty.yarnCount) FROM CostingTransactionYarn cty WHERE cty.costingTransaction.id = :costingTransactionId AND cty.activeFlag = true")
//    java.math.BigDecimal sumWeightByCostingTransactionId(@Param("costingTransactionId") Long costingTransactionId);
}