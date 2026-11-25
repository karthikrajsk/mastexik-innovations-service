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

@Entity
@Table(name = "warping_production_detail", schema = "masters")
public class WarpingProductionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warping_production_id")
    private WarpingProduction warpingProduction;

    @Column(name = "shift")
    private String shift;

    @Column(name = "shift_name")
    private String shiftName;

    @Column(name = "warp_beam_no")
    private String warpBeamNo;

    @Column(name = "ends_per_part")
    private Integer endsPerPart;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "actual_meters")
    private Double actualMeters;

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

    @Column(name = "employee_id")
    private Long employeeId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WarpingProduction getWarpingProduction() { return warpingProduction; }
    public void setWarpingProduction(WarpingProduction warpingProduction) { this.warpingProduction = warpingProduction; }

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
