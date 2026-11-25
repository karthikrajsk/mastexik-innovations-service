package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.util.List;

public class WeavingYarnRequirementDTO {
    private Long id;
    private Long weavingYarnIssueId;
    private String yarnName;
    private String actualYarn;
    private Long yarnId;
    private Long actualYarnId;
    private BigDecimal gramsPerMeter;
    private BigDecimal totalReqQty;
    private BigDecimal totalIssueQty;
    private BigDecimal overallTotalIssueQty;
    private BigDecimal balanceToIssue;
    private boolean activeFlag;

    private List<YarnIssueDTO> yarnIssues;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(Long weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
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

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public BigDecimal getGramsPerMeter() {
        return gramsPerMeter;
    }

    public void setGramsPerMeter(BigDecimal gramsPerMeter) {
        this.gramsPerMeter = gramsPerMeter;
    }

    public BigDecimal getTotalReqQty() {
        return totalReqQty;
    }

    public void setTotalReqQty(BigDecimal totalReqQty) {
        this.totalReqQty = totalReqQty;
    }

    public BigDecimal getTotalIssueQty() {
        return totalIssueQty;
    }

    public void setTotalIssueQty(BigDecimal totalIssueQty) {
        this.totalIssueQty = totalIssueQty;
    }

    public BigDecimal getOverallTotalIssueQty() {
        return overallTotalIssueQty;
    }

    public void setOverallTotalIssueQty(BigDecimal overallTotalIssueQty) {
        this.overallTotalIssueQty = overallTotalIssueQty;
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
    
    public List<YarnIssueDTO> getYarnIssues() {
        return yarnIssues;
    }

    public void setYarnIssues(List<YarnIssueDTO> yarnIssues) {
        this.yarnIssues = yarnIssues;
    }
    
}