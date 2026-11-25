package com.tech3.erp.dto;

public class PurchaseReturnYarnSummaryDTO {
    private String yarnId;
    private String yarnName;
    private Double totalQuantity;

    public PurchaseReturnYarnSummaryDTO() {}

    public String getYarnId() { return yarnId; }
    public void setYarnId(String yarnId) { this.yarnId = yarnId; }

    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }

    public Double getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Double totalQuantity) { this.totalQuantity = totalQuantity; }
}