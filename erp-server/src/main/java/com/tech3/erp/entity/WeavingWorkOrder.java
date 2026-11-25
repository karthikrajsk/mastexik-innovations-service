package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "weaving_work_order", schema = "masters")
public class WeavingWorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weaving_work_order_no", unique = true, nullable = true)
    private String weavingWorkOrderNo;

    @Column(name = "sales_order_no")
    private Long salesOrderNo;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId; // link to WeavingContract (FK by id)

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "consignee_id")
    private Long consigneeId;

    @Column(name = "gst_type_id")
    private Integer gstTypeId;

    @Column(name = "terms_conditions_id")
    private Long termsConditionsId;

    @Column(name = "payment_terms_id")
    private Long paymentTermsId;

    @Column(name = "packing_type_id")
    private Long packingTypeId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    @Column(name = "is_approved")
    private Short isApproved = (short) 0;

    @Column(name = "is_order_id_mapped")
    private Short isOrderIdMapped = (short) 0;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "weavingWorkOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WeavingWorkOrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "weavingWorkOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WeavingWorkOrderYarnRequirement> yarnRequirements = new ArrayList<>();

    @OneToMany(mappedBy = "weavingWorkOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WeavingWorkOrderDetail> orderDetails = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
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

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public Long getPaymentTermsId() {
        return paymentTermsId;
    }

    public void setPaymentTermsId(Long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }

    public Long getPackingTypeId() {
        return packingTypeId;
    }

    public void setPackingTypeId(Long packingTypeId) {
        this.packingTypeId = packingTypeId;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Short getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Short isApproved) {
        this.isApproved = isApproved;
    }

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

    public List<WeavingWorkOrderItem> getItems() {
        return items;
    }

    public String getWeavingWorkOrderNo() {
        return weavingWorkOrderNo;
    }

    public void setWeavingWorkOrderNo(String weavingWorkOrderNo) {
        this.weavingWorkOrderNo = weavingWorkOrderNo;
    }

    public void setItems(List<WeavingWorkOrderItem> items) {
        this.items = items;
    }

    public List<WeavingWorkOrderYarnRequirement> getYarnRequirements() {
        return yarnRequirements;
    }

    public void setYarnRequirements(List<WeavingWorkOrderYarnRequirement> yarnRequirements) {
        this.yarnRequirements = yarnRequirements;
    }

    public List<WeavingWorkOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<WeavingWorkOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(Long salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }
}