package com.tech3.erp.dto;

import com.tech3.erp.entity.AgencyPayment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AgencyPaymentDTO {

    private Long id;

    @NotBlank(message = "Mill Invoice No is required")
    private String millInvoiceNo;

    private LocalDate invoiceDate;
    private String seller;
    private String buyerName;
    private String construction;

    @NotNull(message = "Total Invoice Value is required")
    private Double totalInvoiceValue;

    private Double tdsRate = 0.001;
    private Double tdsDeducted = 0.0;
    private Double receivedAmount = 0.0;
    private Double balanceAmount = 0.0;
    private Double debitNotes = 0.0;

    @NotNull(message = "Payment Date is required")
    private LocalDate paymentDate;

    private String paymentMode = "RTGS / NEFT";
    private String referenceNo;
    private String status = "Fully Settled";
    private String remarks;
    private Long companyId;
    private Boolean activeFlag = true;

    public AgencyPaymentDTO() {}

    public AgencyPaymentDTO(AgencyPayment entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.millInvoiceNo = entity.getMillInvoiceNo();
            this.invoiceDate = entity.getInvoiceDate();
            this.seller = entity.getSeller();
            this.buyerName = entity.getBuyerName();
            this.construction = entity.getConstruction();
            this.totalInvoiceValue = entity.getTotalInvoiceValue();
            this.tdsRate = entity.getTdsRate() != null ? entity.getTdsRate() : 0.001;
            this.tdsDeducted = entity.getTdsDeducted() != null ? entity.getTdsDeducted() : 0.0;
            this.receivedAmount = entity.getReceivedAmount() != null ? entity.getReceivedAmount() : 0.0;
            this.balanceAmount = entity.getBalanceAmount() != null ? entity.getBalanceAmount() : 0.0;
            this.debitNotes = entity.getDebitNotes() != null ? entity.getDebitNotes() : 0.0;
            this.paymentDate = entity.getPaymentDate();
            this.paymentMode = entity.getPaymentMode() != null ? entity.getPaymentMode() : "RTGS / NEFT";
            this.referenceNo = entity.getReferenceNo();
            this.status = entity.getStatus() != null ? entity.getStatus() : "Fully Settled";
            this.remarks = entity.getRemarks();
            this.companyId = entity.getCompanyId();
            this.activeFlag = entity.getActiveFlag() != null ? entity.getActiveFlag() : true;
        }
    }

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
