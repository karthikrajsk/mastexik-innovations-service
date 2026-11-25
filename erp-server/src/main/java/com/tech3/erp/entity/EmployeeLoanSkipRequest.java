package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_loan_skip_requests", schema = "masters")
public class EmployeeLoanSkipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private EmployeeLoan employeeLoan;

    @Column(name = "skip_month", nullable = false)
    private String skipMonth; // YYYY-MM

    @Column(name = "reason")
    private String reason;

    @Column(name = "status", nullable = false)
    private String status; // Pending, Approved, Declined

    @Column(name = "requested_date")
    private LocalDate requestedDate;

    @Column(name = "actioned_date")
    private LocalDate actionedDate;

    public EmployeeLoanSkipRequest() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeLoan getEmployeeLoan() {
        return employeeLoan;
    }

    public void setEmployeeLoan(EmployeeLoan employeeLoan) {
        this.employeeLoan = employeeLoan;
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
