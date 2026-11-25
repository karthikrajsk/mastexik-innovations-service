package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SizingYarnIssueEntryDTO {
    private Long id;
    private Long vendorId;
    private Long companyId;
    private Long sizingPlanId;
    private String sizingPlanNo;
    private Long outwardTypeId;
    private String transportationDetails;
    private Long termsConditionsId;
    private String fabricDetails;
    private LocalDate  sizingYarnIssueDate;
    private String yarnIssueChallanNo;
    private String issueNo;
    private boolean activeFlag;
    private String remark;
    private String vehicleNo;
    private BigDecimal grossWt;
    
    private List<SizingYarnRequirementDTO> requirements;
    private List<SizingYarnIssueDTO> sizingYarnIssue;
 private List<YarnOutwardStockDetailsDTO> yarnOutwardStockDetails;
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
    }

    public String getTransportationDetails() {
        return transportationDetails;
    }

    public void setTransportationDetails(String transportationDetails) {
        this.transportationDetails = transportationDetails;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public LocalDate  getSizingYarnIssueDate() {
        return sizingYarnIssueDate;
    }

    public void setSizingYarnIssueDate(LocalDate  sizingYarnIssueDate) {
        this.sizingYarnIssueDate = sizingYarnIssueDate;
    }

    public String getYarnIssueChallanNo() {
        return yarnIssueChallanNo;
    }

    public void setYarnIssueChallanNo(String yarnIssueChallanNo) {
        this.yarnIssueChallanNo = yarnIssueChallanNo;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public BigDecimal getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(BigDecimal grossWt) {
        this.grossWt = grossWt;
    }

    public List<SizingYarnRequirementDTO> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<SizingYarnRequirementDTO> requirements) {
        this.requirements = requirements;
    }

    public List<SizingYarnIssueDTO> getSizingYarnIssue() {
        return sizingYarnIssue;
    }

    public void setSizingYarnIssue(List<SizingYarnIssueDTO> sizingYarnIssue) {
        this.sizingYarnIssue = sizingYarnIssue;
    }
      public List<YarnOutwardStockDetailsDTO> getYarnOutwardStockDetails() {
        return yarnOutwardStockDetails;
    }

    public void setYarnOutwardStockDetails(List<YarnOutwardStockDetailsDTO> yarnOutwardStockDetails) {
        this.yarnOutwardStockDetails = yarnOutwardStockDetails;
    }

}