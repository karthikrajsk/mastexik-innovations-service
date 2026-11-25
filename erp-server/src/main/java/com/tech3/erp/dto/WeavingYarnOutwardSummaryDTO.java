package com.tech3.erp.dto;

import java.math.BigDecimal;

public class WeavingYarnOutwardSummaryDTO {

    private BigDecimal issuedBags;
    private BigDecimal issuedQuantity;

    public WeavingYarnOutwardSummaryDTO() {
    }

    public WeavingYarnOutwardSummaryDTO(BigDecimal issuedBags, BigDecimal issuedQuantity) {
        this.issuedBags = issuedBags;
        this.issuedQuantity = issuedQuantity;
    }

    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
    }

    public BigDecimal getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(BigDecimal issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }
}
