package com.tech3.erp.dto;

import com.tech3.erp.entity.CompanyBank;
import jakarta.validation.constraints.*;

public class CompanyBankDTO {

    private Long id;

    @NotBlank(message = "Bank Name is required")
    @Pattern(regexp = "^[A-Za-z\\s.&-]+$", message = "Alphabets only")
    private String bankName;

    @NotBlank(message = "Account No is required")
    @Pattern(regexp = "^\\d+$", message = "Numbers only")
    private String accountNo;

    @NotBlank(message = "IFSC Code is required")
    @Pattern(regexp = "^[A-Za-z0-9]{5,15}$", message = "Alpha-numeric, 5-15 characters")
    private String ifscCode;

    public CompanyBankDTO() {}

    public CompanyBankDTO(CompanyBank bank) {
        this.id = bank.getId();
        this.bankName = bank.getBankName();
        this.accountNo = bank.getAccountNo();
        this.ifscCode = bank.getIfscCode();
    }

    // Getters and Setters
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}