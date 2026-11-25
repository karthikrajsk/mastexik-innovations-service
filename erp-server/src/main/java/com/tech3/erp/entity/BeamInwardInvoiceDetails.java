package com.tech3.erp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "beam_inward_invoice_details", schema = "masters")
public class BeamInwardInvoiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inv_no")
    private String invNo;

    @Column(name = "inv_date")
    private String invDate;

    @Column(name = "sizing_cost")
    private Double sizingCost;

    @Column(name = "gst_amount")
    private Double gstAmount;

    @Column(name = "net_total")
    private Double netTotal;

    @Column(name = "media_detail_sno")
    private Long mediaDetailSno;

    @Column(name = "media_sno")
    private Long mediaSno;

    @Column(name = "invoice_upload")
    private String invoiceUpload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beam_inward_id")
    @JsonBackReference
    private BeamInward beamInward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public Double getSizingCost() {
        return sizingCost;
    }

    public void setSizingCost(Double sizingCost) {
        this.sizingCost = sizingCost;
    }

    public Double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(Double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public Long getMediaDetailSno() {
        return mediaDetailSno;
    }

    public void setMediaDetailSno(Long mediaDetailSno) {
        this.mediaDetailSno = mediaDetailSno;
    }

    public Long getMediaSno() {
        return mediaSno;
    }

    public void setMediaSno(Long mediaSno) {
        this.mediaSno = mediaSno;
    }

    public String getInvoiceUpload() {
        return invoiceUpload;
    }

    public void setInvoiceUpload(String invoiceUpload) {
        this.invoiceUpload = invoiceUpload;
    }

    public BeamInward getBeamInward() {
        return beamInward;
    }

    public void setBeamInward(BeamInward beamInward) {
        this.beamInward = beamInward;
    }
}
