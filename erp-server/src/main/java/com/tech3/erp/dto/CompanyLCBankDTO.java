package com.tech3.erp.dto;

import com.tech3.erp.entity.CompanyLCBank;
import jakarta.validation.constraints.*;

public class CompanyLCBankDTO {

    private Long id;

    private String lcBankRefNo;

    private String dateOfIssue;

    private String lutNo;

    public CompanyLCBankDTO() {}

    public CompanyLCBankDTO(CompanyLCBank bank) {
        this.id = bank.getId();
        this.lcBankRefNo = bank.getLcBankRefNo();
        this.dateOfIssue = bank.getDateOfIssue();
        this.lutNo = bank.getLutNo();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLcBankRefNo() {
        return lcBankRefNo;
    }

    public void setLcBankRefNo(String lcBankRefNo) {
        this.lcBankRefNo = lcBankRefNo;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getLutNo() {
        return lutNo;
    }

    public void setLutNo(String lutNo) {
        this.lutNo = lutNo;
    }
}
