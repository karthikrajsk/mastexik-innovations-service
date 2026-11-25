package com.tech3.erp.dto;

import java.time.LocalDate;

public class BeamInwardBeamDetailsDTO {
    private Long id;
    private Long beamInwardId;
    private Long weavingContractId;
    private Long salesOrderId;
//    private Long emptyBeamId;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private Integer totalEnds;
    private String sizedBeamNo;
    private String flangeNo;
    private Long flangeId;
    private Long weavingVendorId;
    private Long sizingPlanId;
    private Integer isBeamOutwarded;
    private Integer isBeamGated;
    private Long fabricId;
    private Double fabricPanelWidth;
    private Integer isReturn;
    private LocalDate returnDate;
    private String returnNo;
    private Long returnWeaverId;
    private Long inwardTypeId;
    private String warpCount;
    private String warpYarnMillName;
    private String warpYarnLotNo;
    private String dcNumber;
    private LocalDate dcDate;
    private String sizingSetNo;
    private Double sizingPickupPercent;

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

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }

    public Double getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(Double fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public Integer getIsBeamOutwarded() {
        return isBeamOutwarded;
    }

    public void setIsBeamOutwarded(Integer isBeamOutwarded) {
        this.isBeamOutwarded = isBeamOutwarded;
    }

    public Integer getIsBeamGated() {
        return isBeamGated;
    }

    public void setIsBeamGated(Integer isBeamGated) {
        this.isBeamGated = isBeamGated;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
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

    public LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDate dcDate) {
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
