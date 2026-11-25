package com.tech3.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "number_format_configurations", schema = "masters")
public class NumberFormatConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen_name", nullable = false, length = 255)
    @NotNull
    @Size(min = 1, max = 255)
    private String screenName;

    @Column(name = "tab_name", length = 255)
    @Size(max = 255)
    private String tabName;

    @Column(name = "prefix", nullable = false, length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String prefix;

    @Column(name = "financial_year", nullable = false, length = 10)
    @NotNull
    @Size(min = 1, max = 10)
    private String financialYear;

    @Column(name = "sequence_number", nullable = false, length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String sequenceNumber;

    @Column(name = "separator", nullable = false, length = 5)
    @NotNull
    @Size(min = 1, max = 5)
    private String separator;

    @Column(name = "format_preview", nullable = false, length = 500)
    @NotNull
    @Size(max = 500)
    private String formatPreview;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

    @Column(name = "company_id")
    private Long companyId;

    // Default constructor
    public NumberFormatConfiguration() {
    }

    // Constructor with parameters
    public NumberFormatConfiguration(Long id, String screenName, String tabName, String prefix,
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