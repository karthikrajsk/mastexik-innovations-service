package com.tech3.erp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FabricDetailsDto {

    @NotNull(message = "Grey width is required")
    @DecimalMin(value = "0.01", message = "Grey width must be greater than 0")
    private BigDecimal greyWidth;

    @NotNull(message = "EPI difference is required")
    private BigDecimal epiDifference;

    @NotNull(message = "EPI on table is required")
    @DecimalMin(value = "0.01", message = "EPI on table must be greater than 0")
    private BigDecimal epiOnTable;

    @NotNull(message = "Reed space is required")
    @DecimalMin(value = "0.01", message = "Reed space must be greater than 0")
    private BigDecimal reedSpace;

    @NotNull(message = "Total ends is required")
    @DecimalMin(value = "1", message = "Total ends must be at least 1")
    private BigDecimal totalEnds;

    @NotNull(message = "Warp crimp is required")
    @DecimalMin(value = "0.00", message = "Warp crimp cannot be negative")
    private BigDecimal warpCrimp;

    // Constructors
    public FabricDetailsDto() {}

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