package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "beam_inward", schema = "masters")
public class BeamInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sizingVendorId;
    private Long sizingPlanId;
    private Long sizingBeamOutwardId;
    private Long termsConditionsId;
    private Long consigneeId;
    private Long paymentTermsId;
    private Double sizingRate;
    private Long orderTypeId;


    @Column(columnDefinition = "text")
    private String remarks;
    

	@Column(name = "beam_inward_date")
	private LocalDate beamInwardDate;

    private String beamInwardNo;

    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "total_beams")
    private Integer totalBeams;

    @Column(name = "sizing_set_no")
    private String sizingSetNo;

    @Column(name = "avg_beam_count")
    private Double avgBeamCount;

    @Column(name = "sizing_pickup")
    private Double sizingPickup;

    @Column(name = "elongation")
    private Double elongation;

    @Column(name = "breaks_million")
    private Double breaksMillion;

    @Column(name = "baby_cone_wt")
    private Double babyConeWt;

    @Column(name = "actual_weight")
    private Double actualWeight;

    @Column(name = "warping_weight")
    private Double warpingWeight;

    @Column(name = "excess_shortage")
    private Double excessShortage;

    @Column(name = "fresh_cone")
    private Double freshCone;

    @Column(name = "rewinding_cone")
    private Double rewindingCone;


    @OneToMany(mappedBy = "beamInward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BeamInwardQualityDetails> qualityDetails;

    @OneToMany(mappedBy = "beamInward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BeamInwardBeamDetails> beamDetails;

    @OneToMany(mappedBy = "beamInward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BeamInwardInvoiceDetails> invoiceDetails;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Long getSizingBeamOutwardId() {
        return sizingBeamOutwardId;
    }

    public void setSizingBeamOutwardId(Long sizingBeamOutwardId) {
        this.sizingBeamOutwardId = sizingBeamOutwardId;
    }
    
    public Long getSizingVendorId() { return sizingVendorId; }

    public void setSizingVendorId(Long sizingVendorId) { this.sizingVendorId = sizingVendorId; }

    public Long getTermsConditionsId() { return termsConditionsId; }

    public void setTermsConditionsId(Long termsConditionsId) { this.termsConditionsId = termsConditionsId; }

    public Long getConsigneeId() { return consigneeId; }

    public void setConsigneeId(Long consigneeId) { this.consigneeId = consigneeId; }

    public Long getPaymentTermsId() { return paymentTermsId; }

    public void setPaymentTermsId(Long paymentTermsId) { this.paymentTermsId = paymentTermsId; }

    public Double getSizingRate() { return sizingRate; }

    public void setSizingRate(Double sizingRate) { this.sizingRate = sizingRate; }

    public Long getOrderTypeId() { return orderTypeId; }

    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }


    public String getRemarks() { return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getBeamInwardNo() { return beamInwardNo; }

    public void setBeamInwardNo(String beamInwardNo) { this.beamInwardNo = beamInwardNo; }

    public String getDcNumber() { return dcNumber; }

    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

    public LocalDate getDcDate() { return dcDate; }

    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public Long getCompanyId() { return companyId; }

    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<BeamInwardQualityDetails> getQualityDetails() { return qualityDetails; }

    public void setQualityDetails(List<BeamInwardQualityDetails> qualityDetails) { this.qualityDetails = qualityDetails; }

    public List<BeamInwardBeamDetails> getBeamDetails() { return beamDetails; }

    public void setBeamDetails(List<BeamInwardBeamDetails> beamDetails) { this.beamDetails = beamDetails; }

    public List<BeamInwardInvoiceDetails> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<BeamInwardInvoiceDetails> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
    
    public LocalDate getBeamInwardDate() {
        return beamInwardDate;
    }

    public void setBeamInwardDate(LocalDate beamInwardDate) {
        this.beamInwardDate = beamInwardDate;
    }

    public Integer getTotalBeams() {
        return totalBeams;
    }

    public void setTotalBeams(Integer totalBeams) {
        this.totalBeams = totalBeams;
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


