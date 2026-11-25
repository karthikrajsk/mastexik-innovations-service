package com.tech3.erp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech3.erp.util.LenientLongDeserializer;

public class WeavingProductionDetailDTO {
    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long id;
    private String shift;
    private Double productionPicks;
    private Double productionMeters;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public Double getProductionPicks() { return productionPicks; }
    public void setProductionPicks(Double productionPicks) { this.productionPicks = productionPicks; }

    public Double getProductionMeters() { return productionMeters; }
    public void setProductionMeters(Double productionMeters) { this.productionMeters = productionMeters; }
}