package com.tech3.erp.dto;

import java.util.Date;

import com.tech3.erp.entity.LoomMaster;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoomMasterDTO {
    private Long id;

    @NotNull
    private Date date;

    private String companyName;
    private Long companyId;

    @NotBlank
    private String loomNo;

    private String loomTypeId;
    private String machineTypeId;
    private String beamWidth;
    private String pickMultiplicationValue;
    private String machineSerialNo;
    private String manufactureYearModel;
    private String camBoxNo;
    private String camBoxModel;
    private Integer isBeamGating;
    private Long shedId;
    private Long groupId;
    private String remarks;
    private Boolean activeFlag;

    public LoomMasterDTO() {}

    public LoomMasterDTO(LoomMaster entity) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.companyName = entity.getCompanyName();
        this.companyId = entity.getCompanyId();
        this.loomNo = entity.getLoomNo();
        this.loomTypeId = entity.getLoomTypeId();
        this.machineTypeId = entity.getMachineTypeId();
        this.beamWidth = entity.getBeamWidth();
        this.pickMultiplicationValue = entity.getPickMultiplicationValue();
        this.machineSerialNo = entity.getMachineSerialNo();
        this.manufactureYearModel = entity.getManufactureYearModel();
        this.camBoxNo = entity.getCamBoxNo();
        this.camBoxModel = entity.getCamBoxModel();
        this.isBeamGating = entity.getIsBeamGating();
        this.shedId = entity.getShedId();
        this.groupId = entity.getGroupId();
        this.remarks = entity.getRemarks();
        this.activeFlag = entity.getActiveFlag();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getShedId() { return shedId; }
    public void setShedId(Long shedId) { this.shedId = shedId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getLoomNo() { return loomNo; }
    public void setLoomNo(String loomNo) { this.loomNo = loomNo; }

    public String getLoomTypeId() { return loomTypeId; }
    public void setLoomTypeId(String loomTypeId) { this.loomTypeId = loomTypeId; }

    public String getMachineTypeId() { return machineTypeId; }
    public void setMachineTypeId(String machineTypeId) { this.machineTypeId = machineTypeId; }

    public String getBeamWidth() { return beamWidth; }
    public void setBeamWidth(String beamWidth) { this.beamWidth = beamWidth; }

    public String getPickMultiplicationValue() { return pickMultiplicationValue; }
    public void setPickMultiplicationValue(String pickMultiplicationValue) { this.pickMultiplicationValue = pickMultiplicationValue; }

    public String getMachineSerialNo() { return machineSerialNo; }
    public void setMachineSerialNo(String machineSerialNo) { this.machineSerialNo = machineSerialNo; }

    public String getManufactureYearModel() { return manufactureYearModel; }
    public void setManufactureYearModel(String manufactureYearModel) { this.manufactureYearModel = manufactureYearModel; }

    public String getCamBoxNo() { return camBoxNo; }
    public void setCamBoxNo(String camBoxNo) { this.camBoxNo = camBoxNo; }

    public String getCamBoxModel() { return camBoxModel; }
    public void setCamBoxModel(String camBoxModel) { this.camBoxModel = camBoxModel; }

    public Integer getIsBeamGating() { return isBeamGating; }
    public void setIsBeamGating(Integer isBeamGating) { this.isBeamGating = isBeamGating; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}
