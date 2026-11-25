package com.tech3.erp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "sizing_yarn_inward", schema = "masters")
public class SizingYarnInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inward_no", length = 100)
    private String inwardNo;

    @Column(name = "inward_date")
    private LocalDate inwardDate;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "sizing_job_work_order_id")
    private Long sizingJobWorkOrderId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "sizing_yarn_issue_id")
    private Long sizingYarnIssueId;

    @OneToMany(
            mappedBy = "sizingYarnInward",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 50)
    private List<SizingYarnInwardDetail> yarnDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public LocalDate getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(LocalDate inwardDate) {
        this.inwardDate = inwardDate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getSizingJobWorkOrderId() {
        return sizingJobWorkOrderId;
    }

    public void setSizingJobWorkOrderId(Long sizingJobWorkOrderId) {
        this.sizingJobWorkOrderId = sizingJobWorkOrderId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Long getSizingYarnIssueId() {
        return sizingYarnIssueId;
    }

    public void setSizingYarnIssueId(Long sizingYarnIssueId) {
        this.sizingYarnIssueId = sizingYarnIssueId;
    }

    public List<SizingYarnInwardDetail> getYarnDetails() {
        return yarnDetails;
    }

    public void setYarnDetails(List<SizingYarnInwardDetail> yarnDetails) {
        this.yarnDetails = yarnDetails;
    }

    public void addYarnDetail(SizingYarnInwardDetail detail) {
        yarnDetails.add(detail);
        detail.setSizingYarnInward(this);
    }

    public void removeYarnDetail(SizingYarnInwardDetail detail) {
        yarnDetails.remove(detail);
        detail.setSizingYarnInward(null);
    }
}
