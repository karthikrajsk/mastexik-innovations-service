package com.tech3.erp.dto;

import com.tech3.erp.entity.BrushingStock;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BrushingStockDTO {
    private Long id;
    private LocalDate date;
    private String partyName;
    private String hoInvoiceNo;
    private String tc;
    private String construction;
    private String weave;
    private String rollNo;
    private BigDecimal receivingMtr;
    private BigDecimal grossWt;
    private BigDecimal netWt;
    private BigDecimal passMtr;
    private BigDecimal glm;
    private LocalDate rollingDate;
    private String hfplRollNo;
    private BigDecimal variation;
    private BigDecimal variationPercentage;
    private LocalDate dispatchDate;
    private String hfplInvoiceNo;
    private String hfplDcNo;
    private String poNo;
    private String remarks;
    private String fabricStatus;
    private Long companyId;

    public BrushingStockDTO() {}

    public BrushingStockDTO(BrushingStock entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.date = entity.getDate();
            this.partyName = entity.getPartyName();
            this.hoInvoiceNo = entity.getHoInvoiceNo();
            this.tc = entity.getTc();
            this.construction = entity.getConstruction();
            this.weave = entity.getWeave();
            this.rollNo = entity.getRollNo();
            this.receivingMtr = entity.getReceivingMtr();
            this.grossWt = entity.getGrossWt();
            this.netWt = entity.getNetWt();
            this.passMtr = entity.getPassMtr();
            this.glm = entity.getGlm();
            this.rollingDate = entity.getRollingDate();
            this.hfplRollNo = entity.getHfplRollNo();
            this.variation = entity.getVariation();
            this.variationPercentage = entity.getVariationPercentage();
            this.dispatchDate = entity.getDispatchDate();
            this.hfplInvoiceNo = entity.getHfplInvoiceNo();
            this.hfplDcNo = entity.getHfplDcNo();
            this.poNo = entity.getPoNo();
            this.remarks = entity.getRemarks();
            this.fabricStatus = entity.getFabricStatus();
            this.companyId = entity.getCompanyId();
        }
    }

    public BrushingStock toEntity() {
        BrushingStock entity = new BrushingStock();
        entity.setId(this.id);
        entity.setDate(this.date);
        entity.setPartyName(this.partyName);
        entity.setHoInvoiceNo(this.hoInvoiceNo);
        entity.setTc(this.tc);
        entity.setConstruction(this.construction);
        entity.setWeave(this.weave);
        entity.setRollNo(this.rollNo);
        entity.setReceivingMtr(this.receivingMtr);
        entity.setGrossWt(this.grossWt);
        entity.setNetWt(this.netWt);
        entity.setPassMtr(this.passMtr);
        entity.setGlm(this.glm);
        entity.setRollingDate(this.rollingDate);
        entity.setHfplRollNo(this.hfplRollNo);
        entity.setVariation(this.variation);
        entity.setVariationPercentage(this.variationPercentage);
        entity.setDispatchDate(this.dispatchDate);
        entity.setHfplInvoiceNo(this.hfplInvoiceNo);
        entity.setHfplDcNo(this.hfplDcNo);
        entity.setPoNo(this.poNo);
        entity.setRemarks(this.remarks);
        entity.setFabricStatus(this.fabricStatus);
        entity.setCompanyId(this.companyId);
        return entity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getHoInvoiceNo() {
        return hoInvoiceNo;
    }

    public void setHoInvoiceNo(String hoInvoiceNo) {
        this.hoInvoiceNo = hoInvoiceNo;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getWeave() {
        return weave;
    }

    public void setWeave(String weave) {
        this.weave = weave;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public BigDecimal getReceivingMtr() {
        return receivingMtr;
    }

    public void setReceivingMtr(BigDecimal receivingMtr) {
        this.receivingMtr = receivingMtr;
    }

    public BigDecimal getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(BigDecimal grossWt) {
        this.grossWt = grossWt;
    }

    public BigDecimal getNetWt() {
        return netWt;
    }

    public void setNetWt(BigDecimal netWt) {
        this.netWt = netWt;
    }

    public BigDecimal getPassMtr() {
        return passMtr;
    }

    public void setPassMtr(BigDecimal passMtr) {
        this.passMtr = passMtr;
    }

    public BigDecimal getGlm() {
        return glm;
    }

    public void setGlm(BigDecimal glm) {
        this.glm = glm;
    }

    public LocalDate getRollingDate() {
        return rollingDate;
    }

    public void setRollingDate(LocalDate rollingDate) {
        this.rollingDate = rollingDate;
    }

    public String getHfplRollNo() {
        return hfplRollNo;
    }

    public void setHfplRollNo(String hfplRollNo) {
        this.hfplRollNo = hfplRollNo;
    }

    public BigDecimal getVariation() {
        return variation;
    }

    public void setVariation(BigDecimal variation) {
        this.variation = variation;
    }

    public BigDecimal getVariationPercentage() {
        return variationPercentage;
    }

    public void setVariationPercentage(BigDecimal variationPercentage) {
        this.variationPercentage = variationPercentage;
    }

    public LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getHfplInvoiceNo() {
        return hfplInvoiceNo;
    }

    public void setHfplInvoiceNo(String hfplInvoiceNo) {
        this.hfplInvoiceNo = hfplInvoiceNo;
    }

    public String getHfplDcNo() {
        return hfplDcNo;
    }

    public void setHfplDcNo(String hfplDcNo) {
        this.hfplDcNo = hfplDcNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFabricStatus() {
        return fabricStatus;
    }

    public void setFabricStatus(String fabricStatus) {
        this.fabricStatus = fabricStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
