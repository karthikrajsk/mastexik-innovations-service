package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "item_inventory", schema = "masters")
public class ItemInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "inward_item_id")
    private Long inwardItemId;

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

    @OneToMany(mappedBy = "itemInventory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemInwardStockDetails> details;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInwardId() { return inwardId; }
    public void setInwardId(Long inwardId) { this.inwardId = inwardId; }

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public Long getInwardItemId() { return inwardItemId; }
    public void setInwardItemId(Long inwardItemId) { this.inwardItemId = inwardItemId; }

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

    public List<ItemInwardStockDetails> getDetails() { return details; }
    public void setDetails(List<ItemInwardStockDetails> details) {
        this.details = details;
        if (details != null) {
            for (ItemInwardStockDetails detail : details) {
                detail.setItemInventory(this);
            }
        }
    }
}

