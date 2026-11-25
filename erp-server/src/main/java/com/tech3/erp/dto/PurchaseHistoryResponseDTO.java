package com.tech3.erp.dto;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryResponseDTO {

    private Long itemId;
    private String itemName;
    private String itemCode;
    private String uom;
    private LatestPurchaseSummaryDTO latestPurchase;
    private List<PurchaseHistoryRecordDTO> records = new ArrayList<>();
    private long total;
    private long offset;
    private long limit;
    private boolean hasMore;

    public PurchaseHistoryResponseDTO() {}

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public LatestPurchaseSummaryDTO getLatestPurchase() {
        return latestPurchase;
    }

    public void setLatestPurchase(LatestPurchaseSummaryDTO latestPurchase) {
        this.latestPurchase = latestPurchase;
    }

    public List<PurchaseHistoryRecordDTO> getRecords() {
        return records;
    }

    public void setRecords(List<PurchaseHistoryRecordDTO> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
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

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
