package com.tech3.erp.dto;

import java.math.BigDecimal;

public class BaleRollPackingItemDTO {
    private Long id;
    private String pieceNo;
    private Long pieceId;
    private BigDecimal meters;
    private BigDecimal yards;
    private BigDecimal netWeight;
    private BigDecimal avgWeight;
    private BigDecimal grossWeight;
    private Integer isReturnedPiece;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Integer getIsReturnedPiece() { return isReturnedPiece; }
    public void setIsReturnedPiece(Integer isReturnedPiece) { this.isReturnedPiece = isReturnedPiece; }
}
