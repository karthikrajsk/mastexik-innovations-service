package com.tech3.erp.dto;

import com.tech3.erp.entity.EmploymentDetails;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class EmploymentDetailsDTO {
    private Long id;

    private String department;

    private String designation;

    private String qualification;

    private LocalDate joiningDate;

    private String employeeType;

    private String status;

    private String hostelFamilyQuarters;

    private String hostelFamilyQuartersRoomNo;

    private Integer isFestivalWageApplicable = 0;

    private Integer isHolidayDeductionApplicable = 0;

    private Double houseRent;

    public EmploymentDetailsDTO() {}

    public EmploymentDetailsDTO(EmploymentDetails entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.department = entity.getDepartment();
            this.designation = entity.getDesignation();
            this.qualification = entity.getQualification();
            this.joiningDate = entity.getJoiningDate();
            this.employeeType = entity.getEmployeeType();
            this.status = entity.getStatus();
            this.hostelFamilyQuarters = entity.getHostelFamilyQuarters();
            this.hostelFamilyQuartersRoomNo = entity.getHostelFamilyQuartersRoomNo();
            this.isFestivalWageApplicable = entity.getIsFestivalWageApplicable();
            this.isHolidayDeductionApplicable = entity.getIsHolidayDeductionApplicable();
            this.houseRent = entity.getHouseRent();
        }
    }

    public Integer getIsHolidayDeductionApplicable() {
        return isHolidayDeductionApplicable;
    }

    public void setIsHolidayDeductionApplicable(Object val) {
        if (val instanceof Boolean) {
            this.isHolidayDeductionApplicable = ((Boolean) val) ? 1 : 0;
        } else if (val instanceof Number) {
            this.isHolidayDeductionApplicable = ((Number) val).intValue();
        } else if (val instanceof String) {
            this.isHolidayDeductionApplicable = Boolean.parseBoolean((String) val) || "1".equals(val) || "true".equalsIgnoreCase((String) val) ? 1 : 0;
        } else {
            this.isHolidayDeductionApplicable = 0;
        }
    }

    public Integer getIsFestivalWageApplicable() {
        return isFestivalWageApplicable;
    }

    public void setIsFestivalWageApplicable(Object val) {
        if (val instanceof Boolean) {
            this.isFestivalWageApplicable = ((Boolean) val) ? 1 : 0;
        } else if (val instanceof Number) {
            this.isFestivalWageApplicable = ((Number) val).intValue();
        } else if (val instanceof String) {
            this.isFestivalWageApplicable = Boolean.parseBoolean((String) val) || "1".equals(val) || "true".equalsIgnoreCase((String) val) ? 1 : 0;
        } else {
            this.isFestivalWageApplicable = 0;
        }
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
