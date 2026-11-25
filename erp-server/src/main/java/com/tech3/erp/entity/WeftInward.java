package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "weft_inward", schema = "masters")
public class WeftInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inward_date", nullable = false)
    private LocalDate inwardDate;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_job_work_order_id")
    private Long weavingJobWorkOrderId;

    @Column(name = "weaving_yarn_issue_id")
    private Long weavingYarnIssueId;

    @Column(name = "fabric_code")
    private String fabricCode;

    @Column(name = "dc_no")
    private String dcNo;

    @Column(name = "inward_no")
    private String inwardNo;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "source_company_id")
    private Long sourceCompanyId;

    @Column(name = "destination_company_id")
    private Long destinationCompanyId;

    @Column(name = "linked_beam_inward_id")
    private Long linkedBeamInwardId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @OneToMany(mappedBy = "weftInward", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<WeftInwardItem> yarnItems = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(LocalDate inwardDate) {
        this.inwardDate = inwardDate;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public Long getWeavingJobWorkOrderId() {
        return weavingJobWorkOrderId;
    }

    public void setWeavingJobWorkOrderId(Long weavingJobWorkOrderId) {
        this.weavingJobWorkOrderId = weavingJobWorkOrderId;
    }

    public Long getWeavingYarnIssueId() {
        return weavingYarnIssueId;
    }

    public void setWeavingYarnIssueId(Long weavingYarnIssueId) {
        this.weavingYarnIssueId = weavingYarnIssueId;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDate dcDate) {
        this.dcDate = dcDate;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSourceCompanyId() {
        return sourceCompanyId;
    }

    public void setSourceCompanyId(Long sourceCompanyId) {
        this.sourceCompanyId = sourceCompanyId;
    }

    public Long getDestinationCompanyId() {
        return destinationCompanyId;
    }

    public void setDestinationCompanyId(Long destinationCompanyId) {
        this.destinationCompanyId = destinationCompanyId;
    }

    public Long getLinkedBeamInwardId() {
        return linkedBeamInwardId;
    }

    public void setLinkedBeamInwardId(Long linkedBeamInwardId) {
        this.linkedBeamInwardId = linkedBeamInwardId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public List<WeftInwardItem> getYarnItems() {
        return yarnItems;
    }

    public void setYarnItems(List<WeftInwardItem> yarnItems) {
        this.yarnItems = yarnItems;
        for (WeftInwardItem item : yarnItems) {
            item.setWeftInward(this);
        }
    }
}
