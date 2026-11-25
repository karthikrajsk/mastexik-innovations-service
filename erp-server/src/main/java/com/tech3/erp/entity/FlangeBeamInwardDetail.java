package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "flange_beam_inward_detail", schema = "masters")
public class FlangeBeamInwardDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flange_no")
    private String flangeNo;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "is_issued_to_sizing_production", nullable = false, columnDefinition = "int default 0")
    private Integer isIssuedToSizingProduction = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flange_beam_inward_id")
    @JsonBackReference
    private FlangeBeamInward flangeBeamInward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public FlangeBeamInward getFlangeBeamInward() {
        return flangeBeamInward;
    }

    public void setFlangeBeamInward(FlangeBeamInward flangeBeamInward) {
        this.flangeBeamInward = flangeBeamInward;
    }

    public Integer getIsIssuedToSizingProduction() {
        return isIssuedToSizingProduction != null ? isIssuedToSizingProduction : 0;
    }

    public void setIsIssuedToSizingProduction(Integer isIssuedToSizingProduction) {
        this.isIssuedToSizingProduction = isIssuedToSizingProduction;
    }
}
