package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "fabric_inspection", schema = "masters")
public class FabricInspection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inspection_date")
    private LocalDate inspectionDate;

    @Column(name = "loom_no")
    private String loomNo; // Changed to String, non-mandatory
    
    @Column(name = "inspector_id") // NEW FIELD
    private Long inspectorId;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "fabric_quality", length = 100)
    private String fabricQuality;

    @Column(name = "piece_meters") // Renamed
    private Double pieceMeters;

    @Column(name = "net_weight") // Renamed
    private Double netWeight;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;
    
    // New fields for source tracking
    @Column(name = "source_type", length = 50)
    private String sourceType; // "PURCHASE_INWARD" or "JOBWORK_FABRIC"
    
    @Column(name = "purchase_inward_id")
    private Long purchaseInwardId;
    
    @Column(name = "jobwork_fabric_receive_id")
    private Long jobworkFabricReceiveId;

    @Column(name = "brushing_receive_id")
    private Long brushingReceiveId;
    
    @Column(name = "piece_no", length = 100)
    private String pieceNo;
    
    @Column(name = "fabric_code", length = 100)
    private String fabricCode;

    @Column(name = "fabric_type_id")
    private Long fabricTypeId;

    @Column(name = "is_factory_fabric")
    private Integer isFactoryFabric;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "weaving_work_order_id")
    private Long weavingWorkOrderId;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "beam_no")
    private String beamNo;

    @Column(name = "is_no_defected_pieces")
    private Integer isNoDefectedPieces = 0;

    @Column(name = "piece_entry_id")
    private Long pieceEntryId;

    @Column(name = "is_inspected")
    private Integer isInspected;

    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "opening_balance_id")
    private Long openingBalanceId;

    @Column(name = "order_no", length = 255)
    private String orderNo;

    @Column(name = "contract_no", length = 255)
    private String contractNo;

    @Column(name = "total_points")
    private Double totalPoints;

    @Column(name = "points_per_100mtr")
    private Double pointsPer100Mtr;

    @Column(name = "doff_date")
    private LocalDate doffDate;

    @Column(name = "po_no", length = 255)
    private String poNo;

    @Column(name = "warp_lot_no", length = 255)
    private String warpLotNo;

    @Column(name = "weft_lot_no", length = 255)
    private String weftLotNo;

    @Column(name = "warp_set_no", length = 255)
    private String warpSetNo;

    @Column(name = "selvedge_thread", length = 255)
    private String selvedgeThread;

    @Column(name = "warp_count", length = 255)
    private String warpCount;

    @Column(name = "weft_count", length = 255)
    private String weftCount;

    @Column(name = "selvedge_width_ps")
    private Double selvedgeWidthPs;

    @Column(name = "selvedge_width_rs")
    private Double selvedgeWidthRs;

    @Column(name = "fringe_width_ps")
    private Double fringeWidthPs;

    @Column(name = "fringe_width_rs")
    private Double fringeWidthRs;

    @Column(name = "weave", length = 255)
    private String weave;

    @Column(name = "epi", length = 50)
    private String epi;

    @Column(name = "ppi", length = 50)
    private String ppi;

    @Column(name = "width", length = 50)
    private String width;

    @Column(name = "inspected_epi", length = 50)
    @JsonProperty("inspectedEpi")
    @JsonAlias({"inspected_epi", "inspectedEpi"})
    private String inspectedEpi;

    @Column(name = "inspected_ppi", length = 50)
    @JsonProperty("inspectedPpi")
    @JsonAlias({"inspected_ppi", "inspectedPpi"})
    private String inspectedPpi;

    @Column(name = "inspected_width", length = 50)
    @JsonProperty("inspectedWidth")
    @JsonAlias({"inspected_width", "inspectedWidth"})
    private String inspectedWidth;

    @Column(name = "buyer_inspector_id")
    private Long buyerInspectorId;

    @Column(name = "buyer_inspector", length = 255)
    private String buyerInspector;

    @Column(name = "buyer_inspector_name", length = 255)
    private String buyerInspectorName;

    @Column(name = "pack_later_reason", length = 1000)
    private String packLaterReason;

    @Column(name = "rejected_reason", length = 1000)
    private String rejectedReason;

    @OneToMany(mappedBy = "fabricInspection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InspectionDetail> inspectionDetails;

    @OneToMany(mappedBy = "fabricInspection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InspectionEntry> inspectionEntries;
    
    

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getLoomNo() {
        return loomNo;
    }

    public void setLoomNo(String loomNo) {
        this.loomNo = loomNo;
    }

    public Long getVendorId() {
        return vendorId;
    }
    
    public Long getInspectorId() { // NEW GETTER
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) { // NEW SETTER
        this.inspectorId = inspectorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getFabricQuality() {
        return fabricQuality;
    }

    public void setFabricQuality(String fabricQuality) {
        this.fabricQuality = fabricQuality;
    }

    public Double getPieceMeters() {
        return pieceMeters;
    }

    public void setPieceMeters(Double pieceMeters) {
        this.pieceMeters = pieceMeters;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getPurchaseInwardId() {
        return purchaseInwardId;
    }

    public void setPurchaseInwardId(Long purchaseInwardId) {
        this.purchaseInwardId = purchaseInwardId;
    }

    public Long getJobworkFabricReceiveId() {
        return jobworkFabricReceiveId;
    }

    public void setJobworkFabricReceiveId(Long jobworkFabricReceiveId) {
        this.jobworkFabricReceiveId = jobworkFabricReceiveId;
    }

    public Long getBrushingReceiveId() {
        return brushingReceiveId;
    }

    public void setBrushingReceiveId(Long brushingReceiveId) {
        this.brushingReceiveId = brushingReceiveId;
    }

    public String getPieceNo() {
        return pieceNo;
    }

    public void setPieceNo(String pieceNo) {
        this.pieceNo = pieceNo;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public Long getFabricTypeId() {
        return fabricTypeId;
    }

    public void setFabricTypeId(Long fabricTypeId) {
        this.fabricTypeId = fabricTypeId;
    }

    public Integer getIsFactoryFabric() {
        return isFactoryFabric;
    }

    public void setIsFactoryFabric(Integer isFactoryFabric) {
        this.isFactoryFabric = isFactoryFabric;
    }

    public List<InspectionDetail> getInspectionDetails() {
        return inspectionDetails;
    }

    public void setInspectionDetails(List<InspectionDetail> inspectionDetails) {
        this.inspectionDetails = inspectionDetails;
    }

    public List<InspectionEntry> getInspectionEntries() {
        return inspectionEntries;
    }

    public void setInspectionEntries(List<InspectionEntry> inspectionEntries) {
        this.inspectionEntries = inspectionEntries;
    }

    public Long getLoomId() {
        return loomId;
    }

    public void setLoomId(Long loomId) {
        this.loomId = loomId;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public Long getWeavingWorkOrderId() {
        return weavingWorkOrderId;
    }

    public void setWeavingWorkOrderId(Long weavingWorkOrderId) {
        this.weavingWorkOrderId = weavingWorkOrderId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public Integer getIsNoDefectedPieces() {
        return isNoDefectedPieces;
    }

    public void setIsNoDefectedPieces(Integer isNoDefectedPieces) {
        this.isNoDefectedPieces = isNoDefectedPieces;
    }

    public Long getPieceEntryId() {
        return pieceEntryId;
    }

    public void setPieceEntryId(Long pieceEntryId) {
        this.pieceEntryId = pieceEntryId;
    }

    public Integer getIsInspected() {
        return isInspected;
    }

    public void setIsInspected(Integer isInspected) {
        this.isInspected = isInspected;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getBeamNo() {
        return beamNo;
    }

    public void setBeamNo(String beamNo) {
        this.beamNo = beamNo;
    }

    public Long getOpeningBalanceId() {
        return openingBalanceId;
    }

    public void setOpeningBalanceId(Long openingBalanceId) {
        this.openingBalanceId = openingBalanceId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getPointsPer100Mtr() {
        return pointsPer100Mtr;
    }

    public void setPointsPer100Mtr(Double pointsPer100Mtr) {
        this.pointsPer100Mtr = pointsPer100Mtr;
    }

    public LocalDate getDoffDate() {
        return doffDate;
    }

    public void setDoffDate(LocalDate doffDate) {
        this.doffDate = doffDate;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getWarpLotNo() {
        return warpLotNo;
    }

    public void setWarpLotNo(String warpLotNo) {
        this.warpLotNo = warpLotNo;
    }

    public String getWeftLotNo() {
        return weftLotNo;
    }

    public void setWeftLotNo(String weftLotNo) {
        this.weftLotNo = weftLotNo;
    }

    public String getWarpSetNo() {
        return warpSetNo;
    }

    public void setWarpSetNo(String warpSetNo) {
        this.warpSetNo = warpSetNo;
    }

    public String getSelvedgeThread() {
        return selvedgeThread;
    }

    public void setSelvedgeThread(String selvedgeThread) {
        this.selvedgeThread = selvedgeThread;
    }

    public String getWarpCount() {
        return warpCount;
    }

    public void setWarpCount(String warpCount) {
        this.warpCount = warpCount;
    }

    public String getWeftCount() {
        return weftCount;
    }

    public void setWeftCount(String weftCount) {
        this.weftCount = weftCount;
    }

    public Double getSelvedgeWidthPs() {
        return selvedgeWidthPs;
    }

    public void setSelvedgeWidthPs(Double selvedgeWidthPs) {
        this.selvedgeWidthPs = selvedgeWidthPs;
    }

    public Double getSelvedgeWidthRs() {
        return selvedgeWidthRs;
    }

    public void setSelvedgeWidthRs(Double selvedgeWidthRs) {
        this.selvedgeWidthRs = selvedgeWidthRs;
    }

    public Double getFringeWidthPs() {
        return fringeWidthPs;
    }

    public void setFringeWidthPs(Double fringeWidthPs) {
        this.fringeWidthPs = fringeWidthPs;
    }

    public Double getFringeWidthRs() {
        return fringeWidthRs;
    }

    public void setFringeWidthRs(Double fringeWidthRs) {
        this.fringeWidthRs = fringeWidthRs;
    }

    public String getWeave() {
        return weave;
    }

    public void setWeave(String weave) {
        this.weave = weave;
    }

    public String getEpi() {
        return epi;
    }

    public void setEpi(String epi) {
        this.epi = epi;
    }

    public String getPpi() {
        return ppi;
    }

    public void setPpi(String ppi) {
        this.ppi = ppi;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getInspectedEpi() {
        return inspectedEpi;
    }

    public void setInspectedEpi(String inspectedEpi) {
        this.inspectedEpi = inspectedEpi;
    }

    public String getInspected_epi() {
        return inspectedEpi;
    }

    public void setInspected_epi(String inspectedEpi) {
        this.inspectedEpi = inspectedEpi;
    }

    public String getInspectedPpi() {
        return inspectedPpi;
    }

    public void setInspectedPpi(String inspectedPpi) {
        this.inspectedPpi = inspectedPpi;
    }

    public String getInspected_ppi() {
        return inspectedPpi;
    }

    public void setInspected_ppi(String inspectedPpi) {
        this.inspectedPpi = inspectedPpi;
    }

    public String getInspectedWidth() {
        return inspectedWidth;
    }

    public void setInspectedWidth(String inspectedWidth) {
        this.inspectedWidth = inspectedWidth;
    }

    public String getInspected_width() {
        return inspectedWidth;
    }

    public void setInspected_width(String inspectedWidth) {
        this.inspectedWidth = inspectedWidth;
    }

    public Long getBuyerInspectorId() {
        return buyerInspectorId;
    }

    public void setBuyerInspectorId(Long buyerInspectorId) {
        this.buyerInspectorId = buyerInspectorId;
    }

    public String getBuyerInspector() {
        return buyerInspector;
    }

    public void setBuyerInspector(String buyerInspector) {
        this.buyerInspector = buyerInspector;
    }

    public String getBuyerInspectorName() {
        return buyerInspectorName;
    }

    public void setBuyerInspectorName(String buyerInspectorName) {
        this.buyerInspectorName = buyerInspectorName;
    }

    public String getPackLaterReason() {
        return packLaterReason;
    }

    public void setPackLaterReason(String packLaterReason) {
        this.packLaterReason = packLaterReason;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}