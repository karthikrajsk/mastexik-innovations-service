package com.tech3.erp.dto;

import java.util.List;
import java.util.Map;

public class ExecuteQueryResponseDTO {
    private Long savedId; // ID of LLMQueryResult row
    private List<Map<String, Object>> rows; // Executed result

    public ExecuteQueryResponseDTO() {}

    public ExecuteQueryResponseDTO(Long savedId, List<Map<String, Object>> rows) {
        this.savedId = savedId;
        this.rows = rows;
    }

    public Long getSavedId() {
        return savedId;
    }

    public void setSavedId(Long savedId) {
        this.savedId = savedId;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}