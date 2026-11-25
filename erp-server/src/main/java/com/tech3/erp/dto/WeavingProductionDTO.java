package com.tech3.erp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech3.erp.util.LenientLongDeserializer;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class WeavingProductionDTO {
    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long id;

    @NotNull
    private LocalDate weavingProductionDate;
    private String weavingProductionNo;

    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long loomId;
    private String loomNo;
    private String beamNo;

    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long beamId;
    private String flangeNo;

    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long flangeId;
    private String fabricCodeQuality;

    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long fabricMasterId;
    private Double totalPicks;
    private Double totalMeters;

    @NotNull
    @JsonDeserialize(using = LenientLongDeserializer.class)
    private Long companyId;

    private List<WeavingProductionDetailDTO> productionDetails;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getWeavingProductionDate() { return weavingProductionDate; }
    public void setWeavingProductionDate(LocalDate weavingProductionDate) { this.weavingProductionDate = weavingProductionDate; }

    public String getWeavingProductionNo() { return weavingProductionNo; }
    public void setWeavingProductionNo(String weavingProductionNo) { this.weavingProductionNo = weavingProductionNo; }

    public Long getLoomId() { return loomId; }
    public void setLoomId(Long loomId) { this.loomId = loomId; }

    public String getLoomNo() { return loomNo; }
    public void setLoomNo(String loomNo) { this.loomNo = loomNo; }

    public String getBeamNo() { return beamNo; }
    public void setBeamNo(String beamNo) { this.beamNo = beamNo; }

    public Long getBeamId() { return beamId; }
    public void setBeamId(Long beamId) { this.beamId = beamId; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }

    public String getFabricCodeQuality() { return fabricCodeQuality; }
    public void setFabricCodeQuality(String fabricCodeQuality) { this.fabricCodeQuality = fabricCodeQuality; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public Double getTotalPicks() { return totalPicks; }
    public void setTotalPicks(Double totalPicks) { this.totalPicks = totalPicks; }

    public Double getTotalMeters() { return totalMeters; }
    public void setTotalMeters(Double totalMeters) { this.totalMeters = totalMeters; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<WeavingProductionDetailDTO> getProductionDetails() { return productionDetails; }
    public void setProductionDetails(List<WeavingProductionDetailDTO> productionDetails) { this.productionDetails = productionDetails; }
}