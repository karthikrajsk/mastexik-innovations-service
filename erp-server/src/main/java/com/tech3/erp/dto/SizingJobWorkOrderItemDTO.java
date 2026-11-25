package com.tech3.erp.dto;

public class SizingJobWorkOrderItemDTO {
    private Long id;
    private Long planDetailId;
    private Long yarnId;
    private String yarnName;
    private Double totalEnds;
    private Double numberOfParts;
    private Double endsPerPart;
    private Double warpMeters;
    private Boolean activeFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(Long planDetailId) {
        this.planDetailId = planDetailId;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Double getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Double totalEnds) {
        this.totalEnds = totalEnds;
    }

    public Double getNumberOfParts() {
        return numberOfParts;
    }

    public void setNumberOfParts(Double numberOfParts) {
        this.numberOfParts = numberOfParts;
    }

    public Double getEndsPerPart() {
        return endsPerPart;
    }

    public void setEndsPerPart(Double endsPerPart) {
        this.endsPerPart = endsPerPart;
    }

    public Double getWarpMeters() {
        return warpMeters;
    }

    public void setWarpMeters(Double warpMeters) {
        this.warpMeters = warpMeters;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
