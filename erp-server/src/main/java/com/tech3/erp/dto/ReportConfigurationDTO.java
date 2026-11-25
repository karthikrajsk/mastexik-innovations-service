package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.ReportConfiguration;
import com.tech3.erp.util.JsonUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportConfigurationDTO {

    private Long id;
    private String reportName;
    private Long companyId;
    private List<ReportColumnDTO> columns;
    private String templateContent;
    private String queryContent;
    @JsonProperty("query_data")
    private Object queryData;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean activeFlag;

    public ReportConfigurationDTO() {
    }

    public ReportConfigurationDTO(ReportConfiguration entity) {
        this.id = entity.getId();
        this.reportName = entity.getReportName();
        this.companyId = entity.getCompanyId();
        this.columns = entity.getColumns()
                .stream()
                .map(ReportColumnDTO::new)
                .collect(Collectors.toList());
        this.templateContent = entity.getTemplateContent();
        this.queryContent = entity.getQueryContent();
        this.queryData = JsonUtils.fromJson(entity.getQueryData());
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.activeFlag = entity.getActiveFlag();
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<ReportColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(List<ReportColumnDTO> columns) {
        this.columns = columns;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }

    public Object getQueryData() {
        return queryData;
    }

    public void setQueryData(Object queryData) {
        this.queryData = queryData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
