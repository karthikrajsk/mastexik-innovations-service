package com.tech3.erp.dto;

import com.tech3.erp.entity.Level1Menu;

import java.util.List;
import java.util.stream.Collectors;

public class Level1MenuDTO {
    private Long id;
    private Long userId;
    private Long userType;
    private List<Level1MenuItemDTO> items;

    public Level1MenuDTO() {}

    public Level1MenuDTO(Level1Menu e) {
        this.id = e.getId();
        this.userId = e.getUserId();
        this.userType = e.getUserType();
        this.items = e.getItems() == null ? null : e.getItems().stream()
                .map(Level1MenuItemDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getUserType() { return userType; }
    public void setUserType(Long userType) { this.userType = userType; }

    public List<Level1MenuItemDTO> getItems() { return items; }
    public void setItems(List<Level1MenuItemDTO> items) { this.items = items; }
}