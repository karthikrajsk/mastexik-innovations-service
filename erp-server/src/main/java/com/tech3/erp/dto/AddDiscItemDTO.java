package com.tech3.erp.dto;

import java.math.BigDecimal;

public class AddDiscItemDTO {
    private Long id;
    private String description;
    private String type; // addition | discount
    private String mode; // percentage | value
    private BigDecimal percValue;
    private BigDecimal amount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}
