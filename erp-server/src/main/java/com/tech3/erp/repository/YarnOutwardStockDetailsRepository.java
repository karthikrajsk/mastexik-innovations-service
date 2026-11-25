package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnOutwardStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.math.BigDecimal;

@Repository
public interface YarnOutwardStockDetailsRepository extends JpaRepository<YarnOutwardStockDetails, Long> {
    
    @Modifying
    @Query(value = "UPDATE masters.yarn_outward_stock_details SET order_item_id = :orderItemId WHERE id = :id", nativeQuery = true)
    void updateOrderItemId(@Param("id") Long id, @Param("orderItemId") Long orderItemId);
    
    @Modifying
    @Query(value = "UPDATE masters.yarn_outward_stock_details SET inward_id = :inwardId WHERE id = :id", nativeQuery = true)
    void updateInwardId(@Param("id") Long id, @Param("inwardId") Long inwardId);
    
    // Find by item ID and lot number
    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.actualItemId = :itemId AND y.lotNo = :lotNo")
    List<YarnOutwardStockDetails> findByItemIdAndLotNo(@Param("itemId") Long itemId, @Param("lotNo") String lotNo);

    // Find by outward ID
    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.yarnOutward.id = :outwardId")
    List<YarnOutwardStockDetails> findByOutwardId(@Param("outwardId") Long outwardId);
    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.actualItemId = :actualItemId AND y.lotNo = :lotNo")
    List<YarnOutwardStockDetails> findByActualItemIdAndLotNo(@Param("actualItemId") Long actualItemId, 
                                                           @Param("lotNo") String lotNo);

    @Query("SELECT COALESCE(SUM(y.issuedQty), 0) FROM YarnOutwardStockDetails y WHERE y.yarnInwardStockDetailsId = :yarnInwardStockDetailsId")
    BigDecimal getTotalIssuedQtyByYarnInwardStockDetailsId(@Param("yarnInwardStockDetailsId") Long yarnInwardStockDetailsId);
    
    @Query("SELECT COALESCE(SUM(y.issuedBags), 0) FROM YarnOutwardStockDetails y WHERE y.yarnInwardStockDetailsId = :yarnInwardStockDetailsId")
    BigDecimal getTotalIssuedBagsByYarnInwardStockDetailsId(@Param("yarnInwardStockDetailsId") Long yarnInwardStockDetailsId);

    @Query("SELECT COALESCE(SUM(y.noOfCones), 0) FROM YarnOutwardStockDetails y WHERE y.yarnInwardStockDetailsId = :yarnInwardStockDetailsId")
    BigDecimal getTotalIssuedConesByYarnInwardStockDetailsId(@Param("yarnInwardStockDetailsId") Long yarnInwardStockDetailsId);

    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.yarnInwardStockDetailsId = :yarnInwardStockDetailsId")
    List<YarnOutwardStockDetails> findByYarnInwardStockDetailsId(@Param("yarnInwardStockDetailsId") Long yarnInwardStockDetailsId);

    @Query("SELECT COALESCE(SUM(y.issuedQty), 0) FROM YarnOutwardStockDetails y WHERE y.orderItemId = :orderItemId")
    BigDecimal getTotalIssuedQtyByOrderItemId(@Param("orderItemId") Long orderItemId);
    
    @Query("SELECT COALESCE(SUM(y.issuedBags), 0) FROM YarnOutwardStockDetails y WHERE y.orderItemId = :orderItemId")
    BigDecimal getTotalIssuedBagsByOrderItemId(@Param("orderItemId") Long orderItemId);
    
    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.actualItemId = :itemId AND y.outwardTypeId = :outwardTypeId")
    List<YarnOutwardStockDetails> findByItemIdAndOutwardTypeId(@Param("itemId") Long itemId, @Param("outwardTypeId") Long outwardTypeId);
    
    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.orderItemId = :orderItemId")
    List<YarnOutwardStockDetails> findByOrderItemId(@Param("orderItemId") Long orderItemId);
    
    @Query("SELECT y FROM YarnOutwardStockDetails y WHERE y.actualItemId = :actualItemId")
    List<YarnOutwardStockDetails> findByActualItemId(@Param("actualItemId") Long actualItemId);

    @Query("SELECT COALESCE(SUM(y.issuedQty), 0) FROM YarnOutwardStockDetails y JOIN y.yarnOutward yo WHERE yo.orderId = :contractId AND y.actualItemId = :itemId AND yo.vendorId = :vendorId AND yo.outwardTypeId = :outwardTypeId")
   BigDecimal getTotalIssuedQtyByContractItemVendorAndType(@Param("contractId") Long contractId, @Param("itemId") Long itemId, @Param("vendorId") Long vendorId, @Param("outwardTypeId") Long outwardTypeId);

    // Check if any yarn outward stock details reference the given yarn inward stock detail
    @Query("SELECT CASE WHEN COUNT(y) > 0 THEN true ELSE false END FROM YarnOutwardStockDetails y WHERE y.yarnInwardStockDetailsId = :yarnInwardStockDetailsId")
    boolean existsByYarnInwardStockDetailsId(@Param("yarnInwardStockDetailsId") Long yarnInwardStockDetailsId);
   
}