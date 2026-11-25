package com.tech3.erp.dto;

public class InspectionDetailDTO {
    private Long id;
    private Long fabricInspectionId;
    private String pieceNo; // Renamed from rollNo
    private Double pieceMeters; // Renamed from doffMeters
    private Double rollDoffMeters;
    private Double inspectedMeters;
    private Double actualNetWeight; // Renamed from weight
    private Double avgWeight; // New field: actualNetWeight / pieceMeters
    private Double totalDefectPoints;
    private Double totalPoints;
    private Double pointsPer100Mtr;
    private Double defectCounts;
    private String grade;
    private Long gradeId; // NEW: For mapping grade string to ID (set in service if needed)
    private Boolean activeFlag;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFabricInspectionId() {
        return fabricInspectionId;
    }

    public void setFabricInspectionId(Long fabricInspectionId) {
        this.fabricInspectionId = fabricInspectionId;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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