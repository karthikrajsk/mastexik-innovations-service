package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeeSalaryHistory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeSalaryHistoryDTO {

    private Long id;
    private Long employeeId;
    private String department;
    private String designation;
    private String salaryType;
    private BigDecimal basicSalary;
    private BigDecimal hra;
    private BigDecimal conveyanceAllowance; // DA
    private BigDecimal medicalAllowance;
    private BigDecimal specialAllowance; // TA
    private BigDecimal otSalary;
    private BigDecimal perHourSalary;
    private BigDecimal otRatePerHour;
    private BigDecimal productionIncentive;
    private Integer isCanteen = 0;
    private LocalDate effectiveDate;
    private String remarks;
    private BigDecimal pfWageCeiling = BigDecimal.valueOf(15000);
    private LocalDateTime createdAt;

    public EmployeeSalaryHistoryDTO() {}

    public EmployeeSalaryHistoryDTO(EmployeeSalaryHistory entity) {
        if (entity == null) return;
        this.id = entity.getId();
        this.employeeId = entity.getEmployeeId();
        this.department = entity.getDepartment();
        this.designation = entity.getDesignation();
        this.salaryType = entity.getSalaryType();
        this.basicSalary = entity.getBasicSalary();
        this.hra = entity.getHra();
        this.conveyanceAllowance = entity.getConveyanceAllowance();
        this.medicalAllowance = entity.getMedicalAllowance();
        this.specialAllowance = entity.getSpecialAllowance();
        this.otSalary = entity.getOtSalary();
        this.perHourSalary = entity.getPerHourSalary();
        this.otRatePerHour = entity.getOtRatePerHour();
        this.productionIncentive = entity.getProductionIncentive();
        this.isCanteen = entity.getIsCanteen();
        this.effectiveDate = entity.getEffectiveDate();
        this.remarks = entity.getRemarks();
        this.pfWageCeiling = entity.getPfWageCeiling() != null ? entity.getPfWageCeiling() : BigDecimal.valueOf(15000);
        this.createdAt = entity.getCreatedAt();
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

    public void setIsCanteen(Object val) {
        if (val == null) {
            this.isCanteen = 0;
        } else if (val instanceof Boolean) {
            this.isCanteen = ((Boolean) val) ? 1 : 0;
        } else if (val instanceof Number) {
            this.isCanteen = ((Number) val).intValue();
        } else if (val instanceof String) {
            String str = ((String) val).trim();
            this.isCanteen = Boolean.parseBoolean(str) || "1".equals(str) || "true".equalsIgnoreCase(str) ? 1 : 0;
        } else {
            this.isCanteen = 0;
        }
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
