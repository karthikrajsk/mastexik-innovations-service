package com.tech3.erp.dto;

import com.tech3.erp.entity.PurchaseIndent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseIndentDTO {

    private Long id;
    private String indentNo;

    @NotNull(message = "Indent date is required")
    private LocalDate indentDate;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Requested by is required")
    private String requestedBy;

    @NotNull(message = "Required date is required")
    private LocalDate requiredDate;

    private String priority = "Normal";
    private String remarks;
    private String status;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    private Boolean submitForApproval = false;
    private LocalDate approveDate;
    private Long approvedBy;
    private String approvalComments;
    private Short isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Valid
    private List<PurchaseIndentItemDTO> items;

    public PurchaseIndentDTO() {
    }

    public PurchaseIndentDTO(PurchaseIndent indent) {
        this.id = indent.getId();
        this.indentNo = indent.getIndentNo();
        this.indentDate = indent.getIndentDate();
        this.department = indent.getDepartment();
        this.requestedBy = indent.getRequestedBy();
        this.requiredDate = indent.getRequiredDate();
        this.priority = indent.getPriority();
        this.remarks = indent.getRemarks();
        this.status = indent.getStatus();
        this.companyId = indent.getCompanyId();
        this.approveDate = indent.getApproveDate();
        this.approvedBy = indent.getApprovedBy();
        this.approvalComments = indent.getApprovalComments();
        this.isApproved = "Approved".equalsIgnoreCase(indent.getStatus()) ? (short) 1 : (short) 0;
        this.createdAt = indent.getCreatedAt();
        this.updatedAt = indent.getUpdatedAt();
        this.items = indent.getItems() != null ? indent.getItems().stream().map(PurchaseIndentItemDTO::new).toList() : List.of();
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
    public Boolean getSubmitForApproval() { return submitForApproval; }
    public void setSubmitForApproval(Boolean submitForApproval) { this.submitForApproval = submitForApproval; }
    public LocalDate getApproveDate() { return approveDate; }
    public void setApproveDate(LocalDate approveDate) { this.approveDate = approveDate; }
    public Long getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Long approvedBy) { this.approvedBy = approvedBy; }
    public String getApprovalComments() { return approvalComments; }
    public void setApprovalComments(String approvalComments) { this.approvalComments = approvalComments; }
    public Short getIsApproved() { return isApproved; }
    public void setIsApproved(Short isApproved) { this.isApproved = isApproved; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<PurchaseIndentItemDTO> getItems() { return items; }
    public void setItems(List<PurchaseIndentItemDTO> items) { this.items = items; }
}
