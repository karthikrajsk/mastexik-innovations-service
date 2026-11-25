package com.tech3.erp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestFormDTO {

    private Long id;
    private LocalDate date;
    private String requestNo;
    private String customerInquiryNo;
    private String buyerName;
    private Long toMillId;
    private String fabricItemCode;
    private String construction;
    private String category;
    private String requestType;
    private String uom;
    private Double quantity;
    private String status;
    private String toMillName;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private Boolean activeFlag;
    private java.util.List<RequestFormRoundDTO> rounds;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getRequestNo() { return requestNo; }
    public void setRequestNo(String requestNo) { this.requestNo = requestNo; }

    public String getCustomerInquiryNo() { return customerInquiryNo; }
    public void setCustomerInquiryNo(String customerInquiryNo) { this.customerInquiryNo = customerInquiryNo; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public Long getToMillId() { return toMillId; }
    public void setToMillId(Long toMillId) { this.toMillId = toMillId; }

    public String getFabricItemCode() { return fabricItemCode; }
    public void setFabricItemCode(String fabricItemCode) { this.fabricItemCode = fabricItemCode; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getToMillName() { return toMillName; }
    public void setToMillName(String toMillName) { this.toMillName = toMillName; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public java.util.List<RequestFormRoundDTO> getRounds() { return rounds; }
    public void setRounds(java.util.List<RequestFormRoundDTO> rounds) { this.rounds = rounds; }
}
