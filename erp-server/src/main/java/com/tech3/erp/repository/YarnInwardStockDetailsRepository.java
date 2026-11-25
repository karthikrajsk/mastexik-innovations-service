// YarnInwardStockDetailsRepository
package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YarnInwardStockDetailsRepository extends JpaRepository<YarnInwardStockDetails, Long> {
    
    @Query("SELECT yisd FROM YarnInwardStockDetails yisd " +
           "WHERE yisd.yarnInventory = :yarnInventory " +
           "AND yisd.itemId = :itemId " +
           "AND yisd.lotNo = :lotNo")
    List<YarnInwardStockDetails> findByYarnInventoryAndItemIdAndLotNo(
        @Param("yarnInventory") YarnInventory yarnInventory,
        @Param("itemId") Long itemId,
        @Param("lotNo") String lotNo);

    List<YarnInwardStockDetails> findByItemIdAndLotNo(Long itemId, String lotNo);
    
    @Query("SELECT yisd FROM YarnInwardStockDetails yisd WHERE yisd.itemId = :itemId")
    List<YarnInwardStockDetails> findByItemId(@Param("itemId") Long itemId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd WHERE yisd.itemId = :itemId AND yisd.inwardTypeId = :inwardTypeId")
    List<YarnInwardStockDetails> findByItemIdAndInwardTypeId(@Param("itemId") Long itemId, @Param("inwardTypeId") Long inwardTypeId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd LEFT JOIN yisd.yarnInventory yi WHERE yi.orderId = :purchaseOrderId AND yi.inwardTypeId = :poTypeId")
    List<YarnInwardStockDetails> findByPurchaseOrderIdAndPoTypeId(@Param("purchaseOrderId") Long purchaseOrderId, @Param("poTypeId") Long poTypeId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd LEFT JOIN yisd.yarnInventory yi WHERE yi.orderId = :purchaseOrderId")
    List<YarnInwardStockDetails> findByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    @Query("SELECT DISTINCT yisd FROM YarnInwardStockDetails yisd LEFT JOIN FETCH yisd.yarnInventory")
    List<YarnInwardStockDetails> findAllWithYarnInventory();

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd LEFT JOIN FETCH yisd.yarnInventory WHERE yisd.id = :id")
    Optional<YarnInwardStockDetails> findByIdWithYarnInventory(@Param("id") Long id);

    Optional<YarnInwardStockDetails> findByOrderItemId(Long orderItemId);
    
    Optional<YarnInwardStockDetails> findFirstByOrderItemId(Long orderItemId);

    @Query(value = "SELECT nextval('masters.yarn_inward_stock_details_order_item_id_seq')", nativeQuery = true)
    Long getNextOrderItemIdValue();

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd INNER JOIN yisd.yarnInventory yi WHERE yi.orderNo = :orderNo AND yisd.inwardTypeId = :inwardTypeId")
    List<YarnInwardStockDetails> findByOrderNoAndInwardTypeId(@Param("orderNo") String orderNo, @Param("inwardTypeId") Long inwardTypeId);

    @Query("SELECT COALESCE(SUM(yisd.availableQty), 0) FROM YarnInwardStockDetails yisd WHERE yisd.itemId = :itemId")
    java.math.BigDecimal sumAvailableQtyByItemId(@Param("itemId") Long itemId);

    @Query("SELECT COALESCE(SUM(yisd.availableQty), 0) FROM YarnInwardStockDetails yisd " +
           "JOIN yisd.yarnInventory yi " +
           "WHERE yisd.itemId = :itemId AND (yi.companyId = :companyId OR :companyId IS NULL)")
    java.math.BigDecimal sumAvailableQtyByItemIdAndCompanyId(@Param("itemId") Long itemId, @Param("companyId") Long companyId);
    
    @Query("SELECT yisd.itemId, COALESCE(SUM(yisd.availableQty), 0) FROM YarnInwardStockDetails yisd " +
           "JOIN yisd.yarnInventory yi " +
           "WHERE yi.orderId = :orderId " +
           "AND yisd.itemId IN :itemIds " +
           "AND (:inwardTypeId IS NULL OR yi.inwardTypeId = :inwardTypeId) " +
           "AND (:companyId IS NULL OR yi.companyId = :companyId) " +
           "GROUP BY yisd.itemId")
    List<Object[]> sumAvailableQtyByOrderIdAndItemIds(
        @Param("orderId") Long orderId,
        @Param("itemIds") List<Long> itemIds,
        @Param("inwardTypeId") Long inwardTypeId,
        @Param("companyId") Long companyId);
    
    @Query("SELECT COUNT(yisd) FROM YarnInwardStockDetails yisd LEFT JOIN yisd.yarnInventory yi WHERE yi.orderId = :purchaseOrderId")
    Long countByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    @Query("SELECT COUNT(yisd) FROM YarnInwardStockDetails yisd " +
            "LEFT JOIN yisd.yarnInventory yi " +
            "WHERE yi.orderId = :purchaseOrderId " +
            "AND (:companyId IS NULL OR yi.companyId = :companyId)")
    Long countByPurchaseOrderIdAndCompanyId(
            @Param("purchaseOrderId") Long purchaseOrderId,
            @Param("companyId") Long companyId
    );

     @Query("SELECT yisd FROM YarnInwardStockDetails yisd WHERE yisd.orderItemId = :orderItemId AND yisd.inwardTypeId = :inwardTypeId")
    List<YarnInwardStockDetails> findAllByOrderItemIdAndInwardTypeId(@Param("orderItemId") Long orderItemId, @Param("inwardTypeId") Long inwardTypeId);
 
    List<YarnInwardStockDetails> findByYarnInventoryId(Long yarnInventoryId);

    Optional<YarnInwardStockDetails> findFirstByYarnInventoryIdAndOrderItemId(Long yarnInventoryId, Long orderItemId);
    
    List<YarnInwardStockDetails> findByYarnInventoryIdAndItemIdAndLotNo(@Param("yarnInventoryId") Long yarnInventoryId, @Param("itemId") Long itemId, @Param("lotNo") String lotNo);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd WHERE yisd.orderItemId = :orderItemId")
    List<YarnInwardStockDetails> findAllByOrderItemId(@Param("orderItemId") Long orderItemId);

     Optional<YarnInwardStockDetails> findByOrderItemIdAndInwardTypeId(Long orderItemId, Long inwardTypeId);


      @Query("SELECT yisd FROM YarnInwardStockDetails yisd WHERE yisd.yarnInventory.id = :yarnInventoryId AND yisd.itemId = :itemId AND yisd.warehouseId = :warehouseId")
    List<YarnInwardStockDetails> findByYarnInventoryIdAndItemIdAndWarehouseId(@Param("yarnInventoryId") Long yarnInventoryId, @Param("itemId") Long itemId, @Param("warehouseId") Long warehouseId);

 @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM YarnInwardStockDetails yisd WHERE yisd.yarnInventory.id IN :yarnInventoryIds")
    void deleteByYarnInventoryIdIn(@Param("yarnInventoryIds") List<Long> yarnInventoryIds);

    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM YarnInwardStockDetails yisd WHERE yisd.orderItemId = :orderItemId AND yisd.inwardTypeId = :inwardTypeId")
    void deleteByOrderItemIdAndInwardTypeId(@Param("orderItemId") Long orderItemId, @Param("inwardTypeId") Long inwardTypeId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd " +
           "INNER JOIN yisd.yarnInventory yi " +
           "WHERE yi.inwardNo = :inwardNo " +
           "AND yi.orderId = :orderId")
    List<YarnInwardStockDetails> findByInwardNoAndOrderId(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd " +
           "WHERE yisd.yarnInventory.inwardNo = :inwardNo " +
           "AND yisd.yarnInventory.orderId = :orderId " +
           "AND yisd.orderItemId = :orderItemId " +
           "AND yisd.inwardTypeId = :inwardTypeId")
    List<YarnInwardStockDetails> findByInwardNoAndOrderIdAndOrderItemIdAndInwardTypeId(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("orderItemId") Long orderItemId,
        @Param("inwardTypeId") Long inwardTypeId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd " +
           "INNER JOIN yisd.yarnInventory yi " +
           "WHERE yi.inwardId = :inwardId " +
           "AND yisd.orderItemId = :orderItemId " +
           "AND yisd.inwardTypeId = :inwardTypeId")
    List<YarnInwardStockDetails> findByInwardIdAndOrderItemIdAndInwardTypeId(
        @Param("inwardId") Long inwardId,
        @Param("orderItemId") Long orderItemId,
        @Param("inwardTypeId") Long inwardTypeId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd " +
           "INNER JOIN yisd.yarnInventory yi " +
           "WHERE yi.inwardNo = :inwardNo " +
           "AND yi.orderId = :orderId " +
           "AND yisd.orderItemId = :orderItemId " +
           "AND yisd.inwardTypeId = :inwardTypeId")
    List<YarnInwardStockDetails> findByInwardNoAndOrderIdWithEagerLoad(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("orderItemId") Long orderItemId,
        @Param("inwardTypeId") Long inwardTypeId);

    @Query("SELECT COUNT(yisd) FROM YarnInwardStockDetails yisd " +
           "INNER JOIN yisd.yarnInventory yi " +
           "WHERE yi.inwardNo = :inwardNo " +
           "AND yi.orderId = :orderId " +
           "AND yi.companyId = :companyId " +
           "AND yisd.lotNo = :lotNo " +
           "AND yisd.itemId = :itemId " +
           "AND yisd.id != :currentId")
    Long countDuplicateLotNoInInward(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("companyId") Long companyId,
        @Param("lotNo") String lotNo,
        @Param("itemId") Long itemId,
        @Param("currentId") Long currentId);
    
    @Query("SELECT yisd FROM YarnInwardStockDetails yisd " +
           "INNER JOIN yisd.yarnInventory yi " +
           "WHERE yi.inwardNo = :inwardNo " +
           "AND yi.orderId = :orderId " +
           "AND yi.companyId = :companyId " +
           "AND yisd.itemId = :itemId " +
           "AND yisd.lotNo = :lotNo")
    List<YarnInwardStockDetails> findExistingLotNoRecords(
        @Param("inwardNo") String inwardNo,
        @Param("orderId") Long orderId,
        @Param("companyId") Long companyId,
        @Param("lotNo") String lotNo,
        @Param("itemId") Long itemId);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd JOIN yisd.yarnInventory yi " +
           "WHERE yi.companyId = :companyId AND yisd.itemId = :itemId AND yisd.lotNo = :lotNo")
    List<YarnInwardStockDetails> findByCompanyIdAndItemIdAndLotNo(
        @Param("companyId") Long companyId,
        @Param("itemId") Long itemId,
        @Param("lotNo") String lotNo);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd JOIN yisd.yarnInventory yi " +
           "WHERE yi.companyId = :companyId AND yisd.itemId = :itemId AND LOWER(yisd.lotNo) = LOWER(:lotNo)")
    List<YarnInwardStockDetails> findByCompanyIdAndItemIdAndLotNoIgnoreCase(
        @Param("companyId") Long companyId,
        @Param("itemId") Long itemId,
        @Param("lotNo") String lotNo);

    @Query("SELECT yisd FROM YarnInwardStockDetails yisd JOIN yisd.yarnInventory yi " +
           "WHERE yi.companyId = :companyId AND LOWER(yisd.lotNo) = LOWER(:lotNo)")
    List<YarnInwardStockDetails> findByCompanyIdAndLotNoIgnoreCase(
        @Param("companyId") Long companyId,
        @Param("lotNo") String lotNo);

    List<YarnInwardStockDetails> findByLotNoIgnoreCase(String lotNo);

        Optional<YarnInwardStockDetails> findByYarnInventoryIdAndItemIdAndMillIdAndLotNo(
    Long yarnInventoryId, 
    Long itemId, 
    Long millId, 
    String lotNo
);


}