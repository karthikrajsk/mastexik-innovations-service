package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fabric_item", schema = "masters")
public class FabricItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sNo;

    // Store foreign key as ID
    @Column(name = "fabric_code_id")
    private Long fabricCodeId;

    @Column(name = "fabric_code")
    private String fabricCode;

    @Column(name = "fabric_type_id")
    private Long fabricTypeId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    private String quality;
    private String packingNo;
    private String pieceNo;
    private Double pieceMeter;
    private Double netWeight;
    private Double avgWeight;

    // Grade as foreign key ID
    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "is_inspected")
    private Boolean isInspected;

    // Relation to OpeningBalance
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_balance_id")
    private OpeningBalanceEntity openingBalance;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getsNo() { return sNo; }
    public void setsNo(Integer sNo) { this.sNo = sNo; }

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

    public String getPackingNo() { return packingNo; }
    public void setPackingNo(String packingNo) { this.packingNo = packingNo; }

    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }

    public Double getPieceMeter() { return pieceMeter; }
    public void setPieceMeter(Double pieceMeter) { this.pieceMeter = pieceMeter; }

    public Double getNetWeight() { return netWeight; }
    public void setNetWeight(Double netWeight) { this.netWeight = netWeight; }

    public Double getAvgWeight() { return avgWeight; }
    public void setAvgWeight(Double avgWeight) { this.avgWeight = avgWeight; }

    public Long getGradeId() { return gradeId; }
    public void setGradeId(Long gradeId) { this.gradeId = gradeId; }

    public Boolean getIsInspected() { return isInspected; }
    public void setIsInspected(Boolean isInspected) { this.isInspected = isInspected; }

    public OpeningBalanceEntity getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(OpeningBalanceEntity openingBalance) { this.openingBalance = openingBalance; }
}
