package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, Long> {
    
    @EntityGraph(attributePaths = {"yarnItems", "fabricItems"})
    @Query("SELECT pr FROM PurchaseReturn pr " +
           "WHERE pr.activeFlag = true OR pr.activeFlag IS NULL " +
           "ORDER BY pr.id DESC")
    List<PurchaseReturn> findAllWithItems();
    
    @EntityGraph(attributePaths = {"yarnItems", "fabricItems"})
    @Query("SELECT pr FROM PurchaseReturn pr " +
           "WHERE pr.activeFlag = true OR pr.activeFlag IS NULL " +
           "ORDER BY pr.id DESC")
    Page<PurchaseReturn> findAllWithItemsPaginated(Pageable pageable);
    
    @EntityGraph(attributePaths = {"yarnItems", "fabricItems"})
    @Query("SELECT pr FROM PurchaseReturn pr WHERE pr.id = :id")
    Optional<PurchaseReturn> findByIdWithItems(Long id);
    
    @EntityGraph(attributePaths = {"yarnItems", "fabricItems"})
    @Query("SELECT pr FROM PurchaseReturn pr WHERE (pr.activeFlag = true OR pr.activeFlag IS NULL) AND pr.companyId = :companyId ORDER BY pr.id DESC")
    List<PurchaseReturn> findAllWithItemsByCompanyId(@Param("companyId") Long companyId);
    
    @EntityGraph(attributePaths = {"yarnItems", "fabricItems"})
    @Query("SELECT pr FROM PurchaseReturn pr WHERE (pr.activeFlag = true OR pr.activeFlag IS NULL) AND pr.companyId = :companyId ORDER BY pr.id DESC")
    Page<PurchaseReturn> findAllWithItemsByCompanyIdPaginated(@Param("companyId") Long companyId, Pageable pageable);
    
    @EntityGraph(attributePaths = {"yarnItems", "fabricItems"})
    Optional<PurchaseReturn> findByIdAndCompanyId(Long id, Long companyId);
    
    void deleteByIdAndCompanyId(Long id, Long companyId);
    
    @Query("SELECT COUNT(pr) FROM PurchaseReturn pr WHERE pr.purchaseOrderId = :purchaseOrderId")
    Long countByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    @Query("SELECT COUNT(pr) FROM PurchaseReturn pr " +
            "WHERE pr.purchaseOrderId = :purchaseOrderId " +
            "AND (pr.activeFlag = true OR pr.activeFlag IS NULL) " +
            "AND (:companyId IS NULL OR pr.companyId = :companyId)")
    Long countByPurchaseOrderIdAndCompanyId(
            @Param("purchaseOrderId") Long purchaseOrderId,
            @Param("companyId") Long companyId
    );
}