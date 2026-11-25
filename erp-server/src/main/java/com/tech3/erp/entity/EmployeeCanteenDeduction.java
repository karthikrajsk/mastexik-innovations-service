package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_canteen_deduction", schema = "masters")
public class EmployeeCanteenDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "month", nullable = false)
    private String month; // Format: "YYYY-MM"

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "canteen_amount", nullable = false)
    private BigDecimal canteenAmount; // Monthly base canteen amount (e.g., 5000)

    @Column(name = "daily_deduction_rate")
    private BigDecimal dailyDeductionRate; // Per day rate (e.g., 5000 / workingDays = 166.67)

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EmployeeCanteenDeduction() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    @JsonProperty("employee")
    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (employee != null) {
            this.employeeCode = employee.getEmployeeCode();
            this.employeeName = employee.getEmployeeName();
        }
    }

    @JsonProperty("employeeId")
    public Long getEmployeeId() {
        return employee != null ? employee.getId() : null;
    }

    @JsonProperty("employeeId")
    public void setEmployeeId(Long employeeId) {
        if (employeeId != null && this.employee == null) {
            Employee emp = new Employee();
            emp.setId(employeeId);
            this.employee = emp;
        }
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getCanteenAmount() {
        return canteenAmount;
    }

    public void setCanteenAmount(BigDecimal canteenAmount) {
        this.canteenAmount = canteenAmount;
    }

    public BigDecimal getDailyDeductionRate() {
        return dailyDeductionRate;
    }

    public void setDailyDeductionRate(BigDecimal dailyDeductionRate) {
        this.dailyDeductionRate = dailyDeductionRate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}
