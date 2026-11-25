package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "purchase_inward_item", schema = "masters")
public class PurchaseInwardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_received", nullable = false)
    private BigDecimal quantityReceived;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @ManyToOne
    @JoinColumn(name = "inward_id")
    @JsonBackReference
    private PurchaseInward purchaseInward;

    @Column(name = "po_item_id", nullable = false)
    private Long purchaseOrderItemId;
    
    @Column(name = "mill_name_id")
    private Long millNameId;
    
    @Column(name = "fabric_code") 
    private String fabricCode;

    @Column(name = "yarn_type_id")
    private Long yarnTypeId;

    @Column(name = "no_of_piece")
    private Long noOfPiece;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "gst_amount", precision = 19, scale = 2)
    private BigDecimal gstAmount;

    @Column(name = "final_amount", precision = 19, scale = 2)
    private BigDecimal finalAmount;

    @Column(name = "balance_qty", precision = 19, scale = 4)
    private BigDecimal balanceQty;

    // Existing relationship - for yarn (lot entries)
    @OneToMany(mappedBy = "purchaseInwardItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LotEntry> lotEntries = new ArrayList<>();

    // NEW relationship - for fabric (piece entries)
    @OneToMany(mappedBy = "purchaseInwardItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("purchaseInwardPieces")
    private List<PieceEntry> pieceEntries = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
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

    public PurchaseInward getPurchaseInward() {
        return purchaseInward;
    }

    public void setPurchaseInward(PurchaseInward purchaseInward) {
        this.purchaseInward = purchaseInward;
    }

    public Long getPurchaseOrderItemId() {
        return purchaseOrderItemId;
    }

    public void setPurchaseOrderItemId(Long purchaseOrderItemId) {
        this.purchaseOrderItemId = purchaseOrderItemId;
    }

    public Long getMillNameId() {
        return millNameId;
    }

    public void setMillNameId(Long millNameId) {
        this.millNameId = millNameId;
    }

    public List<LotEntry> getLotEntries() {
        return lotEntries;
    }

    public void setLotEntries(List<LotEntry> lotEntries) {
        this.lotEntries = lotEntries;
    }

    public List<PieceEntry> getPieceEntries() {
        return pieceEntries;
    }

    public void setPieceEntries(List<PieceEntry> pieceEntries) {
        this.pieceEntries = pieceEntries;
    }
    
    public Long getNoOfPiece() {
        return noOfPiece;
    }

    public void setNoOfPiece(Long noOfPiece) {
        this.noOfPiece = noOfPiece;
    }
    
    public String getFabricCode() {
        return fabricCode;
    }

    public void setFabricCode(String fabricCode) {
        this.fabricCode = fabricCode;
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