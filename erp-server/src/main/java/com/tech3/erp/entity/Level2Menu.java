package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "level2_menus", schema = "masters")
public class Level2Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_type")
    private Long userType;

    @OneToMany(mappedBy = "level2Menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("level2MenuItems")
    private List<Level2MenuItem> items = new ArrayList<>();

    public Level2Menu() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getUserType() { return userType; }
    public void setUserType(Long userType) { this.userType = userType; }

    public List<Level2MenuItem> getItems() { return items; }
    public void setItems(List<Level2MenuItem> items) {
        this.items = items;
        if (items != null) {
            for (Level2MenuItem it : items) {
                it.setLevel2Menu(this);
            }
        }
    }
}