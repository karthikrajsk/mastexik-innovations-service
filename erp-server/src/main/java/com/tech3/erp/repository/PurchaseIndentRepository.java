package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseIndent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;

@Repository
public interface PurchaseIndentRepository extends JpaRepository<PurchaseIndent, Long> {

    boolean existsByCompanyIdAndIndentNo(Long companyId, String indentNo);

    @Query(value = "SELECT * FROM masters.purchase_indent " +
            "WHERE company_id = :companyId " +
            "AND (:status IS NULL OR status = :status) " +
            "ORDER BY created_at DESC, id DESC LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<PurchaseIndent> findPageByCompanyId(
            @Param("companyId") Long companyId,
            @Param("status") String status,
            @Param("limit") long limit,
            @Param("offset") long offset);

    @Query(value = "SELECT COUNT(*) FROM masters.purchase_indent " +
            "WHERE company_id = :companyId AND (:status IS NULL OR status = :status)",
            nativeQuery = true)
    long countByCompanyIdAndStatus(@Param("companyId") Long companyId, @Param("status") String status);

    @Query("SELECT p FROM PurchaseIndent p LEFT JOIN FETCH p.items WHERE p.id = :id AND p.companyId = :companyId")
    Optional<PurchaseIndent> findByIdAndCompanyIdWithItems(
            @Param("id") Long id, @Param("companyId") Long companyId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PurchaseIndent p LEFT JOIN FETCH p.items WHERE p.id = :id AND p.companyId = :companyId")
    Optional<PurchaseIndent> findByIdAndCompanyIdWithItemsForUpdate(
            @Param("id") Long id, @Param("companyId") Long companyId);

    @Query("SELECT p FROM PurchaseIndent p LEFT JOIN FETCH p.items WHERE p.companyId = :companyId ORDER BY p.createdAt DESC, p.id DESC")
    List<PurchaseIndent> findAllByCompanyIdWithItems(@Param("companyId") Long companyId);
}
