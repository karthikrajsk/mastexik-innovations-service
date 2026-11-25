package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "flange_beam_inward", schema = "masters")
public class FlangeBeamInward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flange_inward_date")
    private LocalDate flangeInwardDate;

    @Column(name = "flange_inward_no")
    private String flangeInwardNo;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "sizing_job_work_order_id")
    private Long sizingJobWorkOrderId;

    @Column(name = "no_of_flanges")
    private Integer noOfFlanges = 0;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany(mappedBy = "flangeBeamInward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FlangeBeamInwardDetail> flangeDetails = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.flangeInwardNo == null || this.flangeInwardNo.isEmpty()) {
            this.flangeInwardNo = generateFlangeInwardNo();
        }
    }

    private String generateFlangeInwardNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "FBI-" + datePart + "-" + randomNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFlangeInwardDate() {
        return flangeInwardDate;
    }

    public void setFlangeInwardDate(LocalDate flangeInwardDate) {
        this.flangeInwardDate = flangeInwardDate;
    }

    public String getFlangeInwardNo() {
        return flangeInwardNo;
    }

    public void setFlangeInwardNo(String flangeInwardNo) {
        this.flangeInwardNo = flangeInwardNo;
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

    public Long getSizingJobWorkOrderId() {
        return sizingJobWorkOrderId;
    }

    public void setSizingJobWorkOrderId(Long sizingJobWorkOrderId) {
        this.sizingJobWorkOrderId = sizingJobWorkOrderId;
    }

    public Integer getNoOfFlanges() {
        return noOfFlanges;
    }

    public void setNoOfFlanges(Integer noOfFlanges) {
        this.noOfFlanges = noOfFlanges;
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

    public List<FlangeBeamInwardDetail> getFlangeDetails() {
        return flangeDetails;
    }

    public void setFlangeDetails(List<FlangeBeamInwardDetail> flangeDetails) {
        this.flangeDetails = flangeDetails;
    }
}
