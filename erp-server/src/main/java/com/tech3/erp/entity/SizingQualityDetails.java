package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sizing_quality_details", schema = "masters")
public class SizingQualityDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quality;
    private String fabricCode;
    private Long fabricId;
    private Long yarnId;
    private Long actualYarn;
    private Long sordEnds;
    private Long actualEnds;
    private Long warpEnds;
    private Long actualWarpEnds;
    private Long parts;
    private Long endsPerPart;
    private Long warpMeters;
    private String fabricPanelWidth;
    
    @Column(name = "no_of_creels")
    private Integer noOfCreels;

    @Column(name = "no_req_cones")
    private Long noReqCones;

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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Long getActualYarn() {
        return actualYarn;
    }

    public void setActualYarn(Long actualYarn) {
        this.actualYarn = actualYarn;
    }

    public Long getSordEnds() {
        return sordEnds;
    }

    public void setSordEnds(Long sordEnds) {
        this.sordEnds = sordEnds;
    }

    public Long getActualEnds() {
        return actualEnds;
    }

    public void setActualEnds(Long actualEnds) {
        this.actualEnds = actualEnds;
    }

    public Long getWarpEnds() {
        return warpEnds;
    }

    public void setWarpEnds(Long warpEnds) {
        this.warpEnds = warpEnds;
    }

    public Long getActualWarpEnds() {
        return actualWarpEnds;
    }

    public void setActualWarpEnds(Long actualWarpEnds) {
        this.actualWarpEnds = actualWarpEnds;
    }

    public Long getParts() {
        return parts;
    }

    public void setParts(Long parts) {
        this.parts = parts;
    }

    public Long getEndsPerPart() {
        return endsPerPart;
    }

    public void setEndsPerPart(Long endsPerPart) {
        this.endsPerPart = endsPerPart;
    }

    public Long getWarpMeters() {
        return warpMeters;
    }

    public void setWarpMeters(Long warpMeters) {
        this.warpMeters = warpMeters;
    }

    public String getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(String fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public SizingPlan getSizingPlan() {
        return sizingPlan;
    }

    public void setSizingPlan(SizingPlan sizingPlan) {
        this.sizingPlan = sizingPlan;
    }

    public Integer getNoOfCreels() {
        return noOfCreels;
    }

    public void setNoOfCreels(Integer noOfCreels) {
        this.noOfCreels = noOfCreels;
    }

    public Long getNoReqCones() {
        return noReqCones;
    }

    public void setNoReqCones(Long noReqCones) {
        this.noReqCones = noReqCones;
    }
}