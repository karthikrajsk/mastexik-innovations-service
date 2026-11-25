package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.JobworkFabricReceive;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JobworkFabricReceiveDTO {

    private Long id;
    private Long weavingContractId;
    private String weavingContractNo;
    private Long vendorId;
    private String fabricCode;
    private String fabricDetails;
    private String quality;
    private LocalDate jobFabricReceiveDate;
    private String remarks;
    private Boolean activeFlag;

    // ✅ Newly added fields
    private String dcNumber;
    private LocalDate dcDate;
    private String receiveNo;

    private String jobworkFabricReceiveId;
    private Long companyId;

    @JsonProperty("jobworkFabricReceiveItemsDtl")
    private List<JobworkFabricReceiveItemDTO> jobworkFabricReceiveItemsDtl;

    public JobworkFabricReceiveDTO() {}

    public JobworkFabricReceiveDTO(JobworkFabricReceive jobworkFabricReceive) {
        this.id = jobworkFabricReceive.getId();
        this.weavingContractId = jobworkFabricReceive.getWeavingContractId();
        this.weavingContractNo = jobworkFabricReceive.getWeavingContractNo();
        this.vendorId = jobworkFabricReceive.getVendorId();
        this.fabricCode = jobworkFabricReceive.getFabricCode();
        this.fabricDetails = jobworkFabricReceive.getFabricDetails();
        this.quality = jobworkFabricReceive.getQuality();
        this.jobFabricReceiveDate = jobworkFabricReceive.getJobFabricReceiveDate();
        this.remarks = jobworkFabricReceive.getRemarks();
        this.activeFlag = jobworkFabricReceive.getActiveFlag();
        this.dcNumber = jobworkFabricReceive.getDcNumber();
        this.dcDate = jobworkFabricReceive.getDcDate() != null ? jobworkFabricReceive.getDcDate().toLocalDate() : null;
        this.receiveNo = jobworkFabricReceive.getReceiveNo();
        this.companyId = jobworkFabricReceive.getCompanyId();
        if (jobworkFabricReceive.getItems() != null) {
            this.jobworkFabricReceiveItemsDtl = jobworkFabricReceive.getItems().stream()
                .map(JobworkFabricReceiveItemDTO::new)
                .collect(Collectors.toList());
        }
    }

    // ✅ Getters and Setters for new fields
    public String getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(String dcNumber) {
        this.dcNumber = dcNumber;
    }

    public LocalDate getDcDate() {
        return dcDate;
    }

    public void setDcDate(LocalDate dcDate) {
        this.dcDate = dcDate;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    public String getJobworkFabricReceiveId() {
        return jobworkFabricReceiveId;
    }

    public void setJobworkFabricReceiveId(String jobworkFabricReceiveId) {
        this.jobworkFabricReceiveId = jobworkFabricReceiveId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // Existing getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<JobworkFabricReceiveItemDTO> getJobworkFabricReceiveItemsDtl() {
        return jobworkFabricReceiveItemsDtl;
    }

    public void setJobworkFabricReceiveItemsDtl(List<JobworkFabricReceiveItemDTO> jobworkFabricReceiveItemsDtl) {
        this.jobworkFabricReceiveItemsDtl = jobworkFabricReceiveItemsDtl;
    }

}
