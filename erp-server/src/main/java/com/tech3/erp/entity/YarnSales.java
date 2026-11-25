package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "yarn_sales", schema = "masters")
public class YarnSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "yarn_sales_no", unique = true, nullable = false)
    private String yarnSalesNo;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "delivery_at_id")
    private Long deliveryAtId;

    @Column(name = "gst_type_id")
    private Long gstTypeId;

    @Column(name = "shipment_terms_id")
    private Long shipmentTermsId;

    @Column(name = "payment_terms_id")
    private Long paymentTermsId;

    @Column(name = "terms_conditions_id")
    private Long termsConditionsId;

    @Column(name = "mode_of_shipment_id")
    private Long modeOfShipmentId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @OneToMany(mappedBy = "yarnSales", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<YarnSalesItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYarnSalesNo() {
        return yarnSalesNo;
    }

    public void setYarnSalesNo(String yarnSalesNo) {
        this.yarnSalesNo = yarnSalesNo;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getDeliveryAtId() {
        return deliveryAtId;
    }

    public void setDeliveryAtId(Long deliveryAtId) {
        this.deliveryAtId = deliveryAtId;
    }

    public Long getGstTypeId() {
        return gstTypeId;
    }

    public void setGstTypeId(Long gstTypeId) {
        this.gstTypeId = gstTypeId;
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

    public Long getModeOfShipmentId() {
        return modeOfShipmentId;
    }

    public void setModeOfShipmentId(Long modeOfShipmentId) {
        this.modeOfShipmentId = modeOfShipmentId;
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

    public List<YarnSalesItem> getItems() {
        return items;
    }

    public void setItems(List<YarnSalesItem> items) {
        this.items = items;
    }
}
