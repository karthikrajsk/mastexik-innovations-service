package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class WeavingWorkOrderDTO {
    private Long id;
    private String weavingWorkOrderNo;
    private LocalDate orderDate;
    private Long weavingContractId;
    private Long orderTypeId;
    private Long buyerId;
    private Long consigneeId;
    private Integer gstTypeId;
    private Long termsConditionsId;
    private Long paymentTermsId;
    private Long packingTypeId; // confirmed: packingTypeId, not packingType
    private Long factoryCompanyId;
    private Long tradingCompanyId;
    private String remarks;
    private Boolean activeFlag = true;
    private Boolean isCompleted;
    private Short isApproved;
    private Short isOrderIdMapped;
    private LocalDate approveDate;
    private String comments;
    private Long userId;

    private List<WeavingWorkOrderItemDTO> items;
    private List<WeavingWorkOrderYarnRequirementDTO> yarnRequirements;
    private List<WeavingWorkOrderDetailDTO> orderDetails;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public Long getWeavingContractId() { return weavingContractId; }
    public void setWeavingContractId(Long weavingContractId) { this.weavingContractId = weavingContractId; }

    public Long getOrderTypeId() { return orderTypeId; }
    public void setOrderTypeId(Long orderTypeId) { this.orderTypeId = orderTypeId; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getConsigneeId() { return consigneeId; }
    public void setConsigneeId(Long consigneeId) { this.consigneeId = consigneeId; }

    public Integer getGstTypeId() { return gstTypeId; }
    public void setGstTypeId(Integer gstTypeId) { this.gstTypeId = gstTypeId; }

    public Long getTermsConditionsId() { return termsConditionsId; }
    public void setTermsConditionsId(Long termsConditionsId) { this.termsConditionsId = termsConditionsId; }

    public Long getPaymentTermsId() { return paymentTermsId; }
    public void setPaymentTermsId(Long paymentTermsId) { this.paymentTermsId = paymentTermsId; }

    public Long getPackingTypeId() { return packingTypeId; }
    public void setPackingTypeId(Long packingTypeId) { this.packingTypeId = packingTypeId; }

    public Long getFactoryCompanyId() { return factoryCompanyId; }
    public void setFactoryCompanyId(Long factoryCompanyId) { this.factoryCompanyId = factoryCompanyId; }

    public Long getTradingCompanyId() { return tradingCompanyId; }
    public void setTradingCompanyId(Long tradingCompanyId) { this.tradingCompanyId = tradingCompanyId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    public Short getIsApproved() { return isApproved; }
    public void setIsApproved(Short isApproved) { this.isApproved = isApproved; }

    public Short getIsOrderIdMapped() { return isOrderIdMapped; }
    public void setIsOrderIdMapped(Short isOrderIdMapped) { this.isOrderIdMapped = isOrderIdMapped; }

    public LocalDate getApproveDate() { return approveDate; }
    public void setApproveDate(LocalDate approveDate) { this.approveDate = approveDate; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

     public String getWeavingWorkOrderNo() {
        return weavingWorkOrderNo;
    }

    public void setWeavingWorkOrderNo(String weavingWorkOrderNo) {
        this.weavingWorkOrderNo = weavingWorkOrderNo;
    }

    public List<WeavingWorkOrderItemDTO> getItems() { return items; }
    public void setItems(List<WeavingWorkOrderItemDTO> items) { this.items = items; }

    public List<WeavingWorkOrderYarnRequirementDTO> getYarnRequirements() { return yarnRequirements; }
    public void setYarnRequirements(List<WeavingWorkOrderYarnRequirementDTO> yarnRequirements) { this.yarnRequirements = yarnRequirements; }

    public List<WeavingWorkOrderDetailDTO> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<WeavingWorkOrderDetailDTO> orderDetails) { this.orderDetails = orderDetails; }
}