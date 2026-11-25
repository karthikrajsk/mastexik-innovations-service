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
@Table(name = "sales_return_sizing", schema = "masters")
public class SalesReturnSizing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_return_id")
    private SalesReturn salesReturn;

    @Column(name = "sized_beam_no")
    private String sizedBeamNo;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "beam_meter")
    private Double beamMeter;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "width")
    private Double width;

    @Column(name = "expected_fabric_meter")
    private Double expectedFabricMeter;

    @Column(name = "total_ends")
    private Integer totalEnds;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SalesReturn getSalesReturn() { return salesReturn; }
    public void setSalesReturn(SalesReturn salesReturn) { this.salesReturn = salesReturn; }

    public String getSizedBeamNo() { return sizedBeamNo; }
    public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public Double getBeamMeter() { return beamMeter; }
    public void setBeamMeter(Double beamMeter) { this.beamMeter = beamMeter; }

    public Double getNetWeight() { return netWeight; }
    public void setNetWeight(Double netWeight) { this.netWeight = netWeight; }

    public Double getWidth() { return width; }
    public void setWidth(Double width) { this.width = width; }

    public Double getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(Double expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

    public Integer getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Integer totalEnds) { this.totalEnds = totalEnds; }
}
