package com.tech3.erp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weaving_production", schema = "masters")
public class WeavingProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weaving_production_date")
    private LocalDate weavingProductionDate;

    @Column(name = "weaving_production_no")
    private String weavingProductionNo;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "loom_no")
    private String loomNo;

    @Column(name = "beam_no")
    private String beamNo;

    @Column(name = "beam_id")
    private Long beamId;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "fabric_code_quality")
    private String fabricCodeQuality;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "total_picks")
    private Double totalPicks;

    @Column(name = "total_meters")
    private Double totalMeters;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @OneToMany(mappedBy = "weavingProduction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WeavingProductionDetail> productionDetails = new ArrayList<>();

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

    public List<WeavingProductionDetail> getProductionDetails() { return productionDetails; }
    public void setProductionDetails(List<WeavingProductionDetail> productionDetails) { this.productionDetails = productionDetails; }
}
