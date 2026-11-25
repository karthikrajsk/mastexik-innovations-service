package com.tech3.erp.service;

public class SyncContext {
    private static final ThreadLocal<Boolean> syncInProgress = ThreadLocal.withInitial(() -> false);

    public static boolean isInProgress() {
        return syncInProgress.get();
    }

    public static void setInProgress(boolean inProgress) {
        syncInProgress.set(inProgress);
    }

    public static void clear() {
        syncInProgress.remove();
    }
}
