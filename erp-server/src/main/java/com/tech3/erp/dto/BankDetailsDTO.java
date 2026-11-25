package com.tech3.erp.dto;

import com.tech3.erp.entity.BankDetails;
import jakarta.validation.constraints.NotBlank;

public class BankDetailsDTO {
    private Long id;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    private String branchName;

    // Bank document media fields - following Company pattern
    private String bankDocumentsUrl;
    private Long bankDocumentsMediaDetailSno;
    private Long bankDocumentsMediaSno;

    public BankDetailsDTO() {}

    public BankDetailsDTO(BankDetails entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.bankName = entity.getBankName();
            this.accountNumber = entity.getAccountNumber();
            this.ifscCode = entity.getIfscCode();
            this.branchName = entity.getBranchName();
            this.bankDocumentsUrl = entity.getBankDocumentsUrl();
            this.bankDocumentsMediaDetailSno = entity.getBankDocumentsMediaDetailSno();
            this.bankDocumentsMediaSno = entity.getBankDocumentsMediaSno();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankDocumentsUrl() {
        return bankDocumentsUrl;
    }

    public void setBankDocumentsUrl(String bankDocumentsUrl) {
        this.bankDocumentsUrl = bankDocumentsUrl;
    }

    public Long getBankDocumentsMediaDetailSno() {
        return bankDocumentsMediaDetailSno;
    }

    public void setBankDocumentsMediaDetailSno(Long bankDocumentsMediaDetailSno) {
        this.bankDocumentsMediaDetailSno = bankDocumentsMediaDetailSno;
    }

    public Long getBankDocumentsMediaSno() {
        return bankDocumentsMediaSno;
    }

    public void setBankDocumentsMediaSno(Long bankDocumentsMediaSno) {
        this.bankDocumentsMediaSno = bankDocumentsMediaSno;
    }
}
