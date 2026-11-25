package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LatestPurchaseSummaryDTO {

    private LocalDate lastPurchaseDate;
    private String lastPoNumber;
    private Long lastPoId;
    private Long lastVendorId;
    private String lastVendorName;
    private BigDecimal lastPurchaseQuantity;
    private String lastUom;
    private BigDecimal lastPurchaseUnitPrice;
    private BigDecimal lastGstAmount;
    private BigDecimal lastNetAmount;
    private BigDecimal lastFinalAmount;

    public LatestPurchaseSummaryDTO() {}

    public LatestPurchaseSummaryDTO(LocalDate lastPurchaseDate, String lastPoNumber, Long lastPoId,
                                    Long lastVendorId, String lastVendorName,
                                    BigDecimal lastPurchaseQuantity, String lastUom,
                                    BigDecimal lastPurchaseUnitPrice, BigDecimal lastGstAmount,
                                    BigDecimal lastNetAmount, BigDecimal lastFinalAmount) {
        this.lastPurchaseDate = lastPurchaseDate;
        this.lastPoNumber = lastPoNumber;
        this.lastPoId = lastPoId;
        this.lastVendorId = lastVendorId;
        this.lastVendorName = lastVendorName;
        this.lastPurchaseQuantity = lastPurchaseQuantity;
        this.lastUom = lastUom;
        this.lastPurchaseUnitPrice = lastPurchaseUnitPrice;
        this.lastGstAmount = lastGstAmount;
        this.lastNetAmount = lastNetAmount;
        this.lastFinalAmount = lastFinalAmount;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public String getLastPoNumber() {
        return lastPoNumber;
    }

    public void setLastPoNumber(String lastPoNumber) {
        this.lastPoNumber = lastPoNumber;
    }

    public Long getLastPoId() {
        return lastPoId;
    }

    public void setLastPoId(Long lastPoId) {
        this.lastPoId = lastPoId;
    }

    public Long getLastVendorId() {
        return lastVendorId;
    }

    public void setLastVendorId(Long lastVendorId) {
        this.lastVendorId = lastVendorId;
    }

    public String getLastVendorName() {
        return lastVendorName;
    }

    public void setLastVendorName(String lastVendorName) {
        this.lastVendorName = lastVendorName;
    }

    public BigDecimal getLastPurchaseQuantity() {
        return lastPurchaseQuantity;
    }

    public void setLastPurchaseQuantity(BigDecimal lastPurchaseQuantity) {
        this.lastPurchaseQuantity = lastPurchaseQuantity;
    }

    public String getLastUom() {
        return lastUom;
    }

    public void setLastUom(String lastUom) {
        this.lastUom = lastUom;
    }

    public BigDecimal getLastPurchaseUnitPrice() {
        return lastPurchaseUnitPrice;
    }

    public void setLastPurchaseUnitPrice(BigDecimal lastPurchaseUnitPrice) {
        this.lastPurchaseUnitPrice = lastPurchaseUnitPrice;
    }

    public BigDecimal getLastGstAmount() {
        return lastGstAmount;
    }

    public void setLastGstAmount(BigDecimal lastGstAmount) {
        this.lastGstAmount = lastGstAmount;
    }

    public BigDecimal getLastNetAmount() {
        return lastNetAmount;
    }

    public void setLastNetAmount(BigDecimal lastNetAmount) {
        this.lastNetAmount = lastNetAmount;
    }

    public BigDecimal getLastFinalAmount() {
        return lastFinalAmount;
    }

    public void setLastFinalAmount(BigDecimal lastFinalAmount) {
        this.lastFinalAmount = lastFinalAmount;
    }
}
