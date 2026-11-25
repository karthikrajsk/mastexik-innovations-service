package com.tech3.erp.dto;

import java.util.Date;
import java.util.List;

public class FlangeIssueDTO {

    private Long id;
    private String flangeIssueNo;
    private Date flangeIssueDate;
    private Long vendorId;
    private Long consigneeId;
    private String vechileNo;
    private Long companyId;
    private Long orderTypeId;
    private Long issuedCompanyId;
    private boolean issuedToInternal = false;
    private String remarks;

    private List<FlangeIssueItemDTO> items;
    private List<Long> removedFlangeIds;

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

    public List<FlangeIssueItemDTO> getItems() {
        return items;
    }

    public void setItems(List<FlangeIssueItemDTO> items) {
        this.items = items;
    }

    public List<Long> getRemovedFlangeIds() {
        return removedFlangeIds;
    }

    public void setRemovedFlangeIds(List<Long> removedFlangeIds) {
        this.removedFlangeIds = removedFlangeIds;
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