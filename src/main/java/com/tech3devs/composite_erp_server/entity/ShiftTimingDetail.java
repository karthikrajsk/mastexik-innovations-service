package com.tech3devs.composite_erp_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

@Entity
@Table(name = "shift_timing_details")
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

            int grossMinutes = Math.max(0, outMins - inMins);

            this.workingHours = BigDecimal.valueOf(grossMinutes)
                    .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            this.workingHours = BigDecimal.ZERO;
        }

        return this.workingHours;
    }
}
