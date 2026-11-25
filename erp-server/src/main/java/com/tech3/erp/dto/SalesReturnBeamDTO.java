// package com.tech3.erp.dto;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.tech3.erp.entity.SalesReturnBeamEntity;
// import java.math.BigDecimal;

// public class SalesReturnBeamDTO {
//     private Long id;

//     @JsonProperty("beamOutwardNo")
//     private String beamOutwardNo;

//     @JsonProperty("outwardDate")
//     private String outwardDate;

//     @JsonProperty("weavingContractNo")
//     private String weavingContractNo;

//     @JsonProperty("weaverName")
//     private String weaverName;

//     @JsonProperty("sizingPlanNo")
//     private String sizingPlanNo;

//     @JsonProperty("salesOrderNo")
//     private String salesOrderNo;

//     @JsonProperty("flangeNo")
//     private String flangeNo;

//     @JsonProperty("sizedBeamNo")
//     private String sizedBeamNo;

//     @JsonProperty("beamMeters")
//     private BigDecimal beamMeters;

//     @JsonProperty("expectedFabricMeter")
//     private BigDecimal expectedFabricMeter;

//     @JsonProperty("totalBeams")
//     private BigDecimal totalBeams;

//     @JsonProperty("weaverId")
//     private Long weaverId;

//     @JsonProperty("fabricPanelWidth")
//     private String fabricPanelWidth;

//     @JsonProperty("isReturn")
//     private Integer isReturn;

//     @JsonProperty("returnDate")
//     private String returnDate;

//     @JsonProperty("returnNo")
//     private String returnNo;

//     @JsonProperty("returnWeaverId")
//     private Long returnWeaverId;

//     @JsonProperty("shrinkage")
//     private BigDecimal shrinkage;

//     @JsonProperty("beamOutwardId")
//     private Long beamOutwardId;

//     @JsonProperty("beamInwardId")
//     private Long beamInwardId;

//     public SalesReturnBeamDTO() {}

//     public SalesReturnBeamDTO(SalesReturnBeamEntity entity) {
//         this.id = entity.getId();
//         this.beamOutwardNo = entity.getBeamOutwardNo();
//         this.outwardDate = entity.getOutwardDate() != null ? entity.getOutwardDate().toString() : null;
//         this.weavingContractNo = entity.getWeavingContractNo();
//         this.weaverName = entity.getWeaverName();
//         this.sizingPlanNo = entity.getSizingPlanNo();
//         this.salesOrderNo = entity.getSalesOrderNo();
//         this.flangeNo = entity.getFlangeNo();
//         this.sizedBeamNo = entity.getSizedBeamNo();
//         this.beamMeters = entity.getBeamMeters();
//         this.expectedFabricMeter = entity.getExpectedFabricMeter();
//         this.totalBeams = entity.getTotalBeams();
//         this.weaverId = entity.getWeaverId();
//         this.fabricPanelWidth = entity.getFabricPanelWidth();
//         this.isReturn = entity.getIsReturn();
//         this.returnDate = entity.getReturnDate() != null ? entity.getReturnDate().toString() : null;
//         this.returnNo = entity.getReturnNo();
//         this.returnWeaverId = entity.getReturnWeaverId();
//         this.shrinkage = entity.getShrinkage();
//         this.beamOutwardId = entity.getBeamOutwardId();
//         this.beamInwardId = entity.getBeamInwardId();
//     }

//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getBeamOutwardNo() { return beamOutwardNo; }
//     public void setBeamOutwardNo(String beamOutwardNo) { this.beamOutwardNo = beamOutwardNo; }

//     public String getOutwardDate() { return outwardDate; }
//     public void setOutwardDate(String outwardDate) { this.outwardDate = outwardDate; }

//     public String getWeavingContractNo() { return weavingContractNo; }
//     public void setWeavingContractNo(String weavingContractNo) { this.weavingContractNo = weavingContractNo; }

