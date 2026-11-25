package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "shift_timing_details", schema = "masters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftTimingDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_master_id", nullable = false)
    @JsonBackReference
    private ShiftMaster shiftMaster;

    @Column(name = "slot_number", nullable = false)
    private Integer slotNumber;

    @Column(name = "shift_in_time", nullable = false, length = 10)
    private String shiftInTime; // e.g. "08:00"

    @Column(name = "shift_out_time", nullable = false, length = 10)
    private String shiftOutTime; // e.g. "16:30"

    @Column(name = "is_next_day", nullable = false)
    @Builder.Default
    private Boolean isNextDay = false;

    @Column(name = "break_duration", nullable = false)
    @Builder.Default
    private Integer breakDuration = 0; // minutes

    @Column(name = "break_start_time", length = 10)
    private String breakStartTime;

    @Column(name = "break_end_time", length = 10)
    private String breakEndTime;

    @Column(name = "total_break_hours", precision = 6, scale = 2)
    @Builder.Default
    private BigDecimal totalBreakHours = BigDecimal.ZERO;

    @Column(name = "working_hours", nullable = false, precision = 6, scale = 2)
    @Builder.Default
    private BigDecimal workingHours = BigDecimal.ZERO;

    /**
     * Calculates gross slot working hours. Break time is stored separately.
     */
    public BigDecimal calculateWorkingHours() {
        if (shiftInTime == null || shiftOutTime == null) {
            this.workingHours = BigDecimal.ZERO;
            return this.workingHours;
        }

        try {
            LocalTime in = LocalTime.parse(shiftInTime);
            LocalTime out = LocalTime.parse(shiftOutTime);

            int inMins = in.getHour() * 60 + in.getMinute();
            int outMins = out.getHour() * 60 + out.getMinute();

            if (Boolean.TRUE.equals(isNextDay) || outMins < inMins) {
                outMins += 24 * 60;
            }

            int grossMins = Math.max(0, outMins - inMins);
            calculateBreakMinutes();
            double hours = grossMins / 60.0;
            this.workingHours = BigDecimal.valueOf(hours).setScale(2, java.math.RoundingMode.HALF_UP);
        } catch (Exception e) {
            this.workingHours = BigDecimal.ZERO;
        }
        return this.workingHours;
    }

    public int calculateBreakMinutes() {
        int minutes = 0;
        if (breakStartTime != null && !breakStartTime.isBlank()
                && breakEndTime != null && !breakEndTime.isBlank()) {
            try {
                LocalTime start = LocalTime.parse(breakStartTime);
                LocalTime end = LocalTime.parse(breakEndTime);
                int startMins = start.toSecondOfDay() / 60;
                int endMins = end.toSecondOfDay() / 60;
                if (endMins < startMins) endMins += 24 * 60;
                minutes = Math.max(0, endMins - startMins);
            } catch (Exception ignored) {
                minutes = 0;
            }
        }
        if (minutes == 0) {
            minutes = breakDuration != null ? Math.max(0, breakDuration) : 0;
        }
        this.breakDuration = minutes;
        this.totalBreakHours = BigDecimal.valueOf(minutes / 60.0)
                .setScale(2, java.math.RoundingMode.HALF_UP);
        return minutes;
    }
}
