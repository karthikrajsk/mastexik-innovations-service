package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "item_outward", schema = "masters")
public class ItemOutward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "outward_date", nullable = false)
    private LocalDate outwardDate;

    @Column(name = "outward_no", unique = true, nullable = false)
    private String outwardNo;

    @Column(name = "item_vendor_name")
    private String itemVendorName;

    @Column(name = "department")
    private String department;

    @Column(name = "machine_name")
    private String machineName;

    @Column(name = "warehouse", nullable = false)
    private String warehouse;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "financial_year_id")
    private String financialYearId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @OneToMany(mappedBy = "itemOutward", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ItemOutwardStockDetails> itemOutwardStockDetails = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public String getOutwardNo() {
        return outwardNo;
    }

    public void setOutwardNo(String outwardNo) {
        this.outwardNo = outwardNo;
    }

    public String getItemVendorName() {
        return itemVendorName;
    }

    public void setItemVendorName(String itemVendorName) {
        this.itemVendorName = itemVendorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(String financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<ItemOutwardStockDetails> getItemOutwardStockDetails() {
        return itemOutwardStockDetails;
    }

    public void setItemOutwardStockDetails(List<ItemOutwardStockDetails> itemOutwardStockDetails) {
        this.itemOutwardStockDetails = itemOutwardStockDetails;
        if (itemOutwardStockDetails != null) {
            for (ItemOutwardStockDetails detail : itemOutwardStockDetails) {
                detail.setItemOutward(this);
            }
        }
    }
}