//     public String getWeaverName() { return weaverName; }
//     public void setWeaverName(String weaverName) { this.weaverName = weaverName; }

//     public String getSizingPlanNo() { return sizingPlanNo; }
//     public void setSizingPlanNo(String sizingPlanNo) { this.sizingPlanNo = sizingPlanNo; }

//     public String getSalesOrderNo() { return salesOrderNo; }
//     public void setSalesOrderNo(String salesOrderNo) { this.salesOrderNo = salesOrderNo; }

//     public String getFlangeNo() { return flangeNo; }
//     public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

//     public String getSizedBeamNo() { return sizedBeamNo; }
//     public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

//     public BigDecimal getBeamMeters() { return beamMeters; }
//     public void setBeamMeters(BigDecimal beamMeters) { this.beamMeters = beamMeters; }

//     public BigDecimal getExpectedFabricMeter() { return expectedFabricMeter; }
//     public void setExpectedFabricMeter(BigDecimal expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

//     public BigDecimal getTotalBeams() { return totalBeams; }
//     public void setTotalBeams(BigDecimal totalBeams) { this.totalBeams = totalBeams; }

//     public Long getWeaverId() { return weaverId; }
//     public void setWeaverId(Long weaverId) { this.weaverId = weaverId; }

//     public String getFabricPanelWidth() { return fabricPanelWidth; }
//     public void setFabricPanelWidth(String fabricPanelWidth) { this.fabricPanelWidth = fabricPanelWidth; }

//     public Integer getIsReturn() { return isReturn; }
//     public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

//     public String getReturnDate() { return returnDate; }
//     public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

//     public String getReturnNo() { return returnNo; }
//     public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

//     public Long getReturnWeaverId() { return returnWeaverId; }
//     public void setReturnWeaverId(Long returnWeaverId) { this.returnWeaverId = returnWeaverId; }

//     public BigDecimal getShrinkage() { return shrinkage; }
//     public void setShrinkage(BigDecimal shrinkage) { this.shrinkage = shrinkage; }

//     public Long getBeamOutwardId() { return beamOutwardId; }
//     public void setBeamOutwardId(Long beamOutwardId) { this.beamOutwardId = beamOutwardId; }

//     public Long getBeamInwardId() { return beamInwardId; }
//     public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }
// }
package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.SalesReturnBeamEntity;
import java.math.BigDecimal;

public class SalesReturnBeamDTO {
    private Long id;

    @JsonProperty("beamOutwardNo")
    private String beamOutwardNo;

    @JsonProperty("outwardDate")
    private String outwardDate;

    @JsonProperty("weavingContractNo")
    private String weavingContractNo;

    @JsonProperty("weaverName")
    private String weaverName;

    @JsonProperty("sizingPlanNo")
    private String sizingPlanNo;

    @JsonProperty("salesOrderNo")
    private String salesOrderNo;

    @JsonProperty("flangeNo")
    private String flangeNo;

    @JsonProperty("sizedBeamNo")
    private String sizedBeamNo;

    @JsonProperty("beamMeters")
    private BigDecimal beamMeters;

    @JsonProperty("expectedFabricMeter")
    private BigDecimal expectedFabricMeter;

    @JsonProperty("totalBeams")
    private BigDecimal totalBeams;

    @JsonProperty("weaverId")
    private Long weaverId;

    @JsonProperty("fabricPanelWidth")
    private String fabricPanelWidth;

    @JsonProperty("isReturn")
    private Integer isReturn;

    @JsonProperty("returnDate")
    private String returnDate;

    @JsonProperty("returnNo")
    private String returnNo;

    @JsonProperty("returnWeaverId")
    private Long returnWeaverId;

    @JsonProperty("shrinkage")
    private BigDecimal shrinkage;

    @JsonProperty("beamOutwardId")
    private Long beamOutwardId;

    @JsonProperty("beamInwardId")
    private Long beamInwardId;

    @JsonProperty("isOutwarded")
    private Integer isOutwarded;

    public SalesReturnBeamDTO() {}

