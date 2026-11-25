package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stock_transfer", schema = "masters")
public class StockTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_godown_id", nullable = false)
    private Warehouse fromGodown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_godown_id", nullable = false)
    private Warehouse toGodown;

    @Column(name = "goods_type", nullable = false)
    private String goodsType;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "stock_transfer_no")
    private String stockTransferNo;

    @OneToMany(mappedBy = "stockTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<StockFabricTransferEntity> fabricItems = new HashSet<>();

    @OneToMany(mappedBy = "stockTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<StockYarnTransferEntity> yarnItems = new HashSet<>();

    @OneToMany(mappedBy = "stockTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<StockItemTransferEntity> itemItems = new HashSet<>();

    // Default constructor
    public StockTransferEntity() {
    }

    // Parameterized constructor
    public StockTransferEntity(LocalDate date, Warehouse fromGodown, Warehouse toGodown, String goodsType, String itemName, Double quantity) {
        this.date = date;
        this.fromGodown = fromGodown;
        this.toGodown = toGodown;
        this.goodsType = goodsType;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Warehouse getFromGodown() {
        return fromGodown;
    }

    public void setFromGodown(Warehouse fromGodown) {
        this.fromGodown = fromGodown;
    }

    public Warehouse getToGodown() {
        return toGodown;
    }

    public void setToGodown(Warehouse toGodown) {
        this.toGodown = toGodown;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStockTransferNo() {
        return stockTransferNo;
    }

    public void setStockTransferNo(String stockTransferNo) {
        this.stockTransferNo = stockTransferNo;
    }

    public Set<StockFabricTransferEntity> getFabricItems() {
        return fabricItems;
    }

    public void setFabricItems(Set<StockFabricTransferEntity> fabricItems) {
        this.fabricItems = fabricItems;
    }

    public Set<StockYarnTransferEntity> getYarnItems() {
        return yarnItems;
    }

    public void setYarnItems(Set<StockYarnTransferEntity> yarnItems) {
        this.yarnItems = yarnItems;
    }

    public Set<StockItemTransferEntity> getItemItems() { return itemItems; }
    public void setItemItems(Set<StockItemTransferEntity> itemItems) { this.itemItems = itemItems; }

    public void addFabricItem(StockFabricTransferEntity fabricItem) {
        fabricItems.add(fabricItem);
    }

    public void addYarnItem(StockYarnTransferEntity yarnItem) {
        yarnItems.add(yarnItem);
    }

    public void addItemItem(StockItemTransferEntity itemItem) {
        itemItems.add(itemItem);
    }
}