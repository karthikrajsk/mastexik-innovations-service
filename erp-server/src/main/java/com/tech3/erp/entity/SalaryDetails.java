package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salary_details", schema = "masters")
public class SalaryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salary_structure", nullable = true)
    private String salaryStructure;

    @Column(name = "salary_type", nullable = true)
    private String salaryType; // Monthly/Daily/Hourly

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

    @Column(name = "incentive", nullable = true)
    private BigDecimal incentive;

    @Column(name = "per_hour_salary", nullable = true)
    private BigDecimal perHourSalary;

    @Column(name = "ot_rate_per_hour", nullable = true)
    private BigDecimal otRatePerHour;

    @Column(name = "ot_salary", nullable = true)
    private BigDecimal otSalary;
    
    @Column(name = "gross_salary", nullable = true)
    private BigDecimal grossSalary;

    @Column(name = "production_incentive", nullable = true)
    private BigDecimal productionIncentive;

    @Column(name = "is_canteen", nullable = true)
    private Integer isCanteen = 0;

    @Column(name = "effective_date", nullable = true)
    private LocalDate effectiveDate;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public SalaryDetails() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalaryStructure() {
        return salaryStructure;
    }

    public void setSalaryStructure(String salaryStructure) {
        this.salaryStructure = salaryStructure;
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

    public BigDecimal getIncentive() {
        return incentive;
    }

    public void setIncentive(BigDecimal incentive) {
        this.incentive = incentive;
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

    public BigDecimal getOtSalary() {
        return otSalary;
    }

    public void setOtSalary(BigDecimal otSalary) {
        this.otSalary = otSalary;
    }
    
    public BigDecimal getGrossSalary() {
        return grossSalary;
    }
    
    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
