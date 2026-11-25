package com.tech3.erp.dto;

public class GeneratePackingItemDTO {
    private Long id;
    private Long generatedPackingId;
    private String rollNo;
    private String pieceNo;
    private Double length;  // meters
    private Double yards;
    private Long uomId;
    private Double pounds;  // net weight
    private Double avgWt;
    private Double grossWt;
    private Long lotId;
    private String remarks;
    private Long invoiceId;
    private Integer isInvoice;
    private Integer isScannedPacking;
    private String invoiceNo;
    // Constructors
    public GeneratePackingItemDTO() {
    }

    public GeneratePackingItemDTO(Long id, Long generatedPackingId, String rollNo, 
            String pieceNo, Double length, Double yards, Long uomId, 
            Double pounds, Double avgWt, Double grossWt, 
            Long lotId, String remarks, Long invoiceId, Integer isInvoice, Integer isScannedPacking, String invoiceNo) {
        this.id = id;
        this.generatedPackingId = generatedPackingId;
        this.rollNo = rollNo;
        this.pieceNo = pieceNo;
        this.length = length;
        this.yards = yards;
        this.uomId = uomId;
        this.pounds = pounds;
        this.avgWt = avgWt;
        this.grossWt = grossWt;
        this.lotId = lotId;
        this.remarks = remarks;
        this.invoiceId = invoiceId;
        this.isInvoice = isInvoice;
        this.isScannedPacking = isScannedPacking;
        this.invoiceNo = invoiceNo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGeneratedPackingId() {
        return generatedPackingId;
    }

    public void setGeneratedPackingId(Long generatedPackingId) {
        this.generatedPackingId = generatedPackingId;
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