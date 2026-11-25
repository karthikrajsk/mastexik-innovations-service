package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "level2_menu_items", schema = "masters")
public class Level2MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level2_menu_id")
    @JsonBackReference("level2MenuItems")
    private Level2Menu level2Menu;

    @Column(name = "level1_id", nullable = false)
    private Long level1Id;

    @Column(name = "menu_master_id", nullable = false)
    private Long menuMasterId;

    @Column(name = "url", nullable = false, length = 300)
    private String url = "";

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "can_add", nullable = false)
    private boolean canAdd = false;

    @Column(name = "can_edit", nullable = false)
    private boolean canEdit = false;

    @Column(name = "can_delete", nullable = false)
    private boolean canDelete = false;

    @Column(name = "can_view", nullable = false, columnDefinition = "boolean default true")
    private boolean canView = true;

    @Column(name = "can_export", nullable = false, columnDefinition = "boolean default false")
    private boolean canExport = false;

    @Column(name = "can_print", nullable = false, columnDefinition = "boolean default false")
    private boolean canPrint = false;

    @Column(name = "can_approve", nullable = false, columnDefinition = "boolean default false")
    private boolean canApprove = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Level2Menu getLevel2Menu() { return level2Menu; }
    public void setLevel2Menu(Level2Menu level2Menu) { this.level2Menu = level2Menu; }

    public Long getLevel1Id() { return level1Id; }
    public void setLevel1Id(Long level1Id) { this.level1Id = level1Id; }

    public Long getMenuMasterId() { return menuMasterId; }
    public void setMenuMasterId(Long menuMasterId) { this.menuMasterId = menuMasterId; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getSequence() { return sequence; }
    public void setSequence(Integer sequence) { this.sequence = sequence; }

    public boolean isCanAdd() { return canAdd; }
    public void setCanAdd(boolean canAdd) { this.canAdd = canAdd; }

    public boolean isCanEdit() { return canEdit; }
    public void setCanEdit(boolean canEdit) { this.canEdit = canEdit; }

    public boolean isCanDelete() { return canDelete; }
    public void setCanDelete(boolean canDelete) { this.canDelete = canDelete; }

    public boolean isCanView() { return canView; }
    public void setCanView(boolean canView) { this.canView = canView; }

    public boolean isCanExport() { return canExport; }
    public void setCanExport(boolean canExport) { this.canExport = canExport; }

    public boolean isCanPrint() { return canPrint; }
    public void setCanPrint(boolean canPrint) { this.canPrint = canPrint; }

    public boolean isCanApprove() { return canApprove; }
    public void setCanApprove(boolean canApprove) { this.canApprove = canApprove; }
}