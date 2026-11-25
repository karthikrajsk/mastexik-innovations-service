package com.tech3.erp.dto;

import com.tech3.erp.entity.CommissionStatementItem;
import java.time.LocalDate;

public class CommissionStatementItemDTO {

    private Long id;
    private Long statementId;
    private Long dispatchId;
    private String millInvoiceNo;
    private String dispatchInvoiceNo;
    private LocalDate invoiceDate;
    private String buyerName;
    private String broker;
    private Double brokerCommissionRate = 0.0;
    private String construction;
    private Double mtrs;
    private Double taxableValue;
    private Double commissionRate;
    private Double commissionAmount;
    private String remarks;

    public CommissionStatementItemDTO() {}

    public CommissionStatementItemDTO(CommissionStatementItem entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.statementId = entity.getStatement() != null ? entity.getStatement().getId() : null;
            this.dispatchId = entity.getDispatchId();
            this.millInvoiceNo = entity.getMillInvoiceNo();
            this.dispatchInvoiceNo = entity.getMillInvoiceNo();
            this.invoiceDate = entity.getInvoiceDate();
            this.buyerName = entity.getBuyerName();
            this.broker = entity.getBroker();
            this.brokerCommissionRate = entity.getBrokerCommissionRate() != null ? entity.getBrokerCommissionRate() : 0.0;
            this.construction = entity.getConstruction();
            this.mtrs = entity.getMtrs();
            this.taxableValue = entity.getTaxableValue();
            this.commissionRate = entity.getCommissionRate();
            this.commissionAmount = entity.getCommissionAmount();
            this.remarks = entity.getRemarks();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStatementId() { return statementId; }
    public void setStatementId(Long statementId) { this.statementId = statementId; }

    public Long getDispatchId() { return dispatchId; }
    public void setDispatchId(Long dispatchId) { this.dispatchId = dispatchId; }

    public String getMillInvoiceNo() {
        return millInvoiceNo != null ? millInvoiceNo : dispatchInvoiceNo;
    }
    public void setMillInvoiceNo(String millInvoiceNo) {
        this.millInvoiceNo = millInvoiceNo;
        if (this.dispatchInvoiceNo == null) {
            this.dispatchInvoiceNo = millInvoiceNo;
        }
    }

    public String getDispatchInvoiceNo() {
        return dispatchInvoiceNo != null ? dispatchInvoiceNo : millInvoiceNo;
    }
    public void setDispatchInvoiceNo(String dispatchInvoiceNo) {
        this.dispatchInvoiceNo = dispatchInvoiceNo;
        if (this.millInvoiceNo == null) {
            this.millInvoiceNo = dispatchInvoiceNo;
        }
    }

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
