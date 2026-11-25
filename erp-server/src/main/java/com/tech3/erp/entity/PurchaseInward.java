// package com.tech3.erp.entity;

// import jakarta.persistence.*;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// import com.fasterxml.jackson.annotation.JsonManagedReference;

// @Entity
// @Table(name = "purchase_inward", schema = "masters")
// public class PurchaseInward {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "po_type_id")
//     private Long poTypeId;
    
//     @Column(name = "vendor_id")
//     private Long vendorId;
    
//     @Column(name = "inward_date", nullable = false)
//     private LocalDate inwardDate = LocalDate.now();

//     @Column(name = "remarks")
//     private String remarks;

//     @Column(name = "active_flag")
//     private Boolean activeFlag = true;
    
//     @Column(name = "po_id")
//     private Long poId;
    
//     @Column(name = "fabric_code")
//     private String fabricCode;

//     // New fields
//     @Column(name = "dc_date")
//     private LocalDate dcDate = LocalDate.now();

//     @Column(name = "dc_number")
//     private String dcNumber;

//     @Column(name = "inward_no")
//     private String inwardNo;

//     @Column(name = "company_id")
//     private Long companyId;

//     @OneToMany(mappedBy = "purchaseInward", cascade = CascadeType.ALL)
//     @JsonManagedReference
//     private List<PurchaseInwardItem> items;

//     // Getters and setters
//     public Long getId() { return id; }

//     public Long getPoTypeId() { return poTypeId; }
//     public void setPoTypeId(Long poTypeId) { this.poTypeId = poTypeId; }

//     public Long getVendorId() { return vendorId; }
//     public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

//     public LocalDate getInwardDate() { return inwardDate; }
//     public void setInwardDate(LocalDate inwardDate) { this.inwardDate = inwardDate; }

//     public String getRemarks() { return remarks; }
//     public void setRemarks(String remarks) { this.remarks = remarks; }

//     public Boolean getActiveFlag() { return activeFlag; }
//     public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

//     public Long getPurchaseOrderId() { return poId; }
//     public void setPurchaseOrderId(Long poId) { this.poId = poId; }

//     public String getFabricCode() { return fabricCode; }
//     public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

//     public LocalDate getDcDate() { return dcDate; }
//     public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

//     public String getDcNumber() { return dcNumber; }
//     public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

//     public List<PurchaseInwardItem> getItems() { return items; }
//     public void setItems(List<PurchaseInwardItem> items) {
//         this.items = items;
//         for (PurchaseInwardItem item : items) {
//             item.setPurchaseInward(this);
//         }
//     }

//     // @PrePersist
//     // public void prePersist() {
//     //     if (this.inwardNo == null || this.inwardNo.isEmpty()) {
//     //         this.inwardNo = generateInwardNo();
//     //     }
//     // }

//     // private String generateInwardNo() {
//     //     String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//     //     int randomNum = new Random().nextInt(9000) + 1000;
//     //     return "PI-" + datePart + "-" + randomNum;
//     // }

//     public String getInwardNo() { return inwardNo; }
//     public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

//     public Long getCompanyId() { return companyId; }
//     public void setCompanyId(Long companyId) { this.companyId = companyId; }
// }

