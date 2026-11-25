package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "yarn_issue", schema = "masters")
public class YarnIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weaving_yarn_requirement_id")
    private WeavingYarnRequirement weavingYarnRequirement;

    @Column(name = "yarn_name", length = 100, nullable = false)
    private String yarnName;

    @Column(name = "actual_yarn")
    private String actualYarn;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "actual_yarn_id")
    private Long actualYarnId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "available_req_qty", precision = 38, scale = 2)
    private BigDecimal availableReqQty;

    @Column(name = "issue_qty", precision = 38, scale = 2)
    private BigDecimal issueQty;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag")
    private Double kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "lot_number", length = 100)
    private String lotNumber;

    @Column(name = "lot_available_bags", precision = 38, scale = 3)
    private BigDecimal lotAvailableBags;

    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "inward_type_id")
    private Long inwardTypeId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "beam_gaiting_id")
    private Long beamGaitingId;

    @Column(name = "issue_type", length = 50)
    private String issueType;

    @Column(name = "active_flag")
    private boolean activeFlag = true;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getLoomId() {
        return loomId;
    }

    public void setLoomId(Long loomId) {
        this.loomId = loomId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getBeamGaitingId() {
        return beamGaitingId;
    }

    public void setBeamGaitingId(Long beamGaitingId) {
        this.beamGaitingId = beamGaitingId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeavingYarnRequirement getWeavingYarnRequirement() {
        return weavingYarnRequirement;
    }

    public void setWeavingYarnRequirement(WeavingYarnRequirement weavingYarnRequirement) {
        this.weavingYarnRequirement = weavingYarnRequirement;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public String getActualYarn() {
        return actualYarn;
    }

    public void setActualYarn(String actualYarn) {
        this.actualYarn = actualYarn;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
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

    public BigDecimal getAvailableReqQty() {
        return availableReqQty;
    }

    public void setAvailableReqQty(BigDecimal availableReqQty) {
        this.availableReqQty = availableReqQty;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    public Double getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(Double kgsPerBag) {
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public BigDecimal getLotAvailableBags() {
        return lotAvailableBags;
    }

    public void setLotAvailableBags(BigDecimal lotAvailableBags) {
        this.lotAvailableBags = lotAvailableBags;
    }

    public Long getInwardId() {
        return inwardId;
    }

    public void setInwardId(Long inwardId) {
        this.inwardId = inwardId;
    }

    public Long getInwardTypeId() {
        return inwardTypeId;
    }

    public void setInwardTypeId(Long inwardTypeId) {
        this.inwardTypeId = inwardTypeId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}