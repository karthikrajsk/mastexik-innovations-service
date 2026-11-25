package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_return_fabric_item", schema = "masters")
public class PurchaseReturnFabricItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_return_id")
    @JsonBackReference("purchaseReturnFabricItems")
    private PurchaseReturn purchaseReturn;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "fabric_code")
    private String fabricCode;

    @Column(name = "quality")
    private String quality;

    @Column(name = "return_quantity")
    private String returnQuantity; // pieces/meters (string to match UI)

    @Column(name = "piece_no")
    private String pieceNo;

    @Column(name = "piece_meter")
    private String pieceMeter;

    @Column(name = "net_weight")
    private String netWeight;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "is_packed")
    private String isPacked;

    @Column(name = "purchase_inward_id")
    private Long purchaseInwardId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "is_return")
    private Integer isReturn = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public PurchaseReturn getPurchaseReturn() { return purchaseReturn; }
    public void setPurchaseReturn(PurchaseReturn purchaseReturn) { this.purchaseReturn = purchaseReturn; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public String getFabricCode() { return fabricCode; }
    public void setFabricCode(String fabricCode) { this.fabricCode = fabricCode; }

    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }

    public String getReturnQuantity() { return returnQuantity; }
    public void setReturnQuantity(String returnQuantity) { this.returnQuantity = returnQuantity; }

    public String getPieceNo() { return pieceNo; }
    public void setPieceNo(String pieceNo) { this.pieceNo = pieceNo; }

    public String getPieceMeter() { return pieceMeter; }
    public void setPieceMeter(String pieceMeter) { this.pieceMeter = pieceMeter; }

    public String getNetWeight() { return netWeight; }
    public void setNetWeight(String netWeight) { this.netWeight = netWeight; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getIsPacked() { return isPacked; }
    public void setIsPacked(String isPacked) { this.isPacked = isPacked; }

    public Long getPurchaseInwardId() { return purchaseInwardId; }
    public void setPurchaseInwardId(Long purchaseInwardId) { this.purchaseInwardId = purchaseInwardId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }
}