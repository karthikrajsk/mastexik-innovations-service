package com.tech3.erp.dto;

import com.tech3.erp.entity.YarnInwardStockDetails;
import java.math.BigDecimal;
import java.time.LocalDate;

public class YarnInwardStockDetailsDto {

    private Long orderItemId;
    private Long inwardId;
    private Long inwardTypeId;
    private Long outwardTypeId;
    private Long itemId;
    private Long yarnInwardStockDetailsId;
    private Long uomId;
    private Long millId;
    private Long warehouseId;
    private String lotNo;
    private BigDecimal inwardQty;
    private BigDecimal inwardBags;
    private BigDecimal kgsPerBag;
    private BigDecimal conesPerBag;
    private BigDecimal conesWeight;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;
    private BigDecimal availableQty;
    private BigDecimal availableBags;
    private BigDecimal availableCones;
    private Long yarnTypeId;
    private String inwardNo;
    private String orderNo;
    private Long poCategoryId;
    private String millInvNo;
    private LocalDate millDate;

    public YarnInwardStockDetailsDto() {}

    public YarnInwardStockDetailsDto(YarnInwardStockDetails entity) {
        if (entity == null) return;
        this.yarnInwardStockDetailsId = entity.getId();
        this.orderItemId = entity.getOrderItemId();
        this.inwardId = (entity.getYarnInventory() != null && entity.getYarnInventory().getInwardId() != null) 
                        ? entity.getYarnInventory().getInwardId() 
                        : (entity.getInwardId() != null ? entity.getInwardId() : null);
        this.inwardTypeId = entity.getInwardTypeId();
        this.itemId = entity.getItemId();
        this.uomId = entity.getUomId();
        this.millId = entity.getMillId();
        this.warehouseId = entity.getWarehouseId();
        this.lotNo = entity.getLotNo();
        this.inwardQty = entity.getInwardQty();
        this.inwardBags = entity.getInwardBags();
        this.kgsPerBag = entity.getKgsPerBag();
        this.conesPerBag = entity.getConesPerBag();
        this.conesWeight = entity.getConesWeight();
        this.acceptedQty = entity.getAcceptedQty();
        this.rejectedQty = entity.getRejectedQty();
        this.availableQty = entity.getAvailableQty();
        this.availableBags = entity.getAvailableBags();
        this.availableCones = entity.getAvailableCones() != null 
                        ? entity.getAvailableCones() 
                        : (entity.getAvailableBags() != null && entity.getConesPerBag() != null 
                            ? entity.getAvailableBags().multiply(entity.getConesPerBag()) 
                            : BigDecimal.ZERO);
        this.yarnTypeId = entity.getYarnTypeId();
        this.inwardNo = (entity.getYarnInventory() != null) 
                        ? entity.getYarnInventory().getInwardNo() 
                        : null;
        this.orderNo = (entity.getYarnInventory() != null) 
                        ? entity.getYarnInventory().getOrderNo() 
                        : null;
        this.poCategoryId = entity.getPoCategoryId();
        this.millInvNo = entity.getMillInvNo();
        this.millDate = entity.getMillDate();
    }

    // Getters and setters
    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public Long getInwardId() { return inwardId; }
    public void setInwardId(Long inwardId) { this.inwardId = inwardId; }

    public Long getInwardTypeId() { return inwardTypeId; }
    public void setInwardTypeId(Long inwardTypeId) { this.inwardTypeId = inwardTypeId; }

    public Long getOutwardTypeId() { return outwardTypeId; }
    public void setOutwardTypeId(Long outwardTypeId) { this.outwardTypeId = outwardTypeId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getYarnInwardStockDetailsId() { return yarnInwardStockDetailsId; }
    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) { this.yarnInwardStockDetailsId = yarnInwardStockDetailsId; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }

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

    public BigDecimal getAvailableCones() { return availableCones; }
    public void setAvailableCones(BigDecimal availableCones) { this.availableCones = availableCones; }

    public Long getYarnTypeId() { return yarnTypeId; }
    public void setYarnTypeId(Long yarnTypeId) { this.yarnTypeId = yarnTypeId; }

   public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Long getPoCategoryId() { return poCategoryId; }
    public void setPoCategoryId(Long poCategoryId) { this.poCategoryId = poCategoryId; }

    public String getMillInvNo() { return millInvNo; }
    public void setMillInvNo(String millInvNo) { this.millInvNo = millInvNo; }

    public LocalDate getMillDate() { return millDate; }
    public void setMillDate(LocalDate millDate) { this.millDate = millDate; }
}