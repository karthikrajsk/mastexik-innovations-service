package com.tech3.erp.dto;

import com.tech3.erp.entity.ShiftDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.LocalDate;

public class ShiftDetailsDTO {
    private Long id;

    private String shiftName;

    private String shiftCode;

    private LocalTime shiftStartTime;

    private LocalTime shiftEndTime;

    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private String lunchBreak;
    private Double totalWorkingHours;

    private String weeklyOffDay;

    private boolean overtimeAllowed;
    private int lateEntryAllowedMinutes;
    private LocalDate shiftEffectiveFrom;
    private Integer noOfLeave;

    public ShiftDetailsDTO() {}

    public ShiftDetailsDTO(ShiftDetails entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.shiftName = entity.getShiftName();
            this.shiftCode = entity.getShiftCode();
            this.shiftStartTime = entity.getShiftStartTime();
            this.shiftEndTime = entity.getShiftEndTime();
            this.breakStartTime = entity.getBreakStartTime();
            this.breakEndTime = entity.getBreakEndTime();
            this.lunchBreak = entity.getLunchBreak();
            this.totalWorkingHours = entity.getTotalWorkingHours();
            this.weeklyOffDay = entity.getWeeklyOffDay();
            this.overtimeAllowed = entity.isOvertimeAllowed();
            this.lateEntryAllowedMinutes = entity.getLateEntryAllowedMinutes();
            this.shiftEffectiveFrom = entity.getShiftEffectiveFrom();
            this.noOfLeave = entity.getNoOfLeave();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public LocalTime getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(LocalTime shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    public LocalTime getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(LocalTime shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public LocalTime getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(LocalTime breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public LocalTime getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(LocalTime breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public Double getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(Double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public String getWeeklyOffDay() {
        return weeklyOffDay;
    }

    public void setWeeklyOffDay(String weeklyOffDay) {
        this.weeklyOffDay = weeklyOffDay;
    }

    public boolean isOvertimeAllowed() {
        return overtimeAllowed;
    }

    public void setOvertimeAllowed(boolean overtimeAllowed) {
        this.overtimeAllowed = overtimeAllowed;
    }

    public int getLateEntryAllowedMinutes() {
        return lateEntryAllowedMinutes;
    }

    public void setLateEntryAllowedMinutes(int lateEntryAllowedMinutes) {
        this.lateEntryAllowedMinutes = lateEntryAllowedMinutes;
    }

    public LocalDate getShiftEffectiveFrom() {
        return shiftEffectiveFrom;
    }

    public void setShiftEffectiveFrom(LocalDate shiftEffectiveFrom) {
        this.shiftEffectiveFrom = shiftEffectiveFrom;
    }

    public Integer getNoOfLeave() {
        return noOfLeave;
    }

    public void setNoOfLeave(Integer noOfLeave) {
        this.noOfLeave = noOfLeave;
    }

    public String getLunchBreak() {
        return lunchBreak;
    }

    public void setLunchBreak(String lunchBreak) {
        this.lunchBreak = lunchBreak;
    }
}
