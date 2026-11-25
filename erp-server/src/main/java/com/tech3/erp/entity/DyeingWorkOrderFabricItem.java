package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Entity
@Table(name = "dyeing_work_order_fabric_items", schema = "masters")
public class DyeingWorkOrderFabricItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long finishedFabricCodeId;
    private String finishedFabricName;
    private Long greigeFabricCodeId;
    private String greigeFabricName;
    private Double quantity;
    private Double costPerPound;
    private Double totalAmount;
    private Double gstPercent;
    private Double gstAmount;
    private Double finalAmount;
    private Long colorId;
    private String pantone;
    private Double finishedWeight;
    private Integer greigeWidth;
    private Integer reqFinishedWidth;
    private Long uomId;

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

    public Long getFinishedFabricCodeId() { return finishedFabricCodeId; }
    public void setFinishedFabricCodeId(Long finishedFabricCodeId) { this.finishedFabricCodeId = finishedFabricCodeId; }

    public String getFinishedFabricName() { return finishedFabricName; }
    public void setFinishedFabricName(String finishedFabricName) { this.finishedFabricName = finishedFabricName; }

    public Long getGreigeFabricCodeId() { return greigeFabricCodeId; }
    public void setGreigeFabricCodeId(Long greigeFabricCodeId) { this.greigeFabricCodeId = greigeFabricCodeId; }

    public String getGreigeFabricName() { return greigeFabricName; }
    public void setGreigeFabricName(String greigeFabricName) { this.greigeFabricName = greigeFabricName; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getCostPerPound() { return costPerPound; }
    public void setCostPerPound(Double costPerPound) { this.costPerPound = costPerPound; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public Double getGstAmount() { return gstAmount; }
    public void setGstAmount(Double gstAmount) { this.gstAmount = gstAmount; }

    public Double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(Double finalAmount) { this.finalAmount = finalAmount; }

    public Long getColorId() { return colorId; }
    public void setColorId(Long colorId) { this.colorId = colorId; }

    public String getPantone() { return pantone; }
    public void setPantone(String pantone) { this.pantone = pantone; }

    public Double getFinishedWeight() { return finishedWeight; }
    public void setFinishedWeight(Double finishedWeight) { this.finishedWeight = finishedWeight; }

    public Integer getGreigeWidth() { return greigeWidth; }
    public void setGreigeWidth(Integer greigeWidth) { this.greigeWidth = greigeWidth; }

    public Integer getReqFinishedWidth() { return reqFinishedWidth; }
    public void setReqFinishedWidth(Integer reqFinishedWidth) { this.reqFinishedWidth = reqFinishedWidth; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public DyeingWorkOrder getDyeingWorkOrder() { return dyeingWorkOrder; }
    public void setDyeingWorkOrder(DyeingWorkOrder dyeingWorkOrder) { this.dyeingWorkOrder = dyeingWorkOrder; }


    public String getItemNo() { return itemNo; }
    public void setItemNo(String itemNo) { this.itemNo = itemNo; }
}