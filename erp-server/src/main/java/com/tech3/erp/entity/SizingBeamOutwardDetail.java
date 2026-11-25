package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sizing_beam_outward_detail", schema = "masters")
public class SizingBeamOutwardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizing_beam_outward_id")
    private SizingBeamOutward sizingBeamOutward;

    @Column(name = "sized_beam_no")
    private String sizedBeamNo;

    @Column(name = "width")
    private Double width;

    @Column(name = "total_ends")
    private Integer totalEnds;

    @Column(name = "beam_meters")
    private Double beamMeters;

    @Column(name = "expected_fabric_meter")
    private Double expectedFabricMeter;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "weaving_contract_no")
    private String weavingContractNo;

    @Column(name = "sales_order_no")
    private String salesOrderNo;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_vendor_id")
    private Long weavingVendorId;

    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "issued_trading_company_id")
    private Long issuedTradingCompanyId;

    @Column(name = "is_beam_outward")
    private Integer isBeamOutward = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SizingBeamOutward getSizingBeamOutward() {
        return sizingBeamOutward;
    }

    public void setSizingBeamOutward(SizingBeamOutward sizingBeamOutward) {
        this.sizingBeamOutward = sizingBeamOutward;
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
