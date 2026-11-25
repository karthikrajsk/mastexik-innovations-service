package com.tech3.erp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "jobwork_fabric_receive", schema = "masters")
public class JobworkFabricReceive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jobwork_fabric_receive_id", unique = true)
    private String jobworkFabricReceiveId;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "weaving_contract_no")
    private String weavingContractNo;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "fabric_code")
    private String fabricCode;

    @Column(name = "fabric_details")
    private String fabricDetails;

    @Column(name = "quality")
    private String quality;

    @Column(name = "job_fabric_receive_date", nullable = false)
    private LocalDate jobFabricReceiveDate = LocalDate.now();

    // ✅ New Columns
    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date", columnDefinition = "timestamp without time zone")
    private LocalDateTime dcDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "receive_no")
    private String receiveNo;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany(mappedBy = "jobworkFabricReceive", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<JobworkFabricReceiveItem> items = new ArrayList<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobworkFabricReceiveId() {
        return jobworkFabricReceiveId;
    }

    public void setJobworkFabricReceiveId(String jobworkFabricReceiveId) {
        this.jobworkFabricReceiveId = jobworkFabricReceiveId;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public String getWeavingContractNo() {
        return weavingContractNo;
    }

    public void setWeavingContractNo(String weavingContractNo) {
        this.weavingContractNo = weavingContractNo;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getFabricDetails() {
        return fabricDetails;
    }

    public void setFabricDetails(String fabricDetails) {
        this.fabricDetails = fabricDetails;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public LocalDate getJobFabricReceiveDate() {
        return jobFabricReceiveDate;
    }

    public void setJobFabricReceiveDate(LocalDate jobFabricReceiveDate) {
        this.jobFabricReceiveDate = jobFabricReceiveDate;
    }

    public String getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(String dcNumber) {
        this.dcNumber = dcNumber;
    }

    public LocalDateTime getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDateTime dcDate) {
        this.dcDate = dcDate;
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

    public List<JobworkFabricReceiveItem> getItems() {
        return items;
    }

    public void setItems(List<JobworkFabricReceiveItem> items) {
        this.items.clear();
        if (items != null) {
            for (JobworkFabricReceiveItem item : items) {
                addItem(item);
            }
        }
    }

    public void addItem(JobworkFabricReceiveItem item) {
        item.setJobworkFabricReceive(this);
        this.items.add(item);
    }

    public void removeItem(JobworkFabricReceiveItem item) {
        item.setJobworkFabricReceive(null);
        this.items.remove(item);
    }

    // @PrePersist
    // public void prePersist() {
    //     if (this.receiveNo == null || this.receiveNo.isEmpty()) {
    //         this.receiveNo = generateReceiveNo();
    //     }
    // }

    // private String generateReceiveNo() {
    //     String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    //     int randomNum = new Random().nextInt(9000) + 1000;
    //     return "JFR-" + datePart + "-" + randomNum;
    // }

    public String getReceiveNo() { return receiveNo; }
    public void setReceiveNo(String receiveNo) { this.receiveNo = receiveNo; }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
