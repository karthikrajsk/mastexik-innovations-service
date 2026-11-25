package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Entity
@Table(name = "dyeing_work_order_yarn_items", schema = "masters")
public class DyeingWorkOrderYarnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long finishedYarnCodeId;
    private String finishedYarnName;
    private Long greigeYarnCodeId;
    private String greigeYarnName;
    private Double quantity;
    private Double costPerPound;
    private Double totalAmount;
    private Long colorId;
    private String pantone;
    private Double finishedWeight;
    private Long uomId;
    private Double noOfPiece;

    private Double gstPercent;
    private Double gstAmount;
    private Double finalAmount;
    @Column(name = "dispatch_quantity")
    private Double dispatchQuantity;

    @Column(columnDefinition = "text")
    private String remarks;

    private Boolean activeFlag = true;

    @Column(name = "item_no")
    private String itemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dyeing_work_order_id")
    private DyeingWorkOrder dyeingWorkOrder;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFinishedYarnCodeId() { return finishedYarnCodeId; }
    public void setFinishedYarnCodeId(Long finishedYarnCodeId) { this.finishedYarnCodeId = finishedYarnCodeId; }

    public String getFinishedYarnName() { return finishedYarnName; }
    public void setFinishedYarnName(String finishedYarnName) { this.finishedYarnName = finishedYarnName; }

    public Long getGreigeYarnCodeId() { return greigeYarnCodeId; }
    public void setGreigeYarnCodeId(Long greigeYarnCodeId) { this.greigeYarnCodeId = greigeYarnCodeId; }

    public String getGreigeYarnName() { return greigeYarnName; }
    public void setGreigeYarnName(String greigeYarnName) { this.greigeYarnName = greigeYarnName; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getCostPerPound() { return costPerPound; }
    public void setCostPerPound(Double costPerPound) { this.costPerPound = costPerPound; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Long getColorId() { return colorId; }
    public void setColorId(Long colorId) { this.colorId = colorId; }

    public String getPantone() { return pantone; }
    public void setPantone(String pantone) { this.pantone = pantone; }

    public Double getFinishedWeight() { return finishedWeight; }
    public void setFinishedWeight(Double finishedWeight) { this.finishedWeight = finishedWeight; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Double getNoOfPiece() { return noOfPiece; }
    public void setNoOfPiece(Double noOfPiece) { this.noOfPiece = noOfPiece; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public Double getGstAmount() { return gstAmount; }
    public void setGstAmount(Double gstAmount) { this.gstAmount = gstAmount; }

    public Double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(Double finalAmount) { this.finalAmount = finalAmount; }

    public Double getDispatchQuantity() { return dispatchQuantity; }
    public void setDispatchQuantity(Double dispatchQuantity) { this.dispatchQuantity = dispatchQuantity; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public DyeingWorkOrder getDyeingWorkOrder() { return dyeingWorkOrder; }
    public void setDyeingWorkOrder(DyeingWorkOrder dyeingWorkOrder) { this.dyeingWorkOrder = dyeingWorkOrder; }

    // @PrePersist
    // public void prePersist() {
    //     if (this.itemNo == null || this.itemNo.isEmpty()) {
    //         this.itemNo = generateItemNo();
    //     }
    // }

    // private String generateItemNo() {
    //     String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    //     int randomNum = new Random().nextInt(9000) + 1000;
    //     return "DWYI-" + datePart + "-" + randomNum;
    // }

    public String getItemNo() { return itemNo; }
    public void setItemNo(String itemNo) { this.itemNo = itemNo; }
}