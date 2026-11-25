package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_return_item", schema = "masters")
public class PurchaseReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_return_id")
    @JsonBackReference("purchaseReturnItems")
    private PurchaseReturn purchaseReturn;

    @Column(name = "inward_no")
    private String inwardNo;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "unit_per_bags")
    private String unitPerBags;

    @Column(name = "rejected_qty")
    private String rejectedQty;

    @Column(name = "return_quantity")
    private String returnQuantity;

    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "purchase_inward_id")
    private Long purchaseInwardId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "is_return")
    private Integer isReturn = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public PurchaseReturn getPurchaseReturn() { return purchaseReturn; }
    public void setPurchaseReturn(PurchaseReturn purchaseReturn) { this.purchaseReturn = purchaseReturn; }

    public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getUnitPerBags() { return unitPerBags; }
    public void setUnitPerBags(String unitPerBags) { this.unitPerBags = unitPerBags; }

    public String getRejectedQty() { return rejectedQty; }
    public void setRejectedQty(String rejectedQty) { this.rejectedQty = rejectedQty; }

    public String getReturnQuantity() { return returnQuantity; }
    public void setReturnQuantity(String returnQuantity) { this.returnQuantity = returnQuantity; }

    public Long getInwardId() { return inwardId; }
    public void setInwardId(Long inwardId) { this.inwardId = inwardId; }

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public Long getPurchaseInwardId() { return purchaseInwardId; }
    public void setPurchaseInwardId(Long purchaseInwardId) { this.purchaseInwardId = purchaseInwardId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}
