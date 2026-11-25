package com.tech3.erp.dto;

public class BeamInwardInvoiceDetailsDTO {
    private Long id;
    private String invNo;
    private String invDate;
    private Double sizingCost;
    private Double gstAmount;
    private Double netTotal;
    private Long mediaDetailSno;
    private Long mediaSno;
    private String invoiceUpload;

    public BeamInwardInvoiceDetailsDTO() {}

    public BeamInwardInvoiceDetailsDTO(String invNo, String invDate, Double sizingCost, Double gstAmount, Double netTotal) {
        this.invNo = invNo;
        this.invDate = invDate;
        this.sizingCost = sizingCost;
        this.gstAmount = gstAmount;
        this.netTotal = netTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Long getMediaDetailSno() { return mediaDetailSno; }
    public void setMediaDetailSno(Long mediaDetailSno) { this.mediaDetailSno = mediaDetailSno; }

    public Long getMediaSno() { return mediaSno; }
    public void setMediaSno(Long mediaSno) { this.mediaSno = mediaSno; }

    public String getInvoiceUpload() { return invoiceUpload; }
    public void setInvoiceUpload(String invoiceUpload) { this.invoiceUpload = invoiceUpload; }
}
