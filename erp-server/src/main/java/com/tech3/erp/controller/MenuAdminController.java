package com.tech3.erp.controller;

import com.tech3.erp.entity.AppMenu;
import com.tech3.erp.entity.AppSubMenu;
import com.tech3.erp.entity.UserMenuPermission;
import com.tech3.erp.service.MenuService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/menus")
@Tag(name = "Menu Admin Controller", description = "APIs for managing menus and user permissions")
public class MenuAdminController {

    private final MenuService menuService;

    public MenuAdminController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @Operation(summary = "Get all menus", description = "Retrieve all menus with submenus")
    public ResponseEntity<Object> getAllMenus() {
        return ResponseUtil.success("Menus retrieved successfully", menuService.getAllMenus());
    }

    @PostMapping
    @Operation(summary = "Save menu", description = "Create or update a top-level menu")
    public ResponseEntity<Object> saveMenu(@RequestBody AppMenu menu) {
        return ResponseUtil.success("Menu saved successfully", menuService.saveMenu(menu));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete menu", description = "Delete a top-level menu and its submenus")
    public ResponseEntity<Object> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseUtil.success("Menu deleted successfully", null);
    }

    @PostMapping("/{menuId}/submenus")
    @Operation(summary = "Save submenu", description = "Create or update a submenu for a given menu")
    public ResponseEntity<Object> saveSubMenu(@PathVariable Long menuId, @RequestBody AppSubMenu subMenu) {
        return ResponseUtil.success("Submenu saved successfully", menuService.saveSubMenu(menuId, subMenu));
    }

    @DeleteMapping("/submenus/{id}")
    @Operation(summary = "Delete submenu", description = "Delete a submenu")
    public ResponseEntity<Object> deleteSubMenu(@PathVariable Long id) {
        menuService.deleteSubMenu(id);
        return ResponseUtil.success("Submenu deleted successfully", null);
    }

    @GetMapping("/permissions/{userId}")
    @Operation(summary = "Get user permissions", description = "Retrieve all permissions assigned to a specific user")
    public ResponseEntity<Object> getUserPermissions(@PathVariable Long userId) {
        return ResponseUtil.success("Permissions retrieved successfully", menuService.getUserPermissions(userId));
    }

    @PostMapping("/permissions/{userId}")
    @Operation(summary = "Save user permissions", description = "Assign or update permissions for a specific user")
    public ResponseEntity<Object> saveUserPermissions(@PathVariable Long userId, @RequestBody List<UserMenuPermission> permissions) {
        menuService.saveUserPermissions(userId, permissions);
        return ResponseUtil.success("Permissions saved successfully", null);
    }
}
