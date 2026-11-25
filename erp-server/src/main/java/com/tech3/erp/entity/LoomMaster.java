package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loom_master", schema = "masters")
public class LoomMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loom_date")
    private Date date;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "loom_no", nullable = false)
    private String loomNo;

    @Column(name = "loom_type_id")
    private String loomTypeId;

    @Column(name = "machine_type_id")
    private String machineTypeId;

    @Column(name = "beam_width")
    private String beamWidth;

    @Column(name = "pick_multiplication_value")
    private String pickMultiplicationValue;

    @Column(name = "machine_serial_no")
    private String machineSerialNo;

    @Column(name = "manufacture_year_model")
    private String manufactureYearModel;

    @Column(name = "cam_box_no")
    private String camBoxNo;

    @Column(name = "cam_box_model")
    private String camBoxModel;

    @Column(name = "is_beam_gating")
    private Integer isBeamGating;

    @Column(name = "shed_id")
    private Long shedId;

    @Column(name = "group_id")
    private Long groupId;

    private String remarks;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

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
