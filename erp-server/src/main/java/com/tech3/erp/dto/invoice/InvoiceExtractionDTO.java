package com.tech3.erp.dto.invoice;

public class InvoiceExtractionDTO {
    private String invNo;
    private String invDate;
    private Double sizingCost;
    private Double gstAmount;
    private Double netTotal;

    public InvoiceExtractionDTO() {}

    public InvoiceExtractionDTO(String invNo, String invDate, Double sizingCost, Double gstAmount, Double netTotal) {
        this.invNo = invNo;
        this.invDate = invDate;
        this.sizingCost = sizingCost;
        this.gstAmount = gstAmount;
        this.netTotal = netTotal;
    }

    public String getInvNo() { return invNo; }
    public void setInvNo(String invNo) { this.invNo = invNo; }

    public String getInvDate() { return invDate; }
    public void setInvDate(String invDate) { this.invDate = invDate; }

    public Double getSizingCost() { return sizingCost; }
    public void setSizingCost(Double sizingCost) { this.sizingCost = sizingCost; }

    public Double getGstAmount() { return gstAmount; }
    public void setGstAmount(Double gstAmount) { this.gstAmount = gstAmount; }

    public Double getNetTotal() { return netTotal; }
    public void setNetTotal(Double netTotal) { this.netTotal = netTotal; }
}
