package com.tech3.erp.service;

import com.tech3.erp.dto.MenuMasterDTO;
import com.tech3.erp.entity.MenuMaster;
import com.tech3.erp.repository.MenuMasterRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuMasterService {

    private final MenuMasterRepository repository;

    public MenuMasterService(MenuMasterRepository repository) {
        this.repository = repository;
    }

    public MenuMasterDTO create(MenuMasterDTO dto) {
        String screenName = normalize(dto.getScreenName());
        if (!screenName.isEmpty() && repository.findByScreenNameIgnoreCase(screenName).isPresent()) {
            throw new IllegalArgumentException("Screen name '" + screenName + "' already exists");
        }
        String menuUrl = normalize(dto.getMenuUrl());
        if (!menuUrl.isEmpty()) {
            Optional<MenuMaster> urlConflict = repository.findByMenuUrlIgnoreCase(menuUrl);
            if (urlConflict.isPresent()) {
                throw new IllegalArgumentException("Menu URL '" + menuUrl + "' is already linked with screen name '" +
                        urlConflict.get().getScreenName() + "'.");
            }
        }
        MenuMaster e = new MenuMaster();
        e.setScreenName(screenName);
        e.setMenuUrl(menuUrl);
        return new MenuMasterDTO(repository.save(e));
    }

    public MenuMasterDTO get(Long id) {
        MenuMaster e = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("MenuMaster not found"));
        return new MenuMasterDTO(e);
    }

    public List<MenuMasterDTO> list() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(MenuMaster::getId, Comparator.reverseOrder()))
                .map(MenuMasterDTO::new)
                .collect(Collectors.toList());
    }

    public MenuMasterDTO update(Long id, MenuMasterDTO dto) {
        MenuMaster e = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("MenuMaster not found"));
        String screenName = normalize(dto.getScreenName());
        if (!screenName.isEmpty()) {
            Optional<MenuMaster> screenConflict = repository.findByScreenNameIgnoreCase(screenName);
            if (screenConflict.isPresent() && !screenConflict.get().getId().equals(id)) {
                throw new IllegalArgumentException("Screen name '" + screenName + "' already exists");
            }
        }
        String menuUrl = normalize(dto.getMenuUrl());
        if (!menuUrl.isEmpty()) {
            Optional<MenuMaster> urlConflict = repository.findByMenuUrlIgnoreCase(menuUrl);
            if (urlConflict.isPresent() && !urlConflict.get().getId().equals(id)) {
                throw new IllegalArgumentException("Menu URL '" + menuUrl + "' is already linked with screen name '" +
                        urlConflict.get().getScreenName() + "'.");
            }
        }
        e.setScreenName(screenName);
        e.setMenuUrl(menuUrl);
        return new MenuMasterDTO(repository.save(e));
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("MenuMaster not found");
        repository.deleteById(id);
    }
}