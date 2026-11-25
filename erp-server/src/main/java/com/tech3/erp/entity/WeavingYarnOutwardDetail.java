package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "weaving_yarn_outward_detail", schema = "masters")
public class WeavingYarnOutwardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weaving_yarn_outward_id")
    private WeavingYarnOutward weavingYarnOutward;

    @Column(name = "weft_inward_id")
    private Long weftInwardId;

    @Column(name = "weft_inward_no", length = 100)
    private String weftInwardNo;

    @Column(name = "weaving_yarn_issue_id")
    private Long weavingYarnIssueId;

    @Column(name = "yarn_id")
    private Long yarnId;    

    @Column(name = "yarn_name", length = 150)
    private String yarnName;

    @Column(name = "inward_bags", precision = 38, scale = 3)
    private BigDecimal inwardBags;

    @Column(name = "inward_quantity", precision = 38, scale = 3)
    private BigDecimal inwardQuantity;

    @Column(name = "kgs_per_bag", precision = 38, scale = 3)
    private BigDecimal kgsPerBag;

    @Column(name = "cone_weight", precision = 38, scale = 3)
    private BigDecimal coneWeight;

    @Column(name = "issuing_bags", precision = 38, scale = 3)
    private BigDecimal issuingBags;

    @Column(name = "issued_quantity", precision = 38, scale = 3)
    private BigDecimal issuedQuantity;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "mill_name", length = 150)
    private String millName;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeavingYarnOutward getWeavingYarnOutward() {
        return weavingYarnOutward;
    }

    public void setWeavingYarnOutward(WeavingYarnOutward weavingYarnOutward) {
        this.weavingYarnOutward = weavingYarnOutward;
    }

    public Long getWeftInwardId() {
        return weftInwardId;
    }

    public void setWeftInwardId(Long weftInwardId) {
        this.weftInwardId = weftInwardId;
    }

    public String getWeftInwardNo() {
        return weftInwardNo;
    }

    public void setWeftInwardNo(String weftInwardNo) {
        this.weftInwardNo = weftInwardNo;
    }

    public Long getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(Long weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public BigDecimal getInwardBags() {
        return inwardBags;
    }

    public void setInwardBags(BigDecimal inwardBags) {
        this.inwardBags = inwardBags;
    }

    public BigDecimal getInwardQuantity() {
        return inwardQuantity;
    }

    public void setInwardQuantity(BigDecimal inwardQuantity) {
        this.inwardQuantity = inwardQuantity;
    }

    public BigDecimal getKgsPerBag() {
        return kgsPerBag;
    }

    public void setKgsPerBag(BigDecimal kgsPerBag) {
        this.kgsPerBag = kgsPerBag;
    }

    public BigDecimal getConeWeight() {
        return coneWeight;
    }

    public void setConeWeight(BigDecimal coneWeight) {
        this.coneWeight = coneWeight;
    }

    public BigDecimal getIssuingBags() {
        return issuingBags;
    }

    public void setIssuingBags(BigDecimal issuingBags) {
        this.issuingBags = issuingBags;
    }

    public BigDecimal getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(BigDecimal issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public Long getMillId() {
        return millId;
    }

    public void setMillId(Long millId) {
        this.millId = millId;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
