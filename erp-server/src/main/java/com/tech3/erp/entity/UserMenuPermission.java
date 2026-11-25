package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_menu_permissions", schema = "masters")
public class UserMenuPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submenu_id", nullable = false)
    private AppSubMenu subMenu;

    @Column(name = "can_add")
    private Boolean canAdd = false;

    @Column(name = "can_edit")
    private Boolean canEdit = false;

    @Column(name = "can_delete")
    private Boolean canDelete = false;

    @Column(name = "can_view")
    private Boolean canView = true;

    @Column(name = "can_export")
    private Boolean canExport = false;

    @Column(name = "can_print")
    private Boolean canPrint = false;

    @Column(name = "can_approve")
    private Boolean canApprove = false;

    @Column(name = "can_reject")
    private Boolean canReject = false;

    @Column(name = "can_process")
    private Boolean canProcess = false;

    @Column(name = "can_share")
    private Boolean canShare = false;

    @Column(name = "can_duplicate")
    private Boolean canDuplicate = false;

    @Column(name = "can_schedule")
    private Boolean canSchedule = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public AppSubMenu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(AppSubMenu subMenu) {
        this.subMenu = subMenu;
    }

    public Boolean getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(Boolean canAdd) {
        this.canAdd = canAdd;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Boolean getCanView() {
        return canView;
    }

    public void setCanView(Boolean canView) {
        this.canView = canView;
    }

    public Boolean getCanExport() {
        return canExport;
    }

    public void setCanExport(Boolean canExport) {
        this.canExport = canExport;
    }

    public Boolean getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(Boolean canPrint) {
        this.canPrint = canPrint;
    }

    public Boolean getCanApprove() {
        return canApprove;
    }

    public void setCanApprove(Boolean canApprove) {
        this.canApprove = canApprove;
    }

    public Boolean getCanReject() {
        return canReject;
    }

    public void setCanReject(Boolean canReject) {
        this.canReject = canReject;
    }

    public Boolean getCanProcess() {
        return canProcess;
    }

    public void setCanProcess(Boolean canProcess) {
        this.canProcess = canProcess;
    }

    public Boolean getCanShare() {
        return canShare;
    }

    public void setCanShare(Boolean canShare) {
        this.canShare = canShare;
    }

    public Boolean getCanDuplicate() {
        return canDuplicate;
    }

    public void setCanDuplicate(Boolean canDuplicate) {
        this.canDuplicate = canDuplicate;
    }

    public Boolean getCanSchedule() {
        return canSchedule;
    }

    public void setCanSchedule(Boolean canSchedule) {
        this.canSchedule = canSchedule;
    }
}
