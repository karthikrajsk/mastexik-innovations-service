package com.tech3.erp.dto;

import java.math.BigDecimal;

public class FabricCostDataDto {
    private BigDecimal sizingCostPerKg;
    private BigDecimal weavingChargesPerPick;
    private BigDecimal fabricProcessingCostPerKg;
    private BigDecimal freightCost;
    private BigDecimal valueLossInterest;
    private BigDecimal wastage;
    private BigDecimal commissionCD;
    private BigDecimal others;
    
    // NEW CALCULATED FIELDS
    private BigDecimal totalCostWithoutOverheads;
    private BigDecimal totalOverheads;
    private BigDecimal subtotal;
    private BigDecimal commissionAmount;
    private BigDecimal total;
    private BigDecimal ratePerUnit;

    // Getters and Setters for all fields
    public BigDecimal getSizingCostPerKg() { return sizingCostPerKg; }
    public void setSizingCostPerKg(BigDecimal sizingCostPerKg) { this.sizingCostPerKg = sizingCostPerKg; }

    public BigDecimal getWeavingChargesPerPick() { return weavingChargesPerPick; }
    public void setWeavingChargesPerPick(BigDecimal weavingChargesPerPick) { this.weavingChargesPerPick = weavingChargesPerPick; }

    public BigDecimal getFabricProcessingCostPerKg() { return fabricProcessingCostPerKg; }
    public void setFabricProcessingCostPerKg(BigDecimal fabricProcessingCostPerKg) { this.fabricProcessingCostPerKg = fabricProcessingCostPerKg; }

    public BigDecimal getFreightCost() { return freightCost; }
    public void setFreightCost(BigDecimal freightCost) { this.freightCost = freightCost; }

    public BigDecimal getValueLossInterest() { return valueLossInterest; }
    public void setValueLossInterest(BigDecimal valueLossInterest) { this.valueLossInterest = valueLossInterest; }

    public BigDecimal getWastage() { return wastage; }
    public void setWastage(BigDecimal wastage) { this.wastage = wastage; }

    public BigDecimal getCommissionCD() { return commissionCD; }
    public void setCommissionCD(BigDecimal commissionCD) { this.commissionCD = commissionCD; }

    public BigDecimal getOthers() { return others; }
    public void setOthers(BigDecimal others) { this.others = others; }

    // NEW GETTERS AND SETTERS
    public BigDecimal getTotalCostWithoutOverheads() { return totalCostWithoutOverheads; }
    public void setTotalCostWithoutOverheads(BigDecimal totalCostWithoutOverheads) { this.totalCostWithoutOverheads = totalCostWithoutOverheads; }

    public BigDecimal getTotalOverheads() { return totalOverheads; }
    public void setTotalOverheads(BigDecimal totalOverheads) { this.totalOverheads = totalOverheads; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getCommissionAmount() { return commissionAmount; }
    public void setCommissionAmount(BigDecimal commissionAmount) { this.commissionAmount = commissionAmount; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(BigDecimal ratePerUnit) { this.ratePerUnit = ratePerUnit; }
}
