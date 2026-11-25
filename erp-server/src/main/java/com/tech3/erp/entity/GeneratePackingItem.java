package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "generate_packing_item", schema = "masters")
public class GeneratePackingItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_packing_id", nullable = false)
    private GeneratePacking generatePacking;
    
    @Column(name = "roll_no", length = 1000)
    private String rollNo;
    
    @Column(name = "piece_no", length = 1000)
    private String pieceNo;
    
    @Column(name = "length")  // meters
    private Double length;
    
    @Column(name = "yards")
    private Double yards;
    
    @Column(name = "uom_id")
    private Long uomId;
    
    @Column(name = "pounds")  // net weight
    private Double pounds;
    
    @Column(name = "avg_wt")
    private Double avgWt;
    
    @Column(name = "gross_wt")
    private Double grossWt;
    
    @Column(name = "lot_id")
    private Long lotId;
    
    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "is_invoice")
    private Integer isInvoice = 0;

    @Column(name = "is_scanned_packing")
    private Integer isScannedPacking = 0;

    @Column(name = "invoice_no", length = 255)
    private String invoiceNo;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public GeneratePacking getGeneratePacking() {
        return generatePacking;
    }
    
    public void setGeneratePacking(GeneratePacking generatePacking) {
        this.generatePacking = generatePacking;
    }
    
    public String getRollNo() {
        return rollNo;
    }
    
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
    
    public String getPieceNo() {
        return pieceNo;
    }
    
    public void setPieceNo(String pieceNo) {
        this.pieceNo = pieceNo;
    }
    
    public Double getLength() {
        return length;
    }
    
    public void setLength(Double length) {
        this.length = length;
    }
    
    public Double getYards() {
        return yards;
    }
    
    public void setYards(Double yards) {
        this.yards = yards;
    }
    
    public Long getUomId() {
        return uomId;
    }
    
    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }
    
    public Double getPounds() {
        return pounds;
    }
    
    public void setPounds(Double pounds) {
        this.pounds = pounds;
    }
    
    public Double getAvgWt() {
        return avgWt;
    }
    
    public void setAvgWt(Double avgWt) {
        this.avgWt = avgWt;
    }
    
    public Double getGrossWt() {
        return grossWt;
    }
    
    public void setGrossWt(Double grossWt) {
        this.grossWt = grossWt;
    }
    
    public Long getLotId() {
        return lotId;
    }
    
    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Long getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public Integer getIsInvoice() {
        return isInvoice;
    }
    
    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }
    
    public Integer getIsScannedPacking() {
        return isScannedPacking;
    }
    
    public void setIsScannedPacking(Integer isScannedPacking) {
        this.isScannedPacking = isScannedPacking;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
}
