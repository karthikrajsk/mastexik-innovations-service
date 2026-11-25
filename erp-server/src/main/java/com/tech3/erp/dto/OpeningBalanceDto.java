package com.tech3.erp.dto;

import com.tech3.erp.entity.OpeningBalanceEntity;
import com.tech3.erp.entity.OpeningBalanceSummaryItemEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OpeningBalanceDto {

    private Long id;
    private Integer slNo;
    private String openingBalanceNo;
    private LocalDate date;
    private Boolean isPacked;
    private String goodsType;
    
     private Long uomId;
    private Long companyId;
     private Long vendorId;
     private String dcNumber;
     private LocalDate dcDate;
     private String millInvNo;
     private LocalDate millDate;
     private String icilInvNo;
     private LocalDate icilInvDate;

    @JsonProperty("warehouse")
    private Long warehouseId;

    private List<FabricItemDto> fabricItems = new ArrayList<>();
    private List<YarnItemDto> yarnItems = new ArrayList<>();
    private List<ItemGoodsItemDto> itemGoods = new ArrayList<>();
    private List<OpeningBalanceSummaryItemDto> openingBalanceSummaryItems = new ArrayList<>();

    private List<Object> items = new ArrayList<>();

    public OpeningBalanceDto() {}

    // Constructor to map entity -> DTO
    public OpeningBalanceDto(OpeningBalanceEntity entity) {
        this.id = entity.getId();
        this.openingBalanceNo = entity.getOpeningBalanceNo();
//        this.slNo = entity.getSlNo(); // ✅ CRITICAL: Add this!
        this.date = entity.getDate();
        this.isPacked = entity.getIsPacked();
        this.goodsType = entity.getGoodsType();
        this.warehouseId = entity.getWarehouse() != null ? entity.getWarehouse().getId() : null;
        this.uomId = entity.getUomId();
        this.companyId = entity.getCompanyId();
        this.vendorId = entity.getVendorId();
        this.dcNumber = entity.getDcNumber();
        this.dcDate = entity.getDcDate();
        this.millInvNo = entity.getMillInvNo();
        this.millDate = entity.getMillDate();
        this.icilInvNo = entity.getIcilInvNo();
        this.icilInvDate = entity.getIcilInvDate();

        if (entity.getFabricItems() != null && !entity.getFabricItems().isEmpty()) {
            this.fabricItems = entity.getFabricItems().stream()
                    .map(FabricItemDto::new)
                    .collect(Collectors.toList());
            this.items = new ArrayList<>(this.fabricItems);
        }

        if (entity.getYarnItems() != null && !entity.getYarnItems().isEmpty()) {
            this.yarnItems = entity.getYarnItems().stream()
                    .map(YarnItemDto::new)
                    .collect(Collectors.toList());
            this.items = new ArrayList<>(this.yarnItems);
        }

        if (entity.getItemGoodsItems() != null && !entity.getItemGoodsItems().isEmpty()) {
            this.itemGoods = entity.getItemGoodsItems().stream()
                    .map(ItemGoodsItemDto::new)
                    .collect(Collectors.toList());
            this.items = new ArrayList<>(this.itemGoods);
        }

        if (entity.getOpeningBalanceSummaryItems() != null && !entity.getOpeningBalanceSummaryItems().isEmpty()) {
            this.openingBalanceSummaryItems = entity.getOpeningBalanceSummaryItems().stream()
                    .map(OpeningBalanceSummaryItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSlNo() { return slNo; }
    public void setSlNo(Integer slNo) { this.slNo = slNo; }

    public String getOpeningBalanceNo() { return openingBalanceNo; }
    public void setOpeningBalanceNo(String openingBalanceNo) { this.openingBalanceNo = openingBalanceNo; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Boolean getIsPacked() { return isPacked; }
    public void setIsPacked(Boolean isPacked) { this.isPacked = isPacked; }

    public String getGoodsType() { return goodsType; }
    public void setGoodsType(String goodsType) { this.goodsType = goodsType; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public List<FabricItemDto> getFabricItems() { 
        return fabricItems != null ? fabricItems : new ArrayList<>(); // ✅ Never return null
    }
    
    public void setFabricItems(List<FabricItemDto> fabricItems) { 
        this.fabricItems = fabricItems != null ? fabricItems : new ArrayList<>();
    }

    public List<YarnItemDto> getYarnItems() { 
        return yarnItems != null ? yarnItems : new ArrayList<>();
    }
    
    public void setYarnItems(List<YarnItemDto> yarnItems) { 
        this.yarnItems = yarnItems != null ? yarnItems : new ArrayList<>();
    }

    public List<ItemGoodsItemDto> getItemGoods() {
        return itemGoods != null ? itemGoods : new ArrayList<>();
    }

    public void setItemGoods(List<ItemGoodsItemDto> itemGoods) {
        this.itemGoods = itemGoods != null ? itemGoods : new ArrayList<>();
    }

    public List<OpeningBalanceSummaryItemDto> getOpeningBalanceSummaryItems() {
        return openingBalanceSummaryItems != null ? openingBalanceSummaryItems : new ArrayList<>();
    }

    public void setOpeningBalanceSummaryItems(List<OpeningBalanceSummaryItemDto> openingBalanceSummaryItems) {
        this.openingBalanceSummaryItems = openingBalanceSummaryItems != null ? openingBalanceSummaryItems : new ArrayList<>();
    }

    public List<Object> getItems() { 
        return items != null ? items : new ArrayList<>(); // ✅ Never return null
    }
    
    // ✅ Simplified setter - remove complex logic that might interfere
    public void setItems(List<Object> items) { 
        this.items = items != null ? items : new ArrayList<>();
    }
    
     public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public String getDcNumber() { return dcNumber; }
    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

    public LocalDate getDcDate() { return dcDate; }
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public String getMillInvNo() { return millInvNo; }
    public void setMillInvNo(String millInvNo) { this.millInvNo = millInvNo; }

    public LocalDate getMillDate() { return millDate; }
    public void setMillDate(LocalDate millDate) { this.millDate = millDate; }

    public String getIcilInvNo() { return icilInvNo; }
    public void setIcilInvNo(String icilInvNo) { this.icilInvNo = icilInvNo; }

    public LocalDate getIcilInvDate() { return icilInvDate; }
    public void setIcilInvDate(LocalDate icilInvDate) { this.icilInvDate = icilInvDate; }
    
    @JsonIgnore
    public List<?> getAppropriateItems() {
        if ("Fabric".equalsIgnoreCase(goodsType)) {
            return getFabricItems();
        } else if ("Yarn".equalsIgnoreCase(goodsType)) {
            return getYarnItems();
        } else if ("Item".equalsIgnoreCase(goodsType)) {
            return getItemGoods();
        }
        return new ArrayList<>();
    }
}