package com.tech3.erp.dto;

public class SizingBeamOutwardSummaryDTO {
    private Integer noOfBeams;
    private Double totalBeamMeters;
    private Integer totalEnds;

    public Integer getNoOfBeams() {
        return noOfBeams;
    }

    public void setNoOfBeams(Integer noOfBeams) {
        this.noOfBeams = noOfBeams;
    }

    public Double getTotalBeamMeters() {
        return totalBeamMeters;
    }

    public void setTotalBeamMeters(Double totalBeamMeters) {
        this.totalBeamMeters = totalBeamMeters;
    }

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }
}
