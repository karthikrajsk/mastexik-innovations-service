package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendances", schema = "masters")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate date;

    @Column(name = "attendance_log_time")
    private LocalDateTime time;

    @Column(name = "attendance_type")
    private String type; // check_in, check_out

    @Column(name = "status")
    private String status; // Present, Absent, Leave

    @Column(name = "check_in_time")
    private LocalDateTime checkIn;

    @Column(name = "check_out_time")
    private LocalDateTime checkOut;

    @Column(name = "ot_check_in_time")
    private LocalDateTime otCheckIn;

    @Column(name = "ot_check_out_time")
    private LocalDateTime otCheckOut;

    @Column(name = "working_hours")
    private String workingHours;

    @Column(name = "overtime_hours")
    private String overtimeHours;

    @Column(name = "working_days")
    private Double workingDays;

    @Column(name = "difference")
    private Double difference;

    // Default constructor
    public Attendance() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public LocalDateTime getOtCheckIn() {
        return otCheckIn;
    }

    public void setOtCheckIn(LocalDateTime otCheckIn) {
        this.otCheckIn = otCheckIn;
    }

    public LocalDateTime getOtCheckOut() {
        return otCheckOut;
    }

    public void setOtCheckOut(LocalDateTime otCheckOut) {
        this.otCheckOut = otCheckOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(String overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public Double getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Double workingDays) {
        this.workingDays = workingDays;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }
}
