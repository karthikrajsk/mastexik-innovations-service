package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sizing_production_detail", schema = "masters")
public class SizingProductionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizing_production_id")
    private SizingProduction sizingProduction;

    @Column(name = "shift_name")
    private String shiftName;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "total_ends")
    private Integer totalEnds;

    @Column(name = "sized_beam_no")
    private String sizedBeamNo;

    @Column(name = "beam_meters")
    private Double beamMeters;

    @Column(name = "expected_fabric_meter")
    private Double expectedFabricMeter;

    @Column(name = "sizing_pickup")
    private BigDecimal sizingPickup;

    @Column(name = "breakages")
    private Integer breakages;

    @Column(name = "rpm")
    private Integer rpm;

    @Column(name = "tare_weight")
    private Double tareWt;

    @Column(name = "gross_weight")
    private Double grossWt;

    @Column(name = "net_weight")
    private Double netWt;

    @Column(name = "fabric_panel_width")
    private Double fabricPanelWidth;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "issued_trading_company_id")
    private Long issuedTradingCompanyId;

    @Column(name = "is_trading_inward", nullable = false, columnDefinition = "bigint default 0")
    private Long isTradingInward = 0L;

    @Column(name = "is_issued_to_weaving", nullable = false, columnDefinition = "bigint default 0")
    private Long isIssuedToWeaving = 0L;

    @Column(name = "is_factory_inward", nullable = false, columnDefinition = "bigint default 0")
    private Long isFactoryInward = 0L;

    @Column(name = "is_sizing_beam_outward", nullable = false, columnDefinition = "int default 0")
    private Integer isSizingBeamOutward = 0;

    @Column(name = "is_return", nullable = false, columnDefinition = "int default 0")
    private Integer isReturn = 0;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "return_no")
    private String returnNo;

    @Column(name = "is_expected_fabric_meter_modified", columnDefinition = "boolean default false")
    private Boolean isExpectedFabricMeterModified = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SizingProduction getSizingProduction() { return sizingProduction; }
    public void setSizingProduction(SizingProduction sizingProduction) { this.sizingProduction = sizingProduction; }

    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }

    public Integer getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Integer totalEnds) { this.totalEnds = totalEnds; }

    public String getSizedBeamNo() { return sizedBeamNo; }
    public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

    public Double getBeamMeters() { return beamMeters; }
    public void setBeamMeters(Double beamMeters) { this.beamMeters = beamMeters; }

    public Double getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(Double expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

    public BigDecimal getSizingPickup() { return sizingPickup; }
    public void setSizingPickup(BigDecimal sizingPickup) { this.sizingPickup = sizingPickup; }

    public Integer getBreakages() { return breakages; }
    public void setBreakages(Integer breakages) { this.breakages = breakages; }

    public Integer getRpm() { return rpm; }
    public void setRpm(Integer rpm) { this.rpm = rpm; }

    public Double getTareWt() { return tareWt; }
    public void setTareWt(Double tareWt) { this.tareWt = tareWt; }

    public Double getGrossWt() { return grossWt; }
    public void setGrossWt(Double grossWt) { this.grossWt = grossWt; }

    public Double getNetWt() { return netWt; }
    public void setNetWt(Double netWt) { this.netWt = netWt; }

    public Double getFabricPanelWidth() { return fabricPanelWidth; }
    public void setFabricPanelWidth(Double fabricPanelWidth) { this.fabricPanelWidth = fabricPanelWidth; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Long getIssuedTradingCompanyId() { return issuedTradingCompanyId; }
    public void setIssuedTradingCompanyId(Long issuedTradingCompanyId) { this.issuedTradingCompanyId = issuedTradingCompanyId; }

    public Long getIsTradingInward() { return isTradingInward; }
    public void setIsTradingInward(Long isTradingInward) { this.isTradingInward = isTradingInward; }

    public Long getIsIssuedToWeaving() { return isIssuedToWeaving; }
    public void setIsIssuedToWeaving(Long isIssuedToWeaving) { this.isIssuedToWeaving = isIssuedToWeaving; }

    public Long getIsFactoryInward() { return isFactoryInward; }
    public void setIsFactoryInward(Long isFactoryInward) { this.isFactoryInward = isFactoryInward; }

    public Integer getIsSizingBeamOutward() { return isSizingBeamOutward; }
    public void setIsSizingBeamOutward(Integer isSizingBeamOutward) { this.isSizingBeamOutward = isSizingBeamOutward; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }
}
