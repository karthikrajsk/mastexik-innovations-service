package com.tech3.erp.repository;

import com.tech3.erp.entity.ApprovalEntity;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {

        
    @Transactional
    @Modifying
    @Query("DELETE FROM ApprovalEntity a WHERE a.dataId = :dataId AND a.screenName = :screenName")
    void deleteByDataIdAndScreenName(@Param("dataId") Long dataId, @Param("screenName") String screenName);
    
    List<ApprovalEntity> findByEntryNo(String entryNo);
    
    List<ApprovalEntity> findByEntryNoAndScreenName(String entryNo, String screenName);
    
    List<ApprovalEntity> findByScreenName(String screenName);
    
    List<ApprovalEntity> findByIsApproved(Short isApproved);
    
    List<ApprovalEntity> findByCompanyId(Long companyId);
    
    Page<ApprovalEntity> findByCompanyId(Long companyId, Pageable pageable);
    
    @Query("SELECT a FROM ApprovalEntity a WHERE a.companyId = :companyId AND a.entryDate >= :startDate AND a.entryDate <= :endDate")
    Page<ApprovalEntity> findByCompanyIdAndEntryDateBetween(
            @Param("companyId") Long companyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
    
    @Query("SELECT a FROM ApprovalEntity a WHERE a.companyId = :companyId AND a.screenName = :screenName")
    Page<ApprovalEntity> findByCompanyIdAndScreenName(
            @Param("companyId") Long companyId,
            @Param("screenName") String screenName,
            Pageable pageable
    );
    
    @Query("SELECT a FROM ApprovalEntity a WHERE a.companyId = :companyId AND a.isApproved = :isApproved")
    Page<ApprovalEntity> findByCompanyIdAndIsApproved(
            @Param("companyId") Long companyId,
            @Param("isApproved") Short isApproved,
            Pageable pageable
    );
    
    @Query("SELECT a FROM ApprovalEntity a WHERE a.companyId = :companyId AND a.entryDate >= :startDate AND a.entryDate <= :endDate AND a.isApproved = :isApproved")
    Page<ApprovalEntity> findByCompanyIdAndDateRangeAndApprovalStatus(
            @Param("companyId") Long companyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("isApproved") Short isApproved,
            Pageable pageable
    );
    
    @Query("SELECT a FROM ApprovalEntity a WHERE a.companyId = :companyId ORDER BY a.entryDate DESC, a.id DESC")
    Page<ApprovalEntity> findAllByCompanyIdOrderByDateDesc(
            @Param("companyId") Long companyId,
            Pageable pageable
    );
    
    @Query("SELECT a FROM ApprovalEntity a WHERE a.companyId = :companyId AND a.salesOrderNo = :salesOrderNo")
    Page<ApprovalEntity> findByCompanyIdAndSalesOrderNo(
            @Param("companyId") Long companyId,
            @Param("salesOrderNo") String salesOrderNo,
            Pageable pageable
    );
}
