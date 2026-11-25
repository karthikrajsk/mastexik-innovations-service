package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

import com.tech3.erp.entity.FabricDispatchForDyeing;

public class FabricDispatchForDyeingDTO {

    private Long id;
    private LocalDate fabricDispatchDate;
    private Long vendorId;
    private String issueType;
    private Long dyeingWorkOrderId;
    private Double orderQuantity;
    private Double dispatchedQuantity;
    private Double receivedQuantity;
    private Double balanceQuantity;
    private Double costPerPound;
    private Double totalAmount;
    private Long colorId;
    private String pantone;
    private Long finishingId;
    private Long salesOrderId;
    private Long shipmentModeId;
    private String lotNumber;
    private String remarks;
    private Boolean activeFlag;
    private Long companyId;
    private Long transportId;
    private String transportName;
    private String vehicleNo;
    private Double grossWt;

    private String finishedFabricCode;
    private String finishedFabricName;
    private String greigeFabricCode;
    private String greigeFabricName;
    private String fabricDispatchId;

    // Process type field
    private String processType = "FABRIC"; // "FABRIC" or "YARN"

    // Add these fields for fabric and yarn issues
    private List<FabricDispatchYarnIssueDTO> yarnIssues;
    private List<FabricDispatchFabricIssueDTO> fabricIssues;

    // Default constructor
    public FabricDispatchForDyeingDTO() {}

    // Constructor with all fields
    public FabricDispatchForDyeingDTO(Long id, LocalDate fabricDispatchDate, Long vendorId, Long dyeingWorkOrderId,
                                      Double orderQuantity, Double dispatchedQuantity, Double receivedQuantity,
                                      Double balanceQuantity, Double costPerPound, Double totalAmount, Long colorId,
                                      String pantone, Long finishingId, Long salesOrderId, Long shipmentModeId,
                                      String remarks, Boolean activeFlag, String processType, Long companyId) {
        this.id = id;
        this.fabricDispatchDate = fabricDispatchDate;
        this.vendorId = vendorId;
        this.dyeingWorkOrderId = dyeingWorkOrderId;
        this.orderQuantity = orderQuantity;
        this.dispatchedQuantity = dispatchedQuantity;
        this.receivedQuantity = receivedQuantity;
        this.balanceQuantity = balanceQuantity;
        this.costPerPound = costPerPound;
        this.totalAmount = totalAmount;
        this.colorId = colorId;
        this.pantone = pantone;
        this.finishingId = finishingId;
        this.salesOrderId = salesOrderId;
        this.shipmentModeId = shipmentModeId;
        this.remarks = remarks;
        this.activeFlag = activeFlag;
        this.processType = processType != null ? processType : "FABRIC";
        this.companyId = companyId;
    }

