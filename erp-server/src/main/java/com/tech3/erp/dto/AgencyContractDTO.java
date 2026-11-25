package com.tech3.erp.dto;

import com.tech3.erp.entity.AgencyContract;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AgencyContractDTO {

    private Long id;

    private String contractNo;

    @NotNull(message = "Contract Date is required")
    private LocalDate contractDate;

    @NotBlank(message = "Seller is required")
    private String seller;

    @NotBlank(message = "Buyer Name is required")
    private String buyerName;

    private String construction;

    private Double pieceLength;
    private String packing;

    @NotNull(message = "Ordered Quantity is required")
    private Double orderedQty;

    private Double suppliedQty = 0.0;
    private Double balanceQty;
    private String despatchSchedule;
    private String paymentTerms;
    private String ratePerMtr;
    private Double numericRate;
    private String cdRate;
    private String poNo;
    private LocalDate poDate;
    private String icilConfirm;
    private String lcOpen = "PENDING";
    private Double commissionRate = 0.01;
    private String status = "ACTIVE";
    private Short isApproved;
    private String deliveryLocation;
    private String remarks;
    private Long companyId;
    private Boolean activeFlag = true;

    public AgencyContractDTO() {}

    public AgencyContractDTO(AgencyContract entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.contractNo = entity.getContractNo();
            this.contractDate = entity.getContractDate();
            this.seller = entity.getSeller();
            this.buyerName = entity.getBuyerName();
            this.construction = entity.getConstruction();
            this.pieceLength = entity.getPieceLength();
            this.packing = entity.getPacking();
            this.orderedQty = entity.getOrderedQty();
            this.suppliedQty = entity.getSuppliedQty() != null ? entity.getSuppliedQty() : 0.0;
            this.balanceQty = entity.getBalanceQty() != null ? entity.getBalanceQty() : (this.orderedQty - this.suppliedQty);
            this.despatchSchedule = entity.getDespatchSchedule();
            this.paymentTerms = entity.getPaymentTerms();
            this.ratePerMtr = entity.getRatePerMtr();
            this.numericRate = entity.getNumericRate();
            this.cdRate = entity.getCdRate();
            this.poNo = entity.getPoNo();
            this.poDate = entity.getPoDate();
            this.icilConfirm = entity.getIcilConfirm();
            this.lcOpen = entity.getLcOpen() != null ? entity.getLcOpen() : "PENDING";
            this.commissionRate = entity.getCommissionRate() != null ? entity.getCommissionRate() : 0.01;
            this.status = entity.getStatus() != null ? entity.getStatus() : "ACTIVE";
            this.isApproved = entity.getIsApproved() != null ? entity.getIsApproved() : (short) 0;
            this.deliveryLocation = entity.getDeliveryLocation();
            this.remarks = entity.getRemarks();
            this.companyId = entity.getCompanyId();
            this.activeFlag = entity.getActiveFlag() != null ? entity.getActiveFlag() : true;
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContractNo() { return contractNo; }
    public void setContractNo(String contractNo) { this.contractNo = contractNo; }

    public LocalDate getContractDate() { return contractDate; }
    public void setContractDate(LocalDate contractDate) { this.contractDate = contractDate; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public Double getPieceLength() { return pieceLength; }
    public void setPieceLength(Double pieceLength) { this.pieceLength = pieceLength; }

    public String getPacking() { return packing; }
    public void setPacking(String packing) { this.packing = packing; }

    public Double getOrderedQty() { return orderedQty; }
    public void setOrderedQty(Double orderedQty) { this.orderedQty = orderedQty; }

    public Double getSuppliedQty() { return suppliedQty; }
    public void setSuppliedQty(Double suppliedQty) { this.suppliedQty = suppliedQty; }

    public Double getBalanceQty() { return balanceQty; }
    public void setBalanceQty(Double balanceQty) { this.balanceQty = balanceQty; }

    public String getDespatchSchedule() { return despatchSchedule; }
    public void setDespatchSchedule(String despatchSchedule) { this.despatchSchedule = despatchSchedule; }

    public String getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(String paymentTerms) { this.paymentTerms = paymentTerms; }

    public String getRatePerMtr() { return ratePerMtr; }
    public void setRatePerMtr(String ratePerMtr) { this.ratePerMtr = ratePerMtr; }

    public Double getNumericRate() { return numericRate; }
    public void setNumericRate(Double numericRate) { this.numericRate = numericRate; }

    public String getCdRate() { return cdRate; }
    public void setCdRate(String cdRate) { this.cdRate = cdRate; }

    public String getPoNo() { return poNo; }
    public void setPoNo(String poNo) { this.poNo = poNo; }

    public LocalDate getPoDate() { return poDate; }
    public void setPoDate(LocalDate poDate) { this.poDate = poDate; }

    public String getIcilConfirm() { return icilConfirm; }
    public void setIcilConfirm(String icilConfirm) { this.icilConfirm = icilConfirm; }

    public String getLcOpen() { return lcOpen; }
    public void setLcOpen(String lcOpen) { this.lcOpen = lcOpen; }

    public Double getCommissionRate() { return commissionRate; }
    public void setCommissionRate(Double commissionRate) { this.commissionRate = commissionRate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Short getIsApproved() { return isApproved; }
    public void setIsApproved(Short isApproved) { this.isApproved = isApproved; }

    public String getDeliveryLocation() { return deliveryLocation; }
    public void setDeliveryLocation(String deliveryLocation) { this.deliveryLocation = deliveryLocation; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}
