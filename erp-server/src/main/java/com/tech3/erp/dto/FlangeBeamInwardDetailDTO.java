package com.tech3.erp.dto;

public class FlangeBeamInwardDetailDTO {

    private Long id;
    private String flangeNo;
    private Long flangeId;
    private Integer isIssuedToSizingProduction;

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

    public Integer getIsIssuedToSizingProduction() {
        return isIssuedToSizingProduction;
    }

    public void setIsIssuedToSizingProduction(Integer isIssuedToSizingProduction) {
        this.isIssuedToSizingProduction = isIssuedToSizingProduction;
    }
}
