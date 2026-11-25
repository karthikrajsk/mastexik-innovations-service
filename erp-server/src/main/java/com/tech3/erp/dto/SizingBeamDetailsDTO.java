package com.tech3.erp.dto;

public class SizingBeamDetailsDTO {
	private Long id;
    private Long sizingPlanId;
    private Long weavingContractId;
    private String weavingContractNo;
    private Long salesOrderId;
    private String salesOrderNo;
    private Double warpMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private Long weavingVendorId;
    private Long fabricId;
    private Long beamTypeId;
    private Integer noOfBeams;
    private Integer status;
    private Integer isBeamOutward;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }


    public Double getWarpMeters() {
        return warpMeters;
    }

    public void setWarpMeters(Double warpMeters) {
        this.warpMeters = warpMeters;
    }

    public Double getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(Double shrinkage) {
        this.shrinkage = shrinkage;
    }

    public Double getExpectedFabricMeter() {
        return expectedFabricMeter;
    }

    public void setExpectedFabricMeter(Double expectedFabricMeter) {
        this.expectedFabricMeter = expectedFabricMeter;
    }

    public Long getWeavingVendorId() {
        return weavingVendorId;
    }

    public void setWeavingVendorId(Long weavingVendorId) {
        this.weavingVendorId = weavingVendorId;
    }
    
    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }

    public Long getBeamTypeId() {
        return beamTypeId;
    }

    public void setBeamTypeId(Long beamTypeId) {
        this.beamTypeId = beamTypeId;
    }

    public Integer getNoOfBeams() {
        return noOfBeams;
    }

    public void setNoOfBeams(Integer noOfBeams) {
        this.noOfBeams = noOfBeams;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsBeamOutward() {
        return isBeamOutward;
    }

    public void setIsBeamOutward(Integer isBeamOutward) {
        this.isBeamOutward = isBeamOutward;
    }
}
