package com.tech3devs.composite_erp_server.entity;

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

    @Column(name = "shift_code", nullable = true, unique = true, length = 30)
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

    @OneToMany(mappedBy = "shiftMaster", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    public void addTimingDetail(ShiftTimingDetail detail) {
        if (this.timingDetails == null) {
            this.timingDetails = new ArrayList<>();
        }
        detail.setShiftMaster(this);
        this.timingDetails.add(detail);
        recalculateTotalHours();
    }

    public void recalculateTotalHours() {
        if (this.timingDetails == null || this.timingDetails.isEmpty()) {
            this.totalWorkingHours = BigDecimal.ZERO;
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (ShiftTimingDetail detail : this.timingDetails) {
            total = total.add(detail.calculateWorkingHours());
        }
        this.totalWorkingHours = total;
    }
}
