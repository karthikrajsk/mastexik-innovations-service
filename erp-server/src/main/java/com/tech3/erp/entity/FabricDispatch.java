package com.tech3.erp.entity;

import com.tech3.erp.util.ProcessTypeUtil;

import jakarta.persistence.*;

@Entity
@Table(name = "fabric_dispatch", schema = "masters")
@PrimaryKeyJoinColumn(name = "id")
public class FabricDispatch extends FabricDispatchForDyeing {

    // Fabric-specific fields can be added here
    @Column(name = "fabric_type")
    private String fabricType;
    
    @Column(name = "fabric_code")
    private String fabricCode;
    
    @Column(name = "roll_issue_type")
    private String rollIssueType; // Roll or Piece

@Column(name = "fabric_dispatch_id", unique = true)
    private String fabricDispatchId;

    // Default Constructor
    public FabricDispatch() {
        this.setProcessType(ProcessTypeUtil.FABRIC);
    }

    // Getters and Setters for fabric-specific fields
    public String getFabricType() { return fabricType; }
    public void setFabricType(String fabricType) { this.fabricType = fabricType; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public String getRollIssueType() { return rollIssueType; }
    public void setRollIssueType(String rollIssueType) { this.rollIssueType = rollIssueType; }

  public String getFabricDispatchId() {
        return fabricDispatchId;
    }

    public void setFabricDispatchId(String fabricDispatchId) {
        this.fabricDispatchId = fabricDispatchId;
    }
}