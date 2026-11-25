package com.tech3.erp.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SizingYarnIssueDTO {
	private Long id;
    private Long sizingYarnIssueEntryId;
    private String yarnName;
    private Long yarnId;
    private Long millId;
    private Long warehouseId;
    private String alternateYarn;
    private BigDecimal availableReqQty;
    private BigDecimal availableQty;
    private Integer noOfBags;
    private BigDecimal issuedBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    @JsonAlias({"issuedQty"})
    private BigDecimal issueQty;
    private BigDecimal lotAvailableQty;
    private BigDecimal lotAvailableBags;
    private String lotNo;
    // private Long lotId;
    private String issueNo;
    private boolean activeFlag;
    private Long inwardId;
    private Long inwardTypeId;
    private Long yarnInwardStockDetailsId;
    private Long orderItemId;
    private Long outwardTypeId;
    private Long actualItemId;
    private Long uomId;
    private Long companyId;
    private BigDecimal perBabyConeWeight;
    private BigDecimal noOfCones;
    private BigDecimal availableCones;
    private String inwardNo;
    private String millInvNo;
    private String millDate;

    // Getters and Setters
    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public String getMillInvNo() {
        return millInvNo;
    }

    public void setMillInvNo(String millInvNo) {
        this.millInvNo = millInvNo;
    }

    public String getMillDate() {
        return millDate;
    }

    public void setMillDate(String millDate) {
        this.millDate = millDate;
    }

    public BigDecimal getNoOfCones() {
        return noOfCones;
    }

    public void setNoOfCones(BigDecimal noOfCones) {
        this.noOfCones = noOfCones;
    }

    public BigDecimal getAvailableCones() {
        return availableCones;
    }

    public void setAvailableCones(BigDecimal availableCones) {
        this.availableCones = availableCones;
    }
    public BigDecimal getPerBabyConeWeight() {
        return perBabyConeWeight;
    }

    public void setPerBabyConeWeight(BigDecimal perBabyConeWeight) {
        this.perBabyConeWeight = perBabyConeWeight;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSizingYarnIssueEntryId() {
        return sizingYarnIssueEntryId;
    }

    public void setSizingYarnIssueEntryId(Long sizingYarnIssueEntryId) {
        this.sizingYarnIssueEntryId = sizingYarnIssueEntryId;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public BigDecimal getAvailableReqQty() {
        return availableReqQty;
    }

    public void setAvailableReqQty(BigDecimal availableReqQty) {
        this.availableReqQty = availableReqQty;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getAlternateYarn() {
        return alternateYarn;
    }

    public void setAlternateYarn(String alternateYarn) {
        this.alternateYarn = alternateYarn;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }
    
   public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
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

    // public Long getLotId() {
    //     return lotId;
    // }

    // public void setLotId(Long lotId) {
    //     this.lotId = lotId;
    // }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    
    public Long getCompanyId() {
        return companyId;
    }

      public Long getInwardId() {
        return inwardId;
    }

    public void setInwardId(Long inwardId) {
        this.inwardId = inwardId;
    }

    public Long getInwardTypeId() {
        return inwardTypeId;
    }

    public void setInwardTypeId(Long inwardTypeId) {
        this.inwardTypeId = inwardTypeId;
    }

    public Long getYarnInwardStockDetailsId() {
        return yarnInwardStockDetailsId;
    }

    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) {
        this.yarnInwardStockDetailsId = yarnInwardStockDetailsId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
    }

       public Long getActualItemId() {
        return actualItemId;
    }

    public void setActualItemId(Long actualItemId) {
        this.actualItemId = actualItemId;
    }

  public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    
    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
    }

    public BigDecimal getLotAvailableQty() {
        return lotAvailableQty;
    }

    public void setLotAvailableQty(BigDecimal lotAvailableQty) {
        this.lotAvailableQty = lotAvailableQty;
    }

    public BigDecimal getLotAvailableBags() {
        return lotAvailableBags;
    }

    public void setLotAvailableBags(BigDecimal lotAvailableBags) {
        this.lotAvailableBags = lotAvailableBags;
    }
}