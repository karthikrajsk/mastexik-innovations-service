package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mill", schema = "masters")
public class Mill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mill_name", nullable = false, length = 100)
    private String millName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mill_type_id")
    private SubCategory millType;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public SubCategory getMillType() {
        return millType;
    }

    public void setMillType(SubCategory millType) {
        this.millType = millType;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
