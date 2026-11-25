package com.tech3.erp.dto;

public class ProcessMasterDTO {
	
	private Long id;
    private String processName;
    private String description;
    private Boolean activeFlag = true;
    private String goodsType;

    // Getters
    public Long getId() {
        return id;
    }

    public String getProcessName() {
        return processName;
    }
    
    public String getDescription() {
        return description;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }
    
    public String getGoodsType() { // NEW GETTER
        return goodsType;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public void setGoodsType(String goodsType) { // NEW SETTER
        this.goodsType = goodsType;
    }

}
