package com.tech3.erp.dto;

import com.tech3.erp.entity.SalesYarnReturn;
import java.math.BigDecimal;

public class SalesYarnReturnDTO {
    private Long id;
    private String yarnName;
    private BigDecimal availableQty;
    private BigDecimal issuedQty;
    private String lotNo;
    private Long warehouseId;
    private BigDecimal noOfBags;
    private BigDecimal issuedBags;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private Long millId;

    public SalesYarnReturnDTO() {}

    public SalesYarnReturnDTO(SalesYarnReturn entity) {
        this.id = entity.getId();
        this.yarnName = entity.getYarnName();
        this.availableQty = entity.getAvailableQty();
        this.issuedQty = entity.getIssuedQty();
        this.lotNo = entity.getLotNo();
        this.warehouseId = entity.getWarehouseId();
        this.noOfBags = entity.getNoOfBags();
        this.issuedBags = entity.getIssuedBags();
        this.conesPerBag = entity.getConesPerBag();
        this.conesWeight = entity.getConesWeight();
        this.millId = entity.getMillId();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }

    public BigDecimal getAvailableQty() { return availableQty; }
    public void setAvailableQty(BigDecimal availableQty) { this.availableQty = availableQty; }

    public BigDecimal getIssuedQty() { return issuedQty; }
    public void setIssuedQty(BigDecimal issuedQty) { this.issuedQty = issuedQty; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public BigDecimal getNoOfBags() { return noOfBags; }
    public void setNoOfBags(BigDecimal noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getIssuedBags() { return issuedBags; }
    public void setIssuedBags(BigDecimal issuedBags) { this.issuedBags = issuedBags; }

    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }

    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }
}
