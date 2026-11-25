package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.WeftInward;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class WeftInwardDTO {
    private Long id;
    private String inwardDate;
    private String weavingContractId;
    private String weavingJobWorkOrderId;
    private String weavingContractNo;
    private String weavingWorkOrderNo;
    private String weavingYarnIssueId;
    private String fabricCode;
    private String dcNo;
    private String inwardNo;
    private String dcDate;
    private Boolean activeFlag;
    private String orderTypeId;
    private String companyId;
    private String buyerId;
    private String sourceCompanyId;
    private String destinationCompanyId;
    private String linkedBeamInwardId;
    private String tradingCompanyId;
    private String factoryCompanyId;

    @JsonProperty("yarnDetails")
    private List<WeftInwardItemDTO> yarnDetails;

    public WeftInwardDTO() {}

    public WeftInwardDTO(WeftInward entity) {
        this.id = entity.getId();
        this.inwardDate = entity.getInwardDate() != null ? entity.getInwardDate().toString() : null;
        this.weavingContractId = entity.getWeavingContractId() != null ? entity.getWeavingContractId().toString() : null;
        this.weavingJobWorkOrderId = entity.getWeavingJobWorkOrderId() != null ? entity.getWeavingJobWorkOrderId().toString() : null;
        this.weavingYarnIssueId = entity.getWeavingYarnIssueId() != null ? entity.getWeavingYarnIssueId().toString() : null;
        this.fabricCode = entity.getFabricCode();
        this.dcNo = entity.getDcNo();
        this.inwardNo = entity.getInwardNo();
        this.dcDate = entity.getDcDate() != null ? entity.getDcDate().toString() : null;
        this.activeFlag = entity.getActiveFlag();
        this.orderTypeId = entity.getOrderTypeId() != null ? entity.getOrderTypeId().toString() : null;
        this.companyId = entity.getCompanyId() != null ? entity.getCompanyId().toString() : null;
        this.buyerId = entity.getBuyerId() != null ? entity.getBuyerId().toString() : null;
        this.sourceCompanyId = entity.getSourceCompanyId() != null ? entity.getSourceCompanyId().toString() : null;
        this.destinationCompanyId = entity.getDestinationCompanyId() != null ? entity.getDestinationCompanyId().toString() : null;
        this.linkedBeamInwardId = entity.getLinkedBeamInwardId() != null ? entity.getLinkedBeamInwardId().toString() : null;
        this.tradingCompanyId = entity.getTradingCompanyId() != null ? entity.getTradingCompanyId().toString() : null;
        this.factoryCompanyId = entity.getFactoryCompanyId() != null ? entity.getFactoryCompanyId().toString() : null;
        if (entity.getYarnItems() != null) {
            this.yarnDetails = entity.getYarnItems().stream().map(WeftInwardItemDTO::new).collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(String inwardDate) {
        this.inwardDate = inwardDate;
    }

    public String getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(String weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public String getWeavingJobWorkOrderId() {
        return weavingJobWorkOrderId;
    }

    public void setWeavingJobWorkOrderId(String weavingJobWorkOrderId) {
        this.weavingJobWorkOrderId = weavingJobWorkOrderId;
    }

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }

    public String getWeavingWorkOrderNo() {
        return weavingWorkOrderNo;
    }

    public void setWeavingWorkOrderNo(String weavingWorkOrderNo) {
        this.weavingWorkOrderNo = weavingWorkOrderNo;
    }

    public String getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(String weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSourceCompanyId() {
        return sourceCompanyId;
    }

    public void setSourceCompanyId(String sourceCompanyId) {
        this.sourceCompanyId = sourceCompanyId;
    }

    public String getDestinationCompanyId() {
        return destinationCompanyId;
    }

    public void setDestinationCompanyId(String destinationCompanyId) {
        this.destinationCompanyId = destinationCompanyId;
    }

    public String getLinkedBeamInwardId() {
        return linkedBeamInwardId;
    }

    public void setLinkedBeamInwardId(String linkedBeamInwardId) {
        this.linkedBeamInwardId = linkedBeamInwardId;
    }

    public String getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(String tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public String getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(String factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }

    public List<WeftInwardItemDTO> getYarnDetails() {
        return yarnDetails;
    }

    public void setYarnDetails(List<WeftInwardItemDTO> yarnDetails) {
        this.yarnDetails = yarnDetails;
    }
}
