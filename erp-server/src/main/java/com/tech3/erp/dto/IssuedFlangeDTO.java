package com.tech3.erp.dto;

public class IssuedFlangeDTO {

    private Long id;
    private Long flangeId;
    private String flangeNo;

    public IssuedFlangeDTO() {
    }

    public IssuedFlangeDTO(Long id, Long flangeId, String flangeNo) {
        this.id = id;
        this.flangeId = flangeId;
        this.flangeNo = flangeNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }

    public String getFlangeNo() {
        return flangeNo;
    }

    public void setFlangeNo(String flangeNo) {
        this.flangeNo = flangeNo;
    }
}
