package com.tech3.erp.dto;

import java.util.List;

public class WeavingLiveProductionBulkUploadDTO {

    private Long companyId;
    private String fileName;
    private List<WeavingLiveProductionDTO> records;

    public WeavingLiveProductionBulkUploadDTO() {}

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public List<WeavingLiveProductionDTO> getRecords() { return records; }
    public void setRecords(List<WeavingLiveProductionDTO> records) { this.records = records; }
}
