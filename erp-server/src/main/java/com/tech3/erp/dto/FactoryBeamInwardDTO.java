package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class FactoryBeamInwardDTO {
    private Long id;
    private Long sizingVendorId;
    private Long sizingPlanId;
    private String sizingSetNo;
    private String dcNo;
    private String dcDate;
    private String weavingContractId;
    private Long weavingWorkOrderId;
    private Long orderTypeId;
    private String remarks;
    private String beamInwardNo;
    private LocalDate beamInwardDate;

    private Long beamInwardId;
    private Long beamOutwardId;
    private Integer isBeamPending;
    private String fabricCodeQuality;
    private Long tradingCompanyId;
    private Long factoryCompanyId;

    private List<FactoryBeamInwardQualityDetailsDTO> beamInwardQualityDetails;
    private List<FactoryBeamInwardBeamDetailsDTO> beamInwardBeamDetails;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSizingPlanId() { return sizingPlanId; }
    public void setSizingPlanId(Long sizingPlanId) { this.sizingPlanId = sizingPlanId; }

    public Long getSizingVendorId() { return sizingVendorId; }
    public void setSizingVendorId(Long sizingVendorId) { this.sizingVendorId = sizingVendorId; }



    public String getSizingSetNo() { return sizingSetNo; }
    public void setSizingSetNo(String sizingSetNo) { this.sizingSetNo = sizingSetNo; }

    public String getDcNo() { return dcNo; }
    public void setDcNo(String dcNo) { this.dcNo = dcNo; }

    public String getDcDate() { return dcDate; }
    public void setDcDate(String dcDate) { this.dcDate = dcDate; }

    public String getWeavingContractId() { return weavingContractId; }
    public void setWeavingContractId(String weavingContractId) { this.weavingContractId = weavingContractId; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getBeamInwardNo() { return beamInwardNo; }
    public void setBeamInwardNo(String beamInwardNo) { this.beamInwardNo = beamInwardNo; }

    public List<FactoryBeamInwardQualityDetailsDTO> getBeamInwardQualityDetails() { return beamInwardQualityDetails; }
    public void setBeamInwardQualityDetails(List<FactoryBeamInwardQualityDetailsDTO> beamInwardQualityDetails) { this.beamInwardQualityDetails = beamInwardQualityDetails; }

    public List<FactoryBeamInwardBeamDetailsDTO> getBeamInwardBeamDetails() { return beamInwardBeamDetails; }
    public void setBeamInwardBeamDetails(List<FactoryBeamInwardBeamDetailsDTO> beamInwardBeamDetails) { this.beamInwardBeamDetails = beamInwardBeamDetails; }

    public LocalDate getBeamInwardDate() { return beamInwardDate; }
    public void setBeamInwardDate(LocalDate beamInwardDate) { this.beamInwardDate = beamInwardDate; }

    public Long getBeamInwardId() { return beamInwardId; }
    public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }

    public Long getBeamOutwardId() { return beamOutwardId; }
    public void setBeamOutwardId(Long beamOutwardId) { this.beamOutwardId = beamOutwardId; }

    public Integer getIsBeamPending() { return isBeamPending; }
    public void setIsBeamPending(Integer isBeamPending) { this.isBeamPending = isBeamPending; }

    public String getFabricCodeQuality() { return fabricCodeQuality; }
    public void setFabricCodeQuality(String fabricCodeQuality) { this.fabricCodeQuality = fabricCodeQuality; }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }
}
