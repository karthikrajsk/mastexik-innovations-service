package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.tech3.erp.entity.YarnReturn;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class YarnReturnDTO {

    private Long id;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inwardDate;
    
    private String inwardNumber;
    private String inwardType;
    private String orderNo;
    private Long vendorId;
    private String vendorName;
    private Long millId;
    private Long warehouseId;
    private Double issuedQty;
    private String remarks;
    private Boolean activeFlag;

    // ✅ Newly added fields
    private String dcNumber;   // Delivery Challan Number
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dcDate;  // Delivery Challan Date

    private Long companyId;

    @JsonProperty("yarnReturnItems")
    private List<YarnReturnItemDTO> yarnReturnItems;

    @JsonProperty("yarnReturnRequirements")
    private List<YarnReturnRequirementDTO> yarnReturnRequirements;

    public YarnReturnDTO() {}

    public YarnReturnDTO(YarnReturn yarnReturn) {
        this.id = yarnReturn.getId();
        this.inwardDate = yarnReturn.getInwardDate();
        this.inwardNumber = yarnReturn.getInwardNumber();
        this.inwardType = yarnReturn.getInwardType();
        this.orderNo = yarnReturn.getOrderNo();
        this.vendorId = yarnReturn.getVendorId();
        this.vendorName = yarnReturn.getVendorName();
        this.millId = yarnReturn.getMillId();
        this.warehouseId = yarnReturn.getWarehouseId();
        this.issuedQty = yarnReturn.getIssuedQty();
        this.remarks = yarnReturn.getRemarks();
        this.activeFlag = yarnReturn.getActiveFlag();

        // ✅ Map new DC fields
        this.dcNumber = yarnReturn.getDcNumber();
        this.dcDate = yarnReturn.getDcDate();
        this.companyId = yarnReturn.getCompanyId();

        if (yarnReturn.getYarnReturnItems() != null) {
            this.yarnReturnItems = yarnReturn.getYarnReturnItems().stream()
                .map(YarnReturnItemDTO::new)
                .collect(Collectors.toList());
        }
        if (yarnReturn.getYarnReturnRequirements() != null) {
            this.yarnReturnRequirements = yarnReturn.getYarnReturnRequirements().stream()
                .map(YarnReturnRequirementDTO::new)
                .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(LocalDate inwardDate) {
        this.inwardDate = inwardDate;
    }

    @JsonSetter("inwardDate")
    public void setInwardDate(String dateString) {
        this.inwardDate = parseDateString(dateString);
    }

    public String getInwardNumber() {
        return inwardNumber;
    }

    public void setInwardNumber(String inwardNumber) {
        this.inwardNumber = inwardNumber;
    }

    public String getInwardType() {
        return inwardType;
    }

    public void setInwardType(String inwardType) {
        this.inwardType = inwardType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Double getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(Double issuedQty) {
        this.issuedQty = issuedQty;
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

    // ✅ Added Getters/Setters for DC fields
    public String getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(String dcNumber) {
        this.dcNumber = dcNumber;
    }

    public LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDate dcDate) {
        this.dcDate = dcDate;
    }

    @JsonSetter("dcDate")
    public void setDcDate(String dateString) {
        this.dcDate = parseDateString(dateString);
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<YarnReturnItemDTO> getYarnReturnItems() {
        return yarnReturnItems;
    }

    public void setYarnReturnItems(List<YarnReturnItemDTO> yarnReturnItems) {
        this.yarnReturnItems = yarnReturnItems;
    }

    public List<YarnReturnRequirementDTO> getYarnReturnRequirements() {
        return yarnReturnRequirements;
    }

    public void setYarnReturnRequirements(List<YarnReturnRequirementDTO> yarnReturnRequirements) {
        this.yarnReturnRequirements = yarnReturnRequirements;
    }

    private LocalDate parseDateString(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        String trimmed = dateString.trim();
        if (trimmed.contains("undefined")) {
            return null;
        }
        try {
            return LocalDate.parse(trimmed, DATE_FORMATTER);
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }
}
