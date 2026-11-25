package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.Attendance;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AttendanceDTO {

    private Long id;
    private Long employee_id; // Match user requested param name
    private String employee_code; // Added employee_code
    private String employee_name; // Match user requested param name
    private Integer type; // 1 for check_in, 0 for check_out
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime time; // full date-time
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
    private LocalDate date;
    
    private String status;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime checkIn;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime checkOut;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime otCheckIn;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime otCheckOut;
    private String workingHours;
    @JsonProperty("overtimeHours")
    @JsonAlias({"overtime_hours", "ot_hours", "otHours", "ot"})
    private String overtimeHours;
    private Double workingDays;
    private Double difference;

    @JsonIgnore
    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    @JsonProperty("checkIn")
    public String getCheckInDisplay() {
        if (this.checkIn == null) return "-";
        return this.checkIn.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }

    @JsonProperty("checkIn")
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    @JsonIgnore
    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    @JsonProperty("checkOut")
    public String getCheckOutDisplay() {
        if (this.checkOut == null) return "-";
        return this.checkOut.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }

    @JsonProperty("checkOut")
    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    @JsonIgnore
    public LocalDateTime getOtCheckIn() {
        return otCheckIn;
    }

    @JsonProperty("otCheckIn")
    public String getOtCheckInDisplay() {
        if (this.otCheckIn == null) return "-";
        return this.otCheckIn.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }

    @JsonProperty("otCheckIn")
    @JsonAlias({"ot_check_in", "otCheckin", "ot_checkin", "otCheckInTime", "ot_check_in_time"})
    public void setOtCheckIn(LocalDateTime otCheckIn) {
        this.otCheckIn = otCheckIn;
    }

    @JsonIgnore
    public LocalDateTime getOtCheckOut() {
        return otCheckOut;
    }

    @JsonProperty("otCheckOut")
    public String getOtCheckOutDisplay() {
        if (this.otCheckOut == null) return "-";
        return this.otCheckOut.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }

    @JsonProperty("otCheckOut")
    @JsonAlias({"ot_check_out", "otCheckout", "ot_checkout", "otCheckOutTime", "ot_check_out_time"})
    public void setOtCheckOut(LocalDateTime otCheckOut) {
        this.otCheckOut = otCheckOut;
    }

    public AttendanceDTO() {}

    public AttendanceDTO(Attendance entity) {
        if (entity == null) return;
        this.id = entity.getId();
        if (entity.getEmployee() != null) {
            this.employee_id = entity.getEmployee().getId();
        }
        this.employee_code = entity.getEmployeeCode();
        this.employee_name = entity.getEmployeeName();
        if ("check_in".equalsIgnoreCase(entity.getType())) {
            this.type = 1;
        } else if ("check_out".equalsIgnoreCase(entity.getType())) {
            this.type = 0;
        }
        this.time = entity.getTime() != null ? entity.getTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Kolkata")).toLocalDateTime() : null;
        this.date = entity.getDate();
        this.status = entity.getStatus();
        this.checkIn = entity.getCheckIn() != null ? entity.getCheckIn().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Kolkata")).toLocalDateTime() : null;
        this.checkOut = entity.getCheckOut() != null ? entity.getCheckOut().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Kolkata")).toLocalDateTime() : null;
        this.otCheckIn = entity.getOtCheckIn() != null ? entity.getOtCheckIn().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Kolkata")).toLocalDateTime() : null;
        this.otCheckOut = entity.getOtCheckOut() != null ? entity.getOtCheckOut().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Kolkata")).toLocalDateTime() : null;
        this.workingHours = entity.getWorkingHours();
        this.overtimeHours = entity.getOvertimeHours();
        this.workingDays = entity.getWorkingDays();
        this.difference = entity.getDifference();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @JsonProperty("overtime_hours")
    public String getOvertime_hours() {
        return overtimeHours;
    }

    @JsonProperty("overtime_hours")
    public void setOvertime_hours(String overtime_hours) {
        if (overtime_hours != null && !overtime_hours.isEmpty()) {
            this.overtimeHours = overtime_hours;
        }
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
