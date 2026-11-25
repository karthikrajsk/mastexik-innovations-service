package com.tech3.erp.dto;

import com.tech3.erp.entity.VendorSizingRate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class VendorSizingRateDTO {

    private Long id;
    private Long vendorId;
    private String vendorName;
    private String yarnCount;
    private BigDecimal sizingRate;
    private LocalDate effectiveDate;
    private Boolean activeFlag = true;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public VendorSizingRateDTO() {}

    public VendorSizingRateDTO(VendorSizingRate entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.vendorId = entity.getVendorId();
            this.yarnCount = entity.getYarnCount();
            this.sizingRate = entity.getSizingRate();
            this.effectiveDate = entity.getEffectiveDate();
            this.activeFlag = entity.getActiveFlag();
            this.createdBy = entity.getCreatedBy();
            this.createdAt = entity.getCreatedAt();
            this.updatedBy = entity.getUpdatedBy();
            this.updatedAt = entity.getUpdatedAt();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getYarnCount() {
        return yarnCount;
    }

    public void setYarnCount(String yarnCount) {
        this.yarnCount = yarnCount;
    }

    public BigDecimal getSizingRate() {
        return sizingRate;
    }

    public void setSizingRate(BigDecimal sizingRate) {
        this.sizingRate = sizingRate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
