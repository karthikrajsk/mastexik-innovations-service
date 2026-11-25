package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NotificationDTO {

    private Long id;
    private Long companyId;
    private Long tradingCompanyId;
    private String tradingCompanyName;
    private String eventType;
    private BigDecimal quantity;
    private String unit;
    private String message;
    private Long referenceId;
    private String screenPath;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public NotificationDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public String getTradingCompanyName() { return tradingCompanyName; }
    public void setTradingCompanyName(String tradingCompanyName) { this.tradingCompanyName = tradingCompanyName; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public String getScreenPath() { return screenPath; }
    public void setScreenPath(String screenPath) { this.screenPath = screenPath; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
