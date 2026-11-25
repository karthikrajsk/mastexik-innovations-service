// package com.tech3.erp.entity;

// import com.fasterxml.jackson.annotation.JsonBackReference;
// import jakarta.persistence.*;
// import java.math.BigDecimal;

// @Entity
// @Table(name = "sales_return_item", schema = "masters")
// public class SalesReturnItem {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "id")
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "sales_return_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sales_return_item"))
//     @JsonBackReference("salesReturnItems")
//     private SalesReturn salesReturn;

//     @Column(name = "roll_no", length = 100, nullable = false)
//     private String rollNo;

//     @Column(name = "piece_no", length = 100, nullable = false)
//     private String pieceNo;

//     @Column(name = "packing_list_no", length = 100)
//     private String packingListNo;

//     @Column(name = "meter", precision = 19, scale = 3, nullable = false)
//     private BigDecimal meter;

//     @Column(name = "net_weight", precision = 19, scale = 3, nullable = false)
//     private BigDecimal netWeight;

//     @Column(name = "total_rolls", precision = 19, scale = 3)
//     private BigDecimal totalRolls;

//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public SalesReturn getSalesReturn() { return salesReturn; }
//     public void setSalesReturn(SalesReturn salesReturn) { this.salesReturn = salesReturn; }

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



package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_return_item", schema = "masters")
public class SalesReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_return_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sales_return_item"))
    @JsonBackReference("salesReturnItems")
    private SalesReturn salesReturn;

    @Column(name = "roll_no", length = 100, nullable = false)
    private String rollNo;

    @Column(name = "piece_no", length = 100, nullable = false)
    private String pieceNo;

    @Column(name = "packing_list_no", length = 100)
    private String packingListNo;

    @Column(name = "meter", precision = 19, scale = 3, nullable = false)
    private BigDecimal meter;

    @Column(name = "net_weight", precision = 19, scale = 3, nullable = false)
    private BigDecimal netWeight;

    @Column(name = "is_dispatch", nullable = false, columnDefinition = "int default 0")
    private Integer isDispatch = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SalesReturn getSalesReturn() { return salesReturn; }
    public void setSalesReturn(SalesReturn salesReturn) { this.salesReturn = salesReturn; }

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

    public Integer getIsDispatch() { return isDispatch != null ? isDispatch : 0; }
    public void setIsDispatch(Integer isDispatch) { this.isDispatch = isDispatch; }
}



