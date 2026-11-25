// package com.tech3.erp.dto;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.tech3.erp.entity.SalesReturnItem;
// import java.math.BigDecimal;

// public class SalesReturnItemDTO {
//     private Long id;

//     @JsonProperty("rollNo")
//     private String rollNo;

//     @JsonProperty("pieceNo")
//     private String pieceNo;

//     @JsonProperty("packingListNo")
//     private String packingListNo;

//     @JsonProperty("meter")
//     private BigDecimal meter;

//     @JsonProperty("netWeight")
//     private BigDecimal netWeight;

//     @JsonProperty("totalRolls")
//     private BigDecimal totalRolls;

//     public SalesReturnItemDTO() {}

//     public SalesReturnItemDTO(SalesReturnItem entity) {
//         this.id = entity.getId();
//         this.rollNo = entity.getRollNo();
//         this.pieceNo = entity.getPieceNo();
//         this.packingListNo = entity.getPackingListNo();
//         this.meter = entity.getMeter();
//         this.netWeight = entity.getNetWeight();
//         this.totalRolls = entity.getTotalRolls();
//     }

//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getRollNo() { return rollNo; }
//     public void setRollNo(String rollNo) { this.rollNo = rollNo; }

//     public String getPieceNo() { return pieceNo; }
//     public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }

//     public String getPackingListNo() { return packingListNo; }
//     public void setPackingListNo(String packingListNo) { this.packingListNo = packingListNo; }

//     public BigDecimal getMeter() { return meter; }
//     public void setMeter(BigDecimal meter) { this.meter = meter; }

//     public BigDecimal getNetWeight() { return netWeight; }
//     public void setNetWeight(BigDecimal netWeight) { this.netWeight = netWeight; }

//     public BigDecimal getTotalRolls() { return totalRolls; }
//     public void setTotalRolls(BigDecimal totalRolls) { this.totalRolls = totalRolls; }
// }
package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.SalesReturnItem;
import java.math.BigDecimal;

public class SalesReturnItemDTO {
    private Long id;

    @JsonProperty("rollNo")
    private String rollNo;

    @JsonProperty("pieceNo")
    private String pieceNo;

    @JsonProperty("packingListNo")
    private String packingListNo;

    @JsonProperty("meter")
    private BigDecimal meter;

    @JsonProperty("netWeight")
    private BigDecimal netWeight;

    @JsonProperty("isDispatch")
    private Integer isDispatch;

    public SalesReturnItemDTO() {}

    public SalesReturnItemDTO(SalesReturnItem entity) {
        this.id = entity.getId();
        this.rollNo = entity.getRollNo();
        this.pieceNo = entity.getPieceNo();
        this.packingListNo = entity.getPackingListNo();
        this.meter = entity.getMeter();
        this.netWeight = entity.getNetWeight();
        this.isDispatch = entity.getIsDispatch();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }

    public String getPackingListNo() { return packingListNo; }
    public void setPackingListNo(String packingListNo) { this.packingListNo = packingListNo; }

    public BigDecimal getMeter() { return meter; }
    public void setMeter(BigDecimal meter) { this.meter = meter; }

    public BigDecimal getNetWeight() { return netWeight; }
    public void setNetWeight(BigDecimal netWeight) { this.netWeight = netWeight; }

    public Integer getIsDispatch() { return isDispatch; }
    public void setIsDispatch(Integer isDispatch) { this.isDispatch = isDispatch; }
}
