package com.tech3.erp.repository;

import com.tech3.erp.entity.ItemOutwardStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemOutwardStockDetailsRepository extends JpaRepository<ItemOutwardStockDetails, Long> {
    
    @Query("SELECT COALESCE(SUM(iosd.issuingQuantity), 0) FROM ItemOutwardStockDetails iosd WHERE iosd.itemInwardStockDetailsId = :itemInwardStockDetailsId")
    BigDecimal getTotalIssuedQuantityByItemInwardStockDetailsId(@Param("itemInwardStockDetailsId") Long itemInwardStockDetailsId);
    
    @Query("SELECT COALESCE(SUM(iosd.issuingBags), 0) FROM ItemOutwardStockDetails iosd WHERE iosd.itemInwardStockDetailsId = :itemInwardStockDetailsId")
    BigDecimal getTotalIssuedBagsByItemInwardStockDetailsId(@Param("itemInwardStockDetailsId") Long itemInwardStockDetailsId);
    
    @Query("SELECT iosd FROM ItemOutwardStockDetails iosd WHERE iosd.itemInwardStockDetailsId = :itemInwardStockDetailsId")
    List<ItemOutwardStockDetails> findByItemInwardStockDetailsId(@Param("itemInwardStockDetailsId") Long itemInwardStockDetailsId);
}

