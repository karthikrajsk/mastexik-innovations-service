package com.tech3.erp.dto;

import com.tech3.erp.entity.LotEntry;

import jakarta.persistence.PrePersist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class LotEntryDTO {

    private Long id;
    private String lotNumber;
    private Long poTypeId;
    private Long yarnId;
    private Long fabricId;
    private Object warehouseId; // Changed to Object to handle both String and Long
    private BigDecimal quantity;
    private Integer noOfBags;
    private BigDecimal kgsPerBag;
    private BigDecimal rejectedQuantity;
    private BigDecimal cost;
    private String remarks;
    private Boolean activeFlag;

    public LotEntryDTO() {}

    public LotEntryDTO(LotEntry lot) {
        this.id = lot.getId();
        this.lotNumber = lot.getLotNumber();
        this.poTypeId = lot.getPoTypeId();
        this.yarnId = lot.getYarnId();
        this.fabricId = lot.getFabricId();
        this.warehouseId = lot.getWarehouseId(); // This will be Long from DB
        this.quantity = lot.getQuantity();
        this.noOfBags = lot.getNoOfBags() != null ? lot.getNoOfBags() : 0;
        this.kgsPerBag = lot.getKgsPerBag() != null ? lot.getKgsPerBag() : BigDecimal.ZERO;
        this.rejectedQuantity = lot.getRejectedQuantity();
        this.cost = lot.getCost();
        this.remarks = lot.getRemarks();
        this.activeFlag = lot.getActiveFlag();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
    
    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }
    
    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }
    
    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }
    
    public Object getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Object warehouseId) { this.warehouseId = warehouseId; }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public BigDecimal getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(BigDecimal rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}

