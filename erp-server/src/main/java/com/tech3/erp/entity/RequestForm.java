package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_form", schema = "masters")
public class RequestForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "request_no", unique = true, nullable = false)
    private String requestNo;

    @Column(name = "customer_inquiry_no")
    private String customerInquiryNo;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "to_mill_id")
    private Long toMillId;

    @Column(name = "fabric_item_code")
    private String fabricItemCode;

    @Column
    private String construction;

    @Column
    private String category;

    @Column(name = "request_type")
    private String requestType;

    @Column
    private String uom;

    @Column
    private Double quantity;

    @Column
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @OneToMany(mappedBy = "requestForm", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private java.util.List<RequestFormRound> rounds;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getRequestNo() { return requestNo; }
    public void setRequestNo(String requestNo) { this.requestNo = requestNo; }

    public String getCustomerInquiryNo() { return customerInquiryNo; }
    public void setCustomerInquiryNo(String customerInquiryNo) { this.customerInquiryNo = customerInquiryNo; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public Long getToMillId() { return toMillId; }
    public void setToMillId(Long toMillId) { this.toMillId = toMillId; }

    public String getFabricItemCode() { return fabricItemCode; }
    public void setFabricItemCode(String fabricItemCode) { this.fabricItemCode = fabricItemCode; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public java.util.List<RequestFormRound> getRounds() { return rounds; }
    public void setRounds(java.util.List<RequestFormRound> rounds) { this.rounds = rounds; }
}
