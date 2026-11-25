package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class FinishFabricReceiveDTO {
    private Long id;
    private LocalDate fabricReceiveDate;
    private LocalDate dcDate;       // ✅ Added DC Date
    private String dcNumber;        // ✅ Added DC Number
    private String receiveNo;       // ✅ Added Receive No
    private Long vendorId;
    private Long dyeingWorkOrderId;
    private String dyeingWorkOrderNo;
    private Double orderQuantity;
    private Double costPerPound;
    private Double totalAmount;
    private Long colorId;
    private String pantone;
    private Long finishingId;
    private String lotNumber;
    private Double receivedQuantity;
    private Double balanceQuantity;
    private Long finishedFabricCodeId;
    private Long greigeFabricCodeId;
    private String finishedFabricCode;
    private String greigeFabricCode;
    private String remarks;
    private Boolean activeFlag;
    private Long companyId;
    private String processType; // "FABRIC" or "YARN"
    private List<FinishFabricReceiveItemDTO> items;
    private List<FinishYarnReceiveItemDTO> yarnItems;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFabricReceiveDate() { return fabricReceiveDate; }
    public void setFabricReceiveDate(LocalDate fabricReceiveDate) { this.fabricReceiveDate = fabricReceiveDate; }

    public LocalDate getDcDate() { return dcDate; }             // ✅ Getter
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; } // ✅ Setter

    public String getDcNumber() { return dcNumber; }            // ✅ Getter
    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; } // ✅ Setter

    public String getReceiveNo() { return receiveNo; }          // ✅ Getter
    public void setReceiveNo(String receiveNo) { this.receiveNo = receiveNo; } // ✅ Setter

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

    public List<FinishFabricReceiveItemDTO> getItems() { return items; }
    public void setItems(List<FinishFabricReceiveItemDTO> items) { this.items = items; }

    public List<FinishYarnReceiveItemDTO> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<FinishYarnReceiveItemDTO> yarnItems) { this.yarnItems = yarnItems; }
}
