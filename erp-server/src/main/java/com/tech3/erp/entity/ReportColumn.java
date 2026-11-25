package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_column", schema = "masters")
public class ReportColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_configuration_id", nullable = false)
    private ReportConfiguration reportConfiguration;

    @Column(name = "column_name", nullable = false)
    private String columnName;

    @Column(name = "screen_name", nullable = false)
    private String screenName;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "database_column_name")
    private String databaseColumnName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "is_formula_applied", nullable = false)
    private Boolean isFormulaApplied = false;

    @Column(name = "formula_type")
    private String formulaType;

    @Column(name = "formula_bar", columnDefinition = "TEXT")
    private String formulaBar;

    @Column(name = "is_filter_required", nullable = false)
    private Boolean isFilterRequired = false;

    @Column(name = "filter_type")
    private String filterType;

    @Column(name = "column_order")
    private Integer columnOrder;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportConfiguration getReportConfiguration() {
        return reportConfiguration;
    }

    public void setReportConfiguration(ReportConfiguration reportConfiguration) {
        this.reportConfiguration = reportConfiguration;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatabaseColumnName() {
        return databaseColumnName;
    }

    public void setDatabaseColumnName(String databaseColumnName) {
        this.databaseColumnName = databaseColumnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Boolean getIsFormulaApplied() {
        return isFormulaApplied;
    }

    public void setIsFormulaApplied(Boolean isFormulaApplied) {
        this.isFormulaApplied = isFormulaApplied;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

    public String getFormulaBar() {
        return formulaBar;
    }

    public void setFormulaBar(String formulaBar) {
        this.formulaBar = formulaBar;
    }

    public Boolean getIsFilterRequired() {
        return isFilterRequired;
    }

    public void setIsFilterRequired(Boolean isFilterRequired) {
        this.isFilterRequired = isFilterRequired;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public Integer getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(Integer columnOrder) {
        this.columnOrder = columnOrder;
    }
}