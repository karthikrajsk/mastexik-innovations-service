package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "brushing_stock", schema = "masters")
public class BrushingStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date")
    private LocalDate date;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "ho_invoice_no")
    private String hoInvoiceNo;

    @Column(name = "tc")
    private String tc;

    @Column(name = "construction")
    private String construction;

    @Column(name = "weave")
    private String weave;

    @Column(name = "roll_no")
    private String rollNo;

    @Column(name = "receiving_mtr", precision = 19, scale = 3)
    private BigDecimal receivingMtr = BigDecimal.ZERO;

    @Column(name = "gross_wt", precision = 19, scale = 3)
    private BigDecimal grossWt = BigDecimal.ZERO;

    @Column(name = "net_wt", precision = 19, scale = 3)
    private BigDecimal netWt = BigDecimal.ZERO;

    @Column(name = "pass_mtr", precision = 19, scale = 3)
    private BigDecimal passMtr = BigDecimal.ZERO;

    @Column(name = "glm", precision = 19, scale = 3)
    private BigDecimal glm = BigDecimal.ZERO;

    @Column(name = "rolling_date")
    private LocalDate rollingDate;

    @Column(name = "hfpl_roll_no")
    private String hfplRollNo;

    @Column(name = "variation", precision = 19, scale = 3)
    private BigDecimal variation = BigDecimal.ZERO;

    @Column(name = "variation_percentage", precision = 19, scale = 3)
    private BigDecimal variationPercentage = BigDecimal.ZERO;

    @Column(name = "dispatch_date")
    private LocalDate dispatchDate;

    @Column(name = "hfpl_invoice_no")
    private String hfplInvoiceNo;

    @Column(name = "hfpl_dc_no")
    private String hfplDcNo;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "fabric_status")
    private String fabricStatus;

    @Column(name = "company_id")
    private Long companyId;

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
