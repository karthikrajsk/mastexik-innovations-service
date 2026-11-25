package com.tech3.erp.dto;

import java.math.BigDecimal;

import com.tech3.erp.entity.FabricTestingDetail;


public class FabricTestingDetailDTO {
	
	private Long id;
    private String testingParameter;
    private String stdValue;

    // Default constructor
    public FabricTestingDetailDTO() {}

    // Constructor using entity
    public FabricTestingDetailDTO(FabricTestingDetail entity) {
        this.id = entity.getId();
        this.testingParameter = entity.getTestingParameter();
        this.stdValue = entity.getStdValue();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestingParameter() {
        return testingParameter;
    }

    public void setTestingParameter(String testingParameter) {
        this.testingParameter = testingParameter;
    }

    public String getStdValue() {
        return stdValue;
    }

    public void setStdValue(String stdValue) {
        this.stdValue = stdValue;
    }


}
