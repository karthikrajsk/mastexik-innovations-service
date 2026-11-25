package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "beam_gating", schema = "masters")
public class BeamGating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gaiting_date")
    private LocalDate gaitingDate;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "gaiting_type")
    private String gaitingType;

    @Column(name = "no_of_beams_input")
    private Integer noOfBeamsInput;

    @Column(name = "no_of_fabric_output")
    private Double noOfFabricOutput;

    @Column(name = "fabric_panel")
    private Double fabricPanel;

    @Column(name = "is_beam_production")
    private Integer isBeamProduction = 0;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "beam_gating_no")
    private String beamGatingNo;

    @Column(name = "shed")
    private String shed;

    @Column(name = "shed_id")
    private Long shedId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String group;

    @Column(name = "effect_from")
    private String effectFrom;

    @Column(name = "effective_date")
    private java.time.LocalDateTime effectiveDate;

    @OneToMany(mappedBy = "beamGating", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BeamGatingDetails> beamGatingDetails;

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

    public List<BeamGatingDetails> getBeamGatingDetails() { return beamGatingDetails; }
    public void setBeamGatingDetails(List<BeamGatingDetails> beamGatingDetails) { this.beamGatingDetails = beamGatingDetails; }
}
