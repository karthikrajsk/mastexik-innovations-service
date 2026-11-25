package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "piece_split_detail", schema = "masters")
public class PieceSplitDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_split_id")
    private PieceSplit pieceSplit;

    @Column(name = "piece_no", length = 100)
    private String pieceNo;

    @Column(name = "piece_meter", length = 50)
    private String pieceMeter;

    @Column(name = "net_weight", precision = 15, scale = 3)
    private BigDecimal netWeight;

    @Column(name = "avg_wt", precision = 15, scale = 3)
    private BigDecimal avgWt;

    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "operation_id")
    private Integer operationId;

    @Column(name = "mode_id")
    private Long modeId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PieceSplit getPieceSplit() { return pieceSplit; }
    public void setPieceSplit(PieceSplit pieceSplit) { this.pieceSplit = pieceSplit; }

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
