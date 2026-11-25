package com.tech3.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "flange_master", schema = "masters")
public class Flange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flange_no", unique = true, nullable = false, length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String flangeNo;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;  // Default value as per the DB schema

    // New fields
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "issued_company_id")
    private Long issuedCompanyId;

    @Column(name = "issued_to_internal", nullable = true, columnDefinition = "boolean default false")
    private boolean issuedToInternal = false;

    @Column(name = "is_issued", nullable = true)
    private Integer isIssued = 0; // default 0

    @Column(name = "is_sizing_receive", nullable = true)
    private Integer isSizingReceive = 0; // default 0

    @Column(name = "is_beam_gating", nullable = true)
    private Integer isBeamGating = 0; // default 0

    @Column(name = "is_inwarded", nullable = true)
    private Integer isInwarded = 0; // default 0

    // Default constructor
    public Flange() {
    }

    // Constructor with parameters
    public Flange(Long id, String flangeNo, boolean activeFlag) {
        this.id = id;
        this.flangeNo = flangeNo;
        this.activeFlag = activeFlag;
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

    // New getters and setters
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
