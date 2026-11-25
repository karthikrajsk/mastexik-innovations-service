package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class FabricCostData {

    @Column(name = "sizing_cost_per_kg", precision = 10, scale = 2)
    private BigDecimal sizingCostPerKg;

    @Column(name = "weaving_charges_per_pick", precision = 10, scale = 2)
    private BigDecimal weavingChargesPerPick;

    @Column(name = "fabric_processing_cost_per_kg", precision = 10, scale = 2)
    private BigDecimal fabricProcessingCostPerKg;

    @Column(name = "freight_cost", precision = 10, scale = 2)
    private BigDecimal freightCost;

    @Column(name = "value_loss_interest", precision = 10, scale = 2)
    private BigDecimal valueLossInterest;

    @Column(name = "wastage", precision = 10, scale = 2)
    private BigDecimal wastage;

    @Column(name = "commission_cd", precision = 10, scale = 2)
    private BigDecimal commissionCD;

    @Column(name = "others", precision = 10, scale = 2)
    private BigDecimal others;
    
    // NEW CALCULATED FIELDS
    @Column(name = "total_cost_without_overheads", precision = 10, scale = 2)
    private BigDecimal totalCostWithoutOverheads;
    
    @Column(name = "total_overheads", precision = 10, scale = 2)
    private BigDecimal totalOverheads;
    
    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    @Column(name = "commission_amount", precision = 10, scale = 2)
    private BigDecimal commissionAmount;
    
    @Column(name = "fabric_total", precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "rate_per_unit", precision = 10, scale = 2)
    private BigDecimal ratePerUnit;

    // Constructors
    public FabricCostData() {}

    // All Getters and Setters
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
