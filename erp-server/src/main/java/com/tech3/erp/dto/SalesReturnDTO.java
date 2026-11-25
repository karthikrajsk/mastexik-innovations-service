package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.SalesReturn;
import java.util.List;

public class SalesReturnDTO {
    private Long id;

    @JsonProperty("date")
    private String date;

    @JsonProperty("salesReturnNo")
    private String salesReturnNo;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("invoiceId")
    private Long invoiceId;

    @JsonProperty("orderNo")
    private String orderNo;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("buyerName")
    private String buyerName;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("fabricCode")
    private String fabricCode;

    @JsonProperty("fabricQuality")
    private String fabricQuality;

    @JsonProperty("invoiceType")
    private String invoiceType;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("activeFlag")
    private Boolean activeFlag = true;

    @JsonProperty("companyId")
    private Long companyId;

    @JsonProperty("warehouseId")
    private Long warehouseId;

    @JsonProperty("editNote")
    private String editNote;

    @JsonProperty("packingListNo")
    private String packingListNo;

    @JsonProperty("sizingVendorId")
    private Long sizingVendorId;

    @JsonProperty("sizingPlanId")
    private Long sizingPlanId;

    @JsonProperty("weaverId")
    private Long weaverId;

    @JsonProperty("beamInwardNo")
    private String beamInwardNo;

    @JsonProperty("sizingBeamOutwardNo")
    private String sizingBeamOutwardNo;

    @JsonProperty("totalRolls")
    private Integer totalRolls;

    @JsonProperty("dcNo")
    private String dcNo;

    @JsonProperty("dcDate")
    private String dcDate;

    @JsonProperty("items")
    private List<SalesReturnItemDTO> items;

    @JsonProperty("yarnItems")
    private List<SalesYarnReturnDTO> yarnItems;

    @JsonProperty("beamItems")
    private List<SalesReturnBeamDTO> beamItems;

    @JsonProperty("sizingItems")
    private List<SalesReturnSizingDTO> sizingItems;

    public SalesReturnDTO() {}

    public SalesReturnDTO(SalesReturn entity) {
        this.id = entity.getId();
        this.date = entity.getDate() != null ? entity.getDate().toString() : null;
        this.salesReturnNo = entity.getSalesReturnNo();
        this.invoiceNo = entity.getInvoiceNo();
        this.invoiceId = entity.getInvoiceId();
        this.orderNo = entity.getOrderNo();
        this.orderId = entity.getOrderId();
        this.buyerName = entity.getBuyerName();
        this.customerId = entity.getCustomerId();
        this.fabricCode = entity.getFabricCode();
        this.fabricQuality = entity.getFabricQuality();
        this.invoiceType = entity.getInvoiceType();
        this.remarks = entity.getRemarks();
        this.activeFlag = entity.getActiveFlag();
        this.companyId = entity.getCompanyId();
        this.warehouseId = entity.getWarehouseId();
        this.editNote = entity.getEditNote();
        this.packingListNo = entity.getPackingListNo();
        this.sizingVendorId = entity.getSizingVendorId();
        this.sizingPlanId = entity.getSizingPlanId();
        this.weaverId = entity.getWeaverId();
        this.beamInwardNo = entity.getBeamInwardNo();
        this.sizingBeamOutwardNo = entity.getSizingBeamOutwardNo();
        this.totalRolls = entity.getTotalRolls();
        this.dcNo = entity.getDcNo();
        this.dcDate = entity.getDcDate() != null ? entity.getDcDate().toString() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSalesReturnNo() { return salesReturnNo; }
    public void setSalesReturnNo(String salesReturnNo) { this.salesReturnNo = salesReturnNo; }

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public String getFabricQuality() { return fabricQuality; }
    public void setFabricQuality(String fabricQuality) { this.fabricQuality = fabricQuality; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getEditNote() { return editNote; }
    public void setEditNote(String editNote) { this.editNote = editNote; }

    public String getPackingListNo() { return packingListNo; }
    public void setPackingListNo(String packingListNo) { this.packingListNo = packingListNo; }

    public Long getSizingVendorId() { return sizingVendorId; }
    public void setSizingVendorId(Long sizingVendorId) { this.sizingVendorId = sizingVendorId; }

    public Long getSizingPlanId() { return sizingPlanId; }
    public void setSizingPlanId(Long sizingPlanId) { this.sizingPlanId = sizingPlanId; }

    public Long getWeaverId() { return weaverId; }
    public void setWeaverId(Long weaverId) { this.weaverId = weaverId; }

    public String getBeamInwardNo() { return beamInwardNo; }
    public void setBeamInwardNo(String beamInwardNo) { this.beamInwardNo = beamInwardNo; }

    public String getSizingBeamOutwardNo() { return sizingBeamOutwardNo; }
    public void setSizingBeamOutwardNo(String sizingBeamOutwardNo) { this.sizingBeamOutwardNo = sizingBeamOutwardNo; }

    public Integer getTotalRolls() { return totalRolls; }
    public void setTotalRolls(Integer totalRolls) { this.totalRolls = totalRolls; }

    public List<SalesReturnItemDTO> getItems() { return items; }
    public void setItems(List<SalesReturnItemDTO> items) { this.items = items; }

    public List<SalesYarnReturnDTO> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<SalesYarnReturnDTO> yarnItems) { this.yarnItems = yarnItems; }

    public List<SalesReturnBeamDTO> getBeamItems() { return beamItems; }
    public void setBeamItems(List<SalesReturnBeamDTO> beamItems) { this.beamItems = beamItems; }

    public List<SalesReturnSizingDTO> getSizingItems() { return sizingItems; }
    public void setSizingItems(List<SalesReturnSizingDTO> sizingItems) { this.sizingItems = sizingItems; }

    public String getDcNo() { return dcNo; }
    public void setDcNo(String dcNo) { this.dcNo = dcNo; }

    public String getDcDate() { return dcDate; }
    public void setDcDate(String dcDate) { this.dcDate = dcDate; }
}
