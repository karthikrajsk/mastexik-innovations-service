package com.tech3.erp.dto;

import com.tech3.erp.entity.DyeingWorkOrderYarnItem;

public class DyeingWorkOrderYarnItemDTO {

    private Long id;
    private Long dyeingWorkOrderId;
    private Long finishedYarnCodeId;
    private String finishedYarnName;
    private Long greigeYarnCodeId;
    private String greigeYarnName;
    private Double quantity;
    private Double costPerPound;
    private Double totalAmount;
    private Double gstPercent;
    private Double gstAmount;
    private Double finalAmount;
    private Long colorId;
    private String pantone;
    private Double finishedWeight;
    private Long uomId;
    private String remarks;
    private Boolean activeFlag;
    private Double dispatchQuantity;

    public DyeingWorkOrderYarnItemDTO() {}

    public DyeingWorkOrderYarnItemDTO(DyeingWorkOrderYarnItem entity) {
        this.id = entity.getId();
        this.finishedYarnCodeId = entity.getFinishedYarnCodeId();
        this.finishedYarnName = entity.getFinishedYarnName();
        this.greigeYarnCodeId = entity.getGreigeYarnCodeId();
        this.greigeYarnName = entity.getGreigeYarnName();
        this.quantity = entity.getQuantity();
        this.costPerPound = entity.getCostPerPound();
        this.totalAmount = entity.getTotalAmount();
        this.gstPercent = entity.getGstPercent();
        this.gstAmount = entity.getGstAmount();
        this.finalAmount = entity.getFinalAmount();
        this.colorId = entity.getColorId();
        this.pantone = entity.getPantone();
        this.finishedWeight = entity.getFinishedWeight();
        this.uomId = entity.getUomId();
        this.remarks = entity.getRemarks();
        this.activeFlag = entity.getActiveFlag();
        this.dispatchQuantity = entity.getDispatchQuantity();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDyeingWorkOrderId() { return dyeingWorkOrderId; }
    public void setDyeingWorkOrderId(Long dyeingWorkOrderId) { this.dyeingWorkOrderId = dyeingWorkOrderId; }

    public Long getFinishedYarnCodeId() { return finishedYarnCodeId; }
    public void setFinishedYarnCodeId(Long finishedYarnCodeId) { this.finishedYarnCodeId = finishedYarnCodeId; }

    public String getFinishedYarnName() { return finishedYarnName; }
    public void setFinishedYarnName(String finishedYarnName) { this.finishedYarnName = finishedYarnName; }

    public Long getGreigeYarnCodeId() { return greigeYarnCodeId; }
    public void setGreigeYarnCodeId(Long greigeYarnCodeId) { this.greigeYarnCodeId = greigeYarnCodeId; }

    public String getGreigeYarnName() { return greigeYarnName; }
    public void setGreigeYarnName(String greigeYarnName) { this.greigeYarnName = greigeYarnName; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getCostPerPound() { return costPerPound; }
    public void setCostPerPound(Double costPerPound) { this.costPerPound = costPerPound; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public Double getGstAmount() { return gstAmount; }
    public void setGstAmount(Double gstAmount) { this.gstAmount = gstAmount; }

    public Double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(Double finalAmount) { this.finalAmount = finalAmount; }

    public Long getColorId() { return colorId; }
    public void setColorId(Long colorId) { this.colorId = colorId; }

    public String getPantone() { return pantone; }
    public void setPantone(String pantone) { this.pantone = pantone; }

    public Double getFinishedWeight() { return finishedWeight; }
    public void setFinishedWeight(Double finishedWeight) { this.finishedWeight = finishedWeight; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
    
    public Double getDispatchQuantity() { return dispatchQuantity; }
    public void setDispatchQuantity(Double dispatchQuantity) { this.dispatchQuantity = dispatchQuantity; }
}