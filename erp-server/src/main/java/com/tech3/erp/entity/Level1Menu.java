package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "level1_menus", schema = "masters")
public class Level1Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_type")
    private Long userType;

    @OneToMany(mappedBy = "level1Menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("level1MenuItems")
    private List<Level1MenuItem> items = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getUserType() { return userType; }
    public void setUserType(Long userType) { this.userType = userType; }

    public List<Level1MenuItem> getItems() { return items; }
    public void setItems(List<Level1MenuItem> items) {
        this.items = items;
        if (items != null) {
            for (Level1MenuItem it : items) {
                it.setLevel1Menu(this);
            }
        }
    }
}