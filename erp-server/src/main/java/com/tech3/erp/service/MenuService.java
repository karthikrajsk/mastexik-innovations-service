package com.tech3.erp.service;

import com.tech3.erp.dto.menu.*;
import com.tech3.erp.entity.*;
import com.tech3.erp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final AppMenuRepository appMenuRepository;
    private final AppSubMenuRepository appSubMenuRepository;
    private final UserMenuPermissionRepository userMenuPermissionRepository;
    private final ApplicationUserRepository userRepository;
    private final Level1MenuRepository level1MenuRepository;
    private final Level1MenuItemRepository level1MenuItemRepository;
    private final Level2MenuRepository level2MenuRepository;
    private final MenuMasterRepository menuMasterRepository;

    public MenuService(AppMenuRepository appMenuRepository,
                       AppSubMenuRepository appSubMenuRepository,
                       UserMenuPermissionRepository userMenuPermissionRepository,
                       ApplicationUserRepository userRepository,
                       Level1MenuRepository level1MenuRepository,
                       Level1MenuItemRepository level1MenuItemRepository,
                       Level2MenuRepository level2MenuRepository,
                       MenuMasterRepository menuMasterRepository) {
        this.appMenuRepository = appMenuRepository;
        this.appSubMenuRepository = appSubMenuRepository;
        this.userMenuPermissionRepository = userMenuPermissionRepository;
        this.userRepository = userRepository;
        this.level1MenuRepository = level1MenuRepository;
        this.level1MenuItemRepository = level1MenuItemRepository;
        this.level2MenuRepository = level2MenuRepository;
        this.menuMasterRepository = menuMasterRepository;
    }

    @Transactional(readOnly = true)
    public UserMenuDataDTO getUserMenuData(ApplicationUser user) {
        UserMenuDataDTO data = new UserMenuDataDTO();
        data.setUserId(user.getId().toString());
        data.setUserName(user.getUserName());
        data.setUserRole(user.getRole());
        data.setUserStatus(user.getActiveFlag() ? "Active" : "Inactive");
        data.setLastLogin(LocalDateTime.now().toString());

        List<MenuItemDTO> menuItemDTOs = new ArrayList<>();
        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());

        if (isAdmin) {
            List<AppMenu> allActiveMenus = appMenuRepository.findByActiveTrueOrderByOrderAsc();
            for (AppMenu menu : allActiveMenus) {
                menuItemDTOs.add(convertToMenuItemDTO(menu, true, null));
            }
        } else {
            // Try Level1/Level2 system first
            List<Level2Menu> level2Menus = level2MenuRepository.findByUserId(user.getId());

            if (!level2Menus.isEmpty()) {
                Map<Long, List<Level2MenuItem>> level2ItemsMap = level2Menus.stream()
                        .flatMap(l2 -> l2.getItems().stream())
                        .collect(Collectors.groupingBy(Level2MenuItem::getLevel1Id));

                List<AppMenu> allActiveMenus = appMenuRepository.findByActiveTrueOrderByOrderAsc();

                // Build name->sequence map from THIS user's own Level1Menu configuration
                Map<String, Integer> userLevel1SequenceByName = new java.util.HashMap<>();
                List<Level1Menu> userLevel1Menus = level1MenuRepository.findByUserId(user.getId());
                if (!userLevel1Menus.isEmpty()) {
                    userLevel1Menus.get(0).getItems().forEach(item -> {
                        if (item.getName() != null) {
                            userLevel1SequenceByName.put(item.getName().toLowerCase(), item.getSequence() != null ? item.getSequence() : 999);
                        }
                    });
                }

                // Get all referenced Level 1 item IDs from Level 2 items
                List<Long> referencedL1Ids = new ArrayList<>(level2ItemsMap.keySet());
                
                // Fetch these Level 1 items to get their names
                List<Level1MenuItem> l1Items = level1MenuItemRepository.findAllById(referencedL1Ids);
                
                // Sort Level 1 items using the current user's own sequences (by name match),
                // falling back to the item's stored sequence if not found in the user's map
                l1Items.sort((a, b) -> {
                    int seqA = userLevel1SequenceByName.getOrDefault(
                        a.getName() != null ? a.getName().toLowerCase() : "", 
                        a.getSequence() != null ? a.getSequence() : 999);
                    int seqB = userLevel1SequenceByName.getOrDefault(
                        b.getName() != null ? b.getName().toLowerCase() : "", 
                        b.getSequence() != null ? b.getSequence() : 999);
                    return Integer.compare(seqA, seqB);
                });

                for (Level1MenuItem l1Item : l1Items) {
                    List<Level2MenuItem> l2Items = level2ItemsMap.getOrDefault(l1Item.getId(), Collections.emptyList());
                    if (!l2Items.isEmpty()) {
                        MenuItemDTO menuDTO = new MenuItemDTO();
                        menuDTO.setMenuId(l1Item.getId().toString());
                        menuDTO.setMenuName(l1Item.getName());
                        
                        // Find matching icon
                        String icon = allActiveMenus.stream()
                                .filter(m -> m.getName().equalsIgnoreCase(l1Item.getName()))
                                .map(AppMenu::getIcon)
                                .findFirst()
                                .orElse("Circle");
                        menuDTO.setMenuIcon(icon);
                        // Use the user's own sequence for this menu name; fall back to stored sequence
                        int menuOrder = userLevel1SequenceByName.getOrDefault(
                            l1Item.getName() != null ? l1Item.getName().toLowerCase() : "",
                            l1Item.getSequence() != null ? l1Item.getSequence() : 0);
                        menuDTO.setMenuOrder(menuOrder);
                        menuDTO.setActive(true);

                        List<SubMenuItemDTO> subMenus = new ArrayList<>();
                        for (Level2MenuItem l2Item : l2Items) {
                            SubMenuItemDTO subDTO = new SubMenuItemDTO();
                            subDTO.setSubMenuId(l2Item.getId().toString());
                            
                            String screenName = "Unknown";
                            Optional<MenuMaster> mmOpt = menuMasterRepository.findById(l2Item.getMenuMasterId());
                            if (mmOpt.isPresent()) {
                                screenName = mmOpt.get().getScreenName();
                            }
                            
                            subDTO.setSubMenuName(screenName);
                            subDTO.setSubMenuPath(l2Item.getUrl());
                            subDTO.setSubMenuOrder(l2Item.getSequence() != null ? l2Item.getSequence() : 0);
                            subDTO.setActive(true);

                            MenuActionPermissionsDTO actions = new MenuActionPermissionsDTO();
                            actions.setAdd(l2Item.isCanAdd());
                            actions.setEdit(l2Item.isCanEdit());
                            actions.setDelete(l2Item.isCanDelete());
                            actions.setView(l2Item.isCanView());
                            actions.setExport(l2Item.isCanExport());
                            actions.setPrint(l2Item.isCanPrint());
                            actions.setApprove(l2Item.isCanApprove());
                            actions.setReject(l2Item.isCanApprove()); // Sync reject with approve for now
                            actions.setProcess(true);
                            actions.setShare(true);
                            actions.setDuplicate(true);
                            actions.setSchedule(true);
                            
                            subDTO.setActions(actions);
                            subMenus.add(subDTO);
                        }
                        subMenus.sort((a, b) -> Integer.compare(a.getSubMenuOrder(), b.getSubMenuOrder()));
                        menuDTO.setSubMenus(subMenus);
                        menuItemDTOs.add(menuDTO);
                    }
                }
                menuItemDTOs.sort((a, b) -> Integer.compare(a.getMenuOrder(), b.getMenuOrder()));
            }

            // Fallback to older system if no level1/level2 menus found
            if (menuItemDTOs.isEmpty()) {
                List<AppMenu> allActiveMenus = appMenuRepository.findByActiveTrueOrderByOrderAsc();
                List<UserMenuPermission> userPermissions = userMenuPermissionRepository.findByUserIdAndSubMenu_ActiveTrue(user.getId());
                Map<Long, UserMenuPermission> permissionMap = userPermissions.stream()
                        .collect(Collectors.toMap(p -> p.getSubMenu().getId(), p -> p));

                for (AppMenu menu : allActiveMenus) {
                    MenuItemDTO menuItemDTO = convertToMenuItemDTO(menu, false, permissionMap);
                    if (!menuItemDTO.getSubMenus().isEmpty()) {
                        menuItemDTOs.add(menuItemDTO);
                    }
                }
            }
        }

        data.setMenus(menuItemDTOs);
        return data;
    }

    @Transactional
    public AppMenu saveMenu(AppMenu menu) {
        return appMenuRepository.save(menu);
    }

    @Transactional
    public AppSubMenu saveSubMenu(Long menuId, AppSubMenu subMenu) {
        AppMenu menu = appMenuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        subMenu.setMenu(menu);
        return appSubMenuRepository.save(subMenu);
    }

    @Transactional
    public void deleteMenu(Long id) {
        appMenuRepository.deleteById(id);
    }

    @Transactional
    public void deleteSubMenu(Long id) {
        appSubMenuRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AppMenu> getAllMenus() {
        return appMenuRepository.findAll();
    }

    @Transactional
    public void saveUserPermissions(Long userId, List<UserMenuPermission> permissions) {
        ApplicationUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Remove existing permissions
        List<UserMenuPermission> existing = userMenuPermissionRepository.findByUserId(userId);
        userMenuPermissionRepository.deleteAll(existing);

        // Add new permissions
        for (UserMenuPermission perm : permissions) {
            perm.setUser(user);
            userMenuPermissionRepository.save(perm);
        }
    }

    @Transactional(readOnly = true)
    public List<UserMenuPermission> getUserPermissions(Long userId) {
        return userMenuPermissionRepository.findByUserId(userId);
    }

    private MenuItemDTO convertToMenuItemDTO(AppMenu menu, boolean isAdmin, Map<Long, UserMenuPermission> permissionMap) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setMenuId(menu.getId().toString());
        dto.setMenuName(menu.getName());
        dto.setMenuIcon(menu.getIcon());
        dto.setMenuOrder(menu.getOrder() != null ? menu.getOrder() : 0);
        dto.setActive(menu.getActive());

        List<SubMenuItemDTO> subMenuDTOs = new ArrayList<>();
        for (AppSubMenu subMenu : menu.getSubMenus()) {
            if (!subMenu.getActive()) continue;

            if (isAdmin) {
                subMenuDTOs.add(convertToSubMenuItemDTO(subMenu, null, true));
            } else if (permissionMap != null && permissionMap.containsKey(subMenu.getId())) {
                subMenuDTOs.add(convertToSubMenuItemDTO(subMenu, permissionMap.get(subMenu.getId()), false));
            }
        }
        dto.setSubMenus(subMenuDTOs);
        return dto;
    }

    private SubMenuItemDTO convertToSubMenuItemDTO(AppSubMenu subMenu, UserMenuPermission permission, boolean isAdmin) {
        SubMenuItemDTO dto = new SubMenuItemDTO();
        dto.setSubMenuId(subMenu.getId().toString());
        dto.setSubMenuName(subMenu.getName());
        dto.setSubMenuPath(subMenu.getPath());
        dto.setSubMenuOrder(subMenu.getOrder() != null ? subMenu.getOrder() : 0);
        dto.setActive(subMenu.getActive());

        MenuActionPermissionsDTO actions = new MenuActionPermissionsDTO();
        if (isAdmin) {
            actions.setAdd(true);
            actions.setEdit(true);
            actions.setDelete(true);
            actions.setView(true);
            actions.setExport(true);
            actions.setPrint(true);
            actions.setApprove(true);
            actions.setReject(true);
            actions.setProcess(true);
            actions.setShare(true);
            actions.setDuplicate(true);
            actions.setSchedule(true);
        } else if (permission != null) {
            actions.setAdd(permission.getCanAdd());
            actions.setEdit(permission.getCanEdit());
            actions.setDelete(permission.getCanDelete());
            actions.setView(permission.getCanView());
            actions.setExport(permission.getCanExport());
            actions.setPrint(permission.getCanPrint());
            actions.setApprove(permission.getCanApprove());
            actions.setReject(permission.getCanReject());
            actions.setProcess(permission.getCanProcess());
            actions.setShare(permission.getCanShare());
            actions.setDuplicate(permission.getCanDuplicate());
            actions.setSchedule(permission.getCanSchedule());
        }
        dto.setActions(actions);
        return dto;
    }
}
