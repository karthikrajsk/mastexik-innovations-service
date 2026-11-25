package com.tech3.erp.dto;

import com.tech3.erp.entity.CodesDetail;

public class CodeDetailSimpleDTO {
    private Long id;
    private String code;
    private String value;

    public CodeDetailSimpleDTO() {
    }

    public CodeDetailSimpleDTO(Long id, String code, String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }

    public CodeDetailSimpleDTO(CodesDetail entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.value = entity.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
