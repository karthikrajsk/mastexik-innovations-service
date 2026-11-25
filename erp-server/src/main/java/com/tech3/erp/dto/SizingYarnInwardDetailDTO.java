package com.tech3.erp.dto;

import com.tech3.erp.entity.SizingYarnInwardDetail;
import java.math.BigDecimal;

public class SizingYarnInwardDetailDTO {

    private Long id;
    private String yarnName;
    private Long yarnId;
    private Integer noOfBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private Long warehouseId;
    private Long millId;
    private BigDecimal issueQty;
    private String lotNo;

    public SizingYarnInwardDetailDTO() {
    }

    public SizingYarnInwardDetailDTO(SizingYarnInwardDetail detail) {
        this.id = detail.getId();
        this.yarnName = detail.getYarnName();
        this.yarnId = detail.getYarnId();
        this.noOfBags = detail.getNoOfBags();
        this.kgsPerBag = detail.getKgsPerBag();
        this.conesPerBag = detail.getConesPerBag();
        this.conesWeight = detail.getConesWeight();
        this.warehouseId = detail.getWarehouseId();
        this.millId = detail.getMillId();
        this.issueQty = detail.getIssueQty();
        this.lotNo = detail.getLotNo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
