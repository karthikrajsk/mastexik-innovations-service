package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sizing_yarn_requirement", schema = "masters")
public class SizingYarnRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizing_yarn_issue_entry_id")
    private SizingYarnIssueEntry sizingYarnIssueEntry;

    @Column(name = "fabric_details", columnDefinition = "text")
    private String fabricDetails;

    @Column(name = "yarn_name", length = 100, nullable = false)
    private String yarnName;

    @Column(name = "actual_yarn", length = 100)
    private String actualYarn;

    @Column(name = "actual_yarn_id")
    private Long actualYarnId;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "warp_meters")
    private Integer warpMeters;

    @Column(name = "warp_grams_per_meter", precision = 38, scale = 3)
    private BigDecimal warpGramsPerMeter;

    @Column(name = "warp_requirement", precision = 38, scale = 3)
    private BigDecimal warpRequirement;

    @Column(name = "total_issued_qty", precision = 38, scale = 3)
    private BigDecimal totalIssuedQty;

    @Column(name = "balance_to_issue", precision = 38, scale = 3)
    private BigDecimal balanceToIssue;

    @Column(name = "active_flag")
    private boolean activeFlag = true;

    // Getters and Setters
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

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
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

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Integer getWarpMeters() {
        return warpMeters;
    }

    public void setWarpMeters(Integer warpMeters) {
        this.warpMeters = warpMeters;
    }

    public BigDecimal getWarpGramsPerMeter() {
        return warpGramsPerMeter;
    }

    public void setWarpGramsPerMeter(BigDecimal warpGramsPerMeter) {
        this.warpGramsPerMeter = warpGramsPerMeter;
    }

    public BigDecimal getWarpRequirement() {
        return warpRequirement;
    }

    public void setWarpRequirement(BigDecimal warpRequirement) {
        this.warpRequirement = warpRequirement;
    }

    public BigDecimal getTotalIssuedQty() {
        return totalIssuedQty;
    }

    public void setTotalIssuedQty(BigDecimal totalIssuedQty) {
        this.totalIssuedQty = totalIssuedQty;
    }

    public BigDecimal getBalanceToIssue() {
        return balanceToIssue;
    }

    public void setBalanceToIssue(BigDecimal balanceToIssue) {
        this.balanceToIssue = balanceToIssue;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}