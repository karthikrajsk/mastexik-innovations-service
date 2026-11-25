package com.tech3.erp.dto;

import com.tech3.erp.entity.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class EmployeeDTO {

    private Long id;

    private String employeeCode;

    @NotBlank(message = "Employee Name is required")
    private String employeeName;

    private String gender;

    private LocalDate dob;

    private String fatherHusbandName;

    // @NotBlank(message = "Mobile Number is required")
    private String mobileNumber;

    @Email(message = "Invalid email format")
    private String email;

    private String address;

    private String aadhaarNumber;

    private String panNumber;

    private String maritalStatus;

    private String uanNumber;
    private String pfNumber;
    private LocalDate pfJoiningDate;
    private String esiNumber;
    private Boolean pfApplicable;
    private Boolean esiApplicable;
    private Double pfPercent;
    private Double esiPercent;
    private Double basicDaPercent;
    private Double hraPercent;
    private Double taPercent;
    private Double medicalPercent;
    private Integer isRelieved = 0;
    private Boolean isEligibleFor28Days = false;
    private Integer isAllowance = 0;
    private Integer isAdvance = 0;
    private Integer productionIncentiveApplicable = 0;
    private Integer attendanceIncentiveApplicable = 0;
    private Integer foodAllowanceApplicable = 0;
    private Integer festivalWagesApplicable = 0;
    private Integer holidayDeductionApplicable = 0;
    private Double attendanceIncentive = 0.0;
    private Double productionIncentive = 0.0;
    private String bloodGroup;
    private String emergencyContactNumber;
    private String category;
    private String employeePhoto;

    private String aadhaarCard;

    private Long photoMediaDetailSno;
    private Long photoMediaSno;

    // Existing fields for backward compatibility
    private String designation;
    private String department;
    private String salary;
    private String pf;
    private String tax;

    @Valid
    private EmploymentDetailsDTO employmentDetails;

    @Valid
    private SalaryDetailsDTO salaryDetails;

    @Valid
    private ShiftDetailsDTO shiftDetails;

    @Valid
    private BankDetailsDTO bankDetails;

    private java.util.List<EmployeeSalaryHistoryDTO> salaryHistory;
    private java.util.List<EmployeePfWageCeilingHistoryDTO> pfWageCeilingHistory;
    private Double pfWageCeiling = 15000.0;

    public EmployeeDTO() {}

    public EmployeeDTO(Employee employee) {
        if (employee == null) return;
        this.id = employee.getId();
        this.employeeCode = employee.getEmployeeCode();
        this.employeeName = employee.getEmployeeName();
        this.gender = employee.getGender();
        this.dob = employee.getDob();
        this.fatherHusbandName = employee.getFatherHusbandName();
        this.mobileNumber = employee.getMobileNumber();
        this.email = employee.getEmail();
        this.address = employee.getAddress();
        this.aadhaarNumber = employee.getAadhaarNumber();
        this.panNumber = employee.getPanNumber();
        this.maritalStatus = employee.getMaritalStatus();
        this.uanNumber = employee.getUanNumber();
        this.pfNumber = employee.getPfNumber();
        this.pfJoiningDate = employee.getPfJoiningDate();
        this.esiNumber = employee.getEsiNumber();
        this.pfApplicable = employee.getPfApplicable();
        this.esiApplicable = employee.getEsiApplicable();
        this.pfPercent = employee.getPfPercent();
        this.esiPercent = employee.getEsiPercent();
        this.basicDaPercent = employee.getBasicDaPercent();
        this.hraPercent = employee.getHraPercent();
        this.taPercent = employee.getTaPercent();
        this.medicalPercent = employee.getMedicalPercent();
        this.isRelieved = employee.getIsRelieved();
        this.isEligibleFor28Days = employee.getIsEligibleFor28Days();
        this.isAllowance = employee.getIsAllowance();
        this.isAdvance = employee.getIsAdvance();
        this.productionIncentiveApplicable = employee.getProductionIncentiveApplicable();
        this.attendanceIncentiveApplicable = employee.getAttendanceIncentiveApplicable();
        this.foodAllowanceApplicable = employee.getFoodAllowanceApplicable();
        this.festivalWagesApplicable = employee.getFestivalWagesApplicable();
        if ((this.festivalWagesApplicable == null || this.festivalWagesApplicable == 0) && employee.getEmploymentDetails() != null) {
            this.festivalWagesApplicable = employee.getEmploymentDetails().getIsFestivalWageApplicable();
        }
        this.holidayDeductionApplicable = employee.getHolidayDeductionApplicable();
        if ((this.holidayDeductionApplicable == null || this.holidayDeductionApplicable == 0) && employee.getEmploymentDetails() != null) {
            this.holidayDeductionApplicable = employee.getEmploymentDetails().getIsHolidayDeductionApplicable();
        }
        this.attendanceIncentive = employee.getAttendanceIncentive();
        this.productionIncentive = employee.getProductionIncentive();
        this.bloodGroup = employee.getBloodGroup();
        this.emergencyContactNumber = employee.getEmergencyContactNumber();
        this.category = employee.getCategory();
        this.employeePhoto = employee.getEmployeePhoto();
        this.aadhaarCard = employee.getAadhaarCard();
        this.photoMediaDetailSno = employee.getPhotoMediaDetailSno();
        this.photoMediaSno = employee.getPhotoMediaSno();
        this.pfWageCeiling = employee.getPfWageCeiling() != null ? employee.getPfWageCeiling() : 15000.0;
        
        // Backward compatibility mapping
        this.designation = employee.getDesignation();
        this.department = employee.getDepartment();
        this.salary = employee.getSalary();
        this.pf = employee.getPf();
        this.tax = employee.getTax();

        if (employee.getEmploymentDetails() != null) {
            this.employmentDetails = new EmploymentDetailsDTO(employee.getEmploymentDetails());
        }
        if (employee.getSalaryDetails() != null) {
            this.salaryDetails = new SalaryDetailsDTO(employee.getSalaryDetails());
        }
        if (employee.getShiftDetails() != null) {
            this.shiftDetails = new ShiftDetailsDTO(employee.getShiftDetails());
        }
        if (employee.getBankDetails() != null) {
            this.bankDetails = new BankDetailsDTO(employee.getBankDetails());
        }
        if (employee.getPfWageCeilingHistory() != null && !employee.getPfWageCeilingHistory().isEmpty()) {
            this.pfWageCeilingHistory = employee.getPfWageCeilingHistory().stream()
                    .map(EmployeePfWageCeilingHistoryDTO::new)
                    .collect(java.util.stream.Collectors.toList());
        }
        if (employee.getSalaryHistory() != null && !employee.getSalaryHistory().isEmpty()) {
            this.salaryHistory = employee.getSalaryHistory().stream()
                    .map(EmployeeSalaryHistoryDTO::new)
                    .collect(java.util.stream.Collectors.toList());
        }
    }

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

    public Double getEsiPercent() {
        return esiPercent;
    }

    public void setEsiPercent(Double esiPercent) {
        this.esiPercent = esiPercent;
    }

    private Integer parseIntegerFromObject(Object val) {
        if (val == null) {
            return 0;
        }
        if (val instanceof Boolean) {
            return ((Boolean) val) ? 1 : 0;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        if (val instanceof String) {
            String str = ((String) val).trim();
            if (str.isEmpty()) return 0;
            return Boolean.parseBoolean(str) || "1".equals(str) || "true".equalsIgnoreCase(str) ? 1 : 0;
        }
        return 0;
    }

    public Integer getIsRelieved() {
        return isRelieved;
    }

    public void setIsRelieved(Object val) {
        this.isRelieved = parseIntegerFromObject(val);
    }

    public Integer getIsAllowance() {
        return isAllowance;
    }

    public void setIsAllowance(Object val) {
        this.isAllowance = parseIntegerFromObject(val);
    }

    public Integer getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(Object val) {
        this.isAdvance = parseIntegerFromObject(val);
    }

    public Integer getProductionIncentiveApplicable() {
        return productionIncentiveApplicable;
    }

    public void setProductionIncentiveApplicable(Object val) {
        this.productionIncentiveApplicable = parseIntegerFromObject(val);
    }

    public Integer getAttendanceIncentiveApplicable() {
        return attendanceIncentiveApplicable;
    }

    public void setAttendanceIncentiveApplicable(Object val) {
        this.attendanceIncentiveApplicable = parseIntegerFromObject(val);
    }

    public Integer getFoodAllowanceApplicable() {
        return foodAllowanceApplicable;
    }

    public void setFoodAllowanceApplicable(Object val) {
        this.foodAllowanceApplicable = parseIntegerFromObject(val);
    }

    public Integer getFestivalWagesApplicable() {
        return festivalWagesApplicable;
    }

    public void setFestivalWagesApplicable(Object val) {
        this.festivalWagesApplicable = parseIntegerFromObject(val);
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

    public EmploymentDetailsDTO getEmploymentDetails() {
        return employmentDetails;
    }

    public void setEmploymentDetails(EmploymentDetailsDTO employmentDetails) {
        this.employmentDetails = employmentDetails;
    }

    public SalaryDetailsDTO getSalaryDetails() {
        return salaryDetails;
    }

    public void setSalaryDetails(SalaryDetailsDTO salaryDetails) {
        this.salaryDetails = salaryDetails;
    }

    public ShiftDetailsDTO getShiftDetails() {
        return shiftDetails;
    }

    public void setShiftDetails(ShiftDetailsDTO shiftDetails) {
        this.shiftDetails = shiftDetails;
    }

    public BankDetailsDTO getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetailsDTO bankDetails) {
        this.bankDetails = bankDetails;
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

    public java.util.List<EmployeeSalaryHistoryDTO> getSalaryHistory() {
        return salaryHistory;
    }

    public void setSalaryHistory(java.util.List<EmployeeSalaryHistoryDTO> salaryHistory) {
        this.salaryHistory = salaryHistory;
    }

    public Integer getHolidayDeductionApplicable() {
        return holidayDeductionApplicable;
    }

    public void setHolidayDeductionApplicable(Object val) {
        if (val instanceof Boolean) {
            this.holidayDeductionApplicable = ((Boolean) val) ? 1 : 0;
        } else if (val instanceof Number) {
            this.holidayDeductionApplicable = ((Number) val).intValue();
        } else if (val instanceof String) {
            this.holidayDeductionApplicable = Boolean.parseBoolean((String) val) || "1".equals(val) || "true".equalsIgnoreCase((String) val) ? 1 : 0;
        } else {
            this.holidayDeductionApplicable = 0;
        }
    }

    public Double getPfWageCeiling() {
        return pfWageCeiling;
    }

    public void setPfWageCeiling(Double pfWageCeiling) {
        this.pfWageCeiling = pfWageCeiling;
    }

    public java.util.List<EmployeePfWageCeilingHistoryDTO> getPfWageCeilingHistory() {
        return pfWageCeilingHistory;
    }

    public void setPfWageCeilingHistory(java.util.List<EmployeePfWageCeilingHistoryDTO> pfWageCeilingHistory) {
        this.pfWageCeilingHistory = pfWageCeilingHistory;
    }
}
