package com.tech3.erp.dto;

import com.tech3.erp.entity.CompanyAddress;
import jakarta.validation.constraints.*;

public class CompanyAddressDTO {

    private Long id;

    @NotBlank(message = "Address is required")
    private String line1;

    @NotNull(message = "State ID is required")
    private Long stateId;

    @NotBlank(message = "State Code is required")
    private String stateCode;

    @NotBlank(message = "Pin Code is required")
    @Pattern(regexp = "^\\d{6}$", message = "Pin Code must be 6 digits")
    private String pinCode;

    @NotBlank(message = "Address Type is required")
    @Pattern(regexp = "^(Domestic|Export|Both)$", message = "Must be Domestic, Export, or Both")
    private String addressType;

    @NotNull(message = "isDefault flag is required")
    private Boolean isDefault;

    public CompanyAddressDTO() {}

    public CompanyAddressDTO(CompanyAddress address) {
        this.id = address.getId();
        this.line1 = address.getLine1();
        this.stateId = address.getState() != null ? address.getState().getId() : null;
        this.stateCode = address.getState() != null ? address.getState().getStateCode() : null;
        this.pinCode = address.getPinCode();
        this.addressType = address.getAddressType();
        this.isDefault = address.getIsDefault();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}