package com.tech3.erp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "brushing_receive", schema = "masters")
public class BrushingReceive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receive_challan_no", nullable = false)
    private String receiveChallanNo;

    @Column(name = "receive_date", nullable = false)
    private LocalDate receiveDate;



    @Column(name = "remarks")
    private String remarks;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "status")
    private String status = "PENDING";

    @Column(name = "pass_mtr", precision = 19, scale = 3)
    private java.math.BigDecimal passMtr = java.math.BigDecimal.ZERO;

    @Column(name = "is_brushed")
    private Integer isBrushed = 0;

    @Column(name = "is_rejected")
    private Integer isRejected = 0;

    public Integer getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Integer isRejected) {
        this.isRejected = isRejected;
    }

    @Column(name = "weaver_id")
    private Long weaverId;

    @Column(name = "unrolling_meter", precision = 19, scale = 3)
    private java.math.BigDecimal unrollingMeter;

    @Column(name = "unrolling_date")
    private java.time.LocalDate unrollingDate;

    @Column(name = "ref_invoice_no")
    private String refInvoiceNo;

    @Column(name = "ref_po_no")
    private String refPoNo;

    @Column(name = "party_name", length = 255)
    private String partyName;

    @Column(name = "tc", length = 255)
    private String tc;

    @Column(name = "construction", length = 1024)
    private String construction;

    @Column(name = "weave", length = 255)
    private String weave;

    @Column(name = "rolling_date")
    private java.time.LocalDate rollingDate;

    @Column(name = "hfpl_roll_no", length = 255)
    private String hfplRollNo;

    @Column(name = "dispatch_date")
    private java.time.LocalDate dispatchDate;

    @Column(name = "hfpl_invoice_no", length = 255)
    private String hfplInvoiceNo;

    @Column(name = "hfpl_dc_no", length = 255)
    private String hfplDcNo;

    @Column(name = "po_no", length = 255)
    private String poNo;

    @Column(name = "po_date")
    private java.time.LocalDate poDate;

    @OneToMany(mappedBy = "brushingReceive", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<BrushingReceiveDetail> details = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiveChallanNo() {
        return receiveChallanNo;
    }

    public void setReceiveChallanNo(String receiveChallanNo) {
        this.receiveChallanNo = receiveChallanNo;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<BrushingReceiveDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BrushingReceiveDetail> details) {
        this.details = details;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.math.BigDecimal getPassMtr() {
        return passMtr;
    }

    public void setPassMtr(java.math.BigDecimal passMtr) {
        this.passMtr = passMtr;
    }

    public Integer getIsBrushed() {
        return isBrushed;
    }

    public void setIsBrushed(Integer isBrushed) {
        this.isBrushed = isBrushed;
    }

    public Long getWeaverId() {
        return weaverId;
    }

    public void setWeaverId(Long weaverId) {
        this.weaverId = weaverId;
    }

    public java.math.BigDecimal getUnrollingMeter() {
        return unrollingMeter;
    }

    public void setUnrollingMeter(java.math.BigDecimal unrollingMeter) {
        this.unrollingMeter = unrollingMeter;
    }

    @Column(name = "brushing_completed_date")
    private java.time.LocalDate brushingCompletedDate;

    public java.time.LocalDate getUnrollingDate() {
        return unrollingDate;
    }

    public void setUnrollingDate(java.time.LocalDate unrollingDate) {
        this.unrollingDate = unrollingDate;
    }

    public java.time.LocalDate getBrushingCompletedDate() {
        return brushingCompletedDate;
    }

    public void setBrushingCompletedDate(java.time.LocalDate brushingCompletedDate) {
        this.brushingCompletedDate = brushingCompletedDate;
    }

    public String getRefInvoiceNo() {
        return refInvoiceNo;
    }

    public void setRefInvoiceNo(String refInvoiceNo) {
        this.refInvoiceNo = refInvoiceNo;
    }

    public String getRefPoNo() {
        return refPoNo;
    }

    public void setRefPoNo(String refPoNo) {
        this.refPoNo = refPoNo;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getWeave() {
        return weave;
    }

    public void setWeave(String weave) {
        this.weave = weave;
    }

    public java.time.LocalDate getRollingDate() {
        return rollingDate;
    }

    public void setRollingDate(java.time.LocalDate rollingDate) {
        this.rollingDate = rollingDate;
    }

    public String getHfplRollNo() {
        return hfplRollNo;
    }

    public void setHfplRollNo(String hfplRollNo) {
        this.hfplRollNo = hfplRollNo;
    }

    public java.time.LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(java.time.LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getHfplInvoiceNo() {
        return hfplInvoiceNo;
    }

    public void setHfplInvoiceNo(String hfplInvoiceNo) {
        this.hfplInvoiceNo = hfplInvoiceNo;
    }

    public String getHfplDcNo() {
        return hfplDcNo;
    }

    public void setHfplDcNo(String hfplDcNo) {
        this.hfplDcNo = hfplDcNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public java.time.LocalDate getPoDate() {
        return poDate;
    }

    public void setPoDate(java.time.LocalDate poDate) {
        this.poDate = poDate;
    }
}
