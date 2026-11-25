package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "yarn_inventory", schema = "masters")
public class YarnInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "inward_type_id")
    private Long inwardTypeId;

    @Column(name = "inward_no")
    private String inwardNo;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "inward_date")
    private LocalDateTime inwardDate;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "dc_no")
    private String dcNo;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @OneToMany(mappedBy = "yarnInventory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<YarnInwardStockDetails> details = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInwardId() { return inwardId; }
    public void setInwardId(Long inwardId) { this.inwardId = inwardId; }

    public Long getInwardTypeId() { return inwardTypeId; }
    public void setInwardTypeId(Long inwardTypeId) { this.inwardTypeId = inwardTypeId; }

    public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public LocalDateTime getInwardDate() { return inwardDate; }
    public void setInwardDate(LocalDateTime inwardDate) { this.inwardDate = inwardDate; }

    public String getFinancialYear() { return financialYear; }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }

    public String getDcNo() { return dcNo; }
    public void setDcNo(String dcNo) { this.dcNo = dcNo; }

    public LocalDate getDcDate() { return dcDate; }
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public List<YarnInwardStockDetails> getDetails() { return details; }
    public void setDetails(List<YarnInwardStockDetails> details) {
        this.details.clear();
        if (details != null) {
            for (YarnInwardStockDetails detail : details) {
                detail.setYarnInventory(this);
                this.details.add(detail);
            }
        }
    }
}
