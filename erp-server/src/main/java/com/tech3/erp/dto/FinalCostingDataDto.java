package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinalCostingDataDto {
    private BigDecimal marginPercent;
    private BigDecimal orderQty;
    
    // NEW CALCULATED FIELDS
    private BigDecimal targetPrice;
    private BigDecimal weavingChargesAsPerTP;
    private List<YarnRequirementDTO> warpRequirements;  // Changed to YarnRequirementDTO
    private List<YarnRequirementDTO> weftRequirements;  // Changed to YarnRequirementDTO
    private BigDecimal coverFactor;

    // Getters and Setters
    public BigDecimal getMarginPercent() { return marginPercent; }
    public void setMarginPercent(BigDecimal marginPercent) { this.marginPercent = marginPercent; }

    public BigDecimal getOrderQty() { return orderQty; }
    public void setOrderQty(BigDecimal orderQty) { this.orderQty = orderQty; }

    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }

    public BigDecimal getWeavingChargesAsPerTP() { return weavingChargesAsPerTP; }
    public void setWeavingChargesAsPerTP(BigDecimal weavingChargesAsPerTP) { this.weavingChargesAsPerTP = weavingChargesAsPerTP; }

    public List<YarnRequirementDTO> getWarpRequirements() { return warpRequirements; }  // Changed to YarnRequirementDTO
    public void setWarpRequirements(List<YarnRequirementDTO> warpRequirements) { this.warpRequirements = warpRequirements; }  // Changed to YarnRequirementDTO

    public List<YarnRequirementDTO> getWeftRequirements() { return weftRequirements; }  // Changed to YarnRequirementDTO
    public void setWeftRequirements(List<YarnRequirementDTO> weftRequirements) { this.weftRequirements = weftRequirements; }  // Changed to YarnRequirementDTO

    public BigDecimal getCoverFactor() { return coverFactor; }
    public void setCoverFactor(BigDecimal coverFactor) { this.coverFactor = coverFactor; }
}