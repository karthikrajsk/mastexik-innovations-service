package com.tech3.erp.dto;

import com.tech3.erp.entity.CompanyInvoice;
import jakarta.validation.constraints.*;

public class CompanyInvoiceDTO {

    private Long id;

    @NotNull(message = "Invoice Type is required")
    private Long invoiceTypeId;

    @NotBlank(message = "Prefix is required")
    private String prefix;

    @NotBlank(message = "Financial Year is required")
    private String financialYear;

    @NotBlank(message = "Sequence Number is required")
    private String sequenceNumber;

    private String preview;

    public CompanyInvoiceDTO() {}

    public CompanyInvoiceDTO(CompanyInvoice invoice) {
        this.id = invoice.getId();
        this.invoiceTypeId = invoice.getInvoiceTypeId();
        this.prefix = invoice.getPrefix();
        this.financialYear = invoice.getFinancialYear();
        this.sequenceNumber = invoice.getSequenceNumber();
        this.preview = invoice.getPreview();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(Long invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
