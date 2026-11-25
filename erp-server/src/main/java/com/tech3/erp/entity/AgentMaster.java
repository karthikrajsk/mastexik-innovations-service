package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "agent_master", schema = "masters")
public class AgentMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_code")
    private String agentCode;

    @Column(name = "agent_name", nullable = false)
    private String agentName;

    @Column(name = "agent_type")
    private String agentType; // "Yarn Agent", "Grey Fabric Agent", "Finish Fabric Agent", "General Indentor"

    @Column(name = "agent_percentage", nullable = false)
    private Double agentPercentage;

    @Column(name = "commission_type")
    private String commissionType = "PERCENTAGE"; // "PERCENTAGE", "PER_METER", "FIXED"

    @Column(name = "sac_code")
    private String sacCode = "996111"; // SAC for Brokerage services

    @Column(name = "pan_no")
    private String panNo; // Required for TDS Section 194H

    @Column(name = "gstin")
    private String gstin; // GST Identification Number

    @Column(name = "contact_no", nullable = false)
    private String contactNo;

    @Column(name = "alternate_contact_no")
    private String alternateContactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

    // Getters and Setters

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
