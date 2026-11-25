package com.tech3.erp.dto;

public class WarpingProductionDetailDTO {
    private Long id;
    private String shift;
    private String shiftName;
    private String warpBeamNo;
    private Integer endsPerPart;
    private String startTime;
    private String endTime;
    private Double actualMeters;
    private Integer breakages;
    private Integer rpm;
    private Double tareWt;
    private Double grossWt;
    private Double netWt;
    private Long employeeId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }

    public String getWarpBeamNo() { return warpBeamNo; }
    public void setWarpBeamNo(String warpBeamNo) { this.warpBeamNo = warpBeamNo; }

    public Integer getEndsPerPart() { return endsPerPart; }
    public void setEndsPerPart(Integer endsPerPart) { this.endsPerPart = endsPerPart; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public Double getActualMeters() { return actualMeters; }
    public void setActualMeters(Double actualMeters) { this.actualMeters = actualMeters; }

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

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
}
