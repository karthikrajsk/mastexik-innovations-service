package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class InvoiceItemDTO {
    private Long id;
    private Long invoiceId;
    private Long fabricMasterId;
    private String productName;
    private String productDescription;
    private BigDecimal quantity;
    private Integer noOfRolls;
    private String uom;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal gstPercent;
    private BigDecimal hsnCode;
    private BigDecimal taxableAmount;
    private BigDecimal gstAmount;
    private BigDecimal totalAmount;
    private Boolean isReadOnlyQuantity;
    private Integer isInvoice;
    private Integer isScannedPacking;
    private String packingListNo;
    private Long yarnChallanId;
    private String yarnChallanNo;
    private Long yarnId;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Boolean getIsReadOnlyQuantity() { return isReadOnlyQuantity; }
    public void setIsReadOnlyQuantity(Boolean isReadOnlyQuantity) { this.isReadOnlyQuantity = isReadOnlyQuantity; }

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
