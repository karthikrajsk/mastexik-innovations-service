package com.tech3.erp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tech3.erp.entity.FabricWeftDetail;

public class FabricWeftDetailDTO implements Serializable {
    private Long id;
    private Long yarnId;
    private Short color;
    private BigDecimal shrinkagePercent;
    private BigDecimal gramsPerMeter;
    private BigDecimal pattern;
    private BigDecimal wastePercentage;
    
    private Integer ppi;

    // Default constructor
    public FabricWeftDetailDTO() {}

    // Constructor using entity
    public FabricWeftDetailDTO(FabricWeftDetail entity) {
        this.id = entity.getId();
        this.yarnId = entity.getYarnId();
        this.color = entity.getColor();
        this.shrinkagePercent = entity.getShrinkagePercent();
        this.gramsPerMeter = entity.getGramsPerMeter();
        this.pattern = entity.getPattern();
        this.wastePercentage = entity.getWastePercentage();
        
        this.ppi = entity.getColor() != null ? entity.getColor().intValue() : null;

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

    public BigDecimal getWastePercentage() {
        return wastePercentage;
    }

    public void setWastePercentage(BigDecimal wastePercentage) {
        this.wastePercentage = wastePercentage;
    }
    
    public Integer getPpi() { return ppi; }
    public void setPpi(Integer ppi) { 
        this.ppi = ppi;
        // Also set color when ppi is set (for backward compatibility)
        if (ppi != null) {
            this.color = ppi.shortValue();
        }
    }
}

