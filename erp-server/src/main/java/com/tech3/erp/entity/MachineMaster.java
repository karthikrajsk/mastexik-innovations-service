package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "machine_master", schema = "masters")
public class MachineMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machine_no", nullable = false, unique = true)
    private String machineNo;

    @Column(name = "machine_name")
    private String machineName;

    @Column(name = "department_id", nullable = false)
    private String departmentId;

    @Column(name = "machine_type_id", nullable = false)
    private String machineTypeId;

    @Column(name = "manufacture_year_model")
    private String manufactureYearModel;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

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
