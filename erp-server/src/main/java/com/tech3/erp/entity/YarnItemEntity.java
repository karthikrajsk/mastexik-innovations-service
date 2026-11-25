package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "yarn_item", schema = "masters")
public class YarnItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_balance_id", nullable = false)
    private Long openingBalanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_balance_id", insertable = false, updatable = false)
    private OpeningBalanceEntity openingBalance;

    @Column(name = "s_no", nullable = false)
    private Integer sNo;

    @Column(name = "yarn_id", nullable = false)
    private Long yarnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yarn_id", insertable = false, updatable = false)
    private YarnMaster yarnName;

    @Column(name = "mill_id", nullable = false)
    private Long millId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mill_id", insertable = false, updatable = false)
    private SubCategory millName;

    @Column(name = "no_of_bags", nullable = false)
    private Integer noOfBags;

    @Column(name = "kgs_per_bag", precision = 19, scale = 3, nullable = false)
    private BigDecimal kgsPerBag;

    @Column(name = "total_kg", precision = 19, scale = 3, nullable = false)
    private BigDecimal totalKg;

    @Column(name = "lot_number", length = 100, nullable = false)
    private String lotNumber;

    @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "yarn_type_id")
    private Long yarnTypeId;

    @Column(name = "cones_per_bag", precision = 38, scale = 3)
    private BigDecimal conesPerBag;

    @Column(name = "cones_weight", precision = 38, scale = 3)
    private BigDecimal conesWeight;


    // Getters & Setters
    public Long getId() { return id; }
    public Long getOpeningBalanceId() { return openingBalanceId; }
    public void setOpeningBalanceId(Long openingBalanceId) { this.openingBalanceId = openingBalanceId; }
    public OpeningBalanceEntity getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(OpeningBalanceEntity openingBalance) { this.openingBalance = openingBalance; }
    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }
    public Long getYarnId() { return yarnId; }
    public void setYarnId(Long yarnId) { this.yarnId = yarnId; }
    public YarnMaster getYarnName() { return yarnName; }
    public void setYarnName(YarnMaster yarnName) { this.yarnName = yarnName; }
    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }
    public SubCategory getMillName() { return millName; }
    public void setMillName(SubCategory millName) { this.millName = millName; }
    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }
    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }
    public BigDecimal getTotalKg() { return totalKg; }
    public void setTotalKg(BigDecimal totalKg) { this.totalKg = totalKg; }
    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }
    public Long getYarnTypeId() { return yarnTypeId; }
    public void setYarnTypeId(Long yarnTypeId) { this.yarnTypeId = yarnTypeId; }
    public BigDecimal getConesPerBag() { return conesPerBag; }
    public void setConesPerBag(BigDecimal conesPerBag) { this.conesPerBag = conesPerBag; }
    public BigDecimal getConesWeight() { return conesWeight; }
    public void setConesWeight(BigDecimal conesWeight) { this.conesWeight = conesWeight; }
}
