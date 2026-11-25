package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transport", schema = "masters")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transport_name", nullable = false)
    private String transportName;

    @Column(name = "gst_no")
    private String gstNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

    public Transport() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransportName() { return transportName; }
    public void setTransportName(String transportName) { this.transportName = transportName; }

    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
