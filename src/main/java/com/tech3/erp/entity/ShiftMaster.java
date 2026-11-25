package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shift_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shift_code", nullable = true, unique = true, length = 50)
    private String shiftCode;

    @Column(name = "shift_name", nullable = false, length = 150)
    private String shiftName;

    @Column(name = "shift_type", nullable = false, length = 20)
    @Builder.Default
    private String shiftType = "Fixed"; // "Fixed" or "Rotation"

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "active_flag", nullable = false)
    @Builder.Default
    private Boolean activeFlag = true;

    @Column(name = "total_working_hours", precision = 6, scale = 2)
    @Builder.Default
    private BigDecimal totalWorkingHours = BigDecimal.ZERO;

    @OneToMany(mappedBy = "shiftMaster", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Builder.Default
    private List<ShiftTimingDetail> timingDetails = new ArrayList<>();

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.activeFlag == null) this.activeFlag = true;
        recalculateTotalHours();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        recalculateTotalHours();
    }

    public void recalculateTotalHours() {
        if (timingDetails == null || timingDetails.isEmpty()) {
            if (this.totalWorkingHours == null) {
                this.totalWorkingHours = BigDecimal.ZERO;
            }
            return;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (ShiftTimingDetail detail : timingDetails) {
            BigDecimal hours = detail.calculateWorkingHours();
            sum = sum.add(hours != null ? hours : BigDecimal.ZERO);
        }
        this.totalWorkingHours = sum;
    }

    public void addTimingDetail(ShiftTimingDetail detail) {
        if (this.timingDetails == null) {
            this.timingDetails = new ArrayList<>();
        }
        detail.setShiftMaster(this);
        this.timingDetails.add(detail);
    }

    public void removeTimingDetail(ShiftTimingDetail detail) {
        if (this.timingDetails != null) {
            this.timingDetails.remove(detail);
            detail.setShiftMaster(null);
        }
    }
}
