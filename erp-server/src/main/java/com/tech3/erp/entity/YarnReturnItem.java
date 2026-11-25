package com.tech3.erp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;

@Entity
@Table(name = "yarn_return_item", schema = "masters")
public class YarnReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yarn_return_id", nullable = false)
    @JsonBackReference("yarn-return-items")
    private YarnReturn yarnReturn;

    @Column(name = "yarn_id", nullable = false)
    private Long yarnId;

    @Column(name = "yarn_name", nullable = false)
    private String yarnName;

    @Column(name = "actual_yarn_id")
    private Long actualYarnId;

    @Column(name = "actual_yarn_name")
    private String actualYarnName;

    @Column(name = "no_of_bags", nullable = false, precision = 38, scale = 3)
    private BigDecimal noOfBags;

    @Column(name = "total_kgs", nullable = false)
    private Double totalKgs;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    // ID fields for proper linking based on return type
    @Column(name = "yarn_inventory_id")
    private Long yarnInventoryId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "yarn_outward_id")
    private Long yarnOutwardId;

    @Column(name = "yarn_outward_stockdetails_id")
    private Long yarnOutwardStockdetailsId;

    @Column(name = "sizing_yarn_issue_id")
    private Long sizingYarnIssueId;

    @Column(name = "weaving_yarn_issue_id")
    private Long weavingYarnIssueId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YarnReturn getYarnReturn() {
        return yarnReturn;
    }

    public void setYarnReturn(YarnReturn yarnReturn) {
        this.yarnReturn = yarnReturn;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public String getActualYarnName() {
        return actualYarnName;
    }

    public void setActualYarnName(String actualYarnName) {
        this.actualYarnName = actualYarnName;
    }

    public BigDecimal getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(BigDecimal noOfBags) {
        this.noOfBags = noOfBags;
    }

    public Double getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(Double totalKgs) {
        this.totalKgs = totalKgs;
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

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    // Getters and setters for ID fields
    public Long getYarnInventoryId() {
        return yarnInventoryId;
    }

    public void setYarnInventoryId(Long yarnInventoryId) {
        this.yarnInventoryId = yarnInventoryId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getYarnOutwardId() {
        return yarnOutwardId;
    }

    public void setYarnOutwardId(Long yarnOutwardId) {
        this.yarnOutwardId = yarnOutwardId;
    }

    public Long getYarnOutwardStockdetailsId() {
        return yarnOutwardStockdetailsId;
    }

    public void setYarnOutwardStockdetailsId(Long yarnOutwardStockdetailsId) {
        this.yarnOutwardStockdetailsId = yarnOutwardStockdetailsId;
    }

    public Long getSizingYarnIssueId() {
        return sizingYarnIssueId;
    }

    public void setSizingYarnIssueId(Long sizingYarnIssueId) {
        this.sizingYarnIssueId = sizingYarnIssueId;
    }

    public Long getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(Long weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
    }
}