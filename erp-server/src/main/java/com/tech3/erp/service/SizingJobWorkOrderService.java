// package com.tech3.erp.service;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.SizingJobWorkOrderDTO;
// import com.tech3.erp.dto.SizingJobWorkOrderItemDTO;
// import com.tech3.erp.entity.CodesDetail;
// import com.tech3.erp.entity.Company;
// import com.tech3.erp.entity.SizingJobWorkOrder;
// import com.tech3.erp.entity.SizingJobWorkOrderItem;
// import com.tech3.erp.entity.SizingPlan;
// import com.tech3.erp.entity.SizingQualityDetails;
// import com.tech3.erp.entity.SizingBeamDetails;
// import com.tech3.erp.entity.WeavingContract;
// import com.tech3.erp.entity.YarnMaster;
// import com.tech3.erp.repository.SizingJobWorkOrderRepository;
// import com.tech3.erp.repository.SizingBeamDetailsRepository;
// import com.tech3.erp.repository.SizingBeamOutwardRepository;
// import com.tech3.erp.repository.SizingProductionRepository;
// import com.tech3.erp.repository.WarpingProductionRepository;
// import com.tech3.erp.repository.WeavingContractRepository;
// import com.tech3.erp.repository.CompanyRepository;
// import com.tech3.erp.repository.YarnMasterRepository;
// import com.tech3.erp.repository.YarnReturnRepository;
// import com.tech3.erp.repository.SizingYarnOutwardRepository;
// import com.tech3.erp.repository.SizingYarnInwardRepository;
// import com.tech3.erp.repository.SizingQualityDetailsRepository;
// import com.tech3.erp.repository.SizingPlanRepository;
// import jakarta.persistence.EntityNotFoundException;
// import java.time.LocalDate;
// import java.time.ZoneId;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Date;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.Optional;
// import java.util.Set;
// import java.util.stream.Collectors;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.util.StringUtils;

// @Service
// public class SizingJobWorkOrderService {

//     private final SizingJobWorkOrderRepository repository;
//     private final SizingBeamDetailsRepository sizingBeamDetailsRepository;
//     private final SizingBeamOutwardRepository sizingBeamOutwardRepository;
//     private final SizingProductionRepository sizingProductionRepository;
//     private final WarpingProductionRepository warpingProductionRepository;
//     private final WeavingContractRepository weavingContractRepository;
//     private final YarnMasterRepository yarnMasterRepository;
//     private final YarnReturnRepository yarnReturnRepository;
//     private final SizingYarnOutwardRepository sizingYarnOutwardRepository;
//     private final SizingYarnInwardRepository sizingYarnInwardRepository;
//     private final SizingPlanRepository sizingPlanRepository;
//     private final SizingQualityDetailsRepository sizingQualityDetailsRepository;
//     private final ReferenceValidationService referenceValidationService;
//     private final CompanyRepository companyRepository;

//     public SizingJobWorkOrderService(
//         SizingJobWorkOrderRepository repository,
//         SizingBeamDetailsRepository sizingBeamDetailsRepository,
//         SizingBeamOutwardRepository sizingBeamOutwardRepository,
//         SizingProductionRepository sizingProductionRepository,
//         WarpingProductionRepository warpingProductionRepository,
//         WeavingContractRepository weavingContractRepository,
//         YarnMasterRepository yarnMasterRepository,
//         YarnReturnRepository yarnReturnRepository,
//         SizingYarnOutwardRepository sizingYarnOutwardRepository,
//         SizingYarnInwardRepository sizingYarnInwardRepository,
//         SizingPlanRepository sizingPlanRepository,
//         SizingQualityDetailsRepository sizingQualityDetailsRepository,
//         ReferenceValidationService referenceValidationService,
//         CompanyRepository companyRepository
//     ) {
//         this.repository = repository;
//         this.sizingBeamDetailsRepository = sizingBeamDetailsRepository;
//         this.sizingBeamOutwardRepository = sizingBeamOutwardRepository;
//         this.sizingProductionRepository = sizingProductionRepository;
//         this.warpingProductionRepository = warpingProductionRepository;
//         this.weavingContractRepository = weavingContractRepository;
//         this.yarnMasterRepository = yarnMasterRepository;
//         this.yarnReturnRepository = yarnReturnRepository;
//         this.sizingYarnOutwardRepository = sizingYarnOutwardRepository;
//         this.sizingYarnInwardRepository = sizingYarnInwardRepository;
//         this.sizingPlanRepository = sizingPlanRepository;
//         this.sizingQualityDetailsRepository = sizingQualityDetailsRepository;
//         this.referenceValidationService = referenceValidationService;
//         this.companyRepository = companyRepository;
//     }

//     @Transactional
//     public SizingJobWorkOrderDTO create(SizingJobWorkOrderDTO dto) {
//         requireSizingPlanReference(dto.getSizingPlanId());
//         SizingJobWorkOrder entity = mapDtoToEntity(dto, new SizingJobWorkOrder());
//         SizingJobWorkOrder saved = repository.save(entity);
//         saved = assignSizingWorkOrderNoIfMissing(saved);
//         syncToSizingPlan(saved);
//         updateMappedStatus(saved.getId());
//         return mapEntityToDto(saved);
//     }

//     public PaginatedResponse<SizingJobWorkOrderDTO> getAll(Long factoryCompanyId, Long offset, Long limit) {
//         long finalLimit = limit != null && limit > 0 ? limit : 10L;
//         long finalOffset = offset != null && offset >= 0 ? offset : 0L;
//         PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
//         Page<SizingJobWorkOrder> page = factoryCompanyId != null
//             ? repository.findByFactoryCompanyId(factoryCompanyId, pageRequest)
//             : repository.findAll(pageRequest);
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }

//     public PaginatedResponse<SizingJobWorkOrderDTO> getAllByFactoryCompanyId(Long factoryCompanyId, Long offset, Long limit) {
//         if (factoryCompanyId == null) {
//             throw new IllegalArgumentException("factoryCompanyId is required");
//         }
//         return getAll(factoryCompanyId, offset, limit);
//     }

//     public SizingJobWorkOrderDTO getById(Long id) {
//         return getById(id, null);
//     }

//     public SizingJobWorkOrderDTO getById(Long id, Long factoryCompanyId) {
//         updateMappedStatus(id);
//         SizingJobWorkOrder entity = factoryCompanyId != null
//             ? repository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found for factoryCompanyId: " + factoryCompanyId))
//             : repository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + id));
//         return mapEntityToDto(entity);
//     }

//     @Transactional
//     public SizingJobWorkOrderDTO update(Long id, SizingJobWorkOrderDTO dto) {
//         return update(id, dto, null);
//     }

//     @Transactional
//     public SizingJobWorkOrderDTO update(Long id, SizingJobWorkOrderDTO dto, Long factoryCompanyId) {
//         SizingJobWorkOrder entity = repository.findById(id)
//             .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + id));
//         if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
//             throw new EntityNotFoundException("SizingJobWorkOrder not found for factoryCompanyId: " + factoryCompanyId);
//         }
//         if (factoryCompanyId != null) {
//             dto.setFactoryCompanyId(entity.getFactoryCompanyId());
//         }
//         if (!StringUtils.hasText(dto.getSizingWorkOrderNo())) {
//             dto.setSizingWorkOrderNo(entity.getSizingWorkOrderNo());
//         }
//         dto.setOrderTypeId(entity.getOrderTypeId());
//         dto.setFactoryCompanyId(entity.getFactoryCompanyId());
//         dto.setTradingCompanyId(entity.getTradingCompanyId());
//         requireSizingPlanReference(dto.getSizingPlanId());
//         mapDtoToEntity(dto, entity);
//         SizingJobWorkOrder saved = repository.save(entity);
//         saved = assignSizingWorkOrderNoIfMissing(saved);
//         syncToSizingPlan(saved);
//         updateMappedStatus(saved.getId());
//         return mapEntityToDto(saved);
//     }

