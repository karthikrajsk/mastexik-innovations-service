package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeeAllowanceAdvance;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeAllowanceAdvanceDTO {
    private Long id;
    private Long employeeId;
    private String type;
    private BigDecimal amount;
    private LocalDate date;
    private String month;
    private String employeeCode;
    private String employeeName;
    private LocalDate paymentDate;
    private LocalDate voucherDate;
    private String voucherNo;
    private Boolean eligibleForAdvance = true;
    private String reason;

    public EmployeeAllowanceAdvanceDTO() {}

    public EmployeeAllowanceAdvanceDTO(EmployeeAllowanceAdvance entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
            this.type = entity.getType();
            this.amount = entity.getAmount();
            this.date = entity.getDate();
            this.month = entity.getMonth();
            this.employeeCode = entity.getEmployeeCode();
            this.employeeName = entity.getEmployeeName();
            this.paymentDate = entity.getPaymentDate();
            this.voucherDate = entity.getVoucherDate();
            this.voucherNo = entity.getVoucherNo();
            this.eligibleForAdvance = entity.getEligibleForAdvance();
            this.reason = entity.getReason();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Boolean getEligibleForAdvance() {
        return eligibleForAdvance;
    }

    public void setEligibleForAdvance(Boolean eligibleForAdvance) {
        this.eligibleForAdvance = eligibleForAdvance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
