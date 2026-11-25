package com.tech3.erp.dto;

import java.time.LocalDate;

public class TransactionStatusDTO {
    
    private Long id;
    private Long transactionTypeId;
    private String transactionTypeName;
    private Long primaryTypeId;
    private Long buyerId;
    private Long vendorId;
    private LocalDate date;
    private String description;
    private String status;
    private Boolean isActive;
    
    public TransactionStatusDTO() {
    }
    
    public TransactionStatusDTO(Long id, Long transactionTypeId, String transactionTypeName, Long primaryTypeId, Long buyerId, Long vendorId, LocalDate date, String description, String status, Boolean isActive) {
        this.id = id;
        this.transactionTypeId = transactionTypeId;
        this.transactionTypeName = transactionTypeName;
        this.primaryTypeId = primaryTypeId;
        this.buyerId = buyerId;
        this.vendorId = vendorId;
        this.date = date;
        this.description = description;
        this.status = status;
        this.isActive = isActive;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTransactionTypeId() {
        return transactionTypeId;
    }
    
    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }
    
    public String getTransactionTypeName() {
        return transactionTypeName;
    }
    
    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
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
}
