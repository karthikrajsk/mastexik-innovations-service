package com.tech3.erp.dto;

import com.tech3.erp.entity.WeavingYarnOutwardDetail;
import java.math.BigDecimal;

public class WeavingYarnOutwardDetailDTO {

    private Long id;
    private Long weftInwardId;
    private String weftInwardNo;
    private Long weavingYarnIssueId;
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

    public WeavingYarnOutwardDetailDTO() {
    }

    public WeavingYarnOutwardDetailDTO(WeavingYarnOutwardDetail entity) {
        this.id = entity.getId();
        this.weftInwardId = entity.getWeftInwardId();
        this.weftInwardNo = entity.getWeftInwardNo();
        this.weavingYarnIssueId = entity.getWeavingYarnIssueId();
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

    public Long getWeftInwardId() {
        return weftInwardId;
    }

    public void setWeftInwardId(Long weftInwardId) {
        this.weftInwardId = weftInwardId;
    }

    public String getWeftInwardNo() {
        return weftInwardNo;
    }

    public void setWeftInwardNo(String weftInwardNo) {
        this.weftInwardNo = weftInwardNo;
    }

    public Long getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(Long weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
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
