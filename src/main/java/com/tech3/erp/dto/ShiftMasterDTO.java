package com.tech3.erp.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftMasterDTO {
    private Long id;
    private String shiftCode;
    private String shiftName;
    private String shiftType; // "Fixed" or "Rotation"
    private String description;
    private Boolean activeFlag;
    private BigDecimal totalWorkingHours;
    private List<ShiftTimingDetailDTO> timingDetails;
    private Long companyId;
    private String createdAt;
    private String updatedAt;
}
