package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderDTO {

    private Long id;
    private String poNo;
    private Long poTypeId;
    private LocalDate poDate;
    private Long vendorId;
    private Long taxId;
    private Boolean activeFlag;
    private Short isComplete;
    private Long userId;
        private Short isApproved;

    private Date approveDate;
    private String comments;
    
    private Long companyId;

    private Long fabricMasterId;

    private Long poCategoryId;

    private String note;

    @JsonProperty("purchaseOrderItemsDtl")
    private List<PurchaseOrderItemDTO> purchaseOrderItemsDtl;

    public PurchaseOrderDTO() {}

    public PurchaseOrderDTO(Long id, String poNo, Long poTypeId, LocalDate poDate, Long vendorId, Long taxId,
    							Long deliveryAddressId, Boolean activeFlag,
                            List<PurchaseOrderItemDTO> purchaseOrderItemsDtl) {
        this.id = id;
        this.poNo = poNo;
        this.poTypeId = poTypeId;
        this.poDate = poDate;
        this.vendorId = vendorId;
        this.taxId = taxId;
        this.activeFlag = activeFlag;
        this.purchaseOrderItemsDtl = purchaseOrderItemsDtl;
    }

    public PurchaseOrderDTO(PurchaseOrder po) {
        this.id = po.getId();
        this.poNo = po.getPoNo();
        this.poTypeId = po.getPoTypeId();
        this.poDate = po.getPoDate();
        this.vendorId = po.getVendorId();
        this.taxId = po.getTaxId();
        this.companyId = po.getCompanyId();
        this.fabricMasterId = po.getFabricMasterId();
        this.poCategoryId = po.getPoCategoryId();
        this.note = po.getNote();
        this.activeFlag = po.getActiveFlag();
        this.isComplete = po.getIsComplete();
        this.isApproved = po.getIsApproved();
        this.userId = po.getUserId();
        this.comments = po.getComments();
        if (po.getApproveDate() != null) {
            this.approveDate = Date.from(po.getApproveDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (po.getItems() != null) {
            this.purchaseOrderItemsDtl = po.getItems().stream()
                    .map(PurchaseOrderItemDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    
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
    
    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }

    public LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(LocalDate poDate) {
        this.poDate = poDate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
       public Short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<PurchaseOrderItemDTO> getPurchaseOrderItemsDtl() {
        return purchaseOrderItemsDtl;
    }

    public void setPurchaseOrderItemsDtl(List<PurchaseOrderItemDTO> purchaseOrderItemsDtl) {
        this.purchaseOrderItemsDtl = purchaseOrderItemsDtl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

   


