package com.tech3.erp.dto;

import com.tech3.erp.entity.YarnReturnItem;
import java.math.BigDecimal;

public class YarnReturnItemDTO {

    private Long id;
    private Long yarnReturnId;
    private Long yarnId;
    private String yarnName;
    private Long actualYarnId;
    private String actualYarnName;
    private BigDecimal noOfBags;
    private Double totalKgs;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private String lotNo;
    private Boolean activeFlag;
    private Long sizingYarnIssueId;
    private Long weavingYarnIssueId;

    public YarnReturnItemDTO() {}

    public YarnReturnItemDTO(YarnReturnItem item) {
        this.id = item.getId();
        this.yarnReturnId = item.getYarnReturn().getId();
        this.yarnId = item.getYarnId();
        this.yarnName = item.getYarnName();
        this.actualYarnId = item.getActualYarnId();
        this.actualYarnName = item.getActualYarnName();
        this.noOfBags = item.getNoOfBags();
        this.totalKgs = item.getTotalKgs();
        this.conesPerBag = item.getConesPerBag();
        this.conesWeight = item.getConesWeight();
        this.lotNo = item.getLotNo();
        this.activeFlag = item.getActiveFlag();
        this.sizingYarnIssueId = item.getSizingYarnIssueId();
        this.weavingYarnIssueId = item.getWeavingYarnIssueId();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYarnReturnId() {
        return yarnReturnId;
    }

    public void setYarnReturnId(Long yarnReturnId) {
        this.yarnReturnId = yarnReturnId;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public String getActualYarnName() {
        return actualYarnName;
    }

    public void setActualYarnName(String actualYarnName) {
        this.actualYarnName = actualYarnName;
    }

    public BigDecimal getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(BigDecimal noOfBags) {
        this.noOfBags = noOfBags;
    }

    public Double getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(Double totalKgs) {
        this.totalKgs = totalKgs;
    }

    public BigDecimal getConesPerBag() {
        return conesPerBag;
    }

    public void setConesPerBag(BigDecimal conesPerBag) {
        this.conesPerBag = conesPerBag;
    }

    public BigDecimal getConesWeight() {
        return conesWeight;
    }

    public void setConesWeight(BigDecimal conesWeight) {
        this.conesWeight = conesWeight;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getSizingYarnIssueId() {
        return sizingYarnIssueId;
    }

    public void setSizingYarnIssueId(Long sizingYarnIssueId) {
        this.sizingYarnIssueId = sizingYarnIssueId;
    }

    public Long getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(Long weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
    }
}