package com.tech3.erp.dto;

public class PurchaseReturnFabricSummaryDTO {
    private String fabricCode;
    private String quality;
    private Integer totalPieces;
    private Double totalMeters;
    private Double totalNetWeight;

    public PurchaseReturnFabricSummaryDTO() {}

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }

    public Integer getTotalPieces() { return totalPieces; }
    public void setTotalPieces(Integer totalPieces) { this.totalPieces = totalPieces; }

    public Double getTotalMeters() { return totalMeters; }
    public void setTotalMeters(Double totalMeters) { this.totalMeters = totalMeters; }

    public Double getTotalNetWeight() { return totalNetWeight; }
    public void setTotalNetWeight(Double totalNetWeight) { this.totalNetWeight = totalNetWeight; }
}