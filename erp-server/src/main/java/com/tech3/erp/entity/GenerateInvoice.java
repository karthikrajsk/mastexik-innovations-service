package com.tech3.erp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "generate_invoice", schema = "masters")

public class GenerateInvoice {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "manufacture_id")
	    private Long manufactureId;

	    @Column(name = "invoice_date")
	    private LocalDate invoiceDate;

	    @Column(name = "invoice_no", length = 100)
	    private String invoiceNo;

	    @Column(name = "sales_order_id")
	    private Long salesOrderId;

	    @Column(name = "yarn_sales_order_id")
	    private Long yarnSalesOrderId;

	    @Column(name = "company_bank_id")
	    private Long companyBankId;

	    @Column(name = "terms_conditions_id")
	    private Long termsConditionsId;

	    @Column(name = "payment_terms_id")
	    private Long paymentTermsId;

	    @Column(name = "ship_to_id")
	    private Long shipToId;

	    @Column(name = "shipment_mode")
	    private Long shipmentMode;

	    @Column(name = "customer_id")
	    private Long customerId;

	    @Column(name = "consginee_id")
	    private Long consgineeId;

	    @Column(name = "so_no", length = 100)
	    private String soNo;

	    @Column(name = "company_bank_name", length = 200)
	    private String companyBankName;

	    @Column(name = "shipment_mode_name", length = 200)
	    private String shipmentModeName;

	    @Column(name = "terms_conditions_name", length = 200)
	    private String termsConditionsName;

	    @Column(name = "payment_terms_name", length = 200)
	    private String paymentTermsName;

	    @Column(name = "agent_id")
	    private Long agentId;

	    @Column(name = "agent_name", length = 200)
	    private String agentName;

	    @Column(name = "warehouse_id")
	    private Long warehouseId;

	    @Column(name = "invoice_type_id")
	    private Long invoiceTypeId;
	    
	    @Column(name = "invoice_type", length = 100)
	    private String invoiceType;

	    @Column(name = "warehouse_name", length = 200)
	    private String warehouseName;

	    @Column(name = "gst_type", length = 50)
	    private String gstType;

	    @Column(name = "taxable_amount_total")
	    private BigDecimal taxableAmountTotal;

	    @Column(name = "addition_total")
	    private BigDecimal additionTotal;

	    @Column(name = "discount_total")
	    private BigDecimal discountTotal;

	    @Column(name = "net_addition_discount")
	    private BigDecimal netAdditionDiscount;

	    @Column(name = "round_off")
	    private BigDecimal roundOff;

	    @Column(name = "additional_amount")
	    private BigDecimal additional;

	    @Column(name = "less_amount")
	    private BigDecimal less;

	    @Column(name = "buyer_state_code", length = 10)
	    private String buyerStateCode;

	    @Column(name = "company_state_code", length = 10)
	    private String companyStateCode;

	    @Column(name = "gst_percent")
	    private BigDecimal gstPercent;

	    @Column(name = "cgst_percent")
	    private BigDecimal cgstPercent;

	    @Column(name = "sgst_percent")
	    private BigDecimal sgstPercent;

	    @Column(name = "igst_percent")
	    private BigDecimal igstPercent;

	    @Column(name = "ship_via", length = 200)
	    private String shipVia;

	    @Column(name = "manufacturer_json", columnDefinition = "text")
	    private String manufacturer;

	    @Column(name = "buyer_json", columnDefinition = "text")
	    private String buyer;

	    @Column(name = "consignee_json", columnDefinition = "text")
	    private String consignee;

	    @Column(name = "terms_and_conditions_text", columnDefinition = "text")
	    private String termsAndConditions;

	    @Column(name = "payment_terms_text", columnDefinition = "text")
	    private String paymentTerms;

	    @Column(name = "tax_amount")
	    private BigDecimal taxAmount;

	    @Column(name = "total_amount")
	    private BigDecimal totalAmount;

	    @Column(name = "comments", columnDefinition = "text")
	    private String comments;

	    @Column(name = "active_flag")
	    private Boolean activeFlag = true;

	    @Column(name = "company_id")
	    private Long companyId;

	    @Column(name = "is_invoice")
	    private Integer isInvoice = 0;

	    @Column(name = "is_jobwork")
	    private Integer isJobwork;

	    @Column(name = "weaving_work_order_id")
	    private Long weavingWorkOrderId;

	    @Column(name = "wo_no", length = 100)
	    private String woNo;

	    @Column(name = "packing_list_no", length = 100)
	    private String packingListNo;

	    @Column(name = "transport_name_id")
	    private Long transportNameId;

	    @Column(name = "delivery_terms_id")
	    private Long deliveryTermsId;

	    @Column(name = "delivery_note", length = 200)
	    private String deliveryNote;

	    @Column(name = "lr_no", length = 100)
	    private String lrNo;

	    @Column(name = "lr_date")
	    private LocalDate lrDate;

	    @Column(name = "vehicle_no", length = 50)
	    private String vehicleNo;

	    @Column(name = "sizing_order_id")
	    private Long sizingOrderId;

	    @Column(name = "sjo_no", length = 100)
	    private String sjoNo;

	    @Column(name = "is_brushed_piece")
	    private Integer isBrushedPiece = 0;

	    @Column(name = "delivery_note_date")
	    private LocalDate deliveryNoteDate;

	    @Column(name = "destination", length = 200)
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getSjoNo() {
        return sjoNo;
    }

    public void setSjoNo(String sjoNo) {
        this.sjoNo = sjoNo;
    }

    public String getCompanyBankName() {
        return companyBankName;
    }

    public void setCompanyBankName(String companyBankName) {
        this.companyBankName = companyBankName;
    }

    public String getShipmentModeName() {
        return shipmentModeName;
    }

    public void setShipmentModeName(String shipmentModeName) {
        this.shipmentModeName = shipmentModeName;
    }

    public String getTermsConditionsName() {
        return termsConditionsName;
    }

    public void setTermsConditionsName(String termsConditionsName) {
        this.termsConditionsName = termsConditionsName;
    }

    public String getPaymentTermsName() {
        return paymentTermsName;
    }

    public void setPaymentTermsName(String paymentTermsName) {
        this.paymentTermsName = paymentTermsName;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(Long invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public BigDecimal getTaxableAmountTotal() {
        return taxableAmountTotal;
    }

    public void setTaxableAmountTotal(BigDecimal taxableAmountTotal) {
        this.taxableAmountTotal = taxableAmountTotal;
    }

    public BigDecimal getAdditionTotal() {
        return additionTotal;
    }

    public void setAdditionTotal(BigDecimal additionTotal) {
        this.additionTotal = additionTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getNetAdditionDiscount() {
        return netAdditionDiscount;
    }

    public void setNetAdditionDiscount(BigDecimal netAdditionDiscount) {
        this.netAdditionDiscount = netAdditionDiscount;
    }

    public BigDecimal getRoundOff() {
        return roundOff;
    }

    public void setRoundOff(BigDecimal roundOff) {
        this.roundOff = roundOff;
    }

    public BigDecimal getAdditional() {
        return additional;
    }

    public void setAdditional(BigDecimal additional) {
        this.additional = additional;
    }

    public BigDecimal getLess() {
        return less;
    }

    public void setLess(BigDecimal less) {
        this.less = less;
    }

    public String getBuyerStateCode() {
        return buyerStateCode;
    }

    public void setBuyerStateCode(String buyerStateCode) {
        this.buyerStateCode = buyerStateCode;
    }

    public String getCompanyStateCode() {
        return companyStateCode;
    }

    public void setCompanyStateCode(String companyStateCode) {
        this.companyStateCode = companyStateCode;
    }

    public BigDecimal getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(BigDecimal gstPercent) {
        this.gstPercent = gstPercent;
    }

    public BigDecimal getCgstPercent() {
        return cgstPercent;
    }

    public void setCgstPercent(BigDecimal cgstPercent) {
        this.cgstPercent = cgstPercent;
    }

    public BigDecimal getSgstPercent() {
        return sgstPercent;
    }

    public void setSgstPercent(BigDecimal sgstPercent) {
        this.sgstPercent = sgstPercent;
    }

    public BigDecimal getIgstPercent() {
        return igstPercent;
    }

    public void setIgstPercent(BigDecimal igstPercent) {
        this.igstPercent = igstPercent;
    }

    public String getShipVia() {
        return shipVia;
    }

    public void setShipVia(String shipVia) {
        this.shipVia = shipVia;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Integer getIsJobwork() {
        return isJobwork;
    }

    public void setIsJobwork(Integer isJobwork) {
        this.isJobwork = isJobwork;
    }

    public Long getWeavingWorkOrderId() {
        return weavingWorkOrderId;
    }

    public void setWeavingWorkOrderId(Long weavingWorkOrderId) {
        this.weavingWorkOrderId = weavingWorkOrderId;
    }

    public String getWoNo() {
        return woNo;
    }

    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }

    public String getPackingListNo() {
        return packingListNo;
    }

    public void setPackingListNo(String packingListNo) {
        this.packingListNo = packingListNo;
    }

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