//     private void syncToSizingPlan(SizingJobWorkOrder entity) {
//         if (entity.getSizingPlanId() == null) {
//             return;
//         }

//         sizingPlanRepository.findById(entity.getSizingPlanId()).ifPresent(plan -> {
//             plan.setSizingPlanDate(entity.getOrderDate());
//             plan.setSizingRate(entity.getSizingRatePerKg());
//             plan.setRemarks(entity.getRemarks());
//             plan.setOrderTypeId(entity.getOrderTypeId());
//             plan.setFactoryCompanyId(entity.getFactoryCompanyId());
//             plan.setTradingCompanyId(entity.getTradingCompanyId());
//             sizingPlanRepository.save(plan);

//             if (entity.getItems() != null) {
//                 for (SizingJobWorkOrderItem item : entity.getItems()) {
//                     if (item.getPlanDetailId() != null) {
//                         sizingQualityDetailsRepository.findById(item.getPlanDetailId()).ifPresent(detail -> {
//                             detail.setActualYarn(item.getYarnId());
//                             detail.setActualWarpEnds(item.getTotalEnds() != null ? item.getTotalEnds().longValue() : null);
//                             detail.setParts(item.getNumberOfParts() != null ? item.getNumberOfParts().longValue() : null);
//                             detail.setEndsPerPart(item.getEndsPerPart() != null ? item.getEndsPerPart().longValue() : null);
//                             detail.setWarpMeters(item.getWarpMeters() != null ? item.getWarpMeters().longValue() : null);
//                             sizingQualityDetailsRepository.save(detail);
//                         });
//                     }
//                 }
//             }
//         });
//     }

//     public void delete(Long id) {
//         delete(id, null);
//     }

//     public void delete(Long id, Long factoryCompanyId) {
//         SizingJobWorkOrder entity = repository.findById(id)
//             .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + id));
//         if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
//             throw new EntityNotFoundException("SizingJobWorkOrder not found for factoryCompanyId: " + factoryCompanyId);
//         }
//         ensureNoSizingJobWorkOrderReferences(entity);
//         deleteEntity(entity);
//     }

//     @Transactional
//     public SizingJobWorkOrder createOrUpdateFromSizingPlan(SizingPlan plan, List<SizingQualityDetails> qualityDetails) {
//         if (plan == null || plan.getId() == null) {
//             throw new IllegalArgumentException("Sizing plan is required");
//         }
//         SizingJobWorkOrder entity = repository.findBySizingPlanId(plan.getId()).orElseGet(SizingJobWorkOrder::new);
//         boolean isNew = entity.getId() == null;
//         entity.setSizingPlanId(plan.getId());
//         entity.setOrderDate(plan.getSizingPlanDate());
//         entity.setOrderTypeId(plan.getOrderTypeId());
//         entity.setSizingRatePerKg(plan.getSizingRate());
//         entity.setRemarks(plan.getRemarks());
//         CompanyAssignments assignments = resolveCompanyAssignments(plan);
//         entity.setBuyerId(assignments.getBuyerId());
//         entity.setConsigneeId(assignments.getConsigneeId());
//         entity.setFactoryCompanyId(assignments.getFactoryCompanyId());
//         entity.setTradingCompanyId(assignments.getTradingCompanyId());
//         if (entity.getActiveFlag() == null) {
//             entity.setActiveFlag(Boolean.TRUE);
//         }
//         applyItemsFromPlanDetails(entity, qualityDetails);
//         SizingJobWorkOrder saved = repository.save(entity);
//         updateMappedStatus(saved.getId());
//         return assignSizingWorkOrderNoIfMissing(saved);
//     }

//     @Transactional
//     public void deleteBySizingPlanId(Long sizingPlanId) {
//         if (sizingPlanId == null) {
//             return;
//         }
//         repository.findBySizingPlanId(sizingPlanId).ifPresent(entity -> {
//             ensureNoSizingJobWorkOrderReferences(entity);
//             deleteEntity(entity);
//         });
//     }

//     private void requireSizingPlanReference(Long sizingPlanId) {
//         if (sizingPlanId == null) {
//             return;
//         }
//         Optional<SizingPlan> sizingPlan = sizingPlanRepository.findById(sizingPlanId);
//         String displayIdentifier = sizingPlan
//                 .map(SizingPlan::getSizingPlanNo)
//                 .filter(StringUtils::hasText)
//                 .orElse("Sizing Plan ID " + sizingPlanId);
//         referenceValidationService.requireReference(
//                 sizingPlanId,
//                 () -> sizingPlan,
//                 "Sizing Plan",
//                 displayIdentifier);
//     }

//     private void deleteEntity(SizingJobWorkOrder entity) {
//         try {
//             repository.delete(entity);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete SizingJobWorkOrder because it is referenced by other records", e);
//         }
//     }

//     @Transactional
//     public void updateMappedStatus(Long sizingOrderId) {
//         if (sizingOrderId == null) {
//             return;
//         }
//         repository.findById(sizingOrderId).ifPresent(order -> {
//             boolean isIdMapped = isSizingOrderIdMapped(order);
//             boolean isNoMapped = isSizingOrderNoMapped(order);
            
//             short newIdMappedValue = isIdMapped ? (short) 1 : (short) 0;
//             short newNoMappedValue = isNoMapped ? (short) 1 : (short) 0;
            
//             if (order.getIsOrderIdMapped() == null || order.getIsOrderIdMapped() != newIdMappedValue) {
//                 order.setIsOrderIdMapped(newIdMappedValue);
//                 repository.save(order);
//             }
//         });
//     }

//     @Transactional
//     public SizingJobWorkOrder markAsOrderIdMapped(String sizingWorkOrderNo) {
//         if (!StringUtils.hasText(sizingWorkOrderNo)) {
//             throw new IllegalArgumentException("SizingWorkOrderNo is required");
//         }
//         String trimmedNo = sizingWorkOrderNo.trim();
//         SizingJobWorkOrder wo = repository.findBySizingWorkOrderNo(trimmedNo)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with sizingWorkOrderNo: " + trimmedNo));
//         return markOrderIdMapped(wo);
//     }

//     @Transactional
//     public SizingJobWorkOrder markAsOrderIdMapped(Long sizingWorkOrderId) {
//         if (sizingWorkOrderId == null) {
//             throw new IllegalArgumentException("SizingWorkOrderId is required");
//         }
//         SizingJobWorkOrder wo = repository.findById(sizingWorkOrderId)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + sizingWorkOrderId));
//         return markOrderIdMapped(wo);
//     }

//     private SizingJobWorkOrder markOrderIdMapped(SizingJobWorkOrder wo) {
//         if (Short.valueOf((short) 1).equals(wo.getIsOrderIdMapped())) {
//             return wo;
//         }
//         wo.setIsOrderIdMapped((short) 1);
//         return repository.save(wo);
//     }

//     private boolean isSizingOrderMapped(SizingJobWorkOrder entity) {
//         return isSizingOrderIdMapped(entity) || isSizingOrderNoMapped(entity);
//     }

//     private boolean isSizingOrderIdMapped(SizingJobWorkOrder entity) {
//         if (entity == null || entity.getId() == null) {
//             return false;
//         }
        
//         if (entity.getItems() != null && !entity.getItems().isEmpty()) {
//             return true;
//         }

//         Long jobId = entity.getId();

//         if (warpingProductionRepository.existsBySizingOrderId(jobId)) {
//             return true;
//         }
//         if (sizingProductionRepository.existsBySizingOrderId(jobId)) {
//             return true;
//         }
//         if (sizingBeamOutwardRepository.existsBySizingOrderId(jobId)) {
//             return true;
//         }
//         if (sizingYarnOutwardRepository.existsBySizingOrderId(jobId)) {
//             return true;
//         }
//         if (sizingYarnInwardRepository.existsBySizingJobWorkOrderId(jobId)) {
//             return true;
//         }
//         return false;
//     }

