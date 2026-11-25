package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SummaryRoll {

    @Column(name = "packing_list_no")
    private String packingListNo;

    @Column(name = "no_of_rolls")
    private Integer noOfRolls;

    @Column(name = "total_meters")
    private Double totalMeters;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "gross_weight")
    private Double grossWeight;

    public SummaryRoll() {
    }

    public SummaryRoll(String packingListNo, Integer noOfRolls, Double totalMeters, Double netWeight, Double grossWeight) {
        this.packingListNo = packingListNo;
        this.noOfRolls = noOfRolls;
        this.totalMeters = totalMeters;
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
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