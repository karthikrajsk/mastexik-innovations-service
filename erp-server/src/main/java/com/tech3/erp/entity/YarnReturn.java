package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "yarn_return", schema = "masters")
public class YarnReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inward_date", nullable = false)
    private LocalDate inwardDate;

    @Column(name = "inward_number", unique = true, nullable = false)
    private String inwardNumber;

    @Column(name = "inward_type", nullable = false)
    private String inwardType;

    @Column(name = "order_no", nullable = false)
    private String orderNo;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "issued_qty", nullable = false)
    private Double issuedQty;

    @Column(name = "remarks")
    private String remarks;

    // ✅ Newly added fields
    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany(mappedBy = "yarnReturn", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("yarn-return-items")
    private List<YarnReturnItem> yarnReturnItems = new ArrayList<>();

    @OneToMany(mappedBy = "yarnReturn", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("yarn-return-requirements")
    private List<YarnReturnRequirement> yarnReturnRequirements = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(LocalDate inwardDate) {
        this.inwardDate = inwardDate;
    }

    public String getInwardNumber() {
        return inwardNumber;
    }

    public void setInwardNumber(String inwardNumber) {
        this.inwardNumber = inwardNumber;
    }

    public String getInwardType() {
        return inwardType;
    }

    public void setInwardType(String inwardType) {
        this.inwardType = inwardType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Double getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(Double issuedQty) {
        this.issuedQty = issuedQty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(String dcNumber) {
        this.dcNumber = dcNumber;
    }

    public LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDate dcDate) {
        this.dcDate = dcDate;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<YarnReturnItem> getYarnReturnItems() {
        return yarnReturnItems;
    }

    public void setYarnReturnItems(List<YarnReturnItem> yarnReturnItems) {
        this.yarnReturnItems.clear();
        if (yarnReturnItems != null) {
            for (YarnReturnItem item : yarnReturnItems) {
                item.setYarnReturn(this);
                this.yarnReturnItems.add(item);
            }
        }
    }

    public List<YarnReturnRequirement> getYarnReturnRequirements() {
        return yarnReturnRequirements;
    }

    public void setYarnReturnRequirements(List<YarnReturnRequirement> yarnReturnRequirements) {
        this.yarnReturnRequirements.clear();
        if (yarnReturnRequirements != null) {
            for (YarnReturnRequirement requirement : yarnReturnRequirements) {
                requirement.setYarnReturn(this);
                this.yarnReturnRequirements.add(requirement);
            }
        }
    }
}
