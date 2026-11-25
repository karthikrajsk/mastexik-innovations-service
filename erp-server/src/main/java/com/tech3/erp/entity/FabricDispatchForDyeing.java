package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fabric_dispatch_for_dyeing", schema = "masters")
@Inheritance(strategy = InheritanceType.JOINED)
public class FabricDispatchForDyeing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "transport_id", nullable = true)
    private Long transportId;
    @Column(name = "transport_name", nullable = true)
    private String transportName;
    @Column(name = "vehicle_no", nullable = true)
    private String vehicleNo;
    private Double grossWt;

    @Column(name = "lot_number")
    private String lotNumber;
    @Column(name = "remarks", columnDefinition = "text", nullable = true)
    private String remarks;
    private Boolean activeFlag;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "finished_fabric_code")
    private String finishedFabricCode;

    @Column(name = "finished_fabric_name")
    private String finishedFabricName;

    @Column(name = "greige_fabric_code")
    private String greigeFabricCode;

    @Column(name = "greige_fabric_name")
    private String greigeFabricName;

    // Add process type field
    @Column(name = "process_type")
    private String processType = "FABRIC"; // "FABRIC" or "YARN"

    // For fabric issues - use composition, not inheritance
    @OneToMany(mappedBy = "fabricDispatch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FabricDispatchFabricIssue> fabricIssues = new ArrayList<>();

    // For yarn issues - use composition, not inheritance  
    @OneToMany(mappedBy = "fabricDispatch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FabricDispatchYarnIssue> yarnIssues = new ArrayList<>();

    @Column(name = "dispatch_no")
    private String dispatchNo;


    // Default Constructor
    public FabricDispatchForDyeing() {}

    // Constructor with all fields
    public FabricDispatchForDyeing(Long id, LocalDate fabricDispatchDate, Long vendorId, String issueType, Long dyeingWorkOrderId,
                                   Double orderQuantity, Double dispatchedQuantity, Double receivedQuantity,
                                   Double balanceQuantity, Double costPerPound, Double totalAmount, Long colorId,
                                   String pantone, Long finishingId, Long salesOrderId, Long shipmentModeId,
                                   String lotNumber, String remarks, Boolean activeFlag, String processType) {
        this.id = id;
        this.fabricDispatchDate = fabricDispatchDate;
        this.vendorId = vendorId;
        this.issueType = issueType;
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
        this.lotNumber = lotNumber;
        this.remarks = remarks;
        this.activeFlag = activeFlag;
        this.processType = processType != null ? processType : "FABRIC";
    }

    // Getters and Setters (same as before)
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

    public Long getTransportId() { return transportId; }
    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getTransportName() { return transportName; }
    public void setTransportName(String transportName) { this.transportName = transportName; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public Double getGrossWt() { return grossWt; }
    public void setGrossWt(Double grossWt) { this.grossWt = grossWt; }

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

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

    public List<FabricDispatchFabricIssue> getFabricIssues() { return fabricIssues; }
    public void setFabricIssues(List<FabricDispatchFabricIssue> fabricIssues) { this.fabricIssues = fabricIssues; }

    public List<FabricDispatchYarnIssue> getYarnIssues() { return yarnIssues; }
    public void setYarnIssues(List<FabricDispatchYarnIssue> yarnIssues) { this.yarnIssues = yarnIssues; }

     public String getDispatchNo() { return dispatchNo; }
    public void setDispatchNo(String dispatchNo) { this.dispatchNo = dispatchNo; }
}