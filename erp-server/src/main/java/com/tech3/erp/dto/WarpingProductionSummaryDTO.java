package com.tech3.erp.dto;

public class WarpingProductionSummaryDTO {
    private Double actualMetersAverage;
    private Double totalActualMeters;
    private Integer totalEnds;
    private Double totalNetWeight;
    private Double breakagesPerMillion;
    private Integer warpCount;

    public Double getActualMetersAverage() { return actualMetersAverage; }
    public void setActualMetersAverage(Double actualMetersAverage) { this.actualMetersAverage = actualMetersAverage; }

    public Double getTotalActualMeters() { return totalActualMeters; }
    public void setTotalActualMeters(Double totalActualMeters) { this.totalActualMeters = totalActualMeters; }

    public Integer getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Integer totalEnds) { this.totalEnds = totalEnds; }

    public Double getTotalNetWeight() { return totalNetWeight; }
    public void setTotalNetWeight(Double totalNetWeight) { this.totalNetWeight = totalNetWeight; }

    public Double getBreakagesPerMillion() { return breakagesPerMillion; }
    public void setBreakagesPerMillion(Double breakagesPerMillion) { this.breakagesPerMillion = breakagesPerMillion; }

    public Integer getWarpCount() { return warpCount; }
    public void setWarpCount(Integer warpCount) { this.warpCount = warpCount; }
}
