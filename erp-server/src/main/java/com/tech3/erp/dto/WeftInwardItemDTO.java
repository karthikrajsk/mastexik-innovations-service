package com.tech3.erp.dto;

import com.tech3.erp.entity.WeftInwardItem;

public class WeftInwardItemDTO {
    private Long id;
    private Long yarnId;
    private String yarnName;
    private Long actualYarnId;
    private String actualYarn;
    private Integer bags;
    private Double kgsPerBag;
    private Double totalKgs;
    private Long millId;
    private String millName;
    private Long warehouseId;
    private String lotNo;

    public WeftInwardItemDTO() {}

    public WeftInwardItemDTO(WeftInwardItem item) {
        this.id = item.getId();
        this.yarnId = item.getYarnId();
        this.yarnName = item.getYarnName();
        this.actualYarnId = item.getActualYarnId() != null ? item.getActualYarnId() : item.getYarnId();
        this.actualYarn = item.getActualYarn() != null ? item.getActualYarn() : item.getYarnName();
        this.bags = item.getNoOfBags();
        this.kgsPerBag = item.getKgsPerBag();
        this.totalKgs = item.getTotalKgs();
        this.millId = item.getMillId();
        this.millName = item.getMillName();
        this.warehouseId = item.getWarehouseId();
        this.lotNo = item.getLotNo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getBags() {
        return bags;
    }

    public void setBags(Integer bags) {
        this.bags = bags;
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
}
