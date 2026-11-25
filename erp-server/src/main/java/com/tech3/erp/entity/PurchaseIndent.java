package com.tech3.erp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_indent", schema = "masters")
public class PurchaseIndent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indent_no", nullable = false, length = 100)
    private String indentNo;

    @Column(name = "indent_date", nullable = false)
    private LocalDate indentDate;

    @Column(nullable = false, length = 150)
    private String department;

    @Column(name = "requested_by", nullable = false, length = 150)
    private String requestedBy;

    @Column(name = "required_date", nullable = false)
    private LocalDate requiredDate;

    @Column(nullable = false, length = 40)
    private String priority = "Normal";

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false, length = 40)
    private String status = "Draft";

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approval_comments", columnDefinition = "TEXT")
    private String approvalComments;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "purchaseIndent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<PurchaseIndentItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addItem(PurchaseIndentItem item) {
        item.setPurchaseIndent(this);
        items.add(item);
    }

    public void replaceItems(List<PurchaseIndentItem> replacement) {
        items.clear();
        replacement.forEach(this::addItem);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIndentNo() { return indentNo; }
    public void setIndentNo(String indentNo) { this.indentNo = indentNo; }
    public LocalDate getIndentDate() { return indentDate; }
    public void setIndentDate(LocalDate indentDate) { this.indentDate = indentDate; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getRequestedBy() { return requestedBy; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public LocalDate getApproveDate() { return approveDate; }
    public void setApproveDate(LocalDate approveDate) { this.approveDate = approveDate; }
    public Long getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Long approvedBy) { this.approvedBy = approvedBy; }
    public String getApprovalComments() { return approvalComments; }
    public void setApprovalComments(String approvalComments) { this.approvalComments = approvalComments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<PurchaseIndentItem> getItems() { return items; }
}
