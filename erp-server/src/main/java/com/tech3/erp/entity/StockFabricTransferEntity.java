package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_fabric_transfer", schema="masters")
public class StockFabricTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_transfer_id", nullable = false)
    private StockTransferEntity stockTransfer;
    
    @Column(name = "roll_no", nullable = false)
    private String rollNo;
    
    @Column(name = "piece_no", nullable = false)
    private String pieceNo;
    
    @Column(name = "meter", nullable = false)  // Changed from material to meter
    private Double meter;
    
    @Column(name = "net_weight", nullable = false)
    private Double netWeight;

    @Column(name = "avg_weight", nullable = false)
    private Double avgWeight;
    
    // Constructors
    public StockFabricTransferEntity() {}
    
    public StockFabricTransferEntity(String rollNo, String pieceNo, Double meter, Double netWeight, Double avgWeight) {
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

    public StockTransferEntity getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferEntity stockTransfer) {
        this.stockTransfer = stockTransfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockFabricTransferEntity)) return false;
        StockFabricTransferEntity that = (StockFabricTransferEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "StockFabricTransferEntity{" +
                "id=" + id +
                ", rollNo='" + rollNo + '\'' +
                ", pieceNo='" + pieceNo + '\'' +
                ", meter=" + meter +
                ", netWeight=" + netWeight +
                ", avgWeight=" + avgWeight +
                '}';
    }
}