//     private boolean isSizingOrderNoMapped(SizingJobWorkOrder entity) {
//         if (entity == null || entity.getId() == null) {
//             return false;
//         }
//         String workOrderNo = StringUtils.hasText(entity.getSizingWorkOrderNo()) ? entity.getSizingWorkOrderNo().trim() : null;

//         if (workOrderNo != null) {
//             if (sizingBeamOutwardRepository.existsBySizingOrderNo(workOrderNo)) {
//                 return true;
//             }
//             if (yarnReturnRepository.existsByOrderNo(workOrderNo)) {
//                 return true;
//             }
//         }
//         return false;
//     }

//     private void ensureNoSizingJobWorkOrderReferences(SizingJobWorkOrder entity) {
//         if (entity == null || entity.getId() == null) {
//             return;
//         }
//         Long jobId = entity.getId();
//         String workOrderNo = StringUtils.hasText(entity.getSizingWorkOrderNo()) ? entity.getSizingWorkOrderNo().trim() : null;
//         String orderIdentifier = workOrderNo != null ? "sizing_order_no " + workOrderNo : "sizing_order_id " + jobId;
//         if (warpingProductionRepository.existsBySizingOrderId(jobId)) {
//             throw new IllegalStateException("Cannot delete SizingJobWorkOrder because warping_production references " + orderIdentifier);
//         }
//         if (sizingProductionRepository.existsBySizingOrderId(jobId)) {
//             throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_production references " + orderIdentifier);
//         }
//         if (sizingBeamOutwardRepository.existsBySizingOrderId(jobId)) {
//             throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_beam_outward references " + orderIdentifier);
//         }
//         if (sizingYarnOutwardRepository.existsBySizingOrderId(jobId)) {
//             throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_yarn_outward references " + orderIdentifier);
//         }
//         if (sizingYarnInwardRepository.existsBySizingJobWorkOrderId(jobId)) {
//             throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_yarn_inward references " + orderIdentifier);
//         }
//         if (workOrderNo != null) {
//             if (sizingBeamOutwardRepository.existsBySizingOrderNo(workOrderNo)) {
//                 throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_beam_outward references sizing_order_no " + workOrderNo);
//             }
//             if (yarnReturnRepository.existsByOrderNo(workOrderNo)) {
//                 throw new IllegalStateException("Cannot delete SizingJobWorkOrder because yarn_return references order_no " + workOrderNo);
//             }
//         }
//     }

//     private CompanyAssignments resolveCompanyAssignments(SizingPlan plan) {
//         Long planCompanyId = plan != null ? plan.getCompanyId() : null;
//         Long planTradingCompanyId = plan != null ? plan.getTradingCompanyId() : null;
//         Long planFactoryCompanyId = plan != null ? plan.getFactoryCompanyId() : null;
//         Long orderTypeId = plan != null ? plan.getOrderTypeId() : null;
//         Long tradingCompanyId = planTradingCompanyId;
//         Long factoryCompanyId = planFactoryCompanyId;
//         WeavingContract contract = null;
//         Long weavingContractId = findWeavingContractId(plan);
//         if (weavingContractId != null) {
//             contract = weavingContractRepository.findById(weavingContractId).orElse(null);
//         }
//         if (contract != null) {
//             if (tradingCompanyId == null) {
//                 tradingCompanyId = contract.getTradingCompanyId() != null ? contract.getTradingCompanyId() : contract.getCompanyId();
//             }
//             if (factoryCompanyId == null) {
//                 factoryCompanyId = contract.getFactoryCompanyId() != null ? contract.getFactoryCompanyId() : contract.getCompanyId();
//             }
//             if (orderTypeId == null) {
//                 orderTypeId = contract.getOrderTypeId();
//             }
//         }
//         boolean factoryOnly = orderTypeId != null && orderTypeId == 2L;
//         if (factoryOnly) {
//             tradingCompanyId = null;
//             factoryCompanyId = coalesce(factoryCompanyId, planFactoryCompanyId, planCompanyId);
//         } else {
//             tradingCompanyId = coalesce(tradingCompanyId, planTradingCompanyId, planCompanyId);
//             factoryCompanyId = coalesce(factoryCompanyId, planFactoryCompanyId, tradingCompanyId, planCompanyId);
//         }
//         Long buyerId = coalesce(tradingCompanyId, planTradingCompanyId, planCompanyId);
//         Long consigneeId = buyerId;
//         return new CompanyAssignments(buyerId, consigneeId, factoryCompanyId, tradingCompanyId);
//     }

//     private Long findWeavingContractId(SizingPlan plan) {
//         if (plan == null || plan.getId() == null) {
//             return null;
//         }
//         return sizingBeamDetailsRepository.findBySizingPlanId(plan.getId()).stream()
//             .map(SizingBeamDetails::getWeavingContractId)
//             .filter(Objects::nonNull)
//             .findFirst()
//             .orElse(null);
//     }

//     private Long coalesce(Long... values) {
//         if (values == null) {
//             return null;
//         }
//         for (Long value : values) {
//             if (value != null) {
//                 return value;
//             }
//         }
//         return null;
//     }

//     private static class CompanyAssignments {
//         private final Long buyerId;
//         private final Long consigneeId;
//         private final Long factoryCompanyId;
//         private final Long tradingCompanyId;

//         private CompanyAssignments(Long buyerId, Long consigneeId, Long factoryCompanyId, Long tradingCompanyId) {
//             this.buyerId = buyerId;
//             this.consigneeId = consigneeId;
//             this.factoryCompanyId = factoryCompanyId;
//             this.tradingCompanyId = tradingCompanyId;
//         }

//         private Long getBuyerId() {
//             return buyerId;
//         }

//         private Long getConsigneeId() {
//             return consigneeId;
//         }

//         private Long getFactoryCompanyId() {
//             return factoryCompanyId;
//         }

//         private Long getTradingCompanyId() {
//             return tradingCompanyId;
//         }
//     }

//     private PaginatedResponse<SizingJobWorkOrderDTO> buildPaginatedResponse(Page<SizingJobWorkOrder> page, long offset, long limit) {
//         List<SizingJobWorkOrderDTO> content = page.getContent().stream()
//             .map(this::mapEntityToDto)
//             .collect(Collectors.toList());
//         return new PaginatedResponse<>(page.getTotalElements(), !page.isLast(), page.getNumber() > 0, offset, limit, content);
//     }

//     private SizingJobWorkOrder mapDtoToEntity(SizingJobWorkOrderDTO dto, SizingJobWorkOrder entity) {
//         boolean hasDtoWorkOrderNo = StringUtils.hasText(dto.getSizingWorkOrderNo());
//         boolean isAutoGeneratedType = dto.getOrderTypeId() != null && dto.getOrderTypeId() == 1L;
//         if (hasDtoWorkOrderNo && (entity.getId() != null || !isAutoGeneratedType)) {
//             entity.setSizingWorkOrderNo(dto.getSizingWorkOrderNo());
//         }
//         entity.setOrderDate(dto.getOrderDate());
//         entity.setOrderTypeId(dto.getOrderTypeId());
//         entity.setBuyerId(dto.getBuyerId());
//         entity.setConsigneeId(dto.getConsigneeId());
//         entity.setGstTypeId(dto.getGstTypeId());
//         entity.setSizingPlanId(dto.getSizingPlanId());
//         entity.setSizingRatePerKg(dto.getSizingRatePerKg());
//         entity.setFactoryCompanyId(dto.getFactoryCompanyId());
//         entity.setTradingCompanyId(dto.getTradingCompanyId());
//         entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : Boolean.TRUE);
//         entity.setIsApproved(dto.getIsApproved());
//         entity.setIsComplete(dto.getIsComplete());
//         // if (dto.getIsOrderMapped() != null) {
//         //     entity.setIsOrderMapped(dto.getIsOrderMapped());
//         // } else if (entity.getId() == null) {
//         //     entity.setIsOrderMapped((short) 0);
//         // }
//         if (dto.getIsOrderIdMapped() != null) {
//             entity.setIsOrderIdMapped(dto.getIsOrderIdMapped());
//         } else if (entity.getId() == null) {
//             entity.setIsOrderIdMapped((short) 0);
//         }
//         entity.setUserId(dto.getUserId());
//         entity.setComments(dto.getComments());
//         if (dto.getApproveDate() != null) {
//             entity.setApproveDate(dto.getApproveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//         }
//         entity.setRemarks(dto.getRemarks());
//         applyItems(entity, dto.getItems());
//         return entity;
//     }

