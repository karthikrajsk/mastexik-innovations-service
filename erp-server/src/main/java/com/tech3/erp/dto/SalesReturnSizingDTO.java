package com.tech3.erp.dto;

import com.tech3.erp.entity.SalesReturnSizing;

public class SalesReturnSizingDTO {
    private Long id;
    private String sizedBeamNo;
    private String flangeNo;
    private Double beamMeter;
    private Double netWeight;
    private Double width;
    private Double expectedFabricMeter;
    private Integer totalEnds;

    public SalesReturnSizingDTO() {}

    public SalesReturnSizingDTO(SalesReturnSizing entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.sizedBeamNo = entity.getSizedBeamNo();
            this.flangeNo = entity.getFlangeNo();
            this.beamMeter = entity.getBeamMeter();
            this.netWeight = entity.getNetWeight();
            this.width = entity.getWidth();
            this.expectedFabricMeter = entity.getExpectedFabricMeter();
            this.totalEnds = entity.getTotalEnds();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
