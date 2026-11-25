package com.tech3.erp.repository;

import com.tech3.erp.entity.CostingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostingTransactionRepository extends JpaRepository<CostingTransaction, Long> {

    List<CostingTransaction> findByActiveFlagOrderByIdDesc(boolean activeFlag);

    List<CostingTransaction> findByCompanyIdAndActiveFlagOrderByIdDesc(Long companyId, boolean activeFlag);

    List<CostingTransaction> findByCompanyIdOrderByIdDesc(Long companyId);

    List<CostingTransaction> findAllByOrderByIdDesc();

    Optional<CostingTransaction> findByIdAndCompanyId(Long id, Long companyId);

    Optional<CostingTransaction> findByCostingNo(String costingNo);

    @Query("SELECT MAX(CAST(SUBSTRING(ct.costingNo, 5) AS int)) FROM CostingTransaction ct WHERE ct.costingNo LIKE 'COST%'")
    Optional<Integer> findMaxCostingNumber();
}