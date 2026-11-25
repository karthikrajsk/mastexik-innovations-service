package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "purchase_orders", schema = "masters")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String poNo;
    private LocalDate poDate;
    private Boolean activeFlag;
    private Long vendorId;
    private Long poTypeId;
    private Long taxId;

     @Column(name = "is_approved")
    private Short isApproved = (short) 0;
    
    @Column(name = "is_complete")
    private Short isComplete;
    
    @Column(name = "approve_date")
    private LocalDate approveDate;
    
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "po_category_id")
    private Long poCategoryId;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> items = new ArrayList<>();



    public void addItem(PurchaseOrderItem item) {
        item.setPurchaseOrder(this); // 👈 This is the key
        this.items.add(item);
    }

    // Getters and Setters
    // (Add for all fields including poDate, activeFlag, vendorId etc.)
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public Long getPoCategoryId() {
        return poCategoryId;
    }

    public void setPoCategoryId(Long poCategoryId) {
        this.poCategoryId = poCategoryId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(LocalDate poDate) {
        this.poDate = poDate;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<PurchaseOrderItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseOrderItem> items) {
        this.items = items;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }
public Short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
