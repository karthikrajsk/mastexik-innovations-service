package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.ItemInventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ItemInventoryDto {

    private Long id;
    private Long orderItemId;
    private String inwardNo;
    private Long companyId;
    private LocalDateTime inwardDate;
    private Long financialYearId;
    private String dcNo;
    private LocalDate dcDate;
    private Long orderId;
    private String orderNo;
    private Long vendorId;

    @JsonProperty("itemInwardStockDetails")
    private List<ItemInwardStockDetailsDto> itemInwardStockDetails;

    public ItemInventoryDto() {}

    public ItemInventoryDto(ItemInventory itemInventory) {
        this.id = itemInventory.getId();
        this.orderItemId = itemInventory.getOrderItemId();
        this.inwardNo = itemInventory.getInwardNo();
        this.companyId = itemInventory.getCompanyId();
        this.inwardDate = itemInventory.getInwardDate();
        this.dcNo = itemInventory.getDcNo();
        this.dcDate = itemInventory.getDcDate();
        this.orderId = itemInventory.getOrderId();
        this.orderNo = itemInventory.getOrderNo();
        this.vendorId = itemInventory.getVendorId();

        if (itemInventory.getDetails() != null) {
            this.itemInwardStockDetails = itemInventory.getDetails().stream()
                .map(ItemInwardStockDetailsDto::new)
                .collect(Collectors.toList());
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public String getInwardNo() { return inwardNo; }
    public void setInwardNo(String inwardNo) { this.inwardNo = inwardNo; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public LocalDateTime getInwardDate() { return inwardDate; }
    public void setInwardDate(LocalDateTime inwardDate) { this.inwardDate = inwardDate; }

    public Long getFinancialYearId() { return financialYearId; }
    public void setFinancialYearId(Long financialYearId) { this.financialYearId = financialYearId; }

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

    public List<ItemInwardStockDetailsDto> getItemInwardStockDetails() { return itemInwardStockDetails; }
    public void setItemInwardStockDetails(List<ItemInwardStockDetailsDto> itemInwardStockDetails) {
        this.itemInwardStockDetails = itemInwardStockDetails;
    }
}

