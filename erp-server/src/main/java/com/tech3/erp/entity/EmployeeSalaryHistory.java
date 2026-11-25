package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_salary_history", schema = "masters")
public class EmployeeSalaryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "salary_type", nullable = true)
    private String salaryType;

    @Column(name = "basic_salary", nullable = true)
    private BigDecimal basicSalary;

    @Column(name = "hra", nullable = true)
    private BigDecimal hra;

    @Column(name = "conveyance_allowance", nullable = true)
    private BigDecimal conveyanceAllowance;

    @Column(name = "medical_allowance", nullable = true)
    private BigDecimal medicalAllowance;

    @Column(name = "special_allowance", nullable = true)
    private BigDecimal specialAllowance;

    @Column(name = "ot_salary", nullable = true)
    private BigDecimal otSalary;

    @Column(name = "per_hour_salary", nullable = true)
    private BigDecimal perHourSalary;

    @Column(name = "ot_rate_per_hour", nullable = true)
    private BigDecimal otRatePerHour;

    @Column(name = "production_incentive", nullable = true)
    private BigDecimal productionIncentive;

    @Column(name = "is_canteen", nullable = true)
    private Integer isCanteen = 0;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "pf_wage_ceiling", nullable = true)
    private BigDecimal pfWageCeiling;

    @Column(name = "remarks", nullable = true)
    private String remarks;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt = LocalDateTime.now();

    public EmployeeSalaryHistory() {}

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getHra() {
        return hra;
    }

    public void setHra(BigDecimal hra) {
        this.hra = hra;
    }

    public BigDecimal getConveyanceAllowance() {
        return conveyanceAllowance;
    }

    public void setConveyanceAllowance(BigDecimal conveyanceAllowance) {
        this.conveyanceAllowance = conveyanceAllowance;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getSpecialAllowance() {
        return specialAllowance;
    }

    public void setSpecialAllowance(BigDecimal specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public BigDecimal getOtSalary() {
        return otSalary;
    }

    public void setOtSalary(BigDecimal otSalary) {
        this.otSalary = otSalary;
    }

    public BigDecimal getPerHourSalary() {
        return perHourSalary;
    }

    public void setPerHourSalary(BigDecimal perHourSalary) {
        this.perHourSalary = perHourSalary;
    }

    public BigDecimal getOtRatePerHour() {
        return otRatePerHour;
    }

    public void setOtRatePerHour(BigDecimal otRatePerHour) {
        this.otRatePerHour = otRatePerHour;
    }

    public BigDecimal getProductionIncentive() {
        return productionIncentive;
    }

    public void setProductionIncentive(BigDecimal productionIncentive) {
        this.productionIncentive = productionIncentive;
    }

    public Integer getIsCanteen() {
        return isCanteen;
    }

    public void setIsCanteen(Integer isCanteen) {
        this.isCanteen = isCanteen;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getPfWageCeiling() {
        return pfWageCeiling;
    }

    public void setPfWageCeiling(BigDecimal pfWageCeiling) {
        this.pfWageCeiling = pfWageCeiling;
    }
}
