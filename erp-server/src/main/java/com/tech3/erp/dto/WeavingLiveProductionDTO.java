package com.tech3.erp.dto;

import com.tech3.erp.entity.WeavingLiveProduction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WeavingLiveProductionDTO {

    private Long id;
    private Long companyId;
    private Long loomId;
    private Long fabricMasterId;
    private String machineName;
    private String sortNo;
    private String construction;
    private BigDecimal actualEfficiency;
    private BigDecimal productionEfficiency;
    private BigDecimal kiloPicks;
    private BigDecimal meter;
    private Integer rpm;
    private String warpLNo;
    private Integer warp;
    private String warpSupplier;
    private BigDecimal warpTime;
    private BigDecimal minutesWarp;
    private Integer weft;
    private String weftLNo;
    private String weftSupplier;
    private BigDecimal weftTime;
    private BigDecimal minutesWeft;
    private Integer others;
    private BigDecimal othersStopTime;
    private Integer longStops;
    private BigDecimal longStopsTime;
    private BigDecimal runTime;
    private BigDecimal totalStopTime;
    private BigDecimal runningCfm;
    private BigDecimal idealCfm;
    private String tcAndWeave;
    private BigDecimal wpCmpx;
    private BigDecimal wtCmpx;
    private String otherStops;
    private String shortStops;
    private String runningHoursTime;
    private String stopHoursTime;
    private BigDecimal incentive;
    private String fileName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WeavingLiveProductionDTO() {}

    public WeavingLiveProductionDTO(WeavingLiveProduction entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.companyId = entity.getCompanyId();
            this.loomId = entity.getLoomId();
            this.fabricMasterId = entity.getFabricMasterId();
            this.machineName = entity.getMachineName();
            this.sortNo = entity.getSortNo();
            this.construction = entity.getConstruction();
            this.actualEfficiency = entity.getActualEfficiency();
            this.productionEfficiency = entity.getProductionEfficiency();
            this.kiloPicks = entity.getKiloPicks();
            this.meter = entity.getMeter();
            this.rpm = entity.getRpm();
            this.warpLNo = entity.getWarpLNo();
            this.warp = entity.getWarp();
            this.warpSupplier = entity.getWarpSupplier();
            this.warpTime = entity.getWarpTime();
            this.minutesWarp = entity.getMinutesWarp();
            this.weft = entity.getWeft();
            this.weftLNo = entity.getWeftLNo();
            this.weftSupplier = entity.getWeftSupplier();
            this.weftTime = entity.getWeftTime();
            this.minutesWeft = entity.getMinutesWeft();
            this.others = entity.getOthers();
            this.othersStopTime = entity.getOthersStopTime();
            this.longStops = entity.getLongStops();
            this.longStopsTime = entity.getLongStopsTime();
            this.runTime = entity.getRunTime();
            this.totalStopTime = entity.getTotalStopTime();
            this.runningCfm = entity.getRunningCfm();
            this.idealCfm = entity.getIdealCfm();
            this.tcAndWeave = entity.getTcAndWeave();
            this.wpCmpx = entity.getWpCmpx();
            this.wtCmpx = entity.getWtCmpx();
            this.otherStops = entity.getOtherStops();
            this.shortStops = entity.getShortStops();
            this.runningHoursTime = entity.getRunningHoursTime();
            this.stopHoursTime = entity.getStopHoursTime();
            this.incentive = entity.getIncentive();
            this.fileName = entity.getFileName();
            this.createdAt = entity.getCreatedAt();
            this.updatedAt = entity.getUpdatedAt();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getLoomId() { return loomId; }
    public void setLoomId(Long loomId) { this.loomId = loomId; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }

    public String getSortNo() { return sortNo; }
    public void setSortNo(String sortNo) { this.sortNo = sortNo; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public BigDecimal getActualEfficiency() { return actualEfficiency; }
    public void setActualEfficiency(BigDecimal actualEfficiency) { this.actualEfficiency = actualEfficiency; }

    public BigDecimal getProductionEfficiency() { return productionEfficiency; }
    public void setProductionEfficiency(BigDecimal productionEfficiency) { this.productionEfficiency = productionEfficiency; }

    public BigDecimal getKiloPicks() { return kiloPicks; }
    public void setKiloPicks(BigDecimal kiloPicks) { this.kiloPicks = kiloPicks; }

    public BigDecimal getMeter() { return meter; }
    public void setMeter(BigDecimal meter) { this.meter = meter; }

    public Integer getRpm() { return rpm; }
    public void setRpm(Integer rpm) { this.rpm = rpm; }

    public String getWarpLNo() { return warpLNo; }
    public void setWarpLNo(String warpLNo) { this.warpLNo = warpLNo; }

    public Integer getWarp() { return warp; }
    public void setWarp(Integer warp) { this.warp = warp; }

    public String getWarpSupplier() { return warpSupplier; }
    public void setWarpSupplier(String warpSupplier) { this.warpSupplier = warpSupplier; }

    public BigDecimal getWarpTime() { return warpTime; }
    public void setWarpTime(BigDecimal warpTime) { this.warpTime = warpTime; }

    public BigDecimal getMinutesWarp() { return minutesWarp; }
    public void setMinutesWarp(BigDecimal minutesWarp) { this.minutesWarp = minutesWarp; }

    public Integer getWeft() { return weft; }
    public void setWeft(Integer weft) { this.weft = weft; }

    public String getWeftLNo() { return weftLNo; }
    public void setWeftLNo(String weftLNo) { this.weftLNo = weftLNo; }

    public String getWeftSupplier() { return weftSupplier; }
    public void setWeftSupplier(String weftSupplier) { this.weftSupplier = weftSupplier; }

    public BigDecimal getWeftTime() { return weftTime; }
    public void setWeftTime(BigDecimal weftTime) { this.weftTime = weftTime; }

    public BigDecimal getMinutesWeft() { return minutesWeft; }
    public void setMinutesWeft(BigDecimal minutesWeft) { this.minutesWeft = minutesWeft; }

    public Integer getOthers() { return others; }
    public void setOthers(Integer others) { this.others = others; }

    public BigDecimal getOthersStopTime() { return othersStopTime; }
    public void setOthersStopTime(BigDecimal othersStopTime) { this.othersStopTime = othersStopTime; }

    public Integer getLongStops() { return longStops; }
    public void setLongStops(Integer longStops) { this.longStops = longStops; }

    public BigDecimal getLongStopsTime() { return longStopsTime; }
    public void setLongStopsTime(BigDecimal longStopsTime) { this.longStopsTime = longStopsTime; }

    public BigDecimal getRunTime() { return runTime; }
    public void setRunTime(BigDecimal runTime) { this.runTime = runTime; }

    public BigDecimal getTotalStopTime() { return totalStopTime; }
    public void setTotalStopTime(BigDecimal totalStopTime) { this.totalStopTime = totalStopTime; }

    public BigDecimal getRunningCfm() { return runningCfm; }
    public void setRunningCfm(BigDecimal runningCfm) { this.runningCfm = runningCfm; }

    public BigDecimal getIdealCfm() { return idealCfm; }
    public void setIdealCfm(BigDecimal idealCfm) { this.idealCfm = idealCfm; }

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

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
