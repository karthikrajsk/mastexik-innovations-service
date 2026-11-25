package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeePfWageCeilingHistory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeePfWageCeilingHistoryDTO {

    private Long id;
    private Long employeeId;
    private BigDecimal pfWageCeiling = BigDecimal.valueOf(15000);
    private LocalDate effectiveDate;
    private String remarks;
    private LocalDateTime createdAt;

    public EmployeePfWageCeilingHistoryDTO() {}

    public EmployeePfWageCeilingHistoryDTO(EmployeePfWageCeilingHistory entity) {
        if (entity == null) return;
        this.id = entity.getId();
        this.employeeId = entity.getEmployeeId();
        this.pfWageCeiling = entity.getPfWageCeiling() != null ? entity.getPfWageCeiling() : BigDecimal.valueOf(15000);
        this.effectiveDate = entity.getEffectiveDate();
        this.remarks = entity.getRemarks();
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

    public BigDecimal getPfWageCeiling() {
        return pfWageCeiling;
    }

    public void setPfWageCeiling(BigDecimal pfWageCeiling) {
        this.pfWageCeiling = pfWageCeiling;
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
}
