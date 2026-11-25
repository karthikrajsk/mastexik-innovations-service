package com.tech3.erp.dto;

import java.math.BigDecimal;

public class YarnIssueDTO {
    private Long id;
    private Long weavingYarnRequirementId;
    private String yarnName;
    private String actualYarn;
    private Long yarnId;
    private Long actualYarnId;
    private Long millId;
    private Long warehouseId;
    private BigDecimal availableReqQty;
    private BigDecimal issueQty;
    private Integer noOfBags;
    private Double kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private String lotNumber;
    private BigDecimal lotAvailableBags;
    private Long inwardId;
    private Long inwardTypeId;
    private Long orderItemId;
    private Long loomId;
    private Long machineId;
    private Long beamGaitingId;
    private String issueType;
    private boolean activeFlag;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getLoomId() {
        return loomId;
    }

    public void setLoomId(Long loomId) {
        this.loomId = loomId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getBeamGaitingId() {
        return beamGaitingId;
    }

    public void setBeamGaitingId(Long beamGaitingId) {
        this.beamGaitingId = beamGaitingId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeavingYarnRequirementId() {
        return weavingYarnRequirementId;
    }

    public void setWeavingYarnRequirementId(Long weavingYarnRequirementId) {
        this.weavingYarnRequirementId = weavingYarnRequirementId;
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

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    public Double getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(Double kgsPerBag) {
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigDecimal getLotAvailableBags() {
        return lotAvailableBags;
    }

    public void setLotAvailableBags(BigDecimal lotAvailableBags) {
        this.lotAvailableBags = lotAvailableBags;
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

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}