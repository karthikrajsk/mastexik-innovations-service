package com.tech3.erp.repository;

import com.tech3.erp.entity.ItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemInventoryRepository extends JpaRepository<ItemInventory, Long> {
    
    List<ItemInventory> findByInwardNoStartingWith(String prefix);
    
    Optional<ItemInventory> findByInwardNo(String inwardNo);
    
    @Query("SELECT MAX(CAST(SUBSTRING(ii.inwardNo, LENGTH(:prefix) + 1) AS integer)) " +
           "FROM ItemInventory ii WHERE ii.inwardNo LIKE :prefix% AND ii.companyId = :companyId")
    Integer findMaxOrderNumberByPrefix(@Param("prefix") String prefix, @Param("companyId") Long companyId);

    List<ItemInventory> findByOrderId(Long orderId);
    
    List<ItemInventory> findByInwardId(Long inwardId);

    List<ItemInventory> findByInwardIdAndOrderItemId(Long inwardId, Long orderItemId);
    
    List<ItemInventory> findByInwardNoAndOrderId(String inwardNo, Long orderId);
}