//     private void applyItems(SizingJobWorkOrder entity, List<SizingJobWorkOrderItemDTO> itemDTOs) {
//         if (entity.getItems() == null) {
//             entity.setItems(new ArrayList<>());
//         }
//         List<SizingJobWorkOrderItem> current = entity.getItems();
//         Map<Long, SizingJobWorkOrderItem> existing = current.stream()
//             .filter(item -> item.getId() != null)
//             .collect(Collectors.toMap(SizingJobWorkOrderItem::getId, item -> item, (a, b) -> a));
//         List<SizingJobWorkOrderItem> updated = new ArrayList<>();
//         if (itemDTOs != null) {
//             for (SizingJobWorkOrderItemDTO dto : itemDTOs) {
//                 if (!hasItemData(dto)) {
//                     continue;
//                 }
//                 SizingJobWorkOrderItem item = dto.getId() != null && existing.containsKey(dto.getId())
//                     ? existing.remove(dto.getId())
//                     : new SizingJobWorkOrderItem();
//                 item.setSizingJobWorkOrder(entity);
//                 item.setPlanDetailId(dto.getPlanDetailId());
//                 item.setYarnId(dto.getYarnId());
//                 item.setYarnName(dto.getYarnName());
//                 item.setTotalEnds(dto.getTotalEnds());
//                 item.setNumberOfParts(dto.getNumberOfParts());
//                 item.setEndsPerPart(dto.getEndsPerPart());
//                 item.setWarpMeters(dto.getWarpMeters());
//                 item.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : Boolean.TRUE);
//                 updated.add(item);
//             }
//         }
//         current.clear();
//         current.addAll(updated);
//     }

//     private boolean hasItemData(SizingJobWorkOrderItemDTO dto) {
//         if (dto == null) {
//             return false;
//         }
//         if (dto.getYarnId() != null) {
//             return true;
//         }
//         if (dto.getPlanDetailId() != null) {
//             return true;
//         }
//         return dto.getYarnName() != null && !dto.getYarnName().trim().isEmpty();
//     }

//     private SizingJobWorkOrderDTO mapEntityToDto(SizingJobWorkOrder entity) {
//         SizingJobWorkOrderDTO dto = new SizingJobWorkOrderDTO();
//         dto.setId(entity.getId());
//         dto.setSizingWorkOrderNo(entity.getSizingWorkOrderNo());
//         dto.setOrderDate(entity.getOrderDate());
//         dto.setOrderTypeId(entity.getOrderTypeId());
//         dto.setBuyerId(entity.getBuyerId());
//         dto.setConsigneeId(entity.getConsigneeId());
//         dto.setGstTypeId(entity.getGstTypeId());
//         dto.setSizingPlanId(entity.getSizingPlanId());
//         dto.setSizingRatePerKg(entity.getSizingRatePerKg());
//         dto.setFactoryCompanyId(entity.getFactoryCompanyId());
//         dto.setTradingCompanyId(entity.getTradingCompanyId());
//         dto.setActiveFlag(entity.getActiveFlag());
//         dto.setIsApproved(entity.getIsApproved());
//         dto.setIsComplete(entity.getIsComplete());
//         // dto.setIsOrderMapped(entity.getIsOrderMapped());
//         dto.setIsOrderIdMapped(entity.getIsOrderIdMapped());
//         dto.setUserId(entity.getUserId());
//         dto.setComments(entity.getComments());
//         if (entity.getApproveDate() != null) {
//             dto.setApproveDate(Date.from(entity.getApproveDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//         }
//         dto.setRemarks(entity.getRemarks());
//         List<SizingJobWorkOrderItemDTO> items = entity.getItems() == null ? new ArrayList<>() : entity.getItems().stream()
//             .map(this::mapItemToDto)
//             .collect(Collectors.toList());
//         dto.setItems(items);
//         return dto;
//     }

//     private SizingJobWorkOrderItemDTO mapItemToDto(SizingJobWorkOrderItem item) {
//         SizingJobWorkOrderItemDTO dto = new SizingJobWorkOrderItemDTO();
//         dto.setId(item.getId());
//         dto.setPlanDetailId(item.getPlanDetailId());
//         dto.setYarnId(item.getYarnId());
//         dto.setYarnName(item.getYarnName());
//         dto.setTotalEnds(item.getTotalEnds());
//         dto.setNumberOfParts(item.getNumberOfParts());
//         dto.setEndsPerPart(item.getEndsPerPart());
//         dto.setWarpMeters(item.getWarpMeters());
//         dto.setActiveFlag(item.getActiveFlag());
//         return dto;
//     }

//     private void applyItemsFromPlanDetails(SizingJobWorkOrder entity, List<SizingQualityDetails> details) {
//         if (entity.getItems() == null) {
//             entity.setItems(new ArrayList<>());
//         }
//         List<SizingJobWorkOrderItem> items = entity.getItems();
//         items.clear();
//         if (details == null) {
//             return;
//         }
//         boolean requiresYarnNames = entity.getOrderTypeId() != null && entity.getOrderTypeId() == 1L;
//         Map<Long, String> yarnNames = requiresYarnNames ? resolveYarnNames(details) : Collections.emptyMap();
//         for (SizingQualityDetails detail : details) {
//             SizingJobWorkOrderItem item = new SizingJobWorkOrderItem();
//             item.setSizingJobWorkOrder(entity);
//             item.setPlanDetailId(detail.getId());
//             Long yarnId = resolveYarnId(detail);
//             item.setYarnId(yarnId);
//             String yarnName = detail.getQuality();
//             if (requiresYarnNames && yarnId != null) {
//                 yarnName = yarnNames.getOrDefault(yarnId, yarnName);
//             }
//             item.setYarnName(yarnName);
//             item.setTotalEnds(toDouble(detail.getActualWarpEnds() != null ? detail.getActualWarpEnds() : detail.getWarpEnds()));
//             item.setNumberOfParts(toDouble(detail.getParts()));
//             item.setEndsPerPart(toDouble(detail.getEndsPerPart()));
//             item.setWarpMeters(toDouble(detail.getWarpMeters()));
//             item.setActiveFlag(Boolean.TRUE);
//             items.add(item);
//         }
//     }

//     private Map<Long, String> resolveYarnNames(List<SizingQualityDetails> details) {
//         Set<Long> yarnIds = details.stream()
//             .map(this::resolveYarnId)
//             .filter(Objects::nonNull)
//             .collect(Collectors.toSet());
//         if (yarnIds.isEmpty()) {
//             return Collections.emptyMap();
//         }
//         return yarnMasterRepository.findAllById(yarnIds).stream()
//             .collect(Collectors.toMap(YarnMaster::getId, YarnMaster::getYarnName));
//     }

//     private Long resolveYarnId(SizingQualityDetails detail) {
//         if (detail == null) {
//             return null;
//         }
//         return detail.getActualYarn() != null ? detail.getActualYarn() : detail.getYarnId();
//     }

//     private Double toDouble(Long value) {
//         return value != null ? value.doubleValue() : null;
//     }

//     private SizingJobWorkOrder assignSizingWorkOrderNoIfMissing(SizingJobWorkOrder entity) {
//         if (entity == null) {
//             return null;
//         }
//         if (!StringUtils.hasText(entity.getSizingWorkOrderNo())) {
//             Long companyId = entity.getTradingCompanyId() != null ? entity.getTradingCompanyId() : entity.getFactoryCompanyId();
//             entity.setSizingWorkOrderNo(generateSizingJobWorkOrderNo(entity.getOrderDate(), entity.getId(), companyId));
//             entity = repository.save(entity);
//         }
//         return entity;
//     }

