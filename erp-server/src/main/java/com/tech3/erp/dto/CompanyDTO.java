package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.Company;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyDTO {

    private Long id;

    @NotBlank(message = "Company Name is required")
    @Pattern(regexp = "^[A-Za-z0-9\\s.&-]+$", message = "Only letters, numbers, spaces, . & - allowed")
    private String companyName;

    @NotBlank(message = "Company Type is required")
    @Pattern(regexp = "^(Domestic|Export|Both)$", message = "Must be Domestic, Export, or Both")
    private String companyType;

    @NotBlank(message = "Company Code is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Alpha-numeric only")
    private String companyCode;

    @NotBlank(message = "GST No is required")
    @Pattern(regexp = "^[0-9]{2}[A-Z0-9]{13}$", message = "Invalid GST format")
    private String gstNo;

    @NotBlank(message = "PAN is required")
    @Size(min = 10, max = 10, message = "PAN must be 10 characters")
    private String pan;

    private String cin;
    private String tin;
    private String iecCode;

    @Pattern(regexp = "^$|^\\d{7,15}$", message = "Phone must be 7-15 digits or empty")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    private String website;
    private String msmeType;
    private String msmeNumber;
    private String logoUrl;
      private Long logoMediaDetailSno;
    private Long logoMediaSno;
    private Boolean activeFlag;

    @JsonProperty("address")
    @NotEmpty(message = "At least one address is required")
    private List<CompanyAddressDTO> address;



    @JsonProperty("bank")
    @NotEmpty(message = "At least one bank detail is required")
    private List<CompanyBankDTO> bank;

    @JsonProperty("lcBank")
    private List<CompanyLCBankDTO> lcBank;

    @JsonProperty("invoiceItems")
    private List<CompanyInvoiceDTO> invoiceItems;

    public CompanyDTO() {}

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.companyName = company.getCompanyName();
        this.companyType = company.getCompanyType();
        this.companyCode = company.getCompanyCode();
        this.gstNo = company.getGstNo();
        this.pan = company.getPan();
        this.cin = company.getCin();
        this.tin = company.getTin();
        this.iecCode = company.getIecCode();
        this.phoneNumber = company.getPhoneNumber();
        this.email = company.getEmail();
        this.website = company.getWebsite();
        this.msmeType = company.getMsmeType();
        this.msmeNumber = company.getMsmeNumber();
        this.logoUrl = company.getLogoUrl();
           this.logoMediaDetailSno = company.getLogoMediaDetailSno();
        this.logoMediaSno = company.getLogoMediaSno();
        this.activeFlag = company.getActiveFlag();

        if (company.getAddresses() != null) {
            this.address = company.getAddresses().stream()
                    .map(CompanyAddressDTO::new)
                    .collect(Collectors.toList());
        }



        if (company.getBanks() != null) {
            this.bank = company.getBanks().stream()
                    .map(CompanyBankDTO::new)
                    .collect(Collectors.toList());
        }
        if (company.getLcBanks() != null) {
            this.lcBank = company.getLcBanks().stream()
                    .map(CompanyLCBankDTO::new)
                    .collect(Collectors.toList());
        }

        if (company.getInvoiceItems() != null) {
            this.invoiceItems = company.getInvoiceItems().stream()
                    .map(CompanyInvoiceDTO::new)
                    .collect(Collectors.toList());
        }
         
    }

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

    public List<CompanyAddressDTO> getAddress() {
        return address;
    }

    public void setAddress(List<CompanyAddressDTO> address) {
        this.address = address;
    }



    public List<CompanyBankDTO> getBank() {
        return bank;
    }

    public void setBank(List<CompanyBankDTO> bank) {
        this.bank = bank;
    }

    public List<CompanyLCBankDTO> getLcBank() {
        return lcBank;
    }

    public void setLcBank(List<CompanyLCBankDTO> lcBank) {
        this.lcBank = lcBank;
    }

    public List<CompanyInvoiceDTO> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<CompanyInvoiceDTO> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}