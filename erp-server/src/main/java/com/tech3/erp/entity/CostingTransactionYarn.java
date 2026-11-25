package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "costing_transaction_yarn", schema = "masters")
public class CostingTransactionYarn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "costing_transaction_id", nullable = false,
                foreignKey = @ForeignKey(name = "costing_transaction_yarn_costing_fkey"))
    private CostingTransaction costingTransaction;

    @Column(name = "warp_weft", nullable = false)
    private String warpWeft;

    @Column(name = "yarn_count", nullable = false)
    private Integer yarnCount;

    @Column(name = "count", nullable = false)
    private Integer count = 1;

    @Column(name = "ply", nullable = false)
    private Integer ply = 1;

    @Column(name = "twist", precision = 10, scale = 2)
    private BigDecimal twist;

    @Column(name = "denier", precision = 10, scale = 2)
    private BigDecimal denier;

    @Column(name = "micron", precision = 10, scale = 2)
    private BigDecimal micron;

    @Column(name = "length", precision = 10, scale = 2)
    private BigDecimal length;

    @Column(name = "strength", precision = 10, scale = 2)
    private BigDecimal strength;

    @Column(name = "elongation", precision = 10, scale = 2)
    private BigDecimal elongation;

    @Column(name = "quantity", precision = 10, scale = 3, nullable = false)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate = BigDecimal.ZERO;

    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "weight", precision = 10, scale = 3, nullable = false)
    private BigDecimal weight = BigDecimal.ZERO;

    @Column(name = "rate_per_unit", precision = 10, scale = 2, nullable = false)
    private BigDecimal ratePerUnit;

    @Column(name = "yarn_processing_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal yarnProcessingCost;

    @Column(name = "content", length = 100)
    private String content;

    @Column(name = "yarn_type", length = 100)
    private String yarnType;

    @Column(name = "yarn_type_id", nullable = false)
    private Long yarnTypeId = 1L;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "batch_id", length = 100)
    private String batchId;

    @Column(name = "mill_id")
    private Long millId;

    @Column(name = "epi", precision = 10, scale = 2)
    private BigDecimal epi;

    @Column(name = "ppi", precision = 10, scale = 2)
    private BigDecimal ppi;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    // Constructors
    public CostingTransactionYarn() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CostingTransaction getCostingTransaction() { return costingTransaction; }
    public void setCostingTransaction(CostingTransaction costingTransaction) { this.costingTransaction = costingTransaction; }

    public String getWarpWeft() { return warpWeft; }
    public void setWarpWeft(String warpWeft) { this.warpWeft = warpWeft; }

    public Integer getYarnCount() { return yarnCount; }
    public void setYarnCount(Integer yarnCount) { this.yarnCount = yarnCount; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public Integer getPly() { return ply; }
    public void setPly(Integer ply) { this.ply = ply; }

    public BigDecimal getTwist() { return twist; }
    public void setTwist(BigDecimal twist) { this.twist = twist; }

    public BigDecimal getDenier() { return denier; }
    public void setDenier(BigDecimal denier) { this.denier = denier; }

    public BigDecimal getMicron() { return micron; }
    public void setMicron(BigDecimal micron) { this.micron = micron; }

    public BigDecimal getLength() { return length; }
    public void setLength(BigDecimal length) { this.length = length; }

    public BigDecimal getStrength() { return strength; }
    public void setStrength(BigDecimal strength) { this.strength = strength; }

    public BigDecimal getElongation() { return elongation; }
    public void setElongation(BigDecimal elongation) { this.elongation = elongation; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(BigDecimal ratePerUnit) { this.ratePerUnit = ratePerUnit; }

    public BigDecimal getYarnProcessingCost() { return yarnProcessingCost; }
    public void setYarnProcessingCost(BigDecimal yarnProcessingCost) { this.yarnProcessingCost = yarnProcessingCost; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getYarnType() { return yarnType; }
    public void setYarnType(String yarnType) { this.yarnType = yarnType; }

    public Long getYarnTypeId() { return yarnTypeId; }
    public void setYarnTypeId(Long yarnTypeId) { this.yarnTypeId = yarnTypeId; }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getBatchId() { return batchId; }
    public void setBatchId(String batchId) { this.batchId = batchId; }

    public Long getMillId() { return millId; }
    public void setMillId(Long millId) { this.millId = millId; }

    public BigDecimal getEpi() { return epi; }
    public void setEpi(BigDecimal epi) { this.epi = epi; }

    public BigDecimal getPpi() { return ppi; }
    public void setPpi(BigDecimal ppi) { this.ppi = ppi; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}