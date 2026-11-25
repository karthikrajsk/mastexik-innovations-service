package com.tech3.erp.dto.menu;

import java.util.List;

public class MenuItemDTO {
    private String menuId;
    private String menuName;
    private String menuIcon;
    private int menuOrder;
    private boolean isActive;
    private List<SubMenuItemDTO> subMenus;

    // Getters and Setters
    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuIcon() { return menuIcon; }
    public void setMenuIcon(String menuIcon) { this.menuIcon = menuIcon; }

    public int getMenuOrder() { return menuOrder; }
    public void setMenuOrder(int menuOrder) { this.menuOrder = menuOrder; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public List<SubMenuItemDTO> getSubMenus() { return subMenus; }
    public void setSubMenus(List<SubMenuItemDTO> subMenus) { this.subMenus = subMenus; }
}
