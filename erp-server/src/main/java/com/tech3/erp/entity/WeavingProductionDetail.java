package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "weaving_production_detail", schema = "masters")
public class WeavingProductionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weaving_production_id")
    private WeavingProduction weavingProduction;

    @Column(name = "shift")
    private String shift;

    @Column(name = "production_picks")
    private Double productionPicks;

    @Column(name = "production_meters")
    private Double productionMeters;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WeavingProduction getWeavingProduction() { return weavingProduction; }
    public void setWeavingProduction(WeavingProduction weavingProduction) { this.weavingProduction = weavingProduction; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public Double getProductionPicks() { return productionPicks; }
    public void setProductionPicks(Double productionPicks) { this.productionPicks = productionPicks; }

    public Double getProductionMeters() { return productionMeters; }
    public void setProductionMeters(Double productionMeters) { this.productionMeters = productionMeters; }
}
