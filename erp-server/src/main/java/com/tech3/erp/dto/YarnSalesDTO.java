package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class YarnSalesDTO {
    private Long id;
    private String yarnSalesNo;
    private LocalDate orderDate;
    private Long buyerId;
    private Long deliveryAtId;
    private Long gstTypeId;
    private Long shipmentTermsId;
    private Long paymentTermsId;
    private Long termsConditionsId;
    private Long modeOfShipmentId;
    private Long companyId;
    private Boolean activeFlag = true;
    private List<YarnSalesItemDTO> items;

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

    public List<YarnSalesItemDTO> getItems() {
        return items;
    }

    public void setItems(List<YarnSalesItemDTO> items) {
        this.items = items;
    }
}
