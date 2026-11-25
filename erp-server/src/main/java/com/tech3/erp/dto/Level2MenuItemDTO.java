package com.tech3.erp.dto;

import com.tech3.erp.entity.Level2MenuItem;

public class Level2MenuItemDTO {
    private Long id;
    private Long level1Id;
    private Long menuMasterId;
    private String url;
    private Integer sequence;
    private boolean canAdd;
    private boolean canEdit;
    private boolean canDelete;
    private boolean canView;
    private boolean canExport;
    private boolean canPrint;
    private boolean canApprove;

    public Level2MenuItemDTO() {}

    public Level2MenuItemDTO(Level2MenuItem e) {
        this.id = e.getId();
        this.level1Id = e.getLevel1Id();
        this.menuMasterId = e.getMenuMasterId();
        this.url = e.getUrl();
        this.sequence = e.getSequence();
        this.canAdd = e.isCanAdd();
        this.canEdit = e.isCanEdit();
        this.canDelete = e.isCanDelete();
        this.canView = e.isCanView();
        this.canExport = e.isCanExport();
        this.canPrint = e.isCanPrint();
        this.canApprove = e.isCanApprove();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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