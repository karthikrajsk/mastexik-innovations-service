package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "agency_commission_statement", schema = "masters")
public class CommissionStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statement_no", unique = true, nullable = false)
    private String statementNo;

    @Column(name = "statement_date", nullable = false)
    private LocalDate statementDate;

    @Column(name = "seller", nullable = false)
    private String seller;

    @Column(name = "seller_address")
    private String sellerAddress;

    @Column(name = "seller_gst")
    private String sellerGst;

    @Column(name = "period")
    private String period;

    @Column(name = "sac_code")
    private String sacCode = "996111";

    @Column(name = "total_turnover_eligible", nullable = false)
    private Double totalTurnoverEligible;

    @Column(name = "commission_rate", nullable = false)
    private Double commissionRate = 0.01;

    @Column(name = "gross_commission", nullable = false)
    private Double grossCommission;

    @Column(name = "gst_rate")
    private Double gstRate = 0.18;

    @Column(name = "cgst_amount")
    private Double cgstAmount = 0.0;

    @Column(name = "sgst_amount")
    private Double sgstAmount = 0.0;

    @Column(name = "igst_amount")
    private Double igstAmount = 0.0;

    @Column(name = "total_bill_amount", nullable = false)
    private Double totalBillAmount;

    @Column(name = "tds_194h_rate")
    private Double tds194HRate = 0.05;

    @Column(name = "tds_194h_amount")
    private Double tds194HAmount;

    @Column(name = "net_receivable", nullable = false)
    private Double netReceivable;

    @Column(name = "status")
    private String status = "Billed"; // Draft, Billed, Paid, Cancelled

    @Column(name = "payment_received_date")
    private LocalDate paymentReceivedDate;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CommissionStatementItem> items = new ArrayList<>();

    public CommissionStatement() {}

    // Helpers
    public void addItem(CommissionStatementItem item) {
        items.add(item);
        item.setStatement(this);
    }

    public void removeItem(CommissionStatementItem item) {
        items.remove(item);
        item.setStatement(null);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatementNo() { return statementNo; }
    public void setStatementNo(String statementNo) { this.statementNo = statementNo; }

    public LocalDate getStatementDate() { return statementDate; }
    public void setStatementDate(LocalDate statementDate) { this.statementDate = statementDate; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public String getSellerAddress() { return sellerAddress; }
    public void setSellerAddress(String sellerAddress) { this.sellerAddress = sellerAddress; }

    public String getSellerGst() { return sellerGst; }
    public void setSellerGst(String sellerGst) { this.sellerGst = sellerGst; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getSacCode() { return sacCode; }
    public void setSacCode(String sacCode) { this.sacCode = sacCode; }

    public Double getTotalTurnoverEligible() { return totalTurnoverEligible; }
    public void setTotalTurnoverEligible(Double totalTurnoverEligible) { this.totalTurnoverEligible = totalTurnoverEligible; }

    public Double getCommissionRate() { return commissionRate; }
    public void setCommissionRate(Double commissionRate) { this.commissionRate = commissionRate; }

    public Double getGrossCommission() { return grossCommission; }
    public void setGrossCommission(Double grossCommission) { this.grossCommission = grossCommission; }

    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

    public Double getCgstAmount() { return cgstAmount; }
    public void setCgstAmount(Double cgstAmount) { this.cgstAmount = cgstAmount; }

    public Double getSgstAmount() { return sgstAmount; }
    public void setSgstAmount(Double sgstAmount) { this.sgstAmount = sgstAmount; }

    public Double getIgstAmount() { return igstAmount; }
    public void setIgstAmount(Double igstAmount) { this.igstAmount = igstAmount; }

    public Double getTotalBillAmount() { return totalBillAmount; }
    public void setTotalBillAmount(Double totalBillAmount) { this.totalBillAmount = totalBillAmount; }

    public Double getTds194HRate() { return tds194HRate; }
    public void setTds194HRate(Double tds194HRate) { this.tds194HRate = tds194HRate; }

    public Double getTds194HAmount() { return tds194HAmount; }
    public void setTds194HAmount(Double tds194HAmount) { this.tds194HAmount = tds194HAmount; }

    public Double getNetReceivable() { return netReceivable; }
    public void setNetReceivable(Double netReceivable) { this.netReceivable = netReceivable; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getPaymentReceivedDate() { return paymentReceivedDate; }
    public void setPaymentReceivedDate(LocalDate paymentReceivedDate) { this.paymentReceivedDate = paymentReceivedDate; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public List<CommissionStatementItem> getItems() { return items; }
    public void setItems(List<CommissionStatementItem> items) { this.items = items; }
}
