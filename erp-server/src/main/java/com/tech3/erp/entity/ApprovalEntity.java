package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "approval_transaction", schema = "masters")
public class ApprovalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_no", nullable = false, length = 255)
    private String entryNo;

    @Column(name = "screen_name", nullable = false, length = 255)
    private String screenName;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "screen_id", nullable = false)
    private Long screenId;

    @Column(name = "data_id", nullable = false)
    private Long dataId;

    @Column(name = "sales_order_no", length = 255)
    private String salesOrderNo;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "is_approved", nullable = false)
    private Short isApproved = (short) 0;

    @Column(name = "approved_date")
    private LocalDate approvedDate;

    @Column(name = "approved_comments", columnDefinition = "TEXT")
    private String approvedComments;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
    
    @Column(name = "action_comments", columnDefinition = "TEXT")
    private String actionComments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public ApprovalEntity() {
    }

    public ApprovalEntity(String entryNo, String screenName, Long screenId, Long dataId, LocalDate entryDate, String salesOrderNo, String comments, Long companyId, Long createdBy) {
        this.entryNo = entryNo;
        this.screenName = screenName;
        this.screenId = screenId;
        this.dataId = dataId;
        this.entryDate = entryDate;
        this.salesOrderNo = salesOrderNo;
        this.comments = comments;
        this.companyId = companyId;
        this.createdBy = createdBy;
        this.isApproved = (short) 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedComments() {
        return approvedComments;
    }

    public void setApprovedComments(String approvedComments) {
        this.approvedComments = approvedComments;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getActionComments() {
        return actionComments;
    }

    public void setActionComments(String actionComments) {
        this.actionComments = actionComments;
    }
}
