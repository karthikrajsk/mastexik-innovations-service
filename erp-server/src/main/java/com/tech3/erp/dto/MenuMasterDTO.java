package com.tech3.erp.dto;

import com.tech3.erp.entity.MenuMaster;
import jakarta.validation.constraints.NotBlank;

public class MenuMasterDTO {
    private Long id;

    @NotBlank
    private String screenName;

    @NotBlank
    private String menuUrl;

    public MenuMasterDTO() {}

    public MenuMasterDTO(MenuMaster e) {
        this.id = e.getId();
        this.screenName = e.getScreenName();
        this.menuUrl = e.getMenuUrl();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getScreenName() { return screenName; }
    public void setScreenName(String screenName) { this.screenName = screenName; }

    public String getMenuUrl() { return menuUrl; }
    public void setMenuUrl(String menuUrl) { this.menuUrl = menuUrl; }
}