package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class YarnChallanDTO {
    private Long id;
    private LocalDate challanDate;
    private Long buyerId;
    private Long yarnSalesOrderId;
    private String tareWeight;
    private String grossWeight;
    private String challanNo;
    private String remarks;
    private String deliveryAt;
    private Long consigneeId;
    private Long companyId;
    private Boolean activeFlag = true;
    private Long invoiceId;
    private Integer isInvoice;
    private String vehicleNo;
    private Long transportId;
    private String transportName;
    private List<YarnChallanItemDTO> items;
    private List<YarnIssueDetailDTO> yarnIssueDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(LocalDate challanDate) {
        this.challanDate = challanDate;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getYarnSalesOrderId() {
        return yarnSalesOrderId;
    }

    public void setYarnSalesOrderId(Long yarnSalesOrderId) {
        this.yarnSalesOrderId = yarnSalesOrderId;
    }

    public String getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(String tareWeight) {
        this.tareWeight = tareWeight;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDeliveryAt() {
        return deliveryAt;
    }

    public void setDeliveryAt(String deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public Long getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Long consigneeId) {
        this.consigneeId = consigneeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public Long getTransportId() { return transportId; }
    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getTransportName() { return transportName; }
    public void setTransportName(String transportName) { this.transportName = transportName; }

    public List<YarnChallanItemDTO> getItems() {
        return items;
    }

    public void setItems(List<YarnChallanItemDTO> items) {
        this.items = items;
    }

    public List<YarnIssueDetailDTO> getYarnIssueDetails() {
        return yarnIssueDetails;
    }

    public void setYarnIssueDetails(List<YarnIssueDetailDTO> yarnIssueDetails) {
        this.yarnIssueDetails = yarnIssueDetails;
    }
}
