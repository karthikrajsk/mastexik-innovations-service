package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tech3.erp.entity.Level1MenuItem;

public class Level1MenuItemDTO {
    private Long id;

    @JsonAlias({"name", "Level 1 Name"})
    private String name;

    private Integer sequence;

    public Level1MenuItemDTO() {}

    public Level1MenuItemDTO(Level1MenuItem e) {
        this.id = e.getId();
        this.name = e.getName();
        this.sequence = e.getSequence();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getSequence() { return sequence; }
    public void setSequence(Integer sequence) { this.sequence = sequence; }
}