// package com.tech3.erp.dto;

// import java.math.BigDecimal;

// public class YarnRequirementDTO {

//     private String yarnType;
//     private String yarnCount;
//     private BigDecimal gramsPerMeter;
//     private BigDecimal totalWeavingOrderQty;
//     private BigDecimal totalRequiredQty;
//     private BigDecimal totalAvailableQty;
//     private Boolean activeFlag = true;

//     public String getYarnType() {
//         return yarnType;
//     }

//     public void setYarnType(String yarnType) {
//         this.yarnType = yarnType;
//     }

//     public String getYarnCount() {
//         return yarnCount;
//     }

//     public void setYarnCount(String yarnCount) {
//         this.yarnCount = yarnCount;
//     }

//     public BigDecimal getGramsPerMeter() {
//         return gramsPerMeter;
//     }

//     public void setGramsPerMeter(BigDecimal gramsPerMeter) {
//         this.gramsPerMeter = gramsPerMeter;
//     }

//     public BigDecimal getTotalWeavingOrderQty() {
//         return totalWeavingOrderQty;
//     }

//     public void setTotalWeavingOrderQty(BigDecimal totalWeavingOrderQty) {
//         this.totalWeavingOrderQty = totalWeavingOrderQty;
//     }

//     public BigDecimal getTotalRequiredQty() {
//         return totalRequiredQty;
//     }

//     public void setTotalRequiredQty(BigDecimal totalRequiredQty) {
//         this.totalRequiredQty = totalRequiredQty;
//     }

//     public BigDecimal getTotalAvailableQty() {
//         return totalAvailableQty;
//     }

//     public void setTotalAvailableQty(BigDecimal totalAvailableQty) {
//         this.totalAvailableQty = totalAvailableQty;
//     }

//     public Boolean getActiveFlag() {
//         return activeFlag;
//     }

//     public void setActiveFlag(Boolean activeFlag) {
//         this.activeFlag = activeFlag;
//     }
// }




package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class WeavingYarnIssueDTO {
    private Long id;
    private Long vendorId;
    private Long weavingContractId;
    private String weavingContractNo;
    private Long weavingJobWorkOrderId;
    private String transportationDetails;
    private Long transportId;
    private Long termsConditionsId;
    private String fabricDetails;
    private LocalDate yarnIssueDate;
    private String yarnIssueChallanNo;
    private boolean activeFlag;
    private Long orderTypeId;
    private Long tradingCompanyId;
    private Long factoryCompanyId;
    private String remarks;
    private String vehicleNo;
    private BigDecimal grossWt;

    private List<WeavingYarnRequirementDTO> requirements;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }

    public Long getWeavingJobWorkOrderId() {
        return weavingJobWorkOrderId;
    }

    public void setWeavingJobWorkOrderId(Long weavingJobWorkOrderId) {
        this.weavingJobWorkOrderId = weavingJobWorkOrderId;
    }

    public String getTransportationDetails() {
        return transportationDetails;
    }

    public void setTransportationDetails(String transportationDetails) {
        this.transportationDetails = transportationDetails;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public LocalDate getYarnIssueDate() {
        return yarnIssueDate;
    }

    public void setYarnIssueDate(LocalDate yarnIssueDate) {
        this.yarnIssueDate = yarnIssueDate;
    }

    public String getYarnIssueChallanNo() {
        return yarnIssueChallanNo;
    }

    public void setYarnIssueChallanNo(String yarnIssueChallanNo) {
        this.yarnIssueChallanNo = yarnIssueChallanNo;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public BigDecimal getGrossWt() { return grossWt; }
    public void setGrossWt(BigDecimal grossWt) { this.grossWt = grossWt; }
    
    public List<WeavingYarnRequirementDTO> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<WeavingYarnRequirementDTO> requirements) {
        this.requirements = requirements;
    }
    
}