package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class WarpingProductionDTO {
    private Long id;

    @NotNull
    private LocalDate warpingDate;

    private Long sizingOrderId;
    private Long sizingPlanId;
    private String buyerName;
    private Long buyerId;
    private String manualSetNo;
    private String warpingProductionNo;
    private Integer parts;
    private Integer endsPerPart;
    private Long machineId;
    private String yarnName;
    private Long yarnId;
    private Double totalMeters;
    private Double netWeight;

    @NotNull
    private Long companyId;

    private List<WarpingProductionDetailDTO> productionDetails;
    private WarpingProductionSummaryDTO summary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getWarpingDate() { return warpingDate; }
    public void setWarpingDate(LocalDate warpingDate) { this.warpingDate = warpingDate; }

    public Long getSizingOrderId() { return sizingOrderId; }
    public void setSizingOrderId(Long sizingOrderId) { this.sizingOrderId = sizingOrderId; }

    public Long getSizingPlanId() { return sizingPlanId; }
    public void setSizingPlanId(Long sizingPlanId) { this.sizingPlanId = sizingPlanId; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public String getManualSetNo() { return manualSetNo; }
    public void setManualSetNo(String manualSetNo) { this.manualSetNo = manualSetNo; }

    public String getWarpingProductionNo() { return warpingProductionNo; }
    public void setWarpingProductionNo(String warpingProductionNo) { this.warpingProductionNo = warpingProductionNo; }

    public Integer getParts() { return parts; }
    public void setParts(Integer parts) { this.parts = parts; }

    public Integer getEndsPerPart() { return endsPerPart; }
    public void setEndsPerPart(Integer endsPerPart) { this.endsPerPart = endsPerPart; }

    public Long getMachineId() { return machineId; }
    public void setMachineId(Long machineId) { this.machineId = machineId; }

    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }

    public Long getYarnId() { return yarnId; }
    public void setYarnId(Long yarnId) { this.yarnId = yarnId; }

    public Double getTotalMeters() { return totalMeters; }
    public void setTotalMeters(Double totalMeters) { this.totalMeters = totalMeters; }

    public Double getNetWeight() { return netWeight; }
    public void setNetWeight(Double netWeight) { this.netWeight = netWeight; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<WarpingProductionDetailDTO> getProductionDetails() { return productionDetails; }
    public void setProductionDetails(List<WarpingProductionDetailDTO> productionDetails) { this.productionDetails = productionDetails; }

    public WarpingProductionSummaryDTO getSummary() { return summary; }
    public void setSummary(WarpingProductionSummaryDTO summary) { this.summary = summary; }
}
