package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.PurchaseInwardItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseInwardItemDTO {

    private Long id;
    private Long inwardId;
    private Long poItemId;
    private BigDecimal quantityReceived;
    private BigDecimal price;
    private Boolean activeFlag;
    private Long millNameId;
    private Long noOfPiece;
    private Long yarnTypeId;
    private String fabricCode; // ADD THIS
    private String remarks;
    private BigDecimal totalAmount;
    private BigDecimal gstAmount;
    private BigDecimal finalAmount;
    private BigDecimal balanceQty;

    // For yarn stock details (replaces lot entries)
    @JsonProperty("yarnStockDetails")
    private List<YarnStockDetailDTO> yarnStockDetails;

    // For item stock details
    @JsonProperty("itemStockDetails")
    private List<ItemInwardStockDetailsDto> itemStockDetails;

    // For fabric - piece entries
    @JsonProperty("pieceEntries")
    private List<PieceEntryDTO> pieceEntries;

    public PurchaseInwardItemDTO() {}

    public PurchaseInwardItemDTO(PurchaseInwardItem item) {
        this.id = item.getId();
        this.inwardId = item.getPurchaseInward().getId();
        this.poItemId = item.getPurchaseOrderItemId();
        this.quantityReceived = item.getQuantityReceived();
        this.price = item.getPrice();
        this.activeFlag = item.getActiveFlag();
        this.millNameId = item.getMillNameId();
        this.noOfPiece = item.getNoOfPiece();
        this.yarnTypeId = item.getYarnTypeId();
        this.fabricCode = item.getFabricCode();
        this.remarks = item.getRemarks();
        this.totalAmount = item.getTotalAmount();
        this.gstAmount = item.getGstAmount();
        this.finalAmount = item.getFinalAmount();
        this.balanceQty = item.getBalanceQty();
        
        // Map piece entries if present
        if (item.getPieceEntries() != null && !item.getPieceEntries().isEmpty()) {
            this.pieceEntries = item.getPieceEntries().stream()
                .map(PieceEntryDTO::new)
                .collect(Collectors.toList());

            if (this.pieceEntries != null) {
                this.pieceEntries.forEach(pieceDto -> {
                    if (pieceDto.getInspectionStatus() == null) {
                        pieceDto.setInspectionStatus("PENDING");
                    }
                });
            }
        }
    }

    public PurchaseInwardItemDTO(PurchaseInwardItem item, List<YarnStockDetailDTO> yarnStockDetails) {
        this(item);
        this.yarnStockDetails = yarnStockDetails;
    }

  

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInwardId() {
        return inwardId;
    }

    public void setInwardId(Long inwardId) {
        this.inwardId = inwardId;
    }

    public Long getPoItemId() {
        return poItemId;
    }

    public void setPoItemId(Long poItemId) {
        this.poItemId = poItemId;
    }

    public BigDecimal getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(BigDecimal quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getMillNameId() {
        return millNameId;
    }

    public void setMillNameId(Long millNameId) {
        this.millNameId = millNameId;
    }

    public List<YarnStockDetailDTO> getYarnStockDetails() {
        return yarnStockDetails;
    }

    public void setYarnStockDetails(List<YarnStockDetailDTO> yarnStockDetails) {
        this.yarnStockDetails = yarnStockDetails;
    }

    public List<PieceEntryDTO> getPieceEntries() {
        return pieceEntries;
    }

    public void setPieceEntries(List<PieceEntryDTO> pieceEntries) {
        this.pieceEntries = pieceEntries;
    }
    
    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
    }

    public List<ItemInwardStockDetailsDto> getItemStockDetails() {
        return itemStockDetails;
    }

    public void setItemStockDetails(List<ItemInwardStockDetailsDto> itemStockDetails) {
        this.itemStockDetails = itemStockDetails;
    }

    public Long getNoOfPiece() {
        return noOfPiece;
    }

    public void setNoOfPiece(Long noOfPiece) {
        this.noOfPiece = noOfPiece;
    }

    public Long getYarnTypeId() {
        return yarnTypeId;
    }

    public void setYarnTypeId(Long yarnTypeId) {
        this.yarnTypeId = yarnTypeId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getBalanceQty() {
        return balanceQty;
    }

    public void setBalanceQty(BigDecimal balanceQty) {
        this.balanceQty = balanceQty;
    }
}