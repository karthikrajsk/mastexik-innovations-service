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
@Table(name = "sizing_production", schema = "masters")
public class SizingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sizing_date")
    private LocalDate sizingDate;

    @Column(name = "sizing_order_id")
    private Long sizingOrderId;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "sizing_plan_no")
    private String sizingPlanNo;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "manual_set_no")
    private String manualSetNo;

    @Column(name = "sizing_production_no")
    private String sizingProductionNo;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "machine_name")
    private String machineName;

    @Column(name = "no_of_beams")
    private Integer noOfBeams;

    @Column(name = "total_meters")
    private Double totalMeters;

    @Column(name = "total_net_weight")
    private Double totalNetWeight;

    @Column(name = "sizing_count")
    private Integer sizingCount;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @OneToMany(mappedBy = "sizingProduction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SizingProductionDetail> productionDetails = new ArrayList<>();

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

    public List<SizingProductionDetail> getProductionDetails() { return productionDetails; }
    public void setProductionDetails(List<SizingProductionDetail> productionDetails) { this.productionDetails = productionDetails; }
}