    // Additional constructor for entity mapping
    public FabricDispatchForDyeingDTO(FabricDispatchForDyeing entity) {
        this.id = entity.getId();
        this.fabricDispatchDate = entity.getFabricDispatchDate();
        this.vendorId = entity.getVendorId();
        this.issueType = entity.getIssueType();
        this.dyeingWorkOrderId = entity.getDyeingWorkOrderId();
        this.orderQuantity = entity.getOrderQuantity();
        this.dispatchedQuantity = entity.getDispatchedQuantity();
        this.receivedQuantity = entity.getReceivedQuantity();
        this.balanceQuantity = entity.getBalanceQuantity();
        this.costPerPound = entity.getCostPerPound();
        this.totalAmount = entity.getTotalAmount();
        this.colorId = entity.getColorId();
        this.pantone = entity.getPantone();
        this.finishingId = entity.getFinishingId();
        this.salesOrderId = entity.getSalesOrderId();
        this.shipmentModeId = entity.getShipmentModeId();
        this.lotNumber = entity.getLotNumber();
        this.remarks = entity.getRemarks();
        this.activeFlag = entity.getActiveFlag();
        this.companyId = entity.getCompanyId();
        this.transportId = entity.getTransportId();
        this.transportName = entity.getTransportName();
        this.vehicleNo = entity.getVehicleNo();
        this.grossWt = entity.getGrossWt();
        this.finishedFabricCode = entity.getFinishedFabricCode();
        this.finishedFabricName = entity.getFinishedFabricName();
        this.greigeFabricCode = entity.getGreigeFabricCode();
        this.greigeFabricName = entity.getGreigeFabricName();
        this.processType = entity.getProcessType();
        this.fabricDispatchId = entity.getDispatchNo();
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFabricDispatchDate() { return fabricDispatchDate; }
    public void setFabricDispatchDate(LocalDate fabricDispatchDate) { this.fabricDispatchDate = fabricDispatchDate; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }
    
    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public Long getDyeingWorkOrderId() { return dyeingWorkOrderId; }
    public void setDyeingWorkOrderId(Long dyeingWorkOrderId) { this.dyeingWorkOrderId = dyeingWorkOrderId; }

    public Double getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(Double orderQuantity) { this.orderQuantity = orderQuantity; }

    public Double getDispatchedQuantity() { return dispatchedQuantity; }
    public void setDispatchedQuantity(Double dispatchedQuantity) { this.dispatchedQuantity = dispatchedQuantity; }

    public Double getReceivedQuantity() { return receivedQuantity; }
    public void setReceivedQuantity(Double receivedQuantity) { this.receivedQuantity = receivedQuantity; }

    public Double getBalanceQuantity() { return balanceQuantity; }
    public void setBalanceQuantity(Double balanceQuantity) { this.balanceQuantity = balanceQuantity; }

    public Double getCostPerPound() { return costPerPound; }
    public void setCostPerPound(Double costPerPound) { this.costPerPound = costPerPound; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Long getColorId() { return colorId; }
    public void setColorId(Long colorId) { this.colorId = colorId; }

    public String getPantone() { return pantone; }
    public void setPantone(String pantone) { this.pantone = pantone; }

    public Long getFinishingId() { return finishingId; }
    public void setFinishingId(Long finishingId) { this.finishingId = finishingId; }

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    public Long getShipmentModeId() { return shipmentModeId; }
    public void setShipmentModeId(Long shipmentModeId) { this.shipmentModeId = shipmentModeId; }

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getTransportId() { return transportId; }
    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getTransportName() { return transportName; }
    public void setTransportName(String transportName) { this.transportName = transportName; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public Double getGrossWt() { return grossWt; }
    public void setGrossWt(Double grossWt) { this.grossWt = grossWt; }

    public String getFinishedFabricCode() { return finishedFabricCode; }
    public void setFinishedFabricCode(String finishedFabricCode) { this.finishedFabricCode = finishedFabricCode; }

    public String getFinishedFabricName() { return finishedFabricName; }
    public void setFinishedFabricName(String finishedFabricName) { this.finishedFabricName = finishedFabricName; }

    public String getGreigeFabricCode() { return greigeFabricCode; }
    public void setGreigeFabricCode(String greigeFabricCode) { this.greigeFabricCode = greigeFabricCode; }

    public String getGreigeFabricName() { return greigeFabricName; }
    public void setGreigeFabricName(String greigeFabricName) { this.greigeFabricName = greigeFabricName; }

    public String getProcessType() { return processType; }
    public void setProcessType(String processType) { this.processType = processType; }

    // Yarn issues getter and setter
    public List<FabricDispatchYarnIssueDTO> getYarnIssues() { return yarnIssues; }
    public void setYarnIssues(List<FabricDispatchYarnIssueDTO> yarnIssues) { this.yarnIssues = yarnIssues; }

    // Fabric issues getter and setter
    public List<FabricDispatchFabricIssueDTO> getFabricIssues() { return fabricIssues; }
    public void setFabricIssues(List<FabricDispatchFabricIssueDTO> fabricIssues) { this.fabricIssues = fabricIssues; }

    public String getFabricDispatchId() { return fabricDispatchId; }
    public void setFabricDispatchId(String fabricDispatchId) { this.fabricDispatchId = fabricDispatchId; }
}