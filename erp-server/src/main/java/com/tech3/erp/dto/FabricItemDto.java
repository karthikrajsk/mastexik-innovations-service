
package com.tech3.erp.dto;

import com.tech3.erp.entity.FabricItemEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FabricItemDto {

    private Long id;
    @JsonProperty("sNo")
    private Integer sNo;
    private Long openingBalanceId;
    @JsonProperty("fabricCodeId")
    private Long fabricCodeId;
    @JsonProperty("fabricCode")
    private String fabricCode;
    @JsonProperty("fabricTypeId")
    private Long fabricTypeId;
    @JsonProperty("fabricMasterId")
    private Long fabricMasterId;
    @JsonProperty("quality")
    private String quality;
    @JsonProperty("pieceNo")
    private String pieceNo;
    @JsonProperty("pieceMeter")
    private Double pieceMeter;
    @JsonProperty("netWeight")
    private Double netWeight;
    @JsonProperty("avgWeight")
    private Double avgWeight;
    @JsonProperty("grade")
    private Long grade; // store grade ID
    @JsonProperty("packingNo")
    private String packingNo;
    @JsonProperty("isInspected")
    private Boolean isInspected;

    public FabricItemDto() {
        // Default constructor for JSON deserialization
    }

    public FabricItemDto(FabricItemEntity entity) {
        this.id = entity.getId();
        this.sNo = entity.getsNo();
        this.openingBalanceId = entity.getOpeningBalance() != null ? entity.getOpeningBalance().getId() : null;
        this.fabricCodeId = entity.getFabricCodeId();
        this.fabricCode = entity.getFabricCode();
        this.fabricTypeId = entity.getFabricTypeId();
        this.fabricMasterId = entity.getFabricMasterId();
        this.quality = entity.getQuality();
        this.packingNo = entity.getPackingNo();
        this.pieceNo = entity.getPieceNo();
        this.pieceMeter = entity.getPieceMeter();
        this.netWeight = entity.getNetWeight();
        this.avgWeight = entity.getAvgWeight();
        this.grade = entity.getGradeId();
        this.isInspected = entity.getIsInspected();
    }

    // Constructor to map entity -> DTO
   

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }

    public Long getOpeningBalanceId() { return openingBalanceId; }
    public void setOpeningBalanceId(Long openingBalanceId) { this.openingBalanceId = openingBalanceId; }

    public Long getFabricCodeId() { return fabricCodeId; }
    public void setFabricCodeId(Long fabricCodeId) { this.fabricCodeId = fabricCodeId; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public Long getFabricTypeId() { return fabricTypeId; }
    public void setFabricTypeId(Long fabricTypeId) { this.fabricTypeId = fabricTypeId; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }

    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }

    public Double getPieceMeter() { return pieceMeter; }
    public void setPieceMeter(Double pieceMeter) { this.pieceMeter = pieceMeter; }

    public Double getNetWeight() { return netWeight; }
    public void setNetWeight(Double netWeight) { this.netWeight = netWeight; }

    public Double getAvgWeight() { return avgWeight; }
    public void setAvgWeight(Double avgWeight) { this.avgWeight = avgWeight; }

    public Long getGrade() { return grade; }
    public void setGrade(Long grade) { this.grade = grade; }

    public String getPackingNo() { return packingNo; }
    public void setPackingNo(String packingNo) { this.packingNo = packingNo; }

    public Boolean getIsInspected() { return isInspected; }
    public void setIsInspected(Boolean isInspected) { this.isInspected = isInspected; }
}