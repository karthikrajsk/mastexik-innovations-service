package com.tech3.erp.dto;

import java.math.BigDecimal;

public class FinishYarnReceiveItemDTO {
    private Long id;
    private String finishedYarnCode;
    private Integer noOfBags;
    private Double kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private Double totalKgs;
    private String lotNo;
    private Long warehouseId;
    private Boolean activeFlag;
 private Long finishedYarnItemId;
  private String finishedYarn;
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFinishedYarnCode() { return finishedYarnCode; }
    public void setFinishedYarnCode(String finishedYarnCode) { this.finishedYarnCode = finishedYarnCode; }
    
    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }
    
    public Double getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(Double kgsPerBag) { this.kgsPerBag = kgsPerBag; }
    
    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }
    
    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }
    
    public Double getTotalKgs() { return totalKgs; }
    public void setTotalKgs(Double totalKgs) { this.totalKgs = totalKgs; }
    
    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }
    
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public String getFinishedYarn() { return finishedYarn; }
    public void setFinishedYarn(String finishedYarn) { this.finishedYarn = finishedYarn; }

     public Long getFinishedYarnItemId() { return finishedYarnItemId; }
    public void setFinishedYarnItemId(Long finishedYarnItemId) { this.finishedYarnItemId = finishedYarnItemId; }
}