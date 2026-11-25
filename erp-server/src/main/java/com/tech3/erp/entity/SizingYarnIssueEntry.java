package com.tech3.erp.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;

@Entity
@Table(name = "sizing_yarn_issue_entry", schema = "masters")
public class SizingYarnIssueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "transportation_dtl", columnDefinition = "text", nullable = true)
    private String transportationDetails;

    @Column(name = "terms_conditions_id")
    private Long termsConditionsId;

    @Column(name = "fabric_dtl", columnDefinition = "text")
    private String fabricDetails;

    @Column(name = "sizing_yarn_issue_date")
    private LocalDate sizingYarnIssueDate;

    @Column(name = "yarn_issue_challan_no", length = 100, nullable = true)
    private String yarnIssueChallanNo;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "outward_type_id")
    private Long outwardTypeId;

    @Column(name = "active_flag")
    private boolean activeFlag = true;

    @Column(name = "remark", columnDefinition = "text", nullable = true)
    private String remark;

    @Column(name = "vehicle_no", length = 50, nullable = true)
    private String vehicleNo;

    @Column(name = "gross_wt", precision = 18, scale = 4)
    private BigDecimal grossWt;

@OneToMany(
    mappedBy = "sizingYarnIssueEntry",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
@Fetch(FetchMode.SUBSELECT)
@BatchSize(size = 50)
private List<SizingYarnRequirement> requirements = new ArrayList<>();

@OneToMany(
    mappedBy = "sizingYarnIssueEntry",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
@Fetch(FetchMode.SUBSELECT)
@BatchSize(size = 50)
private List<SizingYarnIssue> sizingYarnIssue = new ArrayList<>();
    
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

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
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

    public LocalDate getSizingYarnIssueDate() {
        return sizingYarnIssueDate;
    }

    public void setSizingYarnIssueDate(LocalDate sizingYarnIssueDate) {
        this.sizingYarnIssueDate = sizingYarnIssueDate;
    }

    public String getYarnIssueChallanNo() {
        return yarnIssueChallanNo;
    }

    public void setYarnIssueChallanNo(String yarnIssueChallanNo) {
        this.yarnIssueChallanNo = yarnIssueChallanNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
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

    public List<SizingYarnRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<SizingYarnRequirement> requirements) {
        this.requirements = requirements;
    }

    public List<SizingYarnIssue> getSizingYarnIssues() {
        return sizingYarnIssue;
    }

    public void setSizingYarnIssues(List<SizingYarnIssue> sizingYarnIssue) {
        this.sizingYarnIssue = sizingYarnIssue;
    }

    public void addSizingYarnIssue(SizingYarnIssue issue) {
        sizingYarnIssue.add(issue);
        issue.setSizingYarnIssueEntry(this);
    }
    
    public void removeSizingYarnIssue(SizingYarnIssue issue) {
        sizingYarnIssue.remove(issue);
        issue.setSizingYarnIssueEntry(null);
    }
    
    // Note: Removed addRequirement to avoid duplicate method error. Use getRequirements().add() directly if needed.
}