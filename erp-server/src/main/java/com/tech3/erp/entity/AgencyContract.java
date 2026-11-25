package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agency_contract", schema = "masters")
public class AgencyContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_no", unique = true, nullable = false)
    private String contractNo;

    @Column(name = "contract_date", nullable = false)
    private LocalDate contractDate;

    @Column(name = "seller", nullable = false)
    private String seller;

    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @Column(name = "construction", length = 500)
    private String construction;

    @Column(name = "piece_length")
    private Double pieceLength;

    @Column(name = "packing", length = 20)
    private String packing;

    @Column(name = "ordered_qty", nullable = false)
    private Double orderedQty;

    @Column(name = "supplied_qty")
    private Double suppliedQty = 0.0;

    @Column(name = "balance_qty")
    private Double balanceQty;

    @Column(name = "despatch_schedule", length = 500)
    private String despatchSchedule;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "rate_per_mtr")
    private String ratePerMtr;

    @Column(name = "numeric_rate")
    private Double numericRate;

    @Column(name = "cd_rate")
    private String cdRate;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "icil_confirm")
    private String icilConfirm;

    @Column(name = "lc_open")
    private String lcOpen = "PENDING"; // OPENED, PENDING, NA

    @Column(name = "commission_rate")
    private Double commissionRate = 0.01;

    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, PARTIAL, COMPLETED, CANCELLED

    @Column(name = "is_approved", nullable = false)
    private Short isApproved = (short) 0;

    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public AgencyContract() {}

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
