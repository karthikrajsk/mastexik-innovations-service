package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales_return", schema = "masters")
public class SalesReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "sales_return_no", length = 100)
    private String salesReturnNo;

    @Column(name = "invoice_no", length = 100, nullable = false)
    private String invoiceNo;

    @Column(name = "invoice_id", nullable = false)
    private Long invoiceId;

    @Column(name = "order_no", length = 100, nullable = false)
    private String orderNo;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "buyer_name", length = 255, nullable = false)
    private String buyerName;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "fabric_code", length = 100)
    private String fabricCode;

    @Column(name = "fabric_quality", length = 100)
    private String fabricQuality;

    @Column(name = "invoice_type", length = 50)
    private String invoiceType;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "edit_note", columnDefinition = "TEXT")
    private String editNote;

    @Column(name = "packing_list_no", length = 100)
    private String packingListNo;

    @Column(name = "sizing_vendor_id")
    private Long sizingVendorId;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "weaver_id")
    private Long weaverId;

    @Column(name = "beam_inward_no", length = 100)
    private String beamInwardNo;

    @Column(name = "sizing_beam_outward_no", length = 100)
    private String sizingBeamOutwardNo;

    @Column(name = "total_rolls")
    private Integer totalRolls;


    @OneToMany(mappedBy = "salesReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("salesReturnItems")
    private List<SalesReturnItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "salesReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("salesReturnYarnItems")
    private List<SalesYarnReturn> yarnItems = new ArrayList<>();

    @OneToMany(mappedBy = "salesReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("salesReturnBeams")
    private List<SalesReturnBeamEntity> beams = new ArrayList<>();

    @OneToMany(mappedBy = "salesReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("salesReturnSizingItems")
    private List<SalesReturnSizing> sizingItems = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

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

    public List<SalesReturnItem> getItems() { return items; }
    public void setItems(List<SalesReturnItem> items) {
        this.items = items;
        if (items != null) {
            for (SalesReturnItem item : items) {
                item.setSalesReturn(this);
            }
        }
    }

    public List<SalesYarnReturn> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<SalesYarnReturn> yarnItems) {
        this.yarnItems = yarnItems;
        if (yarnItems != null) {
            for (SalesYarnReturn item : yarnItems) {
                item.setSalesReturn(this);
            }
        }
    }

    public List<SalesReturnBeamEntity> getBeams() { return beams; }
    public void setBeams(List<SalesReturnBeamEntity> beams) {
        this.beams = beams;
        if (beams != null) {
            for (SalesReturnBeamEntity beam : beams) {
                beam.setSalesReturn(this);
            }
        }
    }

    @Column(name = "dc_no", length = 100)
    private String dcNo;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    public String getDcNo() { return dcNo; }
    public void setDcNo(String dcNo) { this.dcNo = dcNo; }

    public LocalDate getDcDate() { return dcDate; }
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public List<SalesReturnSizing> getSizingItems() { return sizingItems; }
    public void setSizingItems(List<SalesReturnSizing> sizingItems) {
        this.sizingItems = sizingItems;
        if (sizingItems != null) {
            for (SalesReturnSizing item : sizingItems) {
                item.setSalesReturn(this);
            }
        }
    }
}
