package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnOutwardStockDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class YarnOutwardDTO {

    private Long id;
    private Long outwardTypeId;
    private String outwardNo;
    private Long companyId;
    private Long createdBy;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate outwardDate;

    private String financialYearId;
    private Long orderId;
    private String orderNo;
    private Long vendorId;
    private String remarks;
    private Boolean activeFlag;

    @JsonProperty("yarnOutwardStockDetails")
    private List<YarnOutwardStockDetailsDTO> yarnOutwardStockDetails;

    public YarnOutwardDTO() {}

    public YarnOutwardDTO(YarnOutward yarnOutward) {
        this.id = yarnOutward.getId();
        this.outwardTypeId = yarnOutward.getOutwardTypeId();
        this.outwardNo = yarnOutward.getOutwardNo();
        this.companyId = yarnOutward.getCompanyId();
        this.createdBy = yarnOutward.getCreatedBy();
        this.outwardDate = yarnOutward.getOutwardDate();
        this.financialYearId = yarnOutward.getFinancialYearId();
        this.orderId = yarnOutward.getOrderId();
        this.orderNo = yarnOutward.getOrderNo();
        this.vendorId = yarnOutward.getVendorId();
        this.remarks = yarnOutward.getRemarks();
        this.activeFlag = yarnOutward.getActiveFlag();

        if (yarnOutward.getYarnOutwardStockDetails() != null) {
            this.yarnOutwardStockDetails = yarnOutward.getYarnOutwardStockDetails().stream()
                .map(YarnOutwardStockDetailsDTO::new)
                .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
    }

    public String getOutwardNo() {
        return outwardNo;
    }

    public void setOutwardNo(String outwardNo) {
        this.outwardNo = outwardNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public String getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(String financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

    public List<YarnOutwardStockDetailsDTO> getYarnOutwardStockDetails() {
        return yarnOutwardStockDetails;
    }

    public void setYarnOutwardStockDetails(List<YarnOutwardStockDetailsDTO> yarnOutwardStockDetails) {
        this.yarnOutwardStockDetails = yarnOutwardStockDetails;
    }
}
