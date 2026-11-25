package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class FlangeBeamInwardDTO {

    private Long id;
    private LocalDate flangeInwardDate;
    private String flangeInwardNo;
    private Long orderTypeId;
    private Long buyerId;
    private Long sizingJobWorkOrderId;
    private Integer noOfFlanges;
    private Long factoryCompanyId;
    private Long companyId;
    private List<FlangeBeamInwardDetailDTO> flangeDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFlangeInwardDate() {
        return flangeInwardDate;
    }

    public void setFlangeInwardDate(LocalDate flangeInwardDate) {
        this.flangeInwardDate = flangeInwardDate;
    }

    public String getFlangeInwardNo() {
        return flangeInwardNo;
    }

    public void setFlangeInwardNo(String flangeInwardNo) {
        this.flangeInwardNo = flangeInwardNo;
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

    public Long getSizingJobWorkOrderId() {
        return sizingJobWorkOrderId;
    }

    public void setSizingJobWorkOrderId(Long sizingJobWorkOrderId) {
        this.sizingJobWorkOrderId = sizingJobWorkOrderId;
    }

    public Integer getNoOfFlanges() {
        return noOfFlanges;
    }

    public void setNoOfFlanges(Integer noOfFlanges) {
        this.noOfFlanges = noOfFlanges;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<FlangeBeamInwardDetailDTO> getFlangeDetails() {
        return flangeDetails;
    }

    public void setFlangeDetails(List<FlangeBeamInwardDetailDTO> flangeDetails) {
        this.flangeDetails = flangeDetails;
    }
}
