package com.tech3.erp.dto;

import com.tech3.erp.entity.PurchaseReturnItem;

public class PurchaseReturnItemDTO {
    private Long id;
    private String inwardNo;
    private String itemId;
    private String itemName;
    private String itemCode;
    private String warehouseId;
    private String unitPerBags;
    private String rejectedQty;
    private String returnQuantity;
    private String inwardId;
    private String orderItemId;
    private Long purchaseInwardId;
    private Integer isReturn = 0;

    public PurchaseReturnItemDTO() {}

    public PurchaseReturnItemDTO(PurchaseReturnItem e) {
        this.id = e.getId();
        this.inwardNo = e.getInwardNo();
        this.itemId = e.getItemId() != null ? e.getItemId().toString() : null;
        this.itemName = e.getItemName();
        this.itemCode = e.getItemCode();
        this.warehouseId = e.getWarehouseId() != null ? e.getWarehouseId().toString() : null;
        this.unitPerBags = e.getUnitPerBags();
        this.rejectedQty = e.getRejectedQty();
        this.returnQuantity = e.getReturnQuantity();
        this.inwardId = e.getInwardId() != null ? e.getInwardId().toString() : null;
        this.orderItemId = e.getOrderItemId() != null ? e.getOrderItemId().toString() : null;
        this.purchaseInwardId = e.getPurchaseInwardId(); // New field mapping
        this.isReturn = e.getIsReturn();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }

    public String getUnitPerBags() { return unitPerBags; }
    public void setUnitPerBags(String unitPerBags) { this.unitPerBags = unitPerBags; }

    public String getRejectedQty() { return rejectedQty; }
    public void setRejectedQty(String rejectedQty) { this.rejectedQty = rejectedQty; }

    public String getReturnQuantity() { return returnQuantity; }
    public void setReturnQuantity(String returnQuantity) { this.returnQuantity = returnQuantity; }

    public String getInwardId() { return inwardId; }
    public void setInwardId(String inwardId) { this.inwardId = inwardId; }

    public String getOrderItemId() { return orderItemId; }
    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }
    
    public Long getPurchaseInwardId() { return purchaseInwardId; }
    public void setPurchaseInwardId(Long purchaseInwardId) { this.purchaseInwardId = purchaseInwardId; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }
}
