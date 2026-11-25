package com.tech3.erp.dto;

import com.tech3.erp.entity.CommissionStatement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommissionStatementDTO {

    private Long id;

    @NotBlank(message = "Statement No is required")
    private String statementNo;

    @NotNull(message = "Statement Date is required")
    private LocalDate statementDate;

    @NotBlank(message = "Seller is required")
    private String seller;

    private String sellerAddress;
    private String sellerGst;
    private String period;
    private String sacCode = "996111";

    @NotNull(message = "Total Turnover is required")
    private Double totalTurnoverEligible;

    @NotNull(message = "Commission Rate is required")
    private Double commissionRate = 0.01;

    @NotNull(message = "Gross Commission is required")
    private Double grossCommission;

    private Double gstRate = 0.18;
    private Double cgstAmount = 0.0;
    private Double sgstAmount = 0.0;
    private Double igstAmount = 0.0;

    @NotNull(message = "Total Bill Amount is required")
    private Double totalBillAmount;

    private Double tds194HRate = 0.05;
    private Double tds194HAmount = 0.0;

    @NotNull(message = "Net Receivable is required")
    private Double netReceivable;

    private String status = "Billed";
    private LocalDate paymentReceivedDate;
    private String paymentMode;
    private String referenceNo;
    private String bankAccount;
    private String remarks;
    private Long companyId;
    private Boolean activeFlag = true;

    private List<CommissionStatementItemDTO> items = new ArrayList<>();

    public CommissionStatementDTO() {}

    public CommissionStatementDTO(CommissionStatement entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.statementNo = entity.getStatementNo();
            this.statementDate = entity.getStatementDate();
            this.seller = entity.getSeller();
            this.sellerAddress = entity.getSellerAddress();
            this.sellerGst = entity.getSellerGst();
            this.period = entity.getPeriod();
            this.sacCode = entity.getSacCode() != null ? entity.getSacCode() : "996111";
            this.totalTurnoverEligible = entity.getTotalTurnoverEligible();
            this.commissionRate = entity.getCommissionRate() != null ? entity.getCommissionRate() : 0.01;
            this.grossCommission = entity.getGrossCommission();
            this.gstRate = entity.getGstRate() != null ? entity.getGstRate() : 0.18;
            this.cgstAmount = entity.getCgstAmount() != null ? entity.getCgstAmount() : 0.0;
            this.sgstAmount = entity.getSgstAmount() != null ? entity.getSgstAmount() : 0.0;
            this.igstAmount = entity.getIgstAmount() != null ? entity.getIgstAmount() : 0.0;
            this.totalBillAmount = entity.getTotalBillAmount();
            this.tds194HRate = entity.getTds194HRate() != null ? entity.getTds194HRate() : 0.05;
            this.tds194HAmount = entity.getTds194HAmount() != null ? entity.getTds194HAmount() : 0.0;
            this.netReceivable = entity.getNetReceivable();
            this.status = entity.getStatus() != null ? entity.getStatus() : "Billed";
            this.paymentReceivedDate = entity.getPaymentReceivedDate();
            this.paymentMode = entity.getPaymentMode();
            this.referenceNo = entity.getReferenceNo();
            this.bankAccount = entity.getBankAccount();
            this.remarks = entity.getRemarks();
            this.companyId = entity.getCompanyId();
            this.activeFlag = entity.getActiveFlag() != null ? entity.getActiveFlag() : true;

            if (entity.getItems() != null) {
                this.items = entity.getItems().stream()
                        .map(CommissionStatementItemDTO::new)
                        .collect(Collectors.toList());
            }
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatementNo() { return statementNo; }
    public void setStatementNo(String statementNo) { this.statementNo = statementNo; }

    public LocalDate getStatementDate() { return statementDate; }
    public void setStatementDate(LocalDate statementDate) { this.statementDate = statementDate; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public String getSellerAddress() { return sellerAddress; }
    public void setSellerAddress(String sellerAddress) { this.sellerAddress = sellerAddress; }

    public String getSellerGst() { return sellerGst; }
    public void setSellerGst(String sellerGst) { this.sellerGst = sellerGst; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getSacCode() { return sacCode; }
    public void setSacCode(String sacCode) { this.sacCode = sacCode; }

    public Double getTotalTurnoverEligible() { return totalTurnoverEligible; }
    public void setTotalTurnoverEligible(Double totalTurnoverEligible) { this.totalTurnoverEligible = totalTurnoverEligible; }

    public Double getCommissionRate() { return commissionRate; }
    public void setCommissionRate(Double commissionRate) { this.commissionRate = commissionRate; }

    public Double getGrossCommission() { return grossCommission; }
    public void setGrossCommission(Double grossCommission) { this.grossCommission = grossCommission; }

    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

    public Double getCgstAmount() { return cgstAmount; }
    public void setCgstAmount(Double cgstAmount) { this.cgstAmount = cgstAmount; }

    public Double getSgstAmount() { return sgstAmount; }
    public void setSgstAmount(Double sgstAmount) { this.sgstAmount = sgstAmount; }

    public Double getIgstAmount() { return igstAmount; }
    public void setIgstAmount(Double igstAmount) { this.igstAmount = igstAmount; }

    public Double getTotalBillAmount() { return totalBillAmount; }
    public void setTotalBillAmount(Double totalBillAmount) { this.totalBillAmount = totalBillAmount; }

    public Double getTds194HRate() { return tds194HRate; }
    public void setTds194HRate(Double tds194HRate) { this.tds194HRate = tds194HRate; }

    public Double getTds194HAmount() { return tds194HAmount; }
    public void setTds194HAmount(Double tds194HAmount) { this.tds194HAmount = tds194HAmount; }

    public Double getNetReceivable() { return netReceivable; }
    public void setNetReceivable(Double netReceivable) { this.netReceivable = netReceivable; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getPaymentReceivedDate() { return paymentReceivedDate; }
    public void setPaymentReceivedDate(LocalDate paymentReceivedDate) { this.paymentReceivedDate = paymentReceivedDate; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public List<CommissionStatementItemDTO> getItems() { return items; }
    public void setItems(List<CommissionStatementItemDTO> items) { this.items = items; }
}
