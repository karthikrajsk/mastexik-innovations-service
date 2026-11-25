package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_sizing_sales", schema = "masters")
public class InvoiceSizingSalesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "beam_outward_no")
    private String beamOutwardNo;

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "no_of_beams")
    private Integer noOfBeams;

    @Column(name = "beam_meters")
    private BigDecimal beamMeters;

    @Column(name = "net_weight")
    private BigDecimal netWeight;

    @Column(name = "total_ends")
    private Integer totalEnds;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

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
