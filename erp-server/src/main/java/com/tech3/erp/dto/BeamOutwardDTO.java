package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BeamOutwardDTO {
    private Long id;
    private Long beamInwardId;
    private String beamInwardNo;
    private Long weavingContractId;
    private Long weavingVendorId;
    private Long sizingVendorId;
    private Long salesOrderId;
    private String sizedBeamNo;
    private String flangeNo;
    private Long flangeId;
    private String outwardNo;
    private Double wrapMeters;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private Long sizingPlanId;
    private Long orderTypeId;
    private LocalDate outwardDate;
    private String fabricPanelWidth;
    private String financialYear;
    private Long totalNoOfBeams;
    private Double totalBeamMeters;
    private Double totalExpectedMeters;
    private Long totalEnds;
    private List<BeamOutwardDetailDTO> outwardDetails = new ArrayList<>();
    private Long companyId;
    private Long fabricMasterId;
    private Long weavingWorkOrderId;
    private Long tradingCompanyId;
    private Long factoryCompanyId;
    private Integer isReturn;
    private Long isIssuedToWeaving;
    private Long isFactoryInward;
    private LocalDate returnDate;
    private String returnNo;
    private Long returnWeaverId;
    private Long transportId;
    private String vehicleNo;
    private String remarks;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBeamInwardId() { return beamInwardId; }
    public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }

    public String getBeamInwardNo() { return beamInwardNo; }
    public void setBeamInwardNo(String beamInwardNo) { this.beamInwardNo = beamInwardNo; }

    public Long getWeavingContractId() { return weavingContractId; }
    public void setWeavingContractId(Long weavingContractId) { this.weavingContractId = weavingContractId; }

    public Long getWeavingVendorId() { return weavingVendorId; }
    public void setWeavingVendorId(Long weavingVendorId) { this.weavingVendorId = weavingVendorId; }

    public Long getSizingVendorId() { return sizingVendorId; }
    public void setSizingVendorId(Long sizingVendorId) { this.sizingVendorId = sizingVendorId; }

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    public String getSizedBeamNo() { return sizedBeamNo; }
    public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }

    public String getOutwardNo() { return outwardNo; }
    public void setOutwardNo(String outwardNo) { this.outwardNo = outwardNo; }

    public Double getWrapMeters() { return wrapMeters; }
    public void setWrapMeters(Double wrapMeters) { this.wrapMeters = wrapMeters; }

    public Double getShrinkage() { return shrinkage; }
    public void setShrinkage(Double shrinkage) { this.shrinkage = shrinkage; }

    public Double getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(Double expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

    public Long getSizingPlanId() { return sizingPlanId; }
    public void setSizingPlanId(Long sizingPlanId) { this.sizingPlanId = sizingPlanId; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public LocalDate getOutwardDate() { return outwardDate; }
    public void setOutwardDate(LocalDate outwardDate) { this.outwardDate = outwardDate; }

    public String getFabricPanelWidth() { return fabricPanelWidth; }
    public void setFabricPanelWidth(String fabricPanelWidth) { this.fabricPanelWidth = fabricPanelWidth; }

    public String getFinancialYear() { return financialYear; }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }

    public Long getTotalNoOfBeams() { return totalNoOfBeams; }
    public void setTotalNoOfBeams(Long totalNoOfBeams) { this.totalNoOfBeams = totalNoOfBeams; }

    public Double getTotalBeamMeters() { return totalBeamMeters; }
    public void setTotalBeamMeters(Double totalBeamMeters) { this.totalBeamMeters = totalBeamMeters; }

    public Double getTotalExpectedMeters() { return totalExpectedMeters; }
    public void setTotalExpectedMeters(Double totalExpectedMeters) { this.totalExpectedMeters = totalExpectedMeters; }

    public Long getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Long totalEnds) { this.totalEnds = totalEnds; }

    public List<BeamOutwardDetailDTO> getOutwardDetails() { return outwardDetails; }
    public void setOutwardDetails(List<BeamOutwardDetailDTO> outwardDetails) { this.outwardDetails = outwardDetails != null ? outwardDetails : new ArrayList<>(); }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getIsIssuedToWeaving() { return isIssuedToWeaving; }
    public void setIsIssuedToWeaving(Long isIssuedToWeaving) { this.isIssuedToWeaving = isIssuedToWeaving; }

    public Long getIsFactoryInward() { return isFactoryInward; }
    public void setIsFactoryInward(Long isFactoryInward) { this.isFactoryInward = isFactoryInward; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

    public Long getReturnWeaverId() { return returnWeaverId; }
    public void setReturnWeaverId(Long returnWeaverId) { this.returnWeaverId = returnWeaverId; }

    public Long getTransportId() { return transportId; }
    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
