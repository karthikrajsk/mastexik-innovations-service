package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class SizingProductionDTO {
    private Long id;

    @NotNull
    private LocalDate sizingDate;

    private Long sizingOrderId;
    private Long orderTypeId;
    private Long sizingPlanId;
    private String sizingPlanNo;
    private Long buyerId;
    private String buyerName;
    private String manualSetNo;
    private String sizingProductionNo;
    private Long yarnId;
    private String yarnName;
    private Long machineId;
    private String machineName;
    private Integer noOfBeams;
    private Double totalMeters;
    private Double totalNetWeight;
    private Integer sizingCount;

    @NotNull
    private Long companyId;

    private List<SizingProductionDetailDTO> productionDetails;
    private SizingProductionSummaryDTO summary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getSizingDate() { return sizingDate; }
    public void setSizingDate(LocalDate sizingDate) { this.sizingDate = sizingDate; }

    public Long getSizingOrderId() { return sizingOrderId; }
    public void setSizingOrderId(Long sizingOrderId) { this.sizingOrderId = sizingOrderId; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Long getSizingPlanId() { return sizingPlanId; }
    public void setSizingPlanId(Long sizingPlanId) { this.sizingPlanId = sizingPlanId; }

    public String getSizingPlanNo() { return sizingPlanNo; }
    public void setSizingPlanNo(String sizingPlanNo) { this.sizingPlanNo = sizingPlanNo; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getManualSetNo() { return manualSetNo; }
    public void setManualSetNo(String manualSetNo) { this.manualSetNo = manualSetNo; }

    public String getSizingProductionNo() { return sizingProductionNo; }
    public void setSizingProductionNo(String sizingProductionNo) { this.sizingProductionNo = sizingProductionNo; }

    public Long getYarnId() { return yarnId; }
    public void setYarnId(Long yarnId) { this.yarnId = yarnId; }

    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }

    public Long getMachineId() { return machineId; }
    public void setMachineId(Long machineId) { this.machineId = machineId; }

    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }

    public Integer getNoOfBeams() { return noOfBeams; }
    public void setNoOfBeams(Integer noOfBeams) { this.noOfBeams = noOfBeams; }

    public Double getTotalMeters() { return totalMeters; }
    public void setTotalMeters(Double totalMeters) { this.totalMeters = totalMeters; }

    public Double getTotalNetWeight() { return totalNetWeight; }
    public void setTotalNetWeight(Double totalNetWeight) { this.totalNetWeight = totalNetWeight; }

    public Integer getSizingCount() { return sizingCount; }
    public void setSizingCount(Integer sizingCount) { this.sizingCount = sizingCount; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<SizingProductionDetailDTO> getProductionDetails() { return productionDetails; }
    public void setProductionDetails(List<SizingProductionDetailDTO> productionDetails) { this.productionDetails = productionDetails; }

    public SizingProductionSummaryDTO getSummary() { return summary; }
    public void setSummary(SizingProductionSummaryDTO summary) { this.summary = summary; }
}
