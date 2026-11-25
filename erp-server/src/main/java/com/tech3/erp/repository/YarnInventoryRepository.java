package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YarnInventoryRepository extends JpaRepository<YarnInventory, Long> {
    
    Optional<YarnInventory> findByInwardNo(String inwardNo);

    // Add this method for the opening balance service
    List<YarnInventory> findByInwardNoStartingWith(String prefix);
    
    // Existing method (if you have it)
    @Query("SELECT MAX(CAST(SUBSTRING(yi.inwardNo, LENGTH(:prefix) + 1) AS integer)) " +
           "FROM YarnInventory yi WHERE yi.inwardNo LIKE :prefix% AND yi.companyId = :companyId")
    Integer findMaxOrderNumberByPrefix(@Param("prefix") String prefix, @Param("companyId") Long companyId);

     List<YarnInventory> findByOrderId(Long orderId);
     
     List<YarnInventory> findByOrderNo(String orderNo);
    
    void deleteByOrderNo(String orderNo);
    
    void deleteByInwardNo(String inwardNo);
    
    void deleteByOrderNoAndInwardTypeIdAndCompanyId(String orderNo, Long inwardTypeId, Long companyId);
    
    void deleteByOrderIdAndInwardTypeIdAndCompanyId(Long orderId, Long inwardTypeId, Long companyId);

    List<YarnInventory> findByInwardNoAndOrderId(String inwardNo, Long orderId);

    List<YarnInventory> findByInwardIdAndInwardTypeId(Long inwardId, Long inwardTypeId);

    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query("DELETE FROM YarnInventory yi WHERE yi.inwardId = :inwardId AND yi.inwardTypeId = :inwardTypeId")
    void deleteByInwardIdAndInwardTypeId(@Param("inwardId") Long inwardId, @Param("inwardTypeId") Long inwardTypeId);

    List<YarnInventory> findByInwardNoAndCompanyIdAndInwardTypeId(String inwardNo, Long companyId, Long inwardTypeId);

}