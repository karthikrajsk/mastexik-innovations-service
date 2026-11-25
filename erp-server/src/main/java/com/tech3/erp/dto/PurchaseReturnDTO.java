    package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech3.erp.entity.PurchaseReturn;
import com.tech3.erp.util.deser.PurchaseReturnFabricSummaryDeserializer;
import com.tech3.erp.util.deser.PurchaseReturnYarnSummaryDeserializer;
import java.time.LocalDate;
import java.util.List;

public class PurchaseReturnDTO {
    private Long id;
    @JsonProperty("returnNo")
    private String purchaseReturnId;
    private String returnDate; // keep as String to align with UI payload
    private String purchaseType; // yarn | woven | knitted | ""
    private String vendorId;
    private Long purchaseOrderId;
    private Long companyId;
    private String transportId;
    private String remark;
    private String vehicleNo;
    private String grossWt;

    @JsonProperty("yarnItems")
    private List<PurchaseReturnYarnItemDTO> yarnItems;

    @JsonProperty("fabricItems")
    private List<PurchaseReturnFabricItemDTO> fabricItems;

    @JsonProperty("items")
    private List<PurchaseReturnItemDTO> items;

    // New summaries to match frontend payload (single object), but accept array-or-object on input
    @JsonProperty("yarnSummary")
    @JsonDeserialize(using = PurchaseReturnYarnSummaryDeserializer.class)
    private PurchaseReturnYarnSummaryDTO yarnSummary;

    @JsonProperty("fabricSummary")
    @JsonDeserialize(using = PurchaseReturnFabricSummaryDeserializer.class)
    private PurchaseReturnFabricSummaryDTO fabricSummary;

    public PurchaseReturnDTO() {}

    public PurchaseReturnDTO(PurchaseReturn entity) {
        this.id = entity.getId();
        this.purchaseReturnId = entity.getReturnNo();
        this.returnDate = entity.getReturnDate() != null ? entity.getReturnDate().toString() : null;
        this.purchaseType = entity.getPurchaseType();
        this.vendorId = entity.getVendorId() != null ? entity.getVendorId().toString() : null;
        this.purchaseOrderId = entity.getPurchaseOrderId();
        this.companyId = entity.getCompanyId();
        this.transportId = entity.getTransportId() != null ? entity.getTransportId().toString() : null;
        this.remark = entity.getRemark();
        this.vehicleNo = entity.getVehicleNo();
        this.grossWt = entity.getGrossWt() != null ? entity.getGrossWt().toString() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPurchaseReturnId() { return purchaseReturnId; }
    public void setPurchaseReturnId(String purchaseReturnId) { this.purchaseReturnId = purchaseReturnId; }


    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getPurchaseType() { return purchaseType; }
    public void setPurchaseType(String purchaseType) { this.purchaseType = purchaseType; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }

    public Long getPurchaseOrderId() { return purchaseOrderId; }
    public void setPurchaseOrderId(Long purchaseOrderId) { this.purchaseOrderId = purchaseOrderId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getTransportId() { return transportId; }
    public void setTransportId(String transportId) { this.transportId = transportId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public String getGrossWt() { return grossWt; }
    public void setGrossWt(String grossWt) { this.grossWt = grossWt; }

    public List<PurchaseReturnYarnItemDTO> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<PurchaseReturnYarnItemDTO> yarnItems) { this.yarnItems = yarnItems; }

    public List<PurchaseReturnFabricItemDTO> getFabricItems() { return fabricItems; }
    public void setFabricItems(List<PurchaseReturnFabricItemDTO> fabricItems) { this.fabricItems = fabricItems; }

    public List<PurchaseReturnItemDTO> getItems() { return items; }
    public void setItems(List<PurchaseReturnItemDTO> items) { this.items = items; }

    public PurchaseReturnYarnSummaryDTO getYarnSummary() { return yarnSummary; }
    public void setYarnSummary(PurchaseReturnYarnSummaryDTO yarnSummary) { this.yarnSummary = yarnSummary; }

    public PurchaseReturnFabricSummaryDTO getFabricSummary() { return fabricSummary; }
    public void setFabricSummary(PurchaseReturnFabricSummaryDTO fabricSummary) { this.fabricSummary = fabricSummary; }
}