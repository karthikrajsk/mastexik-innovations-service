package com.tech3.erp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weaving_yarn_outward", schema = "masters")
public class WeavingYarnOutward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outward_no", length = 100)
    private String outwardNo;

    @Column(name = "outward_date")
    private LocalDate outwardDate;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "weaving_job_work_order_id")
    private Long weavingJobWorkOrderId;

    @Column(name = "weaving_contract_id")
    private Long weavingContractId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "trading_company_id")
    private Long tradingCompanyId;

    @Column(name = "factory_company_id")
    private Long factoryCompanyId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "buyer_name", length = 150)
    private String buyerName;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "vendor_name", length = 150)
    private String vendorName;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "yarn_name", length = 150)
    private String yarnName;

    @Column(name = "vehicle_no", length = 100)
    private String vehicleNo;

    @Column(name = "transport_id")
    private Long transportId;

    @Column(name = "transport_name", length = 150)
    private String transportName;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "issued_bags", precision = 38, scale = 3)
    private BigDecimal issuedBags;

    @Column(name = "issued_quantity", precision = 38, scale = 3)
    private BigDecimal issuedQuantity;

    @Column(name = "inward_type", length = 100)
    private String inwardType = "WEFT_YARN_RETURN";

    @OneToMany(mappedBy = "weavingYarnOutward", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WeavingYarnOutwardDetail> yarnIssueDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutwardNo() {
        return outwardNo;
    }

    public void setOutwardNo(String outwardNo) {
        this.outwardNo = outwardNo;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getWeavingJobWorkOrderId() {
        return weavingJobWorkOrderId;
    }

    public void setWeavingJobWorkOrderId(Long weavingJobWorkOrderId) {
        this.weavingJobWorkOrderId = weavingJobWorkOrderId;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public void setWeavingContractId(Long weavingContractId) {
        this.weavingContractId = weavingContractId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getTradingCompanyId() {
        return tradingCompanyId;
    }

    public void setTradingCompanyId(Long tradingCompanyId) {
        this.tradingCompanyId = tradingCompanyId;
    }

    public Long getFactoryCompanyId() {
        return factoryCompanyId;
    }

    public void setFactoryCompanyId(Long factoryCompanyId) {
        this.factoryCompanyId = factoryCompanyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getIssuedBags() {
        return issuedBags;
    }

    public void setIssuedBags(BigDecimal issuedBags) {
        this.issuedBags = issuedBags;
    }

    public BigDecimal getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(BigDecimal issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public String getInwardType() {
        return inwardType;
    }

    public void setInwardType(String inwardType) {
        this.inwardType = inwardType;
    }

    public List<WeavingYarnOutwardDetail> getYarnIssueDetails() {
        return yarnIssueDetails;
    }

    public void setYarnIssueDetails(List<WeavingYarnOutwardDetail> yarnIssueDetails) {
        this.yarnIssueDetails.clear();
        if (yarnIssueDetails != null) {
            yarnIssueDetails.forEach(this::addDetail);
        }
    }

    public void addDetail(WeavingYarnOutwardDetail detail) {
        yarnIssueDetails.add(detail);
        detail.setWeavingYarnOutward(this);
    }

    public void removeDetail(WeavingYarnOutwardDetail detail) {
        yarnIssueDetails.remove(detail);
        detail.setWeavingYarnOutward(null);
    }
}
