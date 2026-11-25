package com.tech3.erp.dto;

public class SizingProductionSummaryDTO {
    private Integer noOfBeams;
    private Double totalMeters;
    private Double totalNetWeight;
    private Integer sizingCount;

    public Integer getNoOfBeams() { return noOfBeams; }
    public void setNoOfBeams(Integer noOfBeams) { this.noOfBeams = noOfBeams; }

    public Double getTotalMeters() { return totalMeters; }
    public void setTotalMeters(Double totalMeters) { this.totalMeters = totalMeters; }

    public Double getTotalNetWeight() { return totalNetWeight; }
    public void setTotalNetWeight(Double totalNetWeight) { this.totalNetWeight = totalNetWeight; }

    public Integer getSizingCount() { return sizingCount; }
    public void setSizingCount(Integer sizingCount) { this.sizingCount = sizingCount; }
}
