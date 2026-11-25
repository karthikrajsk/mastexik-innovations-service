package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CostingTransactionDto {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String costingNo;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Customer name is required")
    @Size(min = 1, max = 100, message = "Customer name must be between 1 and 100 characters")
    private String customerName;

    @NotNull(message = "Fabric code quality is required")
    @Size(min = 1, max = 100, message = "Fabric code quality must be between 1 and 100 characters")
    private String fabricCodeQuality;

    @Size(min = 1, max = 100, message = "Style name must be between 1 and 100 characters")
    private String styleName;

    @NotNull(message = "UOM is required")
    private Integer uom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String uomFlag;

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    @NotNull(message = "Agent ID is required")
    private Long agentId;

    private Long companyId;

    private Long fabricTypeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalYarnWeight;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalFabricWeight;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalYarnCost;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalFabricCost;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalCost;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

    private boolean activeFlag = true;

    private List<CostingTransactionYarnDto> yarnDetails;

    private FabricDetailsDto fabricDetails;

    private FabricCostDataDto fabricCostData;

    private FinalCostingDataDto finalCostingData;
    
    // NEW FIELD - Yarn Cost Summary
    private YarnCostSummaryDto yarnCostSummary;

    @NotNull(message = "Created by is required")
    private String createdBy;

    // Constructors
    public CostingTransactionDto() {}

    // Existing Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCostingNo() { return costingNo; }
    public void setCostingNo(String costingNo) { this.costingNo = costingNo; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

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

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

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

    public boolean isActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }

    public List<CostingTransactionYarnDto> getYarnDetails() { return yarnDetails; }
    public void setYarnDetails(List<CostingTransactionYarnDto> yarnDetails) { this.yarnDetails = yarnDetails; }

    public FabricDetailsDto getFabricDetails() { return fabricDetails; }
    public void setFabricDetails(FabricDetailsDto fabricDetails) { this.fabricDetails = fabricDetails; }

    public FabricCostDataDto getFabricCostData() { return fabricCostData; }
    public void setFabricCostData(FabricCostDataDto fabricCostData) { this.fabricCostData = fabricCostData; }

    public FinalCostingDataDto getFinalCostingData() { return finalCostingData; }
    public void setFinalCostingData(FinalCostingDataDto finalCostingData) { this.finalCostingData = finalCostingData; }

    public YarnCostSummaryDto getYarnCostSummary() { return yarnCostSummary; }
    public void setYarnCostSummary(YarnCostSummaryDto yarnCostSummary) { this.yarnCostSummary = yarnCostSummary; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}