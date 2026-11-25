package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.YarnInventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class YarnInventoryDto {

    private Long id;
    private Long inwardId;
    private Long inwardTypeId;
    private String inwardNo;
    private Long companyId;
    private LocalDateTime inwardDate;
    private Long financialYearId;
    private String dcNo;
    private LocalDate dcDate;
    private Long orderId;
    private String orderNo;
    private Long vendorId;
    private Long warehouseId;

    @JsonProperty("yarnInwardStockDetails")
    private List<YarnInwardStockDetailsDto> yarnInwardStockDetails;

    public YarnInventoryDto() {}

    public YarnInventoryDto(YarnInventory yarnInventory) {
        this.id = yarnInventory.getId();
        this.inwardId = yarnInventory.getInwardId();
        this.inwardTypeId = yarnInventory.getInwardTypeId();
        this.inwardNo = yarnInventory.getInwardNo();
        this.companyId = yarnInventory.getCompanyId();
        this.inwardDate = yarnInventory.getInwardDate();
      
        this.dcNo = yarnInventory.getDcNo();
        this.dcDate = yarnInventory.getDcDate();
        this.orderId = yarnInventory.getOrderId();
        this.orderNo = yarnInventory.getOrderNo();
        this.vendorId = yarnInventory.getVendorId();
        this.warehouseId = yarnInventory.getWarehouseId();

        if (yarnInventory.getDetails() != null) {
            this.yarnInwardStockDetails = yarnInventory.getDetails().stream()
                .map(YarnInwardStockDetailsDto::new)
                .collect(Collectors.toList());
        }
    }

    // Getters and setters
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

    public List<YarnInwardStockDetailsDto> getYarnInwardStockDetails() { return yarnInwardStockDetails; }
    public void setYarnInwardStockDetails(List<YarnInwardStockDetailsDto> yarnInwardStockDetails) {
        this.yarnInwardStockDetails = yarnInwardStockDetails;
    }
}