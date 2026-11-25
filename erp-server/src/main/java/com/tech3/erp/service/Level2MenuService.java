package com.tech3.erp.service;

import com.tech3.erp.dto.Level2MenuDTO;
import com.tech3.erp.dto.Level2MenuItemDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Level2Menu;
import com.tech3.erp.entity.Level2MenuItem;
import com.tech3.erp.entity.Level1MenuItem;
import com.tech3.erp.entity.MenuMaster;
import com.tech3.erp.entity.ApplicationUser;
import com.tech3.erp.repository.Level2MenuRepository;
import com.tech3.erp.repository.ApplicationUserRepository;
import com.tech3.erp.repository.MenuMasterRepository;
import com.tech3.erp.repository.Level1MenuItemRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class Level2MenuService {

    private final Level2MenuRepository repository;
    private final ApplicationUserRepository applicationUserRepository;
    private final MenuMasterRepository menuMasterRepository;
    private final Level1MenuItemRepository level1MenuItemRepository;

    public Level2MenuService(Level2MenuRepository repository, 
                             ApplicationUserRepository applicationUserRepository,
                             MenuMasterRepository menuMasterRepository,
                             Level1MenuItemRepository level1MenuItemRepository) {
        this.repository = repository;
        this.applicationUserRepository = applicationUserRepository;
        this.menuMasterRepository = menuMasterRepository;
        this.level1MenuItemRepository = level1MenuItemRepository;
    }

   public Level2MenuDTO create(Level2MenuDTO dto) {
    Level2Menu parent = new Level2Menu();
    parent.setUserId(dto.getUserId());
    
    // Populate userType from ApplicationUser
    applicationUserRepository.findById(dto.getUserId())
            .ifPresent(u -> parent.setUserType(u.getUserType()));

    List<Level2MenuItem> items = (dto.getItems() == null ? List.<Level2MenuItemDTO>of() : dto.getItems())
        .stream()
        .map((Level2MenuItemDTO it) -> toEntity(it)) // forces correct type
        .peek(it -> it.setLevel2Menu(parent))
        .collect(Collectors.toList());

    parent.getItems().clear();
    parent.getItems().addAll(items);

    Level2Menu saved = repository.save(parent);
    return new Level2MenuDTO(saved);
}


    public Level2MenuDTO get(Long id) {
        Level2Menu e = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Level2Menu not found"));
        return new Level2MenuDTO(e);
    }

    public List<Level2MenuDTO> list() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Level2Menu::getId, Comparator.reverseOrder()))
                .map(Level2MenuDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<Level2MenuDTO> listWithPagination(Long offset, Long limit) {
        List<Level2MenuDTO> all = list();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public List<Level2MenuDTO> listByUser(Long userId) {
        List<Level2Menu> userMenus = repository.findByUserId(userId);
        
        if (userMenus.isEmpty()) {
            // New Requirement: Load all Master menus by default for new users
            List<MenuMaster> allMasters = menuMasterRepository.findAll();
            if (!allMasters.isEmpty()) {
                // Find or create "Master" Level 1 item for this user
                Long masterLevel1Id = level1MenuItemRepository.findByNameContainingIgnoreCase("Master").stream()
                        .filter(it -> it.getLevel1Menu() != null && it.getLevel1Menu().getUserId().equals(userId))
                        .map(Level1MenuItem::getId)
                        .findFirst()
                        .orElse(null);

                if (masterLevel1Id != null) {
                    Level2Menu defaultMenu = new Level2Menu();
                    defaultMenu.setUserId(userId);
                    applicationUserRepository.findById(userId).ifPresent(u -> defaultMenu.setUserType(u.getUserType()));
                    
                    List<Level2MenuItem> items = new ArrayList<>();
                    int seq = 1;
                    for (MenuMaster mm : allMasters) {
                        Level2MenuItem item = new Level2MenuItem();
                        item.setLevel2Menu(defaultMenu);
                        item.setLevel1Id(masterLevel1Id);
                        item.setMenuMasterId(mm.getId());
                        item.setUrl(mm.getMenuUrl());
                        item.setSequence(seq++);
                        item.setCanAdd(true);
                        item.setCanEdit(true);
                        item.setCanView(true);
                        item.setCanExport(true);
                        item.setCanPrint(true);
                        // canDelete and canApprove based on user type
                        Long userType = defaultMenu.getUserType();
                        item.setCanDelete(userType != null && userType >= 2);
                        item.setCanApprove(userType != null && userType >= 3);
                        items.add(item);
                    }
                    defaultMenu.setItems(items);
                    // We don't save it yet, we just return it so it's visible by default
                    // It will be saved when the Admin clicks "Save" or "Update"
                    return List.of(new Level2MenuDTO(defaultMenu));
                }
            }
        }
        
        return userMenus.stream().map(Level2MenuDTO::new).collect(Collectors.toList());
    }

    public Level2MenuDTO update(Long id, Level2MenuDTO dto) {
        Level2Menu parent = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Level2Menu not found"));
        parent.setUserId(dto.getUserId());
        
        // Populate userType from ApplicationUser
        applicationUserRepository.findById(dto.getUserId())
                .ifPresent(u -> parent.setUserType(u.getUserType()));

        List<Level2MenuItem> items = dto.getItems().stream().map(this::toEntity)
                .peek(it -> it.setLevel2Menu(parent))
                .collect(Collectors.toList());
        parent.getItems().clear();
        parent.getItems().addAll(items);

        Level2Menu saved = repository.save(parent);
        return new Level2MenuDTO(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Level2Menu not found");
        repository.deleteById(id);
    }

    private Level2MenuItem toEntity(Level2MenuItemDTO dto) {
        Level2MenuItem e = new Level2MenuItem();
        e.setLevel1Id(dto.getLevel1Id());
        e.setMenuMasterId(dto.getMenuMasterId());
        e.setUrl(dto.getUrl() == null ? "" : dto.getUrl());
        e.setSequence(dto.getSequence() == null ? 0 : dto.getSequence());
        e.setCanAdd(dto.isCanAdd());
        e.setCanEdit(dto.isCanEdit());
        e.setCanDelete(dto.isCanDelete());
        e.setCanView(dto.isCanView());
        e.setCanExport(dto.isCanExport());
        e.setCanPrint(dto.isCanPrint());
        e.setCanApprove(dto.isCanApprove());
        return e;
    }
}