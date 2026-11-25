package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class FabricDetails {

    @Column(name = "grey_width", precision = 10, scale = 2)
    private BigDecimal greyWidth;

    @Column(name = "epi_difference", precision = 10, scale = 2)
    private BigDecimal epiDifference;

    @Column(name = "epi_on_table", precision = 10, scale = 2)
    private BigDecimal epiOnTable;

    @Column(name = "reed_space", precision = 10, scale = 2)
    private BigDecimal reedSpace;

    @Column(name = "total_ends", precision = 10, scale = 2)
    private BigDecimal totalEnds;

    @Column(name = "warp_crimp", precision = 10, scale = 2)
    private BigDecimal warpCrimp;

    // Constructors
    public FabricDetails() {}

    // Getters and Setters
    public BigDecimal getGreyWidth() { return greyWidth; }
    public void setGreyWidth(BigDecimal greyWidth) { this.greyWidth = greyWidth; }

    public BigDecimal getEpiDifference() { return epiDifference; }
    public void setEpiDifference(BigDecimal epiDifference) { this.epiDifference = epiDifference; }

    public BigDecimal getEpiOnTable() { return epiOnTable; }
    public void setEpiOnTable(BigDecimal epiOnTable) { this.epiOnTable = epiOnTable; }

    public BigDecimal getReedSpace() { return reedSpace; }
    public void setReedSpace(BigDecimal reedSpace) { this.reedSpace = reedSpace; }

    public BigDecimal getTotalEnds() { return totalEnds; }
    public void setTotalEnds(BigDecimal totalEnds) { this.totalEnds = totalEnds; }

    public BigDecimal getWarpCrimp() { return warpCrimp; }
    public void setWarpCrimp(BigDecimal warpCrimp) { this.warpCrimp = warpCrimp; }
}