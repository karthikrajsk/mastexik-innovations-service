package com.tech3.erp.dto;


import com.tech3.erp.entity.Warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WarehouseDTO {
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String warehouseName;

    private boolean activeFlag; // Matches active_flag in the database
  private String description;
    private String ownerType;
    private Long ownerId;
    private String warehouseType;
     private Boolean isDefault;
   

    // Default constructor
    public WarehouseDTO() {
    }

    // Constructor with parameters
    public WarehouseDTO(Long id, String warehouseName, boolean activeFlag, String description, String ownerType, Long ownerId, String warehouseType,boolean isDefault) {
        this.id = id;
        this.warehouseName = warehouseName;
        this.activeFlag = activeFlag;
        this.description = description;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.warehouseType = warehouseType;
         this.isDefault = isDefault;
    }

    // Constructor to convert from entity to DTO
    public WarehouseDTO(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.warehouseName = warehouse.getWarehouseName();
        this.activeFlag = warehouse.getActiveFlag();
        this.description = warehouse.getDescription();
        this.ownerType = warehouse.getOwnerType();
        this.ownerId = warehouse.getOwnerId();
        this.warehouseType = warehouse.getWarehouseType();
        this.isDefault = warehouse.getIsDefault();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

        public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

}

