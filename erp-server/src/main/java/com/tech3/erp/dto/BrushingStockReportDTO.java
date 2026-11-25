package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BrushingStockReportDTO {
    private LocalDate date;
    private BigDecimal openingStock;
    private BigDecimal receivedMtr;
    private BigDecimal returnMtr;
    private BigDecimal dispatchMtr;
    private BigDecimal excessMtr;
    private BigDecimal shortageMtr;
    private BigDecimal closingStock;
    private Long fabricMasterId;
    private String sortNo;
    private String construction;

    public BrushingStockReportDTO() {}

    public BrushingStockReportDTO(LocalDate date, BigDecimal openingStock, BigDecimal receivedMtr, BigDecimal returnMtr, BigDecimal dispatchMtr, BigDecimal excessMtr, BigDecimal shortageMtr, BigDecimal closingStock) {
        this.date = date;
        this.openingStock = openingStock;
        this.receivedMtr = receivedMtr;
        this.returnMtr = returnMtr;
        this.dispatchMtr = dispatchMtr;
        this.excessMtr = excessMtr;
        this.shortageMtr = shortageMtr;
        this.closingStock = closingStock;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(BigDecimal openingStock) {
        this.openingStock = openingStock;
    }

    public BigDecimal getReceivedMtr() {
        return receivedMtr;
    }

    public void setReceivedMtr(BigDecimal receivedMtr) {
        this.receivedMtr = receivedMtr;
    }

    public BigDecimal getReturnMtr() {
        return returnMtr;
    }

    public void setReturnMtr(BigDecimal returnMtr) {
        this.returnMtr = returnMtr;
    }

    public BigDecimal getDispatchMtr() {
        return dispatchMtr;
    }

    public void setDispatchMtr(BigDecimal dispatchMtr) {
        this.dispatchMtr = dispatchMtr;
    }

    public BigDecimal getExcessMtr() {
        return excessMtr;
    }

    public void setExcessMtr(BigDecimal excessMtr) {
        this.excessMtr = excessMtr;
    }

    public BigDecimal getShortageMtr() {
        return shortageMtr;
    }

    public void setShortageMtr(BigDecimal shortageMtr) {
        this.shortageMtr = shortageMtr;
    }

    public BigDecimal getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(BigDecimal closingStock) {
        this.closingStock = closingStock;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }
}
