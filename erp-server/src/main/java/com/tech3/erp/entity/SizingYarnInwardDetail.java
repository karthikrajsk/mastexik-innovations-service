package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "sizing_yarn_inward_detail", schema = "masters")
public class SizingYarnInwardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizing_yarn_inward_id")
    private SizingYarnInward sizingYarnInward;

    @Column(name = "yarn_name", length = 100)
    private String yarnName;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag", precision = 38, scale = 3)
    private BigDecimal kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "issue_qty", precision = 38, scale = 3)
    private BigDecimal issueQty;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SizingYarnInward getSizingYarnInward() {
        return sizingYarnInward;
    }

    public void setSizingYarnInward(SizingYarnInward sizingYarnInward) {
        this.sizingYarnInward = sizingYarnInward;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }
}
