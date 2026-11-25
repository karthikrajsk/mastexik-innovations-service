package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "yarn_outward_stock_details", schema = "masters")
public class YarnOutwardStockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "outward_id")
    @JsonBackReference
    private YarnOutward yarnOutward;

    // Direct foreign key fields (for insert/update)
    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "yarn_inward_stock_details_id", nullable = true)
    private Long yarnInwardStockDetailsId;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "yarn_inward_item_id", insertable = false, updatable = false)
    // @JsonBackReference
    // private YarnInwardStockDetails yarnInwardStockDetails;
       @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yarn_inward_stock_details_id", insertable = false, updatable = false)
    @JsonBackReference
    private YarnInwardStockDetails yarnInwardStockDetails;
       
    public Long getYarnInwardStockDetailsId() {
        return yarnInwardStockDetailsId;
    }

    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) {
        this.yarnInwardStockDetailsId = yarnInwardStockDetailsId;
    }

    @Column(name = "outward_type_id")
    private Long outwardTypeId;

    @Column(name = "actual_item_id")
    private Long actualItemId;

 @Column(name = "uom_id", insertable = false, updatable = false)
    @Formula("(SELECT yisd.uom_id FROM masters.yarn_inward_stock_details yisd WHERE yisd.id = yarn_inward_stock_details_id)")
    private Long uomId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

 @Column(name = "lot_no", insertable = false, updatable = false)
    @Formula("(SELECT yisd.lot_no FROM masters.yarn_inward_stock_details yisd WHERE yisd.id = yarn_inward_stock_details_id)")
    private String lotNo;

    @Column(name = "issued_qty")
    private BigDecimal issuedQty;

    @Column(name = "issued_bags")
    private BigDecimal issuedBags;

    @Column(name = "no_of_cones", precision = 38, scale = 3)
    private BigDecimal noOfCones;

    @Column(name = "kgs_per_bag")
    private BigDecimal kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YarnOutward getYarnOutward() {
        return yarnOutward;
    }

    public void setYarnOutward(YarnOutward yarnOutward) {
        this.yarnOutward = yarnOutward;
    }

    public Long getInwardId() {
        return inwardId;
    }

    public void setInwardId(Long inwardId) {
        this.inwardId = inwardId;
    }

   public Long getOrderItemId() {
       return orderItemId;
   }

   public void setOrderItemId(Long orderItemId) {
       this.orderItemId = orderItemId;
   }

   public YarnInwardStockDetails getYarnInwardStockDetails() {
       return yarnInwardStockDetails;
   }

   public void setYarnInwardStockDetails(YarnInwardStockDetails yarnInwardStockDetails) {
       this.yarnInwardStockDetails = yarnInwardStockDetails;
   }

    public Long getOutwardTypeId() {
        return outwardTypeId;
    }

    public void setOutwardTypeId(Long outwardTypeId) {
        this.outwardTypeId = outwardTypeId;
    }

    public Long getActualItemId() {
        return actualItemId;
    }

    public void setActualItemId(Long actualItemId) {
        this.actualItemId = actualItemId;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public BigDecimal getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(BigDecimal issuedQty) {
        this.issuedQty = issuedQty;
    }

    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
    }

    public BigDecimal getNoOfCones() {
        return noOfCones;
    }

    public void setNoOfCones(BigDecimal noOfCones) {
        this.noOfCones = noOfCones;
    }

    public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public BigDecimal getConesPerBag() {
        return conesPerBag;
    }

    public void setConesPerBag(BigDecimal conesPerBag) {
        this.conesPerBag = conesPerBag;
    }

    public BigDecimal getConesWeight() {
        return conesWeight;
    }

    public void setConesWeight(BigDecimal conesWeight) {
        this.conesWeight = conesWeight;
    }
}