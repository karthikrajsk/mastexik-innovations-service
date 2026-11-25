package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "costing_transaction_fabric", schema = "masters")
public class CostingTransactionFabric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "costing_transaction_id", nullable = false,
                foreignKey = @ForeignKey(name = "costing_transaction_fabric_costing_fkey"))
    private CostingTransaction costingTransaction;

    @ManyToOne
    @JoinColumn(name = "fabric_type_id", nullable = false,
                foreignKey = @ForeignKey(name = "costing_transaction_fabric_fabric_type_fkey"))
    private FabricType fabricType;

    @Column(name = "construction", length = 100)
    private String construction;

    @Column(name = "gsm", precision = 8, scale = 2)
    private BigDecimal gsm;

    @Column(name = "width", precision = 8, scale = 2)
    private BigDecimal width;

    @Column(name = "quantity", precision = 10, scale = 3, nullable = false)
    private BigDecimal quantity;

    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "weight", precision = 10, scale = 3)
    private BigDecimal weight;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    // Constructors
    public CostingTransactionFabric() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CostingTransaction getCostingTransaction() { return costingTransaction; }
    public void setCostingTransaction(CostingTransaction costingTransaction) { this.costingTransaction = costingTransaction; }

    public FabricType getFabricType() { return fabricType; }
    public void setFabricType(FabricType fabricType) { this.fabricType = fabricType; }

    public String getConstruction() { return construction; }
    public void setConstruction(String construction) { this.construction = construction; }

    public BigDecimal getGsm() { return gsm; }
    public void setGsm(BigDecimal gsm) { this.gsm = gsm; }

    public BigDecimal getWidth() { return width; }
    public void setWidth(BigDecimal width) { this.width = width; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}