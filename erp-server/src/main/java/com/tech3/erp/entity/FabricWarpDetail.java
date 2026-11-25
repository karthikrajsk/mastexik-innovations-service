package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.tech3.erp.dto.FabricWarpDetailDTO;

@Entity
@Table(name = "fabric_warp_detail", schema = "masters")
public class FabricWarpDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long yarnId;
    
    @Column(nullable = false)
    private Short color;
    
    @Column(precision = 10, scale = 4)
    private BigDecimal shrinkagePercent;
    @Column(precision = 10, scale = 4)
    private BigDecimal gramsPerMeter;
    @Column(precision = 10, scale = 4)
    private BigDecimal pattern;
    @Column(name = "warp_waste_percentage", precision = 10, scale = 4)
    private BigDecimal warpWastePercentage;

    @ManyToOne
    @JoinColumn(name = "fabric_id", nullable = false)
    private WovenFabricMaster wovenFabricMaster;

    public FabricWarpDetail() {}

    public FabricWarpDetail(FabricWarpDetailDTO dto) {
        this.yarnId = dto.getYarnId();
        
        // Handle both epi and color fields - FIXED VERSION
        if (dto.getEpi() != null) {
            this.color = dto.getEpi().shortValue();
        } else if (dto.getColor() != null) {
            this.color = dto.getColor();
        } else {
            this.color = (short) 0;
        }
        
        this.shrinkagePercent = dto.getShrinkagePercent();
        this.warpWastePercentage = dto.getWarpWastePercentage();
        this.gramsPerMeter = dto.getGramsPerMeter();
        this.pattern = dto.getPattern();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getYarnId() { return yarnId; }
    public void setYarnId(Long yarnId) { this.yarnId = yarnId; }

    public Short getColor() { return color; }
    public void setColor(Short color) { this.color = color; }

    public BigDecimal getShrinkagePercent() { return shrinkagePercent; }
    public void setShrinkagePercent(BigDecimal shrinkagePercent) { this.shrinkagePercent = shrinkagePercent; }

    public BigDecimal getWarpWastePercentage() { return warpWastePercentage; }
    public void setWarpWastePercentage(BigDecimal warpWastePercentage) { this.warpWastePercentage = warpWastePercentage; }

    public BigDecimal getGramsPerMeter() { return gramsPerMeter; }
    public void setGramsPerMeter(BigDecimal gramsPerMeter) { this.gramsPerMeter = gramsPerMeter; }

    public BigDecimal getPattern() { return pattern; }
    public void setPattern(BigDecimal pattern) { this.pattern = pattern; }

    public WovenFabricMaster getFabricMaster() { return wovenFabricMaster; }
    public void setFabricMaster(WovenFabricMaster wovenFabricMaster) { this.wovenFabricMaster = wovenFabricMaster; }
}