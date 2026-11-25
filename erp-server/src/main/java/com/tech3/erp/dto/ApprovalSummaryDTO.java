package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApprovalSummaryDTO {
    private String salesOrderNo;
    private Long totalApprovals;
    private Long pending;
    private Long approved;

    public ApprovalSummaryDTO() {
    }

    public ApprovalSummaryDTO(String salesOrderNo, Long totalApprovals, Long pending, Long approved) {
        this.salesOrderNo = salesOrderNo;
        this.totalApprovals = totalApprovals;
        this.pending = pending;
        this.approved = approved;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Long getTotalApprovals() {
        return totalApprovals;
    }

    public void setTotalApprovals(Long totalApprovals) {
        this.totalApprovals = totalApprovals;
    }

    public Long getPending() {
        return pending;
    }

    public void setPending(Long pending) {
        this.pending = pending;
    }

    public Long getApproved() {
        return approved;
    }

    public void setApproved(Long approved) {
        this.approved = approved;
    }
}
