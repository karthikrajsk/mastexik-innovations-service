package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "beam_outward_detail", schema = "masters")
public class BeamOutwardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long weavingContractId;
    private Long weavingVendorId;
    private Long salesOrderId;
    private String sizedBeamNo;
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private String fabricPanelWidth;
    private Long totalEnds;

    @Column(name = "is_issued_to_weaving", columnDefinition = "bigint default 0")
    private Long isIssuedToWeaving = 0L;

    @Column(name = "is_factory_inward", columnDefinition = "bigint default 0")
    private Long isFactoryInward = 0L;

    @Column(name = "changed_fabric_master_id")
    private Long changedFabricMasterId;

    @Column(name = "changed_fabric_code_quality", columnDefinition = "text")
    private String changedFabricCodeQuality;

    @Column(name = "is_return", columnDefinition = "int default 0")
    private Integer isReturn = 0;

    @Column(name = "return_date")
    private java.time.LocalDate returnDate;

    @Column(name = "return_no", length = 100)
    private String returnNo;

    @Column(name = "return_weaver_id")
    private Long returnWeaverId;

    @Column(name = "is_beam_gated", columnDefinition = "int default 0")
    private Integer isBeamGated = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beam_outward_id")
    private BeamOutward beamOutward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public Long getWeavingVendorId() {
        return weavingVendorId;
    }

    public void setWeavingVendorId(Long weavingVendorId) {
        this.weavingVendorId = weavingVendorId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSizedBeamNo() {
        return sizedBeamNo;
    }

    public void setSizedBeamNo(String sizedBeamNo) {
        this.sizedBeamNo = sizedBeamNo;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public Double getWrapMeters() {
        return wrapMeters;
    }

    public void setWrapMeters(Double wrapMeters) {
        this.wrapMeters = wrapMeters;
    }

    public Double getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(Double shrinkage) {
        this.shrinkage = shrinkage;
    }

    public Double getExpectedFabricMeter() {
        return expectedFabricMeter;
    }

    public void setExpectedFabricMeter(Double expectedFabricMeter) {
        this.expectedFabricMeter = expectedFabricMeter;
    }

    public String getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(String fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public Long getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Long totalEnds) { this.totalEnds = totalEnds; }

    public Long getIsIssuedToWeaving() { return isIssuedToWeaving; }
    public void setIsIssuedToWeaving(Long isIssuedToWeaving) { this.isIssuedToWeaving = isIssuedToWeaving; }

    public Long getIsFactoryInward() { return isFactoryInward; }
    public void setIsFactoryInward(Long isFactoryInward) { this.isFactoryInward = isFactoryInward; }

    public BeamOutward getBeamOutward() {
        return beamOutward;
    }

    public void setBeamOutward(BeamOutward beamOutward) {
        this.beamOutward = beamOutward;
    }

    public Long getChangedFabricMasterId() {
        return changedFabricMasterId;
    }

    public void setChangedFabricMasterId(Long changedFabricMasterId) {
        this.changedFabricMasterId = changedFabricMasterId;
    }

    public String getChangedFabricCodeQuality() {
        return changedFabricCodeQuality;
    }

    public void setChangedFabricCodeQuality(String changedFabricCodeQuality) {
        this.changedFabricCodeQuality = changedFabricCodeQuality;
    }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public java.time.LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(java.time.LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

    public Long getReturnWeaverId() { return returnWeaverId; }
    public void setReturnWeaverId(Long returnWeaverId) { this.returnWeaverId = returnWeaverId; }

    public Integer getIsBeamGated() { return isBeamGated != null ? isBeamGated : 0; }
    public void setIsBeamGated(Integer isBeamGated) { this.isBeamGated = isBeamGated; }
}
