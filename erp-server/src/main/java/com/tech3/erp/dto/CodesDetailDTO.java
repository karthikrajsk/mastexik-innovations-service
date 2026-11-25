package com.tech3.erp.dto;

public class CodesDetailDTO {
    private Long id;
    private String code;
    private String value;
    private Integer sortOrder;
    private Boolean isActive;

    public CodesDetailDTO() {}

    public CodesDetailDTO(Long id, String code, String value, Integer sortOrder, Boolean isActive) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
