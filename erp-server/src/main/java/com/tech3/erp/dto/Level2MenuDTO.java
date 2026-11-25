package com.tech3.erp.dto;

import com.tech3.erp.entity.Level2Menu;
import com.tech3.erp.entity.Level2MenuItem;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class Level2MenuDTO {
    private Long id;

    @NotNull
    private Long userId;

    private Long userType;

    private List<Level2MenuItemDTO> items;

    public Level2MenuDTO() {}

    public Level2MenuDTO(Level2Menu e) {
        this.id = e.getId();
        this.userId = e.getUserId();
        this.userType = e.getUserType();
        this.items = e.getItems() == null ? null : e.getItems().stream()
                .map(Level2MenuItemDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getUserType() { return userType; }
    public void setUserType(Long userType) { this.userType = userType; }

    public List<Level2MenuItemDTO> getItems() { return items; }
    public void setItems(List<Level2MenuItemDTO> items) { this.items = items; }
}