package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.tech3.erp.entity.DyeingWorkOrder;

public class DyeingWorkOrderDTO {

    private Long id;
    private String dyeingWorkOrderNo;
    private LocalDate processContactDate;
    private LocalDate deliveryDate;
    private Long vendorId;
    private Long salesOrderNo;
    private Long consigneeId;
    private Long lapDipStatusId;
    private Long firstYardageId;
    private Long processTypeId;
    private Long gstTypeId;
    private String processType;
    private String remarks;
    private Boolean activeFlag;
    private Long companyId;
    private Short isComplete;
    private List<DyeingWorkOrderFabricItemDTO> fabricItems;
    private List<DyeingWorkOrderYarnItemDTO> yarnItems;

    public DyeingWorkOrderDTO() {}

    public DyeingWorkOrderDTO(DyeingWorkOrder entity) {
        this.id = entity.getId();
        this.dyeingWorkOrderNo = entity.getDyeingWorkOrderNo();
        this.processContactDate = entity.getProcessContactDate();
        this.deliveryDate = entity.getDeliveryDate();
        this.vendorId = entity.getVendorId();
        this.salesOrderNo = entity.getSalesOrderNo();
        this.consigneeId = entity.getConsigneeId();
        this.lapDipStatusId = entity.getLapDipStatusId();
        this.firstYardageId = entity.getFirstYardageId();
        this.processTypeId = entity.getProcessTypeId();
        this.gstTypeId = entity.getGstTypeId();
        this.processType = entity.getProcessType();
        this.remarks = entity.getRemarks();
        this.activeFlag = entity.getActiveFlag();
        this.companyId = entity.getCompanyId();
        this.isComplete = entity.getIsComplete();

        // Map fabric items
        this.fabricItems = entity.getFabricItems().stream()
                .map(DyeingWorkOrderFabricItemDTO::new)
                .collect(Collectors.toList());
        
        // Map yarn items
        this.yarnItems = entity.getYarnItems().stream()
                .map(DyeingWorkOrderYarnItemDTO::new)
                .collect(Collectors.toList());
    }

    // Getters and Setters (add fabricItems and yarnItems)
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDyeingWorkOrderNo() { return dyeingWorkOrderNo; }
    public void setDyeingWorkOrderNo(String dyeingWorkOrderNo) { this.dyeingWorkOrderNo = dyeingWorkOrderNo; }

    public LocalDate getProcessContactDate() { return processContactDate; }
    public void setProcessContactDate(LocalDate processContactDate) { this.processContactDate = processContactDate; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Long getSalesOrderNo() { return salesOrderNo; }
    public void setSalesOrderNo(Long salesOrderNo) { this.salesOrderNo = salesOrderNo; }

    public Long getConsigneeId() { return consigneeId; }
    public void setConsigneeId(Long consigneeId) { this.consigneeId = consigneeId; }

    public Long getLapDipStatusId() { return lapDipStatusId; }
    public void setLapDipStatusId(Long lapDipStatusId) { this.lapDipStatusId = lapDipStatusId; }

    public Long getFirstYardageId() { return firstYardageId; }
    public void setFirstYardageId(Long firstYardageId) { this.firstYardageId = firstYardageId; }

    public Long getProcessTypeId() { return processTypeId; }
    public void setProcessTypeId(Long processTypeId) { this.processTypeId = processTypeId; }

    public Long getGstTypeId() { return gstTypeId; }
    public void setGstTypeId(Long gstTypeId) { this.gstTypeId = gstTypeId; }

    public String getProcessType() { return processType; }
    public void setProcessType(String processType) { this.processType = processType; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Short getIsComplete() { return isComplete; }
    public void setIsComplete(Short isComplete) { this.isComplete = isComplete; }

    public List<DyeingWorkOrderFabricItemDTO> getFabricItems() { return fabricItems; }
    public void setFabricItems(List<DyeingWorkOrderFabricItemDTO> fabricItems) { this.fabricItems = fabricItems; }

    public List<DyeingWorkOrderYarnItemDTO> getYarnItems() { return yarnItems; }
    public void setYarnItems(List<DyeingWorkOrderYarnItemDTO> yarnItems) { this.yarnItems = yarnItems; }
}