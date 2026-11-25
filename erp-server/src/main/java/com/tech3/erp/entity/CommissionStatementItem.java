package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "agency_commission_statement_item", schema = "masters")
public class CommissionStatementItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statement_id", nullable = false)
    @JsonBackReference
    private CommissionStatement statement;

    @Column(name = "dispatch_id")
    private Long dispatchId;

    @Column(name = "mill_invoice_no", nullable = false)
    private String millInvoiceNo;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "broker")
    private String broker;

    @Column(name = "broker_commission_rate")
    private Double brokerCommissionRate = 0.0;

    @Column(name = "construction", length = 500)
    private String construction;

    @Column(name = "mtrs")
    private Double mtrs;

    @Column(name = "taxable_value")
    private Double taxableValue;

    @Column(name = "commission_rate")
    private Double commissionRate;

    @Column(name = "commission_amount")
    private Double commissionAmount;

    @Column(name = "remarks", length = 500)
    private String remarks;

    public CommissionStatementItem() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CommissionStatement getStatement() { return statement; }
    public void setStatement(CommissionStatement statement) { this.statement = statement; }

    public Long getDispatchId() { return dispatchId; }
    public void setDispatchId(Long dispatchId) { this.dispatchId = dispatchId; }

    public String getMillInvoiceNo() { return millInvoiceNo; }
    public void setMillInvoiceNo(String millInvoiceNo) { this.millInvoiceNo = millInvoiceNo; }

    public String getDispatchInvoiceNo() { return millInvoiceNo; }
    public void setDispatchInvoiceNo(String dispatchInvoiceNo) { this.millInvoiceNo = dispatchInvoiceNo; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getBroker() { return broker; }
    public void setBroker(String broker) { this.broker = broker; }

    public Double getBrokerCommissionRate() { return brokerCommissionRate; }
    public void setBrokerCommissionRate(Double brokerCommissionRate) { this.brokerCommissionRate = brokerCommissionRate; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public Double getMtrs() { return mtrs; }
    public void setMtrs(Double mtrs) { this.mtrs = mtrs; }

    public Double getTaxableValue() { return taxableValue; }
    public void setTaxableValue(Double taxableValue) { this.taxableValue = taxableValue; }

    public Double getCommissionRate() { return commissionRate; }
    public void setCommissionRate(Double commissionRate) { this.commissionRate = commissionRate; }

    public Double getCommissionAmount() { return commissionAmount; }
    public void setCommissionAmount(Double commissionAmount) { this.commissionAmount = commissionAmount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
