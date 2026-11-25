package com.tech3.erp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.entity.FabricTestingDetail;
import com.tech3.erp.entity.FabricType;
import com.tech3.erp.entity.FabricWarpDetail;
import com.tech3.erp.entity.FabricWeftDetail;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

public class WovenFabricMasterDTO implements Serializable {

    private Long id;
    private Short fabricTypeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short fabricCategoryId;
    private Long wovenFabricId;
    private String fabricCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fabricName;
    private String content;
    private Short weave;
    private String fabricQuality;
//    private String uom;
    private BigDecimal reedspace;


    private BigDecimal fabricWidth;
    private String cutWidth;
    private Integer onTableEpi;
    private Integer onLoomEpi;
    private String selvedgeName;
    private Integer selvedgeDents;
    private Integer selvedgeEndsPerDent;
    private Integer selvedgeEnds;
    
    
    private Integer epi;
    private Integer ppi;
    private String greigeCode;
    private Integer totalEnds;
    private BigDecimal gsm;
    private BigDecimal glm;
    
    private Short taxType;
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
    private java.time.LocalDateTime createdOn;
    private String createdBy;
    private String tcMix;
    private java.time.LocalDateTime lastModified;
    private String lastModifiedBy;
    private String singleBeamDbf;
    private String twinBeamDbf;
    private BigDecimal paperTuberTareWeight;
    private BigDecimal hdpeWeight;
    private String remarks;
    private String reedCount;
    private String drawingOrder;
    private String setPlan;
    private Integer endsPerDent;
    
    // Additional crimp and GLM fields
    private String crimp;
    private String approvedCrimp;
    private BigDecimal warpGlmCalculated;
    private BigDecimal weftGlmCalculated;
    private BigDecimal actualCrimp;
    private BigDecimal warpGlmActual;
    private BigDecimal weftGlmActual;
    
    private MediaDetailResponse fabricImageDetail;
    private MediaDetailResponse designPaperImageDetail;
    
    @Transient
    private String fabricTypeName;

    private List<FabricWarpDetailDTO> warpDetails;
    private List<FabricWeftDetailDTO> weftDetails;
    private List<FabricTestingDetailDTO> testingDetails;

     private BigDecimal warpGlmApproved;
    private BigDecimal weftGlmApproved;
    private BigDecimal wastagePercentage;
    
    // Default Constructor
    public WovenFabricMasterDTO() {}

