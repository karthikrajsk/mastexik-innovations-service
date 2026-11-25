package com.tech3.erp.dto;

import com.tech3.erp.entity.YarnItemEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YarnItemDto {
    private Long id;
    private Long openingBalanceId;
    @JsonProperty("sNo")
    private Integer sNo;
    @JsonProperty("yarnId")
    private Long yarnId;
    @JsonProperty("yarnName")
    private String yarnName;
    @JsonProperty("millId")
    private Long millId;
    private String millName;
    @JsonProperty("noOfBags")
    private Integer noOfBags;
    @JsonProperty("kgsPerBag")
    private BigDecimal kgsPerBag;
    @JsonProperty("totalKg")
    private BigDecimal totalKg;
    @JsonProperty("lotNumber")
    private String lotNumber;
     @JsonProperty("uomId")
    private Long uomId;
     @JsonProperty("yarnTypeId")
     private Long yarnTypeId;
     @JsonProperty("conesPerBag")
     private BigDecimal conesPerBag;
     @JsonProperty("conesWeight")
     private BigDecimal conesWeight;

    public YarnItemDto() {}

    // ENTITY → DTO
    public YarnItemDto(YarnItemEntity entity) {
        this.id = entity.getId();
        this.openingBalanceId = entity.getOpeningBalanceId();
        this.sNo = entity.getSNo();
        this.yarnId = entity.getYarnId();
        this.millId = entity.getMillId();
        this.noOfBags = entity.getNoOfBags();
        this.kgsPerBag = entity.getKgsPerBag();
        this.totalKg = entity.getTotalKg();
        this.lotNumber = entity.getLotNumber();
        this.uomId = entity.getUomId();
        this.yarnTypeId = entity.getYarnTypeId();
        this.conesPerBag = entity.getConesPerBag();
        this.conesWeight = entity.getConesWeight();
        if (entity.getYarnName() != null) {
            this.yarnName = entity.getYarnName().getYarnName();
        }
        if (entity.getMillName() != null) {
            this.millName = entity.getMillName().getSubCategoryName();
        }
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOpeningBalanceId() { return openingBalanceId; }
    public void setOpeningBalanceId(Long openingBalanceId) { this.openingBalanceId = openingBalanceId; }
    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }
    public Long getYarnId() { return yarnId; }
    public void setYarnId(Long yarnId) { this.yarnId = yarnId; }
    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }
    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }
    public String getMillName() { return millName; }
    public void setMillName(String millName) { this.millName = millName; }
    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }
    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }
    public BigDecimal getTotalKg() { return totalKg; }
    public void setTotalKg(BigDecimal totalKg) { this.totalKg = totalKg; }
    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }
    public Long getYarnTypeId() { return yarnTypeId; }
    public void setYarnTypeId(Long yarnTypeId) { this.yarnTypeId = yarnTypeId; }
    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }
    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }
}