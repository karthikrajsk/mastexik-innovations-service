package com.tech3.erp.service;

import com.tech3.erp.dto.Level1MenuDTO;
import com.tech3.erp.dto.Level1MenuItemDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Level1Menu;
import com.tech3.erp.entity.Level1MenuItem;
import com.tech3.erp.entity.ApplicationUser;
import com.tech3.erp.repository.Level1MenuRepository;
import com.tech3.erp.repository.ApplicationUserRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Level1MenuService {

    private final Level1MenuRepository repository;
    private final ApplicationUserRepository applicationUserRepository;

    public Level1MenuService(Level1MenuRepository repository, ApplicationUserRepository applicationUserRepository) {
        this.repository = repository;
        this.applicationUserRepository = applicationUserRepository;
    }

    // ==== New CRUD methods (mirroring Level2) ====

    public Level1MenuDTO create(Level1MenuDTO dto) {
        Level1Menu parent = new Level1Menu();
        parent.setUserId(dto.getUserId());
        
        // Populate userType from ApplicationUser
        applicationUserRepository.findById(dto.getUserId())
                .ifPresent(u -> parent.setUserType(u.getUserType()));

        List<Level1MenuItem> items = (dto.getItems() == null ? List.<Level1MenuItemDTO>of() : dto.getItems())
                .stream()
                .map((Level1MenuItemDTO it) -> toEntity(it))
                .peek(it -> it.setLevel1Menu(parent))
                .collect(Collectors.toList());

        parent.getItems().clear();
        parent.getItems().addAll(items);

        Level1Menu saved = repository.save(parent);
        return new Level1MenuDTO(saved);
    }

    public Level1MenuDTO get(Long id) {
        Level1Menu e = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Level1Menu not found"));
        return new Level1MenuDTO(e);
    }

    public List<Level1MenuDTO> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(Level1MenuDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<Level1MenuDTO> listWithPagination(Long offset, Long limit) {
        long sanitizedOffset = Math.max(0, offset != null ? offset : 0L);
        long sanitizedLimit = PaginationHelper.sanitizeLimit(limit);
        long total = repository.count();

        if (total == 0 || sanitizedOffset >= total) {
            boolean hasPrev = sanitizedOffset > 0 && total > 0;
            return new PaginatedResponse<>(total, false, hasPrev, sanitizedOffset, sanitizedLimit, List.of());
        }

        List<Level1MenuDTO> page = fetchPaginatedLevel1Menus(sanitizedOffset, sanitizedLimit);
        boolean hasNext = sanitizedOffset + sanitizedLimit < total;
        boolean hasPrev = sanitizedOffset > 0;

        return new PaginatedResponse<>(total, hasNext, hasPrev, sanitizedOffset, sanitizedLimit, page);
    }

    private List<Level1MenuDTO> fetchPaginatedLevel1Menus(long offset, long limit) {
        if (limit <= 0) {
            return List.of();
        }

        int pageSize = (int) Math.min(limit, Integer.MAX_VALUE);
        long initialPage = offset / limit;
        int skipInPage = (int) (offset % limit);
        List<Level1MenuDTO> result = new ArrayList<>();
        int remaining = (int) limit;
        long currentPage = initialPage;

        while (remaining > 0) {
            if (currentPage > Integer.MAX_VALUE) {
                break;
            }

            PageRequest pageRequest = PageRequest.of((int) currentPage, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            Page<Level1Menu> page = repository.findAll(pageRequest);

            if (page.isEmpty()) {
                break;
            }

            List<Level1MenuDTO> pageDtos = page.getContent()
                    .stream()
                    .map(Level1MenuDTO::new)
                    .collect(Collectors.toList());

            int startIndex = currentPage == initialPage ? skipInPage : 0;
            for (int i = startIndex; i < pageDtos.size() && remaining > 0; i++) {
                result.add(pageDtos.get(i));
                remaining--;
            }

            if (!page.hasNext()) {
                break;
            }

            currentPage++;
        }

        return result;
    }

    public List<Level1MenuDTO> listByUser(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(Level1MenuDTO::new)
                .collect(Collectors.toList());
    }

    public Level1MenuDTO update(Long id, Level1MenuDTO dto) {
        Level1Menu parent = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Level1Menu not found"));
        parent.setUserId(dto.getUserId());
        
        // Populate userType from ApplicationUser
        applicationUserRepository.findById(dto.getUserId())
                .ifPresent(u -> parent.setUserType(u.getUserType()));

        syncItems(parent, dto.getItems());

        Level1Menu saved = repository.save(parent);
        return new Level1MenuDTO(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Level1Menu not found");
        repository.deleteById(id);
    }

    // ==== Existing by-user pattern (kept for compatibility with frontend) ====

    public Level1MenuDTO getByUser(Long userId) {
        List<Level1Menu> existing = repository.findByUserId(userId);
        if (!existing.isEmpty()) {
            return new Level1MenuDTO(existing.get(0));
        }
        
        Level1Menu p = new Level1Menu();
        p.setUserId(userId);
        applicationUserRepository.findById(userId).ifPresent(u -> p.setUserType(u.getUserType()));
        Level1Menu saved = repository.save(p);
        return new Level1MenuDTO(saved);
    }

    public Level1MenuDTO saveByUser(Level1MenuDTO request) {
        List<Level1Menu> existing = repository.findByUserId(request.getUserId());
        Level1Menu parent = existing.isEmpty() ? new Level1Menu() : existing.get(0);
        parent.setUserId(request.getUserId());
        
        applicationUserRepository.findById(request.getUserId())
                .ifPresent(u -> parent.setUserType(u.getUserType()));

        syncItems(parent, request.getItems());
        
        Level1Menu saved = repository.save(parent);
        return new Level1MenuDTO(saved);
    }

    private void syncItems(Level1Menu parent, List<Level1MenuItemDTO> dtos) {
        if (dtos == null) {
            parent.getItems().clear();
            return;
        }

        // Map existing items by ID
        java.util.Map<Long, Level1MenuItem> existingMap = parent.getItems().stream()
                .filter(it -> it.getId() != null)
                .collect(Collectors.toMap(Level1MenuItem::getId, it -> it));

        List<Level1MenuItem> syncedList = new java.util.ArrayList<>();

        for (Level1MenuItemDTO dto : dtos) {
            Level1MenuItem item;
            if (dto.getId() != null && existingMap.containsKey(dto.getId())) {
                item = existingMap.get(dto.getId());
                existingMap.remove(dto.getId()); // Mark as kept
            } else {
                item = new Level1MenuItem();
                item.setLevel1Menu(parent);
            }
            item.setName(dto.getName());
            item.setSequence(dto.getSequence());
            syncedList.add(item);
        }

        // Clear and add all to maintain order if necessary, but orphanRemoval will delete items left in existingMap
        parent.getItems().clear();
        parent.getItems().addAll(syncedList);
    }

    // ==== Mapper ====
    private Level1MenuItem toEntity(Level1MenuItemDTO dto) {
        Level1MenuItem e = new Level1MenuItem();
        if (dto.getId() != null) e.setId(dto.getId());
        e.setName(dto.getName());
        e.setSequence(dto.getSequence());
        return e;
    }
}
