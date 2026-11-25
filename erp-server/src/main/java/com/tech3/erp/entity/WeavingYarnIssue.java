package com.tech3.erp.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "weaving_yarn_issue", schema = "masters")
public class WeavingYarnIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_contract_no", nullable = true)
    private String weavingContractNo;

    @Column(name = "weaving_job_work_order_id")
    private Long weavingJobWorkOrderId;

    @Column(name = "transportation_dtl", columnDefinition = "text", nullable = true)
    private String transportationDetails;

    @Column(name = "transport_id", nullable = true)
    private Long transportId;

    @Column(name = "terms_conditions_id")
    private Long termsConditionsId;

    @Column(name = "fabric_dtl", columnDefinition = "text")
    private String fabricDetails;

    @Column(name = "yarn_issue_date")
    private LocalDate yarnIssueDate;

    @Column(name = "yarn_issue_challan_no", nullable = true)
    private String yarnIssueChallanNo;

    @Column(name = "active_flag")
    private boolean activeFlag = true;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "remarks", columnDefinition = "text", nullable = true)
    private String remarks;

    @Column(name = "vehicle_no", nullable = true)
    private String vehicleNo;

    @Column(name = "gross_wt")
    private BigDecimal grossWt;

    @OneToMany(mappedBy = "weavingYarnIssue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeavingYarnRequirement> requirements = new ArrayList<>();
    
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

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }

    public Long getWeavingJobWorkOrderId() {
        return weavingJobWorkOrderId;
    }

    public void setWeavingJobWorkOrderId(Long weavingJobWorkOrderId) {
        this.weavingJobWorkOrderId = weavingJobWorkOrderId;
    }

    public String getTransportationDetails() {
        return transportationDetails;
    }

    public void setTransportationDetails(String transportationDetails) {
        this.transportationDetails = transportationDetails;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
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

    public LocalDate getYarnIssueDate() {
        return yarnIssueDate;
    }

    public void setYarnIssueDate(LocalDate yarnIssueDate) {
        this.yarnIssueDate = yarnIssueDate;
    }

    public String getYarnIssueChallanNo() {
        return yarnIssueChallanNo;
    }

    public void setYarnIssueChallanNo(String yarnIssueChallanNo) {
        this.yarnIssueChallanNo = yarnIssueChallanNo;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public BigDecimal getGrossWt() { return grossWt; }
    public void setGrossWt(BigDecimal grossWt) { this.grossWt = grossWt; }

    public List<WeavingYarnRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<WeavingYarnRequirement> requirements) {
        this.requirements = requirements;
    }

    public void addRequirement(WeavingYarnRequirement requirement) {
        requirements.add(requirement);
        requirement.setWeavingYarnIssue(this);
    }
    
}