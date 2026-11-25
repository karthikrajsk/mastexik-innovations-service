package com.tech3.erp.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LLMQueryResponseDTO {
   
    private String generatedQuery;
    private boolean success;
    private String errorMessage;
   
    // Explicit all-args constructor to resolve compilation issues (in case Lombok @AllArgsConstructor isn't processing)
    public LLMQueryResponseDTO(String generatedQuery, boolean success, String errorMessage) {
        this.generatedQuery = generatedQuery;
        this.success = success;
        this.errorMessage = errorMessage;
    }
   
    // Explicit getters to resolve compilation issues (in case Lombok @Data isn't processing)
    public String getGeneratedQuery() {
        return generatedQuery;
    }
   
    public void setGeneratedQuery(String generatedQuery) {
        this.generatedQuery = generatedQuery;
    }
   
    public boolean isSuccess() {
        return success;
    }
   
    public void setSuccess(boolean success) {
        this.success = success;
    }
   
    public String getErrorMessage() {
        return errorMessage;
    }
   
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
   
    public static LLMQueryResponseDTO success(String query) {
        return new LLMQueryResponseDTO(query, true, null);
    }
   
    public static LLMQueryResponseDTO error(String errorMessage) {
        return new LLMQueryResponseDTO(null, false, errorMessage);
    }
}