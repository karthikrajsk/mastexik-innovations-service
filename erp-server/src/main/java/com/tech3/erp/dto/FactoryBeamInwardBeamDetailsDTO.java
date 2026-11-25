package com.tech3.erp.dto;

public class FactoryBeamInwardBeamDetailsDTO {
    private Long id;
    private Long beamInwardId;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private String sizedBeamNo;
    private String flangeNo;
    private Long flangeId;
    private Long actualEnds;
    private String fabricPanelWidth;
    private Integer isProductionCompleted;
    private Integer isBeamCut;
    private Double netWeight;
    private String warpCount;
    private String warpYarnMillName;
    private String warpYarnLotNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBeamInwardId() { return beamInwardId; }
    public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }

    public Double getWrapMeters() { return wrapMeters; }
    public void setWrapMeters(Double wrapMeters) { this.wrapMeters = wrapMeters; }

    public Double getShrinkage() { return shrinkage; }
    public void setShrinkage(Double shrinkage) { this.shrinkage = shrinkage; }

    public Double getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(Double expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

    public String getSizedBeamNo() { return sizedBeamNo; }
    public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }

    public Long getActualEnds() { return actualEnds; }
    public void setActualEnds(Long actualEnds) { this.actualEnds = actualEnds; }

    public String getFabricPanelWidth() { return fabricPanelWidth; }
    public void setFabricPanelWidth(String fabricPanelWidth) { this.fabricPanelWidth = fabricPanelWidth; }

    public Integer getIsProductionCompleted() { return isProductionCompleted; }
    public void setIsProductionCompleted(Integer isProductionCompleted) { this.isProductionCompleted = isProductionCompleted; }

    public Integer getIsBeamCut() { return isBeamCut; }
    public void setIsBeamCut(Integer isBeamCut) { this.isBeamCut = isBeamCut; }

    public Double getNetWeight() { return netWeight; }
    public void setNetWeight(Double netWeight) { this.netWeight = netWeight; }

    public String getWarpCount() { return warpCount; }
    public void setWarpCount(String warpCount) { this.warpCount = warpCount; }

    public String getWarpYarnMillName() { return warpYarnMillName; }
    public void setWarpYarnMillName(String warpYarnMillName) { this.warpYarnMillName = warpYarnMillName; }

    public String getWarpYarnLotNo() { return warpYarnLotNo; }
    public void setWarpYarnLotNo(String warpYarnLotNo) { this.warpYarnLotNo = warpYarnLotNo; }
}