package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "purchase_inward", schema = "masters")
public class PurchaseInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_type_id")
    private Long poTypeId;
    
    @Column(name = "vendor_id")
    private Long vendorId;
    
    // Support both LocalDate (new) and LocalDateTime (old) by having both fields
    // Main field will be LocalDate, with helper methods for backward compatibility
    @Column(name = "inward_date", nullable = false)
    private LocalDate inwardDate = LocalDate.now();
    
    // Transient field for LocalDateTime compatibility
    @Transient
    private LocalDateTime inwardDateTime;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;
    
    @Column(name = "po_id")
    private Long poId;
    
    @Column(name = "po_no")
    private String purchaseOrderNo;  // From old code
    
    @Column(name = "fabric_code")
    private String fabricCode;

    // New fields from new code
    @Column(name = "dc_date")
    private LocalDate dcDate = LocalDate.now();

    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "inward_no")
    private String inwardNo;

    @Column(name = "mill_inv_no")
    private String millInvNo;

    @Column(name = "mill_date")
    private LocalDate millDate;

    @Column(name = "icil_inv_no")
    private String icilInvNo;

    @Column(name = "icil_inv_date")
    private LocalDate icilInvDate;

    @Column(name = "company_id")
    private Long companyId;  // Already in old code, keeping it

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "yarn_mill_id")
    private Long yarnMillId;

    // Accounting fields
    @Column(name = "is_accounted")
    private Boolean isAccounted = false;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "purchase_account")
    private String purchaseAccount;

    @Column(name = "supplier_account")
    private String supplierAccount;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "accounting_remarks")
    private String accountingRemarks;

    @OneToMany(mappedBy = "purchaseInward", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseInwardItem> items;

    // ============ CONSTRUCTORS ============
    public PurchaseInward() {
        // Default constructor
    }

    // ============ GETTERS AND SETTERS ============
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPoTypeId() { return poTypeId; }
    public void setPoTypeId(Long poTypeId) { this.poTypeId = poTypeId; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    // Main date field - LocalDate (new code preference)
    public LocalDate getInwardDate() { return inwardDate; }
    public void setInwardDate(LocalDate inwardDate) { 
        this.inwardDate = inwardDate; 
        // Keep inwardDateTime synchronized
        if (inwardDate != null) {
            this.inwardDateTime = inwardDate.atStartOfDay();
        }
    }

    // For backward compatibility - LocalDateTime support
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
    
    // Alias method for old code compatibility
    public LocalDateTime getInwardDateAsDateTime() {
        return getInwardDateTime();
    }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getPurchaseOrderId() { return poId; }
    public void setPurchaseOrderId(Long poId) { this.poId = poId; }

    public String getPurchaseOrderNo() { return purchaseOrderNo; }
    public void setPurchaseOrderNo(String purchaseOrderNo) { this.purchaseOrderNo = purchaseOrderNo; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    // New fields getters and setters
    public LocalDate getDcDate() { return dcDate; }
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public String getDcNumber() { return dcNumber; }
    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

    public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

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

    public List<PurchaseInwardItem> getItems() { 
        return items != null ? items : new ArrayList<>();
    }
    
    public void setItems(List<PurchaseInwardItem> items) {
        this.items = items;
        if (items != null) {
            for (PurchaseInwardItem item : items) {
                item.setPurchaseInward(this);
            }
        }
    }

    // ============ HELPER METHODS ============
    
    /**
     * Helper method to initialize inward date from LocalDateTime (for old code compatibility)
     */
    public void setInwardDateFromDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            this.inwardDate = dateTime.toLocalDate();
            this.inwardDateTime = dateTime;
        }
    }
    
    /**
     * Pre-persist logic for generating inward number
     */
    @PrePersist
    public void prePersist() {
        // Generate inward number if not provided
        if (this.inwardNo == null || this.inwardNo.isEmpty()) {
            this.inwardNo = generateInwardNo();
        }
        
        // Ensure inwardDate is set
        if (this.inwardDate == null) {
            this.inwardDate = LocalDate.now();
        }
        
        // Ensure dcDate is set
        if (this.dcDate == null) {
            this.dcDate = LocalDate.now();
        }
        
        // Ensure active flag is set
        if (this.activeFlag == null) {
            this.activeFlag = true;
        }
    }
    
    private String generateInwardNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "PI-" + datePart + "-" + randomNum;
    }
    
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
    public Long getYarnMillId() { return yarnMillId; }
    public void setYarnMillId(Long yarnMillId) { this.yarnMillId = yarnMillId; }

    @Override
    public String toString() {
        return "PurchaseInward [id=" + id + 
               ", inwardNo=" + inwardNo + 
               ", millInvNo=" + millInvNo + 
               ", millDate=" + millDate + 
               ", icilInvNo=" + icilInvNo + 
               ", icilInvDate=" + icilInvDate + 
               ", vendorId=" + vendorId + 
               ", companyId=" + companyId + 
               ", inwardDate=" + inwardDate + 
               ", fabricCode=" + fabricCode + 
               ", yarnMillId=" + yarnMillId + "]";
    }
}