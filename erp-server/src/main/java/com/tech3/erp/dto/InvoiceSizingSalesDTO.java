package com.tech3.erp.dto;

import java.math.BigDecimal;

public class InvoiceSizingSalesDTO {
    private Long id;
    private Long invoiceId;
    private String beamOutwardNo;
    private String yarnName;
    private Integer noOfBeams;
    private BigDecimal beamMeters;
    private BigDecimal netWeight;
    private Integer totalEnds;
    private Boolean activeFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getBeamOutwardNo() {
        return beamOutwardNo;
    }

    public void setBeamOutwardNo(String beamOutwardNo) {
        this.beamOutwardNo = beamOutwardNo;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Integer getNoOfBeams() {
        return noOfBeams;
    }

    public void setNoOfBeams(Integer noOfBeams) {
        this.noOfBeams = noOfBeams;
    }

    public BigDecimal getBeamMeters() {
        return beamMeters;
    }

    public void setBeamMeters(BigDecimal beamMeters) {
        this.beamMeters = beamMeters;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
