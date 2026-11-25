package com.tech3.erp.dto;

import java.util.Date;
import java.util.List;

public class SizingPlanDTO {
	private Long id;
    private Long vendorId;
    private Long termsConditionsId;
    private Long paymentTermsId;
    private Double sizingRate;
    private Long orderTypeId;
    private String emptyBeamNo;
    private Integer noOfCreels;
    private Long companyId;
    private Long tradingCompanyId;
    private Long factoryCompanyId;
    private String remarks;
    private String sizingPlanNo;
    private String sizingPlanDate;
    private Short isComplete;

    private List<SizingQualityDetailsDTO> sizingQualityDetails;
    private List<SizingBeamDetailsDTO> sizingBeamDetails;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }


    public Long getPaymentTermsId() {
        return paymentTermsId;
    }

    public void setPaymentTermsId(Long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }

    public Double getSizingRate() {
        return sizingRate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public void setSizingRate(Double sizingRate) {
        this.sizingRate = sizingRate;
    }

    public String getEmptyBeamNo() {
        return emptyBeamNo;
    }

    public void setEmptyBeamNo(String emptyBeamNo) {
        this.emptyBeamNo = emptyBeamNo;
    }

    public Integer getNoOfCreels() {
        return noOfCreels;
    }

    public void setNoOfCreels(Integer noOfCreels) {
        this.noOfCreels = noOfCreels;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public String getSizingPlanNo() {
        return sizingPlanNo;
    }

    public void setSizingPlanNo(String sizingPlanNo) {
        this.sizingPlanNo = sizingPlanNo;
    }

    public String getSizingPlanDate() {
        return sizingPlanDate;
    }

    public void setSizingPlanDate(String sizingPlanDate) {
        this.sizingPlanDate = sizingPlanDate;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public List<SizingQualityDetailsDTO> getSizingQualityDetails() {
        return sizingQualityDetails;
    }

    public void setSizingQualityDetails(List<SizingQualityDetailsDTO> sizingQualityDetails) {
        this.sizingQualityDetails = sizingQualityDetails;
    }

    public List<SizingBeamDetailsDTO> getSizingBeamDetails() {
        return sizingBeamDetails;
    }

    public void setSizingBeamDetails(List<SizingBeamDetailsDTO> sizingBeamDetails) {
        this.sizingBeamDetails = sizingBeamDetails;
    }
}
