package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity   
@Table(name = "sizing_yarn_issue", schema = "masters")
public class SizingYarnIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizing_yarn_issue_entry_id")
    private SizingYarnIssueEntry sizingYarnIssueEntry;

    @Column(name = "yarn_name", length = 100, nullable = false)
    private String yarnName;

    
    @Column(name = "available_req_qty", precision = 38, scale = 2)
    private BigDecimal availableReqQty;

    @Column(name = "issue_qty", precision = 38, scale = 2)
    private BigDecimal issueQty;

    @Column(name = "active_flag")
    private boolean activeFlag = true;
    
    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "alternate_yarn", length = 100)
    private String alternateYarn;

    @Column(name = "available_qty", precision = 38, scale = 2)
    private BigDecimal availableQty;

    @Column(name = "no_of_bags")
    private Integer noOfBags;

    @Column(name = "kgs_per_bag")
    private BigDecimal kgsPerBag;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;

    @Column(name = "lot_no", length = 100)
    private String lotNo;

    // @Column(name = "lot_id")
    // private Long lotId;

    @Column(name = "issue_no", length = 100)
    private String issueNo;

    @Column(name = "inward_id")
    private Long inwardId;

    @Column(name = "inward_type_id")
    private Long inwardTypeId;

    @Column(name = "yarn_inward_stock_details_id")
    private Long yarnInwardStockDetailsId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "outward_type_id")
    private Long outwardTypeId;

    @Column(name = "actual_item_id")
    private Long actualItemId;

    @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "issued_bags", precision = 38, scale = 3)
    private BigDecimal issuedBags;

    @Column(name = "lot_available_qty", precision = 38, scale = 3)
    private BigDecimal lotAvailableQty;

    @Column(name = "lot_available_bags", precision = 38, scale = 3)
    private BigDecimal lotAvailableBags;

    @Column(name = "per_baby_cone_weight", precision = 38, scale = 3)
    private BigDecimal perBabyConeWeight;

    @Column(name = "no_of_cones", precision = 38, scale = 3)
    private BigDecimal noOfCones;

    @Column(name = "available_cones", precision = 38, scale = 3)
    private BigDecimal availableCones;

    @Column(name = "company_id")
    private Long companyId;

    // Getters and Setters
    public BigDecimal getNoOfCones() {
        return noOfCones;
    }

    public void setNoOfCones(BigDecimal noOfCones) {
        this.noOfCones = noOfCones;
    }

    public BigDecimal getAvailableCones() {
        return availableCones;
    }

    public void setAvailableCones(BigDecimal availableCones) {
        this.availableCones = availableCones;
    }
    public BigDecimal getPerBabyConeWeight() {
        return perBabyConeWeight;
    }

    public void setPerBabyConeWeight(BigDecimal perBabyConeWeight) {
        this.perBabyConeWeight = perBabyConeWeight;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SizingYarnIssueEntry getSizingYarnIssueEntry() {
        return sizingYarnIssueEntry;
    }

    public void setSizingYarnIssueEntry(SizingYarnIssueEntry sizingYarnIssueEntry) {
        this.sizingYarnIssueEntry = sizingYarnIssueEntry;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
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

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    

    
    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
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

    public String getAlternateYarn() {
        return alternateYarn;
    }

    public void setAlternateYarn(String alternateYarn) {
        this.alternateYarn = alternateYarn;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setActualQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
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

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    // public Long getLotId() {
    //     return lotId;
    // }

    // public void setLotId(Long lotId) {
    //     this.lotId = lotId;
    // }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
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

    public Long getYarnInwardStockDetailsId() {
        return yarnInwardStockDetailsId;
    }

    public void setYarnInwardStockDetailsId(Long yarnInwardStockDetailsId) {
        this.yarnInwardStockDetailsId = yarnInwardStockDetailsId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
    }

    public BigDecimal getLotAvailableQty() {
        return lotAvailableQty;
    }

    public void setLotAvailableQty(BigDecimal lotAvailableQty) {
        this.lotAvailableQty = lotAvailableQty;
    }

    public BigDecimal getLotAvailableBags() {
        return lotAvailableBags;
    }

    public void setLotAvailableBags(BigDecimal lotAvailableBags) {
        this.lotAvailableBags = lotAvailableBags;
    }

  public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }


}