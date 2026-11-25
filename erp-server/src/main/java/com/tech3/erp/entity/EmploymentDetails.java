package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employment_details", schema = "masters")
public class EmploymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "qualification", nullable = true)
    private String qualification;

    @Column(name = "joining_date", nullable = true)
    private LocalDate joiningDate;

    @Column(name = "employee_type", nullable = true)
    private String employeeType; // Permanent/Contract

    @Column(name = "status", nullable = true)
    private String status; // Active/Relieved

    @Column(name = "hostel_family_quarters", nullable = true)
    private String hostelFamilyQuarters;

    @Column(name = "hostel_family_quarters_room_no", nullable = true)
    private String hostelFamilyQuartersRoomNo;

    @Column(name = "is_festival_wage_applicable", nullable = true)
    private Integer isFestivalWageApplicable = 0;

    @Column(name = "is_holiday_deduction_applicable", nullable = true)
    private Integer isHolidayDeductionApplicable = 0;

    @Column(name = "house_rent", nullable = true)
    private Double houseRent;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public EmploymentDetails() {}

    public Integer getIsHolidayDeductionApplicable() {
        return isHolidayDeductionApplicable;
    }

    public void setIsHolidayDeductionApplicable(Integer isHolidayDeductionApplicable) {
        this.isHolidayDeductionApplicable = isHolidayDeductionApplicable;
    }

    public Integer getIsFestivalWageApplicable() {
        return isFestivalWageApplicable;
    }

    public void setIsFestivalWageApplicable(Integer isFestivalWageApplicable) {
        this.isFestivalWageApplicable = isFestivalWageApplicable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHostelFamilyQuarters() {
        return hostelFamilyQuarters;
    }

    public void setHostelFamilyQuarters(String hostelFamilyQuarters) {
        this.hostelFamilyQuarters = hostelFamilyQuarters;
    }

    public String getHostelFamilyQuartersRoomNo() {
        return hostelFamilyQuartersRoomNo;
    }

    public void setHostelFamilyQuartersRoomNo(String hostelFamilyQuartersRoomNo) {
        this.hostelFamilyQuartersRoomNo = hostelFamilyQuartersRoomNo;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(Double houseRent) {
        this.houseRent = houseRent;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
