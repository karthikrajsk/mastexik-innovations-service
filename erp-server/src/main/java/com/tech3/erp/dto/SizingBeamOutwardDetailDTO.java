package com.tech3.erp.dto;

public class SizingBeamOutwardDetailDTO {
    private Long id;
    private String sizedBeamNo;
    private Double width;
    private Integer totalEnds;
    private Double beamMeters;
    private Double expectedFabricMeter;
    private String flangeNo;
    private Long flangeId;
    private String weavingContractNo;
    private String salesOrderNo;
    private Long weavingContractId;
    private Long weavingVendorId;
    private Long salesOrderId;
    private Long issuedTradingCompanyId;
    private Integer isBeamOutward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizedBeamNo() {
        return sizedBeamNo;
    }

    public void setSizedBeamNo(String sizedBeamNo) {
        this.sizedBeamNo = sizedBeamNo;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }

    public Double getBeamMeters() {
        return beamMeters;
    }

    public void setBeamMeters(Double beamMeters) {
        this.beamMeters = beamMeters;
    }

    public Double getExpectedFabricMeter() {
        return expectedFabricMeter;
    }

    public void setExpectedFabricMeter(Double expectedFabricMeter) {
        this.expectedFabricMeter = expectedFabricMeter;
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

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
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

    public Long getIssuedTradingCompanyId() {
        return issuedTradingCompanyId;
    }

    public void setIssuedTradingCompanyId(Long issuedTradingCompanyId) {
        this.issuedTradingCompanyId = issuedTradingCompanyId;
    }

    public Integer getIsBeamOutward() {
        return isBeamOutward;
    }

    public void setIsBeamOutward(Integer isBeamOutward) {
        this.isBeamOutward = isBeamOutward;
    }
}
