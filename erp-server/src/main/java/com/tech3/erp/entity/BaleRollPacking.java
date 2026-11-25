package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "bale_roll_packing", schema = "masters")
public class BaleRollPacking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "packing_date")
    private Date packingDate;

    @Column(name = "packing_type", length = 20)
    private String packingType; // Roll | Bale

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "weaving_work_order_id")
    private Long weavingWorkOrderId;

    @Column(name = "is_jobwork")
    private Integer isJobwork = 0;

    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "fabric_code_id")
    private Long fabricCodeId;

    @Column(name = "fabric_quality_id")
    private Long fabricQualityId;

    @Column(name = "uom_id")
    private Long uomId; // meters/yards

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "grade_id")
    private Long gradeId; // mandatory in UI

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "packing_no", length = 255)
    private String packingNo; // generated unique number

    // Additional optional header totals and number
    @Column(name = "roll_or_bale_no", length = 255)
    private String rollOrBaleNo;

    @Column(name = "total_pieces")
    private Long totalPieces;

    @Column(name = "total_meters", precision = 19, scale = 3)
    private BigDecimal totalMeters;

    @Column(name = "total_yards", precision = 19, scale = 3)
    private BigDecimal totalYards;

    @Column(name = "total_net", precision = 19, scale = 3)
    private BigDecimal totalNet;

    @Column(name = "total_avg", precision = 19, scale = 3)
    private BigDecimal totalAvg;

    @Column(name = "total_gross", precision = 19, scale = 3)
    private BigDecimal totalGross;

    @Column(name = "is_factory_roll")
    private Integer isFactoryRoll = 0;

    @Column(name = "is_dispatched")
    private Integer isDispatched = 0;

    @Column(name = "is_roll_scanned")
    private Integer isRollScanned = 0;

    @Column(name = "is_brush_checked")
    private Integer isBrushChecked = 0;

    @Column(name = "vendor_id")
    private Long vendorId;

    @OneToMany(mappedBy = "baleRollPacking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BaleRollPackingItem> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.packingNo == null || this.packingNo.isEmpty()) {
            this.packingNo = generatePackingNo();
        }
    }

    private String generatePackingNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = new Random().nextInt(9000) + 1000;
        return "BRP-" + datePart + "-" + randomNum;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getPackingDate() { return packingDate; }
    public void setPackingDate(Date packingDate) { this.packingDate = packingDate; }

    public String getPackingType() { return packingType; }
    public void setPackingType(String packingType) { this.packingType = packingType; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public Integer getIsJobwork() { return isJobwork; }
    public void setIsJobwork(Integer isJobwork) { this.isJobwork = isJobwork; }

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }

    public Long getFabricCodeId() { return fabricCodeId; }
    public void setFabricCodeId(Long fabricCodeId) { this.fabricCodeId = fabricCodeId; }

    public Long getFabricQualityId() { return fabricQualityId; }
    public void setFabricQualityId(Long fabricQualityId) { this.fabricQualityId = fabricQualityId; }

    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Long getGradeId() { return gradeId; }
    public void setGradeId(Long gradeId) { this.gradeId = gradeId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getPackingNo() { return packingNo; }
    public void setPackingNo(String packingNo) { this.packingNo = packingNo; }

    public String getRollOrBaleNo() { return rollOrBaleNo; }
    public void setRollOrBaleNo(String rollOrBaleNo) { this.rollOrBaleNo = rollOrBaleNo; }

    public Long getTotalPieces() { return totalPieces; }
    public void setTotalPieces(Long totalPieces) { this.totalPieces = totalPieces; }

    public BigDecimal getTotalMeters() { return totalMeters; }
    public void setTotalMeters(BigDecimal totalMeters) { this.totalMeters = totalMeters; }

    public BigDecimal getTotalYards() { return totalYards; }
    public void setTotalYards(BigDecimal totalYards) { this.totalYards = totalYards; }

    public BigDecimal getTotalNet() { return totalNet; }
    public void setTotalNet(BigDecimal totalNet) { this.totalNet = totalNet; }

    public BigDecimal getTotalAvg() { return totalAvg; }
    public void setTotalAvg(BigDecimal totalAvg) { this.totalAvg = totalAvg; }

    public BigDecimal getTotalGross() { return totalGross; }
    public void setTotalGross(BigDecimal totalGross) { this.totalGross = totalGross; }

    public List<BaleRollPackingItem> getItems() { return items; }
    public void setItems(List<BaleRollPackingItem> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
    }

    public Integer getIsFactoryRoll() { return isFactoryRoll; }
    public void setIsFactoryRoll(Integer isFactoryRoll) { this.isFactoryRoll = isFactoryRoll; }

    public Integer getIsDispatched() { return isDispatched; }
    public void setIsDispatched(Integer isDispatched) { this.isDispatched = isDispatched; }

    public Integer getIsRollScanned() { return isRollScanned; }
    public void setIsRollScanned(Integer isRollScanned) { this.isRollScanned = isRollScanned; }

    public Integer getIsBrushChecked() {
        return isBrushChecked;
    }

    public void setIsBrushChecked(Integer isBrushChecked) {
        this.isBrushChecked = isBrushChecked;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
}