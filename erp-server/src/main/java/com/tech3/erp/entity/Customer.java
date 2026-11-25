package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer", schema = "masters")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = true)
    private String gstno;

    @Column(nullable = true)
    private String pancard;

    @Column(nullable = true)
    private String mobileno;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String iecCode;

    @Column(nullable = true)
    private String cinNo;

    @Column(nullable = true)
    private String tinNo;

    @Column(nullable = true)
    private String ircNo;

    @Column(nullable = true)
    private String binNo;

    @Column(nullable = true)
    private String msmeUdyam;

    @Column(name = "contact_person", nullable = true)
    private String contactPerson;

    @Column(name = "contact_number", nullable = true)
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "customer_address",
        schema = "masters",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "trade_type_id")
    private CodesDetail tradeType;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getGstno() { return gstno; }
    public void setGstno(String gstno) { this.gstno = gstno; }

    public String getPancard() { return pancard; }
    public void setPancard(String pancard) { this.pancard = pancard; }

    public String getMobileno() { return mobileno; }
    public void setMobileno(String mobileno) { this.mobileno = mobileno; }

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

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public CodesDetail getTradeType() { return tradeType; }
    public void setTradeType(CodesDetail tradeType) { this.tradeType = tradeType; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
