package com.tech3.erp.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.YarnMaster;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class YarnMasterDTO {
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String yarnName;

    @NotNull
    private Short countSno;

    private Short unitSno;

    @NotNull
    @Size(min = 1, max = 50)
    private String types;
    
    @Size(max = 50)
    private String content;

    private Short colorSno;

    @NotNull
    private Double conversion;

    private boolean activeFlag;
    
    private Short taxType;
    private BigDecimal gstPercent;
    private BigDecimal igst;
    private BigDecimal cgst;
    private BigDecimal sgst;
    
    private Short plySno;
    @Size(max = 50)
    private String filaments;

    // Default Constructor
    public YarnMasterDTO() {}

    // Constructor with parameters
    public YarnMasterDTO(Long id, String yarnName, Short countSno, Short unitSno,  
    		String types, Double conversion, boolean activeFlag, String content, Short colorSno,  Short plySno, String filaments) {
        this.id = id;
        this.yarnName = yarnName;
        this.countSno = countSno;
        this.unitSno = unitSno;
        this.types = types;
        this.conversion = conversion;
        this.activeFlag = activeFlag;
        this.content = content;
        this.colorSno = colorSno;
        this.plySno = plySno;
        this.filaments = filaments;
    }

    // Constructor to convert from entity to DTO
    public YarnMasterDTO(YarnMaster yarnMaster) {
        this.id = yarnMaster.getId();
        this.yarnName = yarnMaster.getYarnName();
        this.countSno = yarnMaster.getCountSno();
        this.unitSno = yarnMaster.getUnitSno();
        this.colorSno = yarnMaster.getColorSno();
        this.types = yarnMaster.getTypes();
        this.conversion = yarnMaster.getConversion();
        this.activeFlag = yarnMaster.getActiveFlag();
        this.content = yarnMaster.getContent();
   
        this.taxType = yarnMaster.getTaxType();
        this.gstPercent = yarnMaster.getGstPercent();
        this.igst = yarnMaster.getIgst();
        this.cgst = yarnMaster.getCgst();
        this.sgst = yarnMaster.getSgst();
        
        
        this.plySno = yarnMaster.getPlySno();
        this.filaments = yarnMaster.getFilaments();
        
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Short getCountSno() {
        return countSno;
    }

    public void setCountSno(Short countSno) {
        this.countSno = countSno;
    }

    public Short getUnitSno() {
        return unitSno;
    }

    public void setUnitSno(Short unitSno) {
        this.unitSno = unitSno;
    }
    
    public Short getColorSno() {
        return colorSno;
    }
    
    public void setColorSno(Short colorSno) {
        this.colorSno = colorSno;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Double getConversion() {
        return conversion;
    }

    public void setConversion(Double conversion) {
        this.conversion = conversion;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public Short getPlySno() {
        return plySno;
    }

    public void setPlySno(Short plySno) {
        this.plySno = plySno;
    }

    public String getFilaments() {
        return filaments;
    }

    public void setFilaments(String filaments) {
        this.filaments = filaments;
    }
    
    
    public Short getTaxType() { return taxType; }
    
    public void setTaxType(Short taxType) { this.taxType = taxType; }
    
    public BigDecimal getGstPercent() { return gstPercent; }
    
    public void setGstPercent(BigDecimal gstPercent) { this.gstPercent = gstPercent; }

    public BigDecimal getIgst() {
        return igst;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }
}
