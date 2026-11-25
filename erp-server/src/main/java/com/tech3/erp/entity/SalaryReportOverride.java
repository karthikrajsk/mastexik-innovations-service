package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary_report_override", schema = "masters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"employee_id", "month", "year", "company_id"})
})
public class SalaryReportOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    // Editable Earnings
    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    @Column(name = "ot_salary")
    private BigDecimal otSalary;

    @Column(name = "festival_wage_salary")
    private BigDecimal festivalWageSalary;

    @Column(name = "attendance_incentive")
    private BigDecimal attendanceIncentive;

    @Column(name = "production_incentive")
    private BigDecimal productionIncentive;

    @Column(name = "food_allowance")
    private BigDecimal foodAllowance;

    @Column(name = "att_incentive")
    private BigDecimal attIncentive;

    @Column(name = "prod_incentive")
    private BigDecimal prodIncentive;

    @Column(name = "food_allowance_amount")
    private BigDecimal foodAllowanceAmount;

    @Column(name = "basic_da")
    private BigDecimal basicDA;

    @Column(name = "hra")
    private BigDecimal hra;

    @Column(name = "ta")
    private BigDecimal ta;

    @Column(name = "medical")
    private BigDecimal medical;

    @Column(name = "house_rent")
    private BigDecimal houseRent;

    // Editable Deductions
    @Column(name = "salary_advance_deduction")
    private BigDecimal salaryAdvanceDeduction;

    @Column(name = "loan_deduction")
    private BigDecimal loanDeduction;

    @Column(name = "pf_deduction")
    private BigDecimal pfDeduction;

    @Column(name = "esi_deduction")
    private BigDecimal esiDeduction;

    @Column(name = "canteen_deduction")
    private BigDecimal canteenDeduction;

    @Column(name = "tds")
    private BigDecimal tds;

    // Calculated fields
    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "total_earning_for_pf")
    private BigDecimal totalEarningForPF;

    @Column(name = "total_deduction")
    private BigDecimal totalDeduction;

    @Column(name = "net_salary")
    private BigDecimal netSalary;

    @Column(name = "actual_present_days", precision = 10, scale = 2)
    private BigDecimal actualPresentDays;

    @Column(name = "per_day_salary")
    private BigDecimal perDaySalary;

    @Column(name = "ot_hours_worked", precision = 10, scale = 2)
    private BigDecimal otHoursWorked;

    @Column(name = "festival_hours_worked", precision = 10, scale = 2)
    private BigDecimal festivalHoursWorked;

    @Column(name = "days_to_be_present")
    private Integer daysToBePresent;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "department", length = 150)
    private String department;

    @Column(name = "designation", length = 150)
    private String designation;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "is_salary_generated", columnDefinition = "int default 0")
    private Integer isSalaryGenerated = 0;

    public SalaryReportOverride() {}

    public Integer getDaysToBePresent() { return daysToBePresent; }
    public void setDaysToBePresent(Integer daysToBePresent) { this.daysToBePresent = daysToBePresent; }

    public BigDecimal getActualPresentDays() { return actualPresentDays; }
    public void setActualPresentDays(BigDecimal actualPresentDays) { this.actualPresentDays = actualPresentDays; }

    public BigDecimal getPerDaySalary() { return perDaySalary; }
    public void setPerDaySalary(BigDecimal perDaySalary) { this.perDaySalary = perDaySalary; }

    public BigDecimal getOtHoursWorked() { return otHoursWorked; }
    public void setOtHoursWorked(BigDecimal otHoursWorked) { this.otHoursWorked = otHoursWorked; }

    public BigDecimal getFestivalHoursWorked() { return festivalHoursWorked; }
    public void setFestivalHoursWorked(BigDecimal festivalHoursWorked) { this.festivalHoursWorked = festivalHoursWorked; }

    public BigDecimal getFestivalWageSalary() { return festivalWageSalary; }
    public void setFestivalWageSalary(BigDecimal festivalWageSalary) { this.festivalWageSalary = festivalWageSalary; }

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public BigDecimal getBasicSalary() { return basicSalary; }
    public void setBasicSalary(BigDecimal basicSalary) { this.basicSalary = basicSalary; }

    public BigDecimal getOtSalary() { return otSalary; }
    public void setOtSalary(BigDecimal otSalary) { this.otSalary = otSalary; }

    public BigDecimal getAttendanceIncentive() { return attendanceIncentive; }
    public void setAttendanceIncentive(BigDecimal attendanceIncentive) { this.attendanceIncentive = attendanceIncentive; }

    public BigDecimal getProductionIncentive() { return productionIncentive; }
    public void setProductionIncentive(BigDecimal productionIncentive) { this.productionIncentive = productionIncentive; }

    public BigDecimal getFoodAllowance() { return foodAllowance; }
    public void setFoodAllowance(BigDecimal foodAllowance) { this.foodAllowance = foodAllowance; }

    public BigDecimal getAttIncentive() { return attIncentive; }
    public void setAttIncentive(BigDecimal attIncentive) { this.attIncentive = attIncentive; }

    public BigDecimal getProdIncentive() { return prodIncentive; }
    public void setProdIncentive(BigDecimal prodIncentive) { this.prodIncentive = prodIncentive; }

    public BigDecimal getFoodAllowanceAmount() { return foodAllowanceAmount; }
    public void setFoodAllowanceAmount(BigDecimal foodAllowanceAmount) { this.foodAllowanceAmount = foodAllowanceAmount; }

    public BigDecimal getBasicDA() { return basicDA; }
    public void setBasicDA(BigDecimal basicDA) { this.basicDA = basicDA; }

    public BigDecimal getHra() { return hra; }
    public void setHra(BigDecimal hra) { this.hra = hra; }

    public BigDecimal getTa() { return ta; }
    public void setTa(BigDecimal ta) { this.ta = ta; }

    public BigDecimal getMedical() { return medical; }
    public void setMedical(BigDecimal medical) { this.medical = medical; }

    public BigDecimal getSalaryAdvanceDeduction() { return salaryAdvanceDeduction; }
    public void setSalaryAdvanceDeduction(BigDecimal salaryAdvanceDeduction) { this.salaryAdvanceDeduction = salaryAdvanceDeduction; }

    public BigDecimal getLoanDeduction() { return loanDeduction; }
    public void setLoanDeduction(BigDecimal loanDeduction) { this.loanDeduction = loanDeduction; }

    public BigDecimal getPfDeduction() { return pfDeduction; }
    public void setPfDeduction(BigDecimal pfDeduction) { this.pfDeduction = pfDeduction; }

    public BigDecimal getEsiDeduction() { return esiDeduction; }
    public void setEsiDeduction(BigDecimal esiDeduction) { this.esiDeduction = esiDeduction; }

    public BigDecimal getCanteenDeduction() { return canteenDeduction; }
    public void setCanteenDeduction(BigDecimal canteenDeduction) { this.canteenDeduction = canteenDeduction; }

    public BigDecimal getTds() { return tds; }
    public void setTds(BigDecimal tds) { this.tds = tds; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getTotalEarningForPF() { return totalEarningForPF; }
    public void setTotalEarningForPF(BigDecimal totalEarningForPF) { this.totalEarningForPF = totalEarningForPF; }

    public BigDecimal getTotalDeduction() { return totalDeduction; }
    public void setTotalDeduction(BigDecimal totalDeduction) { this.totalDeduction = totalDeduction; }

    public BigDecimal getNetSalary() { return netSalary; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public Integer getIsSalaryGenerated() { return isSalaryGenerated; }
    public void setIsSalaryGenerated(Integer isSalaryGenerated) { this.isSalaryGenerated = isSalaryGenerated; }

    public BigDecimal getHouseRent() { return houseRent; }
    public void setHouseRent(BigDecimal houseRent) { this.houseRent = houseRent; }
}
