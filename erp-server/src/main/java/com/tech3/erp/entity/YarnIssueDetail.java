package com.tech3.erp.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "yarn_issue_detail", schema = "masters")
public class YarnIssueDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "yarn_challan_id")
    @JsonBackReference("yarnChallan-issueDetails")
    private YarnChallan yarnChallan;

    @Column(name = "yarn_sales_item_id")
    private Long yarnSalesItemId;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "available_qty")
    private BigDecimal availableQty;

    @Column(name = "available_bags")
    private BigDecimal availableBags;

    @Column(name = "no_of_bags")
    private BigDecimal noOfBags;

    @Column(name = "kgs_per_bag")
    private BigDecimal kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "issue_qty")
    private BigDecimal issueQty;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "inward_type_id")
    private Long inwardTypeId;

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

    public Long getYarnSalesItemId() {
        return yarnSalesItemId;
    }

    public void setYarnSalesItemId(Long yarnSalesItemId) {
        this.yarnSalesItemId = yarnSalesItemId;
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }

    public BigDecimal getAvailableBags() {
        return availableBags;
    }

    public void setAvailableBags(BigDecimal availableBags) {
        this.availableBags = availableBags;
    }

    public BigDecimal getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(BigDecimal noOfBags) {
        this.noOfBags = noOfBags;
    }

    public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public BigDecimal getConesPerBag() {
        return conesPerBag;
    }

    public void setConesPerBag(BigDecimal conesPerBag) {
        this.conesPerBag = conesPerBag;
    }

    public BigDecimal getConesWeight() {
        return conesWeight;
    }

    public void setConesWeight(BigDecimal conesWeight) {
        this.conesWeight = conesWeight;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getInwardTypeId() {
        return inwardTypeId;
    }

    public void setInwardTypeId(Long inwardTypeId) {
        this.inwardTypeId = inwardTypeId;
    }
}
