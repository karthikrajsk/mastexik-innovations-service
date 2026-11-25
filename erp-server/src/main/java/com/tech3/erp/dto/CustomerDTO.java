package com.tech3.erp.dto;

import com.tech3.erp.entity.Customer;

public class CustomerDTO {

    private Long id;
    private String customerName;
    private String gstNo;
    private String panCard;
    private String mobileNo;
    private String email;
    private String iecCode;
    private String cinNo;
    private String tinNo;
    private String ircNo;
    private String binNo;
    private String msmeUdyam;
    private Boolean activeFlag;
    private String contactPerson;
    private String contactNumber;
    private AddressDTO address;
    private Long tradeTypeId;

    public CustomerDTO() {}

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.customerName = customer.getCustomerName();
        this.gstNo = customer.getGstno();
        this.panCard = customer.getPancard();
        this.mobileNo = customer.getMobileno();
        this.iecCode = customer.getIecCode();
        this.cinNo = customer.getCinNo();
        this.tinNo = customer.getTinNo();
        this.ircNo = customer.getIrcNo();
        this.binNo = customer.getBinNo();
        this.msmeUdyam = customer.getMsmeUdyam();
        this.email = customer.getEmail();
        this.activeFlag = customer.getActiveFlag();
        this.contactPerson = customer.getContactPerson();
        this.contactNumber = customer.getContactNumber();
        this.tradeTypeId = customer.getTradeType() != null ? customer.getTradeType().getId() : null;

        if (customer.getAddress() != null) {
            this.address = new AddressDTO(customer.getAddress());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }

    public String getPanCard() { return panCard; }
    public void setPanCard(String panCard) { this.panCard = panCard; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIecCode() { return iecCode; }
    public void setIecCode(String iecCode) { this.iecCode = iecCode; }

    public String getCinNo() { return cinNo; }
    public void setCinNo(String cinNo) { this.cinNo = cinNo; }

    public String getTinNo() { return tinNo; }
    public void setTinNo(String tinNo) { this.tinNo = tinNo; }

    public String getIrcNo() { return ircNo; }
    public void setIrcNo(String ircNo) { this.ircNo = ircNo; }

    public String getBinNo() { return binNo; }
    public void setBinNo(String binNo) { this.binNo = binNo; }

    public String getMsmeUdyam() { return msmeUdyam; }
    public void setMsmeUdyam(String msmeUdyam) { this.msmeUdyam = msmeUdyam; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public AddressDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }

    public Long getTradeTypeId() { return tradeTypeId; }
    public void setTradeTypeId(Long tradeTypeId) { this.tradeTypeId = tradeTypeId; }
}
