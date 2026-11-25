package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "yarn_challan", schema = "masters")
public class YarnChallan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "challan_date")
    private LocalDate challanDate;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "yarn_sales_order_id")
    private Long yarnSalesOrderId;

    @Column(name = "tare_weight")
    private String tareWeight;

    @Column(name = "gross_weight")
    private String grossWeight;

    @Column(name = "challan_no")
    private String challanNo;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "delivery_at")
    private String deliveryAt;

    @Column(name = "consignee_id")
    private Long consigneeId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "is_invoice")
    private Integer isInvoice = 0;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "transport_id")
    private Long transportId;

    @OneToMany(mappedBy = "yarnChallan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("yarnChallan-items")
    private List<YarnChallanItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "yarnChallan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("yarnChallan-issueDetails")
    private List<YarnIssueDetail> yarnIssueDetails = new ArrayList<>();

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

    public List<YarnChallanItem> getItems() {
        return items;
    }

    public void setItems(List<YarnChallanItem> items) {
        this.items = items;
    }

    public List<YarnIssueDetail> getYarnIssueDetails() {
        return yarnIssueDetails;
    }

    public void setYarnIssueDetails(List<YarnIssueDetail> yarnIssueDetails) {
        this.yarnIssueDetails = yarnIssueDetails;
    }
}
