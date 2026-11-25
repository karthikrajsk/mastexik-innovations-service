package com.tech3.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "warehouse_master", schema = "masters")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String warehouseName;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;  // Default value as per the DB schema


// Added fields
@Size(max = 255)
private String description;

@Column(name = "owner_type")
private String ownerType; // Company or Vendor

@Column(name = "owner_id")
private Long ownerId;

@Column(name = "warehouse_type")
private String warehouseType; // Yarn, Fabric, Item

// Changed from boolean to Boolean to handle NULLs from DB
@Column(name = "is_default")
private Boolean isDefault = false;

    // Default constructor
    public Warehouse() {
    }

    // Constructor with parameters
    public Warehouse(Long id, String warehouseName, boolean activeFlag, String description, String ownerType, Long ownerId, String warehouseType,boolean isDefault) {
        this.id = id;
        this.warehouseName = warehouseName;
        this.activeFlag = activeFlag;
        this.description = description;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.warehouseType = warehouseType;
        this.isDefault = isDefault;
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


// Updated getter/setter
public Boolean getIsDefault() { return isDefault; }
public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }
}


