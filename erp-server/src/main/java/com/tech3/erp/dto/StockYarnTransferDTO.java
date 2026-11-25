package com.tech3.erp.dto;

import java.math.BigDecimal;

public class StockYarnTransferDTO {

    private Long id;
    private Long inwardId;
    private String inwardNo;
    private String inwardNoStr;
    private String millName;
    private String lotNo;
    private Integer noOfBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
     private BigDecimal issuedQty;
    private BigDecimal totalQuantity;

    public StockYarnTransferDTO() {
    }

    public StockYarnTransferDTO(Long id, String inwardNo, String millName, String lotNo, Integer noOfBags, BigDecimal kgsPerBag, BigDecimal totalQuantity) {
        this.id = id;
        this.inwardNo = inwardNo;
        this.millName = millName;
        this.lotNo = lotNo;
        this.noOfBags = noOfBags;
        this.kgsPerBag = kgsPerBag;
        this.totalQuantity = totalQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInwardId() {
        return inwardId;
    }

    public void setInwardId(Long inwardId) {
        this.inwardId = inwardId;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public String getInwardNoStr() {
        return inwardNoStr;
    }

    public void setInwardNoStr(String inwardNoStr) {
        this.inwardNoStr = inwardNoStr;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
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

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
     public BigDecimal getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(BigDecimal issuedQty) {
        this.issuedQty = issuedQty;
    }
}