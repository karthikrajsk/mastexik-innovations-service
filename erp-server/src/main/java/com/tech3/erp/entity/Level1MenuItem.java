package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "level1_menu_items", schema = "masters")
public class Level1MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level1_menu_id")
    @JsonBackReference("level1MenuItems")
    private Level1Menu level1Menu;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "sequence")
    private Integer sequence;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Level1Menu getLevel1Menu() { return level1Menu; }
    public void setLevel1Menu(Level1Menu level1Menu) { this.level1Menu = level1Menu; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getSequence() { return sequence; }
    public void setSequence(Integer sequence) { this.sequence = sequence; }
}