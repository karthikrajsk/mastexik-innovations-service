package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "factory_beam_inward_beam_details", schema = "masters")
public class FactoryBeamInwardBeamDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private String sizedBeamNo;
    private String flangeNo;
    private Long flangeId;
    private Long actualEnds;

    @Column(name = "fabric_panel_width")
    private String fabricPanelWidth;

    @Column(name = "is_production_completed")
    private Integer isProductionCompleted = 0;

    @Column(name = "is_beam_cut")
    private Integer isBeamCut = 0;

    @Column(name = "changed_fabric_master_id")
    private Long changedFabricMasterId;

    @Column(name = "changed_fabric_code_quality", columnDefinition = "text")
    private String changedFabricCodeQuality;

    @Column(name = "warp_count")
    private String warpCount;

    @Column(name = "warp_yarn_mill_name")
    private String warpYarnMillName;

    @Column(name = "warp_yarn_lot_no")
    private String warpYarnLotNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beam_inward_id")
    private FactoryBeamInward beamInward;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public FactoryBeamInward getBeamInward() { return beamInward; }
    public void setBeamInward(FactoryBeamInward beamInward) { this.beamInward = beamInward; }

    public Long getChangedFabricMasterId() { return changedFabricMasterId; }
    public void setChangedFabricMasterId(Long changedFabricMasterId) { this.changedFabricMasterId = changedFabricMasterId; }

    public String getChangedFabricCodeQuality() { return changedFabricCodeQuality; }
    public void setChangedFabricCodeQuality(String changedFabricCodeQuality) { this.changedFabricCodeQuality = changedFabricCodeQuality; }

    public String getWarpCount() { return warpCount; }
    public void setWarpCount(String warpCount) { this.warpCount = warpCount; }

    public String getWarpYarnMillName() { return warpYarnMillName; }
    public void setWarpYarnMillName(String warpYarnMillName) { this.warpYarnMillName = warpYarnMillName; }

    public String getWarpYarnLotNo() { return warpYarnLotNo; }
    public void setWarpYarnLotNo(String warpYarnLotNo) { this.warpYarnLotNo = warpYarnLotNo; }
}
