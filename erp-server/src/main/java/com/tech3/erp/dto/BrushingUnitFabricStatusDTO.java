package com.tech3.erp.dto;

import java.math.BigDecimal;

public class BrushingUnitFabricStatusDTO {
    private Integer sNo;
    private String construction;
    private Long fabricMasterId;
    private BigDecimal brushedFabricStock;
    private BigDecimal waitingForBrushing;
    private BigDecimal rejectedFabric;

    public BrushingUnitFabricStatusDTO() {}

    public BrushingUnitFabricStatusDTO(String construction, Long fabricMasterId, BigDecimal brushedFabricStock, BigDecimal waitingForBrushing) {
        this(construction, fabricMasterId, brushedFabricStock, waitingForBrushing, BigDecimal.ZERO);
    }

    public BrushingUnitFabricStatusDTO(String construction, Long fabricMasterId, BigDecimal brushedFabricStock, BigDecimal waitingForBrushing, BigDecimal rejectedFabric) {
        this.construction = construction;
        this.fabricMasterId = fabricMasterId;
        this.brushedFabricStock = brushedFabricStock != null ? brushedFabricStock : BigDecimal.ZERO;
        this.waitingForBrushing = waitingForBrushing != null ? waitingForBrushing : BigDecimal.ZERO;
        this.rejectedFabric = rejectedFabric != null ? rejectedFabric : BigDecimal.ZERO;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public BigDecimal getBrushedFabricStock() {
        return brushedFabricStock;
    }

    public void setBrushedFabricStock(BigDecimal brushedFabricStock) {
        this.brushedFabricStock = brushedFabricStock;
    }

    public BigDecimal getWaitingForBrushing() {
        return waitingForBrushing;
    }

    public void setWaitingForBrushing(BigDecimal waitingForBrushing) {
        this.waitingForBrushing = waitingForBrushing;
    }

    public BigDecimal getRejectedFabric() {
        return rejectedFabric;
    }

    public void setRejectedFabric(BigDecimal rejectedFabric) {
        this.rejectedFabric = rejectedFabric;
    }
}
