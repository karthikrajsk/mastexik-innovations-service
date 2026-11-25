package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_yarn_transfer", schema = "masters")
public class StockYarnTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_transfer_id", nullable = false)
    private StockTransferEntity stockTransfer;

    @Column(name = "inward_no", nullable = false)
    private Long inwardNo;

    @Column(name = "mill_name", nullable = false)
    private String millName;

    @Column(name = "lot_no", nullable = false)
    private String lotNo;

    @Column(name = "no_of_bags", nullable = false)
    private Integer noOfBags;

    @Column(name = "kgs_per_bag", nullable = false, precision = 19, scale = 2)
    private BigDecimal kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "total_quantity", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalQuantity;

    @Column(name = "issued_qty", precision = 19, scale = 2)
    private BigDecimal issuedQty;


    public StockYarnTransferEntity() {
    }

    public StockYarnTransferEntity(Long inwardNo, String millName, String lotNo, Integer noOfBags, BigDecimal kgsPerBag, BigDecimal totalQuantity) {
        this.inwardNo = inwardNo;
        this.millName = millName;
        this.lotNo = lotNo;
        this.noOfBags = noOfBags;
        this.kgsPerBag = kgsPerBag;
        this.totalQuantity = totalQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockTransferEntity getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferEntity stockTransfer) {
        this.stockTransfer = stockTransfer;
    }

    public Long getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(Long inwardNo) {
        this.inwardNo = inwardNo;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public BigDecimal getConesPerBag() {
        return conesPerBag;
    }

    public void setConesPerBag(BigDecimal conesPerBag) {
        this.conesPerBag = conesPerBag;
    }

    public BigDecimal getConesWeight() {
        return conesWeight;
    }

    public void setConesWeight(BigDecimal conesWeight) {
        this.conesWeight = conesWeight;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

     public BigDecimal getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(BigDecimal issuedQty) {
        this.issuedQty = issuedQty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockYarnTransferEntity)) return false;
        StockYarnTransferEntity that = (StockYarnTransferEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
