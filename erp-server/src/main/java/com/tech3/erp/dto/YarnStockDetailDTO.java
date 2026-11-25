package com.tech3.erp.dto;

import java.math.BigDecimal;

public class YarnStockDetailDTO {

    private Long id;
    private Long inwardId;
    private Long itemId;
    private Long orderItemId;
    private String yarnCode;
    private Long uomId;
    private String lotNo;
    private BigDecimal inwardQty;
    private BigDecimal inwardBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private BigDecimal acceptedQty;
    private BigDecimal availableQty;
    private BigDecimal availableBags;
    private BigDecimal rejectedQty;
    private Long warehouseId;
    private String remark;
    private Long inwardTypeId;
    private Long yarnTypeId;
    private Long millId;

    public YarnStockDetailDTO() {}

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getYarnCode() {
        return yarnCode;
    }

    public void setYarnCode(String yarnCode) {
        this.yarnCode = yarnCode;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
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

    public BigDecimal getAcceptedQty() {
        return acceptedQty;
    }

    public void setAcceptedQty(BigDecimal acceptedQty) {
        this.acceptedQty = acceptedQty;
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

    public BigDecimal getRejectedQty() {
        return rejectedQty;
    }

    public void setRejectedQty(BigDecimal rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

     public Long getInwardTypeId() {
        return inwardTypeId;
    }

    public void setInwardTypeId(Long inwardTypeId) {
        this.inwardTypeId = inwardTypeId;
    }

    public Long getYarnTypeId() {
        return yarnTypeId;
    }

    public void setYarnTypeId(Long yarnTypeId) {
        this.yarnTypeId = yarnTypeId;
    }
}
