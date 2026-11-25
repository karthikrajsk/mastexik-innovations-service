package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class BeamGatingDTO {
    private Long id;
    private LocalDate gaitingDate;
    private Long loomId;
    private String gaitingType;
    private Integer noOfBeamsInput;
    private Double noOfFabricOutput;
    private Double fabricPanel;
    private Integer isBeamProduction;
    private Long companyId;
    private String beamGatingNo;
    private String shed;
    private Long shedId;
    private Long groupId;
    private String group;
    private String effectFrom;
    private java.time.LocalDateTime effectiveDate;
    private List<BeamGatingDetailsDTO> beamGatingDetails;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getGaitingDate() { return gaitingDate; }
    public void setGaitingDate(LocalDate gaitingDate) { this.gaitingDate = gaitingDate; }
    public Long getLoomId() { return loomId; }
    public void setLoomId(Long loomId) { this.loomId = loomId; }
    public String getGaitingType() { return gaitingType; }
    public void setGaitingType(String gaitingType) { this.gaitingType = gaitingType; }
    public Integer getNoOfBeamsInput() { return noOfBeamsInput; }
    public void setNoOfBeamsInput(Integer noOfBeamsInput) { this.noOfBeamsInput = noOfBeamsInput; }
    public Double getNoOfFabricOutput() { return noOfFabricOutput; }
    public void setNoOfFabricOutput(Double noOfFabricOutput) { this.noOfFabricOutput = noOfFabricOutput; }
    public Double getFabricPanel() { return fabricPanel; }
    public void setFabricPanel(Double fabricPanel) { this.fabricPanel = fabricPanel; }
    public Integer getIsBeamProduction() { return isBeamProduction; }
    public void setIsBeamProduction(Integer isBeamProduction) { this.isBeamProduction = isBeamProduction; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getBeamGatingNo() { return beamGatingNo; }
    public void setBeamGatingNo(String beamGatingNo) { this.beamGatingNo = beamGatingNo; }
    public String getShed() { return shed; }
    public void setShed(String shed) { this.shed = shed; }
    public Long getShedId() { return shedId; }
    public void setShedId(Long shedId) { this.shedId = shedId; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
    public String getEffectFrom() { return effectFrom; }
    public void setEffectFrom(String effectFrom) { this.effectFrom = effectFrom; }
    public java.time.LocalDateTime getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(java.time.LocalDateTime effectiveDate) { this.effectiveDate = effectiveDate; }
    public List<BeamGatingDetailsDTO> getBeamGatingDetails() { return beamGatingDetails; }
    public void setBeamGatingDetails(List<BeamGatingDetailsDTO> beamGatingDetails) { this.beamGatingDetails = beamGatingDetails; }
}
