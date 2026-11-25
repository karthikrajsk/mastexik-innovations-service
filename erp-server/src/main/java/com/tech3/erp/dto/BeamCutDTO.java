package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BeamCutDTO {
    private Long id;
    private LocalDate beamCutDate;
    private Long loomId;
    private String beamCutNo;
    private Long companyId;
    private String remarks;
    private List<BeamCutBeamDetailsDTO> beamDetails = new ArrayList<>();

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

    public List<BeamCutBeamDetailsDTO> getBeamDetails() { return beamDetails; }
    public void setBeamDetails(List<BeamCutBeamDetailsDTO> beamDetails) { this.beamDetails = beamDetails; }
}
