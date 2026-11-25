package com.tech3.erp.dto;

import com.tech3.erp.entity.YarnReturnRequirement;

public class YarnReturnRequirementDTO {
    private Long id;
    private String yarnName;
    private Long actualYarnId;
    private String actualYarnName;
    private Long greigeYarnId;
    private String greigeYarnName;
    private Long finishedYarnId;
    private String finishedYarnName;
    private Double issuedQty;

    public YarnReturnRequirementDTO() {
    }

    public YarnReturnRequirementDTO(YarnReturnRequirement requirement) {
        this.id = requirement.getId();
        this.yarnName = requirement.getYarnName();
        this.actualYarnId = requirement.getActualYarnId();
        this.actualYarnName = requirement.getActualYarnName();
        this.greigeYarnId = requirement.getGreigeYarnId();
        this.greigeYarnName = requirement.getGreigeYarnName();
        this.finishedYarnId = requirement.getFinishedYarnId();
        this.finishedYarnName = requirement.getFinishedYarnName();
        this.issuedQty = requirement.getIssuedQty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public String getActualYarnName() {
        return actualYarnName;
    }

    public void setActualYarnName(String actualYarnName) {
        this.actualYarnName = actualYarnName;
    }

    public Long getGreigeYarnId() {
        return greigeYarnId;
    }

    public void setGreigeYarnId(Long greigeYarnId) {
        this.greigeYarnId = greigeYarnId;
    }

    public String getGreigeYarnName() {
        return greigeYarnName;
    }

    public void setGreigeYarnName(String greigeYarnName) {
        this.greigeYarnName = greigeYarnName;
    }

    public Long getFinishedYarnId() {
        return finishedYarnId;
    }

    public void setFinishedYarnId(Long finishedYarnId) {
        this.finishedYarnId = finishedYarnId;
    }

    public String getFinishedYarnName() {
        return finishedYarnName;
    }

    public void setFinishedYarnName(String finishedYarnName) {
        this.finishedYarnName = finishedYarnName;
    }

    public Double getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(Double issuedQty) {
        this.issuedQty = issuedQty;
    }
}
