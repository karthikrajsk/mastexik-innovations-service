package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "module_field_mapping", schema = "masters")
public class ModuleFieldMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Column(name = "field_label", nullable = false)
    private String fieldLabel;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "table_schema")
    private String tableSchema;

    @Column(name = "database_column_name", nullable = false)
    private String databaseColumnName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "is_relation", nullable = false)
    private Boolean isRelation = false;

    @Column(name = "relation_table_name")
    private String relationTableName;

    @Column(name = "relation_column_name")
    private String relationColumnName;

    @Column(name = "relation_display_column")
    private String relationDisplayColumn;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

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