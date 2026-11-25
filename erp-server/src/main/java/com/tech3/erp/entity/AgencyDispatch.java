package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "agency_dispatch", schema = "masters")
public class AgencyDispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mill_invoice_no", nullable = false)
    private String millInvoiceNo;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "seller", nullable = false)
    private String seller;

    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @Column(name = "construction", length = 500)
    private String construction;

    @Column(name = "dispatched_mtrs", nullable = false)
    private Double dispatchedMtrs;

    @Column(name = "taxable_value", nullable = false)
    private Double taxableValue;

    @Column(name = "gst_rate")
    private Double gstRate = 0.05;

    @Column(name = "gst_value")
    private Double gstValue;

    @Column(name = "total_value", nullable = false)
    private Double totalValue;

    @Column(name = "lr_no")
    private String lrNo;

    @Column(name = "transporter_name")
    private String transporterName;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "commission_eligible_value")
    private Double commissionEligibleValue;

    @Column(name = "status")
    private String status = "Delivered"; // Delivered, In-Transit, Cancelled

    @Column(name = "payment_status")
    private String paymentStatus = "Pending"; // Pending, Partially Paid, Fully Paid

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public AgencyDispatch() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMillInvoiceNo() { return millInvoiceNo; }
    public void setMillInvoiceNo(String millInvoiceNo) { this.millInvoiceNo = millInvoiceNo; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getContractNo() { return contractNo; }
    public void setContractNo(String contractNo) { this.contractNo = contractNo; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public Double getDispatchedMtrs() { return dispatchedMtrs; }
    public void setDispatchedMtrs(Double dispatchedMtrs) { this.dispatchedMtrs = dispatchedMtrs; }

    public Double getTaxableValue() { return taxableValue; }
    public void setTaxableValue(Double taxableValue) { this.taxableValue = taxableValue; }

    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

    public Double getGstValue() { return gstValue; }
    public void setGstValue(Double gstValue) { this.gstValue = gstValue; }

    public Double getTotalValue() { return totalValue; }
    public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }

    public String getLrNo() { return lrNo; }
    public void setLrNo(String lrNo) { this.lrNo = lrNo; }

    public String getTransporterName() { return transporterName; }
    public void setTransporterName(String transporterName) { this.transporterName = transporterName; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public Double getCommissionEligibleValue() { return commissionEligibleValue; }
    public void setCommissionEligibleValue(Double commissionEligibleValue) { this.commissionEligibleValue = commissionEligibleValue; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}
