package com.tech3.erp.dto;

import com.tech3.erp.entity.EmployeePerformanceRecord;
import java.time.LocalDate;

public class EmployeePerformanceDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeCode;
    private String reviewPeriod;
    private Integer rating;
    private String performanceComments;
    private String reviewerName;
    private LocalDate reviewDate;

    public EmployeePerformanceDTO() {}

    public EmployeePerformanceDTO(EmployeePerformanceRecord record) {
        if (record == null) return;
        this.id = record.getId();
        if (record.getEmployee() != null) {
            this.employeeId = record.getEmployee().getId();
            this.employeeName = record.getEmployee().getEmployeeName();
            this.employeeCode = record.getEmployee().getEmployeeCode();
        }
        this.reviewPeriod = record.getReviewPeriod();
        this.rating = record.getRating();
        this.performanceComments = record.getPerformanceComments();
        this.reviewerName = record.getReviewerName();
        this.reviewDate = record.getReviewDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getReviewPeriod() {
        return reviewPeriod;
    }

    public void setReviewPeriod(String reviewPeriod) {
        this.reviewPeriod = reviewPeriod;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPerformanceComments() {
        return performanceComments;
    }

    public void setPerformanceComments(String performanceComments) {
        this.performanceComments = performanceComments;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
