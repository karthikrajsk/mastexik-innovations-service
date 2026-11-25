package com.tech3.erp.dto.menu;

public class MenuActionPermissionsDTO {
    private boolean add;
    private boolean edit;
    private boolean delete;
    private boolean view;
    private boolean export;
    private boolean print;
    private boolean approve;
    private boolean reject;
    private boolean process;
    private boolean share;
    private boolean duplicate;
    private boolean schedule;

    // Getters and Setters
    public boolean isAdd() { return add; }
    public void setAdd(boolean add) { this.add = add; }

    public boolean isEdit() { return edit; }
    public void setEdit(boolean edit) { this.edit = edit; }

    public boolean isDelete() { return delete; }
    public void setDelete(boolean delete) { this.delete = delete; }

    public boolean isView() { return view; }
    public void setView(boolean view) { this.view = view; }

    public boolean isExport() { return export; }
    public void setExport(boolean export) { this.export = export; }

    public boolean isPrint() { return print; }
    public void setPrint(boolean print) { this.print = print; }

    public boolean isApprove() { return approve; }
    public void setApprove(boolean approve) { this.approve = approve; }

    public boolean isReject() { return reject; }
    public void setReject(boolean reject) { this.reject = reject; }

    public boolean isProcess() { return process; }
    public void setProcess(boolean process) { this.process = process; }

    public boolean isShare() { return share; }
    public void setShare(boolean share) { this.share = share; }

    public boolean isDuplicate() { return duplicate; }
    public void setDuplicate(boolean duplicate) { this.duplicate = duplicate; }

    public boolean isSchedule() { return schedule; }
    public void setSchedule(boolean schedule) { this.schedule = schedule; }
}
