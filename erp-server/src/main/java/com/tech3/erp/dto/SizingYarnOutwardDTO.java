package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.SizingYarnOutward;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SizingYarnOutwardDTO {

    private Long id;
    private String outwardNo;
    private LocalDate outwardDate;
    private Long orderTypeId;
    private Long sizingOrderId;
    private String sizingOrderNo;
    private Long sizingPlanId;
    private String sizingPlanNo;
    private Long buyerId;
    private Long tradingCompanyId;
    private Long factoryCompanyId;
    private Long companyId;
    private String buyerName;
    private Long vendorId;
    private String vendorName;
    private Long yarnId;
    private String yarnName;
    private String vehicleNo;
    private Long transportId;
    private String transportName;
    private String remarks;
    private String inwardType;

    @JsonProperty("summary")
    private SizingYarnOutwardSummaryDTO summary = new SizingYarnOutwardSummaryDTO();

    @JsonProperty("yarnIssueDetails")
    private List<SizingYarnOutwardDetailDTO> yarnIssueDetails = new ArrayList<>();

    public SizingYarnOutwardDTO() {
    }

    public SizingYarnOutwardDTO(SizingYarnOutward entity) {
        this.id = entity.getId();
        this.outwardNo = entity.getOutwardNo();
        this.outwardDate = entity.getOutwardDate();
        this.orderTypeId = entity.getOrderTypeId();
        this.sizingOrderId = entity.getSizingOrderId();
        this.sizingPlanId = entity.getSizingPlanId();
        this.buyerId = entity.getBuyerId();
        this.tradingCompanyId = entity.getTradingCompanyId();
        this.factoryCompanyId = entity.getFactoryCompanyId();
        this.companyId = entity.getCompanyId();
        this.buyerName = entity.getBuyerName();
        this.vendorId = entity.getVendorId();
        this.vendorName = entity.getVendorName();
        this.yarnId = entity.getYarnId();
        this.yarnName = entity.getYarnName();
        this.vehicleNo = entity.getVehicleNo();
        this.transportId = entity.getTransportId();
        this.transportName = entity.getTransportName();
        this.remarks = entity.getRemarks();
        this.inwardType = entity.getInwardType();
        this.summary = new SizingYarnOutwardSummaryDTO(entity.getIssuedBags(), entity.getIssuedQuantity());
        if (entity.getYarnIssueDetails() != null) {
            this.yarnIssueDetails = entity.getYarnIssueDetails().stream()
                    .map(SizingYarnOutwardDetailDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutwardNo() {
        return outwardNo;
    }

    public void setOutwardNo(String outwardNo) {
        this.outwardNo = outwardNo;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getSizingOrderId() {
        return sizingOrderId;
    }

    public void setSizingOrderId(Long sizingOrderId) {
        this.sizingOrderId = sizingOrderId;
    }

    public String getSizingOrderNo() {
        return sizingOrderNo;
    }

    public void setSizingOrderNo(String sizingOrderNo) {
        this.sizingOrderNo = sizingOrderNo;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public String getSizingPlanNo() {
        return sizingPlanNo;
    }

    public void setSizingPlanNo(String sizingPlanNo) {
        this.sizingPlanNo = sizingPlanNo;
    }

    public Long getBuyerId() {
        return buyerId;
    }
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getInwardType() {
        return inwardType;
    }

    public void setInwardType(String inwardType) {
        this.inwardType = inwardType;
    }

    public SizingYarnOutwardSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(SizingYarnOutwardSummaryDTO summary) {
        this.summary = summary;
    }

    public List<SizingYarnOutwardDetailDTO> getYarnIssueDetails() {
        return yarnIssueDetails;
    }

    public void setYarnIssueDetails(List<SizingYarnOutwardDetailDTO> yarnIssueDetails) {
        this.yarnIssueDetails = yarnIssueDetails;
    }
}
