package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "beam_card", schema = "masters")
public class BeamCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beam_no", unique = true, nullable = false, length = 100)
    private String beamNo;

    @Column(name = "loom_no", length = 100)
    private String loomNo;

    @Column(name = "circle_no", length = 100)
    private String circleNo;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "beam_card_type_id")
    private Long beamCardTypeId;

    @Column(name = "roll_weight")
    private Double rollWeight;

    @Column(name = "roll_glm")
    private Double rollGlm;

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeamNo() {
        return beamNo;
    }

    public void setBeamNo(String beamNo) {
        this.beamNo = beamNo;
    }

    public String getLoomNo() {
        return loomNo;
    }

    public void setLoomNo(String loomNo) {
        this.loomNo = loomNo;
    }

    public String getCircleNo() {
        return circleNo;
    }

    public void setCircleNo(String circleNo) {
        this.circleNo = circleNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBeamCardTypeId() {
        return beamCardTypeId;
    }

    public void setBeamCardTypeId(Long beamCardTypeId) {
        this.beamCardTypeId = beamCardTypeId;
    }

    public Double getRollWeight() {
        return rollWeight;
    }

    public void setRollWeight(Double rollWeight) {
        this.rollWeight = rollWeight;
    }

    public Double getRollGlm() {
        return rollGlm;
    }

    public void setRollGlm(Double rollGlm) {
        this.rollGlm = rollGlm;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
