package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beam_outward", schema = "masters")
public class BeamOutward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "beam_inward_id")
    private Long beamInwardId;

    @Column(name = "beam_inward_no", length = 255)
    private String beamInwardNo;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_vendor_id")
    private Long weavingVendorId;

    @Column(name = "sizing_vendor_id")
    private Long sizingVendorId;

    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "sized_beam_no")
    private String sizedBeamNo;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "outward_no")
    private String outwardNo;

    @Column(name = "wrap_meters")
    private Double wrapMeters;

    private Double shrinkage;

    @Column(name = "expected_fabric_meter")
    private Double expectedFabricMeter;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "sizing_beam_outward_id")
    private Long sizingBeamOutwardId;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "outward_date")
    private LocalDate outwardDate;

    @Column(name = "fabric_panel_width")
    private String fabricPanelWidth;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "total_no_of_beams")
    private Long totalNoOfBeams;

    @Column(name = "total_beam_meters")
    private Double totalBeamMeters;

    @Column(name = "total_expected_meters")
    private Double totalExpectedMeters;

    @Column(name = "total_ends")
    private Long totalEnds;

    @OneToMany(mappedBy = "beamOutward", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeamOutwardDetail> outwardDetails = new ArrayList<>();

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;
    private Long weavingWorkOrderId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;


    @Column(name = "is_issued_to_weaving", columnDefinition = "bigint default 0")
    private Long isIssuedToWeaving = 0L;

    @Column(name = "is_factory_inward", columnDefinition = "bigint default 0")
    private Long isFactoryInward = 0L;

    private Long transportId;
    private String vehicleNo;
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBeamInwardId() {
        return beamInwardId;
    }

    public void setBeamInwardId(Long beamInwardId) {
        this.beamInwardId = beamInwardId;
    }

    public String getBeamInwardNo() {
        return beamInwardNo;
    }

    public void setBeamInwardNo(String beamInwardNo) {
        this.beamInwardNo = beamInwardNo;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public Long getWeavingVendorId() {
        return weavingVendorId;
    }

    public void setWeavingVendorId(Long weavingVendorId) {
        this.weavingVendorId = weavingVendorId;
    }

    public Long getSizingVendorId() {
        return sizingVendorId;
    }

    public void setSizingVendorId(Long sizingVendorId) {
        this.sizingVendorId = sizingVendorId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSizedBeamNo() {
        return sizedBeamNo;
    }

    public void setSizedBeamNo(String sizedBeamNo) {
        this.sizedBeamNo = sizedBeamNo;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public String getOutwardNo() {
        return outwardNo;
    }

    public void setOutwardNo(String outwardNo) {
        this.outwardNo = outwardNo;
    }

    public Double getWrapMeters() {
        return wrapMeters;
    }

    public void setWrapMeters(Double wrapMeters) {
        this.wrapMeters = wrapMeters;
    }

    public Double getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(Double shrinkage) {
        this.shrinkage = shrinkage;
    }

    public Double getExpectedFabricMeter() {
        return expectedFabricMeter;
    }

    public void setExpectedFabricMeter(Double expectedFabricMeter) {
        this.expectedFabricMeter = expectedFabricMeter;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Long getSizingBeamOutwardId() {
        return sizingBeamOutwardId;
    }

    public void setSizingBeamOutwardId(Long sizingBeamOutwardId) {
        this.sizingBeamOutwardId = sizingBeamOutwardId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public String getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(String fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Long getTotalNoOfBeams() {
        return totalNoOfBeams;
    }

    public void setTotalNoOfBeams(Long totalNoOfBeams) {
        this.totalNoOfBeams = totalNoOfBeams;
    }

    public Double getTotalBeamMeters() {
        return totalBeamMeters;
    }

    public void setTotalBeamMeters(Double totalBeamMeters) {
        this.totalBeamMeters = totalBeamMeters;
    }

    public Double getTotalExpectedMeters() {
        return totalExpectedMeters;
    }

    public void setTotalExpectedMeters(Double totalExpectedMeters) {
        this.totalExpectedMeters = totalExpectedMeters;
    }

    public Long getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Long totalEnds) { this.totalEnds = totalEnds; }

    public List<BeamOutwardDetail> getOutwardDetails() {
        return outwardDetails;
    }

    public void setOutwardDetails(List<BeamOutwardDetail> outwardDetails) {
        this.outwardDetails = outwardDetails;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }


    public Long getIsIssuedToWeaving() { return isIssuedToWeaving; }
    public void setIsIssuedToWeaving(Long isIssuedToWeaving) { this.isIssuedToWeaving = isIssuedToWeaving; }

    public Long getIsFactoryInward() { return isFactoryInward; }
    public void setIsFactoryInward(Long isFactoryInward) { this.isFactoryInward = isFactoryInward; }

    public Long getTransportId() { return transportId; }
    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
