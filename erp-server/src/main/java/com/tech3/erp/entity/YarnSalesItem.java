package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "yarn_sales_item", schema = "masters")
public class YarnSalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "yarn_sales_id")
    @JsonBackReference
    private YarnSales yarnSales;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "mill_id")
    private Long millId;

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

    @Column(name = "yarn_inward_stock_details_id")
    private Long yarnInwardStockDetailsId;

    @Column(name = "issued_bags")
    private BigDecimal issuedBags;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YarnSales getYarnSales() {
        return yarnSales;
    }

    public void setYarnSales(YarnSales yarnSales) {
        this.yarnSales = yarnSales;
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

    public Long getYarnInwardStockDetailsId() {
        return yarnInwardStockDetailsId;
    }

    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) {
        this.yarnInwardStockDetailsId = yarnInwardStockDetailsId;
    }

    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
