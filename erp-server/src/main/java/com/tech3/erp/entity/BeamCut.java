package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beam_cut", schema = "masters")
public class BeamCut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beam_cut_date")
    private LocalDate beamCutDate;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "beam_cut_no")
    private String beamCutNo;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy = "beamCut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeamCutBeamDetails> beamDetails = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getBeamCutDate() { return beamCutDate; }
    public void setBeamCutDate(LocalDate beamCutDate) { this.beamCutDate = beamCutDate; }

    public Long getLoomId() { return loomId; }
    public void setLoomId(Long loomId) { this.loomId = loomId; }

    public String getBeamCutNo() { return beamCutNo; }
    public void setBeamCutNo(String beamCutNo) { this.beamCutNo = beamCutNo; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public List<BeamCutBeamDetails> getBeamDetails() { return beamDetails; }
    public void setBeamDetails(List<BeamCutBeamDetails> beamDetails) { this.beamDetails = beamDetails; }
}
