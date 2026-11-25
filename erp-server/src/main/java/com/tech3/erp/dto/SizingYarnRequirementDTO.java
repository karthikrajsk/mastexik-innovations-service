package com.tech3.erp.dto;

import java.math.BigDecimal;

public class SizingYarnRequirementDTO {
    private Long id;
    private String fabricDetails;
    private String yarnName;
    private String actualYarn;
    private Long yarnId;
    private Long actualYarnId; // NEW FIELD - to track the actual yarn ID for backend sync
    private Integer warpMeters;
    private BigDecimal warpGramsPerMeter;
    private BigDecimal warpRequirement;
    private BigDecimal totalIssuedQty;
    private BigDecimal balanceToIssue;
    private boolean activeFlag;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public String getActualYarn() {
        return actualYarn;
    }

    public void setActualYarn(String actualYarn) {
        this.actualYarn = actualYarn;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    // NEW getter and setter for actualYarnId
    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public Integer getWarpMeters() {
        return warpMeters;
    }

    public void setWarpMeters(Integer warpMeters) {
        this.warpMeters = warpMeters;
    }

    public BigDecimal getWarpGramsPerMeter() {
        return warpGramsPerMeter;
    }

    public void setWarpGramsPerMeter(BigDecimal warpGramsPerMeter) {
        this.warpGramsPerMeter = warpGramsPerMeter;
    }

    public BigDecimal getWarpRequirement() {
        return warpRequirement;
    }

    public void setWarpRequirement(BigDecimal warpRequirement) {
        this.warpRequirement = warpRequirement;
    }

    public BigDecimal getTotalIssuedQty() {
        return totalIssuedQty;
    }

    public void setTotalIssuedQty(BigDecimal totalIssuedQty) {
        this.totalIssuedQty = totalIssuedQty;
    }

    public BigDecimal getBalanceToIssue() {
        return balanceToIssue;
    }

    public void setBalanceToIssue(BigDecimal balanceToIssue) {
        this.balanceToIssue = balanceToIssue;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}