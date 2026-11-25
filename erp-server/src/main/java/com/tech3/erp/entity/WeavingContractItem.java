package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "weaving_contract_item", schema = "masters")
public class WeavingContractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weaving_contract_id")
    @JsonBackReference
    private WeavingContract weavingContract;
    private Long fabricCodeId;
    private BigDecimal quantity;
    private BigDecimal pickCost;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private BigDecimal dailyTarget;
    private Integer numberOfLooms;
    private BigDecimal warpLength;
    private BigDecimal warpCrimpPercentage;
    private BigDecimal pieceLength;
    private Integer numberOfPieces;
    private BigDecimal ppi;

    @Column(name = "no_of_days")
    private Integer noOfDays;

    @Column(name = "loom_type")
    private String loomType;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeavingContract getWeavingContract() {
        return weavingContract;
    }

    public void setWeavingContract(WeavingContract weavingContract) {
        this.weavingContract = weavingContract;
    }

    public Long getFabricCodeId() {
        return fabricCodeId;
    }

    public void setFabricCodeId(Long fabricCodeId) {
        this.fabricCodeId = fabricCodeId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPickCost() {
        return pickCost;
    }

    public void setPickCost(BigDecimal pickCost) {
        this.pickCost = pickCost;
    }

    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(LocalDate plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public BigDecimal getDailyTarget() {
        return dailyTarget;
    }

    public void setDailyTarget(BigDecimal dailyTarget) {
        this.dailyTarget = dailyTarget;
    }

    public Integer getNumberOfLooms() {
        return numberOfLooms;
    }

    public void setNumberOfLooms(Integer numberOfLooms) {
        this.numberOfLooms = numberOfLooms;
    }

    public BigDecimal getWarpLength() {
        return warpLength;
    }

    public void setWarpLength(BigDecimal warpLength) {
        this.warpLength = warpLength;
    }

    public BigDecimal getWarpCrimpPercentage() {
        return warpCrimpPercentage;
    }

    public void setWarpCrimpPercentage(BigDecimal warpCrimpPercentage) {
        this.warpCrimpPercentage = warpCrimpPercentage;
    }

    public BigDecimal getPieceLength() {
        return pieceLength;
    }

    public void setPieceLength(BigDecimal pieceLength) {
        this.pieceLength = pieceLength;
    }

    public Integer getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(Integer numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }
    
    public BigDecimal getPpi() {
        return ppi;
    }

    public void setPpi(BigDecimal ppi) {
        this.ppi = ppi;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getLoomType() {
        return loomType;
    }

    public void setLoomType(String loomType) {
        this.loomType = loomType;
    }
}