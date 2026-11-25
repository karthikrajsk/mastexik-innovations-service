package com.tech3.erp.dto;

import com.tech3.erp.entity.PurchaseReturnFabricItem;

public class PurchaseReturnFabricItemDTO {
    private Long id;
    private String fabricMasterId;
    private String fabricCode;
    private String quality;
    private String returnQuantity;
    private String pieceNo;
    private String pieceMeter;
    private String netWeight;
    private String warehouseId;
    private String isPacked;
    private Long purchaseInwardId;
    private Integer isReturn = 0;

    public PurchaseReturnFabricItemDTO() {}

    public PurchaseReturnFabricItemDTO(PurchaseReturnFabricItem e) {
        this.id = e.getId();
        this.fabricMasterId = e.getFabricMasterId() != null ? e.getFabricMasterId().toString() : null;
        this.fabricCode = e.getFabricCode();
        this.quality = e.getQuality();
        this.returnQuantity = e.getReturnQuantity();
        this.pieceNo = e.getPieceNo();
        this.pieceMeter = e.getPieceMeter();
        this.netWeight = e.getNetWeight();
        this.warehouseId = e.getWarehouseId() != null ? e.getWarehouseId().toString() : null;
        this.isPacked = e.getIsPacked();
        this.purchaseInwardId = e.getPurchaseInwardId(); // New field mapping
        this.isReturn = e.getIsReturn();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(String fabricMasterId) { this.fabricMasterId = fabricMasterId; }
    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }
    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }
    public String getReturnQuantity() { return returnQuantity; }
    public void setReturnQuantity(String returnQuantity) { this.returnQuantity = returnQuantity; }
    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }
    public String getPieceMeter() { return pieceMeter; }
    public void setPieceMeter(String pieceMeter) { this.pieceMeter = pieceMeter; }
    public String getNetWeight() { return netWeight; }
    public void setNetWeight(String netWeight) { this.netWeight = netWeight; }
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
    public String getIsPacked() { return isPacked; }
    public void setIsPacked(String isPacked) { this.isPacked = isPacked; }
    
    public Long getPurchaseInwardId() { return purchaseInwardId; }
    public void setPurchaseInwardId(Long purchaseInwardId) { this.purchaseInwardId = purchaseInwardId; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }
}