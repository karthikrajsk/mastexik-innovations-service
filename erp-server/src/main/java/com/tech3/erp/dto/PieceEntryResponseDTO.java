package com.tech3.erp.dto;

import java.math.BigDecimal;

public class PieceEntryResponseDTO {
    private Long id;
    private String pieceNumber;
    private BigDecimal quantity;
    private BigDecimal weight;
    private String remarks;
    private Boolean activeFlag;
    private Long warehouseId;
    private String sourceType;
    private String poType;
    private Long fabricTypeId;
    private Long fabricMasterId;
    private String inspectionStatus;
    private Integer isInspected;
    private Long inspectionId;
    private Long gradeId;
    private BigDecimal inspectedMtr;
    private BigDecimal inspectedNtWt;
    private BigDecimal inspectedAvgWt;
    private String defectType;
    private BigDecimal defectPoints;
    private Integer pieceNo;
    private String piecePrefix;
    private String pieceNumberComputed;
    private Long inspectorId;
    private Integer isPacked;
    private Long packingId;
    private Integer isDispatched;
    private Long dispatchId;
    private Long invoiceId;
    private Integer isRejected;
    private String rejectedReason;
    private String packLaterReason;
    private Integer isFactoryFabric;
    private Integer isLastPiece;
    private Long loomId;
    private String beamNo;
    private Long flangeId;
    private Long beamId;
    private String doffingPicks;
    private Long companyId;
    private String doffingDate;
    private String doffingNo;
    private String pieceMeter;
    private Long orderTypeId;
    private String warpSetNo;
    private String warpCount;
    private String warpYarnMillName;
    private String warpYarnLotNo;
    private String weftCount;
    private String weftYarnMillName;
    private String weftYarnLotNo;

    // Constructors
    public PieceEntryResponseDTO() {}

    public PieceEntryResponseDTO(com.tech3.erp.entity.PieceEntry piece) {
        this.id = piece.getId();
        this.pieceNumber = piece.getPieceNumber();
        this.quantity = piece.getQuantity();
        this.weight = piece.getWeight();
        this.remarks = piece.getRemarks();
        this.activeFlag = piece.getActiveFlag();
        this.warehouseId = piece.getWarehouseId();
        this.sourceType = piece.getSourceType();
        this.poType = piece.getPoType();
        this.fabricTypeId = piece.getFabricTypeId();
        this.fabricMasterId = piece.getFabricMasterId();
        this.inspectionStatus = piece.getInspectionStatus();
        this.isInspected = piece.getIsInspected();
        this.inspectionId = piece.getInspectionId();
        this.gradeId = piece.getGradeId();
        this.inspectedMtr = piece.getInspectedMtr();
        this.inspectedNtWt = piece.getInspectedNtWt();
        this.inspectedAvgWt = piece.getInspectedAvgWt();
        this.defectType = piece.getDefectType();
        this.defectPoints = piece.getDefectPoints();
        this.pieceNo = piece.getPieceNo();
        this.piecePrefix = piece.getPiecePrefix();
        this.pieceNumberComputed = (piece.getPiecePrefix() != null ? piece.getPiecePrefix() : "") + 
                                  (piece.getPieceNo() != null ? piece.getPieceNo().toString() : "");
        this.inspectorId = piece.getInspectorId();
        this.isPacked = piece.getIsPacked();
        this.packingId = piece.getPackingId();
        this.isDispatched = piece.getIsDispatched();
        this.dispatchId = piece.getDispatchId();
        this.invoiceId = piece.getInvoiceId();
        this.isRejected = piece.getIsRejected();
        this.rejectedReason = piece.getRejectedReason();
        this.packLaterReason = piece.getPackLaterReason();
        this.isFactoryFabric = piece.getIsFactoryFabric();
        this.isLastPiece = piece.getIsLastPiece();
        this.loomId = piece.getLoomId();
        this.beamNo = piece.getBeamNo();
        this.flangeId = piece.getFlangeId();
        this.beamId = piece.getBeamId();
        this.doffingPicks = piece.getDoffingPicks();
        this.companyId = piece.getCompanyId();
        this.doffingDate = piece.getDoffingDate();
        this.doffingNo = piece.getDoffingNo();
        this.pieceMeter = piece.getPieceMeter();
        this.orderTypeId = piece.getOrderTypeId();
        this.warpSetNo = piece.getWarpSetNo();
        this.warpCount = piece.getWarpCount();
        this.warpYarnMillName = piece.getWarpYarnMillName();
        this.warpYarnLotNo = piece.getWarpYarnLotNo();
        this.weftCount = piece.getWeftCount();
        this.weftYarnMillName = piece.getWeftYarnMillName();
        this.weftYarnLotNo = piece.getWeftYarnLotNo();
    }

