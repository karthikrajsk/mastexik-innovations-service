package com.tech3.erp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "flange_issue", schema = "masters")
public class FlangeIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flangeIssueNo;
    private Date flangeIssueDate;
    private Long vendorId;
    private Long consigneeId;
    private String vechileNo;
    private Long companyId;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "issued_company_id")
    private Long issuedCompanyId;

    @Column(name = "issued_to_internal", nullable = true, columnDefinition = "boolean default false")
    private boolean issuedToInternal = false;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy = "flangeIssue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FlangeIssueItem> items;

    @PrePersist
    public void prePersist() {
        if (this.flangeIssueNo == null || this.flangeIssueNo.isEmpty()) {
            this.flangeIssueNo = generateFlangeIssueNo();
        }
    }

    private String generateFlangeIssueNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "FIN-" + datePart + "-" + randomNum;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlangeIssueNo() {
        return flangeIssueNo;
    }

    public void setFlangeIssueNo(String flangeIssueNo) {
        this.flangeIssueNo = flangeIssueNo;
    }

    public Date getFlangeIssueDate() {
        return flangeIssueDate;
    }

    public void setFlangeIssueDate(Date flangeIssueDate) {
        this.flangeIssueDate = flangeIssueDate;
    }

    public Long getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Long consigneeId) {
        this.consigneeId = consigneeId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVechileNo() {
        return vechileNo;
    }

    public void setVechileNo(String vechileNo) {
        this.vechileNo = vechileNo;
    }

    public List<FlangeIssueItem> getItems() {
        return items;
    }

    public void setItems(List<FlangeIssueItem> items) {
        this.items = items;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getIssuedCompanyId() {
        return issuedCompanyId;
    }

    public void setIssuedCompanyId(Long issuedCompanyId) {
        this.issuedCompanyId = issuedCompanyId;
    }

    public boolean isIssuedToInternal() {
        return issuedToInternal;
    }

    public void setIssuedToInternal(boolean issuedToInternal) {
        this.issuedToInternal = issuedToInternal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}