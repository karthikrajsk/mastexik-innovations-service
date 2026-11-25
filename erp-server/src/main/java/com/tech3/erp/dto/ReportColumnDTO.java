package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.ReportColumn;

public class ReportColumnDTO {

    private Long id;
    
    @JsonProperty("columnName")
    private String columnName;
    
    @JsonProperty("screenName")
    private String screenName;
    
    @JsonProperty("sectionName")
    private String sectionName;
    
    @JsonProperty("fieldName")
    private String fieldName;
    
    @JsonProperty("tableName")
    private String tableName;
    
    @JsonProperty("databaseColumnName")
    private String databaseColumnName;
    
    @JsonProperty("dataType")
    private String dataType;
    
    @JsonProperty("isFormulaApplied")
    private Boolean isFormulaApplied;
    
    @JsonProperty("formulaType")
    private String formulaType;
    
    @JsonProperty("formulaBar")
    private String formulaBar;
    
    @JsonProperty("isFilterRequired")
    private Boolean isFilterRequired;
    
    @JsonProperty("filterType")
    private String filterType;
    
    private Integer columnOrder;

    public ReportColumnDTO() {
    }

    public ReportColumnDTO(ReportColumn entity) {
        this.id = entity.getId();
        this.columnName = entity.getColumnName();
        this.screenName = entity.getScreenName();
        this.sectionName = entity.getSectionName();
        this.fieldName = entity.getFieldName();
        this.tableName = entity.getTableName();
        this.databaseColumnName = entity.getDatabaseColumnName();
        this.dataType = entity.getDataType();
        this.isFormulaApplied = entity.getIsFormulaApplied();
        this.formulaType = entity.getFormulaType();
        this.formulaBar = entity.getFormulaBar();
        this.isFilterRequired = entity.getIsFilterRequired();
        this.filterType = entity.getFilterType();
        this.columnOrder = entity.getColumnOrder();
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    @Override
    public String toString() {
        return "ReportColumnDTO{" +
                "id=" + id +
                ", columnName='" + columnName + '\'' +
                ", screenName='" + screenName + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", databaseColumnName='" + databaseColumnName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", isFormulaApplied=" + isFormulaApplied +
                ", formulaType='" + formulaType + '\'' +
                ", formulaBar='" + formulaBar + '\'' +
                ", isFilterRequired=" + isFilterRequired +
                ", filterType='" + filterType + '\'' +
                ", columnOrder=" + columnOrder +
                '}';
    }
}
