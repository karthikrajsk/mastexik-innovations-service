package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "finish_yarn_receive_items", schema = "masters")
public class FinishYarnReceiveItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "finished_yarn_code")
    private String finishedYarnCode;

    @Column(name = "finished_yarn")
    private String finishedYarn;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag")
    private Double kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "total_kgs")
    private Double totalKgs;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    // CORRECTED: This MUST reference FinishFabricReceive for combined approach
 @Column(name = "finished_yarn_item_id")
    private Long finishedYarnItemId;
    
    @ManyToOne
    @JoinColumn(name = "finish_fabric_receive_id")
    @JsonIgnore // Add this to break circular reference
    private FinishFabricReceive finishFabricReceive;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFinishedYarnCode() { return finishedYarnCode; }
    public void setFinishedYarnCode(String finishedYarnCode) { this.finishedYarnCode = finishedYarnCode; }

    public String getFinishedYarn() { return finishedYarn; }
    public void setFinishedYarn(String finishedYarn) { this.finishedYarn = finishedYarn; }
    
    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }
    
    public Double getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(Double kgsPerBag) { this.kgsPerBag = kgsPerBag; }
    
    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }
    
    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }
    
    public Double getTotalKgs() { return totalKgs; }
    public void setTotalKgs(Double totalKgs) { this.totalKgs = totalKgs; }
    
    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }
    
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
    
    // CORRECTED: This MUST reference FinishFabricReceive
    public FinishFabricReceive getFinishFabricReceive() { return finishFabricReceive; }
    public void setFinishFabricReceive(FinishFabricReceive finishFabricReceive) { 
        this.finishFabricReceive = finishFabricReceive; 
    }
       public Long getFinishedYarnItemId() { return finishedYarnItemId; }
    public void setFinishedYarnItemId(Long finishedYarnItemId) { this.finishedYarnItemId = finishedYarnItemId; }
}