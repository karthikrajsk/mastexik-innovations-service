package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "port", schema = "masters")
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "port_name", nullable = false)
    private String portName;

    @ManyToOne
    @JoinColumn(name = "port_type_id")
    private SubCategory portType;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

    public Port() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }

    public SubCategory getPortType() { return portType; }
    public void setPortType(SubCategory portType) { this.portType = portType; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
