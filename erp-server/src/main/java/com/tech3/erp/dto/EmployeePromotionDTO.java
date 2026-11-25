package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeePromotion;
import java.time.LocalDate;

public class EmployeePromotionDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeCode;
    private String currentDesignation;
    private String newDesignation;
    private String currentSalary;
    private String incrementAmount;
    private String newSalary;
    private LocalDate effectiveDate;
    private String remarks;

    public EmployeePromotionDTO() {}

    public EmployeePromotionDTO(EmployeePromotion promotion) {
        if (promotion == null) return;
        this.id = promotion.getId();
        if (promotion.getEmployee() != null) {
            this.employeeId = promotion.getEmployee().getId();
            this.employeeName = promotion.getEmployee().getEmployeeName();
            this.employeeCode = promotion.getEmployee().getEmployeeCode();
        }
        this.currentDesignation = promotion.getCurrentDesignation();
        this.newDesignation = promotion.getNewDesignation();
        this.currentSalary = promotion.getCurrentSalary();
        this.incrementAmount = promotion.getIncrementAmount();
        this.newSalary = promotion.getNewSalary();
        this.effectiveDate = promotion.getEffectiveDate();
        this.remarks = promotion.getRemarks();
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getCurrentDesignation() {
        return currentDesignation;
    }

    public void setCurrentDesignation(String currentDesignation) {
        this.currentDesignation = currentDesignation;
    }

    public String getNewDesignation() {
        return newDesignation;
    }

    public void setNewDesignation(String newDesignation) {
        this.newDesignation = newDesignation;
    }

    public String getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(String currentSalary) {
        this.currentSalary = currentSalary;
    }

    public String getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(String incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public String getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(String newSalary) {
        this.newSalary = newSalary;
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
}
