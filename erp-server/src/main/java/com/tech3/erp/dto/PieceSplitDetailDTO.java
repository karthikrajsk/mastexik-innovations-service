package com.tech3.erp.dto;

import com.tech3.erp.entity.PieceSplitDetail;
import java.math.BigDecimal;

public class PieceSplitDetailDTO {
    private Long id;
    private String pieceNo;
    private String pieceMeter;
    private BigDecimal netWeight;
    private BigDecimal avgWt;
    private Long gradeId;
    private Integer operationId;
    private Long modeId;
    private Long fabricMasterId;

    public PieceSplitDetailDTO() {}

    public PieceSplitDetailDTO(PieceSplitDetail entity) {
        this.id = entity.getId();
        this.pieceNo = entity.getPieceNo();
        this.pieceMeter = entity.getPieceMeter();
        this.netWeight = entity.getNetWeight();
        this.avgWt = entity.getAvgWt();
        this.gradeId = entity.getGradeId();
        this.operationId = entity.getOperationId();
        this.modeId = entity.getModeId();
        this.fabricMasterId = entity.getFabricMasterId();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }
    public String getPieceMeter() { return pieceMeter; }
    public void setPieceMeter(String pieceMeter) { this.pieceMeter = pieceMeter; }
    public BigDecimal getNetWeight() { return netWeight; }
    public void setNetWeight(BigDecimal netWeight) { this.netWeight = netWeight; }
    public BigDecimal getAvgWt() { return avgWt; }
    public void setAvgWt(BigDecimal avgWt) { this.avgWt = avgWt; }

    public Long getGradeId() { return gradeId; }
    public void setGradeId(Long gradeId) { this.gradeId = gradeId; }

    public Integer getOperationId() { return operationId; }
    public void setOperationId(Integer operationId) { this.operationId = operationId; }

    public Long getModeId() { return modeId; }
    public void setModeId(Long modeId) { this.modeId = modeId; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }
}
