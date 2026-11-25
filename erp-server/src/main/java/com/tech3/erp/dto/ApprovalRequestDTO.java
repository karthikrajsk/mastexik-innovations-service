package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApprovalRequestDTO {
    private String approvedComments;
    private LocalDate approvedDate;

    public ApprovalRequestDTO() {
    }

    public ApprovalRequestDTO(String approvedComments, LocalDate approvedDate) {
        this.approvedComments = approvedComments;
        this.approvedDate = approvedDate;
    }

    public String getApprovedComments() {
        return approvedComments;
    }

    public void setApprovedComments(String approvedComments) {
        this.approvedComments = approvedComments;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }
}