    // Getters and Setters (same as your existing PieceEntryDTO)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPieceNumber() { return pieceNumber; }
    public void setPieceNumber(String pieceNumber) { this.pieceNumber = pieceNumber; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    public String getPoType() { return poType; }
    public void setPoType(String poType) { this.poType = poType; }
    public Long getFabricTypeId() { return fabricTypeId; }
    public void setFabricTypeId(Long fabricTypeId) { this.fabricTypeId = fabricTypeId; }
    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }
    public String getInspectionStatus() { return inspectionStatus; }
    public void setInspectionStatus(String inspectionStatus) { this.inspectionStatus = inspectionStatus; }
    public Integer getIsInspected() { return isInspected; }
    public void setIsInspected(Integer isInspected) { this.isInspected = isInspected; }
    public Long getInspectionId() { return inspectionId; }
    public void setInspectionId(Long inspectionId) { this.inspectionId = inspectionId; }
    public Long getGradeId() { return gradeId; }
    public void setGradeId(Long gradeId) { this.gradeId = gradeId; }
    public BigDecimal getInspectedMtr() { return inspectedMtr; }
    public void setInspectedMtr(BigDecimal inspectedMtr) { this.inspectedMtr = inspectedMtr; }
    public BigDecimal getInspectedNtWt() { return inspectedNtWt; }
    public void setInspectedNtWt(BigDecimal inspectedNtWt) { this.inspectedNtWt = inspectedNtWt; }
    public BigDecimal getInspectedAvgWt() { return inspectedAvgWt; }
    public void setInspectedAvgWt(BigDecimal inspectedAvgWt) { this.inspectedAvgWt = inspectedAvgWt; }
    public String getDefectType() { return defectType; }
    public void setDefectType(String defectType) { this.defectType = defectType; }
    public BigDecimal getDefectPoints() { return defectPoints; }
    public void setDefectPoints(BigDecimal defectPoints) { this.defectPoints = defectPoints; }
    public Integer getPieceNo() { return pieceNo; }
    public void setPieceNo(Integer pieceNo) { this.pieceNo = pieceNo; }
    public String getPiecePrefix() { return piecePrefix; }
    public void setPiecePrefix(String piecePrefix) { this.piecePrefix = piecePrefix; }
    public String getPieceNumberComputed() { return pieceNumberComputed; }
    public void setPieceNumberComputed(String pieceNumberComputed) { this.pieceNumberComputed = pieceNumberComputed; }
    public Long getInspectorId() { return inspectorId; }
    public void setInspectorId(Long inspectorId) { this.inspectorId = inspectorId; }
    public Integer getIsPacked() { return isPacked; }
    public void setIsPacked(Integer isPacked) { this.isPacked = isPacked; }
    public Long getPackingId() { return packingId; }
    public void setPackingId(Long packingId) { this.packingId = packingId; }
    public Integer getIsDispatched() { return isDispatched; }
    public void setIsDispatched(Integer isDispatched) { this.isDispatched = isDispatched; }
    public Long getDispatchId() { return dispatchId; }
    public void setDispatchId(Long dispatchId) { this.dispatchId = dispatchId; }
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public Integer getIsFactoryFabric() { return isFactoryFabric; }
    public void setIsFactoryFabric(Integer isFactoryFabric) { this.isFactoryFabric = isFactoryFabric; }
    public Integer getIsLastPiece() { return isLastPiece; }
    public void setIsLastPiece(Integer isLastPiece) { this.isLastPiece = isLastPiece; }
    public Long getLoomId() { return loomId; }
    public void setLoomId(Long loomId) { this.loomId = loomId; }
    public String getBeamNo() { return beamNo; }
    public void setBeamNo(String beamNo) { this.beamNo = beamNo; }
    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }
    public Long getBeamId() { return beamId; }
    public void setBeamId(Long beamId) { this.beamId = beamId; }
    public String getDoffingPicks() { return doffingPicks; }
    public void setDoffingPicks(String doffingPicks) { this.doffingPicks = doffingPicks; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getDoffingDate() { return doffingDate; }
    public void setDoffingDate(String doffingDate) { this.doffingDate = doffingDate; }
    public String getDoffingNo() { return doffingNo; }
    public void setDoffingNo(String doffingNo) { this.doffingNo = doffingNo; }
    public String getPieceMeter() { return pieceMeter; }
    public void setPieceMeter(String pieceMeter) { this.pieceMeter = pieceMeter; }
    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Integer getIsRejected() { return isRejected; }
    public void setIsRejected(Integer isRejected) { this.isRejected = isRejected; }

    public String getRejectedReason() { return rejectedReason; }
    public void setRejectedReason(String rejectedReason) { this.rejectedReason = rejectedReason; }

    public String getPackLaterReason() { return packLaterReason; }
    public void setPackLaterReason(String packLaterReason) { this.packLaterReason = packLaterReason; }

    public String getWarpSetNo() { return warpSetNo; }
    public void setWarpSetNo(String warpSetNo) { this.warpSetNo = warpSetNo; }

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