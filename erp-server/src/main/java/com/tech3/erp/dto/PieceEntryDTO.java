
package com.tech3.erp.dto;

import com.tech3.erp.entity.PieceEntry;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PieceEntryDTO {

    private Long id;
    private String pieceNumber;
    private BigDecimal quantity;
    private BigDecimal weight;
    private BigDecimal cost;
    private String remarks;
    private Boolean activeFlag;
    private Long warehouseId; 

    // NEW fields for tracking
    private String sourceType; // 'purchase_inward' or 'jobwork_fabric_receive'
    private String poType; // 'Yarn', 'Woven Fabric', 'Knitted Fabric', etc.
    private Long fabricTypeId;
    private Long fabricMasterId;
     private Long fabricId;
    @JsonAlias("no_of_fabric_output")
    private Double noOfFabricOutput;

    private String inspectionStatus; // 'PENDING' or 'INSPECTED'

    // NEW ENHANCEMENT FIELDS (4-20)
    private Integer isInspected; // 0 or 1
    private Long inspectionId;
    private Long gradeId;
    private BigDecimal inspectedMtr;
    private BigDecimal inspectedNtWt;
    private BigDecimal inspectedAvgWt;
    private String defectType; // Comma-separated
    private BigDecimal defectPoints;
    private Integer pieceNo;
    private String piecePrefix;
    private String pieceNumberComputed; // Transient computed
    private Long inspectorId;
    private Integer isPacked; // 0 or 1
    private Long packingId;
    private Integer isDispatched; // 0 or 1
    private Long dispatchId;
    private Integer isInvoice; // 0 or 1
    private Long invoiceId;
    private Integer isRejected;
    private String rejectedReason;
    private String packLaterReason;
 
    private String orderNo;
    private Long orderId;
    private String contractNo;
    private Long contractId;
    private Integer isLastPiece;
    private Long loomId;
    private String beamNo;
    private Long flangeId;
    private String flangeNo;
    private Long beamId;
    private String doffingPicks;
    private Long companyId;
    private String doffingDate;
    private String doffingNo;
    private String pieceMeter;
    @JsonAlias({"remaining_mtr", "remainingMtr"})
    private BigDecimal remainingMtr;
    private Integer isFactoryFabric;
    private Long parentPieceId;
    private Long joinedPieceId;
    private Integer isParentPiece;
    private Integer isJoined;
    private Long operationId;
    private Long modeId;
    private Long orderTypeId;
 private LocalDate inwardDate;
    
    // NEW FIELDS FOR STOCK TRANSFER
    private Boolean isStockTransfer ;
    private Long stockTransferId;

    private BigDecimal grossWt;
    private String dcNumber;
    private LocalDate dcDate;
    private Long poCategoryId;

    // BRUSHING FIELDS
    private String brushingStatus;
    private Long brushingIssueId;
    private Long brushingReceiveId;
    private BigDecimal brushedMeters;
    private BigDecimal brushedWeight;
    private BigDecimal shrinkagePercentage;
    private BigDecimal weightLossPercentage;
    private Integer isBrushed = 0;

    // WARP & WEFT YARN DETAILS
    @JsonAlias({"beamSetNo", "beam_set_no", "warp_set_no", "warpSetNo"})
    private String warpSetNo;
    private String warpCount;
    private String warpYarnMillName;
    @JsonAlias({"warpLotNo", "warp_lot_no", "warp_yarn_lot_no", "warpYarnLotNo"})
    private String warpYarnLotNo;
    private String weftCount;
    private String weftYarnMillName;
    @JsonAlias({"weftLotNo", "weft_lot_no", "weft_yarn_lot_no", "weftYarnLotNo"})
    private String weftYarnLotNo;

    // NEW EXCEL UPLOAD & FABRIC STOCK FIELDS
    @JsonAlias({"pass_mtr", "passMtr", "passMtrs"})
    private BigDecimal passMtr;

    @JsonAlias({"inspection_date", "inspectionDate"})
    private LocalDate inspectionDate;

    @JsonAlias({"rolling_date", "rollingDate"})
    private LocalDate rollingDate;

    @JsonAlias({"roll_weight", "rollWeight"})
    private BigDecimal rollWeight;

    @JsonAlias({"glm", "glm_grams", "glmGrams"})
    private BigDecimal glm;

    @JsonAlias({"hfpl_roll_no", "hfplRollNo"})
    private String hfplRollNo;

    @JsonAlias({"dispatch_mtr", "dispatchMtr", "dispatchMtrs"})
    private BigDecimal dispatchMtr;

    @JsonAlias({"dispatch_date", "dispatchDate"})
    private LocalDate dispatchDate;

    @JsonAlias({"hfpl_invoice_no", "hfplInvoiceNo"})
    private String hfplInvoiceNo;

    @JsonAlias({"fabric_status", "fabricStatus"})
    private String fabricStatus;

    public PieceEntryDTO() {}

    public PieceEntryDTO(PieceEntry piece) {
        this.id = piece.getId();
        this.pieceNumber = piece.getPieceNumber();
        this.quantity = piece.getQuantity();
        this.weight = piece.getWeight();
        this.cost = piece.getCost();
        this.remarks = piece.getRemarks();
        this.activeFlag = piece.getActiveFlag();
        this.warehouseId = piece.getWarehouseId();
         this.isStockTransfer = piece.getIsStockTransfer();
        this.stockTransferId = piece.getStockTransferId();
        this.sourceType = piece.getSourceType();
        this.poType = piece.getPoType();
        this.fabricTypeId = piece.getFabricTypeId();
        this.fabricMasterId = piece.getFabricMasterId();
        this.inspectionStatus = piece.getInspectionStatus() != null 
                ? piece.getInspectionStatus() 
                : "PENDING";
                 this.fabricId = piece.getFabricId();
        this.noOfFabricOutput = piece.getNoOfFabricOutput();

        // NEW fields
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
        // Compute pieceNumberComputed as Piece_Prefix + Piece_No
        this.pieceNumberComputed = (piece.getPiecePrefix() != null ? piece.getPiecePrefix() : "") + 
                                   (piece.getPieceNo() != null ? piece.getPieceNo().toString() : "");
        this.inspectorId = piece.getInspectorId();
        this.isPacked = piece.getIsPacked();
        this.packingId = piece.getPackingId();
        this.isDispatched = piece.getIsDispatched();
        this.dispatchId = piece.getDispatchId();
        this.isInvoice = piece.getIsInvoice();
        this.invoiceId = piece.getInvoiceId();
        this.isRejected = piece.getIsRejected();
        this.rejectedReason = piece.getRejectedReason();
        this.packLaterReason = piece.getPackLaterReason();
        this.isFactoryFabric = piece.getIsFactoryFabric();
        this.orderNo = piece.getOrderNo();
        this.orderId = piece.getOrderId();
        this.isLastPiece = piece.getIsLastPiece();
        this.loomId = piece.getLoomId();
        this.beamNo = piece.getBeamNo();
        this.flangeId = piece.getFlangeId();
        this.flangeNo = piece.getFlangeNo();
        this.beamId = piece.getBeamId();
        this.doffingPicks = piece.getDoffingPicks();
        this.companyId = piece.getCompanyId();
        this.doffingDate = piece.getDoffingDate();
        this.doffingNo = piece.getDoffingNo();
        this.pieceMeter = piece.getPieceMeter();
        this.remainingMtr = piece.getRemainingMtr();
        this.parentPieceId = piece.getParentPieceId();
        this.joinedPieceId = piece.getJoinedPieceId();
        this.isParentPiece = piece.getIsParentPiece();
        this.isJoined = piece.getIsJoined();
        this.operationId = piece.getOperationId();
        this.modeId = piece.getModeId();
        this.orderTypeId = piece.getOrderTypeId();
         this.inwardDate = piece.getInwardDate();
        this.grossWt = piece.getGrossWt();
        this.dcNumber = piece.getDcNumber();
        this.dcDate = piece.getDcDate();
        this.poCategoryId = piece.getPoCategoryId();
        
        // Brushing population
        this.brushingStatus = piece.getBrushingStatus();
//        this.brushingIssueId = piece.getBrushingIssueId();
        this.brushingReceiveId = piece.getBrushingReceiveId();
        this.brushedMeters = piece.getBrushedMeters();
        this.brushedWeight = piece.getBrushedWeight();
        this.shrinkagePercentage = piece.getShrinkagePercentage();
        this.weightLossPercentage = piece.getWeightLossPercentage();
        this.isBrushed = piece.getIsBrushed();

        // Warp & Weft details population
        this.warpSetNo = piece.getWarpSetNo();
        this.warpCount = piece.getWarpCount();
        this.warpYarnMillName = piece.getWarpYarnMillName();
        this.warpYarnLotNo = piece.getWarpYarnLotNo();
        this.weftCount = piece.getWeftCount();
        this.weftYarnMillName = piece.getWeftYarnMillName();
        this.weftYarnLotNo = piece.getWeftYarnLotNo();

        // New Fabric Stock & Excel fields population
        this.passMtr = piece.getPassMtr();
        this.inspectionDate = piece.getInspectionDate();
        this.rollingDate = piece.getRollingDate();
        this.rollWeight = piece.getRollWeight();
        this.glm = piece.getGlm();
        this.hfplRollNo = piece.getHfplRollNo();
        this.dispatchMtr = piece.getDispatchMtr();
        this.dispatchDate = piece.getDispatchDate();
        this.hfplInvoiceNo = piece.getHfplInvoiceNo();
        this.fabricStatus = piece.getFabricStatus();
    }

    // Existing getters/setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(String pieceNumber) {
        this.pieceNumber = pieceNumber;
    }


    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    public Long getFabricTypeId() {
        return fabricTypeId;
    }

    public void setFabricTypeId(Long fabricTypeId) {
        this.fabricTypeId = fabricTypeId;
    }
    
    public Long getFabricMasterId() {
        return fabricMasterId;
    }

 public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }
    
    public Double getNoOfFabricOutput() {
        return noOfFabricOutput;
    }

    public void setNoOfFabricOutput(Double noOfFabricOutput) {
        this.noOfFabricOutput = noOfFabricOutput;
    }
    
    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }
    
    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    // NEW GETTERS/SETTERS
    public Integer getIsInspected() {
        return isInspected;
    }

    public void setIsInspected(Integer isInspected) {
        this.isInspected = isInspected;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public BigDecimal getInspectedMtr() {
        return inspectedMtr;
    }

    public void setInspectedMtr(BigDecimal inspectedMtr) {
        this.inspectedMtr = inspectedMtr;
    }

    public BigDecimal getInspectedNtWt() {
        return inspectedNtWt;
    }

    public void setInspectedNtWt(BigDecimal inspectedNtWt) {
        this.inspectedNtWt = inspectedNtWt;
    }

    public BigDecimal getInspectedAvgWt() {
        return inspectedAvgWt;
    }

    public void setInspectedAvgWt(BigDecimal inspectedAvgWt) {
        this.inspectedAvgWt = inspectedAvgWt;
    }

    public String getDefectType() {
        return defectType;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    public BigDecimal getDefectPoints() {
        return defectPoints;
    }

    public void setDefectPoints(BigDecimal defectPoints) {
        this.defectPoints = defectPoints;
    }

    public Integer getPieceNo() {
        return pieceNo;
    }

    public void setPieceNo(Integer pieceNo) {
        this.pieceNo = pieceNo;
    }

    public String getPiecePrefix() {
        return piecePrefix;
    }

    public void setPiecePrefix(String piecePrefix) {
        this.piecePrefix = piecePrefix;
    }

    public String getPieceNumberComputed() {
        return pieceNumberComputed;
    }

    public void setPieceNumberComputed(String pieceNumberComputed) {
        this.pieceNumberComputed = pieceNumberComputed;
    }

    public Long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Integer getIsPacked() {
        return isPacked;
    }

    public void setIsPacked(Integer isPacked) {
        this.isPacked = isPacked;
    }

    public Long getPackingId() {
        return packingId;
    }

    public void setPackingId(Long packingId) {
        this.packingId = packingId;
    }

    public Integer getIsDispatched() {
        return isDispatched;
    }

    public void setIsDispatched(Integer isDispatched) {
        this.isDispatched = isDispatched;
    }

    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPoCategoryId() {
        return poCategoryId;
    }

    public void setPoCategoryId(Long poCategoryId) {
        this.poCategoryId = poCategoryId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Integer getIsLastPiece() {
        return isLastPiece;
    }

    public void setIsLastPiece(Integer isLastPiece) {
        this.isLastPiece = isLastPiece;
    }

    public Long getLoomId() {
        return loomId;
    }

    public void setLoomId(Long loomId) {
        this.loomId = loomId;
    }

    public String getBeamNo() {
        return beamNo;
    }

    public void setBeamNo(String beamNo) {
        this.beamNo = beamNo;
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

    public Long getBeamId() {
        return beamId;
    }

    public void setBeamId(Long beamId) {
        this.beamId = beamId;
    }

    public String getDoffingPicks() {
        return doffingPicks;
    }

    public void setDoffingPicks(String doffingPicks) {
        this.doffingPicks = doffingPicks;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getPieceMeter() {
        return pieceMeter;
    }

    public void setPieceMeter(String pieceMeter) {
        this.pieceMeter = pieceMeter;
    }

    public BigDecimal getRemainingMtr() {
        return remainingMtr;
    }

    public void setRemainingMtr(BigDecimal remainingMtr) {
        this.remainingMtr = remainingMtr;
    }

    public String getDoffingDate() {
        return doffingDate;
    }

    public void setDoffingDate(String doffingDate) {
        this.doffingDate = doffingDate;
    }

    public String getDoffingNo() {
        return doffingNo;
    }

    public void setDoffingNo(String doffingNo) {
        this.doffingNo = doffingNo;
    }

 public Integer getIsFactoryFabric() {
        return isFactoryFabric;
    }

    public void setIsFactoryFabric(Integer isFactoryFabric) {
        this.isFactoryFabric = isFactoryFabric;
    }
    
    public Long getParentPieceId() {
        return parentPieceId;
    }

    public void setParentPieceId(Long parentPieceId) {
        this.parentPieceId = parentPieceId;
    }

    public Long getJoinedPieceId() {
        return joinedPieceId;
    }

    public void setJoinedPieceId(Long joinedPieceId) {
        this.joinedPieceId = joinedPieceId;
    }

    public Integer getIsParentPiece() {
        return isParentPiece;
    }

    public void setIsParentPiece(Integer isParentPiece) {
        this.isParentPiece = isParentPiece;
    }

    public Integer getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(Integer isJoined) {
        this.isJoined = isJoined;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

       // NEW FIELDS: isStockTransfer and stockTransferId
    public Boolean getIsStockTransfer() {
        return isStockTransfer;
    }

    public void setIsStockTransfer(Boolean isStockTransfer) {
        this.isStockTransfer = isStockTransfer;
    }

    public Long getStockTransferId() {
        return stockTransferId;
    }

    public void setStockTransferId(Long stockTransferId) {
        this.stockTransferId = stockTransferId;
    }

    public LocalDate getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(LocalDate inwardDate) {
        this.inwardDate = inwardDate;
    }

    public BigDecimal getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(BigDecimal grossWt) {
        this.grossWt = grossWt;
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

    // BRUSHING GETTERS/SETTERS
    public String getBrushingStatus() {
        return brushingStatus;
    }

    public void setBrushingStatus(String brushingStatus) {
        this.brushingStatus = brushingStatus;
    }

    public Long getBrushingIssueId() {
        return brushingIssueId;
    }

    public void setBrushingIssueId(Long brushingIssueId) {
        this.brushingIssueId = brushingIssueId;
    }

    public Long getBrushingReceiveId() {
        return brushingReceiveId;
    }

    public void setBrushingReceiveId(Long brushingReceiveId) {
        this.brushingReceiveId = brushingReceiveId;
    }

    public BigDecimal getBrushedMeters() {
        return brushedMeters;
    }

    public void setBrushedMeters(BigDecimal brushedMeters) {
        this.brushedMeters = brushedMeters;
    }

    public BigDecimal getBrushedWeight() {
        return brushedWeight;
    }

    public void setBrushedWeight(BigDecimal brushedWeight) {
        this.brushedWeight = brushedWeight;
    }

    public BigDecimal getShrinkagePercentage() {
        return shrinkagePercentage;
    }

    public void setShrinkagePercentage(BigDecimal shrinkagePercentage) {
        this.shrinkagePercentage = shrinkagePercentage;
    }

    public BigDecimal getWeightLossPercentage() {
        return weightLossPercentage;
    }

    public void setWeightLossPercentage(BigDecimal weightLossPercentage) {
        this.weightLossPercentage = weightLossPercentage;
    }

    public Integer getIsBrushed() {
        return isBrushed;
    }

    public void setIsBrushed(Integer isBrushed) {
        this.isBrushed = isBrushed;
    }

    public Integer getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Integer isRejected) {
        this.isRejected = isRejected;
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

    public BigDecimal getPassMtr() { return passMtr; }
    public void setPassMtr(BigDecimal passMtr) { this.passMtr = passMtr; }

    public LocalDate getInspectionDate() { return inspectionDate; }
    public void setInspectionDate(LocalDate inspectionDate) { this.inspectionDate = inspectionDate; }

    public LocalDate getRollingDate() { return rollingDate; }
    public void setRollingDate(LocalDate rollingDate) { this.rollingDate = rollingDate; }

    public BigDecimal getRollWeight() { return rollWeight; }
    public void setRollWeight(BigDecimal rollWeight) { this.rollWeight = rollWeight; }

    public BigDecimal getGlm() { return glm; }
    public void setGlm(BigDecimal glm) { this.glm = glm; }

    public String getHfplRollNo() { return hfplRollNo; }
    public void setHfplRollNo(String hfplRollNo) { this.hfplRollNo = hfplRollNo; }

    public BigDecimal getDispatchMtr() { return dispatchMtr; }
    public void setDispatchMtr(BigDecimal dispatchMtr) { this.dispatchMtr = dispatchMtr; }

    public LocalDate getDispatchDate() { return dispatchDate; }
    public void setDispatchDate(LocalDate dispatchDate) { this.dispatchDate = dispatchDate; }

    public String getHfplInvoiceNo() { return hfplInvoiceNo; }
    public void setHfplInvoiceNo(String hfplInvoiceNo) { this.hfplInvoiceNo = hfplInvoiceNo; }

    public String getFabricStatus() { return fabricStatus; }
    public void setFabricStatus(String fabricStatus) { this.fabricStatus = fabricStatus; }
}