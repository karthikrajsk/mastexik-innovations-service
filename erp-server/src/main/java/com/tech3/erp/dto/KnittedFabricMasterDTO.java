package com.tech3.erp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tech3.erp.entity.FabricType;
import com.tech3.erp.entity.KnittedFabricMaster;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

public class KnittedFabricMasterDTO implements Serializable {

    private Long id;
    private Short fabricTypeId;
    private Short fabricCategoryId;
    private Long knittedFabricId;
    private String fabricCode;
    private String fabricName;
    private BigDecimal gsm;
    private String width;
    private String composition;
    private String shrinkage ;
    private String remarks;
    
    private Short taxType;
    private BigDecimal gstPercent;
    private BigDecimal igst;
    private BigDecimal cgst;
    private BigDecimal sgst;
    
    // Default Constructor
    public KnittedFabricMasterDTO() {}

    // Constructor using FabricMaster entity
    public KnittedFabricMasterDTO(KnittedFabricMaster entity) {
        this.id = entity.getId();
        this.fabricTypeId = entity.getFabricType();
        this.fabricCategoryId = entity.getFabricCategoryId();
        this.knittedFabricId = entity.getKnittedFabricId();
        this.fabricCode = entity.getFabricCode();
        this.fabricName = entity.getFabricName();
        this.gsm = entity.getGsm();
        this.width = entity.getWidth();
        this.composition = entity.getComposition();
        this.shrinkage = entity.getShrinkage();
        this.remarks = entity.getRemarks();
    
        this.taxType = entity.getTaxType();
        this.gstPercent = entity.getGstPercent();
        this.igst = entity.getIgst();
        this.cgst = entity.getCgst();
        this.sgst = entity.getSgst();

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getFabricType() {
        return fabricTypeId;
    }
    
    public void setFabricType(Short fabricType) {
        this.fabricTypeId = fabricType;
    }
    
    public Short getFabricCategoryId() {
        return fabricCategoryId;
    }
    
    public void setFabricCategoryId(Short fabricCategoryId) {
        this.fabricCategoryId = fabricCategoryId;
    }

    public void setKnittedFabricId(Long knittedFabricId) {
        this.knittedFabricId = knittedFabricId;
    }
    
    public Long getKnittedFabricId() {
        return knittedFabricId;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getFabricName() {
        return fabricName;
    }

    public void setFabricName(String fabricName) {
        this.fabricName = fabricName;
    }

    public BigDecimal getGsm() {
        return gsm;
    }

    public void setGsm(BigDecimal gsm) {
        this.gsm = gsm;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(String shrinkage) {
        this.shrinkage = shrinkage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

