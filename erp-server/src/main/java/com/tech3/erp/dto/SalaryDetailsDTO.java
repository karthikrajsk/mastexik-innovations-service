package com.tech3.erp.dto;

import com.tech3.erp.entity.SalaryDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SalaryDetailsDTO {
    private Long id;

    private String salaryStructure;

    private String salaryType;

    private BigDecimal basicSalary;

    private BigDecimal hra;
    private BigDecimal conveyanceAllowance;
    private BigDecimal medicalAllowance;
    private BigDecimal specialAllowance;
    private BigDecimal incentive;
    private BigDecimal perHourSalary;
    private BigDecimal otRatePerHour;
    private BigDecimal otSalary;
    private BigDecimal productionIncentive;
    private Integer isCanteen;
    private String effectiveDate;

    public SalaryDetailsDTO() {}

    public SalaryDetailsDTO(SalaryDetails entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.salaryStructure = entity.getSalaryStructure();
            this.salaryType = entity.getSalaryType();
            this.basicSalary = entity.getBasicSalary();
            this.hra = entity.getHra();
            this.conveyanceAllowance = entity.getConveyanceAllowance();
            this.medicalAllowance = entity.getMedicalAllowance();
            this.specialAllowance = entity.getSpecialAllowance();
            this.incentive = entity.getIncentive();
            this.perHourSalary = entity.getPerHourSalary();
            this.otRatePerHour = entity.getOtRatePerHour();
            this.otSalary = entity.getOtSalary();
            this.productionIncentive = entity.getProductionIncentive();
            this.isCanteen = entity.getIsCanteen();
            if (entity.getEffectiveDate() != null) {
                this.effectiveDate = entity.getEffectiveDate().toString();
            }
        }
    }

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

    public BigDecimal getProductionIncentive() {
        return productionIncentive;
    }

    public void setProductionIncentive(BigDecimal productionIncentive) {
        this.productionIncentive = productionIncentive;
    }

    public Integer getIsCanteen() {
        return isCanteen;
    }

    public void setIsCanteen(Object val) {
        if (val == null) {
            this.isCanteen = 0;
        } else if (val instanceof Boolean) {
            this.isCanteen = ((Boolean) val) ? 1 : 0;
        } else if (val instanceof Number) {
            this.isCanteen = ((Number) val).intValue();
        } else if (val instanceof String) {
            String str = ((String) val).trim();
            this.isCanteen = Boolean.parseBoolean(str) || "1".equals(str) || "true".equalsIgnoreCase(str) ? 1 : 0;
        } else {
            this.isCanteen = 0;
        }
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
