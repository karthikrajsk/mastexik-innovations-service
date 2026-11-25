package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_yarn_return", schema = "masters")
public class SalesYarnReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_return_id")
    @JsonBackReference("salesReturnYarnItems")
    private SalesReturn salesReturn;

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "available_qty")
    private BigDecimal availableQty;

    @Column(name = "issued_qty")
    private BigDecimal issuedQty;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "no_of_bags")
    private BigDecimal noOfBags;

    @Column(name = "issued_bags")
    private BigDecimal issuedBags;

    @Column(name = "cones_per_bag")
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight")
    private BigDecimal conesWeight;

    @Column(name = "mill_id")
    private Long millId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SalesReturn getSalesReturn() { return salesReturn; }
    public void setSalesReturn(SalesReturn salesReturn) { this.salesReturn = salesReturn; }

    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }

    public BigDecimal getAvailableQty() { return availableQty; }
    public void setAvailableQty(BigDecimal availableQty) { this.availableQty = availableQty; }

    public BigDecimal getIssuedQty() { return issuedQty; }
    public void setIssuedQty(BigDecimal issuedQty) { this.issuedQty = issuedQty; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public BigDecimal getNoOfBags() { return noOfBags; }
    public void setNoOfBags(BigDecimal noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getIssuedBags() { return issuedBags; }
    public void setIssuedBags(BigDecimal issuedBags) { this.issuedBags = issuedBags; }

    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }

    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }
}
