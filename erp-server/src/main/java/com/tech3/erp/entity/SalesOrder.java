package com.tech3.erp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "sales_order", schema = "masters")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String salesOrderNo;
    private Date orderDate;
    private Long buyerCustomerId;
    private String buyerPoNo;
    private Long deliverToId;
    private Long currencyId;
    private Double exchangeRate;
    private Long modeOfShipmentId;
    private Long shipmentTermsId;
    private Long paymentTermsId;
    private Long termsConditionsId;
    private Boolean activeFlag;
    private String internalOrderNo;
    private Short packingTypeId;
    private Long taxId;
       @Column(name = "is_approved")
    private Short isApproved = (short) 0;

    @Column(name = "is_complete")
    private Short isComplete = (short) 0;

    @Column(name = "internal_ref_no")
    private String internalRefNo;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "ready_date")
    private LocalDate readyDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
    
    private Long orderTypeId;

    @Column(name = "deliver_to_address_id")
    private Long deliverToAddressId;
    
    @Column(name = "company_id")
    private Long companyId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "export_type")
    private String exportType;
    
    @Column(name = "department")
    private String department;
    
    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SalesOrderItem> items;

//    @PrePersist
//    public void prePersist() {
//        if (this.salesOrderNo == null || this.salesOrderNo.isEmpty()) {
//            this.salesOrderNo = generateSalesOrderNo();
//        }
//        if (this.internalOrderNo == null || this.internalOrderNo.isEmpty()) {
//            this.internalOrderNo = generateInternalOrderNo();
//        }
//    }
//
//    private String generateSalesOrderNo() {
//        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        int randomNum = new Random().nextInt(9000) + 1000;
//        return "SO-JVT-" + datePart + "-" + randomNum;
//    }
//    
//    private String generateInternalOrderNo() {
//        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        int randomNum = new Random().nextInt(9000) + 1000;
//        return "JVT-" + datePart + "-" + randomNum;
//    }
    
    // Getters and Setters
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }
    
    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Long getBuyerCustomerId() {
        return buyerCustomerId;
    }

    public void setBuyerCustomerId(Long buyerCustomerId) {
        this.buyerCustomerId = buyerCustomerId;
    }

    public String getBuyerPoNo() {
        return buyerPoNo;
    }

    public void setBuyerPoNo(String buyerPoNo) {
        this.buyerPoNo = buyerPoNo;
    }

    public Long getDeliverToId() {
        return deliverToId;
    }

    public void setDeliverToId(Long deliverToId) {
        this.deliverToId = deliverToId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getModeOfShipmentId() {
        return modeOfShipmentId;
    }

    public void setModeOfShipmentId(Long modeOfShipmentId) {
        this.modeOfShipmentId = modeOfShipmentId;
    }

    public Long getShipmentTermsId() {
        return shipmentTermsId;
    }

    public void setShipmentTermsId(Long shipmentTermsId) {
        this.shipmentTermsId = shipmentTermsId;
    }
    
    public Long getPaymentTermsId() {
        return paymentTermsId;
    }

    public void setPaymentTermsId(Long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public String getInternalOrderNo() {
        return internalOrderNo;
    }

    public void setInternalOrderNo(String internalOrderNo) {
        this.internalOrderNo = internalOrderNo;
    }
       
    public Short getPackingTypeId() {
        return packingTypeId;
    }

    public void setPackingTypeId(Short packingTypeId) {
        this.packingTypeId = packingTypeId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }
    
    public Long getOrderTypeId() {
        return orderTypeId;
    }
    
    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getDeliverToAddressId() {
        return deliverToAddressId;
    }

    public void setDeliverToAddressId(Long deliverToAddressId) {
        this.deliverToAddressId = deliverToAddressId;
    }
    
    public String getExportType() {
        return exportType;
    }
    
    public void setExportType(String exportType) {
        this.exportType = exportType;
    }
    
    public Short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public String getInternalRefNo() {
        return internalRefNo;
    }

    public void setInternalRefNo(String internalRefNo) {
        this.internalRefNo = internalRefNo;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public LocalDate getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(LocalDate readyDate) {
        this.readyDate = readyDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public List<SalesOrderItem> getItems() { return items; }
    
    public void setItems(List<SalesOrderItem> items) { this.items = items; }
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
}
