package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agency_payment", schema = "masters")
public class AgencyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mill_invoice_no", nullable = false)
    private String millInvoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "seller")
    private String seller;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "construction", length = 500)
    private String construction;

    @Column(name = "total_invoice_value", nullable = false)
    private Double totalInvoiceValue;

    @Column(name = "tds_rate")
    private Double tdsRate = 0.001; // 0.1% TDS 194Q

    @Column(name = "tds_deducted")
    private Double tdsDeducted;

    @Column(name = "received_amount")
    private Double receivedAmount;

    @Column(name = "balance_amount")
    private Double balanceAmount;

    @Column(name = "debit_notes")
    private Double debitNotes = 0.0;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_mode")
    private String paymentMode = "RTGS / NEFT"; // RTGS / NEFT, LC Realization, Cheque, Draft

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "status")
    private String status = "Fully Settled"; // Fully Settled, Partially Settled, Pending Settlement

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public AgencyPayment() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMillInvoiceNo() { return millInvoiceNo; }
    public void setMillInvoiceNo(String millInvoiceNo) { this.millInvoiceNo = millInvoiceNo; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public Double getTotalInvoiceValue() { return totalInvoiceValue; }
    public void setTotalInvoiceValue(Double totalInvoiceValue) { this.totalInvoiceValue = totalInvoiceValue; }

    public Double getTdsRate() { return tdsRate; }
    public void setTdsRate(Double tdsRate) { this.tdsRate = tdsRate; }

    public Double getTdsDeducted() { return tdsDeducted; }
    public void setTdsDeducted(Double tdsDeducted) { this.tdsDeducted = tdsDeducted; }

    public Double getReceivedAmount() { return receivedAmount; }
    public void setReceivedAmount(Double receivedAmount) { this.receivedAmount = receivedAmount; }

    public Double getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(Double balanceAmount) { this.balanceAmount = balanceAmount; }

    public Double getDebitNotes() { return debitNotes; }
    public void setDebitNotes(Double debitNotes) { this.debitNotes = debitNotes; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}
