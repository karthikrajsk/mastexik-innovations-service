package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees", schema = "masters")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = true, unique = true)
    private String employeeCode;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "dob", nullable = true)
    private LocalDate dob;

    @Column(name = "father_husband_name", nullable = true)
    private String fatherHusbandName;

    @Column(name = "mobile_number", nullable = true)
    private String mobileNumber;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "aadhaar_number", nullable = true)
    private String aadhaarNumber;

    @Column(name = "pan_number", nullable = true)
    private String panNumber;

    @Column(name = "marital_status", nullable = true)
    private String maritalStatus;

    @Column(name = "uan_number", nullable = true)
    private String uanNumber;

    @Column(name = "pf_number", nullable = true)
    private String pfNumber;

    @Column(name = "pf_joining_date", nullable = true)
    private LocalDate pfJoiningDate;

    @Column(name = "esi_number", nullable = true)
    private String esiNumber;

    @Column(name = "pf_applicable", nullable = true)
    private Boolean pfApplicable;

    @Column(name = "esi_applicable", nullable = true)
    private Boolean esiApplicable;

    @Column(name = "pf_percent", nullable = true)
    private Double pfPercent;

    @Column(name = "pf_wage_ceiling", nullable = true)
    private Double pfWageCeiling;

    @Column(name = "esi_percent", nullable = true)
    private Double esiPercent;

    @Column(name = "basic_da_percent", nullable = true)
    private Double basicDaPercent;

    @Column(name = "hra_percent", nullable = true)
    private Double hraPercent;

    @Column(name = "ta_percent", nullable = true)
    private Double taPercent;

    @Column(name = "medical_percent", nullable = true)
    private Double medicalPercent;

    @Column(name = "is_relieved", nullable = true)
    private Integer isRelieved;

    @Column(name = "is_eligible_for_28_days", nullable = true)
    private Boolean isEligibleFor28Days = false;

    @Column(name = "is_allowance", nullable = true)
    private Integer isAllowance = 0;

    @Column(name = "is_advance", nullable = true)
    private Integer isAdvance = 0;

    @Column(name = "production_incentive_applicable", nullable = true)
    private Integer productionIncentiveApplicable = 0;

    @Column(name = "attendance_incentive_applicable", nullable = true)
    private Integer attendanceIncentiveApplicable = 0;

    @Column(name = "food_allowance_applicable", nullable = true)
    private Integer foodAllowanceApplicable = 0;

    @Column(name = "festival_wages_applicable", nullable = true)
    private Integer festivalWagesApplicable = 0;

    @Column(name = "holiday_deduction_applicable", nullable = true)
    private Integer holidayDeductionApplicable = 0;

    @Column(name = "attendance_incentive", nullable = true)
    private Double attendanceIncentive = 0.0;

    @Column(name = "production_incentive", nullable = true)
    private Double productionIncentive = 0.0;

    @Column(name = "blood_group", nullable = true)
    private String bloodGroup;

    @Column(name = "emergency_contact_number", nullable = true)
    private String emergencyContactNumber;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "employee_photo", columnDefinition = "TEXT")
    private String employeePhoto;

    @Column(name = "aadhaar_card", columnDefinition = "TEXT")
    private String aadhaarCard;

    private Long photoMediaDetailSno;
    private Long photoMediaSno;

    // Redundant but kept for backward compatibility if needed, 
    // though the request says to separate them into tables.
    // I will keep them but they should ideally come from sub-tables.
    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "salary", nullable = true)
    private String salary;

    @Column(name = "pf", nullable = true)
    private String pf;

    @Column(name = "tax", nullable = true)
    private String tax;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private EmploymentDetails employmentDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private SalaryDetails salaryDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private ShiftDetails shiftDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private BankDetails bankDetails;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @OrderBy("effectiveDate DESC")
    private java.util.List<EmployeeSalaryHistory> salaryHistory = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("effectiveDate DESC")
    private java.util.List<EmployeePfWageCeilingHistory> pfWageCeilingHistory = new java.util.ArrayList<>();

    // Default constructor
    public Employee() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getUanNumber() {
        return uanNumber;
    }

    public void setUanNumber(String uanNumber) {
        this.uanNumber = uanNumber;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public LocalDate getPfJoiningDate() {
        return pfJoiningDate;
    }

    public void setPfJoiningDate(LocalDate pfJoiningDate) {
        this.pfJoiningDate = pfJoiningDate;
    }

    public String getEsiNumber() {
        return esiNumber;
    }

    public void setEsiNumber(String esiNumber) {
        this.esiNumber = esiNumber;
    }

    public Boolean getPfApplicable() {
        return pfApplicable;
    }

    public void setPfApplicable(Boolean pfApplicable) {
        this.pfApplicable = pfApplicable;
    }

    public Boolean getEsiApplicable() {
        return esiApplicable;
    }

    public void setEsiApplicable(Boolean esiApplicable) {
        this.esiApplicable = esiApplicable;
    }

    public Double getPfPercent() {
        return pfPercent;
    }

    public void setPfPercent(Double pfPercent) {
        this.pfPercent = pfPercent;
    }

    public Double getPfWageCeiling() {
        return pfWageCeiling;
    }

    public void setPfWageCeiling(Double pfWageCeiling) {
        this.pfWageCeiling = pfWageCeiling;
    }

    public Double getEsiPercent() {
        return esiPercent;
    }

    public void setEsiPercent(Double esiPercent) {
        this.esiPercent = esiPercent;
    }

    public Integer getIsRelieved() {
        return isRelieved;
    }

    public void setIsRelieved(Integer isRelieved) {
        this.isRelieved = isRelieved;
    }

    public Integer getIsAllowance() {
        return isAllowance;
    }

    public void setIsAllowance(Integer isAllowance) {
        this.isAllowance = isAllowance;
    }

    public Integer getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(Integer isAdvance) {
        this.isAdvance = isAdvance;
    }

    public Integer getProductionIncentiveApplicable() {
        return productionIncentiveApplicable;
    }

    public void setProductionIncentiveApplicable(Integer productionIncentiveApplicable) {
        this.productionIncentiveApplicable = productionIncentiveApplicable;
    }

    public Integer getAttendanceIncentiveApplicable() {
        return attendanceIncentiveApplicable;
    }

    public void setAttendanceIncentiveApplicable(Integer attendanceIncentiveApplicable) {
        this.attendanceIncentiveApplicable = attendanceIncentiveApplicable;
    }

    public Integer getFoodAllowanceApplicable() {
        return foodAllowanceApplicable;
    }

    public void setFoodAllowanceApplicable(Integer foodAllowanceApplicable) {
        this.foodAllowanceApplicable = foodAllowanceApplicable;
    }

    public Integer getFestivalWagesApplicable() {
        return festivalWagesApplicable;
    }

    public void setFestivalWagesApplicable(Integer festivalWagesApplicable) {
        this.festivalWagesApplicable = festivalWagesApplicable;
    }

    public Double getAttendanceIncentive() {
        return attendanceIncentive;
    }

    public void setAttendanceIncentive(Double attendanceIncentive) {
        this.attendanceIncentive = attendanceIncentive;
    }

    public Double getProductionIncentive() {
        return productionIncentive;
    }

    public void setProductionIncentive(Double productionIncentive) {
        this.productionIncentive = productionIncentive;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmployeePhoto() {
        return employeePhoto;
    }

    public void setEmployeePhoto(String employeePhoto) {
        this.employeePhoto = employeePhoto;
    }

    public String getAadhaarCard() {
        return aadhaarCard;
    }

    public void setAadhaarCard(String aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    public Long getPhotoMediaDetailSno() {
        return photoMediaDetailSno;
    }

    public void setPhotoMediaDetailSno(Long photoMediaDetailSno) {
        this.photoMediaDetailSno = photoMediaDetailSno;
    }

    public Long getPhotoMediaSno() {
        return photoMediaSno;
    }

    public void setPhotoMediaSno(Long photoMediaSno) {
        this.photoMediaSno = photoMediaSno;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public EmploymentDetails getEmploymentDetails() {
        return employmentDetails;
    }

    public void setEmploymentDetails(EmploymentDetails employmentDetails) {
        this.employmentDetails = employmentDetails;
        if (employmentDetails != null) {
            employmentDetails.setEmployee(this);
        }
    }

    public SalaryDetails getSalaryDetails() {
        return salaryDetails;
    }

    public void setSalaryDetails(SalaryDetails salaryDetails) {
        this.salaryDetails = salaryDetails;
        if (salaryDetails != null) {
            salaryDetails.setEmployee(this);
        }
    }

    public ShiftDetails getShiftDetails() {
        return shiftDetails;
    }

    public void setShiftDetails(ShiftDetails shiftDetails) {
        this.shiftDetails = shiftDetails;
        if (shiftDetails != null) {
            shiftDetails.setEmployee(this);
        }
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
        if (bankDetails != null) {
            bankDetails.setEmployee(this);
        }
    }

    public Boolean getIsEligibleFor28Days() {
        return isEligibleFor28Days;
    }

    public void setIsEligibleFor28Days(Boolean isEligibleFor28Days) {
        this.isEligibleFor28Days = isEligibleFor28Days;
    }

    public Double getBasicDaPercent() {
        return basicDaPercent;
    }

    public void setBasicDaPercent(Double basicDaPercent) {
        this.basicDaPercent = basicDaPercent;
    }

    public Double getHraPercent() {
        return hraPercent;
    }

    public void setHraPercent(Double hraPercent) {
        this.hraPercent = hraPercent;
    }

    public Double getTaPercent() {
        return taPercent;
    }

    public void setTaPercent(Double taPercent) {
        this.taPercent = taPercent;
    }

    public Double getMedicalPercent() {
        return medicalPercent;
    }

    public void setMedicalPercent(Double medicalPercent) {
        this.medicalPercent = medicalPercent;
    }

    public java.util.List<EmployeeSalaryHistory> getSalaryHistory() {
        return salaryHistory;
    }

    public void setSalaryHistory(java.util.List<EmployeeSalaryHistory> salaryHistory) {
        this.salaryHistory = salaryHistory;
    }

    public java.util.List<EmployeePfWageCeilingHistory> getPfWageCeilingHistory() {
        return pfWageCeilingHistory;
    }

    public void setPfWageCeilingHistory(java.util.List<EmployeePfWageCeilingHistory> pfWageCeilingHistory) {
        this.pfWageCeilingHistory = pfWageCeilingHistory;
    }

    public Integer getHolidayDeductionApplicable() {
        return holidayDeductionApplicable;
    }

    public void setHolidayDeductionApplicable(Integer holidayDeductionApplicable) {
        this.holidayDeductionApplicable = holidayDeductionApplicable;
    }
}
