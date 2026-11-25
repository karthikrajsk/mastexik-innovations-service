package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

public class FabricInspectionDTO {
    private Long id;
    private LocalDate inspectionDate;
    private String loomNo; // Changed to String to make it non-mandatory
    private Long vendorId;
    private Long inspectorId;
    private Long companyId;
    private Integer isFactoryFabric;
    private String fabricQuality;
    private Double pieceMeters; // Renamed from doffMeters
    private Double netWeight; // Renamed from doffWeight
    private Boolean activeFlag;
    
    
    // New fields for source tracking
    private String sourceType; // "PURCHASE_INWARD" or "JOBWORK_FABRIC"
    private Long purchaseInwardId;
    private Long jobworkFabricReceiveId;
    private Long brushingReceiveId;
    private String pieceNo;
    private String fabricCode;
    private Long fabricTypeId;
    
    private Long loomId;
    private Long flangeId;
    private Long weavingWorkOrderId;
    private Long orderTypeId;
    private Long buyerId;
    private Long fabricMasterId;
    private String beamNo;
    private Integer isNoDefectedPieces;
    private Long pieceEntryId;
    private Integer isInspected;
    private Long openingBalanceId;
    private String orderNo;
    private String contractNo;
    private Double totalPoints;
    private Double pointsPer100Mtr;

    private LocalDate doffDate;
    private String poNo;
    private String warpLotNo;
    private String weftLotNo;
    private String warpSetNo;
    private String selvedgeThread;
    private String warpCount;
    private String weftCount;
    private Double selvedgeWidthPs;
    private Double selvedgeWidthRs;
    private Double fringeWidthPs;
    private Double fringeWidthRs;
    private String weave;
    private String epi;
    private String ppi;
    private String width;
    @JsonProperty("inspectedEpi")
    @JsonAlias({"inspected_epi", "inspectedEpi"})
    private String inspectedEpi;
    @JsonProperty("inspectedPpi")
    @JsonAlias({"inspected_ppi", "inspectedPpi"})
    private String inspectedPpi;
    @JsonProperty("inspectedWidth")
    @JsonAlias({"inspected_width", "inspectedWidth"})
    private String inspectedWidth;
    private Long buyerInspectorId;
    private String buyerInspector;
    private String buyerInspectorName;
    private List<InspectionDetailDTO> inspectionDetails;
    private List<InspectionEntryDTO> inspectionEntries;
    private List<PieceEntryResponseDTO> pieceEntries;

    // Packing integration transient fields
    private String packingOption;
    private String packingType;
    private Long warehouseId;
    private Long uomId;
    private String packingRemarks;
    private String rollOrBaleNo;
    private String rejectedReason;
    private String packLaterReason;
    private Long salesOrderId;
    private Integer isRejected;
    
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

    public Integer getIsFactoryFabric() {
        return isFactoryFabric;
    }

    public void setIsFactoryFabric(Integer isFactoryFabric) {
        this.isFactoryFabric = isFactoryFabric;
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

    public String getBeamNo() {
        return beamNo;
    }

    public void setBeamNo(String beamNo) {
        this.beamNo = beamNo;
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

    public List<InspectionDetailDTO> getInspectionDetails() {
        return inspectionDetails;
    }

    public void setInspectionDetails(List<InspectionDetailDTO> inspectionDetails) {
        this.inspectionDetails = inspectionDetails;
    }

    public List<InspectionEntryDTO> getInspectionEntries() {
        return inspectionEntries;
    }

    public void setInspectionEntries(List<InspectionEntryDTO> inspectionEntries) {
        this.inspectionEntries = inspectionEntries;
    }
    
    public List<PieceEntryResponseDTO> getPieceEntries() {
        return pieceEntries;
    }

    public void setPieceEntries(List<PieceEntryResponseDTO> pieceEntries) {
        this.pieceEntries = pieceEntries;
    }

    public String getPackingOption() {
        return packingOption;
    }

    public void setPackingOption(String packingOption) {
        this.packingOption = packingOption;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public String getPackingRemarks() {
        return packingRemarks;
    }

    public void setPackingRemarks(String packingRemarks) {
        this.packingRemarks = packingRemarks;
    }

    public String getRollOrBaleNo() {
        return rollOrBaleNo;
    }

    public void setRollOrBaleNo(String rollOrBaleNo) {
        this.rollOrBaleNo = rollOrBaleNo;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public String getPackLaterReason() {
        return packLaterReason;
    }

    public void setPackLaterReason(String packLaterReason) {
        this.packLaterReason = packLaterReason;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Integer getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Integer isRejected) {
        this.isRejected = isRejected;
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
} 