package com.tech3.erp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tech3.erp.entity.FabricWarpDetail;

public class FabricWarpDetailDTO implements Serializable {
    private Long id;
    private Long yarnId;
    private Short color;
    private BigDecimal shrinkagePercent;
    private BigDecimal gramsPerMeter;
    private BigDecimal pattern;
    private BigDecimal warpWastePercentage;
    
    private Integer epi;

    public FabricWarpDetailDTO() {}
    
 // Constructor using entity
    public FabricWarpDetailDTO(FabricWarpDetail entity) {
        this.id = entity.getId();
        this.yarnId = entity.getYarnId();
        this.color = entity.getColor();
        this.shrinkagePercent = entity.getShrinkagePercent();
        this.gramsPerMeter = entity.getGramsPerMeter();
        this.pattern = entity.getPattern();
        this.warpWastePercentage = entity.getWarpWastePercentage();
        
        this.epi = entity.getColor() != null ? entity.getColor().intValue() : null;

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Short getColor() {
        return color;
    }

    public void setColor(Short color) {
        this.color = color;
    }

    public BigDecimal getShrinkagePercent() {
        return shrinkagePercent;
    }

    public void setShrinkagePercent(BigDecimal shrinkagePercent) {
        this.shrinkagePercent = shrinkagePercent;
    }

    public BigDecimal getGramsPerMeter() {
        return gramsPerMeter;
    }

    public void setGramsPerMeter(BigDecimal gramsPerMeter) {
        this.gramsPerMeter = gramsPerMeter;
    }
    public BigDecimal getPattern() {
        return pattern;
    }

    public void setPattern(BigDecimal pattern) {
        this.pattern = pattern;
    }
    
    public BigDecimal getWarpWastePercentage() {
        return warpWastePercentage;
    }

    public void setWarpWastePercentage(BigDecimal warpWastePercentage) {
        this.warpWastePercentage = warpWastePercentage;
    }

    public Integer getEpi() { return epi; }
    public void setEpi(Integer epi) { 
        this.epi = epi;
        // Also set color when epi is set (for backward compatibility)
        if (epi != null) {
            this.color = epi.shortValue();
        }
    }
}
