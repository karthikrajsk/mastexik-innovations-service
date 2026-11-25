package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

public class InvoiceAddDiscLegacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "mode", length = 50)
    private String mode;

    @Column(name = "perc_value")
    private BigDecimal percValue;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "is_draft")
    private Boolean isDraft;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public BigDecimal getPercValue() { return percValue; }
    public void setPercValue(BigDecimal percValue) { this.percValue = percValue; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Boolean getIsDraft() { return isDraft; }
    public void setIsDraft(Boolean isDraft) { this.isDraft = isDraft; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}
