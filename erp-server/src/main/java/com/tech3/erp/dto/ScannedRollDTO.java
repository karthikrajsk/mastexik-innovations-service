package com.tech3.erp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ScannedRollDTO {

    @NotBlank
    private String packingListNo;

    @NotBlank
    private String rollNo;

    @NotNull
    @Min(0)
    @Max(1)
    private Integer isLoadedPacking;

    public ScannedRollDTO() {
    }

    public String getPackingListNo() {
        return packingListNo;
    }

    public void setPackingListNo(String packingListNo) {
        this.packingListNo = packingListNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public Integer getIsLoadedPacking() {
        return isLoadedPacking;
    }

    public void setIsLoadedPacking(Integer isLoadedPacking) {
        this.isLoadedPacking = isLoadedPacking;
    }
}