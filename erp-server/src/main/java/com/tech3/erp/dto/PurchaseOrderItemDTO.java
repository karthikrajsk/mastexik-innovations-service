package com.tech3.erp.dto;

import com.tech3.erp.entity.PurchaseOrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseOrderItemDTO {

    private Long id;
    private Long productCategoryId = 1L;
    private Long poTypeId;
    private Long yarnId;
    private Long yarnTypeId;
    private Long fabricTypeId;
    private Long fabricId;
    private Long fabricCategoryId;
    private Long fabricMasterId; // ADD THIS
    private Long millNameId;
    private String millName;
    private Long itemId;
    private String itemName;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal price;
    private BigDecimal netAmount;
    private LocalDateTime deliveryDate;
    private String remarks;
    private Boolean activeFlag;
    
    private BigDecimal cgstPercent;
    private BigDecimal sgstPercent;
    private BigDecimal igstPercent;
    private BigDecimal gstAmount;
    private BigDecimal finalAmount;
    private String fabricCode;
    private String quality;
    private Long salesOrderId;
    private String salesOrderNo;
    private String gstPercent;
    private String productType;
    private BigDecimal receivedQuantity;
    private String performaNo;
    private String discountType = "Percentage";
    private BigDecimal discountValue;
    private BigDecimal discountAmount;
    private BigDecimal netPricePerUnit;
    private BigDecimal packingAndForwarding;
    private BigDecimal others;
    private String note;

    public PurchaseOrderItemDTO() {}

    public PurchaseOrderItemDTO(Long id, Long productCategoryId,  Long fabricMasterId, Long poTypeId, Long yarnId, Long yarnTypeId, Long fabricTypeId, Long fabricId, Long fabricCategoryId, 
    							BigDecimal quantity, String unit,
                                BigDecimal price, BigDecimal netAmount, LocalDateTime deliveryDate,
                                String remarks, Boolean activeFlag,
                                BigDecimal cgstPercent, BigDecimal sgstPercent, BigDecimal igstPercent,
                                BigDecimal gstAmount, BigDecimal finalAmount, String fabricCode, 
                                String quality, Long salesOrderId, String salesOrderNo, String gstPercent, String productType) {
        this.id = id;
        this.productCategoryId = productCategoryId;
        this.poTypeId = poTypeId;
        this.yarnId = yarnId;
        this.yarnTypeId = yarnTypeId;
        this.fabricTypeId = fabricTypeId;
        this.fabricMasterId = fabricMasterId; // INITIALIZE
        this.fabricId = fabricId;
        this.fabricCategoryId = fabricCategoryId;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.netAmount = netAmount;
        this.deliveryDate = deliveryDate;
        this.remarks = remarks;
        this.activeFlag = activeFlag;
        this.cgstPercent = cgstPercent;
        this.sgstPercent = sgstPercent;
        this.igstPercent = igstPercent;
        this.gstAmount = gstAmount;
        this.finalAmount = finalAmount;
        this.fabricCode = fabricCode;
        this.quality = quality;
        this.salesOrderId = salesOrderId;
        this.salesOrderNo = salesOrderNo;
        this.gstPercent = gstPercent;
        this.productType = productType;
    }

    public PurchaseOrderItemDTO(PurchaseOrderItem entity) {
        this.id = entity.getId();
        this.productCategoryId = entity.getProductCategoryId();
        this.poTypeId = entity.getPoTypeId();
        this.yarnId = entity.getYarnId();
        this.yarnTypeId = entity.getYarnTypeId();
        this.fabricTypeId = entity.getFabricTypeId();
        this.fabricId = entity.getFabricId();
        this.fabricCategoryId = entity.getFabricCategoryId();
        this.fabricMasterId = entity.getFabricMasterId();
        this.millNameId = entity.getMillNameId();
        this.itemId = entity.getItemId();
        this.quantity = entity.getQuantity();
        this.unit = entity.getUnit();
        this.price = entity.getPrice();
        this.netAmount = entity.getNetAmount();
        this.deliveryDate = entity.getDeliveryDate();
        this.remarks = entity.getRemarks();
        this.activeFlag = entity.getActiveFlag();
        this.cgstPercent = entity.getCgstPercent();
        this.sgstPercent = entity.getSgstPercent();
        this.igstPercent = entity.getIgstPercent();
        this.gstAmount = entity.getGstAmount();
        this.finalAmount = entity.getFinalAmount();
        this.fabricCode = entity.getFabricCode();
        this.quality = entity.getQuality();
        this.salesOrderId = entity.getSalesOrderId();
        this.salesOrderNo = entity.getSalesOrderNo();
        this.gstPercent = entity.getGstPercent();
        this.productType = entity.getProductType();
        this.performaNo = entity.getPerformaNo();
        this.discountType = entity.getDiscountType() != null ? entity.getDiscountType() : "Percentage";
        this.discountValue = entity.getDiscountValue();
        this.discountAmount = entity.getDiscountAmount();
        this.netPricePerUnit = entity.getNetPricePerUnit();
        this.packingAndForwarding = entity.getPackingAndForwarding();
        this.others = entity.getOthers();
        this.note = entity.getNote();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }
    
    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Long getYarnTypeId() {
        return yarnTypeId;
    }

    public void setYarnTypeId(Long yarnTypeId) {
        this.yarnTypeId = yarnTypeId;
    }
    
    public Long getFabricTypeId() {
        return fabricTypeId;
    }

    public void setFabricTypeId(Long fabricTypeId) {
        this.fabricTypeId = fabricTypeId;
    }
    
    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }
    
    public Long getFabricCategoryId() {
        return fabricCategoryId;
    }

    public void setFabricCategoryId(Long fabricCategoryId) {
        this.fabricCategoryId = fabricCategoryId;
    }
    
    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public Long getMillNameId() {
        return millNameId;
    }

    public void setMillNameId(Long millNameId) {
        this.millNameId = millNameId;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public BigDecimal getCgstPercent() {
        return cgstPercent;
    }

    public void setCgstPercent(BigDecimal cgstPercent) {
        this.cgstPercent = cgstPercent;
    }

    public BigDecimal getSgstPercent() {
        return sgstPercent;
    }

    public void setSgstPercent(BigDecimal sgstPercent) {
        this.sgstPercent = sgstPercent;
    }

    public BigDecimal getIgstPercent() {
        return igstPercent;
    }

    public void setIgstPercent(BigDecimal igstPercent) {
        this.igstPercent = igstPercent;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(String gstPercent) {
        this.gstPercent = gstPercent;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    private String yarnName;
    public String getYarnName() {
         return yarnName;
     }

     public void setYarnName(String yarnName) {
         this.yarnName = yarnName;
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
    
    public BigDecimal getReceivedQuantity() { return receivedQuantity; }
    public void setReceivedQuantity(BigDecimal receivedQuantity) { this.receivedQuantity = receivedQuantity; }

    public String getPerformaNo() { return performaNo; }
    public void setPerformaNo(String performaNo) { this.performaNo = performaNo; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }

    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

    public BigDecimal getNetPricePerUnit() { return netPricePerUnit; }
    public void setNetPricePerUnit(BigDecimal netPricePerUnit) { this.netPricePerUnit = netPricePerUnit; }

    public BigDecimal getPackingAndForwarding() { return packingAndForwarding; }
    public void setPackingAndForwarding(BigDecimal packingAndForwarding) { this.packingAndForwarding = packingAndForwarding; }

    public BigDecimal getOthers() { return others; }
    public void setOthers(BigDecimal others) { this.others = others; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
