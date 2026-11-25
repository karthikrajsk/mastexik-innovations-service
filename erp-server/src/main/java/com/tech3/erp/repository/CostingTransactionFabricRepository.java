package com.tech3.erp.repository;

import com.tech3.erp.entity.CostingTransactionFabric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostingTransactionFabricRepository extends JpaRepository<CostingTransactionFabric, Long> {

    List<CostingTransactionFabric> findByCostingTransaction_IdOrderById(Long costingTransactionId);

    List<CostingTransactionFabric> findByCostingTransaction_IdAndActiveFlagOrderById(Long costingTransactionId, boolean activeFlag);

    @Modifying
    @Query("DELETE FROM CostingTransactionFabric ctf WHERE ctf.costingTransaction.id = :costingTransactionId")
    void deleteByCostingTransactionId(@Param("costingTransactionId") Long costingTransactionId);

    @Query("SELECT SUM(ctf.amount) FROM CostingTransactionFabric ctf WHERE ctf.costingTransaction.id = :costingTransactionId")
    java.math.BigDecimal sumAmountByCostingTransactionId(@Param("costingTransactionId") Long costingTransactionId);

    @Query("SELECT SUM(ctf.weight) FROM CostingTransactionFabric ctf WHERE ctf.costingTransaction.id = :costingTransactionId")
    java.math.BigDecimal sumWeightByCostingTransactionId(@Param("costingTransactionId") Long costingTransactionId);
}