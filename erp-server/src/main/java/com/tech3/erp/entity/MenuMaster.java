package com.tech3.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "menu_masters", schema = "masters")
public class MenuMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen_name", nullable = false, length = 150)
    @NotBlank
    private String screenName;

    @Column(name = "menu_url", nullable = false, length = 300)
    @NotBlank
    private String menuUrl;

    public MenuMaster() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getScreenName() { return screenName; }
    public void setScreenName(String screenName) { this.screenName = screenName; }

    public String getMenuUrl() { return menuUrl; }
    public void setMenuUrl(String menuUrl) { this.menuUrl = menuUrl; }
}