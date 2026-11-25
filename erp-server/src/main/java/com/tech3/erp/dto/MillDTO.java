package com.tech3.erp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech3.erp.entity.Mill;
import com.tech3.erp.util.AddressListDeserializer;

import java.util.ArrayList;
import java.util.List;

public class MillDTO {
    private Long id;
    private String millName;
    private Long millTypeId;
    private String millTypeName;
    private String contactPerson;
    private String contactNumber;
    private Boolean activeFlag;
    
    @JsonDeserialize(using = AddressListDeserializer.class)
    private List<AddressDTO> address;

    public MillDTO() {}

    public MillDTO(Mill mill) {
        this.id = mill.getId();
        this.millName = mill.getMillName();
        if (mill.getMillType() != null) {
            this.millTypeId = mill.getMillType().getId();
            this.millTypeName = mill.getMillType().getSubCategoryName();
        }
        this.contactPerson = mill.getContactPerson();
        this.contactNumber = mill.getContactNumber();
        this.activeFlag = mill.getActiveFlag();
        if (mill.getAddress() != null) {
            this.address = new ArrayList<>();
            this.address.add(new AddressDTO(mill.getAddress()));
        }
    }

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

    public Long getMillTypeId() {
        return millTypeId;
    }

    public void setMillTypeId(Long millTypeId) {
        this.millTypeId = millTypeId;
    }

    public String getMillTypeName() {
        return millTypeName;
    }

    public void setMillTypeName(String millTypeName) {
        this.millTypeName = millTypeName;
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

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<AddressDTO> getAddress() {
        return address;
    }

    public void setAddress(List<AddressDTO> address) {
        this.address = address;
    }
}
