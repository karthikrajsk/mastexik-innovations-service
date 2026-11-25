package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "opening_balance_summary_item", schema = "masters")
public class OpeningBalanceSummaryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "no_of_pieces")
    private Integer noOfPieces;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_balance_id")
    private OpeningBalanceEntity openingBalance;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Integer getNoOfPieces() { return noOfPieces; }
    public void setNoOfPieces(Integer noOfPieces) { this.noOfPieces = noOfPieces; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }

    public OpeningBalanceEntity getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(OpeningBalanceEntity openingBalance) { this.openingBalance = openingBalance; }
}
