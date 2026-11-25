package com.tech3.erp.dto;

import com.tech3.erp.entity.Flange;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FlangeDTO {
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String flangeNo;

    private boolean activeFlag; // Matches active_flag in the database

    // New fields aligned with Flange entity
    private Long companyId;
    private Long issuedCompanyId;
    private boolean issuedToInternal = false;
    private Integer isIssued = 0;       // 0/1
    private Integer isSizingReceive = 0;   // 0/1
    private Integer isBeamGating = 0;   // 0/1
    private Integer isInwarded = 0;     // 0/1

    // Default constructor
    public FlangeDTO() {
    }

    // Constructor with parameters (basic)
    public FlangeDTO(Long id, String flangeNo, boolean activeFlag) {
        this.id = id;
        this.flangeNo = flangeNo;
        this.activeFlag = activeFlag;
    }

    // Constructor to convert from entity to DTO
    public FlangeDTO(Flange flange) {
        this.id = flange.getId();
        this.flangeNo = flange.getFlangeNo();
        this.activeFlag = flange.getActiveFlag();
        this.companyId = flange.getCompanyId();
        this.issuedCompanyId = flange.getIssuedCompanyId();
        this.issuedToInternal = flange.isIssuedToInternal();
        this.isIssued = flange.getIsIssued();
        this.isSizingReceive = flange.getIsSizingReceive();
        this.isBeamGating = flange.getIsBeamGating();
        this.isInwarded = flange.getIsInwarded();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getIssuedCompanyId() {
        return issuedCompanyId;
    }

    public void setIssuedCompanyId(Long issuedCompanyId) {
        this.issuedCompanyId = issuedCompanyId;
    }

    public boolean isIssuedToInternal() {
        return issuedToInternal;
    }

    public void setIssuedToInternal(boolean issuedToInternal) {
        this.issuedToInternal = issuedToInternal;
    }

    public Integer getIsIssued() {
        return isIssued;
    }

    public void setIsIssued(Integer isIssued) {
        this.isIssued = isIssued;
    }

    public Integer getIsSizingReceive() {
        return isSizingReceive;
    }

    public void setIsSizingReceive(Integer isSizingReceive) {
        this.isSizingReceive = isSizingReceive;
    }

    public Integer getIsBeamGating() {
        return isBeamGating;
    }

    public void setIsBeamGating(Integer isBeamGating) {
        this.isBeamGating = isBeamGating;
    }

    public Integer getIsInwarded() {
        return isInwarded;
    }

    public void setIsInwarded(Integer isInwarded) {
        this.isInwarded = isInwarded;
    }
}
