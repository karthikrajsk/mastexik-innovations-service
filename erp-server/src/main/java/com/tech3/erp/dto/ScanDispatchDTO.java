package com.tech3.erp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScanDispatchDTO {

    private Long id;

    @NotNull
    private Long invoiceId;

    @NotBlank
    private String invoiceNo;

    private Long orderId;

    private String orderNo;

    @NotNull
    private Long buyerId;

    @NotBlank
    private String buyerName;

    @NotNull
    private Long fabricMasterId;

    @NotNull
    private Long companyId;

    @Valid
    private List<ScannedRollDTO> rolls = new ArrayList<>();

    @Valid
    private List<SummaryRollDTO> summary = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScanDispatchDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<ScannedRollDTO> getRolls() {
        return rolls;
    }

    public void setRolls(List<ScannedRollDTO> rolls) {
        this.rolls = rolls != null ? rolls : new ArrayList<>();
    }

    public List<SummaryRollDTO> getSummary() {
        return summary;
    }

    public void setSummary(List<SummaryRollDTO> summary) {
        this.summary = summary != null ? summary : new ArrayList<>();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}