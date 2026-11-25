package com.tech3.erp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "weft_inward_item", schema = "masters")
public class WeftInwardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weft_inward_id", nullable = false)
    @JsonBackReference
    private WeftInward weftInward;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "actual_yarn_id")
    private Long actualYarnId;

    @Column(name = "actual_yarn")
    private String actualYarn;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag")
    private Double kgsPerBag;

    @Column(name = "total_kgs")
    private Double totalKgs;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "mill_name")
    private String millName;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeftInward getWeftInward() {
        return weftInward;
    }

    public void setWeftInward(WeftInward weftInward) {
        this.weftInward = weftInward;
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

    public String getActualYarn() {
        return actualYarn;
    }

    public void setActualYarn(String actualYarn) {
        this.actualYarn = actualYarn;
    }

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    public Double getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(Double kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public Double getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(Double totalKgs) {
        this.totalKgs = totalKgs;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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
}
