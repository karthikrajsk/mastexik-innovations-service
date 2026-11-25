package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.dto.StockItemTransferDTO;

public class StockTransferDTO {

    private Long id;
    private LocalDate date;
    private Long fromGodownId;
    private String fromGodownName;
    private Long toGodownId;
    private String toGodownName;
    private String goodsType;
    private Long itemId;
    private String itemName;
    private Double quantity;
    private Long companyId;
    private String stockTransferNo;

    private List<StockFabricTransferDTO> fabricItems = new ArrayList<>();
    private List<StockYarnTransferDTO> yarnItems = new ArrayList<>();
    private List<StockItemTransferDTO> itemItems = new ArrayList<>();

    @JsonProperty("itemGoods")
    private List<StockItemTransferDTO> itemGoods = new ArrayList<>();

    // Default constructor
    public StockTransferDTO() {
    }

    // Parameterized constructor
    public StockTransferDTO(Long id, LocalDate date, Long fromGodownId, String fromGodownName,
                           Long toGodownId, String toGodownName, String goodsType, Long itemId, String itemName, Double quantity) {
        this.id = id;
        this.date = date;
        this.fromGodownId = fromGodownId;
        this.fromGodownName = fromGodownName;
        this.toGodownId = toGodownId;
        this.toGodownName = toGodownName;
        this.goodsType = goodsType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getFromGodownId() {
        return fromGodownId;
    }

    public void setFromGodownId(Long fromGodownId) {
        this.fromGodownId = fromGodownId;
    }

    public String getFromGodownName() {
        return fromGodownName;
    }

    public void setFromGodownName(String fromGodownName) {
        this.fromGodownName = fromGodownName;
    }

    public Long getToGodownId() {
        return toGodownId;
    }

    public void setToGodownId(Long toGodownId) {
        this.toGodownId = toGodownId;
    }

    public String getToGodownName() {
        return toGodownName;
    }

    public void setToGodownName(String toGodownName) {
        this.toGodownName = toGodownName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStockTransferNo() {
        return stockTransferNo;
    }

    public void setStockTransferNo(String stockTransferNo) {
        this.stockTransferNo = stockTransferNo;
    }

    public List<StockFabricTransferDTO> getFabricItems() {
        return fabricItems;
    }

    public void setFabricItems(List<StockFabricTransferDTO> fabricItems) {
        this.fabricItems = fabricItems;
    }

    public List<StockYarnTransferDTO> getYarnItems() {
        return yarnItems;
    }

    public void setYarnItems(List<StockYarnTransferDTO> yarnItems) {
        this.yarnItems = yarnItems;
    }

    public List<StockItemTransferDTO> getItemItems() {
        return itemItems;
    }

    public void setItemItems(List<StockItemTransferDTO> itemItems) {
        this.itemItems = itemItems;
    }

    public List<StockItemTransferDTO> getItemGoods() {
        return itemGoods;
    }

    public void setItemGoods(List<StockItemTransferDTO> itemGoods) {
        this.itemGoods = itemGoods;
    }
}