package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.tech3.erp.dto.FabricWeftDetailDTO;

@Entity
@Table(name = "fabric_weft_detail", schema = "masters")
public class FabricWeftDetail {

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
    @Column(name = "waste_percentage", precision = 10, scale = 4)
    private BigDecimal wastePercentage;

    @ManyToOne
    @JoinColumn(name = "fabric_id", nullable = false)
    private WovenFabricMaster wovenFabricMaster;

    public FabricWeftDetail() {}
    
    public FabricWeftDetail(FabricWeftDetailDTO dto) {
        this.yarnId = dto.getYarnId();
        
        // Handle both ppi and color fields - ADD THIS LOGIC
        if (dto.getPpi() != null) {
            this.color = dto.getPpi().shortValue();
        } else if (dto.getColor() != null) {
            this.color = dto.getColor();
        } else {
            this.color = (short) 0;
        }
        
        this.shrinkagePercent = dto.getShrinkagePercent();
        this.gramsPerMeter = dto.getGramsPerMeter();
        this.pattern = dto.getPattern();
        this.wastePercentage = dto.getWastePercentage();
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

    public BigDecimal getGramsPerMeter() { return gramsPerMeter; }
    public void setGramsPerMeter(BigDecimal gramsPerMeter) { this.gramsPerMeter = gramsPerMeter; }

    public BigDecimal getPattern() { return pattern; }
    public void setPattern(BigDecimal pattern) { this.pattern = pattern; }

    public BigDecimal getWastePercentage() { return wastePercentage; }
    public void setWastePercentage(BigDecimal wastePercentage) { this.wastePercentage = wastePercentage; }

    public WovenFabricMaster getFabricMaster() { return wovenFabricMaster; }
    public void setFabricMaster(WovenFabricMaster wovenFabricMaster) { this.wovenFabricMaster = wovenFabricMaster; }
}