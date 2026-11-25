package com.tech3.erp.dto;

import java.time.LocalDateTime;

public class BeamCardDto {

    private Long id;
    private String beamNo;
    private String loomNo;
    private String circleNo;
    private String status;
    private Long beamCardTypeId;
    private Double rollWeight;
    private Double rollGlm;
    private Object data;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BeamCardDto() {}

    public BeamCardDto(Long id, String beamNo, String loomNo, String circleNo, String status, Object data, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, beamNo, loomNo, circleNo, status, null, null, null, data, createdAt, updatedAt);
    }

    public BeamCardDto(Long id, String beamNo, String loomNo, String circleNo, String status, Long beamCardTypeId, Double rollWeight, Double rollGlm, Object data, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.beamNo = beamNo;
        this.loomNo = loomNo;
        this.circleNo = circleNo;
        this.status = status;
        this.beamCardTypeId = beamCardTypeId;
        this.rollWeight = rollWeight;
        this.rollGlm = rollGlm;
        this.data = data;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
