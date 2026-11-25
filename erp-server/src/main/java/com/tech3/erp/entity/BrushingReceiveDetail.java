package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "brushing_receive_detail", schema = "masters")
public class BrushingReceiveDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brushing_receive_id", nullable = false)
    @JsonBackReference
    private BrushingReceive brushingReceive;



    @Column(name = "piece_number", length = 255)
    private String pieceNumber;

    @Column(name = "is_packed")
    private Integer isPacked = 0;

    @Column(name = "is_dispatched")
    private Integer isDispatched = 0;

    @Column(name = "is_invoice")
    private Integer isInvoice = 0;

    @Column(name = "greige_meters", precision = 19, scale = 3)
    private BigDecimal greigeMeters;

    @Column(name = "greige_weight", precision = 19, scale = 3)
    private BigDecimal greigeWeight;

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

    @Column(name = "cost_per_unit", precision = 19, scale = 3)
    private BigDecimal costPerUnit;

    @Column(name = "total_amount", precision = 19, scale = 3)
    private BigDecimal totalAmount;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "greige_gross_wt", precision = 19, scale = 3)
    private BigDecimal greigeGrossWt;

    @Column(name = "brushed_gross_wt", precision = 19, scale = 3)
    private BigDecimal brushedGrossWt;

    @Column(name = "tc_value", length = 255)
    private String tc;

    @Column(name = "weave", length = 255)
    private String weave;

    @Column(name = "row_date")
    private String date;

    @Column(name = "party_name", length = 255)
    private String partyName;

    @Column(name = "invoice_no", length = 255)
    private String invoiceNo;

    @Column(name = "construction", length = 1024)
    private String construction;

    @Column(name = "glm", precision = 19, scale = 4)
    private BigDecimal glm;

    @Column(name = "pass_mtr", precision = 19, scale = 3)
    private BigDecimal passMtr;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrushingReceive getBrushingReceive() {
        return brushingReceive;
    }

    public void setBrushingReceive(BrushingReceive brushingReceive) {
        this.brushingReceive = brushingReceive;
    }


    public BigDecimal getGreigeMeters() {
        return greigeMeters;
    }

    public void setGreigeMeters(BigDecimal greigeMeters) {
        this.greigeMeters = greigeMeters;
    }

    public BigDecimal getGreigeWeight() {
        return greigeWeight;
    }

    public void setGreigeWeight(BigDecimal greigeWeight) {
        this.greigeWeight = greigeWeight;
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

    public BigDecimal getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public BigDecimal getGreigeGrossWt() {
        return greigeGrossWt;
    }

    public void setGreigeGrossWt(BigDecimal greigeGrossWt) {
        this.greigeGrossWt = greigeGrossWt;
    }

    public BigDecimal getBrushedGrossWt() {
        return brushedGrossWt;
    }

    public void setBrushedGrossWt(BigDecimal brushedGrossWt) {
        this.brushedGrossWt = brushedGrossWt;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getWeave() {
        return weave;
    }

    public void setWeave(String weave) {
        this.weave = weave;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public BigDecimal getGlm() {
        return glm;
    }

    public void setGlm(BigDecimal glm) {
        this.glm = glm;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(String pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public Integer getIsPacked() {
        return isPacked;
    }

    public void setIsPacked(Integer isPacked) {
        this.isPacked = isPacked;
    }

    public Integer getIsDispatched() {
        return isDispatched;
    }

    public void setIsDispatched(Integer isDispatched) {
        this.isDispatched = isDispatched;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    // --- Unwinding & Verification Fields ---
    @Column(name = "unwound_meters", precision = 19, scale = 3)
    private BigDecimal unwoundMeters;

    @Column(name = "unwound_weight", precision = 19, scale = 3)
    private BigDecimal unwoundWeight;

    @Column(name = "unwound_gross_wt", precision = 19, scale = 3)
    private BigDecimal unwoundGrossWt;

    @Column(name = "unwind_date")
    private java.time.LocalDate unwindDate;

    @Column(name = "unwinded_date")
    private java.time.LocalDate unwindedDate;

    @Column(name = "unwind_status", length = 50)
    private String unwindStatus = "PENDING"; // PENDING, ACCEPTED, REJECTED

    @Column(name = "unwind_remarks", length = 1024)
    private String unwindRemarks;

    @Column(name = "is_unwound")
    private Integer isUnwound = 0; // 0 = No, 1 = Yes

    @Column(name = "is_rejected")
    private Integer isRejected = 0;

    @Column(name = "rejection_date")
    private java.time.LocalDate rejectionDate;

    @Column(name = "rejected_reason", length = 1024)
    private String rejectedReason;

    @Column(name = "is_return")
    private Integer isReturn = 0;

    @Column(name = "return_date")
    private java.time.LocalDate returnDate;

    @Column(name = "return_reason", length = 1024)
    private String returnReason;

    @Column(name = "hfpl_dc_no", length = 100)
    private String hfplDcNo;

    @Column(name = "dc_no", length = 100)
    private String dcNo;

    @Column(name = "return_type", length = 50)
    private String returnType;

    public Integer getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Integer isRejected) {
        this.isRejected = isRejected;
    }

    public java.time.LocalDate getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(java.time.LocalDate rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
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

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    // --- Brushing Process (Production) Fields ---
    @Column(name = "brushing_date")
    private java.time.LocalDate brushingDate;

    @Column(name = "brushing_machine_no", length = 255)
    private String brushingMachineNo;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "brushing_status", length = 50)
    private String brushingStatus = "PENDING"; // PENDING, IN_PROCESS, COMPLETED

    @Column(name = "hfpl_invoice_no", length = 255)
    private String hfplInvoiceNo;

    @Column(name = "po_no", length = 255)
    private String poNo;

    @Column(name = "po_date")
    private java.time.LocalDate poDate;

    @Column(name = "hfpl_roll_no", length = 255)
    private String hfplRollNo;

    @Column(name = "rolling_date")
    private java.time.LocalDate rollingDate;

    @Column(name = "dispatch_date")
    private java.time.LocalDate dispatchDate;

    @Column(name = "unwind_updated_at")
    private java.time.LocalDateTime unwindUpdatedAt;

    @Column(name = "unwind_updated_by", length = 255)
    private String unwindUpdatedBy;

    @Column(name = "brushing_updated_at")
    private java.time.LocalDateTime brushingUpdatedAt;

    @Column(name = "brushing_updated_by", length = 255)
    private String brushingUpdatedBy;

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public BigDecimal getUnwoundMeters() {
        return unwoundMeters;
    }

    public void setUnwoundMeters(BigDecimal unwoundMeters) {
        this.unwoundMeters = unwoundMeters;
    }

    public BigDecimal getUnwoundWeight() {
        return unwoundWeight;
    }

    public void setUnwoundWeight(BigDecimal unwoundWeight) {
        this.unwoundWeight = unwoundWeight;
    }

    public BigDecimal getUnwoundGrossWt() {
        return unwoundGrossWt;
    }

    public void setUnwoundGrossWt(BigDecimal unwoundGrossWt) {
        this.unwoundGrossWt = unwoundGrossWt;
    }

    public java.time.LocalDate getUnwindDate() {
        return unwindDate;
    }

    public void setUnwindDate(java.time.LocalDate unwindDate) {
        this.unwindDate = unwindDate;
    }

    public java.time.LocalDate getUnwindedDate() {
        return unwindedDate;
    }

    public void setUnwindedDate(java.time.LocalDate unwindedDate) {
        this.unwindedDate = unwindedDate;
    }

    public String getUnwindStatus() {
        return unwindStatus;
    }

    public void setUnwindStatus(String unwindStatus) {
        this.unwindStatus = unwindStatus;
    }

    public String getUnwindRemarks() {
        return unwindRemarks;
    }

    public void setUnwindRemarks(String unwindRemarks) {
        this.unwindRemarks = unwindRemarks;
    }

    public Integer getIsUnwound() {
        return isUnwound;
    }

    public void setIsUnwound(Integer isUnwound) {
        this.isUnwound = isUnwound;
    }

    public java.time.LocalDate getBrushingDate() {
        return brushingDate;
    }

    public void setBrushingDate(java.time.LocalDate brushingDate) {
        this.brushingDate = brushingDate;
    }

    public String getBrushingMachineNo() {
        return brushingMachineNo;
    }

    public void setBrushingMachineNo(String brushingMachineNo) {
        this.brushingMachineNo = brushingMachineNo;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getBrushingStatus() {
        return brushingStatus;
    }

    public void setBrushingStatus(String brushingStatus) {
        this.brushingStatus = brushingStatus;
    }

    public BigDecimal getPassMtr() {
        return passMtr;
    }

    public void setPassMtr(BigDecimal passMtr) {
        this.passMtr = passMtr;
    }

    public String getHfplInvoiceNo() {
        return hfplInvoiceNo;
    }

    public void setHfplInvoiceNo(String hfplInvoiceNo) {
        this.hfplInvoiceNo = hfplInvoiceNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public java.time.LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(java.time.LocalDate poDate) {
        this.poDate = poDate;
    }

    public String getHfplRollNo() {
        return hfplRollNo;
    }

    public void setHfplRollNo(String hfplRollNo) {
        this.hfplRollNo = hfplRollNo;
    }

    public java.time.LocalDate getRollingDate() {
        return rollingDate;
    }

    public void setRollingDate(java.time.LocalDate rollingDate) {
        this.rollingDate = rollingDate;
    }

    public java.time.LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(java.time.LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public java.time.LocalDateTime getUnwindUpdatedAt() {
        return unwindUpdatedAt;
    }

    public void setUnwindUpdatedAt(java.time.LocalDateTime unwindUpdatedAt) {
        this.unwindUpdatedAt = unwindUpdatedAt;
    }

    public String getUnwindUpdatedBy() {
        return unwindUpdatedBy;
    }

    public void setUnwindUpdatedBy(String unwindUpdatedBy) {
        this.unwindUpdatedBy = unwindUpdatedBy;
    }

    public java.time.LocalDateTime getBrushingUpdatedAt() {
        return brushingUpdatedAt;
    }

    public void setBrushingUpdatedAt(java.time.LocalDateTime brushingUpdatedAt) {
        this.brushingUpdatedAt = brushingUpdatedAt;
    }

    public String getBrushingUpdatedBy() {
        return brushingUpdatedBy;
    }

    public void setBrushingUpdatedBy(String brushingUpdatedBy) {
        this.brushingUpdatedBy = brushingUpdatedBy;
    }

    public String getHfplDcNo() {
        return hfplDcNo;
    }

    public void setHfplDcNo(String hfplDcNo) {
        this.hfplDcNo = hfplDcNo;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
