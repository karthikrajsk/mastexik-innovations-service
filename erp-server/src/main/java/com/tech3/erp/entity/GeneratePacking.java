package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "generate_packing", schema = "masters")
public class GeneratePacking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "packing_date")
    private Date packingDate;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "sales_order_id", nullable = true)
    private Long salesOrderId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "tare_weight", length = 50)
    private String tareWeight;

    @Column(name = "gross_weight", length = 50)
    private String grossWeight;

    @Column(name = "packing_slip_no", length = 255)
    private String packingSlipNo;

    @Column(name = "fabric_code", length = 100)
    private String fabricCode;

    @Column(name = "fabric_quality", length = 255)
    private String fabricQuality;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "delivery_at", length = 1000)
    private String deliveryAt;

    @Column(name = "is_jobwork")
    private Integer isJobwork = 0;

    @Column(name = "weaving_work_order_id", nullable = true)
    private Long weavingWorkOrderId;

    @Column(name = "is_invoice")
    private Integer isInvoice = 0;

    @Column(name = "is_brush_checked")
    private Integer isBrushChecked = 0;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private java.time.LocalDate poDate;

    @Column(name = "dc_no")
    private String dcNo;

    @Column(name = "transport_id")
    private Long transportId;

    @Column(name = "truck_no")
    private String truckNo;

    @Column(name = "lr_no")
    private String lrNo;

    @Column(name = "lr_date")
    private java.time.LocalDate lrDate;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_date")
    private java.time.LocalDate invoiceDate;

    @OneToMany(mappedBy = "generatePacking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneratePackingItem> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.packingSlipNo == null || this.packingSlipNo.isEmpty()) {
            this.packingSlipNo = generatePackingSlipNo();
        }
    }

    private String generatePackingSlipNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "PSNO-" + datePart + "-" + randomNum;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Date getPackingDate() { return packingDate; }
    public void setPackingDate(Date packingDate) { this.packingDate = packingDate; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getTareWeight() { return tareWeight; }
    public void setTareWeight(String tareWeight) { this.tareWeight = tareWeight; }

    public String getGrossWeight() { return grossWeight; }
    public void setGrossWeight(String grossWeight) { this.grossWeight = grossWeight; }

    public String getPackingSlipNo() { return packingSlipNo; }
    public void setPackingSlipNo(String packingSlipNo) { this.packingSlipNo = packingSlipNo; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public String getFabricQuality() { return fabricQuality; }
    public void setFabricQuality(String fabricQuality) { this.fabricQuality = fabricQuality; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public String getDeliveryAt() { return deliveryAt; }
    public void setDeliveryAt(String deliveryAt) { this.deliveryAt = deliveryAt; }

    public Integer getIsJobwork() { return isJobwork; }
    public void setIsJobwork(Integer isJobwork) { this.isJobwork = isJobwork; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Integer getIsInvoice() { return isInvoice; }
    public void setIsInvoice(Integer isInvoice) { this.isInvoice = isInvoice; }

    public List<GeneratePackingItem> getItems() { return items; }
    public void setItems(List<GeneratePackingItem> items) { this.items = items; }

    public Integer getIsBrushChecked() {
        return isBrushChecked;
    }

    public void setIsBrushChecked(Integer isBrushChecked) {
        this.isBrushChecked = isBrushChecked;
    }
    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public java.time.LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(java.time.LocalDate poDate) {
        this.poDate = poDate;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getLrNo() {
        return lrNo;
    }

    public void setLrNo(String lrNo) {
        this.lrNo = lrNo;
    }

    public java.time.LocalDate getLrDate() {
        return lrDate;
    }

    public void setLrDate(java.time.LocalDate lrDate) {
        this.lrDate = lrDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public java.time.LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(java.time.LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
