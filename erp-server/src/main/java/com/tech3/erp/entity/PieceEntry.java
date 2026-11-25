package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "piece_entry", schema = "masters")
public class PieceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "piece_number", length = 1024)
    private String pieceNumber;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;
    
    // ============ EXISTING FIELDS ============
    @Column(name = "inspection_status", length = 50)
    @JsonProperty("inspectionStatus")
    private String inspectionStatus = "PENDING"; // 'PENDING' or 'INSPECTED'
    
    @Column(name = "source_type", length = 50)
    private String sourceType; // 'purchase_inward' or 'jobwork_fabric_receive'
    
    @Column(name = "po_type", length = 50)
    private String poType; // 'Yarn', 'Woven Fabric', 'Knitted Fabric', etc.
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobwork_fabric_receive_item_id", nullable = true, foreignKey = @ForeignKey(name = "fk_piece_jobwork"))
    @JsonBackReference
    private JobworkFabricReceiveItem jobworkFabricReceiveItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_inward_item_id", nullable = true, foreignKey = @ForeignKey(name = "fk_piece_purchase_inward"))
    @JsonBackReference("purchaseInwardPieces")
    private PurchaseInwardItem purchaseInwardItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finish_fabric_receive_item_id", nullable = true, foreignKey = @ForeignKey(name = "fk_piece_finish_fabric"))
    @JsonBackReference
    private FinishFabricReceiveItem finishFabricReceiveItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonIgnore
    private Warehouse warehouse;
    
    @Column(name = "fabric_type_id")
    private Long fabricTypeId;
    
    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

       @Column(name = "fabric_id")
    private Long fabricId;

    @Column(name = "no_of_fabric_output")
    private Double noOfFabricOutput;
    
    // ============ NEW FIELDS FOR INSPECTION INTEGRATION ============
    
    // 4. Is_Inspected
    @Column(name = "is_inspected")
    private Integer isInspected = 0; // 0 = Not Inspected, 1 = Inspected
    
    // 5. Inspection_Id
    @Column(name = "inspection_id")
    private Long inspectionId;
    
    // 6. Grade_Id
    @Column(name = "grade_id")
    private Long gradeId;
    
    // 7. Inspected_Mtr
    @Column(name = "inspected_mtr", precision = 19, scale = 3)
    private BigDecimal inspectedMtr;
    
    // 8. Inspected_Nt_Wt
    @Column(name = "inspected_nt_wt", precision = 19, scale = 3)
    private BigDecimal inspectedNtWt;
    
    // 9. Inspected_Avg_Wt
    @Column(name = "inspected_avg_wt", precision = 19, scale = 6)
    private BigDecimal inspectedAvgWt;
    
    // 10. Defect_Type
    @Column(name = "defect_type", length = 255)
    private String defectType;
    
    // 11. Defect_Points
    @Column(name = "defect_points", precision = 19, scale = 3)
    private BigDecimal defectPoints;
    
    // 12. Piece_No (Auto-numbered sequence)
    @Column(name = "piece_no")
    private Integer pieceNo;
    
    // 13. Piece_Prefix (GFP or FFP)
    @Column(name = "piece_prefix", length = 10)
    private String piecePrefix;
    
    // 14. Piece_Number is already present (unique identifier)
    // This will be concatenated as Piece_Prefix + Piece_No in getPieceNumber()
    
    // 15. Inspector_ID
    @Column(name = "inspector_id")
    private Long inspectorId;
    
    // ============ NEW FIELDS FOR PACKING INTEGRATION ============
    
    // 16. Is_Packed
    @Column(name = "is_packed")
    private Integer isPacked = 0; // 0 = Not Packed, 1 = Packed
    
    // 17. Packing_Id (stores Bale/Roll Id)
    @Column(name = "packing_id")
    private Long packingId;
    
    // 17b. Is_Rejected
    @Column(name = "is_rejected")
    private Integer isRejected = 0; // 0 = Active, 1 = Rejected

    // 17c. Rejected_Reason
    @Column(name = "rejected_reason", length = 1000)
    private String rejectedReason;

    // 17d. Pack_Later_Reason
    @Column(name = "pack_later_reason", length = 1000)
    private String packLaterReason;
    
    // ============ NEW FIELDS FOR DISPATCH INTEGRATION ============
    
    // 18. Is_Dispatched
    @Column(name = "is_dispatched")
    private Integer isDispatched = 0; // 0 = Not Dispatched, 1 = Dispatched
    
    // 19. Dispatch_Id (stores Packing List Id)
    @Column(name = "dispatch_id")
    private Long dispatchId;
    
    // ============ NEW FIELD FOR INVOICE INTEGRATION ============
    
    // 20. Is_Invoice
    @Column(name = "is_invoice")
    private Integer isInvoice = 0; // 0 = Not Invoiced, 1 = Invoiced
    
    // 21. Invoice_Id
    @Column(name = "invoice_id")
    private Long invoiceId;
    
   
    
    @Column(name = "order_no", length = 255)
    private String orderNo;

    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "is_last_piece")
    private Integer isLastPiece = 0;
    
    @Column(name = "loom_id")
    private Long loomId;
    
    @Column(name = "beam_no")
    private String beamNo;
    
    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "flange_no", length = 100)
    private String flangeNo;
    
    @Column(name = "beam_id")
    private Long beamId;
    
    @Column(name = "doffing_picks")
    private String doffingPicks;
    
    @Column(name = "company_id")
    private Long companyId;
    
    @Column(name = "doffing_date")
    private String doffingDate;
    
    @Column(name = "doffing_no")
    private String doffingNo;
    
    @Column(name = "piece_meter")
    private String pieceMeter;

    @Column(name = "remaining_mtr", precision = 19, scale = 3)
    private BigDecimal remainingMtr;

     @Column(name = "is_factory_fabric")
    private Integer isFactoryFabric = 0;

    @Column(name = "parent_piece_id")
    private Long parentPieceId;

    @Column(name = "joined_piece_id")
    private Long joinedPieceId;

    @Column(name = "is_parent_piece")
    private Integer isParentPiece = 0;

    @Column(name = "is_joined")
    private Integer isJoined = 0;

    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "mode_id")
    private Long modeId;

    @Column(name = "order_type_id")
    private Long orderTypeId;
    
    
    // ===== Opening Balance Integration =====
    @Column(name = "is_opening_balance")
    private Boolean isOpeningBalance = false;

    @Column(name = "opening_balance_id")
    private Long openingBalanceId;

        @Column(name = "inward_date")
    private LocalDate inwardDate;

    @Column(name = "inward_id")
    private Long inwardId;

      @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date")
    private LocalDate dcDate;

     @Column(name = "po_category_id")
     private Long poCategoryId;

    @PrePersist
    public void prePersist() {
        if (this.pieceNumber == null || this.pieceNumber.isEmpty()) {
            this.pieceNumber = generatePieceNumber();
        }
        if (this.inspectionStatus == null) {
            this.inspectionStatus = "PENDING";
        }
        if (this.isInvoice == null) {
            this.isInvoice = 0;
        }
       
        if (this.isLastPiece == null) {
            this.isLastPiece = 0;
        }
        if (this.isRejected == null) {
            this.isRejected = 0;
        }
         if (this.isFactoryFabric == null) {
            this.isFactoryFabric = 0;
        }
        if (this.isParentPiece == null) {
            this.isParentPiece = 0;
        }
        if (this.isJoined == null) {
            this.isJoined = 0;
        }
        if (this.operationId == null) {
            this.operationId = 0L;
        }
        if (this.modeId == null) {
            this.modeId = 0L;
        }
        if (this.parentPieceId == null) {
            this.parentPieceId = 0L;
        }
        if (this.joinedPieceId == null) {
            this.joinedPieceId = 0L;
        }
        if (this.isStockTransfer == null) {
            this.isStockTransfer = false;
        }
        if (this.piecePrefix == null && this.sourceType != null) {
            this.piecePrefix = determinePiecePrefix();
        }

           if (this.inwardDate == null && (("doffing_entry".equalsIgnoreCase(this.sourceType)) || (this.isFactoryFabric != null && this.isFactoryFabric == 1)) && this.doffingDate != null) {
            try {
                this.inwardDate = LocalDate.parse(this.doffingDate);
            } catch (Exception e) {
                // Ignore if parsing fails
            }
        }
        if (this.fabricMasterId != null) {
            this.fabricId = this.fabricMasterId;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.fabricMasterId != null) {
            this.fabricId = this.fabricMasterId;
        }
    }

    private String generatePieceNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "PNO-" + datePart + "-" + randomNum;
    }
    
    /**
     * Determines piece prefix based on fabric management type
     * GFP – Greige Fabric Piece (for Purchase Inward Greige or Weaving Jobwork Receive)
     * FFP – Finished Fabric Piece (for Purchase Inward Finished/Finished Shade or Processing Fabric Receive)
     */
    private String determinePiecePrefix() {
        if ("purchase_inward".equalsIgnoreCase(this.sourceType)) {
            // Check poType for Greige vs Finished
            if (this.poType != null && this.poType.toLowerCase().contains("greige")) {
                return "GFP";
            } else {
                return "FFP"; // Finished or Finished Shade
            }
        } else if ("jobwork_fabric_receive".equalsIgnoreCase(this.sourceType)) {
            // Weaving Jobwork = GFP, Processing = FFP
            if (this.poType != null && this.poType.toLowerCase().contains("weaving")) {
                return "GFP";
            } else {
                return "FFP"; // Processing
            }
        }
        return "FFP"; // Default to Finished
    }

    // ============ GETTERS AND SETTERS ============

    public Long getId() {
        return id;
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

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
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

    public JobworkFabricReceiveItem getJobworkFabricReceiveItem() {
        return jobworkFabricReceiveItem;
    }

    public void setJobworkFabricReceiveItem(JobworkFabricReceiveItem jobworkFabricReceiveItem) {
        this.jobworkFabricReceiveItem = jobworkFabricReceiveItem;
    }

    public PurchaseInwardItem getPurchaseInwardItem() {
        return purchaseInwardItem;
    }

    public void setPurchaseInwardItem(PurchaseInwardItem purchaseInwardItem) {
        this.purchaseInwardItem = purchaseInwardItem;
    }

    public FinishFabricReceiveItem getFinishFabricReceiveItem() {
        return finishFabricReceiveItem;
    }

    public void setFinishFabricReceiveItem(FinishFabricReceiveItem finishFabricReceiveItem) {
        this.finishFabricReceiveItem = finishFabricReceiveItem;
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

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
        this.fabricId = fabricMasterId;
    }

    @JsonProperty("warehouseId")
    public Long getWarehouseId() {
        return warehouse != null ? warehouse.getId() : null;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    // NEW GETTERS AND SETTERS
    
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

    public Integer getIsFactoryFabric() {
        return isFactoryFabric;
    }

    public void setIsFactoryFabric(Integer isFactoryFabric) {
        this.isFactoryFabric = isFactoryFabric;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public BigDecimal getRemainingMtr() {
        return remainingMtr;
    }

    public void setRemainingMtr(BigDecimal remainingMtr) {
        this.remainingMtr = remainingMtr;
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

    public String getDoffingDate() {
        return doffingDate;
    }

    public void setDoffingDate(String doffingDate) {
        this.doffingDate = doffingDate;
    }

    public String getPieceMeter() {
        return pieceMeter;
    }

    public void setPieceMeter(String pieceMeter) {
        this.pieceMeter = pieceMeter;
    }

    public String getDoffingNo() {
        return doffingNo;
    }

    public void setDoffingNo(String doffingNo) {
        this.doffingNo = doffingNo;
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
    public Boolean getIsOpeningBalance() {
        return isOpeningBalance;
    }

    public void setIsOpeningBalance(Boolean isOpeningBalance) {
        this.isOpeningBalance = isOpeningBalance;
    }

    public Long getOpeningBalanceId() {
        return openingBalanceId;
    }

    public void setOpeningBalanceId(Long openingBalanceId) {
        this.openingBalanceId = openingBalanceId;
    }
    
    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        if (this.fabricMasterId != null) {
            this.fabricId = this.fabricMasterId;
        } else {
            this.fabricId = fabricId;
        }
    }

    public Double getNoOfFabricOutput() {
        return noOfFabricOutput;
    }

    public void setNoOfFabricOutput(Double noOfFabricOutput) {
        this.noOfFabricOutput = noOfFabricOutput;
    }

      // ============ NEW FIELDS FOR STOCK TRANSFER ============
    @Column(name = "gross_wt", precision = 19, scale = 3)
    private BigDecimal grossWt;

    @Column(name = "is_stock_transfer")
    private Boolean isStockTransfer = false;

    @Column(name = "stock_transfer_id")
    private Long stockTransferId;

        public BigDecimal getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(BigDecimal grossWt) {
        this.grossWt = grossWt;
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

    public Long getInwardId() {
        return inwardId;
    }

    public void setInwardId(Long inwardId) {
        this.inwardId = inwardId;
    }

    public String getDcNumber() { return dcNumber; }

    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

    public LocalDate getDcDate() { return dcDate; }

    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    // ============ BRUSHING INTEGRATION ============
    @Column(name = "brushing_status", length = 50)
    private String brushingStatus = "NOT_STARTED"; // 'NOT_STARTED', 'ISSUED', 'RECEIVED'

    @Column(name = "brushing_receive_id")
    private Long brushingReceiveId;

    @Column(name = "brushed_meters", precision = 19, scale = 3)
    private BigDecimal brushedMeters;

    @Column(name = "brushed_weight", precision = 19, scale = 3)
    private BigDecimal brushedWeight;

    @Column(name = "shrinkage_percentage", precision = 10, scale = 2)
    private BigDecimal shrinkagePercentage;

    @Column(name = "weight_loss_percentage", precision = 10, scale = 2)
    private BigDecimal weightLossPercentage;

    @Column(name = "is_brushed")
    private Integer isBrushed = 0;

    public String getBrushingStatus() {
        return brushingStatus;
    }

    public void setBrushingStatus(String brushingStatus) {
        this.brushingStatus = brushingStatus;
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
        if (isBrushed != null) {
            if (isBrushed == 1) {
                if (this.pieceNumber != null && !this.pieceNumber.endsWith("_BR")) {
                    this.pieceNumber = this.pieceNumber + "_BR";
                }
            } else {
                if (this.pieceNumber != null && this.pieceNumber.endsWith("_BR")) {
                    this.pieceNumber = this.pieceNumber.substring(0, this.pieceNumber.length() - 3);
                }
            }
        }
    }

    public Long getPoCategoryId() {
        return poCategoryId;
    }

    public void setPoCategoryId(Long poCategoryId) {
        this.poCategoryId = poCategoryId;
    }

    // ============ WARP & WEFT YARN DETAILS ============
    @Column(name = "warp_set_no")
    private String warpSetNo;

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

    // ============ NEW FABRIC STOCK & EXCEL UPLOAD FIELDS ============
    @Column(name = "pass_mtr", precision = 19, scale = 3)
    private BigDecimal passMtr;

    @Column(name = "inspection_date")
    private LocalDate inspectionDate;

    @Column(name = "rolling_date")
    private LocalDate rollingDate;

    @Column(name = "roll_weight", precision = 19, scale = 3)
    private BigDecimal rollWeight;

    @Column(name = "glm", precision = 19, scale = 3)
    private BigDecimal glm;

    @Column(name = "hfpl_roll_no", length = 255)
    private String hfplRollNo;

    @Column(name = "dispatch_mtr", precision = 19, scale = 3)
    private BigDecimal dispatchMtr;

    @Column(name = "dispatch_date")
    private LocalDate dispatchDate;

    @Column(name = "hfpl_invoice_no", length = 255)
    private String hfplInvoiceNo;

    @Column(name = "fabric_status", length = 50)
    private String fabricStatus;

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