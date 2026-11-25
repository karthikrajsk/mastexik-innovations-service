package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "opening_balance", schema = "masters")
public class OpeningBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Integer slNo;
    private LocalDate date;

    @Column(name = "is_packed")
    private Boolean isPacked;

    private String goodsType;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    
     @Column(name = "uom_id")
    private Long uomId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "opening_balance_no")
    private String openingBalanceNo;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "dc_number")
    private String dcNumber;

    @Column(name = "dc_date")
    private LocalDate dcDate;

    @Column(name = "mill_inv_no")
    private String millInvNo;

    @Column(name = "mill_date")
    private LocalDate millDate;

    @Column(name = "icil_inv_no")
    private String icilInvNo;

    @Column(name = "icil_inv_date")
    private LocalDate icilInvDate;

    // Warehouse ID for storage
//    @Column(name = "warehouse_id", nullable = false)
//    private Long warehouseMaster;
//
//    // Warehouse entity for reference (read-only)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
//    private Warehouse warehouse;

    @OneToMany(mappedBy = "openingBalance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FabricItemEntity> fabricItems = new ArrayList<>();

    @OneToMany(mappedBy = "openingBalance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YarnItemEntity> yarnItems = new ArrayList<>();

    @OneToMany(mappedBy = "openingBalance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningBalanceSummaryItemEntity> openingBalanceSummaryItems = new ArrayList<>();

    @OneToMany(mappedBy = "openingBalance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemGoodsItemEntity> itemGoodsItems = new ArrayList<>();

 public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getOpeningBalanceNo() { return openingBalanceNo; }
    public void setOpeningBalanceNo(String openingBalanceNo) { this.openingBalanceNo = openingBalanceNo; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public String getDcNumber() { return dcNumber; }
    public void setDcNumber(String dcNumber) { this.dcNumber = dcNumber; }

    public LocalDate getDcDate() { return dcDate; }
    public void setDcDate(LocalDate dcDate) { this.dcDate = dcDate; }

    public String getMillInvNo() { return millInvNo; }
    public void setMillInvNo(String millInvNo) { this.millInvNo = millInvNo; }

    public LocalDate getMillDate() { return millDate; }
    public void setMillDate(LocalDate millDate) { this.millDate = millDate; }

    public String getIcilInvNo() { return icilInvNo; }
    public void setIcilInvNo(String icilInvNo) { this.icilInvNo = icilInvNo; }

    public LocalDate getIcilInvDate() { return icilInvDate; }
    public void setIcilInvDate(LocalDate icilInvDate) { this.icilInvDate = icilInvDate; }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

//    public Integer getSlNo() { return slNo; }
//    public void setSlNo(Integer slNo) { this.slNo = slNo; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Boolean getIsPacked() { return isPacked; }
    public void setIsPacked(Boolean isPacked) { this.isPacked = isPacked; }

    public String getGoodsType() { return goodsType; }
    public void setGoodsType(String goodsType) { this.goodsType = goodsType; }

    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }
//    
//    public YarnMaster getWarehouseName() { return warehousename; }
//    public void setWarehouseName(YarnMaster yarnName) { this.warehouseName = warehousename; }
    public List<FabricItemEntity> getFabricItems() { return fabricItems; }
    public void setFabricItems(List<FabricItemEntity> fabricItems) { this.fabricItems = fabricItems; }

    public List<YarnItemEntity> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<YarnItemEntity> yarnItems) { this.yarnItems = yarnItems; }

    public List<OpeningBalanceSummaryItemEntity> getOpeningBalanceSummaryItems() { return openingBalanceSummaryItems; }
    public void setOpeningBalanceSummaryItems(List<OpeningBalanceSummaryItemEntity> openingBalanceSummaryItems) { this.openingBalanceSummaryItems = openingBalanceSummaryItems; }

    public List<ItemGoodsItemEntity> getItemGoodsItems() { return itemGoodsItems; }
    public void setItemGoodsItems(List<ItemGoodsItemEntity> itemGoodsItems) { this.itemGoodsItems = itemGoodsItems; }
}
