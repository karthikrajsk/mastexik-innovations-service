package com.tech3.erp.dto;

public class SalesOrderConsigneeAddressDTO {

    private Long consigneeId;
    private String consigneeName;
    private String gstNo;
    private Long addressId;
    private String addressLine1;
    private String addressLine2;
    private String cityName;
    private String stateName;
    private String stateCode;
    private String countryName;
    private Long pinCode;

    public SalesOrderConsigneeAddressDTO() {}

    public Long getConsigneeId() { return consigneeId; }
    public void setConsigneeId(Long consigneeId) { this.consigneeId = consigneeId; }

    public String getConsigneeName() { return consigneeName; }
    public void setConsigneeName(String consigneeName) { this.consigneeName = consigneeName; }

    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public Long getPinCode() { return pinCode; }
    public void setPinCode(Long pinCode) { this.pinCode = pinCode; }
}
