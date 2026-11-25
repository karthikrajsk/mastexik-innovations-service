package com.tech3.erp.dto;

import com.tech3.erp.entity.Port;

public class PortDTO {
    private Long id;
    private String portName;
    private Long portTypeId;
    private String portTypeName;
    private Long cityId;
    private String cityName;
    private Long countryId;
    private String countryName;
    private boolean activeFlag;

    public PortDTO() {}

    public PortDTO(Port port) {
        this.id = port.getId();
        this.portName = port.getPortName();
        this.activeFlag = port.getActiveFlag();
        if (port.getPortType() != null) {
            this.portTypeId = port.getPortType().getId();
            this.portTypeName = port.getPortType().getSubCategoryName();
        }
        if (port.getCity() != null) {
            this.cityId = port.getCity().getId();
            this.cityName = port.getCity().getCityName();
        }
        if (port.getCountry() != null) {
            this.countryId = port.getCountry().getId();
            this.countryName = port.getCountry().getCountryName();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }

    public Long getPortTypeId() { return portTypeId; }
    public void setPortTypeId(Long portTypeId) { this.portTypeId = portTypeId; }

    public String getPortTypeName() { return portTypeName; }
    public void setPortTypeName(String portTypeName) { this.portTypeName = portTypeName; }

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public Long getCountryId() { return countryId; }
    public void setCountryId(Long countryId) { this.countryId = countryId; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
