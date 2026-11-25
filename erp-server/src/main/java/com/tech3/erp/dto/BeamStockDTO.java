package com.tech3.erp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BeamStockDTO {

    private Long id;
    private Long companyId;
    private LocalDate stockDate;
    private String beamInwardNo;
    private String sizedBeamNo;
    private Long flangeId;
    private String flangeNo;
    private Long weavingContractId;
    private String weavingContractNo;
    private Long weavingVendorId;
    private String weaverVendorName;
    private Long sizingVendorId;
    private String sizingVendorName;
    private Long salesOrderId;
    private String salesOrderNo;
    private Long sizingPlanId;
    private String sizingPlanNo;
    private Long fabricId;
    private String fabricCode;
    private String quality;
    private Double wrapMeters;
    private Double onLoomMtr;
    private Double shrinkage;
    private Double expectedFabricMeter;
    private Integer totalEnds;
    private Double fabricPanelWidth;
    private String warpCount;
    private String warpYarnMillName;
    private String warpYarnLotNo;
    private String sizingSetNo;
    private Double sizingPickupPercent;
    private String dcNumber;
    private LocalDate dcDate;
    private Double sizingRate;
    private Integer isBeamOutwarded;
    private Integer isBeamGated;
    private Integer isReturn;
    private String remarks;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Inner class for Bulk Upload Payload
    public static class BulkUploadRequest {
        private String fileName;
        private Long companyId;
        private List<BeamStockDTO> records;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }

        public List<BeamStockDTO> getRecords() {
            return records;
        }

        public void setRecords(List<BeamStockDTO> records) {
            this.records = records;
        }
    }

    public static class BulkUploadResponse {
        private int totalRows;
        private int successRows;
        private String message;

        public BulkUploadResponse(int totalRows, int successRows, String message) {
            this.totalRows = totalRows;
            this.successRows = successRows;
            this.message = message;
        }

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }

        public int getSuccessRows() {
            return successRows;
        }

        public void setSuccessRows(int successRows) {
            this.successRows = successRows;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class OnLoomMtrRow {
        private String sizedBeamNo;
        private String loomNo;
        private Double onLoomMtr;

        public String getSizedBeamNo() { return sizedBeamNo; }
        public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }
        public String getLoomNo() { return loomNo; }
        public void setLoomNo(String loomNo) { this.loomNo = loomNo; }
        public Double getOnLoomMtr() { return onLoomMtr; }
        public void setOnLoomMtr(Double onLoomMtr) { this.onLoomMtr = onLoomMtr; }
    }

    public static class OnLoomMtrUpdateRequest {
        private Long companyId;
        private List<OnLoomMtrRow> records;

        public Long getCompanyId() { return companyId; }
        public void setCompanyId(Long companyId) { this.companyId = companyId; }
        public List<OnLoomMtrRow> getRecords() { return records; }
        public void setRecords(List<OnLoomMtrRow> records) { this.records = records; }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDate getStockDate() {
        return stockDate;
    }

    public void setStockDate(LocalDate stockDate) {
        this.stockDate = stockDate;
    }

    public String getBeamInwardNo() {
        return beamInwardNo;
    }

    public void setBeamInwardNo(String beamInwardNo) {
        this.beamInwardNo = beamInwardNo;
    }

    public String getSizedBeamNo() {
        return sizedBeamNo;
    }

    public void setSizedBeamNo(String sizedBeamNo) {
        this.sizedBeamNo = sizedBeamNo;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
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

    public Long getWeavingVendorId() {
        return weavingVendorId;
    }

    public void setWeavingVendorId(Long weavingVendorId) {
        this.weavingVendorId = weavingVendorId;
    }

    public String getWeaverVendorName() {
        return weaverVendorName;
    }

    public void setWeaverVendorName(String weaverVendorName) {
        this.weaverVendorName = weaverVendorName;
    }

    public Long getSizingVendorId() {
        return sizingVendorId;
    }

    public void setSizingVendorId(Long sizingVendorId) {
        this.sizingVendorId = sizingVendorId;
    }

    public String getSizingVendorName() {
        return sizingVendorName;
    }

    public void setSizingVendorName(String sizingVendorName) {
        this.sizingVendorName = sizingVendorName;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public String getSizingPlanNo() {
        return sizingPlanNo;
    }

    public void setSizingPlanNo(String sizingPlanNo) {
        this.sizingPlanNo = sizingPlanNo;
    }

    public Long getFabricId() {
        return fabricId;
    }

    public void setFabricId(Long fabricId) {
        this.fabricId = fabricId;
    }

    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Double getWrapMeters() {
        return wrapMeters;
    }

    public void setWrapMeters(Double wrapMeters) {
        this.wrapMeters = wrapMeters;
    }

    public Double getOnLoomMtr() {
        return onLoomMtr;
    }

    public void setOnLoomMtr(Double onLoomMtr) {
        this.onLoomMtr = onLoomMtr;
    }

    public Double getShrinkage() {
        return shrinkage;
    }

    public void setShrinkage(Double shrinkage) {
        this.shrinkage = shrinkage;
    }

    public Double getExpectedFabricMeter() {
        return expectedFabricMeter;
    }

    public void setExpectedFabricMeter(Double expectedFabricMeter) {
        this.expectedFabricMeter = expectedFabricMeter;
    }

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }

    public Double getFabricPanelWidth() {
        return fabricPanelWidth;
    }

    public void setFabricPanelWidth(Double fabricPanelWidth) {
        this.fabricPanelWidth = fabricPanelWidth;
    }

    public String getWarpCount() {
        return warpCount;
    }

    public void setWarpCount(String warpCount) {
        this.warpCount = warpCount;
    }

    public String getWarpYarnMillName() {
        return warpYarnMillName;
    }

    public void setWarpYarnMillName(String warpYarnMillName) {
        this.warpYarnMillName = warpYarnMillName;
    }

    public String getWarpYarnLotNo() {
        return warpYarnLotNo;
    }

    public void setWarpYarnLotNo(String warpYarnLotNo) {
        this.warpYarnLotNo = warpYarnLotNo;
    }

    public String getSizingSetNo() {
        return sizingSetNo;
    }

    public void setSizingSetNo(String sizingSetNo) {
        this.sizingSetNo = sizingSetNo;
    }

    public Double getSizingPickupPercent() {
        return sizingPickupPercent;
    }

    public void setSizingPickupPercent(Double sizingPickupPercent) {
        this.sizingPickupPercent = sizingPickupPercent;
    }

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

    public Double getSizingRate() {
        return sizingRate;
    }

    public void setSizingRate(Double sizingRate) {
        this.sizingRate = sizingRate;
    }

    public Integer getIsBeamOutwarded() {
        return isBeamOutwarded;
    }

    public void setIsBeamOutwarded(Integer isBeamOutwarded) {
        this.isBeamOutwarded = isBeamOutwarded;
    }

    public Integer getIsBeamGated() {
        return isBeamGated;
    }

    public void setIsBeamGated(Integer isBeamGated) {
        this.isBeamGated = isBeamGated;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
