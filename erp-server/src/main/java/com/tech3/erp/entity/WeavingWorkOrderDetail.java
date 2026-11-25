package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "weaving_work_order_detail", schema = "masters")
public class WeavingWorkOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weaving_work_order_id")
    @JsonBackReference
    private WeavingWorkOrder weavingWorkOrder;

    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "order_quantity")
    private BigDecimal orderQuantity;

    @Column(name = "planned_quantity")
    private BigDecimal plannedQuantity;

    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WeavingWorkOrder getWeavingWorkOrder() { return weavingWorkOrder; }
    public void setWeavingWorkOrder(WeavingWorkOrder weavingWorkOrder) { this.weavingWorkOrder = weavingWorkOrder; }

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    public BigDecimal getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(BigDecimal orderQuantity) { this.orderQuantity = orderQuantity; }

    public BigDecimal getPlannedQuantity() { return plannedQuantity; }
    public void setPlannedQuantity(BigDecimal plannedQuantity) { this.plannedQuantity = plannedQuantity; }

    public BigDecimal getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(BigDecimal totalQuantity) { this.totalQuantity = totalQuantity; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}