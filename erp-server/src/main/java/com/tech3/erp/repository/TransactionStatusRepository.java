package com.tech3.erp.repository;

import com.tech3.erp.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {
    
    List<TransactionStatus> findByTransactionTypeId(Long transactionTypeId);
    
    List<TransactionStatus> findByBuyerId(Long buyerId);
    
    List<TransactionStatus> findByVendorId(Long vendorId);
    
    List<TransactionStatus> findByStatus(String status);
    
    List<TransactionStatus> findByDate(LocalDate date);
    
    List<TransactionStatus> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<TransactionStatus> findByIsActive(Boolean isActive);
    
    @Query("SELECT ts FROM TransactionStatus ts " +
           "LEFT JOIN FETCH ts.transactionType " +
           "WHERE ts.id = :id")
    Optional<TransactionStatus> findByIdWithDetails(@Param("id") Long id);
    
    @Query("SELECT ts FROM TransactionStatus ts " +
           "LEFT JOIN FETCH ts.transactionType " +
           "WHERE ts.transactionType.id = :transactionTypeId")
    List<TransactionStatus> findByTransactionTypeIdWithDetails(@Param("transactionTypeId") Long transactionTypeId);
    
    @Query("SELECT ts FROM TransactionStatus ts " +
           "LEFT JOIN FETCH ts.transactionType")
    List<TransactionStatus> findAllWithDetails();
}
