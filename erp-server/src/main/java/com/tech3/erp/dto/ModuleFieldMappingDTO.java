package com.tech3.erp.dto;

import com.tech3.erp.entity.ModuleFieldMapping;

public class ModuleFieldMappingDTO {
    private Long id;
    private String moduleName;
    private String sectionName;
    private String fieldName;
    private String fieldLabel;
    private String tableName;
    private String tableSchema;
    private String databaseColumnName;
    private String dataType;
    private Boolean isRelation;
    private String relationTableName;
    private String relationColumnName;
    private String relationDisplayColumn;
    private Boolean activeFlag;

    public ModuleFieldMappingDTO() {
    }

    public ModuleFieldMappingDTO(ModuleFieldMapping entity) {
        this.id = entity.getId();
        this.moduleName = entity.getModuleName();
        this.sectionName = entity.getSectionName();
        this.fieldName = entity.getFieldName();
        this.fieldLabel = entity.getFieldLabel();
        this.tableName = entity.getTableName();
        this.tableSchema = entity.getTableSchema();
        this.databaseColumnName = entity.getDatabaseColumnName();
        this.dataType = entity.getDataType();
        this.isRelation = entity.getIsRelation();
        this.relationTableName = entity.getRelationTableName();
        this.relationColumnName = entity.getRelationColumnName();
        this.relationDisplayColumn = entity.getRelationDisplayColumn();
        this.activeFlag = entity.getActiveFlag();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
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

    public Boolean getIsRelation() {
        return isRelation;
    }

    public void setIsRelation(Boolean isRelation) {
        this.isRelation = isRelation;
    }

    public String getRelationTableName() {
        return relationTableName;
    }

    public void setRelationTableName(String relationTableName) {
        this.relationTableName = relationTableName;
    }

    public String getRelationColumnName() {
        return relationColumnName;
    }

    public void setRelationColumnName(String relationColumnName) {
        this.relationColumnName = relationColumnName;
    }

    public String getRelationDisplayColumn() {
        return relationDisplayColumn;
    }

    public void setRelationDisplayColumn(String relationDisplayColumn) {
        this.relationDisplayColumn = relationDisplayColumn;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}