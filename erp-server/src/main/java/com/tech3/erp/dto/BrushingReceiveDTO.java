package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

public class BrushingReceiveDTO {
    private Long id;
    private String receiveChallanNo;
    private LocalDate receiveDate;
    private String remarks;
    private Long companyId;
    private List<BrushingReceiveDetailDTO> details;
    
    private String orderType;
    private Long buyerId;
    private Long fabricMasterId;
    private String status;
    private BigDecimal passMtr;
    private Integer isBrushed;
    private Integer isRejected = 0;

    private Long weaverId;
    private BigDecimal unrollingMeter;
    private LocalDate unrollingDate;
    private String refInvoiceNo;
    private String refPoNo;

    public Integer getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Integer isRejected) {
        this.isRejected = isRejected;
    }

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

    public List<BrushingReceiveDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<BrushingReceiveDetailDTO> details) {
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

    public BigDecimal getPassMtr() {
        return passMtr;
    }

    public void setPassMtr(BigDecimal passMtr) {
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

    public BigDecimal getUnrollingMeter() {
        return unrollingMeter;
    }

    public void setUnrollingMeter(BigDecimal unrollingMeter) {
        this.unrollingMeter = unrollingMeter;
    }

    private LocalDate brushingCompletedDate;

    public LocalDate getUnrollingDate() {
        return unrollingDate;
    }

    public void setUnrollingDate(LocalDate unrollingDate) {
        this.unrollingDate = unrollingDate;
    }

    public LocalDate getBrushingCompletedDate() {
        return brushingCompletedDate;
    }

    public void setBrushingCompletedDate(LocalDate brushingCompletedDate) {
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
}
