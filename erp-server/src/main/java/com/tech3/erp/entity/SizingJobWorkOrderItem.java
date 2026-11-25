package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sizing_job_work_order_item", schema = "masters")
public class SizingJobWorkOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sizing_job_work_order_id")
    @JsonBackReference
    private SizingJobWorkOrder sizingJobWorkOrder;

    @Column(name = "plan_detail_id")
    private Long planDetailId;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "total_ends")
    private Double totalEnds;

    @Column(name = "number_of_parts")
    private Double numberOfParts;

    @Column(name = "ends_per_part")
    private Double endsPerPart;

    @Column(name = "warp_meters")
    private Double warpMeters;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SizingJobWorkOrder getSizingJobWorkOrder() {
        return sizingJobWorkOrder;
    }

    public void setSizingJobWorkOrder(SizingJobWorkOrder sizingJobWorkOrder) {
        this.sizingJobWorkOrder = sizingJobWorkOrder;
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
