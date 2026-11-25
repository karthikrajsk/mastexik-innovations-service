package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies", schema = "masters")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String companyName;
    
    @Column(nullable = false)
    private String companyType; // Domestic/Export/Both
    
    @Column(nullable = false, unique = true)
    private String companyCode;
    
    @Column(nullable = false, unique = true, length = 15)
    private String gstNo;
    
    @Column(nullable = false, length = 10)
    private String pan;
    
    private String cin;
    private String tin;
    private String iecCode;
    @Column(nullable = true) // Make sure this is nullable
    private String phoneNumber;
    private String email;
    private String website;
    private String msmeType;
    private String msmeNumber;
    private String logoUrl;

      private Long logoMediaDetailSno;
    private Long logoMediaSno;
    
    @Column(nullable = false)
    private Boolean activeFlag = true;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAddress> addresses = new ArrayList<>();



    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBank> banks = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyInvoice> invoiceItems = new ArrayList<>();

       @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
private List<CompanyLCBank> lcBanks = new ArrayList<>();


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getIecCode() {
        return iecCode;
    }

    public void setIecCode(String iecCode) {
        this.iecCode = iecCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMsmeType() {
        return msmeType;
    }

    public void setMsmeType(String msmeType) {
        this.msmeType = msmeType;
    }

    public String getMsmeNumber() {
        return msmeNumber;
    }

    public void setMsmeNumber(String msmeNumber) {
        this.msmeNumber = msmeNumber;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

public Long getLogoMediaDetailSno() {
        return logoMediaDetailSno;
    }

    public void setLogoMediaDetailSno(Long logoMediaDetailSno) {
        this.logoMediaDetailSno = logoMediaDetailSno;
    }

    public Long getLogoMediaSno() {
        return logoMediaSno;
    }

    public void setLogoMediaSno(Long logoMediaSno) {
        this.logoMediaSno = logoMediaSno;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<CompanyAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CompanyAddress> addresses) {
        this.addresses = addresses;
    }



    public List<CompanyBank> getBanks() {
        return banks;
    }

    public void setBanks(List<CompanyBank> banks) {
        this.banks = banks;
    }

    public List<CompanyInvoice> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<CompanyInvoice> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
      public List<CompanyLCBank> getLcBanks() {
        return lcBanks;
    }

    public void setLcBanks(List<CompanyLCBank> lcBanks) {
        this.lcBanks = lcBanks;
    }
}