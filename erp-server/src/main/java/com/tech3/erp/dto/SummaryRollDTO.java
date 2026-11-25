package com.tech3.erp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SummaryRollDTO {

    @NotBlank
    private String packingListNo;

    @NotNull
    @Min(0)
    private Integer noOfRolls;

    @NotNull
    @DecimalMin("0")
    private Double totalMeters;

    @NotNull
    @DecimalMin("0")
    private Double netWeight;

    @NotNull
    @DecimalMin("0")
    private Double grossWeight;

    public SummaryRollDTO() {
    }

    public String getPackingListNo() {
        return packingListNo;
    }

    public void setPackingListNo(String packingListNo) {
        this.packingListNo = packingListNo;
    }

    public Integer getNoOfRolls() {
        return noOfRolls;
    }

    public void setNoOfRolls(Integer noOfRolls) {
        this.noOfRolls = noOfRolls;
    }

    public Double getTotalMeters() {
        return totalMeters;
    }

    public void setTotalMeters(Double totalMeters) {
        this.totalMeters = totalMeters;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }
}