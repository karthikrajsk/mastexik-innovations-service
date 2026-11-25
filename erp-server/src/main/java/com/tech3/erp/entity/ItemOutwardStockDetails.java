package com.tech3.erp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "item_outward_stock_details", schema = "masters")
public class ItemOutwardStockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_outward_id")
    @JsonBackReference
    private ItemOutward itemOutward;

    @Column(name = "inward_no")
    private String inwardNo;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @Column(name = "warehouse", nullable = false)
    private String warehouse;

    @Column(name = "available_bags")
    private Integer availableBags;

    @Column(name = "unit_per_bag")
    private Integer unitPerBag;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "item_inward_stock_details_id")
    private Long itemInwardStockDetailsId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "return_item_id")
    private Long returnItemId;

    @Column(name = "issuing_bags")
    private Integer issuingBags;

    @Column(name = "issuing_quantity")
    private Integer issuingQuantity;

    @Column(name = "machine_id")
    private Long machineId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemOutward getItemOutward() {
        return itemOutward;
    }

    public void setItemOutward(ItemOutward itemOutward) {
        this.itemOutward = itemOutward;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getAvailableBags() {
        return availableBags;
    }

    public void setAvailableBags(Integer availableBags) {
        this.availableBags = availableBags;
    }

    public Integer getUnitPerBag() {
        return unitPerBag;
    }

    public void setUnitPerBag(Integer unitPerBag) {
        this.unitPerBag = unitPerBag;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Long getItemInwardStockDetailsId() {
        return itemInwardStockDetailsId;
    }

    public void setItemInwardStockDetailsId(Long itemInwardStockDetailsId) {
        this.itemInwardStockDetailsId = itemInwardStockDetailsId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getReturnItemId() {
        return returnItemId;
    }

    public void setReturnItemId(Long returnItemId) {
        this.returnItemId = returnItemId;
    }

    public Integer getIssuingBags() {
        return issuingBags;
    }

    public void setIssuingBags(Integer issuingBags) {
        this.issuingBags = issuingBags;
    }

    public Integer getIssuingQuantity() {
        return issuingQuantity;
    }

    public void setIssuingQuantity(Integer issuingQuantity) {
        this.issuingQuantity = issuingQuantity;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}

