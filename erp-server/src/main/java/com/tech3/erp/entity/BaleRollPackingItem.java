package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bale_roll_packing_item", schema = "masters")
public class BaleRollPackingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bale_roll_packing_id")
    private BaleRollPacking baleRollPacking;

    @Column(name = "piece_no", length = 1000)
    private String pieceNo;

    @Column(name = "piece_id")
    private Long pieceId;

    @Column(name = "meters")
    private BigDecimal meters;

    @Column(name = "yards")
    private BigDecimal yards;

    @Column(name = "net_weight")
    private BigDecimal netWeight;

    @Column(name = "avg_weight")
    private BigDecimal avgWeight;

    @Column(name = "gross_weight")
    private BigDecimal grossWeight;

    @Column(name = "is_returned_piece")
    private Integer isReturnedPiece = 0;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BaleRollPacking getBaleRollPacking() { return baleRollPacking; }
    public void setBaleRollPacking(BaleRollPacking baleRollPacking) { this.baleRollPacking = baleRollPacking; }

    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }

    public Long getPieceId() { return pieceId; }
    public void setPieceId(Long pieceId) { this.pieceId = pieceId; }

    public BigDecimal getMeters() { return meters; }
    public void setMeters(BigDecimal meters) { this.meters = meters; }

    public BigDecimal getYards() { return yards; }
    public void setYards(BigDecimal yards) { this.yards = yards; }

    public BigDecimal getNetWeight() { return netWeight; }
    public void setNetWeight(BigDecimal netWeight) { this.netWeight = netWeight; }

    public BigDecimal getAvgWeight() { return avgWeight; }
    public void setAvgWeight(BigDecimal avgWeight) { this.avgWeight = avgWeight; }

    public BigDecimal getGrossWeight() { return grossWeight; }
    public void setGrossWeight(BigDecimal grossWeight) { this.grossWeight = grossWeight; }

    public Integer getisReturnedPiece() { return isReturnedPiece; }
    public void setisReturnedPiece(Integer isReturnedPiece) { this.isReturnedPiece = isReturnedPiece; }
}