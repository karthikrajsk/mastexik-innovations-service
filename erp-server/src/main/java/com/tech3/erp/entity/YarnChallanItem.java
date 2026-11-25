package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "yarn_challan_item", schema = "masters")
public class YarnChallanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "yarn_challan_id")
    @JsonBackReference("yarnChallan-items")
    private YarnChallan yarnChallan;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "order_quantity")
    private BigDecimal orderQuantity;

    @Column(name = "rate_per_unit")
    private BigDecimal ratePerUnit;

    @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "taxable_value")
    private BigDecimal taxableValue;

    @Column(name = "gst_percent_id")
    private Long gstPercentId;

    @Column(name = "gst_amount")
    private BigDecimal gstAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "issue_qty")
    private BigDecimal issueQty;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "is_invoice")
    private Integer isInvoice = 0;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YarnChallan getYarnChallan() {
        return yarnChallan;
    }

    public void setYarnChallan(YarnChallan yarnChallan) {
        this.yarnChallan = yarnChallan;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getRatePerUnit() {
        return ratePerUnit;
    }

    public void setRatePerUnit(BigDecimal ratePerUnit) {
        this.ratePerUnit = ratePerUnit;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public BigDecimal getTaxableValue() {
        return taxableValue;
    }

    public void setTaxableValue(BigDecimal taxableValue) {
        this.taxableValue = taxableValue;
    }

    public Long getGstPercentId() {
        return gstPercentId;
    }

    public void setGstPercentId(Long gstPercentId) {
        this.gstPercentId = gstPercentId;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
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

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
