package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "finish_fabric_receive", schema = "masters")
public class FinishFabricReceive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fabric_receive_date")
    private LocalDate fabricReceiveDate;

    @Column(name = "dc_date")
    private LocalDate dcDate; // ✅ Added DC Date

    @Column(name = "dc_number")
    private String dcNumber; // ✅ Added DC Number

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "dyeing_work_order_id")
    private Long dyeingWorkOrderId;

    @Column(name = "dyeing_work_order_no")
    private String dyeingWorkOrderNo;

    @Column(name = "order_quantity")
    private Double orderQuantity;

    @Column(name = "cost_per_pound")
    private Double costPerPound;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "receive_no")
    private String receiveNo;

    @Column(name = "color_id")
    private Long colorId;

    @Column(name = "pantone")
    private String pantone;

    @Column(name = "finishing_id")
    private Long finishingId;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "received_quantity")
    private Double receivedQuantity;

    @Column(name = "balance_quantity")
    private Double balanceQuantity;

    @Column(name = "finished_fabric_code_id")
    private Long finishedFabricCodeId;

    @Column(name = "greige_fabric_code_id")
    private Long greigeFabricCodeId;

    @Column(name = "finished_fabric_code")
    private String finishedFabricCode;

    @Column(name = "greige_fabric_code")
    private String greigeFabricCode;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "process_type")
    private String processType = "FABRIC";

    // For fabric items
    @OneToMany(mappedBy = "finishFabricReceive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FinishFabricReceiveItem> items;

    // For yarn items (when processType = "YARN")
    @OneToMany(mappedBy = "finishFabricReceive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FinishYarnReceiveItem> yarnItems;

    // ===== Getters and Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFabricReceiveDate() { return fabricReceiveDate; }
    public void setFabricReceiveDate(LocalDate fabricReceiveDate) { this.fabricReceiveDate = fabricReceiveDate; }

    public LocalDate getDcDate() { return dcDate; }  // ✅ Getter
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }  // ✅ Setter

    public String getDcNumber() { return dcNumber; }  // ✅ Getter
    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }  // ✅ Setter

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Long getDyeingWorkOrderId() { return dyeingWorkOrderId; }
    public void setDyeingWorkOrderId(Long dyeingWorkOrderId) { this.dyeingWorkOrderId = dyeingWorkOrderId; }

    public String getDyeingWorkOrderNo() { return dyeingWorkOrderNo; }
    public void setDyeingWorkOrderNo(String dyeingWorkOrderNo) { this.dyeingWorkOrderNo = dyeingWorkOrderNo; }

    public Double getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(Double orderQuantity) { this.orderQuantity = orderQuantity; }

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

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public Double getReceivedQuantity() { return receivedQuantity; }
    public void setReceivedQuantity(Double receivedQuantity) { this.receivedQuantity = receivedQuantity; }

    public Double getBalanceQuantity() { return balanceQuantity; }
    public void setBalanceQuantity(Double balanceQuantity) { this.balanceQuantity = balanceQuantity; }

    public Long getFinishedFabricCodeId() { return finishedFabricCodeId; }
    public void setFinishedFabricCodeId(Long finishedFabricCodeId) { this.finishedFabricCodeId = finishedFabricCodeId; }

    public Long getGreigeFabricCodeId() { return greigeFabricCodeId; }
    public void setGreigeFabricCodeId(Long greigeFabricCodeId) { this.greigeFabricCodeId = greigeFabricCodeId; }

    public String getFinishedFabricCode() { return finishedFabricCode; }
    public void setFinishedFabricCode(String finishedFabricCode) { this.finishedFabricCode = finishedFabricCode; }

    public String getGreigeFabricCode() { return greigeFabricCode; }
    public void setGreigeFabricCode(String greigeFabricCode) { this.greigeFabricCode = greigeFabricCode; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getProcessType() { return processType; }
    public void setProcessType(String processType) { this.processType = processType; }

    public List<FinishFabricReceiveItem> getItems() { return items; }
    public void setItems(List<FinishFabricReceiveItem> items) { this.items = items; }

    public List<FinishYarnReceiveItem> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<FinishYarnReceiveItem> yarnItems) { this.yarnItems = yarnItems; }

    public String getReceiveNo() { return receiveNo; }
    public void setReceiveNo(String receiveNo) { this.receiveNo = receiveNo; }
}
