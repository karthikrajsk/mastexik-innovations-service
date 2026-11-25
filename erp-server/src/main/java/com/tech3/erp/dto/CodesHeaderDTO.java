package com.tech3.erp.dto;

import com.tech3.erp.entity.CodesHeader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CodesHeaderDTO {
    private Long id;
    private String codeGroup;
    private String description;
    private List<CodeDetailSimpleDTO> details;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CodesHeaderDTO() {
    }

    public CodesHeaderDTO(Long id, String codeGroup, String description, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.codeGroup = codeGroup;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CodesHeaderDTO(CodesHeader entity) {
        this.id = entity.getId();
        this.codeGroup = entity.getCodeGroup();
        this.description = entity.getDescription();
        this.details = entity.getDetails() != null ? 
                entity.getDetails().stream()
                    .map(CodeDetailSimpleDTO::new)
                    .collect(Collectors.toList()) : null;
        this.isActive = entity.getIsActive();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CodeDetailSimpleDTO> getDetails() {
        return details;
    }

    public void setDetails(List<CodeDetailSimpleDTO> details) {
        this.details = details;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
}
