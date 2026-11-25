package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseOrder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
	
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(p.poNo, LENGTH(:prefix) + 1) AS INTEGER)), 0) " +
           "FROM PurchaseOrder p WHERE p.companyId = :companyId AND p.poNo LIKE CONCAT(:prefix, '%')")
    Integer findMaxOrderNumberByPrefix(@Param("prefix") String prefix, @Param("companyId") Long companyId);
	
    List<PurchaseOrder> findByVendorId(Long vendorId);
	
    @Query("SELECT DISTINCT po.vendorId FROM PurchaseOrder po WHERE po.poTypeId = :poTypeId")
    List<Long> findVendorIdsByPoTypeId(@Param("poTypeId") Long poTypeId);
    
    // ============ N+1 FIX: Two-Query Approach ============
    
    // Step 1: Get all IDs (efficient, no joins)
    @Query("SELECT po.id FROM PurchaseOrder po WHERE po.activeFlag = true OR po.activeFlag IS NULL ORDER BY po.id DESC")
    List<Long> findAllIds();
    
    // Step 2: Fetch entities with items for specific IDs
    @EntityGraph(attributePaths = "items")
    @Query("SELECT po FROM PurchaseOrder po WHERE po.id IN :ids AND (po.activeFlag = true OR po.activeFlag IS NULL) ORDER BY po.id DESC")
    List<PurchaseOrder> findByIdsWithItems(@Param("ids") List<Long> ids);
    
    @Query("SELECT po.id FROM PurchaseOrder po WHERE po.companyId = :companyId AND (po.activeFlag = true OR po.activeFlag IS NULL) ORDER BY po.id DESC")
    List<Long> findIdsByCompanyId(@Param("companyId") Long companyId);
    
    @EntityGraph(attributePaths = "items")
    @Query("SELECT po FROM PurchaseOrder po WHERE po.companyId = :companyId AND po.id IN :ids AND (po.activeFlag = true OR po.activeFlag IS NULL) ORDER BY po.id DESC")
    List<PurchaseOrder> findByCompanyIdAndIds(@Param("companyId") Long companyId, @Param("ids") List<Long> ids);
    
    @EntityGraph(attributePaths = "items")
    @Query("SELECT po FROM PurchaseOrder po WHERE po.companyId = :companyId AND po.vendorId = :vendorId AND (po.activeFlag = true OR po.activeFlag IS NULL) ORDER BY po.id DESC")
    List<PurchaseOrder> findByCompanyIdAndVendorId(@Param("companyId") Long companyId, @Param("vendorId") Long vendorId);
    
    @EntityGraph(attributePaths = "items")
    Optional<PurchaseOrder> findByIdAndCompanyId(Long id, Long companyId);
    
    @Modifying
    @Transactional
    void deleteByIdAndCompanyId(Long id, Long companyId);
    
    // For single PO fetch (if needed)
    @EntityGraph(attributePaths = "items")
    @Query("SELECT po FROM PurchaseOrder po WHERE po.id = :id")
    PurchaseOrder findByIdWithItems(@Param("id") Long id);
}