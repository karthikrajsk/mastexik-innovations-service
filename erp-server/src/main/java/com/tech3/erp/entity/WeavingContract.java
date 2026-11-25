package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "weaving_contract", schema = "masters")
public class WeavingContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weaving_contract_no", nullable = false)
    private String weavingContractNo;
    
    
    @Column(name = "contract_date")
    private LocalDate contractDate; // NEW FIELD

    @Column(name = "tax_id")
    private Integer taxId; 

    @Column(name = "sales_order_no")
    private Long salesOrderNo;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "terms_conditions_id")
    private Long termsConditionsId;

    @Column(name = "payment_terms_id")
    private Long paymentTermsId;
    
    @Column(name = "order_type_id")
    private Long orderTypeId;

    private String remarks;

    private Integer rpm;
    private Double efficiency;

    @Column(name = "product_calculation")
    private Double productCalculation;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "is_complete")
    private Short isComplete;
    
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @OneToMany(mappedBy = "weavingContract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WeavingContractItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "weavingContract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<YarnRequirement> yarnRequirements = new ArrayList<>();
    
    @OneToMany(mappedBy = "weavingContract", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
//    @PrePersist
//    public void prePersist() {
//        if (this.weavingContractNo == null || this.weavingContractNo.isEmpty()) {
//            this.weavingContractNo = generateWeavingContractNo();
//        }
//    }
//
//    private String generateWeavingContractNo() {
//        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        int randomNum = new Random().nextInt(9000) + 1000;
//        return "WC-" + datePart + "-" + randomNum;
//    }

    // Getters and Setters
    
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }
    
    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public Long getPaymentTermsId() {
        return paymentTermsId;
    }

    public void setPaymentTermsId(Long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
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

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public List<WeavingContractItem> getItems() {
        return items;
    }

    public void setItems(List<WeavingContractItem> items) {
        this.items = items;
    }

    public List<YarnRequirement> getYarnRequirements() {
        return yarnRequirements;
    }

    public void setYarnRequirements(List<YarnRequirement> yarnRequirements) {
        this.yarnRequirements = yarnRequirements;
    }
    
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    public Long getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(Long salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public Double getProductCalculation() {
        return productCalculation;
    }

    public void setProductCalculation(Double productCalculation) {
        this.productCalculation = productCalculation;
    }
}