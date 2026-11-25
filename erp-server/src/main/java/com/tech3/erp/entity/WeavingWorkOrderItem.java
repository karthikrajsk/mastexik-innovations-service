package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "weaving_work_order_item", schema = "masters")
public class WeavingWorkOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weaving_work_order_id")
    @JsonBackReference
    private WeavingWorkOrder weavingWorkOrder;

    private Long fabricCodeId;
    private Long uomId;
    private BigDecimal quantity;
    private BigDecimal pickCost;
    @Column(name = "rate_per_unit")
    private BigDecimal ratePerUnit;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private BigDecimal dailyTarget;
    private Integer numberOfLooms;
    private BigDecimal warpLength;
    private BigDecimal warpCrimpPercentage;
    private BigDecimal pieceLength;
    private Integer numberOfPieces;
    private BigDecimal ppi;
    private BigDecimal taxableAmount;
    private BigDecimal gstPercent;
    private BigDecimal totalAmount;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WeavingWorkOrder getWeavingWorkOrder() { return weavingWorkOrder; }
    public void setWeavingWorkOrder(WeavingWorkOrder weavingWorkOrder) { this.weavingWorkOrder = weavingWorkOrder; }

    public Long getFabricCodeId() { return fabricCodeId; }
    public void setFabricCodeId(Long fabricCodeId) { this.fabricCodeId = fabricCodeId; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getPickCost() { return pickCost; }
    public void setPickCost(BigDecimal pickCost) { this.pickCost = pickCost; }

    public BigDecimal getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(BigDecimal ratePerUnit) { this.ratePerUnit = ratePerUnit; }

    public LocalDate getPlannedStartDate() { return plannedStartDate; }
    public void setPlannedStartDate(LocalDate plannedStartDate) { this.plannedStartDate = plannedStartDate; }

    public LocalDate getPlannedEndDate() { return plannedEndDate; }
    public void setPlannedEndDate(LocalDate plannedEndDate) { this.plannedEndDate = plannedEndDate; }

    public BigDecimal getDailyTarget() { return dailyTarget; }
    public void setDailyTarget(BigDecimal dailyTarget) { this.dailyTarget = dailyTarget; }

    public Integer getNumberOfLooms() { return numberOfLooms; }
    public void setNumberOfLooms(Integer numberOfLooms) { this.numberOfLooms = numberOfLooms; }

    public BigDecimal getWarpLength() { return warpLength; }
    public void setWarpLength(BigDecimal warpLength) { this.warpLength = warpLength; }

    public BigDecimal getWarpCrimpPercentage() { return warpCrimpPercentage; }
    public void setWarpCrimpPercentage(BigDecimal warpCrimpPercentage) { this.warpCrimpPercentage = warpCrimpPercentage; }

    public BigDecimal getPieceLength() { return pieceLength; }
    public void setPieceLength(BigDecimal pieceLength) { this.pieceLength = pieceLength; }

    public Integer getNumberOfPieces() { return numberOfPieces; }
    public void setNumberOfPieces(Integer numberOfPieces) { this.numberOfPieces = numberOfPieces; }

    public BigDecimal getPpi() { return ppi; }
    public void setPpi(BigDecimal ppi) { this.ppi = ppi; }

    public BigDecimal getTaxableAmount() { return taxableAmount; }
    public void setTaxableAmount(BigDecimal taxableAmount) { this.taxableAmount = taxableAmount; }

    public BigDecimal getGstPercent() { return gstPercent; }
    public void setGstPercent(BigDecimal gstPercent) { this.gstPercent = gstPercent; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}