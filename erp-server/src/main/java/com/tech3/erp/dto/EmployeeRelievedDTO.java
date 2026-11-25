package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeRelievedDTO {

    private Long id;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate relievingDate;

    private String reason;

    private String remarks;

    private String department;

    private String designation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    private Integer isRelieved = 0;

    private Boolean activeFlag = true;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

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

    public LocalDate getRelievingDate() {
        return relievingDate;
    }

    public void setRelievingDate(LocalDate relievingDate) {
        this.relievingDate = relievingDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getIsRelieved() {
        return isRelieved;
    }

    public void setIsRelieved(Integer isRelieved) {
        this.isRelieved = isRelieved;
        // Auto-update activeFlag based on isRelieved: if isRelieved is 0, activeFlag is true; if isRelieved is 1, activeFlag is false
        this.activeFlag = (isRelieved != null && isRelieved == 0);
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
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
