package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "beam_stock", schema = "masters")
public class BeamStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "stock_date")
    private LocalDate stockDate;

    @Column(name = "beam_inward_no")
    private String beamInwardNo;

    @Column(name = "sized_beam_no", nullable = false)
    private String sizedBeamNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_contract_no")
    private String weavingContractNo;

    @Column(name = "weaving_vendor_id")
    private Long weavingVendorId;

    @Column(name = "weaver_vendor_name")
    private String weaverVendorName;

    @Column(name = "sizing_vendor_id")
    private Long sizingVendorId;

    @Column(name = "sizing_vendor_name")
    private String sizingVendorName;

    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "sales_order_no")
    private String salesOrderNo;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "sizing_plan_no")
    private String sizingPlanNo;

    @Column(name = "fabric_id")
    private Long fabricId;

    @Column(name = "fabric_code")
    private String fabricCode;

    @Column(name = "quality")
    private String quality;

    @Column(name = "wrap_meters")
    private Double wrapMeters;

    @Column(name = "on_loom_mtr")
    private Double onLoomMtr;

    @Column(name = "shrinkage")
    private Double shrinkage;

    @Column(name = "expected_fabric_meter")
    private Double expectedFabricMeter;

    @Column(name = "total_ends")
    private Integer totalEnds;

    @Column(name = "fabric_panel_width")
    private Double fabricPanelWidth;

    @Column(name = "warp_count")
    private String warpCount;

    @Column(name = "warp_yarn_mill_name")
    private String warpYarnMillName;

    @Column(name = "warp_yarn_lot_no")
    private String warpYarnLotNo;

    @Column(name = "sizing_set_no")
    private String sizingSetNo;

    @Column(name = "sizing_pickup_percent")
    private Double sizingPickupPercent;

    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    @Column(name = "sizing_rate")
    private Double sizingRate;

    @Column(name = "is_beam_outwarded", columnDefinition = "int default 0")
    private Integer isBeamOutwarded = 0;

    @Column(name = "is_beam_gated", columnDefinition = "int default 0")
    private Integer isBeamGated = 0;

    @Column(name = "is_return", columnDefinition = "int default 0")
    private Integer isReturn = 0;

    @Column(name = "remarks", columnDefinition = "text")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDate getStockDate() {
        return stockDate;
    }

    public void setStockDate(LocalDate stockDate) {
        this.stockDate = stockDate;
    }

    public String getBeamInwardNo() {
        return beamInwardNo;
    }

    public void setBeamInwardNo(String beamInwardNo) {
        this.beamInwardNo = beamInwardNo;
    }

    public String getSizedBeamNo() {
        return sizedBeamNo;
    }

    public void setSizedBeamNo(String sizedBeamNo) {
        this.sizedBeamNo = sizedBeamNo;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }

    public Long getWeavingVendorId() {
        return weavingVendorId;
    }

    public void setWeavingVendorId(Long weavingVendorId) {
        this.weavingVendorId = weavingVendorId;
    }

    public String getWeaverVendorName() {
        return weaverVendorName;
    }

    public void setWeaverVendorName(String weaverVendorName) {
        this.weaverVendorName = weaverVendorName;
    }

    public Long getSizingVendorId() {
        return sizingVendorId;
    }

    public void setSizingVendorId(Long sizingVendorId) {
        this.sizingVendorId = sizingVendorId;
    }

    public String getSizingVendorName() {
        return sizingVendorName;
    }

    public void setSizingVendorName(String sizingVendorName) {
        this.sizingVendorName = sizingVendorName;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public String getSizingPlanNo() {
        return sizingPlanNo;
    }

    public void setSizingPlanNo(String sizingPlanNo) {
        this.sizingPlanNo = sizingPlanNo;
    }

    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Double getWrapMeters() {
        return wrapMeters;
    }

    public void setWrapMeters(Double wrapMeters) {
        this.wrapMeters = wrapMeters;
    }

    public Double getOnLoomMtr() {
        return onLoomMtr;
    }

    public void setOnLoomMtr(Double onLoomMtr) {
        this.onLoomMtr = onLoomMtr;
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

    public Double getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(Double fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
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

    public Double getSizingRate() {
        return sizingRate;
    }

    public void setSizingRate(Double sizingRate) {
        this.sizingRate = sizingRate;
    }

    public Integer getIsBeamOutwarded() {
        return isBeamOutwarded != null ? isBeamOutwarded : 0;
    }

    public void setIsBeamOutwarded(Integer isBeamOutwarded) {
        this.isBeamOutwarded = isBeamOutwarded;
    }

    public Integer getIsBeamGated() {
        return isBeamGated != null ? isBeamGated : 0;
    }

    public void setIsBeamGated(Integer isBeamGated) {
        this.isBeamGated = isBeamGated;
    }

    public Integer getIsReturn() {
        return isReturn != null ? isReturn : 0;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
