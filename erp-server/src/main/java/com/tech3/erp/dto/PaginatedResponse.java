package com.tech3.erp.dto;

import java.util.List;

public class PaginatedResponse<T> {
    private long total;
    private boolean next;
    private boolean prev;
    private long offset;
    private long limit;
    private List<T> data;

    public PaginatedResponse(long total, boolean next, boolean prev, long offset, long limit, List<T> data) {
        this.total = total;
        this.next = next;
        this.prev = prev;
        this.offset = offset;
        this.limit = limit;
        this.data = data;
    }

    public PaginatedResponse() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
