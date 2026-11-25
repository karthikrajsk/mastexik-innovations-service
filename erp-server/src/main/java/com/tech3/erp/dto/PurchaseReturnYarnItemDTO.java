package com.tech3.erp.dto;

import com.tech3.erp.entity.PurchaseReturnYarnItem;

public class PurchaseReturnYarnItemDTO {
    private Long id;
    private String yarnId;
    private String yarnName;
    private String rejectedQty;
    private String returnQuantity;
    private String kgsPerBag;
    private String conesPerBag;
    private String conesWeight;
    private String lotNo;
    private String millNameId;
    private String warehouseId;
    private String inwardId;
    private String orderItemId;
    private Long purchaseInwardId;
    private Integer isReturn = 0;
    private String ratePerUnit;
    private String gstPercent;
    private String gstAmount;
    private String totalAmount;

    public PurchaseReturnYarnItemDTO() {}

    public PurchaseReturnYarnItemDTO(PurchaseReturnYarnItem e) {
        this.id = e.getId();
        this.yarnId = e.getYarnId() != null ? e.getYarnId().toString() : null;
        this.yarnName = e.getYarnName();
        this.rejectedQty = e.getRejectedQty();
        this.returnQuantity = e.getReturnQuantity();
        this.kgsPerBag = e.getKgsPerBag();
        this.conesPerBag = e.getConesPerBag() != null ? e.getConesPerBag().toPlainString() : null;
        this.conesWeight = e.getConesWeight() != null ? e.getConesWeight().toPlainString() : null;
        this.lotNo = e.getLotNo();
        this.millNameId = e.getMillNameId() != null ? e.getMillNameId().toString() : null;
        this.warehouseId = e.getWarehouseId() != null ? e.getWarehouseId().toString() : null;
        this.inwardId = e.getInwardId() != null ? e.getInwardId().toString() : null;
        this.orderItemId = e.getOrderItemId() != null ? e.getOrderItemId().toString() : null;
        this.purchaseInwardId = e.getPurchaseInwardId(); // New field mapping
        this.isReturn = e.getIsReturn();
        this.ratePerUnit = e.getRatePerUnit() != null ? e.getRatePerUnit().toPlainString() : null;
        this.gstPercent = null; // This will be set from frontend
        this.gstAmount = e.getGstAmount() != null ? e.getGstAmount().toPlainString() : null;
        this.totalAmount = e.getTotalAmount() != null ? e.getTotalAmount().toPlainString() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getYarnId() { return yarnId; }
    public void setYarnId(String yarnId) { this.yarnId = yarnId; }
    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }
    public String getRejectedQty() { return rejectedQty; }
    public void setRejectedQty(String rejectedQty) { this.rejectedQty = rejectedQty; }
    public String getReturnQuantity() { return returnQuantity; }
    public void setReturnQuantity(String returnQuantity) { this.returnQuantity = returnQuantity; }
    public String getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(String kgsPerBag) { this.kgsPerBag = kgsPerBag; }
    public String getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(String conesPerBag) { this.conesPerBag = conesPerBag; }
    public String getConesWeight() { return conesWeight; }
    public void setConesWeight(String conesWeight) { this.conesWeight = conesWeight; }
    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }
    public String getMillNameId() { return millNameId; }
    public void setMillNameId(String millNameId) { this.millNameId = millNameId; }
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
    public String getInwardId() { return inwardId; }
    public void setInwardId(String inwardId) { this.inwardId = inwardId; }
    public String getOrderItemId() { return orderItemId; }
    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }
    
    public Long getPurchaseInwardId() { return purchaseInwardId; }
    public void setPurchaseInwardId(Long purchaseInwardId) { this.purchaseInwardId = purchaseInwardId; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }
    
    public String getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(String ratePerUnit) { this.ratePerUnit = ratePerUnit; }
    
    public String getGstPercent() { return gstPercent; }
    public void setGstPercent(String gstPercent) { this.gstPercent = gstPercent; }
    
    public String getGstAmount() { return gstAmount; }
    public void setGstAmount(String gstAmount) { this.gstAmount = gstAmount; }
    
    public String getTotalAmount() { return totalAmount; }
    public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
}