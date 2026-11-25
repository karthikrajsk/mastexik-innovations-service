package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "holidays", schema = "masters")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "holiday_name", nullable = false)
    private String holidayName;

    @Column(name = "holiday_date", nullable = false)
    private LocalDate holidayDate;

    @Column(name = "is_festival_wage_applicable", nullable = false)
    private Boolean isFestivalWageApplicable = true;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status = "Active";

    public Holiday() {}

    public Holiday(Long id, String holidayName, LocalDate holidayDate, Boolean isFestivalWageApplicable, String description, String status) {
        this.id = id;
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.isFestivalWageApplicable = isFestivalWageApplicable;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Boolean getIsFestivalWageApplicable() {
        return isFestivalWageApplicable;
    }

    public void setIsFestivalWageApplicable(Boolean festivalWageApplicable) {
        isFestivalWageApplicable = festivalWageApplicable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
