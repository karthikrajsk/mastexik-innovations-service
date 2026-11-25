package com.tech3.erp.dto;

import com.tech3.erp.entity.SizingYarnOutwardDetail;
import java.math.BigDecimal;

public class SizingYarnOutwardDetailDTO {

    private Long id;
    private Long sizingYarnInwardId;
    private String sizingYarnInwardNo;
    private Long sizingYarnIssueId;
    private Long yarnId;
    private String yarnName;
    private BigDecimal inwardBags;
    private BigDecimal inwardQuantity;
    private BigDecimal kgsPerBag;
    private BigDecimal coneWeight;
    private BigDecimal issuingBags;
    private BigDecimal issuedQuantity;
    private Long millId;
    private String millName;
    private String lotNo;
    private Long warehouseId;

    public SizingYarnOutwardDetailDTO() {
    }

    public SizingYarnOutwardDetailDTO(SizingYarnOutwardDetail entity) {
        this.id = entity.getId();
        this.sizingYarnInwardId = entity.getSizingYarnInwardId();
        this.sizingYarnInwardNo = entity.getSizingYarnInwardNo();
        this.sizingYarnIssueId = entity.getSizingYarnIssueId();
        this.yarnId = entity.getYarnId();
        this.yarnName = entity.getYarnName();
        this.inwardBags = entity.getInwardBags();
        this.inwardQuantity = entity.getInwardQuantity();
        this.kgsPerBag = entity.getKgsPerBag();
        this.coneWeight = entity.getConeWeight();
        this.issuingBags = entity.getIssuingBags();
        this.issuedQuantity = entity.getIssuedQuantity();
        this.millId = entity.getMillId();
        this.millName = entity.getMillName();
        this.lotNo = entity.getLotNo();
        this.warehouseId = entity.getWarehouseId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSizingYarnInwardId() {
        return sizingYarnInwardId;
    }

    public void setSizingYarnInwardId(Long sizingYarnInwardId) {
        this.sizingYarnInwardId = sizingYarnInwardId;
    }

    public String getSizingYarnInwardNo() {
        return sizingYarnInwardNo;
    }

    public void setSizingYarnInwardNo(String sizingYarnInwardNo) {
        this.sizingYarnInwardNo = sizingYarnInwardNo;
    }

    public Long getSizingYarnIssueId() {
        return sizingYarnIssueId;
    }

    public void setSizingYarnIssueId(Long sizingYarnIssueId) {
        this.sizingYarnIssueId = sizingYarnIssueId;
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

    public BigDecimal getInwardBags() {
        return inwardBags;
    }

    public void setInwardBags(BigDecimal inwardBags) {
        this.inwardBags = inwardBags;
    }

    public BigDecimal getInwardQuantity() {
        return inwardQuantity;
    }

    public void setInwardQuantity(BigDecimal inwardQuantity) {
        this.inwardQuantity = inwardQuantity;
    }

    public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public BigDecimal getConeWeight() {
        return coneWeight;
    }

    public void setConeWeight(BigDecimal coneWeight) {
        this.coneWeight = coneWeight;
    }

    public BigDecimal getIssuingBags() {
        return issuingBags;
    }

    public void setIssuingBags(BigDecimal issuingBags) {
        this.issuingBags = issuingBags;
    }

    public BigDecimal getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(BigDecimal issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
