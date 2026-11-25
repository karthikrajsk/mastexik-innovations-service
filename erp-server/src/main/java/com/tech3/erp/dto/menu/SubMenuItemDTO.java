package com.tech3.erp.dto.menu;

public class SubMenuItemDTO {
    private String subMenuId;
    private String subMenuName;
    private String subMenuPath;
    private int subMenuOrder;
    private boolean isActive;
    private MenuActionPermissionsDTO actions;

    // Getters and Setters
    public String getSubMenuId() { return subMenuId; }
    public void setSubMenuId(String subMenuId) { this.subMenuId = subMenuId; }

    public String getSubMenuName() { return subMenuName; }
    public void setSubMenuName(String subMenuName) { this.subMenuName = subMenuName; }

    public String getSubMenuPath() { return subMenuPath; }
    public void setSubMenuPath(String subMenuPath) { this.subMenuPath = subMenuPath; }

    public int getSubMenuOrder() { return subMenuOrder; }
    public void setSubMenuOrder(int subMenuOrder) { this.subMenuOrder = subMenuOrder; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public MenuActionPermissionsDTO getActions() { return actions; }
    public void setActions(MenuActionPermissionsDTO actions) { this.actions = actions; }
}
