package com.tech3.erp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warping_production", schema = "masters")
public class WarpingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warping_date")
    private LocalDate warpingDate;

    @Column(name = "sizing_order_id")
    private Long sizingOrderId;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "manual_set_no")
    private String manualSetNo;

    @Column(name = "warping_production_no")
    private String warpingProductionNo;

    @Column(name = "parts")
    private Integer parts;

    @Column(name = "ends_per_part")
    private Integer endsPerPart;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "total_meters")
    private Double totalMeters;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "avg_actual_meters")
    private Double actualMetersAverage;

    @Column(name = "total_actual_meters")
    private Double totalActualMeters;

    @Column(name = "total_ends")
    private Integer totalEnds;

    @Column(name = "total_net_weight")
    private Double totalNetWeight;

    @Column(name = "breakages_per_million")
    private Double breakagesPerMillion;

    @Column(name = "warp_count")
    private Integer warpCount;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @OneToMany(mappedBy = "warpingProduction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WarpingProductionDetail> productionDetails = new ArrayList<>();

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

    public Double getActualMetersAverage() { return actualMetersAverage; }
    public void setActualMetersAverage(Double actualMetersAverage) { this.actualMetersAverage = actualMetersAverage; }

    public Double getTotalActualMeters() { return totalActualMeters; }
    public void setTotalActualMeters(Double totalActualMeters) { this.totalActualMeters = totalActualMeters; }

    public Integer getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Integer totalEnds) { this.totalEnds = totalEnds; }

    public Double getTotalNetWeight() { return totalNetWeight; }
    public void setTotalNetWeight(Double totalNetWeight) { this.totalNetWeight = totalNetWeight; }

    public Double getBreakagesPerMillion() { return breakagesPerMillion; }
    public void setBreakagesPerMillion(Double breakagesPerMillion) { this.breakagesPerMillion = breakagesPerMillion; }

    public Integer getWarpCount() { return warpCount; }
    public void setWarpCount(Integer warpCount) { this.warpCount = warpCount; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<WarpingProductionDetail> getProductionDetails() { return productionDetails; }
    public void setProductionDetails(List<WarpingProductionDetail> productionDetails) { this.productionDetails = productionDetails; }
}
