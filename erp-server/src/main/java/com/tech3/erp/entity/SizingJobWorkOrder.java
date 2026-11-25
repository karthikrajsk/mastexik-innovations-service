package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sizing_job_work_order", schema = "masters")
public class SizingJobWorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sizing_work_order_no", unique = true)
    private String sizingWorkOrderNo;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "consignee_id")
    private Long consigneeId;

    @Column(name = "gst_type_id")
    private Integer gstTypeId;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "sizing_rate_per_kg")
    private Double sizingRatePerKg;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "is_approved")
    private Short isApproved = (short) 0;

    @Column(name = "is_complete")
    private Short isComplete = (short) 0;

    // @Column(name = "is_order_mapped")
    // private Short isOrderMapped = (short) 0;

    @Column(name = "is_order_id_mapped")
    private Short isOrderIdMapped = (short) 0;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "user_id")
    private Long userId;

    @Column(columnDefinition = "text")
    private String remarks;

    @OneToMany(mappedBy = "sizingJobWorkOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SizingJobWorkOrderItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizingWorkOrderNo() {
        return sizingWorkOrderNo;
    }

    public void setSizingWorkOrderNo(String sizingWorkOrderNo) {
        this.sizingWorkOrderNo = sizingWorkOrderNo;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Long consigneeId) {
        this.consigneeId = consigneeId;
    }

    public Integer getGstTypeId() {
        return gstTypeId;
    }

    public void setGstTypeId(Integer gstTypeId) {
        this.gstTypeId = gstTypeId;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public Double getSizingRatePerKg() {
        return sizingRatePerKg;
    }

    public void setSizingRatePerKg(Double sizingRatePerKg) {
        this.sizingRatePerKg = sizingRatePerKg;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

    public Short getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    // public Short getIsOrderMapped() {
    //     return isOrderMapped;
    // }

    // public void setIsOrderMapped(Short isOrderMapped) {
    //     this.isOrderMapped = isOrderMapped;
    // }

    public Short getIsOrderIdMapped() {
        return isOrderIdMapped;
    }

    public void setIsOrderIdMapped(Short isOrderIdMapped) {
        this.isOrderIdMapped = isOrderIdMapped;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<SizingJobWorkOrderItem> getItems() {
        return items;
    }

    public void setItems(List<SizingJobWorkOrderItem> items) {
        this.items = items;
    }
}
