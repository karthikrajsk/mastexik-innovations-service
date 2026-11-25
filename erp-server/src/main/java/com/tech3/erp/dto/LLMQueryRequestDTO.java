package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class LLMQueryRequestDTO {
    
    @JsonProperty("reportName")
    @NotBlank(message = "Report name is required")
    private String reportName;
    
    @JsonProperty("columns")
    @NotNull(message = "Columns list cannot be null")
    @NotEmpty(message = "Columns list cannot be empty")
    private List<ReportColumnDTO> columns;
    
    public LLMQueryRequestDTO() {
    }
    
    public LLMQueryRequestDTO(String reportName, List<ReportColumnDTO> columns) {
        this.reportName = reportName;
        this.columns = columns;
    }
    
    public String getReportName() {
        return reportName;
    }
    
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    public List<ReportColumnDTO> getColumns() {
        return columns;
    }
    
    public void setColumns(List<ReportColumnDTO> columns) {
        this.columns = columns;
    }
    
    @Override
    public String toString() {
        return "LLMQueryRequestDTO{" +
                "reportName='" + reportName + '\'' +
                ", columns=" + columns +
                '}';
    }
}