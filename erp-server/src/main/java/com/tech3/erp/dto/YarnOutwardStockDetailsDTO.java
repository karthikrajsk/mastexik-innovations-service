package com.tech3.erp.dto;

import com.tech3.erp.entity.YarnOutwardStockDetails;
import java.math.BigDecimal;

public class YarnOutwardStockDetailsDTO {

    private Long id;
    private Long outwardId;
    private Long orderItemId;
    private Long inwardId;
    private Long outwardTypeId;
    private Long actualItemId;
    private Long uomId;
    private Long millId;
    private Long warehouseId;
    private String lotNo;
    private BigDecimal issuedQty;
    private BigDecimal issuedBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;

    public YarnOutwardStockDetailsDTO() {}

    public YarnOutwardStockDetailsDTO(YarnOutwardStockDetails detail) {
        this.id = detail.getId();
        this.outwardId = detail.getYarnOutward() != null ? detail.getYarnOutward().getId() : null;
        this.orderItemId = detail.getOrderItemId();
        this.inwardId = detail.getInwardId();
        this.outwardTypeId = detail.getOutwardTypeId();
        this.actualItemId = detail.getActualItemId();
        this.uomId = detail.getUomId();
        this.millId = detail.getMillId();
        this.warehouseId = detail.getWarehouseId();
        this.lotNo = detail.getLotNo();
        this.issuedQty = detail.getIssuedQty();
        this.issuedBags = detail.getIssuedBags();
        this.kgsPerBag = detail.getKgsPerBag();
        this.conesPerBag = detail.getConesPerBag();
        this.conesWeight = detail.getConesWeight();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutwardId() {
        return outwardId;
    }

    public void setOutwardId(Long outwardId) {
        this.outwardId = outwardId;
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

    public Long getMillId() {
        return millId;
    }
    
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public BigDecimal getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(BigDecimal issuedQty) {
        this.issuedQty = issuedQty;
    }

    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
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
}
