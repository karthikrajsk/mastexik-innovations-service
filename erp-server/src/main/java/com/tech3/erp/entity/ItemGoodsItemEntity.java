package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_goods_item", schema = "masters")
public class ItemGoodsItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_balance_id", nullable = false)
    private Long openingBalanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_balance_id", insertable = false, updatable = false)
    private OpeningBalanceEntity openingBalance;

    @Column(name = "s_no")
    private Integer sNo;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag", precision = 19, scale = 3)
    private BigDecimal kgsPerBag;

    @Column(name = "accepted_qty", precision = 19, scale = 3)
    private BigDecimal acceptedQty;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOpeningBalanceId() { return openingBalanceId; }
    public void setOpeningBalanceId(Long openingBalanceId) { this.openingBalanceId = openingBalanceId; }

    public OpeningBalanceEntity getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(OpeningBalanceEntity openingBalance) { this.openingBalance = openingBalance; }

    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }

    public BigDecimal getAcceptedQty() { return acceptedQty; }
    public void setAcceptedQty(BigDecimal acceptedQty) { this.acceptedQty = acceptedQty; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }
}
