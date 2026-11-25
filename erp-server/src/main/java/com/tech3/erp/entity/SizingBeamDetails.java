package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sizing_beam_details", schema = "masters")
public class SizingBeamDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long weavingContractId;
    private Long salesOrderId;
    private Double warpMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private Long weavingVendorId;
    private Long fabricId;
    
    @Column(name = "beam_type_id")
    private Long beamTypeId;
    
    @Column(name = "no_of_beams")
    private Integer noOfBeams = 0;
    
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer status = 0;

    @Column(name = "is_beam_outward")
    private Integer isBeamOutward = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizing_plan_id")
    private SizingPlan sizingPlan;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return noOfBeams != null ? noOfBeams : 0;
    }

    public void setNoOfBeams(Integer noOfBeams) {
        this.noOfBeams = noOfBeams;
    }

    public SizingPlan getSizingPlan() {
        return sizingPlan;
    }

    public void setSizingPlan(SizingPlan sizingPlan) {
        this.sizingPlan = sizingPlan;
    }
    
    public Integer getStatus() {
        return status != null ? status : 0;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsBeamOutward() {
        return isBeamOutward != null ? isBeamOutward : 0;
    }

    public void setIsBeamOutward(Integer isBeamOutward) {
        this.isBeamOutward = isBeamOutward;
    }
}
