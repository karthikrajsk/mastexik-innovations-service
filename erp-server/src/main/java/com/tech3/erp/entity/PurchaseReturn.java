package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Random;

@Entity
@Table(name = "purchase_return", schema = "masters")
public class PurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "purchase_type", length = 20)
    private String purchaseType; // yarn | woven | knitted

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "po_id")
    private Long purchaseOrderId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

       // Store summaries as JSON text to match frontend payload without extra tables
       @Column(name = "yarn_summary_json", columnDefinition = "TEXT")
       private String yarnSummaryJson;

       @Column(name = "fabric_summary_json", columnDefinition = "TEXT")
       private String fabricSummaryJson;

       @Column(name = "return_no")
       private String returnNo;

       @Column(name = "company_id")
       private Long companyId;

       @Column(name = "transport_id")
       private Long transportId;

       @Column(name = "remark", columnDefinition = "TEXT")
       private String remark;

       @Column(name = "vehicle_no", length = 50)
       private String vehicleNo;

       @Column(name = "gross_wt", precision = 18, scale = 3)
       private BigDecimal grossWt;

    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("purchaseReturnYarnItems")
    private Set<PurchaseReturnYarnItem> yarnItems = new HashSet<>();

    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("purchaseReturnFabricItems")
    private Set<PurchaseReturnFabricItem> fabricItems = new HashSet<>();

    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("purchaseReturnItems")
    private Set<PurchaseReturnItem> items = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getPurchaseType() { return purchaseType; }
    public void setPurchaseType(String purchaseType) { this.purchaseType = purchaseType; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Long getPurchaseOrderId() { return purchaseOrderId; }
    public void setPurchaseOrderId(Long purchaseOrderId) { this.purchaseOrderId = purchaseOrderId; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Set<PurchaseReturnYarnItem> getYarnItems() { return yarnItems; }
    public void setYarnItems(Set<PurchaseReturnYarnItem> yarnItems) {
        this.yarnItems = yarnItems;
        if (yarnItems != null) {
            for (PurchaseReturnYarnItem it : yarnItems) {
                it.setPurchaseReturn(this);
            }
        }
    }

    public Set<PurchaseReturnFabricItem> getFabricItems() { return fabricItems; }
    public void setFabricItems(Set<PurchaseReturnFabricItem> fabricItems) {
        this.fabricItems = fabricItems;
        if (fabricItems != null) {
            for (PurchaseReturnFabricItem it : fabricItems) {
                it.setPurchaseReturn(this);
            }
        }
    }

    public Set<PurchaseReturnItem> getItems() { return items; }
    public void setItems(Set<PurchaseReturnItem> items) {
        this.items = items;
        if (items != null) {
            for (PurchaseReturnItem it : items) {
                it.setPurchaseReturn(this);
            }
        }
    }

    public String getYarnSummaryJson() { return yarnSummaryJson; }
    public void setYarnSummaryJson(String yarnSummaryJson) { this.yarnSummaryJson = yarnSummaryJson; }

    public String getFabricSummaryJson() { return fabricSummaryJson; }
    public void setFabricSummaryJson(String fabricSummaryJson) { this.fabricSummaryJson = fabricSummaryJson; }

    // @PrePersist
    // public void prePersist() {
    //     if (this.returnNo == null || this.returnNo.isEmpty()) {
    //         this.returnNo = generateReturnNo();
    //     }
    // }

    // private String generateReturnNo() {
    //     String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    //     int randomNum = new Random().nextInt(9000) + 1000;
    //     return "PR-" + datePart + "-" + randomNum;
    // }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getTransportId() { return transportId; }
    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public BigDecimal getGrossWt() { return grossWt; }
    public void setGrossWt(BigDecimal grossWt) { this.grossWt = grossWt; }
}