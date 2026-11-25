package com.tech3.erp.service;

import com.tech3.erp.dto.DyeingWorkOrderDTO;
import com.tech3.erp.dto.DyeingWorkOrderFabricItemDTO;
import com.tech3.erp.dto.DyeingWorkOrderYarnItemDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.DyeingWorkOrder;
import com.tech3.erp.entity.DyeingWorkOrderFabricItem;
import com.tech3.erp.entity.DyeingWorkOrderYarnItem;
import com.tech3.erp.entity.FabricDispatchForDyeing;
import com.tech3.erp.entity.FinishFabricReceive;
import com.tech3.erp.exception.ApiException;
import com.tech3.erp.repository.DyeingWorkOrderFabricItemRepository;
import com.tech3.erp.repository.DyeingWorkOrderRepository;
import com.tech3.erp.repository.DyeingWorkOrderYarnItemRepository;
import com.tech3.erp.repository.FabricDispatchForDyeingRepository;
import com.tech3.erp.repository.FinishFabricReceiveRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.service.ReferenceValidationService;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.util.ProcessTypeUtil;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DyeingWorkOrderService {

    private final DyeingWorkOrderRepository dyeingWorkOrderRepository;
    private final DyeingWorkOrderFabricItemRepository fabricItemRepository;
    private final DyeingWorkOrderYarnItemRepository yarnItemRepository;
    private final FabricDispatchForDyeingRepository fabricDispatchRepository;
    private final FinishFabricReceiveRepository finishFabricReceiveRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final ReferenceValidationService referenceValidationService;

    public DyeingWorkOrderService(DyeingWorkOrderRepository dyeingWorkOrderRepository,
                                 DyeingWorkOrderFabricItemRepository fabricItemRepository,
                                 FabricDispatchForDyeingRepository fabricDispatchRepository,
                                 DyeingWorkOrderYarnItemRepository yarnItemRepository,
                                 FinishFabricReceiveRepository finishFabricReceiveRepository,
                                 SalesOrderRepository salesOrderRepository,
                                 ReferenceValidationService referenceValidationService) {
        this.dyeingWorkOrderRepository = dyeingWorkOrderRepository;
        this.fabricItemRepository = fabricItemRepository;
        this.fabricDispatchRepository = fabricDispatchRepository;
        this.yarnItemRepository = yarnItemRepository;
        this.finishFabricReceiveRepository = finishFabricReceiveRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.referenceValidationService = referenceValidationService;
    }

    @Transactional
    public DyeingWorkOrderDTO createDyeingWorkOrder(DyeingWorkOrderDTO dto) {
        DyeingWorkOrder entity = new DyeingWorkOrder();
        mapDtoToEntity(dto, entity);

        // Map and attach items based on process type
        if ("FABRIC".equals(dto.getProcessType()) && dto.getFabricItems() != null && !dto.getFabricItems().isEmpty()) {
            List<DyeingWorkOrderFabricItem> fabricItems = dto.getFabricItems().stream().map(i -> {
                DyeingWorkOrderFabricItem item = new DyeingWorkOrderFabricItem();
                mapFabricItemDtoToEntity(i, item);
                item.setDyeingWorkOrder(entity);
                return item;
            }).collect(Collectors.toList());
            entity.setFabricItems(fabricItems);
        } else if ("YARN".equals(dto.getProcessType()) && dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
            List<DyeingWorkOrderYarnItem> yarnItems = dto.getYarnItems().stream().map(i -> {
                DyeingWorkOrderYarnItem item = new DyeingWorkOrderYarnItem();
                mapYarnItemDtoToEntity(i, item);
                item.setDyeingWorkOrder(entity);
                return item;
            }).collect(Collectors.toList());
            entity.setYarnItems(yarnItems);
        }

        // Save work order to get ID
        DyeingWorkOrder finalSaved = dyeingWorkOrderRepository.save(entity);

        return mapToDTO(finalSaved);
    }

    public List<DyeingWorkOrderDTO> getAllDyeingWorkOrders() {
        return dyeingWorkOrderRepository.findAll().stream()
                .sorted(Comparator.comparing(DyeingWorkOrder::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<DyeingWorkOrderDTO> getAllDyeingWorkOrdersWithPagination(Long offset, Long limit) {
        List<DyeingWorkOrderDTO> allOrders = dyeingWorkOrderRepository.findAll().stream()
                .sorted(Comparator.comparing(DyeingWorkOrder::getId).reversed())
                 .map(this::mapToDTO)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allOrders, offset, limit);
    }

    public List<DyeingWorkOrderDTO> getAllDyeingWorkOrdersByType(String processType) {
        String validType = ProcessTypeUtil.getValidProcessType(processType);
        return dyeingWorkOrderRepository.findAll().stream()
                .filter(order -> validType.equals(order.getProcessType()))
                .sorted(Comparator.comparing(DyeingWorkOrder::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<DyeingWorkOrderDTO> getAllDyeingWorkOrdersByTypeWithPagination(String processType, Long offset, Long limit) {
        String validType = ProcessTypeUtil.getValidProcessType(processType);
        List<DyeingWorkOrderDTO> allOrders = dyeingWorkOrderRepository.findAll().stream()
                .filter(order -> validType.equals(order.getProcessType()))
                .sorted(Comparator.comparing(DyeingWorkOrder::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allOrders, offset, limit);
    }

    public PaginatedResponse<DyeingWorkOrderDTO> getAllDyeingWorkOrdersByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        List<DyeingWorkOrderDTO> allOrders = dyeingWorkOrderRepository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(DyeingWorkOrder::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(allOrders, offset, limit);
    }

    public PaginatedResponse<DyeingWorkOrderDTO> getAllDyeingWorkOrdersByCompanyAndTypeWithPagination(Long companyId, String processType, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        String validType = ProcessTypeUtil.getValidProcessType(processType);
        List<DyeingWorkOrderDTO> allOrders = dyeingWorkOrderRepository.findByCompanyIdAndProcessType(companyId, validType).stream()
                .sorted(Comparator.comparing(DyeingWorkOrder::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(allOrders, offset, limit);
    }

    public DyeingWorkOrderDTO getDyeingWorkOrderById(Long id) {
        Optional<DyeingWorkOrder> op = dyeingWorkOrderRepository.findById(id);
        return op.map(this::mapToDTO).orElse(null);
    }

    public DyeingWorkOrderDTO getDyeingWorkOrderByIdAndCompany(Long id, Long companyId) {
        DyeingWorkOrder order = dyeingWorkOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("DyeingWorkOrder not found with id: " + id + " and companyId: " + companyId));
        return new DyeingWorkOrderDTO(order);
    }

    public DyeingWorkOrderDTO getDyeingWorkOrderByIdAndCompanyAndType(Long id, Long companyId, String processType) {
        String validType = ProcessTypeUtil.getValidProcessType(processType);
        DyeingWorkOrder order = dyeingWorkOrderRepository.findByIdAndCompanyIdAndProcessType(id, companyId, validType)
                .orElseThrow(() -> new RuntimeException("DyeingWorkOrder not found with id: " + id + ", companyId: " + companyId + " and processType: " + validType));
        return new DyeingWorkOrderDTO(order);
    }

    @Transactional
    public DyeingWorkOrderDTO updateDyeingWorkOrder(Long id, DyeingWorkOrderDTO dto) {
        DyeingWorkOrder entity = dyeingWorkOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DyeingWorkOrder not found with id: " + id));
        return applyUpdate(entity, dto);
    }

    @Transactional
    public DyeingWorkOrderDTO updateDyeingWorkOrderByCompany(Long id, Long companyId, DyeingWorkOrderDTO dto) {
        DyeingWorkOrder entity = dyeingWorkOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("DyeingWorkOrder not found with id: " + id + " and companyId: " + companyId));
        return applyUpdate(entity, dto);
    }

    @Transactional
    public DyeingWorkOrderDTO updateDyeingWorkOrderByCompanyAndType(Long id, Long companyId, String processType, DyeingWorkOrderDTO dto) {
        String validType = ProcessTypeUtil.getValidProcessType(processType);
        DyeingWorkOrder entity = dyeingWorkOrderRepository.findByIdAndCompanyIdAndProcessType(id, companyId, validType)
                .orElseThrow(() -> new RuntimeException("DyeingWorkOrder not found with id: " + id + ", companyId: " + companyId + " and processType: " + validType));
        return applyUpdate(entity, dto);
    }

    @Transactional
    public boolean deleteDyeingWorkOrder(Long id) {
        DyeingWorkOrder workOrder = dyeingWorkOrderRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Dyeing Work Order not found with id: " + id));

        validateDeletion(id);

        workOrder.setActiveFlag(false);
        dyeingWorkOrderRepository.save(workOrder);
        return true;
    }

    @Transactional
    public boolean deleteDyeingWorkOrderByCompany(Long id, Long companyId) {
        DyeingWorkOrder order = dyeingWorkOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Dyeing Work Order not found with id: " + id + " and companyId: " + companyId));
        validateDeletion(order.getId());
        dyeingWorkOrderRepository.delete(order);
        return true;
    }

    @Transactional
    public boolean deleteDyeingWorkOrderByCompanyAndType(Long id, Long companyId, String processType) {
        String validType = ProcessTypeUtil.getValidProcessType(processType);
        DyeingWorkOrder order = dyeingWorkOrderRepository.findByIdAndCompanyIdAndProcessType(id, companyId, validType)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Dyeing Work Order not found with id: " + id + ", companyId: " + companyId + " and processType: " + validType));
        validateDeletion(order.getId());
        dyeingWorkOrderRepository.delete(order);
        return true;
    }
     @Transactional
    public void hardDeleteDyeingWorkOrder(Long id) {
        DyeingWorkOrder workOrder = dyeingWorkOrderRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Dyeing Work Order not found with id: " + id));

        // Validation
        validateDeletion(id);

        // Delete associated items
        fabricItemRepository.deleteByDyeingWorkOrderId(id);
        yarnItemRepository.deleteByDyeingWorkOrderId(id);

       

        dyeingWorkOrderRepository.delete(workOrder);
    }

    private void validateDeletion(Long id) {
        // Check for linked dispatches (only active ones)
        List<FabricDispatchForDyeing> dispatches = fabricDispatchRepository.findByDyeingWorkOrderId(id).stream()
                .filter(d -> Boolean.TRUE.equals(d.getActiveFlag()))
                .collect(Collectors.toList());
        if (!dispatches.isEmpty()) {
            String dispatchNos = dispatches.stream()
                    .map(FabricDispatchForDyeing::getDispatchNo)
                    .collect(Collectors.joining(", "));
            throw new ApiException(HttpStatus.CONFLICT, "Cannot delete: This Work Order is already linked to Dispatches: " + dispatchNos);
        }

        // Check for linked finish fabric receives (only active ones)
        List<FinishFabricReceive> receives = finishFabricReceiveRepository.findByDyeingWorkOrderId(id).stream()
                .filter(r -> Boolean.TRUE.equals(r.getActiveFlag()))
                .collect(Collectors.toList());
        if (!receives.isEmpty()) {
            String receiveNos = receives.stream()
                    .map(r -> r.getReceiveNo() != null ? r.getReceiveNo() : r.getId().toString())
                    .collect(Collectors.joining(", "));
            throw new ApiException(HttpStatus.CONFLICT, "Cannot delete: This Work Order is already linked to Finish Receive(s): " + receiveNos);
        }
    }
    private DyeingWorkOrderDTO applyUpdate(DyeingWorkOrder entity, DyeingWorkOrderDTO dto) {
        mapDtoToEntity(dto, entity);
        String processType = entity.getProcessType();

        if (ProcessTypeUtil.isFabric(processType)) {
            syncFabricItems(entity, dto.getFabricItems());
            entity.getYarnItems().clear();
        } else if (ProcessTypeUtil.isYarn(processType)) {
            syncYarnItems(entity, dto.getYarnItems());
            entity.getFabricItems().clear();
        } else {
            entity.getFabricItems().clear();
            entity.getYarnItems().clear();
        }

        DyeingWorkOrder finalSaved = dyeingWorkOrderRepository.save(entity);
        return mapToDTO(finalSaved);
    }

    private void mapDtoToEntity(DyeingWorkOrderDTO dto, DyeingWorkOrder entity) {
        entity.setDyeingWorkOrderNo(dto.getDyeingWorkOrderNo());
        entity.setProcessContactDate(dto.getProcessContactDate());
        entity.setDeliveryDate(dto.getDeliveryDate());
        entity.setVendorId(dto.getVendorId());

        Long salesOrderId = normalizeId(dto.getSalesOrderNo());
        if (salesOrderId != null) {
            referenceValidationService.requireReference(
                    salesOrderId,
                    () -> salesOrderRepository.findById(salesOrderId),
                    "Sales Order",
                    null);
        }
        entity.setSalesOrderNo(salesOrderId);

        entity.setConsigneeId(dto.getConsigneeId());
        entity.setLapDipStatusId(dto.getLapDipStatusId());
        entity.setFirstYardageId(dto.getFirstYardageId());
        entity.setProcessTypeId(dto.getProcessTypeId());
        entity.setGstTypeId(dto.getGstTypeId());
        entity.setProcessType(ProcessTypeUtil.getValidProcessType(dto.getProcessType()));
        entity.setRemarks(dto.getRemarks());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        entity.setCompanyId(dto.getCompanyId());
        entity.setIsComplete(dto.getIsComplete());
    }

    private void mapFabricItemDtoToEntity(DyeingWorkOrderFabricItemDTO dto, DyeingWorkOrderFabricItem entity) {
        entity.setFinishedFabricCodeId(dto.getFinishedFabricCodeId());
        entity.setFinishedFabricName(dto.getFinishedFabricName());
        entity.setGreigeFabricCodeId(dto.getGreigeFabricCodeId());
        entity.setGreigeFabricName(dto.getGreigeFabricName());
        entity.setQuantity(dto.getQuantity());
        entity.setCostPerPound(dto.getCostPerPound());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setGstPercent(dto.getGstPercent());
        entity.setGstAmount(dto.getGstAmount());
        entity.setFinalAmount(dto.getFinalAmount());
        entity.setColorId(dto.getColorId());
        entity.setPantone(dto.getPantone());
        entity.setFinishedWeight(dto.getFinishedWeight());
        entity.setGreigeWidth(dto.getGreigeWidth());
        entity.setReqFinishedWidth(dto.getReqFinishedWidth());
        entity.setUomId(dto.getUomId());
        entity.setRemarks(dto.getRemarks());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
    }

    private void mapYarnItemDtoToEntity(DyeingWorkOrderYarnItemDTO dto, DyeingWorkOrderYarnItem entity) {
        entity.setFinishedYarnCodeId(dto.getFinishedYarnCodeId());
        entity.setFinishedYarnName(dto.getFinishedYarnName());
        entity.setGreigeYarnCodeId(dto.getGreigeYarnCodeId());
        entity.setGreigeYarnName(dto.getGreigeYarnName());
        entity.setQuantity(dto.getQuantity());
        entity.setCostPerPound(dto.getCostPerPound());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setGstPercent(dto.getGstPercent());
        entity.setGstAmount(dto.getGstAmount());
        entity.setFinalAmount(dto.getFinalAmount());
        entity.setDispatchQuantity(dto.getDispatchQuantity());
        entity.setColorId(dto.getColorId());
        entity.setPantone(dto.getPantone());
        entity.setFinishedWeight(dto.getFinishedWeight());
        entity.setUomId(dto.getUomId());
        entity.setRemarks(dto.getRemarks());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
    }

    private void syncFabricItems(DyeingWorkOrder entity, List<DyeingWorkOrderFabricItemDTO> dtoItems) {
        Map<Long, DyeingWorkOrderFabricItem> existingItems = entity.getFabricItems().stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(DyeingWorkOrderFabricItem::getId, Function.identity()));
        List<DyeingWorkOrderFabricItem> merged = new ArrayList<>();
        if (dtoItems != null) {
            for (DyeingWorkOrderFabricItemDTO dtoItem : dtoItems) {
                DyeingWorkOrderFabricItem item = dtoItem.getId() != null ? existingItems.get(dtoItem.getId()) : null;
                if (item == null) {
                    item = new DyeingWorkOrderFabricItem();
                    item.setDyeingWorkOrder(entity);
                }
                mapFabricItemDtoToEntity(dtoItem, item);
                merged.add(item);
            }
        }
        entity.getFabricItems().clear();
        entity.getFabricItems().addAll(merged);
    }

    private void syncYarnItems(DyeingWorkOrder entity, List<DyeingWorkOrderYarnItemDTO> dtoItems) {
        Map<Long, DyeingWorkOrderYarnItem> existingItems = entity.getYarnItems().stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(DyeingWorkOrderYarnItem::getId, Function.identity()));
        List<DyeingWorkOrderYarnItem> merged = new ArrayList<>();
        if (dtoItems != null) {
            for (DyeingWorkOrderYarnItemDTO dtoItem : dtoItems) {
                DyeingWorkOrderYarnItem item = dtoItem.getId() != null ? existingItems.get(dtoItem.getId()) : null;
                if (item == null) {
                    item = new DyeingWorkOrderYarnItem();
                    item.setDyeingWorkOrder(entity);
                }
                mapYarnItemDtoToEntity(dtoItem, item);
                merged.add(item);
            }
        }
        entity.getYarnItems().clear();
        entity.getYarnItems().addAll(merged);
    }

    // private String generateWorkOrderNo(Long id) {
    //     String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    //     return "DWO-" + dateStr + "-" + String.format("%05d", id);
    // }

    private DyeingWorkOrderDTO mapToDTO(DyeingWorkOrder entity) {
        DyeingWorkOrderDTO dto = new DyeingWorkOrderDTO(entity);
        if (entity.getId() != null) {
            List<FabricDispatchForDyeing> dispatches = fabricDispatchRepository.findByDyeingWorkOrderId(entity.getId());
            for (DyeingWorkOrderYarnItemDTO yarnItemDto : dto.getYarnItems()) {
                Double totalDispatchQty = dispatches.stream()
                        .flatMap(d -> d.getYarnIssues().stream())
                        .mapToDouble(yi -> yi.getIssueQty() != null ? yi.getIssueQty() : 0.0)
                        .sum();
                yarnItemDto.setDispatchQuantity(totalDispatchQty);
            }
        }
        return dto;
    }

    private Long normalizeId(Long id) {
        return (id == null || id <= 0) ? null : id;
    }
}