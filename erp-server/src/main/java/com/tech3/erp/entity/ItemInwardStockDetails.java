package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "item_inward_stock_details", schema = "masters")
public class ItemInwardStockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "inward_id")
    @JsonBackReference
    private ItemInventory itemInventory;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "inward_item_id")
    private Long inwardItemId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "rack_no")
    private String rackNo;

    @Column(name = "inward_qty")
    private BigDecimal inwardQty;

    @Column(name = "inward_bags")
    private BigDecimal inwardBags;

    @Column(name = "kgs_per_bag", precision = 38, scale = 3)
    private BigDecimal kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "accepted_qty")
    private BigDecimal acceptedQty;

    @Column(name = "rejected_qty")
    private BigDecimal rejectedQty;

    @Column(name = "available_qty")
    private BigDecimal availableQty;

    @Column(name = "available_bags")
    private BigDecimal availableBags;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "po_category_id")
    private Long poCategoryId;

    // Getters and setters
    public ItemInventory getItemInventory() { return itemInventory; }
    public void setItemInventory(ItemInventory itemInventory) { this.itemInventory = itemInventory; }

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public Long getInwardItemId() { return inwardItemId; }
    public void setInwardItemId(Long inwardItemId) { this.inwardItemId = inwardItemId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }

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

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public BigDecimal calculateAvailableQty(BigDecimal issuedQty) {
        return acceptedQty.subtract(issuedQty);
    }

    public Long getPoCategoryId() { return poCategoryId; }
    public void setPoCategoryId(Long poCategoryId) { this.poCategoryId = poCategoryId; }
}

