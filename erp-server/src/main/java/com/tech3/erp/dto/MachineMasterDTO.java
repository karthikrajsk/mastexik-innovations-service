package com.tech3.erp.dto;

import com.tech3.erp.entity.MachineMaster;

import jakarta.validation.constraints.NotBlank;

public class MachineMasterDTO {
    private Long id;

    @NotBlank
    private String machineNo;

    private String machineName;

    @NotBlank
    private String departmentId;

    @NotBlank
    private String machineTypeId;

    private String manufactureYearModel;
    private String serialNo;

    private boolean activeFlag = true;

    public MachineMasterDTO() {}

    public MachineMasterDTO(MachineMaster entity) {
        this.id = entity.getId();
        this.machineNo = entity.getMachineNo();
        this.machineName = entity.getMachineName();
        this.departmentId = entity.getDepartmentId();
        this.machineTypeId = entity.getMachineTypeId();
        this.manufactureYearModel = entity.getManufactureYearModel();
        this.serialNo = entity.getSerialNo();
        this.activeFlag = entity.isActiveFlag();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(String machineTypeId) {
        this.machineTypeId = machineTypeId;
    }

    public String getManufactureYearModel() {
        return manufactureYearModel;
    }

    public void setManufactureYearModel(String manufactureYearModel) {
        this.manufactureYearModel = manufactureYearModel;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
