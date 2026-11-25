package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ScannedRoll {

    @Column(name = "packing_list_no")
    private String packingListNo;

    @Column(name = "roll_no")
    private String rollNo;

    @Column(name = "is_loaded_packing")
    private Integer isLoadedPacking;

    public ScannedRoll() {
    }

    public ScannedRoll(String packingListNo, String rollNo, Integer isLoadedPacking) {
        this.packingListNo = packingListNo;
        this.rollNo = rollNo;
        this.isLoadedPacking = isLoadedPacking;
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