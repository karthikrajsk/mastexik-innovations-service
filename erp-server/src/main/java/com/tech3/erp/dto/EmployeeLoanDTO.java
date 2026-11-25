package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeeLoan;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeLoanDTO {
    private Long id;
    private Long employeeId;
    private BigDecimal loanAmount;
    private Integer tenureMonths;
    private BigDecimal installmentAmount;
    private String fromMonth;
    private String toMonth;
    private LocalDate disbursementDate;
    private String purpose;
    private String status;
    private String employeeCode;
    private String employeeName;
    private String voucherNo;
    private LocalDate voucherDate;
    private LocalDate paymentDate;
    private String skippedMonths;

    public EmployeeLoanDTO() {}

    public EmployeeLoanDTO(EmployeeLoan entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
            this.loanAmount = entity.getLoanAmount();
            this.tenureMonths = entity.getTenureMonths();
            this.installmentAmount = entity.getInstallmentAmount();
            this.fromMonth = entity.getFromMonth();
            this.toMonth = entity.getToMonth();
            this.disbursementDate = entity.getDisbursementDate();
            this.purpose = entity.getPurpose();
            this.status = entity.getStatus();
            this.employeeCode = entity.getEmployeeCode();
            this.employeeName = entity.getEmployeeName();
            this.voucherNo = entity.getVoucherNo();
            this.voucherDate = entity.getVoucherDate();
            this.paymentDate = entity.getPaymentDate();
            this.skippedMonths = entity.getSkippedMonths();
        }
    }

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

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public BigDecimal getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(BigDecimal installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getSkippedMonths() {
        return skippedMonths;
    }

    public void setSkippedMonths(String skippedMonths) {
        this.skippedMonths = skippedMonths;
    }
}
