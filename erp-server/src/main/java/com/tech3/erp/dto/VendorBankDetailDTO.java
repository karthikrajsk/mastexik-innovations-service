package com.tech3.erp.dto;

public class VendorBankDetailDTO {

    private Long id;
    private String bankName;
    private String accountNo;
    private String ifscCode;

    public VendorBankDetailDTO() {}

    public VendorBankDetailDTO(Long id, String bankName, String accountNo, String ifscCode) {
        this.id = id;
        this.bankName = bankName;
        this.accountNo = accountNo;
        this.ifscCode = ifscCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
}
