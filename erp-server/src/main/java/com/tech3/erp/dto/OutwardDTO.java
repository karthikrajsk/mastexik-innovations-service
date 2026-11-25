package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OutwardDTO {
    private Long id;
    private Long lotEntryId;
    private String outwardType;
    private Long referenceId;
    private String referenceType;
    private String referenceTypeId;
    private BigDecimal quantity;
    private LocalDate outwardDate;
    private String remarks;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean activeFlag;

    // Constructors, Getters and Setters
    public OutwardDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLotEntryId() { return lotEntryId; }
    public void setLotEntryId(Long lotEntryId) { this.lotEntryId = lotEntryId; }

    public String getOutwardType() { return outwardType; }
    public void setOutwardType(String outwardType) { this.outwardType = outwardType; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public String getReferenceTypeId() { return referenceTypeId; }
    public void setReferenceTypeId(String referenceTypeId) { this.referenceTypeId = referenceTypeId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public LocalDate getOutwardDate() { return outwardDate; }
    public void setOutwardDate(LocalDate outwardDate) { this.outwardDate = outwardDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}