package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company_print_contexts", schema = "masters")
public class CompanyPrintContext {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String screenName; // SO/PO/Weaving Contract/etc.

    @Column(nullable = false)
    private String parameter1; // Prefix/Financial Year/Order Number/Invoice Number

    @Column(nullable = false)
    private String parameter2;

    @Column(nullable = false)
    private String parameter3;

    @Column(nullable = false, length = 1)
    private String separator; // - or /
    
    @Column(nullable = false)
    private String sequenceNumber = "0001";

    @Column(name = "prefix_name")
    private String prefixName; // optional, new column


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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
    
    public String getSequenceNumber() { return sequenceNumber; }
    public void setSequenceNumber(String sequenceNumber) { this.sequenceNumber = sequenceNumber; }

    public String getPrefixName() { return prefixName; }
    public void setPrefixName(String prefixName) { this.prefixName = prefixName; }

}