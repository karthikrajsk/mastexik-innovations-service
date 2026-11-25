package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "beam_inward_beam_details", schema = "masters")
public class BeamInwardBeamDetails {

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer isBeamOutwarded = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long weavingContractId;
    private Long salesOrderId;
//    private Long emptyBeamId;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    @Column(name = "total_ends")
    private Integer totalEnds;
    private String sizedBeamNo;
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;
    private Long weavingVendorId;
    private Long sizingPlanId;
    private Long fabricId;

    @Column(name = "weaver_vendor_name")
    private String weaverVendorName;

    @Column(name = "fabric_panel_width")
    private Double fabricPanelWidth;

    @Column(name = "changed_fabric_master_id")
    private Long changedFabricMasterId;

    @Column(name = "changed_fabric_code_quality", columnDefinition = "text")
    private String changedFabricCodeQuality;

    @Column(name = "is_return", columnDefinition = "int default 0")
    private Integer isReturn = 0;

    @Column(name = "return_date")
    private java.time.LocalDate returnDate;

    @Column(name = "return_no", length = 100)
    private String returnNo;

    @Column(name = "return_weaver_id")
    private Long returnWeaverId;

    @Column(name = "is_beam_gated", columnDefinition = "int default 0")
    private Integer isBeamGated = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beam_inward_id")
    private BeamInward beamInward;

    @Column(name = "inward_type_id")
    private Long inwardTypeId;

    @Column(name = "warp_count")
    private String warpCount;

    @Column(name = "warp_yarn_mill_name")
    private String warpYarnMillName;

    @Column(name = "warp_yarn_lot_no")
    private String warpYarnLotNo;

    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date")
    private java.time.LocalDate dcDate;

    @Column(name = "sizing_set_no")
    private String sizingSetNo;

    @Column(name = "sizing_pickup_percent")
    private Double sizingPickupPercent;

    // Getters and Setters

    public Integer getIsBeamOutwarded() {
        return isBeamOutwarded != null ? isBeamOutwarded : 0;
    }

    public void setIsBeamOutwarded(Integer isBeamOutwarded) {
        this.isBeamOutwarded = isBeamOutwarded;
    }

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

//    public Long getEmptyBeamId() {
//        return emptyBeamId;
//    }
//
//    public void setEmptyBeamId(Long emptyBeamId) {
//        this.emptyBeamId = emptyBeamId;
//    }

    public Double getWrapMeters() {
        return wrapMeters;
    }

    public void setWrapMeters(Double wrapMeters) {
        this.wrapMeters = wrapMeters;
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

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }

    public BeamInward getBeamInward() {
        return beamInward;
    }

    public void setBeamInward(BeamInward beamInward) {
        this.beamInward = beamInward;
    }
    
    public String getSizedBeamNo() {
        return sizedBeamNo;
    }

    public void setSizedBeamNo(String sizedBeamNo) {
        this.sizedBeamNo = sizedBeamNo;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
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

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Double getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(Double fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public java.time.LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(java.time.LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public Long getReturnWeaverId() {
        return returnWeaverId;
    }

    public void setReturnWeaverId(Long returnWeaverId) {
        this.returnWeaverId = returnWeaverId;
    }

    public Integer getIsBeamGated() {
        return isBeamGated != null ? isBeamGated : 0;
    }

    public void setIsBeamGated(Integer isBeamGated) {
        this.isBeamGated = isBeamGated;
    }

    public String getWeaverVendorName() {
        return weaverVendorName;
    }

    public void setWeaverVendorName(String weaverVendorName) {
        this.weaverVendorName = weaverVendorName;
    }

    public Long getChangedFabricMasterId() {
        return changedFabricMasterId;
    }

    public void setChangedFabricMasterId(Long changedFabricMasterId) {
        this.changedFabricMasterId = changedFabricMasterId;
    }

    public String getChangedFabricCodeQuality() {
        return changedFabricCodeQuality;
    }

    public void setChangedFabricCodeQuality(String changedFabricCodeQuality) {
        this.changedFabricCodeQuality = changedFabricCodeQuality;
    }

    public Long getInwardTypeId() {
        return inwardTypeId;
    }

    public void setInwardTypeId(Long inwardTypeId) {
        this.inwardTypeId = inwardTypeId;
    }

    public String getWarpCount() {
        return warpCount;
    }

    public void setWarpCount(String warpCount) {
        this.warpCount = warpCount;
    }

    public String getWarpYarnMillName() {
        return warpYarnMillName;
    }

    public void setWarpYarnMillName(String warpYarnMillName) {
        this.warpYarnMillName = warpYarnMillName;
    }

    public String getWarpYarnLotNo() {
        return warpYarnLotNo;
    }

    public void setWarpYarnLotNo(String warpYarnLotNo) {
        this.warpYarnLotNo = warpYarnLotNo;
    }

    public String getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(String dcNumber) {
        this.dcNumber = dcNumber;
    }

    public java.time.LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(java.time.LocalDate dcDate) {
        this.dcDate = dcDate;
    }

    public String getSizingSetNo() {
        return sizingSetNo;
    }

    public void setSizingSetNo(String sizingSetNo) {
        this.sizingSetNo = sizingSetNo;
    }

    public Double getSizingPickupPercent() {
        return sizingPickupPercent;
    }

    public void setSizingPickupPercent(Double sizingPickupPercent) {
        this.sizingPickupPercent = sizingPickupPercent;
    }
}
