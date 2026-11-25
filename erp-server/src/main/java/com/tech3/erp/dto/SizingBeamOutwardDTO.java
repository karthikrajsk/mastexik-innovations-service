package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class SizingBeamOutwardDTO {
    private Long id;
    private String sizingBeamOutwardNo;
    private String orderType;
    private Long sizingOrderId;
    private String sizingOrderNo;
    private Long sizingPlanId;
    private String sizingPlanNo;
    private Long orderTypeId;
    private String buyerName;
    private Long buyerId;
    private List<String> deliveryAt;
    private String yarnName;
    private Long yarnId;
    @NotNull
    private Long companyId;
    private List<SizingBeamOutwardDetailDTO> beamDetails;
    private SizingBeamOutwardSummaryDTO summary;
    private LocalDate outwardDate;
    private Integer isReturn;
    private LocalDate returnDate;
    private String returnNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizingBeamOutwardNo() {
        return sizingBeamOutwardNo;
    }

    public void setSizingBeamOutwardNo(String sizingBeamOutwardNo) {
        this.sizingBeamOutwardNo = sizingBeamOutwardNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getSizingOrderId() {
        return sizingOrderId;
    }

    public void setSizingOrderId(Long sizingOrderId) {
        this.sizingOrderId = sizingOrderId;
    }

    public String getSizingOrderNo() {
        return sizingOrderNo;
    }

    public void setSizingOrderNo(String sizingOrderNo) {
        this.sizingOrderNo = sizingOrderNo;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public String getSizingPlanNo() {
        return sizingPlanNo;
    }

    public void setSizingPlanNo(String sizingPlanNo) {
        this.sizingPlanNo = sizingPlanNo;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public List<String> getDeliveryAt() {
        return deliveryAt;
    }

    public void setDeliveryAt(List<String> deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<SizingBeamOutwardDetailDTO> getBeamDetails() {
        return beamDetails;
    }

    public void setBeamDetails(List<SizingBeamOutwardDetailDTO> beamDetails) {
        this.beamDetails = beamDetails;
    }

    public SizingBeamOutwardSummaryDTO getSummary() {
        return summary;
    }

    public void setSummary(SizingBeamOutwardSummaryDTO summary) {
        this.summary = summary;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }
}
