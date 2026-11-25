package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "inspection_dtl", schema = "masters")
public class InspectionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fabric_inspection_id", referencedColumnName = "id")
    @JsonBackReference
    private FabricInspection fabricInspection;

    @Column(name = "roll_no", length = 100) // Renamed from roll_no
    private String pieceNo;
    

    @Column(name = "piece_meters") // Renamed from doff_meters
    private Double pieceMeters;

    @Column(name = "roll_doff_meters")
    private Double rollDoffMeters;

    @Column(name = "inspected_meters")
    private Double inspectedMeters;

    @Column(name = "actual_net_weight") // Renamed from weight
    private Double actualNetWeight;
    
    @Column(name = "avg_weight") // New field
    private Double avgWeight;

    @Column(name = "total_defect_points")
    private Double totalDefectPoints;

    @Column(name = "total_points")
    private Double totalPoints;

    @Column(name = "points_per_100mtr")
    private Double pointsPer100Mtr;

    @Column(name = "defect_counts")
    private Double defectCounts;

    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FabricInspection getFabricInspection() {
        return fabricInspection;
    }

    public void setFabricInspection(FabricInspection fabricInspection) {
        this.fabricInspection = fabricInspection;
    }

    public String getPieceNo() {
        return pieceNo;
    }

    public void setPieceNo(String pieceNo) {
        this.pieceNo = pieceNo;
    }

    public Double getPieceMeters() {
        return pieceMeters;
    }

    public void setPieceMeters(Double pieceMeters) {
        this.pieceMeters = pieceMeters;
    }

    public Double getInspectedMeters() {
        return inspectedMeters;
    }

    public void setInspectedMeters(Double inspectedMeters) {
        this.inspectedMeters = inspectedMeters;
    }

    public Double getActualNetWeight() {
        return actualNetWeight;
    }

    public void setActualNetWeight(Double actualNetWeight) {
        this.actualNetWeight = actualNetWeight;
    }

    public Double getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(Double avgWeight) {
        this.avgWeight = avgWeight;
    }

    public Double getTotalDefectPoints() {
        return totalDefectPoints;
    }

    public void setTotalDefectPoints(Double totalDefectPoints) {
        this.totalDefectPoints = totalDefectPoints;
    }

    public Double getDefectCounts() {
        return defectCounts;
    }

    public void setDefectCounts(Double defectCounts) {
        this.defectCounts = defectCounts;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getPointsPer100Mtr() {
        return pointsPer100Mtr;
    }

    public void setPointsPer100Mtr(Double pointsPer100Mtr) {
        this.pointsPer100Mtr = pointsPer100Mtr;
    }

    public Double getRollDoffMeters() {
        return rollDoffMeters;
    }

    public void setRollDoffMeters(Double rollDoffMeters) {
        this.rollDoffMeters = rollDoffMeters;
    }
}