package com.tech3.erp.dto;

public class FabricDispatchPieceDTO {
    
    private Long id;
    private String pieceNo;
    private Double availableQty;
    private Double issueQty;
    private Boolean activeFlag;

    // Constructors
    public FabricDispatchPieceDTO() {}

    public FabricDispatchPieceDTO(Long id, String pieceNo, Double availableQty, 
                                  Double issueQty, Boolean activeFlag) {
        this.id = id;
        this.pieceNo = pieceNo;
        this.availableQty = availableQty;
        this.issueQty = issueQty;
        this.activeFlag = activeFlag;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }
    
    public Double getAvailableQty() { return availableQty; }
    public void setAvailableQty(Double availableQty) { this.availableQty = availableQty; }
    
    public Double getIssueQty() { return issueQty; }
    public void setIssueQty(Double issueQty) { this.issueQty = issueQty; }
    
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}