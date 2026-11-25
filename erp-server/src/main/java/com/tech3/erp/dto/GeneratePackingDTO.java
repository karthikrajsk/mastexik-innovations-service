//package com.tech3.erp.dto;
//
//import java.util.Date;
//import java.util.List;
//
//import com.tech3.erp.entity.GeneratePacking;
//
//public class GeneratePackingDTO {
//    private Long id;
//    private Long companyId;
//    private Date packingDate;
//    private Long buyerId;
//    private Long salesOrderId;
//    private Long warehouseId;
//    private String tareWeight;
//    private String grossWeight;
//    private String packingSlipNo;
//    private List<Long> lotId;
//
//    public GeneratePackingDTO() {
//    }
//
//    // Constructor that takes GeneratePacking entity
//    public GeneratePackingDTO(GeneratePacking entity) {
//        this.id = entity.getId();
//        this.packingDate = entity.getPackingDate();
//        this.buyerId = entity.getBuyerId();
//        this.salesOrderId = entity.getSalesOrderId();
//        this.warehouseId = entity.getWarehouseId();
//        this.tareWeight = entity.getTareWeight();
//        this.grossWeight = entity.getGrossWeight();
//        this.packingSlipNo = entity.getPackingSlipNo();
//        this.lotId = entity.getLotId();
//    }
//
//    // All-arguments constructor (optional)
//    public GeneratePackingDTO(Long id, Long companyId, Date packingDate, Long buyerId, Long salesOrderId, 
//                            Long warehouseId, String tareWeight, String grossWeight, 
//                            String packingSlipNo, List<Long> lotId) {
//        this.id = id;
//        this.packingDate = packingDate;
//        this.buyerId = buyerId;
//        this.salesOrderId = salesOrderId;
//        this.warehouseId = warehouseId;
//        this.tareWeight = tareWeight;
//        this.grossWeight = grossWeight;
//        this.packingSlipNo = packingSlipNo;
//        this.lotId = lotId;
//    }
//
//	// Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Date getPackingDate() {
//        return packingDate;
//    }
//
//    public void setPackingDate(Date packingDate) {
//        this.packingDate = packingDate;
//    }
//
//    public Long getBuyerId() {
//        return buyerId;
//    }
//
//    public void setBuyerId(Long buyerId) {
//        this.buyerId = buyerId;
//    }
//
//    public Long getSalesOrderId() {
//        return salesOrderId;
//    }
//
//    public void setSalesOrderId(Long salesOrderId) {
//        this.salesOrderId = salesOrderId;
//    }
//
//    public Long getWarehouseId() {
//        return warehouseId;
//    }
//
//    public void setWarehouseId(Long warehouseId) {
//        this.warehouseId = warehouseId;
//    }
//
//    public String getTareWeight() {
//        return tareWeight;
//    }
//
//    public void setTareWeight(String tareWeight) {
//        this.tareWeight = tareWeight;
//    }
//
//    public String getGrossWeight() {
//        return grossWeight;
//    }
//
//    public void setGrossWeight(String grossWeight) {
//        this.grossWeight = grossWeight;
//    }
//
//    public String getPackingSlipNo() {
//        return packingSlipNo;
//    }
//
//    public void setPackingSlipNo(String packingSlipNo) {
//        this.packingSlipNo = packingSlipNo;
//    }
//
//    public List<Long> getLotId() {
//        return lotId;
//    }
//
//    public void setLotId(List<Long> lotId) {
//        this.lotId = lotId;
//    }
//}




package com.tech3.erp.dto;

import java.util.Date;
import java.util.List;

public class GeneratePackingDTO {
    private Long id;
    private Long companyId;
    private Date packingDate;
    private Long buyerId;
    private Long salesOrderId;
    private Long warehouseId;
    private String tareWeight;
    private String grossWeight;
    private String packingSlipNo;
    private String fabricCode;
    private String fabricQuality;
    private String remarks;
    private Long orderTypeId;
    private String deliveryAt;
    private Integer isJobwork;
    private Long weavingWorkOrderId;
    private Integer isInvoice;
    private List<GeneratePackingItemDTO> items;
    private Integer isBrushChecked;
    private String poNo;
    private java.time.LocalDate poDate;
    private String dcNo;
    private Long transportId;
    private String truckNo;
    private String lrNo;
    private java.time.LocalDate lrDate;
    private String invoiceNo;
    private java.time.LocalDate invoiceDate;

    // Constructors
    public GeneratePackingDTO() {
    }

    public GeneratePackingDTO(Long id, Long companyId, Date packingDate, Long buyerId, Long salesOrderId, 
            Long warehouseId, String tareWeight, String grossWeight, 
            String packingSlipNo, String fabricCode, String fabricQuality,
            String remarks, Long orderTypeId, String deliveryAt, Integer isJobwork, Long weavingWorkOrderId, Integer isInvoice, List<GeneratePackingItemDTO> items) {
this.id = id;
this.companyId = companyId;
this.packingDate = packingDate;
this.buyerId = buyerId;
this.salesOrderId = salesOrderId;
this.warehouseId = warehouseId;
this.tareWeight = tareWeight;
this.grossWeight = grossWeight;
this.packingSlipNo = packingSlipNo;
this.fabricCode = fabricCode;
this.fabricQuality = fabricQuality;
this.remarks = remarks;
this.orderTypeId = orderTypeId;
this.deliveryAt = deliveryAt;
this.isJobwork = isJobwork;
this.weavingWorkOrderId = weavingWorkOrderId;
this.isInvoice = isInvoice;
this.items = items;
}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getPackingDate() {
        return packingDate;
    }

    public void setPackingDate(Date packingDate) {
        this.packingDate = packingDate;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(String tareWeight) {
        this.tareWeight = tareWeight;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getPackingSlipNo() {
        return packingSlipNo;
    }

    public void setPackingSlipNo(String packingSlipNo) {
        this.packingSlipNo = packingSlipNo;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getFabricQuality() {
        return fabricQuality;
    }

    public void setFabricQuality(String fabricQuality) {
        this.fabricQuality = fabricQuality;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Integer getIsJobwork() {
        return isJobwork;
    }

    public void setIsJobwork(Integer isJobwork) {
        this.isJobwork = isJobwork;
    }

    public Long getWeavingWorkOrderId() {
        return weavingWorkOrderId;
    }

    public void setWeavingWorkOrderId(Long weavingWorkOrderId) {
        this.weavingWorkOrderId = weavingWorkOrderId;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getDeliveryAt() {
        return deliveryAt;
    }

    public void setDeliveryAt(String deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public List<GeneratePackingItemDTO> getItems() {
        return items;
    }

    public void setItems(List<GeneratePackingItemDTO> items) {
        this.items = items;
    }

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
