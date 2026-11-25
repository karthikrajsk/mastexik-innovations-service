package com.tech3.erp.dto;

import com.tech3.erp.entity.SizingYarnInward;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SizingYarnInwardDTO {

    private Long id;
    private String inwardNo;
    private LocalDate inwardDate;
    private Long orderTypeId;
    private Long sizingJobWorkOrderId;
    private String sizingJobWorkOrderNo;
    private Long buyerId;
    private Long factoryCompanyId;
    private Long companyId;
    private Long tradingCompanyId;
    private Long sizingYarnIssueId;
    private List<SizingYarnInwardDetailDTO> yarnDetails = new ArrayList<>();

    public SizingYarnInwardDTO() {
    }

    public SizingYarnInwardDTO(SizingYarnInward inward) {
        this.id = inward.getId();
        this.inwardNo = inward.getInwardNo();
        this.inwardDate = inward.getInwardDate();
        this.orderTypeId = inward.getOrderTypeId();
        this.sizingJobWorkOrderId = inward.getSizingJobWorkOrderId();
        this.buyerId = inward.getBuyerId();
        this.factoryCompanyId = inward.getFactoryCompanyId();
        this.companyId = inward.getCompanyId();
        this.tradingCompanyId = inward.getTradingCompanyId();
        this.sizingYarnIssueId = inward.getSizingYarnIssueId();
        if (inward.getYarnDetails() != null) {
            this.yarnDetails = inward.getYarnDetails().stream()
                    .map(SizingYarnInwardDetailDTO::new)
                    .collect(Collectors.toList());
        }
    }

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

    public String getSizingJobWorkOrderNo() {
        return sizingJobWorkOrderNo;
    }

    public void setSizingJobWorkOrderNo(String sizingJobWorkOrderNo) {
        this.sizingJobWorkOrderNo = sizingJobWorkOrderNo;
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

    public List<SizingYarnInwardDetailDTO> getYarnDetails() {
        return yarnDetails;
    }

    public void setYarnDetails(List<SizingYarnInwardDetailDTO> yarnDetails) {
        this.yarnDetails = yarnDetails;
    }
}
