package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "yarn_outward", schema = "masters")
public class YarnOutward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outward_type_id", nullable = false)
    private Long outwardTypeId;

    @Column(name = "outward_no", unique = true, nullable = false)
    private String outwardNo;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "outward_date", nullable = false)
    private LocalDate outwardDate;

    @Column(name = "financial_year_id")
    private String financialYearId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @OneToMany(mappedBy = "yarnOutward", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<YarnOutwardStockDetails> yarnOutwardStockDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
    }

    public String getOutwardNo() {
        return outwardNo;
    }

    public void setOutwardNo(String outwardNo) {
        this.outwardNo = outwardNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public String getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(String financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<YarnOutwardStockDetails> getYarnOutwardStockDetails() {
        return yarnOutwardStockDetails;
    }

    public void setYarnOutwardStockDetails(List<YarnOutwardStockDetails> yarnOutwardStockDetails) {
        this.yarnOutwardStockDetails.clear();
        if (yarnOutwardStockDetails != null) {
            for (YarnOutwardStockDetails detail : yarnOutwardStockDetails) {
                detail.setYarnOutward(this);
                this.yarnOutwardStockDetails.add(detail);
            }
        }
    }
}
