// salary repport dto


package com.tech3.erp.dto;

import java.math.BigDecimal;

public class SalaryReportDTO {
    private Long id;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String designation;
    private String month;
    private String salaryType;
    private Long companyId;
    
    // Working days and hours
    private Integer daysToBePresent;
    private Double workingHoursPerDay;
    private Double actualPresentDays;
    private Integer additionalWeekOffDays;
    private Double otHoursWorked;
    private Double festivalHoursWorked = 0.0;
    private BigDecimal houseRent;
    
    // Earnings
    private BigDecimal grossSalary;
    private BigDecimal perDaySalary;
    private BigDecimal basicSalary;
    private BigDecimal otSalary;
    private BigDecimal festivalWageSalary = BigDecimal.ZERO;
    private BigDecimal attendanceIncentive;
    private BigDecimal productionIncentive;
    private BigDecimal foodAllowance;
    private BigDecimal attIncentive;
    private BigDecimal prodIncentive;
    private BigDecimal foodAllowanceAmount;
    private BigDecimal totalEarnings;
    private BigDecimal total; // TOTAL Earnings (Intermediate)
    
    // Allowances and HRA components
    private BigDecimal basicDA;
    private BigDecimal hra;
    private BigDecimal ta;
    private BigDecimal medical;
    private BigDecimal totalEarningForPF;
    
    // Deductions
    private BigDecimal salaryAdvanceDeduction;
    private BigDecimal loanDeduction;
    private BigDecimal pfDeduction;
    private BigDecimal pfWageCeiling;
    private BigDecimal esiDeduction;
    private BigDecimal canteenDeduction;
    private BigDecimal tds;
    private BigDecimal totalDeduction;
    
    // Final calculations
    private BigDecimal netSalary;
    private String category;
    private String remarks;
    private Integer isSalaryGenerated;

    // Legacy fields for backward compatibility
    private Double totalWorkingHours;
    private Double otHours;
    private BigDecimal regularSalary;
    private BigDecimal perHourSalary;
    private BigDecimal otRatePerHour;
    private BigDecimal food;

