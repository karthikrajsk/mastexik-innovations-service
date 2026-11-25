package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "weaving_live_production", schema = "masters")
public class WeavingLiveProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "machine_name", length = 255)
    private String machineName;

    @Column(name = "sort_no", length = 255)
    private String sortNo;

    @Column(name = "construction", length = 500)
    private String construction;

    @Column(name = "actual_efficiency", precision = 10, scale = 2)
    private BigDecimal actualEfficiency;

    @Column(name = "production_efficiency", precision = 10, scale = 2)
    private BigDecimal productionEfficiency;

    @Column(name = "kilo_picks", precision = 10, scale = 2)
    private BigDecimal kiloPicks;

    @Column(name = "meter", precision = 10, scale = 2)
    private BigDecimal meter;

    @Column(name = "rpm")
    private Integer rpm;

    @Column(name = "warp_l_no", length = 255)
    private String warpLNo;

    @Column(name = "warp")
    private Integer warp;

    @Column(name = "warp_supplier", length = 255)
    private String warpSupplier;

    @Column(name = "warp_time", precision = 10, scale = 2)
    private BigDecimal warpTime;

    @Column(name = "minutes_warp", precision = 10, scale = 2)
    private BigDecimal minutesWarp;

    @Column(name = "weft")
    private Integer weft;

    @Column(name = "weft_l_no", length = 255)
    private String weftLNo;

    @Column(name = "weft_supplier", length = 255)
    private String weftSupplier;

    @Column(name = "weft_time", precision = 10, scale = 2)
    private BigDecimal weftTime;

    @Column(name = "minutes_weft", precision = 10, scale = 2)
    private BigDecimal minutesWeft;

    @Column(name = "others")
    private Integer others;

    @Column(name = "others_stop_time", precision = 10, scale = 2)
    private BigDecimal othersStopTime;

    @Column(name = "long_stops")
    private Integer longStops;

    @Column(name = "long_stops_time", precision = 10, scale = 2)
    private BigDecimal longStopsTime;

    @Column(name = "run_time", precision = 10, scale = 2)
    private BigDecimal runTime;

    @Column(name = "total_stop_time", precision = 10, scale = 2)
    private BigDecimal totalStopTime;

    @Column(name = "running_cfm", precision = 10, scale = 2)
    private BigDecimal runningCfm;

    @Column(name = "ideal_cfm", precision = 10, scale = 2)
    private BigDecimal idealCfm;

    @Column(name = "tc_and_weave", length = 255)
    private String tcAndWeave;

    @Column(name = "wp_cmpx", precision = 10, scale = 2)
    private BigDecimal wpCmpx;

    @Column(name = "wt_cmpx", precision = 10, scale = 2)
    private BigDecimal wtCmpx;

    @Column(name = "other_stops", length = 255)
    private String otherStops;

    @Column(name = "short_stops", length = 255)
    private String shortStops;

    @Column(name = "running_hours_time", length = 255)
    private String runningHoursTime;

    @Column(name = "stop_hours_time", length = 255)
    private String stopHoursTime;

    @Column(name = "incentive", precision = 10, scale = 2)
    private BigDecimal incentive;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public WeavingLiveProduction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getLoomId() {
        return loomId;
    }

    public void setLoomId(Long loomId) {
        this.loomId = loomId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public BigDecimal getActualEfficiency() {
        return actualEfficiency;
    }

    public void setActualEfficiency(BigDecimal actualEfficiency) {
        this.actualEfficiency = actualEfficiency;
    }

    public BigDecimal getProductionEfficiency() {
        return productionEfficiency;
    }

    public void setProductionEfficiency(BigDecimal productionEfficiency) {
        this.productionEfficiency = productionEfficiency;
    }

    public BigDecimal getKiloPicks() {
        return kiloPicks;
    }

    public void setKiloPicks(BigDecimal kiloPicks) {
        this.kiloPicks = kiloPicks;
    }

    public BigDecimal getMeter() {
        return meter;
    }

    public void setMeter(BigDecimal meter) {
        this.meter = meter;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public String getWarpLNo() {
        return warpLNo;
    }

    public void setWarpLNo(String warpLNo) {
        this.warpLNo = warpLNo;
    }

    public Integer getWarp() {
        return warp;
    }

    public void setWarp(Integer warp) {
        this.warp = warp;
    }

    public String getWarpSupplier() {
        return warpSupplier;
    }

    public void setWarpSupplier(String warpSupplier) {
        this.warpSupplier = warpSupplier;
    }

    public BigDecimal getWarpTime() {
        return warpTime;
    }

    public void setWarpTime(BigDecimal warpTime) {
        this.warpTime = warpTime;
    }

    public BigDecimal getMinutesWarp() {
        return minutesWarp;
    }

    public void setMinutesWarp(BigDecimal minutesWarp) {
        this.minutesWarp = minutesWarp;
    }

    public Integer getWeft() {
        return weft;
    }

    public void setWeft(Integer weft) {
        this.weft = weft;
    }

    public String getWeftLNo() {
        return weftLNo;
    }

    public void setWeftLNo(String weftLNo) {
        this.weftLNo = weftLNo;
    }

    public String getWeftSupplier() {
        return weftSupplier;
    }

    public void setWeftSupplier(String weftSupplier) {
        this.weftSupplier = weftSupplier;
    }

    public BigDecimal getWeftTime() {
        return weftTime;
    }

    public void setWeftTime(BigDecimal weftTime) {
        this.weftTime = weftTime;
    }

    public BigDecimal getMinutesWeft() {
        return minutesWeft;
    }

    public void setMinutesWeft(BigDecimal minutesWeft) {
        this.minutesWeft = minutesWeft;
    }

    public Integer getOthers() {
        return others;
    }

    public void setOthers(Integer others) {
        this.others = others;
    }

    public BigDecimal getOthersStopTime() {
        return othersStopTime;
    }

    public void setOthersStopTime(BigDecimal othersStopTime) {
        this.othersStopTime = othersStopTime;
    }

    public Integer getLongStops() {
        return longStops;
    }

    public void setLongStops(Integer longStops) {
        this.longStops = longStops;
    }

    public BigDecimal getLongStopsTime() {
        return longStopsTime;
    }

    public void setLongStopsTime(BigDecimal longStopsTime) {
        this.longStopsTime = longStopsTime;
    }

    public BigDecimal getRunTime() {
        return runTime;
    }

    public void setRunTime(BigDecimal runTime) {
        this.runTime = runTime;
    }

    public BigDecimal getTotalStopTime() {
        return totalStopTime;
    }

    public void setTotalStopTime(BigDecimal totalStopTime) {
        this.totalStopTime = totalStopTime;
    }

    public BigDecimal getRunningCfm() {
        return runningCfm;
    }

    public void setRunningCfm(BigDecimal runningCfm) {
        this.runningCfm = runningCfm;
    }

    public BigDecimal getIdealCfm() {
        return idealCfm;
    }

    public void setIdealCfm(BigDecimal idealCfm) {
        this.idealCfm = idealCfm;
    }

    public String getTcAndWeave() { return tcAndWeave; }
    public void setTcAndWeave(String tcAndWeave) { this.tcAndWeave = tcAndWeave; }

    public BigDecimal getWpCmpx() { return wpCmpx; }
    public void setWpCmpx(BigDecimal wpCmpx) { this.wpCmpx = wpCmpx; }

    public BigDecimal getWtCmpx() { return wtCmpx; }
    public void setWtCmpx(BigDecimal wtCmpx) { this.wtCmpx = wtCmpx; }

    public String getOtherStops() { return otherStops; }
    public void setOtherStops(String otherStops) { this.otherStops = otherStops; }

    public String getShortStops() { return shortStops; }
    public void setShortStops(String shortStops) { this.shortStops = shortStops; }

    public String getRunningHoursTime() { return runningHoursTime; }
    public void setRunningHoursTime(String runningHoursTime) { this.runningHoursTime = runningHoursTime; }

    public String getStopHoursTime() { return stopHoursTime; }
    public void setStopHoursTime(String stopHoursTime) { this.stopHoursTime = stopHoursTime; }

    public BigDecimal getIncentive() { return incentive; }
    public void setIncentive(BigDecimal incentive) { this.incentive = incentive; }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
