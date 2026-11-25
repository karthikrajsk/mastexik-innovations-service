package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "beam_cut_beam_details", schema = "masters")
public class BeamCutBeamDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beam_cut_id")
    @JsonBackReference
    private BeamCut beamCut;

    @Column(name = "beam_no")
    private String beamNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "beam_id")
    private Long beamId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "remaining_meters")
    private Double remainingMeters;

    @Column(name = "used_meters")
    private Double usedMeters;

    @Column(name = "width")
    private String width;

    @Column(name = "total_ends")
    private Long totalEnds;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BeamCut getBeamCut() { return beamCut; }
    public void setBeamCut(BeamCut beamCut) { this.beamCut = beamCut; }

    public String getBeamNo() { return beamNo; }
    public void setBeamNo(String beamNo) { this.beamNo = beamNo; }

    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }

    public Long getBeamId() { return beamId; }
    public void setBeamId(Long beamId) { this.beamId = beamId; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public Double getRemainingMeters() { return remainingMeters; }
    public void setRemainingMeters(Double remainingMeters) { this.remainingMeters = remainingMeters; }

    public Double getUsedMeters() { return usedMeters; }
    public void setUsedMeters(Double usedMeters) { this.usedMeters = usedMeters; }

    public String getWidth() { return width; }
    public void setWidth(String width) { this.width = width; }

    public Long getTotalEnds() { return totalEnds; }
    public void setTotalEnds(Long totalEnds) { this.totalEnds = totalEnds; }
}
