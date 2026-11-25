package com.tech3.erp.dto;

public class RequestFormRoundItemDTO {

    private Long id;
    private Long roundId;
    private String item;
    private String customerInstruction;
    private String approvalStatus;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRoundId() { return roundId; }
    public void setRoundId(Long roundId) { this.roundId = roundId; }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public String getCustomerInstruction() { return customerInstruction; }
    public void setCustomerInstruction(String customerInstruction) { this.customerInstruction = customerInstruction; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
}
