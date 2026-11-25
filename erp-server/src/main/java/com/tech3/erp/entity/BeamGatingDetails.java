package com.tech3.erp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "beam_gating_details", schema = "masters")
public class BeamGatingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beam_gating_id")
    @JsonBackReference
    private BeamGating beamGating;

    @Column(name = "beam_no")
    private String beamNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "beam_id")
    private Long beamId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "fabric_code_quality", columnDefinition = "text")
    private String fabricCodeQuality;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_work_order_id")
    private Long weavingWorkOrderId;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "weaving_contract_no")
    private String weavingContractNo;

    @Column(name = "weaving_work_order_no")
    private String weavingWorkOrderNo;

    @Column(name = "beam_meter")
    private Double beamMeter;

    @Column(name = "on_loom_mtr")
    private Double onLoomMtr;

    @Column(name = "shrinkage")
    private Double shrinkage;

    @Column(name = "expected_fabric_meter")
    private Double expectedFabricMeter;

    @Column(name = "gating_picks")
    private Double gatingPicks;

    @Column(name = "remaining_meters")
    private Double remainingMeters;

    @Column(name = "is_production_completed")
    private Integer isProductionCompleted = 0;

    @Column(name = "is_beam_cut")
    private Integer isBeamCut = 0;

    @Column(name = "is_doffed_beam")
    private Integer isDoffedBeam = 0;

    @Column(name = "changed_fabric_master_id")
    private Long changedFabricMasterId;

    @Column(name = "changed_fabric_code_quality", columnDefinition = "text")
    private String changedFabricCodeQuality;

    @Column(name = "sizing_set_no")
    private String sizingSetNo;

    @Column(name = "warp_count")
    private String warpCount;

    @Column(name = "warp_yarn_mill_name")
    private String warpYarnMillName;

    @Column(name = "warp_yarn_lot_no")
    private String warpYarnLotNo;

    @Column(name = "weft_count")
    private String weftCount;

    @Column(name = "weft_yarn_mill_name")
    private String weftYarnMillName;

    @Column(name = "weft_yarn_lot_no")
    private String weftYarnLotNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BeamGating getBeamGating() { return beamGating; }
    public void setBeamGating(BeamGating beamGating) { this.beamGating = beamGating; }

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