    public SalesReturnBeamDTO(SalesReturnBeamEntity entity) {
        this.id = entity.getId();
        this.beamOutwardNo = entity.getBeamOutwardNo();
        this.outwardDate = entity.getOutwardDate() != null ? entity.getOutwardDate().toString() : null;
        this.weavingContractNo = entity.getWeavingContractNo();
        this.weaverName = entity.getWeaverName();
        this.sizingPlanNo = entity.getSizingPlanNo();
        this.salesOrderNo = entity.getSalesOrderNo();
        this.flangeNo = entity.getFlangeNo();
        this.sizedBeamNo = entity.getSizedBeamNo();
        this.beamMeters = entity.getBeamMeters();
        this.expectedFabricMeter = entity.getExpectedFabricMeter();
        this.totalBeams = entity.getTotalBeams();
        this.weaverId = entity.getWeaverId();
        this.fabricPanelWidth = entity.getFabricPanelWidth();
        this.isReturn = entity.getIsReturn();
        this.returnDate = entity.getReturnDate() != null ? entity.getReturnDate().toString() : null;
        this.returnNo = entity.getReturnNo();
        this.returnWeaverId = entity.getReturnWeaverId();
        this.shrinkage = entity.getShrinkage();
        this.beamOutwardId = entity.getBeamOutwardId();
        this.beamInwardId = entity.getBeamInwardId();
        this.isOutwarded = entity.getIsOutwarded();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBeamOutwardNo() { return beamOutwardNo; }
    public void setBeamOutwardNo(String beamOutwardNo) { this.beamOutwardNo = beamOutwardNo; }

    public String getOutwardDate() { return outwardDate; }
    public void setOutwardDate(String outwardDate) { this.outwardDate = outwardDate; }

    public String getWeavingContractNo() { return weavingContractNo; }
    public void setWeavingContractNo(String weavingContractNo) { this.weavingContractNo = weavingContractNo; }

    public String getWeaverName() { return weaverName; }
    public void setWeaverName(String weaverName) { this.weaverName = weaverName; }

    public String getSizingPlanNo() { return sizingPlanNo; }
    public void setSizingPlanNo(String sizingPlanNo) { this.sizingPlanNo = sizingPlanNo; }

    public String getSalesOrderNo() { return salesOrderNo; }
    public void setSalesOrderNo(String salesOrderNo) { this.salesOrderNo = salesOrderNo; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public String getSizedBeamNo() { return sizedBeamNo; }
    public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

    public BigDecimal getBeamMeters() { return beamMeters; }
    public void setBeamMeters(BigDecimal beamMeters) { this.beamMeters = beamMeters; }

    public BigDecimal getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(BigDecimal expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

    public BigDecimal getTotalBeams() { return totalBeams; }
    public void setTotalBeams(BigDecimal totalBeams) { this.totalBeams = totalBeams; }

    public Long getWeaverId() { return weaverId; }
    public void setWeaverId(Long weaverId) { this.weaverId = weaverId; }

    public String getFabricPanelWidth() { return fabricPanelWidth; }
    public void setFabricPanelWidth(String fabricPanelWidth) { this.fabricPanelWidth = fabricPanelWidth; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

    public Long getReturnWeaverId() { return returnWeaverId; }
    public void setReturnWeaverId(Long returnWeaverId) { this.returnWeaverId = returnWeaverId; }

    public BigDecimal getShrinkage() { return shrinkage; }
    public void setShrinkage(BigDecimal shrinkage) { this.shrinkage = shrinkage; }

    public Long getBeamOutwardId() { return beamOutwardId; }
    public void setBeamOutwardId(Long beamOutwardId) { this.beamOutwardId = beamOutwardId; }

    public Long getBeamInwardId() { return beamInwardId; }
    public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }

    public Integer getIsOutwarded() { return isOutwarded; }
    public void setIsOutwarded(Integer isOutwarded) { this.isOutwarded = isOutwarded; }
}