//     private String generateSizingJobWorkOrderNo(LocalDate orderDate, Long id, Long companyId) {
//         if (id == null) {
//             return null;
//         }
//         String cmpPrefix = "";
//         if (companyId != null) {
//             Company company = companyRepository.findById(companyId).orElse(null);
//             if (company != null) {
//                 if (StringUtils.hasText(company.getCompanyCode())) {
//                     cmpPrefix = company.getCompanyCode() + "/";
//                 } else if (StringUtils.hasText(company.getCompanyName())) {
//                     cmpPrefix = company.getCompanyName().substring(0, 1).toUpperCase() + "/";
//                 }
//             }
//         }
//         LocalDate referenceDate = orderDate != null ? orderDate : LocalDate.now();
//         String fy = getFinancialYear(referenceDate);
//         String prefix = cmpPrefix + "SJO/" + fy + "/";
//         String sequence = String.format("%03d", id);
//         return prefix + sequence;
//     }

//     private String getFinancialYear(LocalDate date) {
//         if (date == null) {
//             date = LocalDate.now();
//         }
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int fyStartYear = month >= 4 ? year : year - 1;
//         int fy1 = fyStartYear % 100;
//         int fy2 = (fyStartYear + 1) % 100;
//         return String.format("%02d-%02d", fy1, fy2);
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingJobWorkOrderDTO;
import com.tech3.erp.dto.SizingJobWorkOrderItemDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.SizingJobWorkOrder;
import com.tech3.erp.entity.SizingJobWorkOrderItem;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingQualityDetails;
import com.tech3.erp.entity.SizingBeamDetails;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.SizingBeamOutwardRepository;
import com.tech3.erp.repository.SizingProductionRepository;
import com.tech3.erp.repository.WarpingProductionRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.repository.YarnReturnRepository;
import com.tech3.erp.repository.SizingYarnOutwardRepository;
import com.tech3.erp.repository.SizingYarnInwardRepository;
import com.tech3.erp.repository.SizingQualityDetailsRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import com.tech3.erp.service.NotificationService;

@Service
public class SizingJobWorkOrderService {

    private final SizingJobWorkOrderRepository repository;
    private final SizingBeamDetailsRepository sizingBeamDetailsRepository;
    private final SizingBeamOutwardRepository sizingBeamOutwardRepository;
    private final SizingProductionRepository sizingProductionRepository;
    private final WarpingProductionRepository warpingProductionRepository;
    private final WeavingContractRepository weavingContractRepository;
    private final YarnMasterRepository yarnMasterRepository;
    private final YarnReturnRepository yarnReturnRepository;
    private final SizingYarnOutwardRepository sizingYarnOutwardRepository;
    private final SizingYarnInwardRepository sizingYarnInwardRepository;
    private final SizingPlanRepository sizingPlanRepository;
    private final SizingQualityDetailsRepository sizingQualityDetailsRepository;
    private final ReferenceValidationService referenceValidationService;
    private final CompanyRepository companyRepository;
    private final NotificationService notificationService;

    public SizingJobWorkOrderService(
        SizingJobWorkOrderRepository repository,
        SizingBeamDetailsRepository sizingBeamDetailsRepository,
        SizingBeamOutwardRepository sizingBeamOutwardRepository,
        SizingProductionRepository sizingProductionRepository,
        WarpingProductionRepository warpingProductionRepository,
        WeavingContractRepository weavingContractRepository,
        YarnMasterRepository yarnMasterRepository,
        YarnReturnRepository yarnReturnRepository,
        SizingYarnOutwardRepository sizingYarnOutwardRepository,
        SizingYarnInwardRepository sizingYarnInwardRepository,
        SizingPlanRepository sizingPlanRepository,
        SizingQualityDetailsRepository sizingQualityDetailsRepository,
        ReferenceValidationService referenceValidationService,
        CompanyRepository companyRepository,
        NotificationService notificationService
    ) {
        this.repository = repository;
        this.sizingBeamDetailsRepository = sizingBeamDetailsRepository;
        this.sizingBeamOutwardRepository = sizingBeamOutwardRepository;
        this.sizingProductionRepository = sizingProductionRepository;
        this.warpingProductionRepository = warpingProductionRepository;
        this.weavingContractRepository = weavingContractRepository;
        this.yarnMasterRepository = yarnMasterRepository;
        this.yarnReturnRepository = yarnReturnRepository;
        this.sizingYarnOutwardRepository = sizingYarnOutwardRepository;
        this.sizingYarnInwardRepository = sizingYarnInwardRepository;
        this.sizingPlanRepository = sizingPlanRepository;
        this.sizingQualityDetailsRepository = sizingQualityDetailsRepository;
        this.referenceValidationService = referenceValidationService;
        this.companyRepository = companyRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public SizingJobWorkOrderDTO create(SizingJobWorkOrderDTO dto) {
        requireSizingPlanReference(dto.getSizingPlanId());
        SizingJobWorkOrder entity = mapDtoToEntity(dto, new SizingJobWorkOrder());
        SizingJobWorkOrder saved = repository.save(entity);
        saved = assignSizingWorkOrderNoIfMissing(saved);
        syncToSizingPlan(saved);
        updateMappedStatus(saved.getId());
        return mapEntityToDto(saved);
    }

    public PaginatedResponse<SizingJobWorkOrderDTO> getAll(Long factoryCompanyId, Long offset, Long limit) {
        long finalLimit = limit != null && limit > 0 ? limit : 10L;
        long finalOffset = offset != null && offset >= 0 ? offset : 0L;
        PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
        Page<SizingJobWorkOrder> page = factoryCompanyId != null
            ? repository.findByFactoryCompanyId(factoryCompanyId, pageRequest)
            : repository.findAll(pageRequest);
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public PaginatedResponse<SizingJobWorkOrderDTO> getAllByFactoryCompanyId(Long factoryCompanyId, Long offset, Long limit) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("factoryCompanyId is required");
        }
        return getAll(factoryCompanyId, offset, limit);
    }

    public SizingJobWorkOrderDTO getById(Long id) {
        return getById(id, null);
    }

