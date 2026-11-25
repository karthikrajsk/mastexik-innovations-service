package com.tech3.erp.dto;

public class FabricDispatchFabricIssueDTO {
    
    private Long id;
    private String pieceNo;
    private String fabricCode;
    private String fabricName;
    private Double availableQty;
    private Double issueQty;
    private String issueType; // "Roll" or "Piece"
    private Boolean activeFlag;
    
    private Double meters;
    private Double netWeight;
    private Double avgWeight;
    private String gradeLabel;

    // Constructors
    public FabricDispatchFabricIssueDTO() {}
    
    public FabricDispatchFabricIssueDTO(Long id, String pieceNo, String fabricCode, String fabricName, 
            Double availableQty, Double issueQty, String issueType, Boolean activeFlag,
            Double meters, Double netWeight, Double avgWeight, String gradeLabel) {
this.id = id;
this.pieceNo = pieceNo;
this.fabricCode = fabricCode;
this.fabricName = fabricName;
this.availableQty = availableQty;
this.issueQty = issueQty;
this.issueType = issueType;
this.activeFlag = activeFlag;
this.meters = meters;
this.netWeight = netWeight;
this.avgWeight = avgWeight;
this.gradeLabel = gradeLabel;
}
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }
    
    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }
    
    public String getFabricName() { return fabricName; }
    public void setFabricName(String fabricName) { this.fabricName = fabricName; }
    
    public Double getAvailableQty() { return availableQty; }
    public void setAvailableQty(Double availableQty) { this.availableQty = availableQty; }
    
    public Double getIssueQty() { return issueQty; }
    public void setIssueQty(Double issueQty) { this.issueQty = issueQty; }
    
    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }
    
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
    
    public Double getMeters() { return meters; }
    public void setMeters(Double meters) { this.meters = meters; }
    
    public Double getNetWeight() { return netWeight; }
    public void setNetWeight(Double netWeight) { this.netWeight = netWeight; }
    
    public Double getAvgWeight() { return avgWeight; }
    public void setAvgWeight(Double avgWeight) { this.avgWeight = avgWeight; }
    
    public String getGradeLabel() { return gradeLabel; }
    public void setGradeLabel(String gradeLabel) { this.gradeLabel = gradeLabel; }
}