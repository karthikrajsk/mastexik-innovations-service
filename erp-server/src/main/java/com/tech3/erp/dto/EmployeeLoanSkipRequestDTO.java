package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeeLoanSkipRequest;
import java.time.LocalDate;

public class EmployeeLoanSkipRequestDTO {
    private Long id;
    private Long loanId;
    private String skipMonth;
    private String reason;
    private String status;
    private LocalDate requestedDate;
    private LocalDate actionedDate;

    public EmployeeLoanSkipRequestDTO() {}

    public EmployeeLoanSkipRequestDTO(EmployeeLoanSkipRequest entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.loanId = entity.getEmployeeLoan() != null ? entity.getEmployeeLoan().getId() : null;
            this.skipMonth = entity.getSkipMonth();
            this.reason = entity.getReason();
            this.status = entity.getStatus();
            this.requestedDate = entity.getRequestedDate();
            this.actionedDate = entity.getActionedDate();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getSkipMonth() {
        return skipMonth;
    }

    public void setSkipMonth(String skipMonth) {
        this.skipMonth = skipMonth;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public LocalDate getActionedDate() {
        return actionedDate;
    }

    public void setActionedDate(LocalDate actionedDate) {
        this.actionedDate = actionedDate;
    }
}
