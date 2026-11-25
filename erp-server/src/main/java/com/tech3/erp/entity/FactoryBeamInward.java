package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "factory_beam_inward", schema = "masters")
public class FactoryBeamInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "vendor_id")
    private Long sizingVendorId;
    private Long sizingPlanId;
    private String sizingSetNo;
    private String dcNo;
    private String dcDate;
    private String weavingContractId;
    private Long weavingWorkOrderId;
    private Long orderTypeId;

    @Column(name = "beam_inward_id")
    private Long beamInwardId;

    @Column(name = "beam_outward_id")
    private Long beamOutwardId;

    @Column(columnDefinition = "text")
    private String remarks;

    @Column(name = "beam_inward_date")
    private LocalDate beamInwardDate;

    private String beamInwardNo;

    @Column(name = "is_beam_pending", nullable = false)
    private Integer isBeamPending = 0;

    @Column(columnDefinition = "text")
    private String fabricCodeQuality;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @OneToMany(mappedBy = "beamInward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FactoryBeamInwardQualityDetails> qualityDetails;

    @OneToMany(mappedBy = "beamInward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FactoryBeamInwardBeamDetails> beamDetails;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        // Only generate beamInwardNo if not already set and no beamOutwardId reference
        if ((this.beamInwardNo == null || this.beamInwardNo.isEmpty()) && this.beamOutwardId == null) {
            this.beamInwardNo = generateBeamInwardNo();
        }
    }

    private String generateBeamInwardNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "BI-" + datePart + "-" + randomNum;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSizingPlanId() { return sizingPlanId; }
    public void setSizingPlanId(Long sizingPlanId) { this.sizingPlanId = sizingPlanId; }

    public Long getSizingVendorId() { return sizingVendorId; }
    public void setSizingVendorId(Long sizingVendorId) { this.sizingVendorId = sizingVendorId; }

    public String getSizingSetNo() { return sizingSetNo; }
    public void setSizingSetNo(String sizingSetNo) { this.sizingSetNo = sizingSetNo; }

    public String getDcNo() { return dcNo; }
    public void setDcNo(String dcNo) { this.dcNo = dcNo; }

    public String getDcDate() { return dcDate; }
    public void setDcDate(String dcDate) { this.dcDate = dcDate; }

    public String getWeavingContractId() { return weavingContractId; }
    public void setWeavingContractId(String weavingContractId) { this.weavingContractId = weavingContractId; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getBeamInwardNo() { return beamInwardNo; }
    public void setBeamInwardNo(String beamInwardNo) { this.beamInwardNo = beamInwardNo; }

    public List<FactoryBeamInwardQualityDetails> getQualityDetails() { return qualityDetails; }
    public void setQualityDetails(List<FactoryBeamInwardQualityDetails> qualityDetails) { this.qualityDetails = qualityDetails; }

    public List<FactoryBeamInwardBeamDetails> getBeamDetails() { return beamDetails; }
    public void setBeamDetails(List<FactoryBeamInwardBeamDetails> beamDetails) { this.beamDetails = beamDetails; }

    public LocalDate getBeamInwardDate() { return beamInwardDate; }
    public void setBeamInwardDate(LocalDate beamInwardDate) { this.beamInwardDate = beamInwardDate; }

    public Long getBeamInwardId() { return beamInwardId; }
    public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }

    public Long getBeamOutwardId() { return beamOutwardId; }
    public void setBeamOutwardId(Long beamOutwardId) { this.beamOutwardId = beamOutwardId; }

    public Integer getIsBeamPending() {
        return isBeamPending != null ? isBeamPending : 0;
    }

    public void setIsBeamPending(Integer isBeamPending) {
        this.isBeamPending = isBeamPending;
    }

    public String getFabricCodeQuality() { return fabricCodeQuality; }
    public void setFabricCodeQuality(String fabricCodeQuality) { this.fabricCodeQuality = fabricCodeQuality; }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }
}
