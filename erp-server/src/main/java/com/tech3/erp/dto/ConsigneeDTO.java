package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech3.erp.entity.Consignee;
import com.tech3.erp.util.AddressListDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsigneeDTO {

    private Long id;
    private String consigneeName;
    private String gstNo;
    private String panCard;
    private String mobileNo;
    private String email;
    private Boolean activeFlag;
    @JsonDeserialize(using = AddressListDeserializer.class)
    private List<AddressDTO> address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactPerson;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactNumber;

    public ConsigneeDTO() {}

    public ConsigneeDTO(Consignee consignee) {
        this.id = consignee.getId();
        this.consigneeName = consignee.getConsigneeName();
        this.gstNo = consignee.getGstno();
        this.panCard = consignee.getPancard();
        this.mobileNo = consignee.getMobileno();
        this.email = consignee.getEmail();
        this.activeFlag = consignee.getActiveFlag();
        this.contactPerson = consignee.getContactPerson();
        this.contactNumber = consignee.getContactNumber();
        if (consignee.getAddresses() != null && !consignee.getAddresses().isEmpty()) {
            this.address = consignee.getAddresses().stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else if (consignee.getAddress() != null) {
            this.address = new ArrayList<>();
            this.address.add(new AddressDTO(consignee.getAddress()));
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getConsigneeName() { return consigneeName; }
    public void setConsigneeName(String consigneeName) { this.consigneeName = consigneeName; }

    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }

    public String getPanCard() { return panCard; }
    public void setPanCard(String panCard) { this.panCard = panCard; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public List<AddressDTO> getAddress() { return address; }
    public void setAddress(List<AddressDTO> address) { this.address = address; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
