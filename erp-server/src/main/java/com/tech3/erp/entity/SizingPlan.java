package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "sizing_plan", schema = "masters")
public class SizingPlan {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendorId;
    private Long termsConditionsId;
    private Long paymentTermsId;
    private Double sizingRate;
    private Long orderTypeId;

    @Column(name = "empty_beam_no")
    private String emptyBeamNo;

    @Column(name = "no_of_creels")
    private Integer noOfCreels;

    @Column(columnDefinition = "text")
    private String remarks;

    private String sizingPlanNo;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "sizing_plan_date")
    private LocalDate sizingPlanDate;

     
    @Column(name = "is_complete")
    private Short isComplete;

    @OneToMany(mappedBy = "sizingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SizingQualityDetails> qualityDetails;

    @OneToMany(mappedBy = "sizingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SizingBeamDetails> beamDetails;

    @PrePersist
    public void prePersist() {
        if (this.sizingPlanNo == null || this.sizingPlanNo.isEmpty()) {
            this.sizingPlanNo = generateSizingPlanNo();
        }
    }

    private String generateSizingPlanNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "SP-" + datePart + "-" + randomNum;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getVendorId() { return vendorId; }

    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Long getTermsConditionsId() { return termsConditionsId; }

    public void setTermsConditionsId(Long termsConditionsId) { this.termsConditionsId = termsConditionsId; }


    public Long getPaymentTermsId() { return paymentTermsId; }

    public void setPaymentTermsId(Long paymentTermsId) { this.paymentTermsId = paymentTermsId; }

    public Long getOrderTypeId() { return orderTypeId; }

    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Double getSizingRate() { return sizingRate; }

    public void setSizingRate(Double sizingRate) { this.sizingRate = sizingRate; }

    public String getEmptyBeamNo() { return emptyBeamNo; }

    public void setEmptyBeamNo(String emptyBeamNo) { this.emptyBeamNo = emptyBeamNo; }

    public Integer getNoOfCreels() { return noOfCreels; }

    public void setNoOfCreels(Integer noOfCreels) { this.noOfCreels = noOfCreels; }

    public String getRemarks() { return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getSizingPlanNo() { return sizingPlanNo; }

    public void setSizingPlanNo(String sizingPlanNo) { this.sizingPlanNo = sizingPlanNo; }
    
    public Long getCompanyId() { return companyId; }
    
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getTradingCompanyId() { return tradingCompanyId; }

    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }

    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }

    public LocalDate getSizingPlanDate() { return sizingPlanDate; }

    public void setSizingPlanDate(LocalDate sizingPlanDate) { this.sizingPlanDate = sizingPlanDate; }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }
    public List<SizingQualityDetails> getQualityDetails() { return qualityDetails; }

    public void setQualityDetails(List<SizingQualityDetails> qualityDetails) { this.qualityDetails = qualityDetails; }

    public List<SizingBeamDetails> getBeamDetails() { return beamDetails; }

    public void setBeamDetails(List<SizingBeamDetails> beamDetails) { this.beamDetails = beamDetails; }
}
