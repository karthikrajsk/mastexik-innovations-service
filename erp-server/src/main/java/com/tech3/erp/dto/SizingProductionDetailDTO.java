package com.tech3.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SizingProductionDetailDTO {
    private Long id;
    private String shiftName;
    private String flangeNo;
    private Long flangeId;
    private Integer totalEnds;
    private String sizedBeamNo;
    private Double beamMeters;
    private Double expectedFabricMeter;
    private BigDecimal sizingPickup;
    private Integer breakages;
    private Integer rpm;
    private Double tareWt;
    private Double grossWt;
    private Double netWt;
    private Double fabricPanelWidth;
    private Long employeeId;
    private Long orderTypeId;
    private Long issuedTradingCompanyId;
    private Long isTradingInward;
    private Long isIssuedToWeaving;
    private Long isFactoryInward;
    private Integer isSizingBeamOutward;
    private Integer isReturn;
    private LocalDate returnDate;
    private String returnNo;
    private Boolean isExpectedFabricMeterModified;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
