package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_item_transfer", schema = "masters")
public class StockItemTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_transfer_id", nullable = false)
    private StockTransferEntity stockTransfer;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "s_no")
    private Integer sNo;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag", precision = 19, scale = 3)
    private BigDecimal kgsPerBag;

    @Column(name = "accepted_qty", precision = 19, scale = 3)
    private BigDecimal acceptedQty;

    @Column(name = "issuing_bags")
    private Integer issuingBags;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    public StockItemTransferEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StockTransferEntity getStockTransfer() { return stockTransfer; }
    public void setStockTransfer(StockTransferEntity stockTransfer) { this.stockTransfer = stockTransfer; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }

    public BigDecimal getAcceptedQty() { return acceptedQty; }
    public void setAcceptedQty(BigDecimal acceptedQty) { this.acceptedQty = acceptedQty; }

    public Integer getIssuingBags() { return issuingBags; }
    public void setIssuingBags(Integer issuingBags) { this.issuingBags = issuingBags; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockItemTransferEntity)) return false;
        StockItemTransferEntity that = (StockItemTransferEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
