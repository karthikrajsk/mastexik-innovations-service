package com.tech3.erp.dto;

import jakarta.validation.constraints.NotBlank;

public class ExecuteQueryRequestDTO {

    // Optional: when provided, update existing LLMQueryResult instead of creating a new one
    private Long id;

    @NotBlank
    private String sql;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}