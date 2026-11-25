package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GenerateInvoiceDTO {
    private Long id;
    private Long manufactureId;
    private LocalDate invoiceDate;
    private String invoiceNo;
    private String soNo;
    private String sjoNo;
    private Long salesOrderId;
    private Long sizingOrderId;
    private Long yarnSalesOrderId;
    private Long companyBankId;
    private Long termsConditionsId;
    private Long paymentTermsId;
    private Long shipToId;
    private Long shipmentMode;
    private Long customerId;
    @JsonAlias({"consigneeId"})
    private Long consgineeId;
    private Long agentId;
    private Long invoiceTypeId;
    private String invoiceType;
    private Long warehouseId;
    private String gstType;
    private BigDecimal taxableAmountTotal;
    private BigDecimal taxAmount;
    private BigDecimal additionTotal;
    private BigDecimal discountTotal;
    private BigDecimal netAdditionDiscount;
    private BigDecimal roundOff;
    private BigDecimal totalAmount;
    private BigDecimal additional;
    private BigDecimal less;
    private String comments;
    private Boolean activeFlag;
    private String shipVia;
    private String companyBankName;
    private String shipmentModeName;
    private String termsConditionsName;
    private String paymentTermsName;
    private String agentName;
    private String warehouseName;
    private String buyerStateCode;
    private String companyStateCode;
    private BigDecimal gstPercent;
    private BigDecimal cgstPercent;
    private BigDecimal sgstPercent;
    private BigDecimal igstPercent;
    private Long companyId;
    private Integer isInvoice;
    private Integer isJobwork;
    private Long weavingWorkOrderId;
    private String woNo;
    private String packingListNo;
    private Long transportNameId;
    private Long deliveryTermsId;
    private String deliveryNote;
    private String lrNo;
    private LocalDate lrDate;
    private String vehicleNo;
    private List<InvoiceItemDTO> items;
    private List<InvoiceSizingSalesDTO> sizingSalesItems;
    private List<AddDiscItemDTO> addDiscItems;
    private List<AddDiscItemDTO> addDiscDrafts;
    private Integer isBrushedPiece;
    private LocalDate deliveryNoteDate;
    private String destination;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Long manufactureId) {
        this.manufactureId = manufactureId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getSizingOrderId() {
        return sizingOrderId;
    }

    public void setSizingOrderId(Long sizingOrderId) {
        this.sizingOrderId = sizingOrderId;
    }

    public Long getYarnSalesOrderId() {
        return yarnSalesOrderId;
    }

    public void setYarnSalesOrderId(Long yarnSalesOrderId) {
        this.yarnSalesOrderId = yarnSalesOrderId;
    }

    public Long getCompanyBankId() {
        return companyBankId;
    }

    public void setCompanyBankId(Long companyBankId) {
        this.companyBankId = companyBankId;
    }

    public Long getTermsConditionsId() {
        return termsConditionsId;
    }

    public void setTermsConditionsId(Long termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public Long getPaymentTermsId() {
        return paymentTermsId;
    }

    public void setPaymentTermsId(Long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long getShipToId() {
        return shipToId;
    }

    public void setShipToId(Long shipToId) {
        this.shipToId = shipToId;
    }

    public Long getShipmentMode() {
        return shipmentMode;
    }

    public void setShipmentMode(Long shipmentMode) {
        this.shipmentMode = shipmentMode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getConsgineeId() {
        return consgineeId;
    }

    public void setConsgineeId(Long consgineeId) {
        this.consgineeId = consgineeId;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    public List<InvoiceItemDTO> getItems() { return items; }
    public void setItems(List<InvoiceItemDTO> items) { this.items = items; }

    public List<InvoiceSizingSalesDTO> getSizingSalesItems() {
        return sizingSalesItems;
    }

    public void setSizingSalesItems(List<InvoiceSizingSalesDTO> sizingSalesItems) {
        this.sizingSalesItems = sizingSalesItems;
    }

    public String getSoNo() { return soNo; }
    public void setSoNo(String soNo) { this.soNo = soNo; }

    public String getSjoNo() { return sjoNo; }
    public void setSjoNo(String sjoNo) { this.sjoNo = sjoNo; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public Long getInvoiceTypeId() { return invoiceTypeId; }
    public void setInvoiceTypeId(Long invoiceTypeId) { this.invoiceTypeId = invoiceTypeId; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public String getGstType() { return gstType; }
    public void setGstType(String gstType) { this.gstType = gstType; }

    public BigDecimal getTaxableAmountTotal() { return taxableAmountTotal; }
    public void setTaxableAmountTotal(BigDecimal taxableAmountTotal) { this.taxableAmountTotal = taxableAmountTotal; }

    public BigDecimal getAdditionTotal() { return additionTotal; }
    public void setAdditionTotal(BigDecimal additionTotal) { this.additionTotal = additionTotal; }

    public BigDecimal getDiscountTotal() { return discountTotal; }
    public void setDiscountTotal(BigDecimal discountTotal) { this.discountTotal = discountTotal; }

    public BigDecimal getNetAdditionDiscount() { return netAdditionDiscount; }
    public void setNetAdditionDiscount(BigDecimal netAdditionDiscount) { this.netAdditionDiscount = netAdditionDiscount; }

    public BigDecimal getRoundOff() { return roundOff; }
    public void setRoundOff(BigDecimal roundOff) { this.roundOff = roundOff; }

    public BigDecimal getAdditional() { return additional; }
    public void setAdditional(BigDecimal additional) { this.additional = additional; }

    public BigDecimal getLess() { return less; }
    public void setLess(BigDecimal less) { this.less = less; }

    public String getShipVia() { return shipVia; }
    public void setShipVia(String shipVia) { this.shipVia = shipVia; }

    public String getCompanyBankName() { return companyBankName; }
    public void setCompanyBankName(String companyBankName) { this.companyBankName = companyBankName; }

    public String getShipmentModeName() { return shipmentModeName; }
    public void setShipmentModeName(String shipmentModeName) { this.shipmentModeName = shipmentModeName; }

    public String getTermsConditionsName() { return termsConditionsName; }
    public void setTermsConditionsName(String termsConditionsName) { this.termsConditionsName = termsConditionsName; }

    public String getPaymentTermsName() { return paymentTermsName; }
    public void setPaymentTermsName(String paymentTermsName) { this.paymentTermsName = paymentTermsName; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    public String getBuyerStateCode() { return buyerStateCode; }
    public void setBuyerStateCode(String buyerStateCode) { this.buyerStateCode = buyerStateCode; }

    public String getCompanyStateCode() { return companyStateCode; }
    public void setCompanyStateCode(String companyStateCode) { this.companyStateCode = companyStateCode; }

    public BigDecimal getGstPercent() { return gstPercent; }
    public void setGstPercent(BigDecimal gstPercent) { this.gstPercent = gstPercent; }

    public BigDecimal getCgstPercent() { return cgstPercent; }
    public void setCgstPercent(BigDecimal cgstPercent) { this.cgstPercent = cgstPercent; }

    public BigDecimal getSgstPercent() { return sgstPercent; }
    public void setSgstPercent(BigDecimal sgstPercent) { this.sgstPercent = sgstPercent; }

    public BigDecimal getIgstPercent() { return igstPercent; }
    public void setIgstPercent(BigDecimal igstPercent) { this.igstPercent = igstPercent; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<AddDiscItemDTO> getAddDiscItems() { return addDiscItems; }
    public void setAddDiscItems(List<AddDiscItemDTO> addDiscItems) { this.addDiscItems = addDiscItems; }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<AddDiscItemDTO> getAddDiscDrafts() { return addDiscDrafts; }
    public void setAddDiscDrafts(List<AddDiscItemDTO> addDiscDrafts) { this.addDiscDrafts = addDiscDrafts; }

    public Integer getIsInvoice() { return isInvoice; }
    public void setIsInvoice(Integer isInvoice) { this.isInvoice = isInvoice; }

    public Integer getIsJobwork() { return isJobwork; }
    public void setIsJobwork(Integer isJobwork) { this.isJobwork = isJobwork; }

    public Long getWeavingWorkOrderId() { return weavingWorkOrderId; }
    public void setWeavingWorkOrderId(Long weavingWorkOrderId) { this.weavingWorkOrderId = weavingWorkOrderId; }

    public String getWoNo() { return woNo; }
    public void setWoNo(String woNo) { this.woNo = woNo; }

    public String getPackingListNo() { return packingListNo; }
    public void setPackingListNo(String packingListNo) { this.packingListNo = packingListNo; }

    public Long getTransportNameId() {
        return transportNameId;
    }

    public void setTransportNameId(Long transportNameId) {
        this.transportNameId = transportNameId;
    }

    public Long getDeliveryTermsId() {
        return deliveryTermsId;
    }

    public void setDeliveryTermsId(Long deliveryTermsId) {
        this.deliveryTermsId = deliveryTermsId;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public String getLrNo() {
        return lrNo;
    }

    public void setLrNo(String lrNo) {
        this.lrNo = lrNo;
    }

    public LocalDate getLrDate() {
        return lrDate;
    }

    public void setLrDate(LocalDate lrDate) {
        this.lrDate = lrDate;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getIsBrushedPiece() {
        return isBrushedPiece;
    }

    public void setIsBrushedPiece(Integer isBrushedPiece) {
        this.isBrushedPiece = isBrushedPiece;
    }

    public LocalDate getDeliveryNoteDate() {
        return deliveryNoteDate;
    }

    public void setDeliveryNoteDate(LocalDate deliveryNoteDate) {
        this.deliveryNoteDate = deliveryNoteDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
