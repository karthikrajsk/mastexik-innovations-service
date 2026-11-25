package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BaleRollPackingDTO {
    private Long id;
    private Date packingDate;
    private String packingType; // Roll | Bale
    private Long orderTypeId;
    private Long buyerId;
    private Long weavingWorkOrderId;   // optional
    private Integer isJobwork;
    private Long salesOrderId;
    private Long fabricCodeId;
    private String fabricQuality; // optional
    private Long uomId;         // meters/yards
    private Long warehouseId;
    private Long gradeId;       // mandatory
    private String remarks;     // optional
    private String packingNo;
    private Long companyId;

    // Additional totals and number from UI header
    private String rollOrBaleNo;
    private Long totalPieces;
    private java.math.BigDecimal totalMeters;
    private java.math.BigDecimal totalYards;
    private java.math.BigDecimal totalNet;
    private java.math.BigDecimal totalAvg;
    private java.math.BigDecimal totalGross;
    private Integer isFactoryRoll;
    private Integer isDispatched;
    private Integer isRollScanned;

    private Integer isBrushChecked;
    private Long vendorId;

    private List<BaleRollPackingItemDTO> items;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getPackingDate() { return packingDate; }
    public void setPackingDate(Date packingDate) { this.packingDate = packingDate; }

    public String getPackingType() { return packingType; }
    public void setPackingType(String packingType) { this.packingType = packingType; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Integer getIsJobwork() { return isJobwork; }
    public void setIsJobwork(Integer isJobwork) { this.isJobwork = isJobwork; }

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    public Long getFabricCodeId() { return fabricCodeId; }
    public void setFabricCodeId(Long fabricCodeId) { this.fabricCodeId = fabricCodeId; }

    public String getFabricQuality() { return fabricQuality; }
    public void setFabricQuality(String fabricQuality) { this.fabricQuality = fabricQuality; }

    @Deprecated
    public Long getFabricQualityId() {
        try {
            return fabricQuality != null ? Long.valueOf(fabricQuality) : null;
        } catch (NumberFormatException e) {
            return null; // Non-numeric value cannot be converted
        }
    }

    @Deprecated
    public void setFabricQualityId(Long fabricQualityId) {
        this.fabricQuality = fabricQualityId != null ? fabricQualityId.toString() : null;
    }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Long getGradeId() { return gradeId; }
    public void setGradeId(Long gradeId) { this.gradeId = gradeId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getPackingNo() { return packingNo; }
    public void setPackingNo(String packingNo) { this.packingNo = packingNo; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getRollOrBaleNo() { return rollOrBaleNo; }
    public void setRollOrBaleNo(String rollOrBaleNo) { this.rollOrBaleNo = rollOrBaleNo; }

    public Long getTotalPieces() { return totalPieces; }
    public void setTotalPieces(Long totalPieces) { this.totalPieces = totalPieces; }

    public java.math.BigDecimal getTotalMeters() { return totalMeters; }
    public void setTotalMeters(java.math.BigDecimal totalMeters) { this.totalMeters = totalMeters; }

    public java.math.BigDecimal getTotalYards() { return totalYards; }
    public void setTotalYards(java.math.BigDecimal totalYards) { this.totalYards = totalYards; }

    public java.math.BigDecimal getTotalNet() { return totalNet; }
    public void setTotalNet(java.math.BigDecimal totalNet) { this.totalNet = totalNet; }

    public java.math.BigDecimal getTotalAvg() { return totalAvg; }
    public void setTotalAvg(java.math.BigDecimal totalAvg) { this.totalAvg = totalAvg; }

    public java.math.BigDecimal getTotalGross() { return totalGross; }
    public void setTotalGross(java.math.BigDecimal totalGross) { this.totalGross = totalGross; }

    public Integer getIsFactoryRoll() { return isFactoryRoll; }
    public void setIsFactoryRoll(Integer isFactoryRoll) { this.isFactoryRoll = isFactoryRoll; }

    public Integer getIsDispatched() { return isDispatched; }
    public void setIsDispatched(Integer isDispatched) { this.isDispatched = isDispatched; }

    public Integer getIsRollScanned() { return isRollScanned; }
    public void setIsRollScanned(Integer isRollScanned) { this.isRollScanned = isRollScanned; }

    public Integer getIsBrushChecked() { return isBrushChecked; }
    public void setIsBrushChecked(Integer isBrushChecked) { this.isBrushChecked = isBrushChecked; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public List<BaleRollPackingItemDTO> getItems() { return items; }
    public void setItems(List<BaleRollPackingItemDTO> items) { this.items = items; }

}