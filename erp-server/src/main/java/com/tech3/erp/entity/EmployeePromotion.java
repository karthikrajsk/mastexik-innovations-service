package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_promotions", schema = "masters")
public class EmployeePromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "current_designation")
    private String currentDesignation;

    @Column(name = "new_designation")
    private String newDesignation;

    @Column(name = "current_salary")
    private String currentSalary;

    @Column(name = "increment_amount")
    private String incrementAmount;

    @Column(name = "new_salary")
    private String newSalary;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    public EmployeePromotion() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
