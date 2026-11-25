package com.tech3.erp.dto;

import com.tech3.erp.entity.ItemOutwardStockDetails;

public class ItemOutwardStockDetailsDTO {

    private Long id;
    private Long itemOutwardId;
    private String inwardNo;
    private String itemName;
    private String itemCode;
    private String warehouse;
    private Integer availableBags;
    private Integer unitPerBag;
    private Integer availableQuantity;
    private Long itemInwardStockDetailsId;
    private Long itemId;
    private Long returnItemId;
    private Integer issuingBags;
    private Integer issuingQuantity;
    private Long warehouseId;
    private Long itemStockDetailId; // Alternative field name for itemInwardStockDetailsId
    private Integer unitPerBags; // Alternative field name for unitPerBag
    private Long machineId;

    public ItemOutwardStockDetailsDTO() {}

    public ItemOutwardStockDetailsDTO(ItemOutwardStockDetails entity) {
        if (entity == null) return;
        
        this.id = entity.getId();
        this.itemOutwardId = (entity.getItemOutward() != null && entity.getItemOutward().getId() != null) 
                            ? entity.getItemOutward().getId() 
                            : null;
        this.inwardNo = entity.getInwardNo();
        this.itemName = entity.getItemName();
        this.itemCode = entity.getItemCode();
        this.warehouse = entity.getWarehouse();
        this.availableBags = entity.getAvailableBags();
        this.unitPerBag = entity.getUnitPerBag();
        this.availableQuantity = entity.getAvailableQuantity();
        this.itemInwardStockDetailsId = entity.getItemInwardStockDetailsId();
        this.itemId = entity.getItemId();
        this.returnItemId = entity.getReturnItemId();
        this.issuingBags = entity.getIssuingBags();
        this.issuingQuantity = entity.getIssuingQuantity();
        this.machineId = entity.getMachineId();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemOutwardId() {
        return itemOutwardId;
    }

    public void setItemOutwardId(Long itemOutwardId) {
        this.itemOutwardId = itemOutwardId;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getItemStockDetailId() {
        return itemStockDetailId;
    }

    public void setItemStockDetailId(Long itemStockDetailId) {
        this.itemStockDetailId = itemStockDetailId;
    }

    public Integer getUnitPerBags() {
        return unitPerBags;
    }

    public void setUnitPerBags(Integer unitPerBags) {
        this.unitPerBags = unitPerBags;
    }

    public Long getReturnItemId() {
        return returnItemId;
    }

    public void setReturnItemId(Long returnItemId) {
        this.returnItemId = returnItemId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}

