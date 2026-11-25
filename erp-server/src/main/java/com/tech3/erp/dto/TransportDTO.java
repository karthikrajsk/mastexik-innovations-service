package com.tech3.erp.dto;

import com.tech3.erp.entity.Transport;

public class TransportDTO {
    private Long id;
    private String transportName;
    private String gstNo;
    private AddressDTO address;
    private boolean activeFlag;

    public TransportDTO() {}

    public TransportDTO(Transport transport) {
        this.id = transport.getId();
        this.transportName = transport.getTransportName();
        this.gstNo = transport.getGstNo();
        this.activeFlag = transport.getActiveFlag();
        if (transport.getAddress() != null) {
            this.address = new AddressDTO(transport.getAddress());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransportName() { return transportName; }
    public void setTransportName(String transportName) { this.transportName = transportName; }

    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }

    public AddressDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
