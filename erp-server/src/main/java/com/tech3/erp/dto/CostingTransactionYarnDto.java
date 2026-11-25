package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CostingTransactionYarnDto {

    private Long id;

    @NotNull(message = "Warp weft is required")
    private String warpWeft;

    @NotNull(message = "Yarn count is required")
    private Integer yarnCount;

    @NotNull(message = "Rate per unit is required")
    @DecimalMin(value = "0.00", message = "Rate per unit cannot be negative")
    private BigDecimal ratePerUnit;

    @NotNull(message = "Yarn processing cost is required")
    @DecimalMin(value = "0.00", message = "Yarn processing cost cannot be negative")
    private BigDecimal yarnProcessingCost;

    @Size(max = 100, message = "Content must be up to 100 characters")
    private String content;

    @Size(max = 100, message = "Yarn type must be up to 100 characters")
    private String yarnType;

    @DecimalMin(value = "0.00", message = "EPI cannot be negative")
    private BigDecimal epi;

    @DecimalMin(value = "0.00", message = "PPI cannot be negative")
    private BigDecimal ppi;

    private boolean activeFlag = true;
    
    // NEW CALCULATED FIELDS
    private BigDecimal calculatedWeight;
    private BigDecimal calculatedCost;
    private BigDecimal calculatedWastage;
    private BigDecimal wastagePercent;

    // Constructors
    public CostingTransactionYarnDto() {}

    // Existing Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWarpWeft() { return warpWeft; }
    public void setWarpWeft(String warpWeft) { this.warpWeft = warpWeft; }

    public Integer getYarnCount() { return yarnCount; }
    public void setYarnCount(Integer yarnCount) { this.yarnCount = yarnCount; }

    public BigDecimal getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(BigDecimal ratePerUnit) { this.ratePerUnit = ratePerUnit; }

    public BigDecimal getYarnProcessingCost() { return yarnProcessingCost; }
    public void setYarnProcessingCost(BigDecimal yarnProcessingCost) { this.yarnProcessingCost = yarnProcessingCost; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getYarnType() { return yarnType; }
    public void setYarnType(String yarnType) { this.yarnType = yarnType; }

    public BigDecimal getEpi() { return epi; }
    public void setEpi(BigDecimal epi) { this.epi = epi; }

    public BigDecimal getPpi() { return ppi; }
    public void setPpi(BigDecimal ppi) { this.ppi = ppi; }

    public boolean isActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }

    // NEW GETTERS AND SETTERS
    public BigDecimal getCalculatedWeight() { return calculatedWeight; }
    public void setCalculatedWeight(BigDecimal calculatedWeight) { this.calculatedWeight = calculatedWeight; }

    public BigDecimal getCalculatedCost() { return calculatedCost; }
    public void setCalculatedCost(BigDecimal calculatedCost) { this.calculatedCost = calculatedCost; }

    public BigDecimal getCalculatedWastage() { return calculatedWastage; }
    public void setCalculatedWastage(BigDecimal calculatedWastage) { this.calculatedWastage = calculatedWastage; }

    public BigDecimal getWastagePercent() { return wastagePercent; }
    public void setWastagePercent(BigDecimal wastagePercent) { this.wastagePercent = wastagePercent; }
}