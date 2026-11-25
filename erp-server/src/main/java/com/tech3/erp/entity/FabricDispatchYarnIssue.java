package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fabric_dispatch_yarn_issues", schema = "masters")
public class FabricDispatchYarnIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String yarnName;
    private Double availableReqQty;
    private Double issueQty;
    private Boolean activeFlag = true;
    private Long millId;
    private Long warehouseId;
    private Integer noOfBags;
    @Column(name = "kgs_per_bag", precision = 38, scale = 3)
    private BigDecimal kgsPerBag;
    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;
    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;
    private String lotNumber;
    private Integer availableBags;
    private Long yarnInwardStockDetailsId;
    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "inward_type_id")
    private Long inwardTypeId;
        private Long orderItemId;


    private Long greigeYarnId;
    private String greigeYarn;

    private String remark;
    private String vehicleNo;
    @Column(name = "gross_weight", precision = 38, scale = 3)
    private BigDecimal grossWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabric_dispatch_id")
    private FabricDispatchForDyeing fabricDispatch;

    private Long finishedYarnId;
private String finishedYarnName;

// Getters and Setters
public Long getFinishedYarnId() { return finishedYarnId; }
public void setFinishedYarnId(Long finishedYarnId) { this.finishedYarnId = finishedYarnId; }

public String getFinishedYarnName() { return finishedYarnName; }
public void setFinishedYarnName(String finishedYarnName) { this.finishedYarnName = finishedYarnName; }

    public FabricDispatchYarnIssue() {}

    public FabricDispatchYarnIssue(Long id, String yarnName, Double availableReqQty, 
                       Double issueQty, Boolean activeFlag) {
        this.id = id;
        this.yarnName = yarnName;
        this.availableReqQty = availableReqQty;
        this.issueQty = issueQty;
        this.activeFlag = activeFlag;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }
    
    public Double getAvailableReqQty() { return availableReqQty; }
    public void setAvailableReqQty(Double availableReqQty) { this.availableReqQty = availableReqQty; }
    
    public Double getIssueQty() { return issueQty; }
    public void setIssueQty(Double issueQty) { this.issueQty = issueQty; }
    
    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }

    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }

    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public Integer getAvailableBags() { return availableBags; }
    public void setAvailableBags(Integer availableBags) { this.availableBags = availableBags; }

    public Long getGreigeYarnId() { return greigeYarnId; }
    public void setGreigeYarnId(Long greigeYarnId) { this.greigeYarnId = greigeYarnId; }

    public String getGreigeYarn() { return greigeYarn; }
    public void setGreigeYarn(String greigeYarn) { this.greigeYarn = greigeYarn; }

    public Long getYarnInwardStockDetailsId() { return yarnInwardStockDetailsId; }
    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) { this.yarnInwardStockDetailsId = yarnInwardStockDetailsId; }

    public Long getInwardId() { return inwardId; }
    public void setInwardId(Long inwardId) { this.inwardId = inwardId; }

    public Long getInwardTypeId() { return inwardTypeId; }
    public void setInwardTypeId(Long inwardTypeId) { this.inwardTypeId = inwardTypeId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public BigDecimal getGrossWeight() { return grossWeight; }
    public void setGrossWeight(BigDecimal grossWeight) { this.grossWeight = grossWeight; }

    public FabricDispatchForDyeing getFabricDispatch() { return fabricDispatch; }
    public void setFabricDispatch(FabricDispatchForDyeing fabricDispatch) { this.fabricDispatch = fabricDispatch; }

       public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }
}