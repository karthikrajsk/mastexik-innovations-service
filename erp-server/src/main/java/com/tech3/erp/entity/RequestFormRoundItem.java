package com.tech3.erp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "request_form_round_item", schema = "masters")
public class RequestFormRoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    @JsonBackReference
    private RequestFormRound round;

    @Column(name = "item")
    private String item;

    @Column(name = "customer_instruction", columnDefinition = "TEXT")
    private String customerInstruction;

    @Column(name = "approval_status")
    private String approvalStatus;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RequestFormRound getRound() { return round; }
    public void setRound(RequestFormRound round) { this.round = round; }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public String getCustomerInstruction() { return customerInstruction; }
    public void setCustomerInstruction(String customerInstruction) { this.customerInstruction = customerInstruction; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
}
