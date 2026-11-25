package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_details", schema = "masters")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", nullable = true)
    private String bankName;

    @Column(name = "account_number", nullable = true)
    private String accountNumber;

    @Column(name = "ifsc_code", nullable = true)
    private String ifscCode;

    @Column(name = "branch_name", nullable = true)
    private String branchName;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    // Bank document media fields - following Company pattern
    private String bankDocumentsUrl;
    private Long bankDocumentsMediaDetailSno;
    private Long bankDocumentsMediaSno;

    public BankDetails() {}

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
