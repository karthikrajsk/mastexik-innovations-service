package com.tech3.erp.repository;

import com.tech3.erp.entity.ItemInwardStockDetails;
import com.tech3.erp.entity.ItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemInwardStockDetailsRepository extends JpaRepository<ItemInwardStockDetails, Long> {
    
    @Query("SELECT iisd FROM ItemInwardStockDetails iisd " +
           "WHERE iisd.itemInventory = :itemInventory " +
           "AND iisd.itemId = :itemId " +
           "AND iisd.lotNo = :lotNo")
    List<ItemInwardStockDetails> findByItemInventoryAndItemIdAndLotNo(
        @Param("itemInventory") ItemInventory itemInventory,
        @Param("itemId") Long itemId,
        @Param("lotNo") String lotNo);

    List<ItemInwardStockDetails> findByItemIdAndLotNo(Long itemId, String lotNo);
    
    @Query("SELECT iisd FROM ItemInwardStockDetails iisd WHERE iisd.itemId = :itemId")
    List<ItemInwardStockDetails> findByItemId(@Param("itemId") Long itemId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd WHERE iisd.itemId = :itemId AND iisd.orderItemId = :orderItemId")
    List<ItemInwardStockDetails> findByItemIdAndOrderItemId(@Param("itemId") Long itemId, @Param("orderItemId") Long orderItemId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd LEFT JOIN iisd.itemInventory ii WHERE ii.orderId = :purchaseOrderId AND iisd.orderItemId = :poTypeId")
    List<ItemInwardStockDetails> findByPurchaseOrderIdAndPoTypeId(@Param("purchaseOrderId") Long purchaseOrderId, @Param("poTypeId") Long poTypeId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd LEFT JOIN iisd.itemInventory ii WHERE ii.orderId = :purchaseOrderId")
    List<ItemInwardStockDetails> findByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    @Query("SELECT DISTINCT iisd FROM ItemInwardStockDetails iisd LEFT JOIN FETCH iisd.itemInventory")
    List<ItemInwardStockDetails> findAllWithItemInventory();

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd LEFT JOIN FETCH iisd.itemInventory WHERE iisd.id = :id")
    Optional<ItemInwardStockDetails> findByIdWithItemInventory(@Param("id") Long id);

    Optional<ItemInwardStockDetails> findByOrderItemId(Long orderItemId);

    Optional<ItemInwardStockDetails> findFirstByOrderItemId(Long orderItemId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd INNER JOIN iisd.itemInventory ii WHERE ii.orderNo = :orderNo AND iisd.orderItemId = :orderItemId")
    List<ItemInwardStockDetails> findByOrderNoAndOrderItemId(@Param("orderNo") String orderNo, @Param("orderItemId") Long orderItemId);

    @Query("SELECT COALESCE(SUM(iisd.availableQty), 0) FROM ItemInwardStockDetails iisd WHERE iisd.itemId = :itemId")
    java.math.BigDecimal sumAvailableQtyByItemId(@Param("itemId") Long itemId);
    
    @Query("SELECT COUNT(iisd) FROM ItemInwardStockDetails iisd LEFT JOIN iisd.itemInventory ii WHERE ii.orderId = :purchaseOrderId")
    Long countByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
 
    List<ItemInwardStockDetails> findByItemInventoryId(Long itemInventoryId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd WHERE iisd.itemInventory.id = :itemInventoryId AND iisd.itemId = :itemId AND iisd.warehouseId = :warehouseId")
    List<ItemInwardStockDetails> findByItemInventoryIdAndItemIdAndWarehouseId(@Param("itemInventoryId") Long itemInventoryId, @Param("itemId") Long itemId, @Param("warehouseId") Long warehouseId);
    
    @Query("SELECT iisd FROM ItemInwardStockDetails iisd " +
           "LEFT JOIN FETCH iisd.itemInventory ii " +
           "WHERE (:vendorId IS NULL OR ii.vendorId = :vendorId) " +
           "AND (:warehouseId IS NULL OR iisd.warehouseId = :warehouseId) " +
           "AND (:companyId IS NULL OR ii.companyId = :companyId)")
    List<ItemInwardStockDetails> findByFilters(@Param("vendorId") Long vendorId, @Param("warehouseId") Long warehouseId, @Param("companyId") Long companyId);

    @Query("SELECT COUNT(iisd) FROM ItemInwardStockDetails iisd " +
           "INNER JOIN iisd.itemInventory ii " +
           "WHERE ii.inwardNo = :inwardNo " +
           "AND ii.orderId = :orderId " +
           "AND ii.companyId = :companyId " +
           "AND iisd.lotNo = :lotNo " +
           "AND iisd.itemId = :itemId " +
           "AND iisd.id != :currentId")
    Long countDuplicateLotNoInInward(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("companyId") Long companyId,
        @Param("lotNo") String lotNo,
        @Param("itemId") Long itemId,
        @Param("currentId") Long currentId);
    
    @Query("SELECT iisd FROM ItemInwardStockDetails iisd " +
           "INNER JOIN iisd.itemInventory ii " +
           "WHERE ii.inwardNo = :inwardNo " +
           "AND ii.orderId = :orderId " +
           "AND ii.companyId = :companyId " +
           "AND iisd.itemId = :itemId " +
           "AND iisd.lotNo = :lotNo")
    List<ItemInwardStockDetails> findExistingLotNoRecords(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("companyId") Long companyId,
        @Param("lotNo") String lotNo,
        @Param("itemId") Long itemId);

    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM ItemInwardStockDetails iisd WHERE iisd.itemInventory.id = :itemInventoryId")
    void deleteByItemInventoryId(@Param("itemInventoryId") Long itemInventoryId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd " +
           "INNER JOIN iisd.itemInventory ii " +
           "WHERE ii.inwardNo = :inwardNo " +
           "AND ii.orderId = :orderId")
    List<ItemInwardStockDetails> findByInwardNoAndOrderId(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd " +
           "INNER JOIN iisd.itemInventory ii " +
           "WHERE ii.inwardNo = :inwardNo " +
           "AND ii.orderId = :orderId " +
           "AND iisd.itemId = :itemId " +
           "AND iisd.orderItemId = :poTypeId")
    List<ItemInwardStockDetails> findByInwardNoAndOrderIdAndItemId(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("itemId") Long itemId,
        @Param("poTypeId") Long poTypeId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd JOIN iisd.itemInventory ii " +
           "WHERE ii.companyId = :companyId AND iisd.itemId = :itemId AND iisd.lotNo = :lotNo")
    List<ItemInwardStockDetails> findByCompanyIdAndItemIdAndLotNo(
        @Param("companyId") Long companyId,
        @Param("itemId") Long itemId,
        @Param("lotNo") String lotNo);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd JOIN iisd.itemInventory ii " +
           "WHERE ii.companyId = :companyId AND iisd.itemId = :itemId AND LOWER(iisd.lotNo) = LOWER(:lotNo)")
    List<ItemInwardStockDetails> findByCompanyIdAndItemIdAndLotNoIgnoreCase(
        @Param("companyId") Long companyId,
        @Param("itemId") Long itemId,
        @Param("lotNo") String lotNo);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd JOIN iisd.itemInventory ii " +
           "WHERE ii.companyId = :companyId AND LOWER(iisd.lotNo) = LOWER(:lotNo)")
    List<ItemInwardStockDetails> findByCompanyIdAndLotNoIgnoreCase(
        @Param("companyId") Long companyId,
        @Param("lotNo") String lotNo);

    List<ItemInwardStockDetails> findByLotNoIgnoreCase(String lotNo);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd WHERE iisd.itemId = :itemId AND iisd.warehouseId = :warehouseId")
    List<ItemInwardStockDetails> findByItemIdAndWarehouseId(@Param("itemId") Long itemId, @Param("warehouseId") Long warehouseId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd WHERE iisd.itemId = :itemId AND iisd.warehouseId = :warehouseId AND iisd.lotNo = :lotNo")
    List<ItemInwardStockDetails> findByItemIdAndWarehouseIdAndLotNo(@Param("itemId") Long itemId, @Param("warehouseId") Long warehouseId, @Param("lotNo") String lotNo);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd WHERE iisd.orderItemId = :orderItemId")
    List<ItemInwardStockDetails> findAllByOrderItemId(@Param("orderItemId") Long orderItemId);

    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM ItemInwardStockDetails i WHERE i.orderItemId = :orderItemId")
    void deleteByOrderItemId(@Param("orderItemId") Long orderItemId);

    @Query("SELECT iisd FROM ItemInwardStockDetails iisd JOIN iisd.itemInventory ii WHERE ii.inwardNo = :inwardNo AND iisd.itemId = :itemId")
    List<ItemInwardStockDetails> findByInwardNoAndItemId(@Param("inwardNo") String inwardNo, @Param("itemId") Long itemId);
}

