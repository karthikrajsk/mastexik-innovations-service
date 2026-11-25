package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company_lc_banks", schema = "masters")
public class CompanyLCBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = true)
    private String lcBankRefNo;

    @Column(nullable = true)
    private String dateOfIssue;

    @Column(nullable = true)
    private String lutNo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
