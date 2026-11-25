package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeeInsurance;
import java.time.LocalDate;

public class EmployeeInsuranceDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeCode;
    private String insuranceProvider;
    private String policyNumber;
    private String coverageAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String nominee;

    public EmployeeInsuranceDTO() {}

    public EmployeeInsuranceDTO(EmployeeInsurance insurance) {
        if (insurance == null) return;
        this.id = insurance.getId();
        if (insurance.getEmployee() != null) {
            this.employeeId = insurance.getEmployee().getId();
            this.employeeName = insurance.getEmployee().getEmployeeName();
            this.employeeCode = insurance.getEmployee().getEmployeeCode();
        }
        this.insuranceProvider = insurance.getInsuranceProvider();
        this.policyNumber = insurance.getPolicyNumber();
        this.coverageAmount = insurance.getCoverageAmount();
        this.startDate = insurance.getStartDate();
        this.endDate = insurance.getEndDate();
        this.nominee = insurance.getNominee();
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

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(String coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }
}
