package com.tech3.erp.dto;

import com.tech3.erp.entity.ItemMaster;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ItemMasterDto {

    private Long id;

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name must not exceed 100 characters")
    private String itemName;

    @Size(max = 50, message = "Item code must not exceed 50 characters")
    private String itemCode;

    @NotBlank(message = "Department is required")
    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @Size(max = 100, message = "Item category must not exceed 100 characters")
    private String itemCategory;

    @Size(max = 50, message = "HSN code must not exceed 50 characters")
    private String hsnCode;

    @NotBlank(message = "UOM name is required")
    @Size(max = 50, message = "UOM name must not exceed 50 characters")
    private String uomName;

    private Boolean activeFlag;
    private BigDecimal igst;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ItemMasterDto() {
    }

    public ItemMasterDto(ItemMaster itemMaster) {
        this.id = itemMaster.getId();
        this.itemName = itemMaster.getItemName();
        this.itemCode = itemMaster.getItemCode();
        this.department = itemMaster.getDepartment();
        this.itemCategory = itemMaster.getItemCategory();
        this.hsnCode = itemMaster.getHsnCode();
        this.uomName = itemMaster.getUomName();
        this.activeFlag = itemMaster.getActiveFlag();
        this.igst = itemMaster.getIgst();
        this.cgst = itemMaster.getCgst();
        this.sgst = itemMaster.getSgst();
        this.imageUrl = itemMaster.getImageUrl();
        this.createdAt = itemMaster.getCreatedAt();
        this.updatedAt = itemMaster.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

