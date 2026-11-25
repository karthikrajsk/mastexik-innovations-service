package com.tech3.erp.dto;

import java.util.List;

public class FinishFabricReceiveItemDTO {
    private Long id;
    private String finishedFabricCodeQuality;
    private String pieceRollNo;
    private Double meter;
    private Double yards;
    private Double netWeight;
    private Double averageWeight;
    private Long gradeId;
    private Long warehouseId;
    private Long fabricMasterId;
    private Boolean activeFlag;
    private List<PieceEntryResponseDTO> pieceEntries;

    public FinishFabricReceiveItemDTO() {}

    public FinishFabricReceiveItemDTO(Long id, String finishedFabricCodeQuality, String pieceRollNo, 
                                     Double meter, Double yards, Double netWeight, Double averageWeight,
                                     Long gradeId, Long warehouseId, Long fabricMasterId, Boolean activeFlag) {
        this.id = id;
        this.finishedFabricCodeQuality = finishedFabricCodeQuality;
        this.pieceRollNo = pieceRollNo;
        this.meter = meter;
        this.yards = yards;
        this.netWeight = netWeight;
        this.averageWeight = averageWeight;
        this.gradeId = gradeId;
        this.warehouseId = warehouseId;
        this.fabricMasterId = fabricMasterId;
        this.activeFlag = activeFlag;
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getFinishedFabricCodeQuality() { 
        return finishedFabricCodeQuality; 
    }
    
    public void setFinishedFabricCodeQuality(String finishedFabricCodeQuality) { 
        this.finishedFabricCodeQuality = finishedFabricCodeQuality; 
    }

    public String getPieceRollNo() { 
        return pieceRollNo; 
    }
    
    public void setPieceRollNo(String pieceRollNo) { 
        this.pieceRollNo = pieceRollNo; 
    }

    public Double getMeter() { 
        return meter; 
    }
    
    public void setMeter(Double meter) { 
        this.meter = meter; 
    }

    public Double getYards() { 
        return yards; 
    }
    
    public void setYards(Double yards) { 
        this.yards = yards; 
    }

    public Double getNetWeight() { 
        return netWeight; 
    }
    
    public void setNetWeight(Double netWeight) { 
        this.netWeight = netWeight; 
    }

    public Double getAverageWeight() { 
        return averageWeight; 
    }
    
    public void setAverageWeight(Double averageWeight) { 
        this.averageWeight = averageWeight; 
    }

    public Long getGradeId() { 
        return gradeId; 
    }
    
    public void setGradeId(Long gradeId) { 
        this.gradeId = gradeId; 
    }

    public Long getWarehouseId() { 
        return warehouseId; 
    }
    
    public void setWarehouseId(Long warehouseId) { 
        this.warehouseId = warehouseId; 
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public Boolean getActiveFlag() { 
        return activeFlag; 
    }
    
    public void setActiveFlag(Boolean activeFlag) { 
        this.activeFlag = activeFlag; 
    }

    public List<PieceEntryResponseDTO> getPieceEntries() {
        return pieceEntries;
    }

    public void setPieceEntries(List<PieceEntryResponseDTO> pieceEntries) {
        this.pieceEntries = pieceEntries;
    }
}
