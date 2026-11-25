package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class BeamInwardDTO {
    private Long id;
    private Long sizingVendorId;
    private Long sizingPlanId;
    private Long termsConditionsId;
    private Long consigneeId;
    private Long paymentTermsId;
    private Double sizingRate;
    private Long orderTypeId;
    private String remarks;
    private String beamInwardNo;
    private String dcNumber;
    private LocalDate dcDate;
    private LocalDate beamInwardDate;
    private Long companyId;
    private Integer totalBeams;
    private String sizingSetNo;
    private Double avgBeamCount;
    private Double sizingPickup;
    private Double elongation;
    private Double breaksMillion;
    private Double babyConeWt;
    private Double actualWeight;
    private Double warpingWeight;
    private Double excessShortage;
    private Double freshCone;
    private Double rewindingCone;


    private List<BeamInwardQualityDetailsDTO> beamInwardQualityDetails;
    private List<BeamInwardBeamDetailsDTO> beamInwardBeamDetails;
    private List<BeamInwardInvoiceDetailsDTO> invoiceDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

   
    public Long getSizingVendorId() {
        return sizingVendorId;
    }

    public void setSizingVendorId(Long sizingVendorId) {
        this.sizingVendorId = sizingVendorId;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public Long getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Long consigneeId) {
        this.consigneeId = consigneeId;
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

    public void setSizingRate(Double sizingRate) {
        this.sizingRate = sizingRate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBeamInwardNo() {
        return beamInwardNo;
    }

    public void setBeamInwardNo(String beamInwardNo) {
        this.beamInwardNo = beamInwardNo;
    }

    public String getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(String dcNumber) {
        this.dcNumber = dcNumber;
    }

    public LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDate dcDate) {
        this.dcDate = dcDate;
    }

    public LocalDate getBeamInwardDate() {
        return beamInwardDate;
    }

    public void setBeamInwardDate(LocalDate beamInwardDate) {
        this.beamInwardDate = beamInwardDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getTotalBeams() {
        return totalBeams;
    }

    public void setTotalBeams(Integer totalBeams) {
        this.totalBeams = totalBeams;
    }

    public List<BeamInwardQualityDetailsDTO> getBeamInwardQualityDetails() {
        return beamInwardQualityDetails;
    }

    public void setBeamInwardQualityDetails(List<BeamInwardQualityDetailsDTO> beamInwardQualityDetails) {
        this.beamInwardQualityDetails = beamInwardQualityDetails;
    }

    public List<BeamInwardBeamDetailsDTO> getBeamInwardBeamDetails() {
        return beamInwardBeamDetails;
    }

    public void setBeamInwardBeamDetails(List<BeamInwardBeamDetailsDTO> beamInwardBeamDetails) {
        this.beamInwardBeamDetails = beamInwardBeamDetails;
    }

    public List<BeamInwardInvoiceDetailsDTO> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<BeamInwardInvoiceDetailsDTO> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public String getSizingSetNo() { return sizingSetNo; }
    public void setSizingSetNo(String sizingSetNo) { this.sizingSetNo = sizingSetNo; }

    public Double getAvgBeamCount() { return avgBeamCount; }
    public void setAvgBeamCount(Double avgBeamCount) { this.avgBeamCount = avgBeamCount; }

    public Double getSizingPickup() { return sizingPickup; }
    public void setSizingPickup(Double sizingPickup) { this.sizingPickup = sizingPickup; }

    public Double getElongation() { return elongation; }
    public void setElongation(Double elongation) { this.elongation = elongation; }

    public Double getBreaksMillion() { return breaksMillion; }
    public void setBreaksMillion(Double breaksMillion) { this.breaksMillion = breaksMillion; }

    public Double getBabyConeWt() { return babyConeWt; }
    public void setBabyConeWt(Double babyConeWt) { this.babyConeWt = babyConeWt; }

    public Double getActualWeight() { return actualWeight; }
    public void setActualWeight(Double actualWeight) { this.actualWeight = actualWeight; }

    public Double getWarpingWeight() { return warpingWeight; }
    public void setWarpingWeight(Double warpingWeight) { this.warpingWeight = warpingWeight; }

    public Double getExcessShortage() { return excessShortage; }
    public void setExcessShortage(Double excessShortage) { this.excessShortage = excessShortage; }

    public Double getFreshCone() { return freshCone; }
    public void setFreshCone(Double freshCone) { this.freshCone = freshCone; }

    public Double getRewindingCone() { return rewindingCone; }
    public void setRewindingCone(Double rewindingCone) { this.rewindingCone = rewindingCone; }
}