    public SizingJobWorkOrderDTO getById(Long id, Long factoryCompanyId) {
        updateMappedStatus(id);
        SizingJobWorkOrder entity = factoryCompanyId != null
            ? repository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found for factoryCompanyId: " + factoryCompanyId))
            : repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + id));
        return mapEntityToDto(entity);
    }

    @Transactional
    public SizingJobWorkOrderDTO update(Long id, SizingJobWorkOrderDTO dto) {
        return update(id, dto, null);
    }

    @Transactional
    public SizingJobWorkOrderDTO update(Long id, SizingJobWorkOrderDTO dto, Long factoryCompanyId) {
        SizingJobWorkOrder entity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + id));
        if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
            throw new EntityNotFoundException("SizingJobWorkOrder not found for factoryCompanyId: " + factoryCompanyId);
        }
        if (factoryCompanyId != null) {
            dto.setFactoryCompanyId(entity.getFactoryCompanyId());
        }
        if (!StringUtils.hasText(dto.getSizingWorkOrderNo())) {
            dto.setSizingWorkOrderNo(entity.getSizingWorkOrderNo());
        }
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setFactoryCompanyId(entity.getFactoryCompanyId());
        dto.setTradingCompanyId(entity.getTradingCompanyId());
        requireSizingPlanReference(dto.getSizingPlanId());
        mapDtoToEntity(dto, entity);
        SizingJobWorkOrder saved = repository.save(entity);
        saved = assignSizingWorkOrderNoIfMissing(saved);
        syncToSizingPlan(saved);
        updateMappedStatus(saved.getId());
        return mapEntityToDto(saved);
    }

    private void syncToSizingPlan(SizingJobWorkOrder entity) {
        if (entity.getSizingPlanId() == null) {
            return;
        }

        sizingPlanRepository.findById(entity.getSizingPlanId()).ifPresent(plan -> {
            plan.setSizingPlanDate(entity.getOrderDate());
            plan.setSizingRate(entity.getSizingRatePerKg());
            // Preserve existing remarks in SizingPlan - don't overwrite with SizingJobWorkOrder.remarks
            // Only update remarks if both are not null to avoid data loss
            if (entity.getRemarks() != null && plan.getRemarks() == null) {
                plan.setRemarks(entity.getRemarks());
            }
            plan.setOrderTypeId(entity.getOrderTypeId());
            plan.setFactoryCompanyId(entity.getFactoryCompanyId());
            plan.setTradingCompanyId(entity.getTradingCompanyId());
            sizingPlanRepository.save(plan);

            if (entity.getItems() != null) {
                for (SizingJobWorkOrderItem item : entity.getItems()) {
                    if (item.getPlanDetailId() != null) {
                        sizingQualityDetailsRepository.findById(item.getPlanDetailId()).ifPresent(detail -> {
                            detail.setActualYarn(item.getYarnId());
                            detail.setActualWarpEnds(item.getTotalEnds() != null ? item.getTotalEnds().longValue() : null);
                            detail.setParts(item.getNumberOfParts() != null ? item.getNumberOfParts().longValue() : null);
                            detail.setEndsPerPart(item.getEndsPerPart() != null ? item.getEndsPerPart().longValue() : null);
                            detail.setWarpMeters(item.getWarpMeters() != null ? item.getWarpMeters().longValue() : null);
                            sizingQualityDetailsRepository.save(detail);
                        });
                    }
                }
            }
        });
    }

    public void delete(Long id) {
        delete(id, null);
    }

    public void delete(Long id, Long factoryCompanyId) {
        SizingJobWorkOrder entity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + id));
        if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
            throw new EntityNotFoundException("SizingJobWorkOrder not found for factoryCompanyId: " + factoryCompanyId);
        }
        ensureNoSizingJobWorkOrderReferences(entity);
        deleteEntity(entity);
    }

    @Transactional
    public SizingJobWorkOrder createOrUpdateFromSizingPlan(SizingPlan plan, List<SizingQualityDetails> qualityDetails) {
        if (plan == null || plan.getId() == null) {
            throw new IllegalArgumentException("Sizing plan is required");
        }
        SizingJobWorkOrder entity = repository.findBySizingPlanId(plan.getId()).orElseGet(SizingJobWorkOrder::new);
        boolean isNew = entity.getId() == null;
        entity.setSizingPlanId(plan.getId());
        entity.setOrderDate(plan.getSizingPlanDate());
        entity.setOrderTypeId(plan.getOrderTypeId());
        entity.setSizingRatePerKg(plan.getSizingRate());
        entity.setRemarks(plan.getRemarks());
        CompanyAssignments assignments = resolveCompanyAssignments(plan);
        entity.setBuyerId(assignments.getBuyerId());
        entity.setConsigneeId(assignments.getConsigneeId());
        entity.setFactoryCompanyId(assignments.getFactoryCompanyId());
        entity.setTradingCompanyId(assignments.getTradingCompanyId());
        if (entity.getActiveFlag() == null) {
            entity.setActiveFlag(Boolean.TRUE);
        }
        applyItemsFromPlanDetails(entity, qualityDetails);
        SizingJobWorkOrder saved = repository.save(entity);
        updateMappedStatus(saved.getId());
        SizingJobWorkOrder result = assignSizingWorkOrderNoIfMissing(saved);
        if (isNew && result.getFactoryCompanyId() != null && result.getTradingCompanyId() != null) {
            try {
                notificationService.createAndPush(
                    result.getFactoryCompanyId(),
                    result.getTradingCompanyId(),
                    NotificationService.EVENT_SIZING_JOBWORK,
                    result.getId(),
                    null, null
                );
            } catch (Exception ignored) {}
        }
        return result;
    }

    @Transactional
    public void deleteBySizingPlanId(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return;
        }
        repository.findBySizingPlanId(sizingPlanId).ifPresent(entity -> {
            ensureNoSizingJobWorkOrderReferences(entity);
            deleteEntity(entity);
        });
    }

    private void requireSizingPlanReference(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return;
        }
        Optional<SizingPlan> sizingPlan = sizingPlanRepository.findById(sizingPlanId);
        String displayIdentifier = sizingPlan
                .map(SizingPlan::getSizingPlanNo)
                .filter(StringUtils::hasText)
                .orElse("Sizing Plan ID " + sizingPlanId);
        referenceValidationService.requireReference(
                sizingPlanId,
                () -> sizingPlan,
                "Sizing Plan",
                displayIdentifier);
    }

    private void deleteEntity(SizingJobWorkOrder entity) {
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete SizingJobWorkOrder because it is referenced by other records", e);
        }
    }

    @Transactional
    public void updateMappedStatus(Long sizingOrderId) {
        if (sizingOrderId == null) {
            return;
        }
        repository.findById(sizingOrderId).ifPresent(order -> {
            boolean isIdMapped = isSizingOrderIdMapped(order);
            boolean isNoMapped = isSizingOrderNoMapped(order);
            
            short newIdMappedValue = isIdMapped ? (short) 1 : (short) 0;
            short newNoMappedValue = isNoMapped ? (short) 1 : (short) 0;
            
            if (order.getIsOrderIdMapped() == null || order.getIsOrderIdMapped() != newIdMappedValue) {
                order.setIsOrderIdMapped(newIdMappedValue);
                repository.save(order);
            }
        });
    }

    @Transactional
    public SizingJobWorkOrder markAsOrderIdMapped(String sizingWorkOrderNo) {
        if (!StringUtils.hasText(sizingWorkOrderNo)) {
            throw new IllegalArgumentException("SizingWorkOrderNo is required");
        }
        String trimmedNo = sizingWorkOrderNo.trim();
        SizingJobWorkOrder wo = repository.findBySizingWorkOrderNo(trimmedNo)
                .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with sizingWorkOrderNo: " + trimmedNo));
        return markOrderIdMapped(wo);
    }

    @Transactional
    public SizingJobWorkOrder markAsOrderIdMapped(Long sizingWorkOrderId) {
        if (sizingWorkOrderId == null) {
            throw new IllegalArgumentException("SizingWorkOrderId is required");
        }
        SizingJobWorkOrder wo = repository.findById(sizingWorkOrderId)
                .orElseThrow(() -> new EntityNotFoundException("SizingJobWorkOrder not found with id: " + sizingWorkOrderId));
        return markOrderIdMapped(wo);
    }

    private SizingJobWorkOrder markOrderIdMapped(SizingJobWorkOrder wo) {
        if (Short.valueOf((short) 1).equals(wo.getIsOrderIdMapped())) {
            return wo;
        }
        wo.setIsOrderIdMapped((short) 1);
        return repository.save(wo);
    }

    private boolean isSizingOrderMapped(SizingJobWorkOrder entity) {
        return isSizingOrderIdMapped(entity) || isSizingOrderNoMapped(entity);
    }

    private boolean isSizingOrderIdMapped(SizingJobWorkOrder entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        
        if (entity.getItems() != null && !entity.getItems().isEmpty()) {
            return true;
        }

        Long jobId = entity.getId();

        if (warpingProductionRepository.existsBySizingOrderId(jobId)) {
            return true;
        }
        if (sizingProductionRepository.existsBySizingOrderId(jobId)) {
            return true;
        }
        if (sizingBeamOutwardRepository.existsBySizingOrderId(jobId)) {
            return true;
        }
        if (sizingYarnOutwardRepository.existsBySizingOrderId(jobId)) {
            return true;
        }
        if (sizingYarnInwardRepository.existsBySizingJobWorkOrderId(jobId)) {
            return true;
        }
        return false;
    }

    private boolean isSizingOrderNoMapped(SizingJobWorkOrder entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        String workOrderNo = StringUtils.hasText(entity.getSizingWorkOrderNo()) ? entity.getSizingWorkOrderNo().trim() : null;

        if (workOrderNo != null) {
            if (sizingBeamOutwardRepository.existsBySizingOrderNo(workOrderNo)) {
                return true;
            }
            if (yarnReturnRepository.existsByOrderNo(workOrderNo)) {
                return true;
            }
        }
        return false;
    }

    private void ensureNoSizingJobWorkOrderReferences(SizingJobWorkOrder entity) {
        if (entity == null || entity.getId() == null) {
            return;
        }
        Long jobId = entity.getId();
        String workOrderNo = StringUtils.hasText(entity.getSizingWorkOrderNo()) ? entity.getSizingWorkOrderNo().trim() : null;
        String orderIdentifier = workOrderNo != null ? "sizing_order_no " + workOrderNo : "sizing_order_id " + jobId;
        if (warpingProductionRepository.existsBySizingOrderId(jobId)) {
            throw new IllegalStateException("Cannot delete SizingJobWorkOrder because warping_production references " + orderIdentifier);
        }
        if (sizingProductionRepository.existsBySizingOrderId(jobId)) {
            throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_production references " + orderIdentifier);
        }
        if (sizingBeamOutwardRepository.existsBySizingOrderId(jobId)) {
            throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_beam_outward references " + orderIdentifier);
        }
        if (sizingYarnOutwardRepository.existsBySizingOrderId(jobId)) {
            throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_yarn_outward references " + orderIdentifier);
        }
        if (sizingYarnInwardRepository.existsBySizingJobWorkOrderId(jobId)) {
            throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_yarn_inward references " + orderIdentifier);
        }
        if (workOrderNo != null) {
            if (sizingBeamOutwardRepository.existsBySizingOrderNo(workOrderNo)) {
                throw new IllegalStateException("Cannot delete SizingJobWorkOrder because sizing_beam_outward references sizing_order_no " + workOrderNo);
            }
            if (yarnReturnRepository.existsByOrderNo(workOrderNo)) {
                throw new IllegalStateException("Cannot delete SizingJobWorkOrder because yarn_return references order_no " + workOrderNo);
            }
        }
    }

    private CompanyAssignments resolveCompanyAssignments(SizingPlan plan) {
        Long planCompanyId = plan != null ? plan.getCompanyId() : null;
        Long planTradingCompanyId = plan != null ? plan.getTradingCompanyId() : null;
        Long planFactoryCompanyId = plan != null ? plan.getFactoryCompanyId() : null;
        Long orderTypeId = plan != null ? plan.getOrderTypeId() : null;
        Long tradingCompanyId = planTradingCompanyId;
        Long factoryCompanyId = planFactoryCompanyId;
        WeavingContract contract = null;
        Long weavingContractId = findWeavingContractId(plan);
        if (weavingContractId != null) {
            contract = weavingContractRepository.findById(weavingContractId).orElse(null);
        }
        if (contract != null) {
            if (tradingCompanyId == null) {
                tradingCompanyId = contract.getTradingCompanyId() != null ? contract.getTradingCompanyId() : contract.getCompanyId();
            }
            if (factoryCompanyId == null) {
                factoryCompanyId = contract.getFactoryCompanyId() != null ? contract.getFactoryCompanyId() : contract.getCompanyId();
            }
            if (orderTypeId == null) {
                orderTypeId = contract.getOrderTypeId();
            }
        }
        boolean factoryOnly = orderTypeId != null && orderTypeId == 2L;
        if (factoryOnly) {
            tradingCompanyId = null;
            factoryCompanyId = coalesce(factoryCompanyId, planFactoryCompanyId, planCompanyId);
        } else {
            tradingCompanyId = coalesce(tradingCompanyId, planTradingCompanyId, planCompanyId);
            factoryCompanyId = coalesce(factoryCompanyId, planFactoryCompanyId, tradingCompanyId, planCompanyId);
        }
        Long buyerId = coalesce(tradingCompanyId, planTradingCompanyId, planCompanyId);
        Long consigneeId = buyerId;
        return new CompanyAssignments(buyerId, consigneeId, factoryCompanyId, tradingCompanyId);
    }

    private Long findWeavingContractId(SizingPlan plan) {
        if (plan == null || plan.getId() == null) {
            return null;
        }
        return sizingBeamDetailsRepository.findBySizingPlanId(plan.getId()).stream()
            .map(SizingBeamDetails::getWeavingContractId)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    private Long coalesce(Long... values) {
        if (values == null) {
            return null;
        }
        for (Long value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private static class CompanyAssignments {
        private final Long buyerId;
        private final Long consigneeId;
        private final Long factoryCompanyId;
        private final Long tradingCompanyId;

        private CompanyAssignments(Long buyerId, Long consigneeId, Long factoryCompanyId, Long tradingCompanyId) {
            this.buyerId = buyerId;
            this.consigneeId = consigneeId;
            this.factoryCompanyId = factoryCompanyId;
            this.tradingCompanyId = tradingCompanyId;
        }

        private Long getBuyerId() {
            return buyerId;
        }

        private Long getConsigneeId() {
            return consigneeId;
        }

        private Long getFactoryCompanyId() {
            return factoryCompanyId;
        }

        private Long getTradingCompanyId() {
            return tradingCompanyId;
        }
    }

    private PaginatedResponse<SizingJobWorkOrderDTO> buildPaginatedResponse(Page<SizingJobWorkOrder> page, long offset, long limit) {
        List<SizingJobWorkOrderDTO> content = page.getContent().stream()
            .map(this::mapEntityToDto)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(page.getTotalElements(), !page.isLast(), page.getNumber() > 0, offset, limit, content);
    }

    private SizingJobWorkOrder mapDtoToEntity(SizingJobWorkOrderDTO dto, SizingJobWorkOrder entity) {
        boolean hasDtoWorkOrderNo = StringUtils.hasText(dto.getSizingWorkOrderNo());
        boolean isAutoGeneratedType = dto.getOrderTypeId() != null && dto.getOrderTypeId() == 1L;
        if (hasDtoWorkOrderNo && (entity.getId() != null || !isAutoGeneratedType)) {
            entity.setSizingWorkOrderNo(dto.getSizingWorkOrderNo());
        }
        entity.setOrderDate(dto.getOrderDate());
        entity.setOrderTypeId(dto.getOrderTypeId());
        entity.setBuyerId(dto.getBuyerId());
        entity.setConsigneeId(dto.getConsigneeId());
        entity.setGstTypeId(dto.getGstTypeId());
        entity.setSizingPlanId(dto.getSizingPlanId());
        entity.setSizingRatePerKg(dto.getSizingRatePerKg());
        entity.setFactoryCompanyId(dto.getFactoryCompanyId());
        entity.setTradingCompanyId(dto.getTradingCompanyId());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : Boolean.TRUE);
        entity.setIsApproved(dto.getIsApproved());
        entity.setIsComplete(dto.getIsComplete());
        // if (dto.getIsOrderMapped() != null) {
        //     entity.setIsOrderMapped(dto.getIsOrderMapped());
        // } else if (entity.getId() == null) {
        //     entity.setIsOrderMapped((short) 0);
        // }
        if (dto.getIsOrderIdMapped() != null) {
            entity.setIsOrderIdMapped(dto.getIsOrderIdMapped());
        } else if (entity.getId() == null) {
            entity.setIsOrderIdMapped((short) 0);
        }
        entity.setUserId(dto.getUserId());
        entity.setComments(dto.getComments());
        if (dto.getApproveDate() != null) {
            entity.setApproveDate(dto.getApproveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        entity.setRemarks(dto.getRemarks());
        applyItems(entity, dto.getItems());
        return entity;
    }

    private void applyItems(SizingJobWorkOrder entity, List<SizingJobWorkOrderItemDTO> itemDTOs) {
        if (entity.getItems() == null) {
            entity.setItems(new ArrayList<>());
        }
        List<SizingJobWorkOrderItem> current = entity.getItems();
        Map<Long, SizingJobWorkOrderItem> existing = current.stream()
            .filter(item -> item.getId() != null)
            .collect(Collectors.toMap(SizingJobWorkOrderItem::getId, item -> item, (a, b) -> a));
        List<SizingJobWorkOrderItem> updated = new ArrayList<>();
        if (itemDTOs != null) {
            for (SizingJobWorkOrderItemDTO dto : itemDTOs) {
                if (!hasItemData(dto)) {
                    continue;
                }
                SizingJobWorkOrderItem item = dto.getId() != null && existing.containsKey(dto.getId())
                    ? existing.remove(dto.getId())
                    : new SizingJobWorkOrderItem();
                item.setSizingJobWorkOrder(entity);
                item.setPlanDetailId(dto.getPlanDetailId());
                item.setYarnId(dto.getYarnId());
                item.setYarnName(dto.getYarnName());
                item.setTotalEnds(dto.getTotalEnds());
                item.setNumberOfParts(dto.getNumberOfParts());
                item.setEndsPerPart(dto.getEndsPerPart());
                item.setWarpMeters(dto.getWarpMeters());
                item.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : Boolean.TRUE);
                updated.add(item);
            }
        }
        current.clear();
        current.addAll(updated);
    }

    private boolean hasItemData(SizingJobWorkOrderItemDTO dto) {
        if (dto == null) {
            return false;
        }
        if (dto.getYarnId() != null) {
            return true;
        }
        if (dto.getPlanDetailId() != null) {
            return true;
        }
        return dto.getYarnName() != null && !dto.getYarnName().trim().isEmpty();
    }

    private SizingJobWorkOrderDTO mapEntityToDto(SizingJobWorkOrder entity) {
        SizingJobWorkOrderDTO dto = new SizingJobWorkOrderDTO();
        dto.setId(entity.getId());
        dto.setSizingWorkOrderNo(entity.getSizingWorkOrderNo());
        dto.setOrderDate(entity.getOrderDate());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setBuyerId(entity.getBuyerId());
        dto.setConsigneeId(entity.getConsigneeId());
        dto.setGstTypeId(entity.getGstTypeId());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setSizingRatePerKg(entity.getSizingRatePerKg());
        dto.setFactoryCompanyId(entity.getFactoryCompanyId());
        dto.setTradingCompanyId(entity.getTradingCompanyId());
        dto.setActiveFlag(entity.getActiveFlag());
        dto.setIsApproved(entity.getIsApproved());
        dto.setIsComplete(entity.getIsComplete());
        // dto.setIsOrderMapped(entity.getIsOrderMapped());
        dto.setIsOrderIdMapped(entity.getIsOrderIdMapped());
        dto.setUserId(entity.getUserId());
        dto.setComments(entity.getComments());
        if (entity.getApproveDate() != null) {
            dto.setApproveDate(Date.from(entity.getApproveDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        dto.setRemarks(entity.getRemarks());
        List<SizingJobWorkOrderItemDTO> items = entity.getItems() == null ? new ArrayList<>() : entity.getItems().stream()
            .map(this::mapItemToDto)
            .collect(Collectors.toList());
        dto.setItems(items);
        return dto;
    }

    private SizingJobWorkOrderItemDTO mapItemToDto(SizingJobWorkOrderItem item) {
        SizingJobWorkOrderItemDTO dto = new SizingJobWorkOrderItemDTO();
        dto.setId(item.getId());
        dto.setPlanDetailId(item.getPlanDetailId());
        dto.setYarnId(item.getYarnId());
        dto.setYarnName(item.getYarnName());
        dto.setTotalEnds(item.getTotalEnds());
        dto.setNumberOfParts(item.getNumberOfParts());
        dto.setEndsPerPart(item.getEndsPerPart());
        dto.setWarpMeters(item.getWarpMeters());
        dto.setActiveFlag(item.getActiveFlag());
        return dto;
    }

    private void applyItemsFromPlanDetails(SizingJobWorkOrder entity, List<SizingQualityDetails> details) {
        if (entity.getItems() == null) {
            entity.setItems(new ArrayList<>());
        }
        List<SizingJobWorkOrderItem> items = entity.getItems();
        items.clear();
        if (details == null) {
            return;
        }
        boolean requiresYarnNames = entity.getOrderTypeId() != null && entity.getOrderTypeId() == 1L;
        Map<Long, String> yarnNames = requiresYarnNames ? resolveYarnNames(details) : Collections.emptyMap();
        for (SizingQualityDetails detail : details) {
            SizingJobWorkOrderItem item = new SizingJobWorkOrderItem();
            item.setSizingJobWorkOrder(entity);
            item.setPlanDetailId(detail.getId());
            Long yarnId = resolveYarnId(detail);
            item.setYarnId(yarnId);
            String yarnName = detail.getQuality();
            if (requiresYarnNames && yarnId != null) {
                yarnName = yarnNames.getOrDefault(yarnId, yarnName);
            }
            item.setYarnName(yarnName);
            item.setTotalEnds(toDouble(detail.getActualWarpEnds() != null ? detail.getActualWarpEnds() : detail.getWarpEnds()));
            item.setNumberOfParts(toDouble(detail.getParts()));
            item.setEndsPerPart(toDouble(detail.getEndsPerPart()));
            item.setWarpMeters(toDouble(detail.getWarpMeters()));
            item.setActiveFlag(Boolean.TRUE);
            items.add(item);
        }
    }

    private Map<Long, String> resolveYarnNames(List<SizingQualityDetails> details) {
        Set<Long> yarnIds = details.stream()
            .map(this::resolveYarnId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        if (yarnIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return yarnMasterRepository.findAllById(yarnIds).stream()
            .collect(Collectors.toMap(YarnMaster::getId, YarnMaster::getYarnName));
    }

    private Long resolveYarnId(SizingQualityDetails detail) {
        if (detail == null) {
            return null;
        }
        return detail.getActualYarn() != null ? detail.getActualYarn() : detail.getYarnId();
    }

    private Double toDouble(Long value) {
        return value != null ? value.doubleValue() : null;
    }

    private SizingJobWorkOrder assignSizingWorkOrderNoIfMissing(SizingJobWorkOrder entity) {
        if (entity == null) {
            return null;
        }
        if (!StringUtils.hasText(entity.getSizingWorkOrderNo())) {
            Long companyId = entity.getTradingCompanyId() != null ? entity.getTradingCompanyId() : entity.getFactoryCompanyId();
            entity.setSizingWorkOrderNo(generateSizingJobWorkOrderNo(entity.getOrderDate(), entity.getId(), companyId));
            entity = repository.save(entity);
        }
        return entity;
    }

    private String generateSizingJobWorkOrderNo(LocalDate orderDate, Long id, Long companyId) {
        if (id == null) {
            return null;
        }
        String cmpPrefix = "";
        if (companyId != null) {
            Company company = companyRepository.findById(companyId).orElse(null);
            if (company != null) {
                if (StringUtils.hasText(company.getCompanyCode())) {
                    cmpPrefix = company.getCompanyCode() + "/";
                } else if (StringUtils.hasText(company.getCompanyName())) {
                    cmpPrefix = company.getCompanyName().substring(0, 1).toUpperCase() + "/";
                }
            }
        }
        LocalDate referenceDate = orderDate != null ? orderDate : LocalDate.now();
        String fy = getFinancialYear(referenceDate);
        String prefix = cmpPrefix + "SJO/" + fy + "/";
        String sequence = String.format("%03d", id);
        return prefix + sequence;
    }

    private String getFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        int fyStartYear = month >= 4 ? year : year - 1;
        int fy1 = fyStartYear % 100;
        int fy2 = (fyStartYear + 1) % 100;
        return String.format("%02d-%02d", fy1, fy2);
    }
}
