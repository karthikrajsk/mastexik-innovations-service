package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.LocalDate;

@Entity
@Table(name = "shift_details", schema = "masters")
public class ShiftDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shift_name", nullable = true)
    private String shiftName;

    @Column(name = "shift_code", nullable = true)
    private String shiftCode;

    @Column(name = "shift_start_time", nullable = true)
    private LocalTime shiftStartTime;

    @Column(name = "shift_end_time", nullable = true)
    private LocalTime shiftEndTime;

    @Column(name = "break_start_time", nullable = true)
    private LocalTime breakStartTime;

    @Column(name = "break_end_time", nullable = true)
    private LocalTime breakEndTime;

    @Column(name = "lunch_break", nullable = true)
    private String lunchBreak;

    @Column(name = "total_working_hours", nullable = true)
    private Double totalWorkingHours;

    @Column(name = "weekly_off_day", nullable = true)
    private String weeklyOffDay;

    @Column(name = "overtime_allowed", nullable = true)
    private boolean overtimeAllowed;

    @Column(name = "late_entry_allowed_minutes", nullable = true)
    private int lateEntryAllowedMinutes;

    @Column(name = "shift_effective_from", nullable = true)
    private LocalDate shiftEffectiveFrom;

    @Column(name = "no_of_leave", nullable = true)
    private Integer noOfLeave;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public ShiftDetails() {}

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getLunchBreak() {
        return lunchBreak;
    }

    public void setLunchBreak(String lunchBreak) {
        this.lunchBreak = lunchBreak;
    }
}
