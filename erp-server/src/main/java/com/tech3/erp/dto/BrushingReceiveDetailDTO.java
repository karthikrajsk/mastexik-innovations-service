package com.tech3.erp.dto;

import java.math.BigDecimal;

public class BrushingReceiveDetailDTO {
    private Long id;
    private Long pieceEntryId;
    private String pieceNumber;
    private BigDecimal greigeMeters; // transient/helper
    private BigDecimal greigeWeight; // transient/helper
    private BigDecimal brushedMeters;
    private BigDecimal brushedWeight;
    private BigDecimal shrinkagePercentage;
    private BigDecimal weightLossPercentage;
    private Integer isBrushed;
    private BigDecimal costPerUnit;
    private BigDecimal totalAmount;
    private BigDecimal greigeGrossWt;
    private BigDecimal brushedGrossWt;
    private String tc;
    private String weave;
    private String date;
    private String partyName;
    private String invoiceNo;
    private String construction;
    private BigDecimal glm;
    private Integer isPacked;
    private Integer isDispatched;
    private Integer isInvoice;
    private BigDecimal passMtr;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPieceEntryId() {
        return pieceEntryId;
    }

    public void setPieceEntryId(Long pieceEntryId) {
        this.pieceEntryId = pieceEntryId;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(String pieceNumber) {
        this.pieceNumber = pieceNumber;
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

    private Long vendorId;
    private Long fabricMasterId;

    // --- Unwinding & Verification Fields ---
    private BigDecimal unwoundMeters;
    private BigDecimal unwoundWeight;
    private BigDecimal unwoundGrossWt;
    private java.time.LocalDate unwindDate;
    private java.time.LocalDate unwindedDate;
    private String unwindStatus;
    private String unwindRemarks;
    private Integer isUnwound;
    private Integer isRejected = 0;
    private java.time.LocalDate rejectionDate;
    private String rejectedReason;
    private Integer isReturn = 0;
    private java.time.LocalDate returnDate;
    private String returnReason;
    private String hfplDcNo;
    private String dcNo;
    private String returnType;

    // --- Brushing Process (Production) Fields ---
    private java.time.LocalDate brushingDate;
    private String brushingMachineNo;
    private Long machineId;
    private String brushingStatus;
    private String hfplRollNo;
    private String hfplInvoiceNo;
    private java.time.LocalDate rollingDate;
    private java.time.LocalDate dispatchDate;

    private java.time.LocalDateTime unwindUpdatedAt;
    private String unwindUpdatedBy;
    private java.time.LocalDateTime brushingUpdatedAt;
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

    public String getHfplRollNo() {
        return hfplRollNo;
    }

    public void setHfplRollNo(String hfplRollNo) {
        this.hfplRollNo = hfplRollNo;
    }

    public String getHfplInvoiceNo() {
        return hfplInvoiceNo;
    }

    public void setHfplInvoiceNo(String hfplInvoiceNo) {
        this.hfplInvoiceNo = hfplInvoiceNo;
    }

    public java.time.LocalDate getRollingDate() {
        return rollingDate;
    }

    public void setRollingDate(java.time.LocalDate rollingDate) {
        this.rollingDate = rollingDate;
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

    public java.time.LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(java.time.LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }
}
