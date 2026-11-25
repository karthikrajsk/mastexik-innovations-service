package com.tech3.erp.dto;

import com.tech3.erp.entity.ItemInwardStockDetails;
import java.math.BigDecimal;

public class ItemInwardStockDetailsDto {

    private Long id;
    private Long inwardId;
    private Long orderItemId;
    private Long outwardTypeId;
    private Long itemId;
    private Long itemOrderItemId;
    private Long itemInwardItemId;
    private String itemName;
    private Long uomId;
    private Long millId;
    private Long warehouseId;
    private String lotNo;
    private String rackNo;
    private BigDecimal inwardQty;
    private BigDecimal inwardBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;
    private BigDecimal availableQty;
    private BigDecimal availableBags;
    private String inwardNo;
    private String remark;
    private Long poCategoryId;

    public ItemInwardStockDetailsDto() {}

    public ItemInwardStockDetailsDto(ItemInwardStockDetails entity) {
        if (entity == null) return;
        this.id = entity.getId();
        this.inwardId = (entity.getItemInventory() != null && entity.getItemInventory().getId() != null) 
                        ? entity.getItemInventory().getId() 
                        : null;
        this.orderItemId = entity.getOrderItemId();
        this.itemId = entity.getItemId();
        this.uomId = entity.getUomId();
        this.millId = entity.getMillId();
        this.warehouseId = entity.getWarehouseId();
        this.lotNo = entity.getLotNo();
        this.rackNo = entity.getRackNo();
        this.inwardQty = entity.getInwardQty();
        this.inwardBags = entity.getInwardBags();
        this.kgsPerBag = entity.getKgsPerBag();
        this.conesPerBag = entity.getConesPerBag();
        this.conesWeight = entity.getConesWeight();
        this.acceptedQty = entity.getAcceptedQty();
        this.rejectedQty = entity.getRejectedQty();
        this.availableQty = entity.getAvailableQty();
        this.availableBags = entity.getAvailableBags();
        this.inwardNo = (entity.getItemInventory() != null) 
                        ? entity.getItemInventory().getInwardNo() 
                        : null;
        this.remark = entity.getRemark();
        this.poCategoryId = entity.getPoCategoryId();
    }

    // Getters and setters
    public Long getInwardId() { return inwardId; }
    public void setInwardId(Long inwardId) { this.inwardId = inwardId; }

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public Long getOutwardTypeId() { return outwardTypeId; }
    public void setOutwardTypeId(Long outwardTypeId) { this.outwardTypeId = outwardTypeId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }

    public String getRackNo() { return rackNo; }
    public void setRackNo(String rackNo) { this.rackNo = rackNo; }

    public BigDecimal getInwardQty() { return inwardQty; }
    public void setInwardQty(BigDecimal inwardQty) { this.inwardQty = inwardQty; }

    public BigDecimal getInwardBags() { return inwardBags; }
    public void setInwardBags(BigDecimal inwardBags) { this.inwardBags = inwardBags; }

    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }

    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }

    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }

    public BigDecimal getAcceptedQty() { return acceptedQty; }
    public void setAcceptedQty(BigDecimal acceptedQty) { this.acceptedQty = acceptedQty; }

    public BigDecimal getRejectedQty() { return rejectedQty; }
    public void setRejectedQty(BigDecimal rejectedQty) { this.rejectedQty = rejectedQty; }

    public BigDecimal getAvailableQty() { return availableQty; }
    public void setAvailableQty(BigDecimal availableQty) { this.availableQty = availableQty; }

    public BigDecimal getAvailableBags() { return availableBags; }
    public void setAvailableBags(BigDecimal availableBags) { this.availableBags = availableBags; }

    public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemOrderItemId() { return itemOrderItemId; }
    public void setItemOrderItemId(Long itemOrderItemId) { this.itemOrderItemId = itemOrderItemId; }

    public Long getItemInwardItemId() { return itemInwardItemId; }
    public void setItemInwardItemId(Long itemInwardItemId) { this.itemInwardItemId = itemInwardItemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Long getPoCategoryId() { return poCategoryId; }
    public void setPoCategoryId(Long poCategoryId) { this.poCategoryId = poCategoryId; }
}

