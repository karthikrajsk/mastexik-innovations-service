package com.tech3.erp.dto;

import java.math.BigDecimal;

public class YarnIssueDetailDTO {
    private Long id;
    private Long yarnSalesItemId;
    private Long yarnId;
    private Long millId;
    private Long warehouseId;
    private String lotNumber;
    private BigDecimal availableQty;
    private BigDecimal availableBags;
    private BigDecimal noOfBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private BigDecimal issueQty;
    private Boolean activeFlag = true;

    // New fields
    private Long orderItemId;
    private Long inwardId;
    private Long inwardTypeId;
    private Long outwardTypeId;
    private Long uomId;
    private BigDecimal inwardQty;
    private BigDecimal inwardBags;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;
    private String inwardNo;
    private Long yarnInwardStockDetailsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYarnSalesItemId() {
        return yarnSalesItemId;
    }

    public void setYarnSalesItemId(Long yarnSalesItemId) {
        this.yarnSalesItemId = yarnSalesItemId;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }

    public BigDecimal getAvailableBags() {
        return availableBags;
    }

    public void setAvailableBags(BigDecimal availableBags) {
        this.availableBags = availableBags;
    }

    public BigDecimal getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(BigDecimal noOfBags) {
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

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public BigDecimal getInwardQty() {
        return inwardQty;
    }

    public void setInwardQty(BigDecimal inwardQty) {
        this.inwardQty = inwardQty;
    }

    public BigDecimal getInwardBags() {
        return inwardBags;
    }

    public void setInwardBags(BigDecimal inwardBags) {
        this.inwardBags = inwardBags;
    }

    public BigDecimal getAcceptedQty() {
        return acceptedQty;
    }

    public void setAcceptedQty(BigDecimal acceptedQty) {
        this.acceptedQty = acceptedQty;
    }

    public BigDecimal getRejectedQty() {
        return rejectedQty;
    }

    public void setRejectedQty(BigDecimal rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public Long getYarnInwardStockDetailsId() {
        return yarnInwardStockDetailsId;
    }

    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) {
        this.yarnInwardStockDetailsId = yarnInwardStockDetailsId;
    }
}
