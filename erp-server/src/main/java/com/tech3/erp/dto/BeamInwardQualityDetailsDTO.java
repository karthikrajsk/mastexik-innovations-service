package com.tech3.erp.dto;

public class BeamInwardQualityDetailsDTO {
	
    private Long id;
    private Long beamInwardId;
    private String quality;
    private Long yarnId;
    private Long sordEnds;
    private Long actualEnds;
    private Long parts;
    private Long endsPerPart;
    private Long wrapMeters;
    private String fabricPanelWidth;

    // Additional fields required by frontend
    private String fabricCode;
    private Long actualYarn;
    private Long warpEnds;
    private Long actualWarpEnds;
    private Long totalEnds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBeamInwardId() {
        return beamInwardId;
    }

    public void setBeamInwardId(Long beamInwardId) {
        this.beamInwardId = beamInwardId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
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

    public Long getWrapMeters() {
        return wrapMeters;
    }

    public void setWrapMeters(Long wrapMeters) {
        this.wrapMeters = wrapMeters;
    }

    public String getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(String fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public Long getActualYarn() {
        return actualYarn;
    }

    public void setActualYarn(Long actualYarn) {
        this.actualYarn = actualYarn;
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

    public Long getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Long totalEnds) {
        this.totalEnds = totalEnds;
    }
}

