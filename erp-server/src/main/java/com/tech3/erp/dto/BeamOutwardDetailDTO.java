package com.tech3.erp.dto;

public class BeamOutwardDetailDTO {
    private Long id;
    private Long beamOutwardId;
    private Long weavingContractId;
    private Long weavingVendorId;
    private Long salesOrderId;
    private String sizedBeamNo;
    private String flangeNo;
    private Long flangeId;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private String fabricPanelWidth;
    private Long totalEnds;
    private Long isIssuedToWeaving;
    private Long isFactoryInward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBeamOutwardId() {
        return beamOutwardId;
    }

    public void setBeamOutwardId(Long beamOutwardId) {
        this.beamOutwardId = beamOutwardId;
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

    private Integer isReturn;
    private java.time.LocalDate returnDate;
    private String returnNo;
    private Long returnWeaverId;
    private Integer isBeamGated;

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public java.time.LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(java.time.LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

    public Long getReturnWeaverId() { return returnWeaverId; }
    public void setReturnWeaverId(Long returnWeaverId) { this.returnWeaverId = returnWeaverId; }

    public Integer getIsBeamGated() { return isBeamGated; }
    public void setIsBeamGated(Integer isBeamGated) { this.isBeamGated = isBeamGated; }
}
