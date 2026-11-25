package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class FinalCostingData {

    @Column(name = "margin_percent", precision = 10, scale = 2)
    private BigDecimal marginPercent;

    @Column(name = "order_qty", precision = 10, scale = 2)
    private BigDecimal orderQty;
    
    // NEW CALCULATED FIELDS
    @Column(name = "target_price", precision = 10, scale = 2)
    private BigDecimal targetPrice;
    
    @Column(name = "weaving_charges_as_per_tp", precision = 10, scale = 2)
    private BigDecimal weavingChargesAsPerTP;
    
    @Column(name = "cover_factor", precision = 10, scale = 2)
    private BigDecimal coverFactor;

    // Constructors
    public FinalCostingData() {}

    // Getters and Setters
    public BigDecimal getMarginPercent() { return marginPercent; }
    public void setMarginPercent(BigDecimal marginPercent) { this.marginPercent = marginPercent; }

    public BigDecimal getOrderQty() { return orderQty; }
    public void setOrderQty(BigDecimal orderQty) { this.orderQty = orderQty; }

    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }

    public BigDecimal getWeavingChargesAsPerTP() { return weavingChargesAsPerTP; }
    public void setWeavingChargesAsPerTP(BigDecimal weavingChargesAsPerTP) { this.weavingChargesAsPerTP = weavingChargesAsPerTP; }

    public BigDecimal getCoverFactor() { return coverFactor; }
    public void setCoverFactor(BigDecimal coverFactor) { this.coverFactor = coverFactor; }
}