package com.tech3.erp.dto;

public class BeamCutBeamDetailsDTO {

    private String beamNo;
    private Long flangeId;
    private Long beamId;
    private Long fabricMasterId;
    private Double remainingMeters;
    private Double usedMeters;
    private String width;
    private Long totalEnds;

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
