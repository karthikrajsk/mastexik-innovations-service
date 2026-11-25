package com.tech3.erp.dto;

import com.tech3.erp.entity.ItemOutward;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemOutwardDTO {

    private Long id;
    private LocalDate outwardDate;
    private String outwardNo;
    private String itemVendorName;
    private String department;
    private String machineNo;
    private String machineName;
    private String warehouse;
    private Long warehouseId;
    private Long vendorId;
    private Long companyId;
    private List<ItemOutwardStockDetailsDTO> items; // Alternative field name for itemOutwardStockDetails
    private String financialYearId;
    private Boolean activeFlag;
    private List<ItemOutwardStockDetailsDTO> itemOutwardStockDetails;

    public ItemOutwardDTO() {
        this.itemOutwardStockDetails = new ArrayList<>();
    }

    public ItemOutwardDTO(ItemOutward entity) {
        if (entity == null) return;
        
        this.id = entity.getId();
        this.outwardDate = entity.getOutwardDate();
        this.outwardNo = entity.getOutwardNo();
        this.itemVendorName = entity.getItemVendorName();
        this.department = entity.getDepartment();
        this.machineName = entity.getMachineName();
        this.warehouse = entity.getWarehouse();
        this.vendorId = entity.getVendorId();
        this.companyId = entity.getCompanyId();
        this.financialYearId = entity.getFinancialYearId();
        this.activeFlag = entity.getActiveFlag();
        
        if (entity.getItemOutwardStockDetails() != null) {
            this.itemOutwardStockDetails = entity.getItemOutwardStockDetails().stream()
                    .map(ItemOutwardStockDetailsDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.itemOutwardStockDetails = new ArrayList<>();
        }
    }

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

    public List<ItemOutwardStockDetailsDTO> getItemOutwardStockDetails() {
        return itemOutwardStockDetails;
    }

    public void setItemOutwardStockDetails(List<ItemOutwardStockDetailsDTO> itemOutwardStockDetails) {
        this.itemOutwardStockDetails = itemOutwardStockDetails;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<ItemOutwardStockDetailsDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemOutwardStockDetailsDTO> items) {
        this.items = items;
    }
}

