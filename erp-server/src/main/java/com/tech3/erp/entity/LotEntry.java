package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "lot_entry", schema = "masters")
public class LotEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_number", length = 100)
    private String lotNumber;
    
    @Column(name = "po_type_id")
    private Long poTypeId;
    
    @Column(name = "yarn_id")
    private Long yarnId;
    
    @Column(name = "fabric_id")
    private Long fabricId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "warehouse_id")
    private Long warehouseId;
    
    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "no_of_bags")
    private Integer noOfBags = 0;

    @Column(name = "kgs_per_bag")
    private BigDecimal kgsPerBag = BigDecimal.ZERO;

    @Column(name = "rejected_quantity")
    private BigDecimal rejectedQuantity;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @ManyToOne
    @JoinColumn(name = "inward_item_id")
    @JsonBackReference
    private PurchaseInwardItem purchaseInwardItem;

    @PrePersist
    public void prePersist() {
        if (this.lotNumber == null || this.lotNumber.isEmpty()) {
            this.lotNumber = generateLotNumber();
        }
    }

    private String generateLotNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "LOT-" + datePart + "-" + randomNum;
    }

    @PreUpdate
    public void preUpdate() {
        if (this.noOfBags == null) this.noOfBags = 0;
        if (this.kgsPerBag == null) this.kgsPerBag = BigDecimal.ZERO;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
    
    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }
    
    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }
    
    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }
    
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(BigDecimal rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public PurchaseInwardItem getPurchaseInwardItem() {
        return purchaseInwardItem;
    }

    public void setPurchaseInwardItem(PurchaseInwardItem purchaseInwardItem) {
        this.purchaseInwardItem = purchaseInwardItem;
    }
}