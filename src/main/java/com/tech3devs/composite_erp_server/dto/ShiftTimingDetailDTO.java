package com.tech3devs.composite_erp_server.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftTimingDetailDTO {
    private Long id;
    private Long shiftMasterId;
    private Integer slotNumber;
    private String inTime; // "HH:mm"
    private String outTime; // "HH:mm"
    private Boolean isNextDay;
    private Integer breakDuration; // minutes
    private BigDecimal workingHours; // calculated decimal hours
}
