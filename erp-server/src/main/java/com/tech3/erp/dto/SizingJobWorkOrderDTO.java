package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class SizingJobWorkOrderDTO {
    private Long id;
    private String sizingWorkOrderNo;
    private LocalDate orderDate;
    private Long orderTypeId;
    private Long buyerId;
    private Long consigneeId;
    private Integer gstTypeId;
    private Long sizingPlanId;
    private Double sizingRatePerKg;
    private Long factoryCompanyId;
    private Long tradingCompanyId;
    private Boolean activeFlag;
    private Short isComplete;
    private Short isApproved;
    // private Short isOrderMapped;
    private Short isOrderIdMapped;
    private Long userId;
    private Date approveDate;
    private String comments;
    private String remarks;
    private List<SizingJobWorkOrderItemDTO> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizingWorkOrderNo() {
        return sizingWorkOrderNo;
    }

    public void setSizingWorkOrderNo(String sizingWorkOrderNo) {
        this.sizingWorkOrderNo = sizingWorkOrderNo;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Long consigneeId) {
        this.consigneeId = consigneeId;
    }

    public Integer getGstTypeId() {
        return gstTypeId;
    }

    public void setGstTypeId(Integer gstTypeId) {
        this.gstTypeId = gstTypeId;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Double getSizingRatePerKg() {
        return sizingRatePerKg;
    }

    public void setSizingRatePerKg(Double sizingRatePerKg) {
        this.sizingRatePerKg = sizingRatePerKg;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public Short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

    // public Short getIsOrderMapped() {
    //     return isOrderMapped;
    // }

    // public void setIsOrderMapped(Short isOrderMapped) {
    //     this.isOrderMapped = isOrderMapped;
    // }

    public Short getIsOrderIdMapped() {
        return isOrderIdMapped;
    }

    public void setIsOrderIdMapped(Short isOrderIdMapped) {
        this.isOrderIdMapped = isOrderIdMapped;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SizingJobWorkOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SizingJobWorkOrderItemDTO> items) {
        this.items = items;
    }
}
