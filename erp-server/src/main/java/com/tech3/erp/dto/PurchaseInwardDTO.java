// package com.tech3.erp.dto;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.tech3.erp.entity.PurchaseInward;
// import com.tech3.erp.entity.PurchaseInwardItem;

// import java.time.LocalDate;
// import java.util.Collection;
// import java.util.List;
// import java.util.stream.Collectors;

// public class PurchaseInwardDTO {

//     private Long id;
//     private Long poTypeId;
//     private Long vendorId;
//     private Long poId;
//     private LocalDate inwardDate;
//     private String remarks;
//     private Boolean activeFlag;
//     private String fabricCode;

//     // New fields
//     private LocalDate dcDate;
//     private String dcNumber;
//     private String purchaseInwardNo;
//     private Long companyId;

//     @JsonProperty("purchaseInwardItemsDtl")
//     private List<PurchaseInwardItemDTO> purchaseInwardItemsDtl;

//     public PurchaseInwardDTO() {}

//     public PurchaseInwardDTO(PurchaseInward inward) {
//         this.id = inward.getId();
//         this.poTypeId = inward.getPoTypeId();
//         this.vendorId = inward.getVendorId();
//         this.poId = inward.getPurchaseOrderId();
//         this.inwardDate = inward.getInwardDate();
//         this.remarks = inward.getRemarks();
//         this.activeFlag = inward.getActiveFlag();
//         this.fabricCode = inward.getFabricCode();
//         this.dcDate = inward.getDcDate();
//         this.dcNumber = inward.getDcNumber();
//         this.purchaseInwardNo = inward.getInwardNo();
//         this.companyId = inward.getCompanyId();

//         if (inward.getItems() != null) {
//             this.purchaseInwardItemsDtl = inward.getItems().stream()
//                 .map(PurchaseInwardItemDTO::new)
//                 .collect(Collectors.toList());
//         }
//     }

//     // Getters and setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public Long getPoTypeId() { return poTypeId; }
//     public void setPoTypeId(Long poTypeId) { this.poTypeId = poTypeId; }

//     public Long getVendorId() { return vendorId; }
//     public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

//     public Long getPurchaseOrderId() { return poId; }
//     public void setPurchaseOrderId(Long poId) { this.poId = poId; }

//     public LocalDate getInwardDate() { return inwardDate; }
//     public void setInwardDate(LocalDate inwardDate) { this.inwardDate = inwardDate; }

//     public String getRemarks() { return remarks; }
//     public void setRemarks(String remarks) { this.remarks = remarks; }

//     public Boolean getActiveFlag() { return activeFlag; }
//     public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

//     public String getFabricCode() { return fabricCode; }
//     public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

//     public LocalDate getDcDate() { return dcDate; }
//     public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

//     public String getDcNumber() { return dcNumber; }
//     public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

//     public String getPurchaseInwardNo() { return purchaseInwardNo; }
//     public void setPurchaseInwardNo(String purchaseInwardNo) { this.purchaseInwardNo = purchaseInwardNo; }

//     public Long getCompanyId() { return companyId; }
//     public void setCompanyId(Long companyId) { this.companyId = companyId; }