    // Constructor using FabricMaster entity
    public WovenFabricMasterDTO(WovenFabricMaster entity) {
        this.id = entity.getId();
        this.fabricTypeId = entity.getFabricType();
        this.fabricCategoryId = entity.getFabricCategoryId();
        this.wovenFabricId = entity.getWovenFabricId();
        this.fabricCode = entity.getFabricCode();
        this.fabricName = entity.getFabricName();
        this.weave = entity.getWeave();
        this.fabricQuality = entity.getFabricQuality();
//        this.uom = entity.getUom();
        this.reedspace = entity.getReedspace();
        
        this.epi = entity.getEpi();
        
        this.fabricWidth = entity.getFabricWidth();
        this.cutWidth = entity.getCutWidth();
        this.onTableEpi = entity.getOnTableEpi();
        this.onLoomEpi = entity.getOnLoomEpi();
        this.selvedgeName = entity.getSelvedgeName();
        this.selvedgeDents = entity.getSelvedgeDents();
        this.selvedgeEndsPerDent = entity.getSelvedgeEndsPerDent();
        this.selvedgeEnds = entity.getSelvedgeEnds();
        
        this.ppi = entity.getPpi();
        this.greigeCode = entity.getGreigeCode();
        this.totalEnds = entity.getTotalEnds();
        this.gsm = entity.getGsm();
        this.glm = entity.getGlm();

        this.taxType = entity.getTaxType();
        this.gstPercent = entity.getGstPercent();
        
        this.igst = entity.getIgst();
        this.cgst = entity.getCgst();
        this.sgst = entity.getSgst();
         this.content = entity.getContent();

        this.warpCount = entity.getWarpCount();
        this.warpConversionCount = entity.getWarpConversionCount();
        this.weftCount = entity.getWeftCount();
        this.weftConversionCount = entity.getWeftConversionCount();
        this.tc = entity.getTc();
        this.noOfInsertion = entity.getNoOfInsertion();

        this.stdValue = entity.getStdValue();
        this.fabricImageUrl = entity.getFabricImageUrl();
        this.fabricImage = entity.getFabricImage();
        this.designPaperImage = entity.getDesignPaperImage();
        
        // New fields mapping
        this.createdOn = entity.getCreatedOn();
        this.createdBy = entity.getCreatedBy();
        this.tcMix = entity.getTcMix();
        this.lastModified = entity.getLastModified();
        this.lastModifiedBy = entity.getLastModifiedBy();
        this.singleBeamDbf = entity.getSingleBeamDbf();
        this.twinBeamDbf = entity.getTwinBeamDbf();
        this.paperTuberTareWeight = entity.getPaperTuberTareWeight();
        this.hdpeWeight = entity.getHdpeWeight();
        this.remarks = entity.getRemarks();
        this.reedCount = entity.getReedCount();
        this.drawingOrder = entity.getDrawingOrder();
        this.setPlan = entity.getSetPlan();
        this.endsPerDent = entity.getEndsPerDent();
        
        // Additional crimp and GLM fields mapping
        this.crimp = entity.getCrimp();
        this.approvedCrimp = entity.getApprovedCrimp();
        this.warpGlmCalculated = entity.getWarpGlmCalculated();
        this.weftGlmCalculated = entity.getWeftGlmCalculated();
        this.actualCrimp = entity.getActualCrimp();
        this.warpGlmActual = entity.getWarpGlmActual();
        this.weftGlmActual = entity.getWeftGlmActual();

        this.warpGlmApproved = entity.getWarpGlmApproved();
        this.weftGlmApproved = entity.getWeftGlmApproved();
        this.wastagePercentage = entity.getWastagePercentage();

        // Convert Warp and Weft detail entities to DTOs
        if (entity.getWarpDetails() != null) {
            this.warpDetails = entity.getWarpDetails().stream()
                .map(FabricWarpDetailDTO::new)
                .collect(Collectors.toList());
        }

        if (entity.getWeftDetails() != null) {
            this.weftDetails = entity.getWeftDetails().stream()
                .map(FabricWeftDetailDTO::new)
                .collect(Collectors.toList());
        }
        
        if (entity.getTestingDetails() != null) {
            this.testingDetails = entity.getTestingDetails().stream()
                .map(FabricTestingDetailDTO::new)
                .collect(Collectors.toList());
        }
        

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

    public void setWovenFabricId(Long wovenFabricId) {
        this.wovenFabricId = wovenFabricId;
    }
    
    public Long getWovenFabricId() {
        return wovenFabricId;
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
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Short getWeave() {
        return weave;
    }

    public void setWeave(Short weave) {
        this.weave = weave;
    }

    public String getFabricQuality() {
        return fabricQuality;
    }

    public void setFabricQuality(String fabricQuality) {
        this.fabricQuality = fabricQuality;
    }

//    public String getUom() {
//        return uom;
//    }
//
//    public void setUom(String uom) {
//        this.uom = uom;
//    }
    
    public BigDecimal getReedspace() {
        return reedspace;
    }

    public void setReedspace(BigDecimal reedspace) {
        this.reedspace = reedspace;
    }


    public BigDecimal getFabricWidth() {
        return fabricWidth;
    }

    public void setFabricWidth(BigDecimal fabricWidth) {
        this.fabricWidth = fabricWidth;
    }

    public String getCutWidth() {
        return cutWidth;
    }

    public void setCutWidth(String cutWidth) {
        this.cutWidth = cutWidth;
    }

    public Integer getOnTableEpi() {
        return onTableEpi;
    }

    public void setOnTableEpi(Integer onTableEpi) {
        this.onTableEpi = onTableEpi;
    }

    public Integer getOnLoomEpi() {
        return onLoomEpi;
    }

    public void setOnLoomEpi(Integer onLoomEpi) {
        this.onLoomEpi = onLoomEpi;
    }

    public String getSelvedgeName() {
        return selvedgeName;
    }

    public void setSelvedgeName(String selvedgeName) {
        this.selvedgeName = selvedgeName;
    }

    public Integer getSelvedgeDents() {
        return selvedgeDents;
    }

    public void setSelvedgeDents(Integer selvedgeDents) {
        this.selvedgeDents = selvedgeDents;
    }

    public Integer getSelvedgeEndsPerDent() {
        return selvedgeEndsPerDent;
    }

    public void setSelvedgeEndsPerDent(Integer selvedgeEndsPerDent) {
        this.selvedgeEndsPerDent = selvedgeEndsPerDent;
    }

    public Integer getSelvedgeEnds() {
        return selvedgeEnds;
    }

    public void setSelvedgeEnds(Integer selvedgeEnds) {
        this.selvedgeEnds = selvedgeEnds;
    }
    
    public Integer getEpi() {
        return epi;
    }

    public void setEpi(Integer epi) {
        this.epi = epi;
    }

    public Integer getPpi() {
        return ppi;
    }

    public void setPpi(Integer ppi) {
        this.ppi = ppi;
    }

    public String getGreigeCode() {
        return greigeCode;
    }

    public void setGreigeCode(String greigeCode) {
        this.greigeCode = greigeCode;
    }

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }

    public BigDecimal getGsm() {
        return gsm;
    }

    public void setGsm(BigDecimal gsm) {
        this.gsm = gsm;
    }

    public BigDecimal getGlm() {
        return glm;
    }

    public void setGlm(BigDecimal glm) {
        this.glm = glm;
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

    public String getFabricImageUrl() {
        return fabricImageUrl;
    }

    public void setFabricImageUrl(String fabricImageUrl) {
        this.fabricImageUrl = fabricImageUrl;
    }

    public Short getFabricImage() {
        return fabricImage;
    }

    public void setFabricImage(Short fabricImage) {
        this.fabricImage = fabricImage;
    }

    public Short getDesignPaperImage() {
        return designPaperImage;
    }

    public void setDesignPaperImage(Short designPaperImage) {
        this.designPaperImage = designPaperImage;
    }

    public MediaDetailResponse getFabricImageDetail() {
        return fabricImageDetail;
    }

    public void setFabricImageDetail(MediaDetailResponse fabricImageDetail) {
        this.fabricImageDetail = fabricImageDetail;
    }

    public MediaDetailResponse getDesignPaperImageDetail() {
        return designPaperImageDetail;
    }

    public void setDesignPaperImageDetail(MediaDetailResponse designPaperImageDetail) {
        this.designPaperImageDetail = designPaperImageDetail;
    }

    public List<FabricWarpDetailDTO> getWarpDetails() {
        return warpDetails;
    }

    public void setWarpDetails(List<FabricWarpDetailDTO> warpDetails) {
        this.warpDetails = warpDetails;
    }

    public List<FabricWeftDetailDTO> getWeftDetails() {
        return weftDetails;
    }

    public void setWeftDetails(List<FabricWeftDetailDTO> weftDetails) {
        this.weftDetails = weftDetails;
    }
    
    public List<FabricTestingDetailDTO> getTestingDetails() { 
    	return testingDetails; 
    }
    
    public void setTestingDetails(List<FabricTestingDetailDTO> testingDetails) {
    	this.testingDetails = testingDetails;
    }
    
    public String getFabricTypeName() {
        return fabricTypeName;
    }

    public void setFabricTypeName(String fabricTypeName) {
        this.fabricTypeName = fabricTypeName;
    }
    
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

