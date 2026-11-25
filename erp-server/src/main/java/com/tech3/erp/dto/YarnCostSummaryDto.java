package com.tech3.erp.dto;

import java.math.BigDecimal;

public class YarnCostSummaryDto {
    private BigDecimal totalYarnWeight;
    private BigDecimal totalYarnCost;
    private BigDecimal totalWastage;
    private BigDecimal glm;

    public YarnCostSummaryDto() {}

    public BigDecimal getTotalYarnWeight() { return totalYarnWeight; }
    public void setTotalYarnWeight(BigDecimal totalYarnWeight) { this.totalYarnWeight = totalYarnWeight; }

    public BigDecimal getTotalYarnCost() { return totalYarnCost; }
    public void setTotalYarnCost(BigDecimal totalYarnCost) { this.totalYarnCost = totalYarnCost; }

    public BigDecimal getTotalWastage() { return totalWastage; }
    public void setTotalWastage(BigDecimal totalWastage) { this.totalWastage = totalWastage; }

    public BigDecimal getGlm() { return glm; }
    public void setGlm(BigDecimal glm) { this.glm = glm; }
}