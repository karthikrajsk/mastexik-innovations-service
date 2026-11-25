package com.tech3.erp.dto;

import com.tech3.erp.entity.StockFabricTransferEntity;

public class StockFabricTransferDTO {
    
    private Long id;
    private Long stockTransferId;
    private String rollNo;
    private String pieceNo;
    private Double meter;  // Changed from material to meter
    private Double netWeight;
    private Double avgWeight;
    
    // Constructors
    public StockFabricTransferDTO() {}
    
    public StockFabricTransferDTO(Long stockTransferId, String rollNo, String pieceNo, Double meter, Double netWeight, Double avgWeight) {
        this.stockTransferId = stockTransferId;
        this.rollNo = rollNo;
        this.pieceNo = pieceNo;
        this.meter = meter;
        this.netWeight = netWeight;
        this.avgWeight = avgWeight;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getStockTransferId() {
        return stockTransferId;
    }
    
    public void setStockTransferId(Long stockTransferId) {
        this.stockTransferId = stockTransferId;
    }
    
    public String getRollNo() {
        return rollNo;
    }
    
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
    
    public String getPieceNo() {
        return pieceNo;
    }
    
    public void setPieceNo(String pieceNo) {
        this.pieceNo = pieceNo;
    }
    
    public Double getMeter() {
        return meter;
    }
    
    public void setMeter(Double meter) {
        this.meter = meter;
    }
    
    public Double getNetWeight() {
        return netWeight;
    }
    
    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(Double avgWeight) {
        this.avgWeight = avgWeight;
    }

    @Override
    public String toString() {
        return "StockFabricTransferDTO{" +
                "id=" + id +
                ", stockTransferId=" + stockTransferId +
                ", rollNo='" + rollNo + '\'' +
                ", pieceNo='" + pieceNo + '\'' +
                ", meter=" + meter +
                ", netWeight=" + netWeight +
                ", avgWeight=" + avgWeight +
                '}';
    }
    
    // Utility method to convert Entity to DTO
    public static StockFabricTransferDTO fromEntity(StockFabricTransferEntity entity) {
        StockFabricTransferDTO dto = new StockFabricTransferDTO();
        dto.setId(entity.getId());
        dto.setStockTransferId(entity.getStockTransfer() != null ? entity.getStockTransfer().getId() : null);
        dto.setRollNo(entity.getRollNo());
        dto.setPieceNo(entity.getPieceNo());
        dto.setMeter(entity.getMeter());
        dto.setNetWeight(entity.getNetWeight());
        dto.setAvgWeight(entity.getAvgWeight());
        return dto;
    }

    // Utility method to convert DTO to Entity
    public StockFabricTransferEntity toEntity() {
        StockFabricTransferEntity entity = new StockFabricTransferEntity();
        entity.setId(this.id);
        entity.setRollNo(this.rollNo);
        entity.setPieceNo(this.pieceNo);
        entity.setMeter(this.meter);
        entity.setNetWeight(this.netWeight);
        entity.setAvgWeight(this.avgWeight);
        return entity;
    }
}