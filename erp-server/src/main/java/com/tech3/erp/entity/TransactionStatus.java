package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_status", schema = "masters")
public class TransactionStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private CodesDetail transactionType;
    
    @Column(name = "primary_type_id")
    private Long primaryTypeId;
    
    @Column(name = "buyer_id")
    private Long buyerId;
    
    @Column(name = "vendor_id")
    private Long vendorId;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public TransactionStatus() {
    }
    
    public TransactionStatus(Long id, CodesDetail transactionType, Long primaryTypeId, Long buyerId, Long vendorId, LocalDate date, String description, String status, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.transactionType = transactionType;
        this.primaryTypeId = primaryTypeId;
        this.buyerId = buyerId;
        this.vendorId = vendorId;
        this.date = date;
        this.description = description;
        this.status = status;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public CodesDetail getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(CodesDetail transactionType) {
        this.transactionType = transactionType;
    }
    
    public Long getPrimaryTypeId() {
        return primaryTypeId;
    }
    
    public void setPrimaryTypeId(Long primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }
    
    public Long getBuyerId() {
        return buyerId;
    }
    
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
    
    public Long getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
