package com.tech3.erp.dto;

import com.tech3.erp.entity.NumberFormatConfiguration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NumberFormatConfigurationDTO {
    private Long id;

    @NotBlank(message = "Screen name is required")
    @Size(min = 1, max = 255, message = "Screen name must be between 1 and 255 characters")
    private String screenName;

    @Size(max = 255, message = "Tab name must not exceed 255 characters")
    private String tabName;

    @NotBlank(message = "Prefix is required")
    @Size(min = 1, max = 50, message = "Prefix must be between 1 and 50 characters")
    private String prefix;

    @NotBlank(message = "Financial year is required")
    @Size(min = 1, max = 10, message = "Financial year must be between 1 and 10 characters")
    private String financialYear;

    @NotBlank(message = "Sequence number is required")
    @Size(min = 1, max = 20, message = "Sequence number must be between 1 and 20 characters")
    private String sequenceNumber;

    @NotBlank(message = "Separator is required")
    @Size(min = 1, max = 5, message = "Separator must be between 1 and 5 characters")
    private String separator;

    private String formatPreview;

    private boolean activeFlag = true;

    private Long companyId;

    // Default constructor
    public NumberFormatConfigurationDTO() {
    }

    // Constructor with parameters
    public NumberFormatConfigurationDTO(Long id, String screenName, String tabName, String prefix,
                                       String financialYear, String sequenceNumber, String separator,
                                       String formatPreview, boolean activeFlag, Long companyId) {
        this.id = id;
        this.screenName = screenName;
        this.tabName = tabName;
        this.prefix = prefix;
        this.financialYear = financialYear;
        this.sequenceNumber = sequenceNumber;
        this.separator = separator;
        this.formatPreview = formatPreview;
        this.activeFlag = activeFlag;
        this.companyId = companyId;
    }

    // Constructor to convert from entity to DTO
    public NumberFormatConfigurationDTO(NumberFormatConfiguration configuration) {
        this.id = configuration.getId();
        this.screenName = configuration.getScreenName();
        this.tabName = configuration.getTabName();
        this.prefix = configuration.getPrefix();
        this.financialYear = configuration.getFinancialYear();
        this.sequenceNumber = configuration.getSequenceNumber();
        this.separator = configuration.getSeparator();
        this.formatPreview = configuration.getFormatPreview();
        this.activeFlag = configuration.getActiveFlag();
        this.companyId = configuration.getCompanyId();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getFormatPreview() {
        return formatPreview;
    }

    public void setFormatPreview(String formatPreview) {
        this.formatPreview = formatPreview;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}