package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "costing_transaction", schema = "masters")
public class CostingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "costing_no", unique = true, nullable = false)
    private String costingNo;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "fabric_code_quality", nullable = false)
    private String fabricCodeQuality;

    @Column(name = "style_name")
    private String styleName;

    @Column(name = "uom", nullable = false)
    private Integer uom;

    @Column(name = "uom_flag", nullable = false)
    private String uomFlag;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "fabric_type_id")
    private Long fabricTypeId;

    @Column(name = "total_yarn_weight", precision = 10, scale = 3)
    private BigDecimal totalYarnWeight;

    @Column(name = "total_fabric_weight", precision = 10, scale = 3)
    private BigDecimal totalFabricWeight;

    @Column(name = "total_yarn_cost", precision = 10, scale = 2)
    private BigDecimal totalYarnCost;

    @Column(name = "total_fabric_cost", precision = 10, scale = 2)
    private BigDecimal totalFabricCost;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany(mappedBy = "costingTransaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CostingTransactionYarn> yarnDetails;

    @Embedded
    private FabricDetails fabricDetails;

    @Embedded
    private FabricCostData fabricCostData;

    @Embedded
    private FinalCostingData finalCostingData;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    // Constructors
    public CostingTransaction() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCostingNo() { return costingNo; }
    public void setCostingNo(String costingNo) { this.costingNo = costingNo; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public String getFabricCodeQuality() { return fabricCodeQuality; }
    public void setFabricCodeQuality(String fabricCodeQuality) { this.fabricCodeQuality = fabricCodeQuality; }

    public String getStyleName() { return styleName; }
    public void setStyleName(String styleName) { this.styleName = styleName; }

    public Integer getUom() { return uom; }
    public void setUom(Integer uom) { this.uom = uom; }

    public String getUomFlag() { return uomFlag; }
    public void setUomFlag(String uomFlag) { this.uomFlag = uomFlag; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getFabricTypeId() { return fabricTypeId; }
    public void setFabricTypeId(Long fabricTypeId) { this.fabricTypeId = fabricTypeId; }

    public BigDecimal getTotalYarnWeight() { return totalYarnWeight; }
    public void setTotalYarnWeight(BigDecimal totalYarnWeight) { this.totalYarnWeight = totalYarnWeight; }

    public BigDecimal getTotalFabricWeight() { return totalFabricWeight; }
    public void setTotalFabricWeight(BigDecimal totalFabricWeight) { this.totalFabricWeight = totalFabricWeight; }

    public BigDecimal getTotalYarnCost() { return totalYarnCost; }
    public void setTotalYarnCost(BigDecimal totalYarnCost) { this.totalYarnCost = totalYarnCost; }

    public BigDecimal getTotalFabricCost() { return totalFabricCost; }
    public void setTotalFabricCost(BigDecimal totalFabricCost) { this.totalFabricCost = totalFabricCost; }

    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<CostingTransactionYarn> getYarnDetails() { return yarnDetails; }
    public void setYarnDetails(List<CostingTransactionYarn> yarnDetails) { this.yarnDetails = yarnDetails; }

    public FabricDetails getFabricDetails() { return fabricDetails; }
    public void setFabricDetails(FabricDetails fabricDetails) { this.fabricDetails = fabricDetails; }

    public FabricCostData getFabricCostData() { return fabricCostData; }
    public void setFabricCostData(FabricCostData fabricCostData) { this.fabricCostData = fabricCostData; }

    public FinalCostingData getFinalCostingData() { return finalCostingData; }
    public void setFinalCostingData(FinalCostingData finalCostingData) { this.finalCostingData = finalCostingData; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}