    public SalaryReportDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public String getSalaryType() { return salaryType; }
    public void setSalaryType(String salaryType) { this.salaryType = salaryType; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Integer getDaysToBePresent() { return daysToBePresent; }
    public void setDaysToBePresent(Integer daysToBePresent) { this.daysToBePresent = daysToBePresent; }

    public Double getWorkingHoursPerDay() { return workingHoursPerDay; }
    public void setWorkingHoursPerDay(Double workingHoursPerDay) { this.workingHoursPerDay = workingHoursPerDay; }

    public Double getActualPresentDays() { return actualPresentDays; }
    public void setActualPresentDays(Double actualPresentDays) { this.actualPresentDays = actualPresentDays; }

    public Integer getAdditionalWeekOffDays() { return additionalWeekOffDays; }
    public void setAdditionalWeekOffDays(Integer additionalWeekOffDays) { this.additionalWeekOffDays = additionalWeekOffDays; }

    public Double getOtHoursWorked() { return otHoursWorked; }
    public void setOtHoursWorked(Double otHoursWorked) { this.otHoursWorked = otHoursWorked; }

    public Double getFestivalHoursWorked() { return festivalHoursWorked; }
    public void setFestivalHoursWorked(Double festivalHoursWorked) { this.festivalHoursWorked = festivalHoursWorked; }

    public BigDecimal getGrossSalary() { return grossSalary; }
    public void setGrossSalary(BigDecimal grossSalary) { this.grossSalary = grossSalary; }

    public BigDecimal getPerDaySalary() { return perDaySalary; }
    public void setPerDaySalary(BigDecimal perDaySalary) { this.perDaySalary = perDaySalary; }

    public BigDecimal getBasicSalary() { return basicSalary; }
    public void setBasicSalary(BigDecimal basicSalary) { this.basicSalary = basicSalary; }

    public BigDecimal getOtSalary() { return otSalary; }
    public void setOtSalary(BigDecimal otSalary) { this.otSalary = otSalary; }

    public BigDecimal getFestivalWageSalary() { return festivalWageSalary; }
    public void setFestivalWageSalary(BigDecimal festivalWageSalary) { this.festivalWageSalary = festivalWageSalary; }

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

    public BigDecimal getTotalEarnings() { return totalEarnings; }
    public void setTotalEarnings(BigDecimal totalEarnings) { this.totalEarnings = totalEarnings; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getBasicDA() { return basicDA; }
    public void setBasicDA(BigDecimal basicDA) { this.basicDA = basicDA; }

    public BigDecimal getHra() { return hra; }
    public void setHra(BigDecimal hra) { this.hra = hra; }

    public BigDecimal getTa() { return ta; }
    public void setTa(BigDecimal ta) { this.ta = ta; }

    public BigDecimal getMedical() { return medical; }
    public void setMedical(BigDecimal medical) { this.medical = medical; }

    public BigDecimal getTotalEarningForPF() { return totalEarningForPF; }
    public void setTotalEarningForPF(BigDecimal totalEarningForPF) { this.totalEarningForPF = totalEarningForPF; }

    public BigDecimal getSalaryAdvanceDeduction() { return salaryAdvanceDeduction; }
    public void setSalaryAdvanceDeduction(BigDecimal salaryAdvanceDeduction) { this.salaryAdvanceDeduction = salaryAdvanceDeduction; }

    public BigDecimal getLoanDeduction() { return loanDeduction; }
    public void setLoanDeduction(BigDecimal loanDeduction) { this.loanDeduction = loanDeduction; }

    public BigDecimal getPfDeduction() { return pfDeduction; }
    public void setPfDeduction(BigDecimal pfDeduction) { this.pfDeduction = pfDeduction; }
    public BigDecimal getPfWageCeiling() { return pfWageCeiling; }
    public void setPfWageCeiling(BigDecimal pfWageCeiling) { this.pfWageCeiling = pfWageCeiling; }

    public BigDecimal getEsiDeduction() { return esiDeduction; }
    public void setEsiDeduction(BigDecimal esiDeduction) { this.esiDeduction = esiDeduction; }

    public BigDecimal getCanteenDeduction() { return canteenDeduction; }
    public void setCanteenDeduction(BigDecimal canteenDeduction) { this.canteenDeduction = canteenDeduction; }

    public BigDecimal getTds() { return tds; }
    public void setTds(BigDecimal tds) { this.tds = tds; }

    public BigDecimal getTotalDeduction() { return totalDeduction; }
    public void setTotalDeduction(BigDecimal totalDeduction) { this.totalDeduction = totalDeduction; }

    public BigDecimal getNetSalary() { return netSalary; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Integer getIsSalaryGenerated() { return isSalaryGenerated; }
    public void setIsSalaryGenerated(Integer isSalaryGenerated) { this.isSalaryGenerated = isSalaryGenerated; }

    // Legacy getters and setters for backward compatibility
    public Double getTotalWorkingHours() { return totalWorkingHours; }
    public void setTotalWorkingHours(Double totalWorkingHours) { this.totalWorkingHours = totalWorkingHours; }

    public Double getOtHours() { return otHours; }
    public void setOtHours(Double otHours) { this.otHours = otHours; }

    public BigDecimal getRegularSalary() { return regularSalary; }
    public void setRegularSalary(BigDecimal regularSalary) { this.regularSalary = regularSalary; }

    public BigDecimal getPerHourSalary() { return perHourSalary; }
    public void setPerHourSalary(BigDecimal perHourSalary) { this.perHourSalary = perHourSalary; }

    public BigDecimal getOtRatePerHour() { return otRatePerHour; }
    public void setOtRatePerHour(BigDecimal otRatePerHour) { this.otRatePerHour = otRatePerHour; }

    public BigDecimal getFood() { return food; }
    public void setFood(BigDecimal food) { this.food = food; }

    public BigDecimal getHouseRent() { return houseRent; }
    public void setHouseRent(BigDecimal houseRent) { this.houseRent = houseRent; }
}
