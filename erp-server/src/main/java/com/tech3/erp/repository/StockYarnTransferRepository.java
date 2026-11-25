package com.tech3.erp.repository;

import com.tech3.erp.entity.StockYarnTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockYarnTransferRepository extends JpaRepository<StockYarnTransferEntity, Long> {

    List<StockYarnTransferEntity> findByStockTransferId(Long stockTransferId);

    @Query("SELECT syt FROM StockYarnTransferEntity syt " +
           "WHERE syt.stockTransfer.id = :stockTransferId")
    List<StockYarnTransferEntity> findByStockTransferIdWithDetails(@Param("stockTransferId") Long stockTransferId);
}
