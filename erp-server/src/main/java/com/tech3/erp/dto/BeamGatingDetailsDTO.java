package com.tech3.erp.dto;

public class BeamGatingDetailsDTO {
    private Long id;
    private String beamNo;
    private Long flangeId;
    private String flangeNo;
    private Long beamId;
    private Long fabricMasterId;
    private String fabricCodeQuality;
    private Long weavingContractId;
    private Long weavingWorkOrderId;
    private Long orderTypeId;
    private String weavingContractNo;
    private String weavingWorkOrderNo;
    private Double beamMeter;
    private Double onLoomMtr;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private Double gatingPicks;
    private Double remainingMeters;
    private Integer isProductionCompleted;
    private Integer isBeamCut;
    private Integer isDoffedBeam;
    private Long changedFabricMasterId;
    private String changedFabricCodeQuality;
    private String sizingSetNo;
    private String warpCount;
    private String warpYarnMillName;
    private String warpYarnLotNo;
    private String weftCount;
    private String weftYarnMillName;
    private String weftYarnLotNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBeamNo() { return beamNo; }
    public void setBeamNo(String beamNo) { this.beamNo = beamNo; }
    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }
    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }
    public Long getBeamId() { return beamId; }
    public void setBeamId(Long beamId) { this.beamId = beamId; }
    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }
    public String getFabricCodeQuality() { return fabricCodeQuality; }
    public void setFabricCodeQuality(String fabricCodeQuality) { this.fabricCodeQuality = fabricCodeQuality; }
    public Long getWeavingContractId() { return weavingContractId; }
    public void setWeavingContractId(Long weavingContractId) { this.weavingContractId = weavingContractId; }
    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }
    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }
    public String getWeavingContractNo() { return weavingContractNo; }
    public void setWeavingContractNo(String weavingContractNo) { this.weavingContractNo = weavingContractNo; }
    public String getWeavingWorkOrderNo() { return weavingWorkOrderNo; }
    public void setWeavingWorkOrderNo(String weavingWorkOrderNo) { this.weavingWorkOrderNo = weavingWorkOrderNo; }
    public Double getBeamMeter() { return beamMeter; }
    public void setBeamMeter(Double beamMeter) { this.beamMeter = beamMeter; }
    public Double getOnLoomMtr() { return onLoomMtr; }
    public void setOnLoomMtr(Double onLoomMtr) { this.onLoomMtr = onLoomMtr; }
    public Double getShrinkage() { return shrinkage; }
    public void setShrinkage(Double shrinkage) { this.shrinkage = shrinkage; }
    public Double getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(Double expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }
    public Double getGatingPicks() { return gatingPicks; }
    public void setGatingPicks(Double gatingPicks) { this.gatingPicks = gatingPicks; }
    public Double getRemainingMeters() { return remainingMeters; }
    public void setRemainingMeters(Double remainingMeters) { this.remainingMeters = remainingMeters; }
    public Integer getIsProductionCompleted() { return isProductionCompleted; }
    public void setIsProductionCompleted(Integer isProductionCompleted) { this.isProductionCompleted = isProductionCompleted; }
    public Integer getIsBeamCut() { return isBeamCut; }
    public void setIsBeamCut(Integer isBeamCut) { this.isBeamCut = isBeamCut; }

    public Integer getIsDoffedBeam() { return isDoffedBeam; }
    public void setIsDoffedBeam(Integer isDoffedBeam) { this.isDoffedBeam = isDoffedBeam; }

    public Long getChangedFabricMasterId() { return changedFabricMasterId; }
    public void setChangedFabricMasterId(Long changedFabricMasterId) { this.changedFabricMasterId = changedFabricMasterId; }

    public String getChangedFabricCodeQuality() { return changedFabricCodeQuality; }
    public void setChangedFabricCodeQuality(String changedFabricCodeQuality) { this.changedFabricCodeQuality = changedFabricCodeQuality; }

    public String getSizingSetNo() { return sizingSetNo; }
    public void setSizingSetNo(String sizingSetNo) { this.sizingSetNo = sizingSetNo; }

    public String getWarpCount() { return warpCount; }
    public void setWarpCount(String warpCount) { this.warpCount = warpCount; }

    public String getWarpYarnMillName() { return warpYarnMillName; }
    public void setWarpYarnMillName(String warpYarnMillName) { this.warpYarnMillName = warpYarnMillName; }

    public String getWarpYarnLotNo() { return warpYarnLotNo; }
    public void setWarpYarnLotNo(String warpYarnLotNo) { this.warpYarnLotNo = warpYarnLotNo; }

    public String getWeftCount() { return weftCount; }
    public void setWeftCount(String weftCount) { this.weftCount = weftCount; }

    public String getWeftYarnMillName() { return weftYarnMillName; }
    public void setWeftYarnMillName(String weftYarnMillName) { this.weftYarnMillName = weftYarnMillName; }

    public String getWeftYarnLotNo() { return weftYarnLotNo; }
    public void setWeftYarnLotNo(String weftYarnLotNo) { this.weftYarnLotNo = weftYarnLotNo; }
}
