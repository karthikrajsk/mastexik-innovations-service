package com.tech3.erp.dto;

import com.tech3.erp.entity.AgentMaster;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class AgentMasterDTO {
    private Long id;

    private String agentCode;

    @NotBlank(message = "Agent Name is required")
    private String agentName;

    private String agentType; // "Yarn Agent", "Grey Fabric Agent", "Finish Fabric Agent", "General Indentor"

    @NotNull(message = "Agent Percentage is required")
    @Positive(message = "Agent Percentage must be a positive number")
    private Double agentPercentage;

    private String commissionType = "PERCENTAGE";

    private String sacCode = "996111";

    @Pattern(regexp = "^$|^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "PAN must be a valid 10-character PAN (e.g. AAACH1290P)")
    private String panNo;

    @Pattern(regexp = "^$|^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message = "GSTIN must be a valid 15-character GSTIN")
    private String gstin;

    @NotBlank(message = "Contact No is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Contact No must be a valid phone number (10-15 digits)")
    private String contactNo;

    private String alternateContactNo;

    @Email(message = "Email must be a valid email address")
    private String email;

    private String address;
    private String city;
    private String state;
    private String pincode;

    private String bankName;
    private String accountNo;
    private String ifscCode;
    private String branchName;

    private boolean activeFlag = true;

    public AgentMasterDTO() {}

    public AgentMasterDTO(AgentMaster agentMaster) {
        if (agentMaster != null) {
            this.id = agentMaster.getId();
            this.agentCode = agentMaster.getAgentCode();
            this.agentName = agentMaster.getAgentName();
            this.agentType = agentMaster.getAgentType();
            this.agentPercentage = agentMaster.getAgentPercentage();
            this.commissionType = agentMaster.getCommissionType();
            this.sacCode = agentMaster.getSacCode();
            this.panNo = agentMaster.getPanNo();
            this.gstin = agentMaster.getGstin();
            this.contactNo = agentMaster.getContactNo();
            this.alternateContactNo = agentMaster.getAlternateContactNo();
            this.email = agentMaster.getEmail();
            this.address = agentMaster.getAddress();
            this.city = agentMaster.getCity();
            this.state = agentMaster.getState();
            this.pincode = agentMaster.getPincode();
            this.bankName = agentMaster.getBankName();
            this.accountNo = agentMaster.getAccountNo();
            this.ifscCode = agentMaster.getIfscCode();
            this.branchName = agentMaster.getBranchName();
            this.activeFlag = agentMaster.getActiveFlag();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAgentCode() { return agentCode; }
    public void setAgentCode(String agentCode) { this.agentCode = agentCode; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getAgentType() { return agentType; }
    public void setAgentType(String agentType) { this.agentType = agentType; }

    public Double getAgentPercentage() { return agentPercentage; }
    public void setAgentPercentage(Double agentPercentage) { this.agentPercentage = agentPercentage; }

    public String getCommissionType() { return commissionType; }
    public void setCommissionType(String commissionType) { this.commissionType = commissionType; }

    public String getSacCode() { return sacCode; }
    public void setSacCode(String sacCode) { this.sacCode = sacCode; }

    public String getPanNo() { return panNo; }
    public void setPanNo(String panNo) { this.panNo = panNo; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getAlternateContactNo() { return alternateContactNo; }
    public void setAlternateContactNo(String alternateContactNo) { this.alternateContactNo = alternateContactNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
