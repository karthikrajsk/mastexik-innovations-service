package com.tech3.erp.dto;

import com.tech3.erp.entity.AgencyDispatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AgencyDispatchDTO {

    private Long id;

    @NotBlank(message = "Mill Invoice No is required")
    private String millInvoiceNo;

    @NotNull(message = "Invoice Date is required")
    private LocalDate invoiceDate;

    private String contractNo;

    @NotBlank(message = "Seller is required")
    private String seller;

    @NotBlank(message = "Buyer Name is required")
    private String buyerName;

    private String construction;

    @NotNull(message = "Dispatched Meters is required")
    private Double dispatchedMtrs;

    @NotNull(message = "Taxable Value is required")
    private Double taxableValue;

    private Double gstRate = 0.05;
    private Double gstValue;

    @NotNull(message = "Total Value is required")
    private Double totalValue;

    private String lrNo;
    private String transporterName;
    private String vehicleNo;
    private Double commissionEligibleValue;
    private String status = "Delivered";
    private String paymentStatus = "Pending";
    private String remarks;
    private Long companyId;
    private Boolean activeFlag = true;

    public AgencyDispatchDTO() {}

    public AgencyDispatchDTO(AgencyDispatch entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.millInvoiceNo = entity.getMillInvoiceNo();
            this.invoiceDate = entity.getInvoiceDate();
            this.contractNo = entity.getContractNo();
            this.seller = entity.getSeller();
            this.buyerName = entity.getBuyerName();
            this.construction = entity.getConstruction();
            this.dispatchedMtrs = entity.getDispatchedMtrs();
            this.taxableValue = entity.getTaxableValue();
            this.gstRate = entity.getGstRate() != null ? entity.getGstRate() : 0.05;
            this.gstValue = entity.getGstValue();
            this.totalValue = entity.getTotalValue();
            this.lrNo = entity.getLrNo();
            this.transporterName = entity.getTransporterName();
            this.vehicleNo = entity.getVehicleNo();
            this.commissionEligibleValue = entity.getCommissionEligibleValue() != null ? entity.getCommissionEligibleValue() : this.taxableValue;
            this.status = entity.getStatus() != null ? entity.getStatus() : "Delivered";
            this.paymentStatus = entity.getPaymentStatus() != null ? entity.getPaymentStatus() : "Pending";
            this.remarks = entity.getRemarks();
            this.companyId = entity.getCompanyId();
            this.activeFlag = entity.getActiveFlag() != null ? entity.getActiveFlag() : true;
        }
    }

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
