package com.tech3.erp.service;

public class WeavingWorkOrderUpdatedEvent {
    private final Long workOrderId;
    private final Long weavingContractId;
    private final Long orderTypeId;

    public WeavingWorkOrderUpdatedEvent(Long workOrderId, Long weavingContractId, Long orderTypeId) {
        this.workOrderId = workOrderId;
        this.weavingContractId = weavingContractId;
        this.orderTypeId = orderTypeId;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public Long getWeavingContractId() {
        return weavingContractId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }
}
