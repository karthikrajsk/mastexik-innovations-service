package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_items", schema = "masters")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "no_of_rolls")
    private Integer noOfRolls;

    @Column(name = "uom")
    private String uom;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "gst_percent")
    private BigDecimal gstPercent;

    @Column(name = "hsn_code")
    private BigDecimal hsnCode;

    @Column(name = "taxable_amount")
    private BigDecimal taxableAmount;

    @Column(name = "gst_amount")
    private BigDecimal gstAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "is_read_only_quantity")
    private Boolean isReadOnlyQuantity;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "is_invoice")
    private Integer isInvoice = 0;

    @Column(name = "is_scanned_packing")
    private Integer isScannedPacking = 0;

    @Column(name = "packing_list_no", length = 100)
    private String packingListNo;

    @Column(name = "yarn_challan_id")
    private Long yarnChallanId;

    @Column(name = "yarn_challan_no", length = 100)
    private String yarnChallanNo;

    @Column(name = "yarn_id")
    private Long yarnId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    
    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public Integer getNoOfRolls() { return noOfRolls; }
    public void setNoOfRolls(Integer noOfRolls) { this.noOfRolls = noOfRolls; }
    
    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }
    
    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getGstPercent() { return gstPercent; }
    public void setGstPercent(BigDecimal gstPercent) { this.gstPercent = gstPercent; }

    public BigDecimal getHsnCode() { return hsnCode; }
    public void setHsnCode(BigDecimal hsnCode) { this.hsnCode = hsnCode; }

    public BigDecimal getTaxableAmount() { return taxableAmount; }
    public void setTaxableAmount(BigDecimal taxableAmount) { this.taxableAmount = taxableAmount; }

    public BigDecimal getGstAmount() { return gstAmount; }
    public void setGstAmount(BigDecimal gstAmount) { this.gstAmount = gstAmount; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Boolean getIsReadOnlyQuantity() { return isReadOnlyQuantity; }
    public void setIsReadOnlyQuantity(Boolean isReadOnlyQuantity) { this.isReadOnlyQuantity = isReadOnlyQuantity; }
    
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Integer getIsInvoice() { return isInvoice; }
    public void setIsInvoice(Integer isInvoice) { this.isInvoice = isInvoice; }

    public Integer getIsScannedPacking() { return isScannedPacking; }
    public void setIsScannedPacking(Integer isScannedPacking) { this.isScannedPacking = isScannedPacking; }

    public String getPackingListNo() { return packingListNo; }
    public void setPackingListNo(String packingListNo) { this.packingListNo = packingListNo; }

    public Long getYarnChallanId() { return yarnChallanId; }
    public void setYarnChallanId(Long yarnChallanId) { this.yarnChallanId = yarnChallanId; }

    public String getYarnChallanNo() { return yarnChallanNo; }
    public void setYarnChallanNo(String yarnChallanNo) { this.yarnChallanNo = yarnChallanNo; }

    public Long getYarnId() { return yarnId; }
    public void setYarnId(Long yarnId) { this.yarnId = yarnId; }
}