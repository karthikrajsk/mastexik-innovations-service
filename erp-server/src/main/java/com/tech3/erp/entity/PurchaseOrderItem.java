package com.tech3.erp.entity;

import com.tech3.erp.dto.PurchaseOrderItemDTO;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_order_item", schema = "masters")
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_category_id")
    private Long productCategoryId = 1L; 
    
    private Long poTypeId;
    private Long yarnId;
    private Long yarnTypeId;
    private Long fabricTypeId;
    private Long fabricId;
    private Long fabricCategoryId;
    private Long fabricMasterId;
    private Long millNameId;
    private Long itemId;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal price;
    private BigDecimal netAmount;
    @Column(name = "delivery_date", nullable = true)
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
    @Column(name = "sales_order_no")
    private String salesOrderNo;
    private String gstPercent;
    private String productType;


    @Column(name = "performa_no")
    private String performaNo;

    @Column(name = "discount_type")
    private String discountType = "Percentage";

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "net_price_per_unit")
    private BigDecimal netPricePerUnit;

    @Column(name = "packing_and_forwarding")
    private BigDecimal packingAndForwarding;

    @Column(name = "others")
    private BigDecimal others;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    public PurchaseOrderItem() {}

    public PurchaseOrderItem(PurchaseOrderItemDTO dto) {
        this.productCategoryId = dto.getProductCategoryId(); // 👈 ADD THIS

    	this.poTypeId = dto.getPoTypeId();
        this.yarnId = dto.getYarnId();
        this.yarnTypeId = dto.getYarnTypeId();
        this.fabricTypeId = dto.getFabricTypeId();
        this.fabricId = dto.getFabricId();
        this.fabricCategoryId = dto.getFabricCategoryId();
        this.fabricMasterId = dto.getFabricMasterId();
        this.millNameId = dto.getMillNameId();
        this.itemId = dto.getItemId();
        this.quantity = dto.getQuantity();
        this.unit = dto.getUnit();
        this.price = dto.getPrice();
        this.netAmount = dto.getNetAmount();
        this.deliveryDate = dto.getDeliveryDate();
        this.remarks = dto.getRemarks();
        this.activeFlag = dto.getActiveFlag();
        this.cgstPercent = dto.getCgstPercent();
        this.sgstPercent = dto.getSgstPercent();
        this.igstPercent = dto.getIgstPercent();
        this.gstAmount = dto.getGstAmount();
        this.finalAmount = dto.getFinalAmount();
        this.fabricCode = dto.getFabricCode();
        this.quality = dto.getQuality();
        this.salesOrderId = dto.getSalesOrderId();
        this.salesOrderNo = dto.getSalesOrderNo();
        this.gstPercent = dto.getGstPercent();
        this.productType = dto.getProductType();
        this.performaNo = dto.getPerformaNo();
        this.discountType = dto.getDiscountType() != null ? dto.getDiscountType() : "Percentage";
        this.discountValue = dto.getDiscountValue();
        this.discountAmount = dto.getDiscountAmount();
        this.netPricePerUnit = dto.getNetPricePerUnit();
        this.packingAndForwarding = dto.getPackingAndForwarding();
        this.others = dto.getOthers();
        this.note = dto.getNote();
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

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
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

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getPerformaNo() {
        return performaNo;
    }

    public void setPerformaNo(String performaNo) {
        this.performaNo = performaNo;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getNetPricePerUnit() {
        return netPricePerUnit;
    }

    public void setNetPricePerUnit(BigDecimal netPricePerUnit) {
        this.netPricePerUnit = netPricePerUnit;
    }

    public BigDecimal getPackingAndForwarding() {
        return packingAndForwarding;
    }

    public void setPackingAndForwarding(BigDecimal packingAndForwarding) {
        this.packingAndForwarding = packingAndForwarding;
    }

    public BigDecimal getOthers() {
        return others;
    }

    public void setOthers(BigDecimal others) {
        this.others = others;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

