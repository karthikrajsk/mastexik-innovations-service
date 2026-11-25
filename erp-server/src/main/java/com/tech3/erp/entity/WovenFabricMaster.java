package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "woven_fabric_master", schema = "masters")
public class WovenFabricMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Short fabricTypeId;
    @Column(name = "fabric_category_id", nullable = true)
    private Short fabricCategoryId;
    private Long wovenFabricId;
    private String fabricCode;
    @Column(name = "fabric_name", nullable = true)
    private String fabricName;
    private String content;
    private Short weave;
    private String fabricQuality;
//    private String uom;
    @Column(precision = 10, scale = 4)
    private BigDecimal reedspace;

    
    private Integer epi;
    
    @Column(precision = 10, scale = 4)
    private BigDecimal fabricWidth;
    private String cutWidth;
    private Integer onTableEpi;
    private Integer onLoomEpi;
    private String selvedgeName;
    private Integer selvedgeDents;
    private Integer selvedgeEndsPerDent;
    private Integer selvedgeEnds;
    
    private Integer ppi;
    @Column(name = "greige_id")
    private String greigeCode;
    private Integer totalEnds;
    @Column(precision = 10, scale = 4)
    private BigDecimal gsm;
    @Column(precision = 10, scale = 4)
    private BigDecimal glm;

    private Short taxType; // 1=CGST/SGST, 2=IGST
    private BigDecimal gstPercent;
    private BigDecimal igst;
    private BigDecimal cgst;
    private BigDecimal sgst;

    private Integer warpCount;
    private String warpConversionCount;
    private Integer weftCount;
    private String weftConversionCount;
    private String tc;
    private Integer noOfInsertion;

    private BigDecimal stdValue;
    private String fabricImageUrl;  
    private Short fabricImage;
    private Short designPaperImage;
    private String status;
    
    // New fields for tracking and additional properties
    @Column(name = "created_on")
    private java.time.LocalDateTime createdOn;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "tc_mix")
    private String tcMix;
    
    @Column(name = "last_modified")
    private java.time.LocalDateTime lastModified;
    
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    
    @Column(name = "single_beam_dbf")
    private String singleBeamDbf;
    
    @Column(name = "twin_beam_dbf")
    private String twinBeamDbf;
    
    @Column(name = "paper_tuber_tare_weight", precision = 10, scale = 4)
    private BigDecimal paperTuberTareWeight;
    
    @Column(name = "hdpe_weight", precision = 10, scale = 4)
    private BigDecimal hdpeWeight;
    
    @Column(name = "remarks")
    private String remarks; 
    
    @Column(name = "reed_count")
    private String reedCount;

    @Column(name = "drawing_order")
    private String drawingOrder;

    @Column(name = "set_plan")
    private String setPlan;

    @Column(name = "ends_per_dent")
    private Integer endsPerDent;
    
    // Additional crimp and GLM fields
    @Column(name = "crimp")
    private String crimp;
    
    @Column(name = "approved_crimp")
    private String approvedCrimp;
    
    @Column(name = "warp_glm_calculated", precision = 10, scale = 4)
    private BigDecimal warpGlmCalculated;
    
    @Column(name = "weft_glm_calculated", precision = 10, scale = 4)
    private BigDecimal weftGlmCalculated;
    
    @Column(name = "actual_crimp", precision = 10, scale = 4)
    private BigDecimal actualCrimp;
    
    @Column(name = "warp_glm_actual", precision = 10, scale = 4)
    private BigDecimal warpGlmActual;
    
    @Column(name = "weft_glm_actual", precision = 10, scale = 4)
    private BigDecimal weftGlmActual;
    
    // @OneToMany(mappedBy = "wovenFabricMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // private List<FabricWarpDetail> warpDetails;
    
    // @OneToMany(mappedBy = "wovenFabricMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // private List<FabricWeftDetail> weftDetails;
    
    // @OneToMany(mappedBy = "wovenFabricMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // private List<FabricTestingDetail> testingDetails;
     @OneToMany(mappedBy = "wovenFabricMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FabricWarpDetail> warpDetails;
    
    @OneToMany(mappedBy = "wovenFabricMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FabricWeftDetail> weftDetails;
    
    @OneToMany(mappedBy = "wovenFabricMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FabricTestingDetail> testingDetails;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fabric_type", insertable = false, updatable = false)
//    private FabricType fabricTypeEntity;
   @Column(precision = 10, scale = 4)
    private BigDecimal warpGlmApproved;
    @Column(precision = 10, scale = 4)
    private BigDecimal weftGlmApproved;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Short getFabricType() { return fabricTypeId; }
    public void setFabricType(Short fabricType) { this.fabricTypeId = fabricType; }
    
    public Short getFabricCategoryId() { return fabricCategoryId; }
    public void setFabricCategoryId(Short fabricCategoryId) { this.fabricCategoryId = fabricCategoryId; }
    
    public Long getWovenFabricId() { return wovenFabricId; }
    public void setWovenFabricId(Long wovenFabricId) { this.wovenFabricId = wovenFabricId; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public String getFabricName() { return fabricName; }
    public void setFabricName(String fabricName) { this.fabricName = fabricName; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Short getWeave() { return weave; }
    public void setWeave(Short weave) { this.weave = weave; }

    public String getFabricQuality() { return fabricQuality; }
    public void setFabricQuality(String fabricQuality) { this.fabricQuality = fabricQuality; }

//    public String getUom() { return uom; }
//    public void setUom(String uom) { this.uom = uom; }
    
    public BigDecimal getReedspace() {
        return reedspace;
    }

    public void setReedspace(BigDecimal reedspace) {
        this.reedspace = reedspace;
    }
    
    
    public BigDecimal getFabricWidth() { return fabricWidth; }
    public void setFabricWidth(BigDecimal fabricWidth) { this.fabricWidth = fabricWidth; }

    public String getCutWidth() { return cutWidth; }
    public void setCutWidth(String cutWidth) { this.cutWidth = cutWidth; }

    public Integer getOnTableEpi() { return onTableEpi; }
    public void setOnTableEpi(Integer onTableEpi) { this.onTableEpi = onTableEpi; }

    public Integer getOnLoomEpi() { return onLoomEpi; }
    public void setOnLoomEpi(Integer onLoomEpi) { this.onLoomEpi = onLoomEpi; }

    public String getSelvedgeName() { return selvedgeName; }
    public void setSelvedgeName(String selvedgeName) { this.selvedgeName = selvedgeName; }

    public Integer getSelvedgeDents() { return selvedgeDents; }
    public void setSelvedgeDents(Integer selvedgeDents) { this.selvedgeDents = selvedgeDents; }

    public Integer getSelvedgeEndsPerDent() { return selvedgeEndsPerDent; }
    public void setSelvedgeEndsPerDent(Integer selvedgeEndsPerDent) { this.selvedgeEndsPerDent = selvedgeEndsPerDent; }

    public Integer getSelvedgeEnds() { return selvedgeEnds; }
    public void setSelvedgeEnds(Integer selvedgeEnds) { this.selvedgeEnds = selvedgeEnds; }


    public Integer getEpi() { return epi; }
    public void setEpi(Integer epi) { this.epi = epi; }

    public Integer getPpi() { return ppi; }
    public void setPpi(Integer ppi) { this.ppi = ppi; }

    public String getGreigeCode() { return greigeCode; }
    public void setGreigeCode(String greigeCode) { this.greigeCode = greigeCode; }

    public Integer getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Integer totalEnds) { this.totalEnds = totalEnds; }

    public BigDecimal getGsm() { return gsm; }
    public void setGsm(BigDecimal gsm) { this.gsm = gsm; }

    public BigDecimal getGlm() { return glm; }
    public void setGlm(BigDecimal glm) { this.glm = glm; }

    public Short getTaxType() { return taxType; }
    public void setTaxType(Short taxType) { this.taxType = taxType; }
    
    public BigDecimal getGstPercent() { return gstPercent; }
    public void setGstPercent(BigDecimal gstPercent) { this.gstPercent = gstPercent; }

    public BigDecimal getIgst() { return igst; }
    public void setIgst(BigDecimal igst) { this.igst = igst; }

    public BigDecimal getCgst() { return cgst; }
    public void setCgst(BigDecimal cgst) { this.cgst = cgst; }

    public BigDecimal getSgst() { return sgst; }
    public void setSgst(BigDecimal sgst) { this.sgst = sgst; }
    
    public Integer getWarpCount() { return warpCount; }
    public void setWarpCount(Integer warpCount) { this.warpCount = warpCount; }

    public String getWarpConversionCount() { return warpConversionCount; }
    public void setWarpConversionCount(String warpConversionCount) { this.warpConversionCount = warpConversionCount; }

    public Integer getWeftCount() { return weftCount; }
    public void setWeftCount(Integer weftCount) { this.weftCount = weftCount; }

    public String getWeftConversionCount() { return weftConversionCount; }
    public void setWeftConversionCount(String weftConversionCount) { this.weftConversionCount = weftConversionCount; }

    public String getTc() { return tc; }
    public void setTc(String tc) { this.tc = tc; }

    public Integer getNoOfInsertion() { return noOfInsertion; }
    public void setNoOfInsertion(Integer noOfInsertion) { this.noOfInsertion = noOfInsertion; }

    public BigDecimal getStdValue() {
        return stdValue;
    }

    public void setStdValue(BigDecimal stdValue) {
        this.stdValue = stdValue;
    }

    public String getFabricImageUrl() { return fabricImageUrl; }
    public void setFabricImageUrl(String fabricImageUrl) { this.fabricImageUrl = fabricImageUrl; }

    public Short getFabricImage() { return fabricImage; }
    public void setFabricImage(Short fabricImage) { this.fabricImage = fabricImage; }

    public Short getDesignPaperImage() { return designPaperImage; }
    public void setDesignPaperImage(Short designPaperImage) { this.designPaperImage = designPaperImage; }

    public List<FabricWarpDetail> getWarpDetails() { return warpDetails; }
    public void setWarpDetails(List<FabricWarpDetail> warpDetails) { this.warpDetails = warpDetails; }

    public List<FabricWeftDetail> getWeftDetails() { return weftDetails; }
    public void setWeftDetails(List<FabricWeftDetail> weftDetails) { this.weftDetails = weftDetails; }
    
    public List<FabricTestingDetail> getTestingDetails() { return testingDetails; }
    public void setTestingDetails(List<FabricTestingDetail> testingDetails) { this.testingDetails = testingDetails; }
    
//    public FabricType getFabricTypeEntity() {
//        return fabricTypeEntity;
//    }
//
//    public void setFabricTypeEntity(FabricType fabricTypeEntity) {
//        this.fabricTypeEntity = fabricTypeEntity;
//    }

    // Getters and Setters for new fields
    public java.time.LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(java.time.LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTcMix() {
        return tcMix;
    }

    public void setTcMix(String tcMix) {
        this.tcMix = tcMix;
    }

    public java.time.LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(java.time.LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getSingleBeamDbf() {
        return singleBeamDbf;
    }

    public void setSingleBeamDbf(String singleBeamDbf) {
        this.singleBeamDbf = singleBeamDbf;
    }

    public String getTwinBeamDbf() {
        return twinBeamDbf;
    }

    public void setTwinBeamDbf(String twinBeamDbf) {
        this.twinBeamDbf = twinBeamDbf;
    }

    public BigDecimal getPaperTuberTareWeight() {
        return paperTuberTareWeight;
    }

    public void setPaperTuberTareWeight(BigDecimal paperTuberTareWeight) {
        this.paperTuberTareWeight = paperTuberTareWeight;
    }

    public BigDecimal getHdpeWeight() {
        return hdpeWeight;
    }

    public void setHdpeWeight(BigDecimal hdpeWeight) {
        this.hdpeWeight = hdpeWeight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // Getters and Setters for new crimp and GLM fields
    public String getCrimp() {
        return crimp;
    }

    public void setCrimp(String crimp) {
        this.crimp = crimp;
    }

    public String getApprovedCrimp() {
        return approvedCrimp;
    }

    public void setApprovedCrimp(String approvedCrimp) {
        this.approvedCrimp = approvedCrimp;
    }

    public BigDecimal getWarpGlmCalculated() {
        return warpGlmCalculated;
    }

    public void setWarpGlmCalculated(BigDecimal warpGlmCalculated) {
        this.warpGlmCalculated = warpGlmCalculated;
    }

    public BigDecimal getWeftGlmCalculated() {
        return weftGlmCalculated;
    }

    public void setWeftGlmCalculated(BigDecimal weftGlmCalculated) {
        this.weftGlmCalculated = weftGlmCalculated;
    }

    public BigDecimal getActualCrimp() {
        return actualCrimp;
    }

    public void setActualCrimp(BigDecimal actualCrimp) {
        this.actualCrimp = actualCrimp;
    }

    public BigDecimal getWarpGlmActual() {
        return warpGlmActual;
    }

    public void setWarpGlmActual(BigDecimal warpGlmActual) {
        this.warpGlmActual = warpGlmActual;
    }

    public BigDecimal getWeftGlmActual() {
        return weftGlmActual;
    }

    public void setWeftGlmActual(BigDecimal weftGlmActual) {
        this.weftGlmActual = weftGlmActual;
    }
     public BigDecimal getWarpGlmApproved() { return warpGlmApproved; }
    public void setWarpGlmApproved(BigDecimal warpGlmApproved) { this.warpGlmApproved = warpGlmApproved; }

    public BigDecimal getWeftGlmApproved() { return weftGlmApproved; }
    public void setWeftGlmApproved(BigDecimal weftGlmApproved) { this.weftGlmApproved = weftGlmApproved; }

    @Column(name = "wastage_percentage", precision = 10, scale = 4)
    private BigDecimal wastagePercentage;

    public BigDecimal getWastagePercentage() {
        return wastagePercentage;
    }

    public void setWastagePercentage(BigDecimal wastagePercentage) {
        this.wastagePercentage = wastagePercentage;
    }

    public String getReedCount() {
        return reedCount;
    }

    public void setReedCount(String reedCount) {
        this.reedCount = reedCount;
    }

    public String getDrawingOrder() {
        return drawingOrder;
    }

    public void setDrawingOrder(String drawingOrder) {
        this.drawingOrder = drawingOrder;
    }

    public String getSetPlan() {
        return setPlan;
    }

    public void setSetPlan(String setPlan) {
        this.setPlan = setPlan;
    }

    public Integer getEndsPerDent() {
        return endsPerDent;
    }

    public void setEndsPerDent(Integer endsPerDent) {
        this.endsPerDent = endsPerDent;
    }
}