//     public List<PurchaseInwardItemDTO> getPurchaseInwardItemsDtl() { return purchaseInwardItemsDtl; }
//     public void setPurchaseInwardItemsDtl(List<PurchaseInwardItemDTO> purchaseInwardItemsDtl) {
//         this.purchaseInwardItemsDtl = purchaseInwardItemsDtl;
//     }
// }
package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.PurchaseInward;
import com.tech3.erp.entity.PurchaseInwardItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseInwardDTO {

    private Long id;
    private Long poTypeId;
    private Long vendorId;
    private Long poId;
    private String purchaseOrderNo; 
    private String financialYear; // From old code
    
    // Support both LocalDate (new) and LocalDateTime (old)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inwardDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime inwardDateTime;  // For backward compatibility
    
    private String remarks;
    private Boolean activeFlag;
    private String fabricCode;
    
    // New fields from new code
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dcDate;
    
    private String dcNumber;
    private String purchaseInwardNo;
    
    private String millInvNo;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate millDate;
    
    private String icilInvNo;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate icilInvDate;

    private Long companyId;
    private Long yarnMillId;

    // Accounting fields
    private Boolean isAccounted;
    private String invoiceNumber;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;
    
    private String purchaseAccount;
    private String supplierAccount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String accountingRemarks;
     

    @JsonProperty("purchaseInwardItemsDtl")
    private List<PurchaseInwardItemDTO> purchaseInwardItemsDtl;

    // ============ CONSTRUCTORS ============
    
    public PurchaseInwardDTO() {}

    public PurchaseInwardDTO(PurchaseInward inward) {
        this.id = inward.getId();
        this.poTypeId = inward.getPoTypeId();
        this.vendorId = inward.getVendorId();
        this.poId = inward.getPurchaseOrderId();
        this.purchaseOrderNo = inward.getPurchaseOrderNo();
        
        // Handle date conversion - prefer LocalDate for new code
        if (inward.getInwardDate() != null) {
            this.inwardDate = inward.getInwardDate();
            this.inwardDateTime = inward.getInwardDate().atStartOfDay();
        }
        
        this.remarks = inward.getRemarks();
        this.activeFlag = inward.getActiveFlag();
        this.fabricCode = inward.getFabricCode();
        
        // New fields
        this.dcDate = inward.getDcDate();
        this.dcNumber = inward.getDcNumber();
        this.purchaseInwardNo = inward.getInwardNo();
        this.millInvNo = inward.getMillInvNo();
        this.millDate = inward.getMillDate();
        this.icilInvNo = inward.getIcilInvNo();
        this.icilInvDate = inward.getIcilInvDate();
        this.companyId = inward.getCompanyId();
        this.yarnMillId = inward.getYarnMillId();
        this.financialYear = inward.getFinancialYear();

        // Accounting fields
        this.isAccounted = inward.getIsAccounted();
        this.invoiceNumber = inward.getInvoiceNumber();
        this.invoiceDate = inward.getInvoiceDate();
        this.purchaseAccount = inward.getPurchaseAccount();
        this.supplierAccount = inward.getSupplierAccount();
        this.taxAmount = inward.getTaxAmount();
        this.totalAmount = inward.getTotalAmount();
        this.accountingRemarks = inward.getAccountingRemarks();

        // Map items
        if (inward.getItems() != null) {
            this.purchaseInwardItemsDtl = inward.getItems().stream()
                .map(PurchaseInwardItemDTO::new)
                .collect(Collectors.toList());
        }
    }

    // ============ GETTERS AND SETTERS ============
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPoTypeId() { return poTypeId; }
    public void setPoTypeId(Long poTypeId) { this.poTypeId = poTypeId; }
    
    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }
    
    public Long getPurchaseOrderId() { return poId; }
    public void setPurchaseOrderId(Long poId) { this.poId = poId; }

    public String getPurchaseOrderNo() { return purchaseOrderNo; }
    public void setPurchaseOrderNo(String purchaseOrderNo) { this.purchaseOrderNo = purchaseOrderNo; }

    // Date handling with backward compatibility
    public LocalDate getInwardDate() { 
        if (inwardDate == null && inwardDateTime != null) {
            return inwardDateTime.toLocalDate();
        }
        return inwardDate; 
    }
    
    public void setInwardDate(LocalDate inwardDate) { 
        this.inwardDate = inwardDate; 
        if (inwardDate != null) {
            this.inwardDateTime = inwardDate.atStartOfDay();
        }
    }

    public LocalDateTime getInwardDateTime() {
        if (inwardDateTime == null && inwardDate != null) {
            return inwardDate.atStartOfDay();
        }
        return inwardDateTime;
    }
    
    public void setInwardDateTime(LocalDateTime inwardDateTime) {
        this.inwardDateTime = inwardDateTime;
        if (inwardDateTime != null) {
            this.inwardDate = inwardDateTime.toLocalDate();
        }
    }
    
    // Alias method for backward compatibility
    public LocalDateTime getInwardDateAsDateTime() {
        return getInwardDateTime();
    }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
    
    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    // New fields
    public LocalDate getDcDate() { return dcDate; }
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public String getDcNumber() { return dcNumber; }
    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

    public String getPurchaseInwardNo() { return purchaseInwardNo; }
    public void setPurchaseInwardNo(String purchaseInwardNo) { this.purchaseInwardNo = purchaseInwardNo; }

    public String getMillInvNo() { return millInvNo; }
    public void setMillInvNo(String millInvNo) { this.millInvNo = millInvNo; }

    public LocalDate getMillDate() { return millDate; }
    public void setMillDate(LocalDate millDate) { this.millDate = millDate; }

    public String getIcilInvNo() { return icilInvNo; }
    public void setIcilInvNo(String icilInvNo) { this.icilInvNo = icilInvNo; }

    public LocalDate getIcilInvDate() { return icilInvDate; }
    public void setIcilInvDate(LocalDate icilInvDate) { this.icilInvDate = icilInvDate; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getFinancialYear() { return financialYear; }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }

    public List<PurchaseInwardItemDTO> getPurchaseInwardItemsDtl() { 
        return purchaseInwardItemsDtl != null ? purchaseInwardItemsDtl : new java.util.ArrayList<>();
    }
    
    public void setPurchaseInwardItemsDtl(List<PurchaseInwardItemDTO> purchaseInwardItemsDtl) {
        this.purchaseInwardItemsDtl = purchaseInwardItemsDtl;
    }

    // ============ HELPER METHODS ============
    
    /**
     * Convenience method to check if this is a yarn purchase
     */
    public boolean isYarnPurchase() {
        return poTypeId != null && poTypeId == 1L; // PO_TYPE_YARN = 1L
    }
    
    /**
     * Convenience method to check if this is a fabric purchase
     */
    public boolean isFabricPurchase() {
        return poTypeId != null && (poTypeId == 2L || poTypeId == 3L); // Woven or Knitted Fabric
    }
    
    /**
     * Get formatted display string
     */
    @Override
    public String toString() {
        return "PurchaseInwardDTO [id=" + id + 
               ", purchaseInwardNo=" + purchaseInwardNo + 
               ", vendorId=" + vendorId + 
               ", companyId=" + companyId + 
               ", inwardDate=" + inwardDate + 
               ", fabricCode=" + fabricCode + "]";
    }
    
    /**
     * Validation method
     */
    public boolean isValid() {
        return vendorId != null && companyId != null && poTypeId != null;
    }

    public Long getYarnMillId() { return yarnMillId; }
    public void setYarnMillId(Long yarnMillId) { this.yarnMillId = yarnMillId; }

    public Boolean getIsAccounted() { return isAccounted != null ? isAccounted : false; }
    public void setIsAccounted(Boolean isAccounted) { this.isAccounted = isAccounted; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getPurchaseAccount() { return purchaseAccount; }
    public void setPurchaseAccount(String purchaseAccount) { this.purchaseAccount = purchaseAccount; }

    public String getSupplierAccount() { return supplierAccount; }
    public void setSupplierAccount(String supplierAccount) { this.supplierAccount = supplierAccount; }

    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getAccountingRemarks() { return accountingRemarks; }
    public void setAccountingRemarks(String accountingRemarks) { this.accountingRemarks = accountingRemarks; }
    
    /**
     * Method to convert to entity (for manual conversions if needed)
     */
    public PurchaseInward toEntity() {
        PurchaseInward entity = new PurchaseInward();
        entity.setId(this.id);
        entity.setPoTypeId(this.poTypeId);
        entity.setVendorId(this.vendorId);
        entity.setPurchaseOrderId(this.poId);
        entity.setPurchaseOrderNo(this.purchaseOrderNo);
        entity.setRemarks(this.remarks);
        entity.setActiveFlag(this.activeFlag);
        entity.setFabricCode(this.fabricCode);
        entity.setCompanyId(this.companyId);
        entity.setYarnMillId(this.yarnMillId);
        
        // Handle dates
        if (this.inwardDate != null) {
            entity.setInwardDate(this.inwardDate);
        } else if (this.inwardDateTime != null) {
            entity.setInwardDateFromDateTime(this.inwardDateTime);
        } else {
            entity.setInwardDate(LocalDate.now());
        }
        
        // New fields
        entity.setDcDate(this.dcDate != null ? this.dcDate : LocalDate.now());
        entity.setDcNumber(this.dcNumber);
        entity.setInwardNo(this.purchaseInwardNo);
        entity.setMillInvNo(this.millInvNo);
        entity.setMillDate(this.millDate);
        entity.setIcilInvNo(this.icilInvNo);
        entity.setIcilInvDate(this.icilInvDate);

        // Accounting fields
        entity.setIsAccounted(this.isAccounted != null ? this.isAccounted : false);
        entity.setInvoiceNumber(this.invoiceNumber);
        entity.setInvoiceDate(this.invoiceDate);
        entity.setPurchaseAccount(this.purchaseAccount);
        entity.setSupplierAccount(this.supplierAccount);
        entity.setTaxAmount(this.taxAmount);
        entity.setTotalAmount(this.totalAmount);
        entity.setAccountingRemarks(this.accountingRemarks);
        
        return entity;
    }
}