package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BrushingMovementDTO {
    private LocalDate periodDate;
    private BigDecimal receivedMtr;
    private BigDecimal returnMtr;
    private BigDecimal dispatchMtr;
    private BigDecimal excessMtr;
    private BigDecimal shortageMtr;

    public BrushingMovementDTO() {}

    public BrushingMovementDTO(LocalDate periodDate, BigDecimal receivedMtr, BigDecimal returnMtr, BigDecimal dispatchMtr, BigDecimal excessMtr, BigDecimal shortageMtr) {
        this.periodDate = periodDate;
        this.receivedMtr = receivedMtr;
        this.returnMtr = returnMtr;
        this.dispatchMtr = dispatchMtr;
        this.excessMtr = excessMtr;
        this.shortageMtr = shortageMtr;
    }

    public LocalDate getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(LocalDate periodDate) {
        this.periodDate = periodDate;
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
}
