package com.tech3.erp.dto.menu;

import java.util.List;

public class UserMenuDataDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String userRole;
    private String userStatus;
    private String lastLogin;
    private List<MenuItemDTO> menus;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    public List<MenuItemDTO> getMenus() { return menus; }
    public void setMenus(List<MenuItemDTO> menus) { this.menus = menus; }
}
