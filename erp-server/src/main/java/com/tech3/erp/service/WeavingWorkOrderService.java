// package com.tech3.erp.service;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.WeavingWorkOrderDetailDTO;
// import com.tech3.erp.dto.WeavingWorkOrderDTO;
// import com.tech3.erp.dto.WeavingWorkOrderItemDTO;
// import com.tech3.erp.dto.WeavingWorkOrderYarnRequirementDTO;
// import com.tech3.erp.entity.WeavingWorkOrder;
// import com.tech3.erp.entity.WeavingWorkOrderItem;
// import com.tech3.erp.entity.WeavingWorkOrderDetail;
// import com.tech3.erp.entity.WeavingWorkOrderYarnRequirement;
// import com.tech3.erp.repository.BaleRollPackingRepository;
// import com.tech3.erp.repository.BeamGatingDetailsRepository;
// import com.tech3.erp.repository.BeamOutwardRepository;
// import com.tech3.erp.repository.FabricInspectionRepository;
// import com.tech3.erp.repository.FactoryBeamInwardRepository;
// import com.tech3.erp.repository.GenerateInvoiceRepository;
// import com.tech3.erp.repository.GeneratePackingRepository;
// import com.tech3.erp.repository.WeavingWorkOrderRepository;
// import com.tech3.erp.util.PaginationHelper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationEventPublisher;
// import org.springframework.transaction.annotation.Transactional;

// import jakarta.persistence.EntityNotFoundException;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;
// import org.springframework.util.StringUtils;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// @Service
// public class WeavingWorkOrderService {

//     @Autowired
//     private WeavingWorkOrderRepository workOrderRepo;

//     @Autowired
//     private FactoryBeamInwardRepository factoryBeamInwardRepository;

//     @Autowired
//     private BeamOutwardRepository beamOutwardRepository;

//     @Autowired
//     private BaleRollPackingRepository baleRollPackingRepository;

//     @Autowired
//     private GeneratePackingRepository generatePackingRepository;

//     @Autowired
//     private GenerateInvoiceRepository generateInvoiceRepository;

//     @Autowired
//     private FabricInspectionRepository fabricInspectionRepository;

//     @Autowired
//     private BeamGatingDetailsRepository beamGatingDetailsRepository;

//     @Autowired
//     private ApplicationEventPublisher eventPublisher;

//     public WeavingWorkOrder create(WeavingWorkOrderDTO dto) {
//         WeavingWorkOrder wo = new WeavingWorkOrder();
//         String providedWorkOrderNo = dto.getWeavingWorkOrderNo();
//         wo.setOrderDate(dto.getOrderDate());
//         wo.setWeavingContractId(dto.getWeavingContractId());
//         wo.setOrderTypeId(dto.getOrderTypeId());
//         wo.setBuyerId(dto.getBuyerId());
//         wo.setConsigneeId(dto.getConsigneeId());
//         wo.setGstTypeId(dto.getGstTypeId());
//         wo.setTermsConditionsId(dto.getTermsConditionsId());
//         wo.setPaymentTermsId(dto.getPaymentTermsId());
//         wo.setPackingTypeId(dto.getPackingTypeId());
//         applyCompanyAssignments(wo, dto);
//         wo.setRemarks(dto.getRemarks());
//         wo.setActiveFlag(true);
//         wo.setIsCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : false);
//         wo.setIsApproved(dto.getIsApproved() != null ? dto.getIsApproved() : (short) 0);
//         wo.setIsOrderIdMapped(dto.getIsOrderIdMapped() != null ? dto.getIsOrderIdMapped() : (short) 0);
//         wo.setApproveDate(dto.getApproveDate());
//         wo.setComments(dto.getComments());
//         wo.setUserId(dto.getUserId());
//         wo.setWeavingWorkOrderNo(providedWorkOrderNo);
        
//         // Only require and set salesOrderNo if NOT internal (orderTypeId != 1)
//         boolean isInternal = dto.getOrderTypeId() != null && dto.getOrderTypeId() == 1L;
//         if (!isInternal) {
//             if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
//                 wo.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
//             } else {
//                 throw new IllegalArgumentException("At least one order detail with valid sales order ID is required for non-internal orders");
//             }
//         }
        
//         List<WeavingWorkOrderItem> items = (dto.getItems() == null ? new ArrayList<WeavingWorkOrderItemDTO>() : dto.getItems())
//                 .stream()
//                 .filter((WeavingWorkOrderItemDTO i) -> i.getFabricCodeId() != null)
//                 .map((WeavingWorkOrderItemDTO i) -> {
//                     WeavingWorkOrderItem e = new WeavingWorkOrderItem();
//                     e.setFabricCodeId(i.getFabricCodeId());
//                     e.setFabricQualityId(i.getFabricQualityId());
//                     e.setUomId(i.getUomId());
//                     e.setQuantity(i.getQuantity());
//                     e.setPickCost(i.getPickCost());
//                     e.setRatePerUnit(i.getRatePerUnit());
//                     e.setPlannedStartDate(i.getPlannedStartDate());
//                     e.setPlannedEndDate(i.getPlannedEndDate());
//                     e.setDailyTarget(i.getDailyTarget());
//                     e.setNumberOfLooms(i.getNumberOfLooms());
//                     e.setWarpLength(i.getWarpLength());
//                     e.setWarpCrimpPercentage(i.getWarpCrimpPercentage());
//                     e.setPieceLength(i.getPieceLength());
//                     e.setNumberOfPieces(i.getNumberOfPieces());
//                     e.setPpi(i.getPpi());
//                     e.setTaxableAmount(i.getTaxableAmount());
//                     e.setGstPercent(i.getGstPercent());
//                     e.setTotalAmount(i.getTotalAmount());
//                     e.setWeavingWorkOrder(wo);
//                     e.setActiveFlag(i.getActiveFlag() != null ? i.getActiveFlag() : true);
//                     return e;
//                 }).collect(Collectors.toList());
//         List<WeavingWorkOrderYarnRequirement> yarns = (dto.getYarnRequirements() == null ? new ArrayList<WeavingWorkOrderYarnRequirementDTO>() : dto.getYarnRequirements())
//                 .stream()
//                 .filter((WeavingWorkOrderYarnRequirementDTO y) -> y.getYarnType() != null && !y.getYarnType().isEmpty())
//                 .map((WeavingWorkOrderYarnRequirementDTO y) -> {
//                     WeavingWorkOrderYarnRequirement e = new WeavingWorkOrderYarnRequirement();
//                     e.setYarnType(y.getYarnType());
//                     e.setYarnCount(y.getYarnCount());
//                     e.setGramsPerMeter(y.getGramsPerMeter());
//                     e.setTotalWeavingOrderQty(y.getTotalWeavingOrderQty());
//                     e.setTotalRequiredQty(y.getTotalRequiredQty());
//                     e.setTotalAvailableQty(y.getTotalAvailableQty());
//                     e.setWeavingWorkOrder(wo);
//                     e.setActiveFlag(y.getActiveFlag() != null ? y.getActiveFlag() : true);
//                     return e;
//                 }).collect(Collectors.toList());
//         List<WeavingWorkOrderDetail> orders = (dto.getOrderDetails() == null ? new ArrayList<WeavingWorkOrderDetailDTO>() : dto.getOrderDetails())
//                 .stream()
//                 .filter((WeavingWorkOrderDetailDTO o) -> o.getSalesOrderId() != null)
//                 .map((WeavingWorkOrderDetailDTO o) -> {
//                     WeavingWorkOrderDetail e = new WeavingWorkOrderDetail();
//                     e.setSalesOrderId(o.getSalesOrderId());
//                     e.setOrderQuantity(o.getOrderQuantity());
//                     e.setPlannedQuantity(o.getPlannedQuantity());
//                     e.setTotalQuantity(o.getTotalQuantity());
//                     e.setWeavingWorkOrder(wo);
//                     e.setActiveFlag(o.getActiveFlag() != null ? o.getActiveFlag() : true);
//                     return e;
//                 }).collect(Collectors.toList());
//         wo.setItems(items);
//         wo.setYarnRequirements(yarns);
//         wo.setOrderDetails(orders);
//         WeavingWorkOrder saved = workOrderRepo.save(wo);
//         if (!hasText(providedWorkOrderNo)) {
//             String generated = generateWeavingWorkOrderNo(saved.getOrderDate(), saved.getId());
//             if (hasText(generated)) {
//                 saved.setWeavingWorkOrderNo(generated);
//                 saved = workOrderRepo.save(saved);
//             }
//         }
//         return saved;
//     }


//     public List<WeavingWorkOrder> getAll() {
//         return workOrderRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
//     }

//     public PaginatedResponse<WeavingWorkOrder> getAllWithPagination(Long offset, Long limit, Long factoryCompanyId) {
//         List<WeavingWorkOrder> list = factoryCompanyId == null
//                 ? getAll()
//                 : getAllByFactoryCompanyId(factoryCompanyId);
//         return PaginationHelper.paginate(list, offset, limit);
//     }

//     public List<WeavingWorkOrder> getAllByFactoryCompanyId(Long factoryCompanyId) {
//         return workOrderRepo.findByFactoryCompanyId(factoryCompanyId, Sort.by(Sort.Direction.DESC, "id"));
//     }

//     public PaginatedResponse<WeavingWorkOrder> getAllByFactoryCompanyIdWithPagination(Long factoryCompanyId, Long offset, Long limit) {
//         List<WeavingWorkOrder> list = getAllByFactoryCompanyId(factoryCompanyId);
//         return PaginationHelper.paginate(list, offset, limit);
//     }

//     public WeavingWorkOrder getById(Long id) {
//         return workOrderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
//     }

//     public WeavingWorkOrder update(Long id, WeavingWorkOrderDTO dto) {
//         return update(id, dto, null, true);
//     }

//     public WeavingWorkOrder update(Long id, WeavingWorkOrderDTO dto, Long factoryCompanyId) {
//         return update(id, dto, factoryCompanyId, true);
//     }

//     @Transactional
//     public WeavingWorkOrder update(Long id, WeavingWorkOrderDTO dto, Long factoryCompanyId, boolean publishEvent) {
//         WeavingWorkOrder wo = workOrderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
        
//         // Security check: only validate factoryCompanyId if order is NOT internal (orderTypeId != 1)
//         // This prevents EntityNotFoundException for internal orders which might have different company mappings
//         boolean isInternal = wo.getOrderTypeId() != null && wo.getOrderTypeId() == 1L;
        
//         if (!isInternal && factoryCompanyId != null && wo.getFactoryCompanyId() != null && !factoryCompanyId.equals(wo.getFactoryCompanyId())) {
//             throw new EntityNotFoundException("WeavingWorkOrder not found for the specified factoryCompanyId");
//         }

//         wo.setOrderDate(dto.getOrderDate());
//         wo.setWeavingContractId(dto.getWeavingContractId());
        
//         // Only update critical fields if NOT internal
//         if (!isInternal) {
//             wo.setOrderTypeId(dto.getOrderTypeId());
//             wo.setWeavingWorkOrderNo(dto.getWeavingWorkOrderNo());
//             applyCompanyAssignments(wo, dto);
//             if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
//                 wo.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
//             }
//         }
        
//         wo.setBuyerId(dto.getBuyerId());
//         wo.setConsigneeId(dto.getConsigneeId());
//         wo.setGstTypeId(dto.getGstTypeId());
//         wo.setTermsConditionsId(dto.getTermsConditionsId());
//         wo.setPaymentTermsId(dto.getPaymentTermsId());
//         wo.setPackingTypeId(dto.getPackingTypeId());
//         wo.setRemarks(dto.getRemarks());
//         wo.setIsCompleted(dto.getIsCompleted());
//         wo.setIsApproved(dto.getIsApproved());
//         if (dto.getIsOrderIdMapped() != null) {
//             wo.setIsOrderIdMapped(dto.getIsOrderIdMapped());
//         }
//         wo.setApproveDate(dto.getApproveDate());
//         wo.setComments(dto.getComments());
//         wo.setUserId(dto.getUserId());

//         updateItems(wo, dto.getItems());
//         updateYarnRequirements(wo, dto.getYarnRequirements());
//         updateOrderDetails(wo, dto.getOrderDetails());

//         WeavingWorkOrder saved = workOrderRepo.save(wo);
//         if (publishEvent && saved.getOrderTypeId() != null && saved.getOrderTypeId().equals(1L)) {
//             eventPublisher.publishEvent(new WeavingWorkOrderUpdatedEvent(saved.getId(), saved.getWeavingContractId(), saved.getOrderTypeId()));
//         }
//         return saved;
//     }

//     @Transactional
//     public WeavingWorkOrder markAsOrderIdMapped(String weavingWorkOrderNo) {
//         if (!hasText(weavingWorkOrderNo)) {
//             throw new IllegalArgumentException("WeavingWorkOrderNo is required");
//         }
//         String trimmedNo = weavingWorkOrderNo.trim();
//         WeavingWorkOrder wo = workOrderRepo.findByWeavingWorkOrderNo(trimmedNo)
//                 .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with weavingWorkOrderNo: " + trimmedNo));
//         return markOrderIdMapped(wo);
//     }

//     @Transactional
//     public WeavingWorkOrder markAsOrderIdMapped(Long weavingWorkOrderId) {
//         if (weavingWorkOrderId == null) {
//             throw new IllegalArgumentException("WeavingWorkOrderId is required");
//         }
//         WeavingWorkOrder wo = workOrderRepo.findById(weavingWorkOrderId)
//                 .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + weavingWorkOrderId));
//         return markOrderIdMapped(wo);
//     }

//     private WeavingWorkOrder markOrderIdMapped(WeavingWorkOrder wo) {
//         if (Short.valueOf((short) 1).equals(wo.getIsOrderIdMapped())) {
//             return wo;
//         }
//         wo.setIsOrderIdMapped((short) 1);
//         return workOrderRepo.save(wo);
//     }

//     private void updateItems(WeavingWorkOrder wo, List<WeavingWorkOrderItemDTO> itemDTOs) {
//         if (itemDTOs == null) return;
//         Map<Long, WeavingWorkOrderItem> existing = wo.getItems().stream().collect(Collectors.toMap(WeavingWorkOrderItem::getId, it -> it));
//         List<WeavingWorkOrderItem> updated = new ArrayList<>();
//         for (WeavingWorkOrderItemDTO dto : itemDTOs) {
//             if (dto.getFabricCodeId() == null) continue;
//             WeavingWorkOrderItem e = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new WeavingWorkOrderItem();
//             e.setWeavingWorkOrder(wo);
//             e.setFabricCodeId(dto.getFabricCodeId());
//             e.setFabricQualityId(dto.getFabricQualityId());
//             e.setUomId(dto.getUomId());
//             e.setQuantity(dto.getQuantity());
//             e.setPickCost(dto.getPickCost());
//             e.setRatePerUnit(dto.getRatePerUnit());
//             e.setPlannedStartDate(dto.getPlannedStartDate());
//             e.setPlannedEndDate(dto.getPlannedEndDate());
//             e.setDailyTarget(dto.getDailyTarget());
//             e.setNumberOfLooms(dto.getNumberOfLooms());
//             e.setWarpLength(dto.getWarpLength());
//             e.setWarpCrimpPercentage(dto.getWarpCrimpPercentage());
//             e.setPieceLength(dto.getPieceLength());
//             e.setNumberOfPieces(dto.getNumberOfPieces());
//             e.setPpi(dto.getPpi());
//             e.setTaxableAmount(dto.getTaxableAmount());
//             e.setGstPercent(dto.getGstPercent());
//             e.setTotalAmount(dto.getTotalAmount());
//             e.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
//             updated.add(e);
//         }
//         wo.getItems().clear();
//         wo.getItems().addAll(updated);
//     }

//     private void updateYarnRequirements(WeavingWorkOrder wo, List<WeavingWorkOrderYarnRequirementDTO> yarnDTOs) {
//         if (yarnDTOs == null) return;
//         Map<Long, WeavingWorkOrderYarnRequirement> existing = wo.getYarnRequirements().stream().collect(Collectors.toMap(WeavingWorkOrderYarnRequirement::getId, it -> it));
//         List<WeavingWorkOrderYarnRequirement> updated = new ArrayList<>();
//         for (WeavingWorkOrderYarnRequirementDTO dto : yarnDTOs) {
//             if (dto.getYarnType() == null || dto.getYarnType().isEmpty()) continue;
//             WeavingWorkOrderYarnRequirement e = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new WeavingWorkOrderYarnRequirement();
//             e.setWeavingWorkOrder(wo);
//             e.setYarnType(dto.getYarnType());
//             e.setYarnCount(dto.getYarnCount());
//             e.setGramsPerMeter(dto.getGramsPerMeter());
//             e.setTotalWeavingOrderQty(dto.getTotalWeavingOrderQty());
//             e.setTotalRequiredQty(dto.getTotalRequiredQty());
//             e.setTotalAvailableQty(dto.getTotalAvailableQty());
//             e.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
//             updated.add(e);
//         }
//         wo.getYarnRequirements().clear();
//         wo.getYarnRequirements().addAll(updated);
//     }

//     private void updateOrderDetails(WeavingWorkOrder wo, List<WeavingWorkOrderDetailDTO> orderDTOs) {
//         if (orderDTOs == null) return;
//         Map<Long, WeavingWorkOrderDetail> existing = wo.getOrderDetails().stream().collect(Collectors.toMap(WeavingWorkOrderDetail::getId, it -> it));
//         List<WeavingWorkOrderDetail> updated = new ArrayList<>();
//         for (WeavingWorkOrderDetailDTO dto : orderDTOs) {
//             if (dto.getSalesOrderId() == null) continue;
//             WeavingWorkOrderDetail e = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new WeavingWorkOrderDetail();
//             e.setWeavingWorkOrder(wo);
//             e.setSalesOrderId(dto.getSalesOrderId());
//             e.setOrderQuantity(dto.getOrderQuantity());
//             e.setPlannedQuantity(dto.getPlannedQuantity());
//             e.setTotalQuantity(dto.getTotalQuantity());
//             e.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
//             updated.add(e);
//         }
//         wo.getOrderDetails().clear();
//         wo.getOrderDetails().addAll(updated);
//     }

//     public void delete(Long id) {
//         WeavingWorkOrder entity = workOrderRepo.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
//         deleteEntity(entity);
//     }

//     public void delete(Long id, Long factoryCompanyId) {
//         WeavingWorkOrder entity = workOrderRepo.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
//         if (factoryCompanyId != null && entity.getFactoryCompanyId() != null && !factoryCompanyId.equals(entity.getFactoryCompanyId())) {
//             throw new EntityNotFoundException("WeavingWorkOrder not found for the specified factoryCompanyId");
//         }
//         deleteEntity(entity);
//     }

//     private void deleteEntity(WeavingWorkOrder entity) {
//         ensureNoWeavingWorkOrderReferences(entity);
//         try {
//             workOrderRepo.delete(entity);
//         } catch (DataIntegrityViolationException ex) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because it is referenced by other records");
//         }
//     }

//     private void ensureNoWeavingWorkOrderReferences(WeavingWorkOrder entity) {
//         if (entity == null || entity.getId() == null) {
//             return;
//         }
//         Long workOrderId = entity.getId();
//         String workOrderNo = StringUtils.hasText(entity.getWeavingWorkOrderNo()) ? entity.getWeavingWorkOrderNo().trim() : null;
//         String orderIdentifier = workOrderNo != null ? "weaving_work_order_no " + workOrderNo : "weaving_work_order_id " + workOrderId;
//         if (factoryBeamInwardRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because factory_beam_inward references " + orderIdentifier);
//         }
//         if (beamOutwardRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because beam_outward references " + orderIdentifier);
//         }
//         if (baleRollPackingRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because bale_roll_packing references " + orderIdentifier);
//         }
//         if (generatePackingRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because generate_packing references " + orderIdentifier);
//         }
//         if (generateInvoiceRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because generate_invoice references " + orderIdentifier);
//         }
//         if (fabricInspectionRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because fabric_inspection references " + orderIdentifier);
//         }
//         if (beamGatingDetailsRepository.existsByWeavingWorkOrderId(workOrderId)) {
//             throw new IllegalStateException("Cannot delete WeavingWorkOrder because beam_gating_details references " + orderIdentifier);
//         }
//         if (workOrderNo != null) {
//             if (beamGatingDetailsRepository.existsByWeavingWorkOrderNo(workOrderNo)) {
//                 throw new IllegalStateException("Cannot delete WeavingWorkOrder because beam_gating_details references weaving_work_order_no " + workOrderNo);
//             }
//             if (fabricInspectionRepository.existsByOrderNo(workOrderNo)) {
//                 throw new IllegalStateException("Cannot delete WeavingWorkOrder because fabric_inspection references order_no " + workOrderNo);
//             }
//             if (generateInvoiceRepository.existsByWoNo(workOrderNo)) {
//                 throw new IllegalStateException("Cannot delete WeavingWorkOrder because generate_invoice references wo_no " + workOrderNo);
//             }
//         }
//     }

//     private boolean hasText(String value) {
//         return value != null && !value.trim().isEmpty();
//     }

//     private String generateWeavingWorkOrderNo(LocalDate orderDate, Long id) {
//         if (orderDate == null || id == null) {
//             return null;
//         }
//         String fy = getFinancialYear(orderDate);
//         String prefix = "JWON/" + fy + "/";
//         String sequence = String.format("%04d", id);
//         return prefix + sequence;
//     }

//     private String getFinancialYear(LocalDate date) {
//         if (date == null) {
//             return null;
//         }
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int fyStartYear = month >= 4 ? year : year - 1;
//         int fy1 = fyStartYear % 100;
//         int fy2 = (fyStartYear + 1) % 100;
//         return String.format("%02d-%02d", fy1, fy2);
//     }

//     private void applyCompanyAssignments(WeavingWorkOrder wo, WeavingWorkOrderDTO dto) {
//         Long orderTypeId = dto.getOrderTypeId();
//         boolean isOrderTypeTwo = orderTypeId != null && orderTypeId == 2L;
//         if (isOrderTypeTwo) {
//             wo.setFactoryCompanyId(dto.getFactoryCompanyId());
//             wo.setTradingCompanyId(null);
//         } else {
//             wo.setFactoryCompanyId(dto.getFactoryCompanyId());
//             wo.setTradingCompanyId(dto.getTradingCompanyId());
//         }
//         if (wo.getFactoryCompanyId() == null) {
//             wo.setFactoryCompanyId(dto.getConsigneeId() != null ? dto.getConsigneeId() : dto.getBuyerId());
//         }
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeavingWorkOrderDetailDTO;
import com.tech3.erp.dto.WeavingWorkOrderDTO;
import com.tech3.erp.dto.WeavingWorkOrderItemDTO;
import com.tech3.erp.dto.WeavingWorkOrderYarnRequirementDTO;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.WeavingWorkOrderItem;
import com.tech3.erp.entity.WeavingWorkOrderDetail;
import com.tech3.erp.entity.WeavingWorkOrderYarnRequirement;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.GeneratePackingRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.service.WeavingContractService;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WeavingWorkOrderService {

    @Autowired
    private WeavingWorkOrderRepository workOrderRepo;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FactoryBeamInwardRepository factoryBeamInwardRepository;

    @Autowired
    private BeamOutwardRepository beamOutwardRepository;

    @Autowired
    private BaleRollPackingRepository baleRollPackingRepository;

    @Autowired
    private GeneratePackingRepository generatePackingRepository;

    @Autowired
    private GenerateInvoiceRepository generateInvoiceRepository;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;

    @Autowired
    private BeamGatingDetailsRepository beamGatingDetailsRepository;

    @Autowired
    @Lazy
    private WeavingContractService weavingContractService;

    @Autowired
    private NotificationService notificationService;

    public WeavingWorkOrder create(WeavingWorkOrderDTO dto) {
        WeavingWorkOrder wo = new WeavingWorkOrder();
        String providedWorkOrderNo = dto.getWeavingWorkOrderNo();
        wo.setOrderDate(dto.getOrderDate());
        wo.setWeavingContractId(dto.getWeavingContractId());
        wo.setOrderTypeId(dto.getOrderTypeId());
        wo.setBuyerId(dto.getBuyerId());
        wo.setConsigneeId(dto.getConsigneeId());
        wo.setGstTypeId(dto.getGstTypeId());
        wo.setTermsConditionsId(dto.getTermsConditionsId());
        wo.setPaymentTermsId(dto.getPaymentTermsId());
        wo.setPackingTypeId(dto.getPackingTypeId());
        applyCompanyAssignments(wo, dto);
        wo.setRemarks(dto.getRemarks());
        wo.setActiveFlag(true);
        wo.setIsCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : false);
        wo.setIsApproved(dto.getIsApproved() != null ? dto.getIsApproved() : (short) 0);
        wo.setIsOrderIdMapped(dto.getIsOrderIdMapped() != null ? dto.getIsOrderIdMapped() : (short) 0);
        wo.setApproveDate(dto.getApproveDate());
        wo.setComments(dto.getComments());
        wo.setUserId(dto.getUserId());
        wo.setWeavingWorkOrderNo(providedWorkOrderNo);
        
        // Only require and set salesOrderNo if NOT internal (orderTypeId != 1)
        boolean isInternal = dto.getOrderTypeId() != null && dto.getOrderTypeId() == 1L;
        if (!isInternal) {
            if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
                wo.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
            } else {
                throw new IllegalArgumentException("At least one order detail with valid sales order ID is required for non-internal orders");
            }
        }
        
        List<WeavingWorkOrderItem> items = (dto.getItems() == null ? new ArrayList<WeavingWorkOrderItemDTO>() : dto.getItems())
                .stream()
                .filter((WeavingWorkOrderItemDTO i) -> i.getFabricCodeId() != null)
                .map((WeavingWorkOrderItemDTO i) -> {
                    WeavingWorkOrderItem e = new WeavingWorkOrderItem();
                    e.setFabricCodeId(i.getFabricCodeId());
                    e.setUomId(i.getUomId());
                    e.setQuantity(i.getQuantity());
                    e.setPickCost(i.getPickCost());
                    e.setRatePerUnit(i.getRatePerUnit());
                    e.setPlannedStartDate(i.getPlannedStartDate());
                    e.setPlannedEndDate(i.getPlannedEndDate());
                    e.setDailyTarget(i.getDailyTarget());
                    e.setNumberOfLooms(i.getNumberOfLooms());
                    e.setWarpLength(i.getWarpLength());
                    e.setWarpCrimpPercentage(i.getWarpCrimpPercentage());
                    e.setPieceLength(i.getPieceLength());
                    e.setNumberOfPieces(i.getNumberOfPieces());
                    e.setPpi(i.getPpi());
                    e.setTaxableAmount(i.getTaxableAmount());
                    e.setGstPercent(i.getGstPercent());
                    e.setTotalAmount(i.getTotalAmount());
                    e.setWeavingWorkOrder(wo);
                    e.setActiveFlag(i.getActiveFlag() != null ? i.getActiveFlag() : true);
                    return e;
                }).collect(Collectors.toList());
        List<WeavingWorkOrderYarnRequirement> yarns = (dto.getYarnRequirements() == null ? new ArrayList<WeavingWorkOrderYarnRequirementDTO>() : dto.getYarnRequirements())
                .stream()
                .filter((WeavingWorkOrderYarnRequirementDTO y) -> y.getYarnType() != null && !y.getYarnType().isEmpty())
                .map((WeavingWorkOrderYarnRequirementDTO y) -> {
                    WeavingWorkOrderYarnRequirement e = new WeavingWorkOrderYarnRequirement();
//                    e.setYarnId(y.getYarnId());
                    e.setYarnType(y.getYarnType());
                    e.setYarnCount(y.getYarnCount());
                    e.setGramsPerMeter(y.getGramsPerMeter());
                    e.setTotalWeavingOrderQty(y.getTotalWeavingOrderQty());
                    e.setTotalRequiredQty(y.getTotalRequiredQty());
                    e.setTotalAvailableQty(y.getTotalAvailableQty());
                    e.setWeavingWorkOrder(wo);
                    e.setActiveFlag(y.getActiveFlag() != null ? y.getActiveFlag() : true);
                    return e;
                }).collect(Collectors.toList());
        List<WeavingWorkOrderDetail> orders = (dto.getOrderDetails() == null ? new ArrayList<WeavingWorkOrderDetailDTO>() : dto.getOrderDetails())
                .stream()
                .filter((WeavingWorkOrderDetailDTO o) -> o.getSalesOrderId() != null)
                .map((WeavingWorkOrderDetailDTO o) -> {
                    WeavingWorkOrderDetail e = new WeavingWorkOrderDetail();
                    e.setSalesOrderId(o.getSalesOrderId());
                    e.setOrderQuantity(o.getOrderQuantity());
                    e.setPlannedQuantity(o.getPlannedQuantity());
                    e.setTotalQuantity(o.getTotalQuantity());
                    e.setWeavingWorkOrder(wo);
                    e.setActiveFlag(o.getActiveFlag() != null ? o.getActiveFlag() : true);
                    return e;
                }).collect(Collectors.toList());
        wo.setItems(items);
        wo.setYarnRequirements(yarns);
        wo.setOrderDetails(orders);
        WeavingWorkOrder saved = workOrderRepo.save(wo);
          if (!hasText(providedWorkOrderNo)) {
            String generated = generateWeavingWorkOrderNo(saved.getOrderDate(), saved.getId());
            if (hasText(generated)) {
                saved.setWeavingWorkOrderNo(generated);
                saved = workOrderRepo.save(saved);
            }
        }
        // if (!StringUtils.hasText(providedWorkOrderNo)) {
        //     Long companyId = saved.getTradingCompanyId() != null ? saved.getTradingCompanyId() : saved.getFactoryCompanyId();
        //     String generated = generateWeavingWorkOrderNo(saved.getOrderDate(), saved.getId(), companyId);
        //     if (StringUtils.hasText(generated)) {
        //         saved.setWeavingWorkOrderNo(generated);
        //         saved = workOrderRepo.save(saved);
        //     }
        // }
        if (saved.getFactoryCompanyId() != null && saved.getTradingCompanyId() != null) {
            try {
                notificationService.createAndPush(
                    saved.getFactoryCompanyId(),
                    saved.getTradingCompanyId(),
                    NotificationService.EVENT_WEAVING_JOBWORK,
                    saved.getId(),
                    null, null
                );
            } catch (Exception ignored) {}
        }
        return saved;
    }


    public List<WeavingWorkOrder> getAll() {
        return workOrderRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public PaginatedResponse<WeavingWorkOrder> getAllWithPagination(Long offset, Long limit, Long factoryCompanyId) {
        List<WeavingWorkOrder> list = factoryCompanyId == null
                ? getAll()
                : getAllByFactoryCompanyId(factoryCompanyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<WeavingWorkOrder> getAllByFactoryCompanyId(Long factoryCompanyId) {
        return workOrderRepo.findByFactoryCompanyId(factoryCompanyId, Sort.by(Sort.Direction.DESC, "id"));
    }

    public PaginatedResponse<WeavingWorkOrder> getAllByFactoryCompanyIdWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        List<WeavingWorkOrder> list = getAllByFactoryCompanyId(factoryCompanyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public WeavingWorkOrder getById(Long id) {
        return workOrderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
    }

    public WeavingWorkOrder update(Long id, WeavingWorkOrderDTO dto) {
        return update(id, dto, null, true);
    }

    public WeavingWorkOrder update(Long id, WeavingWorkOrderDTO dto, Long factoryCompanyId) {
        return update(id, dto, factoryCompanyId, true);
    }

    @Transactional
    public WeavingWorkOrder update(Long id, WeavingWorkOrderDTO dto, Long factoryCompanyId, boolean publishEvent) {
        WeavingWorkOrder wo = workOrderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
        
        // Security check: only validate factoryCompanyId if order is NOT internal (orderTypeId != 1)
        // This prevents EntityNotFoundException for internal orders which might have different company mappings
        boolean isInternal = wo.getOrderTypeId() != null && wo.getOrderTypeId() == 1L;
        
        if (!isInternal && factoryCompanyId != null && wo.getFactoryCompanyId() != null && !factoryCompanyId.equals(wo.getFactoryCompanyId())) {
            throw new EntityNotFoundException("WeavingWorkOrder not found for the specified factoryCompanyId");
        }

        wo.setOrderDate(dto.getOrderDate());
        wo.setWeavingContractId(dto.getWeavingContractId());
        
        // Only update critical fields if NOT internal
        if (!isInternal) {
            wo.setOrderTypeId(dto.getOrderTypeId());
            wo.setWeavingWorkOrderNo(dto.getWeavingWorkOrderNo());
            applyCompanyAssignments(wo, dto);
        }
        
        if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
            wo.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
        }
        
        wo.setBuyerId(dto.getBuyerId());
        wo.setConsigneeId(dto.getConsigneeId());
        wo.setGstTypeId(dto.getGstTypeId());
        wo.setTermsConditionsId(dto.getTermsConditionsId());
        wo.setPaymentTermsId(dto.getPaymentTermsId());
        wo.setPackingTypeId(dto.getPackingTypeId());
        wo.setRemarks(dto.getRemarks());
        wo.setIsCompleted(dto.getIsCompleted());
        wo.setIsApproved(dto.getIsApproved());
        if (dto.getIsOrderIdMapped() != null) {
            wo.setIsOrderIdMapped(dto.getIsOrderIdMapped());
        }
        wo.setApproveDate(dto.getApproveDate());
        wo.setComments(dto.getComments());
        wo.setUserId(dto.getUserId());

        updateItems(wo, dto.getItems());
        updateYarnRequirements(wo, dto.getYarnRequirements());
        updateOrderDetails(wo, dto.getOrderDetails());

        WeavingWorkOrder saved = workOrderRepo.save(wo);
        if (publishEvent && saved.getOrderTypeId() != null && saved.getOrderTypeId().equals(1L)) {
            weavingContractService.syncWorkOrderToContract(saved);
        }
        return saved;
    }

    @Transactional
    public WeavingWorkOrder markAsOrderIdMapped(String weavingWorkOrderNo) {
        if (!hasText(weavingWorkOrderNo)) {
            throw new IllegalArgumentException("WeavingWorkOrderNo is required");
        }
        String trimmedNo = weavingWorkOrderNo.trim();
        WeavingWorkOrder wo = workOrderRepo.findByWeavingWorkOrderNo(trimmedNo)
                .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with weavingWorkOrderNo: " + trimmedNo));
        return updateOrderIdMappedFlag(wo, true);
    }

    @Transactional
    public WeavingWorkOrder markAsOrderIdMapped(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            throw new IllegalArgumentException("WeavingWorkOrderId is required");
        }
        WeavingWorkOrder wo = workOrderRepo.findById(weavingWorkOrderId)
                .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + weavingWorkOrderId));
        return updateOrderIdMappedFlag(wo, true);
    }

    @Transactional
    public WeavingWorkOrder syncOrderIdMappedStatus(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            throw new IllegalArgumentException("WeavingWorkOrderId is required");
        }
        WeavingWorkOrder wo = workOrderRepo.findById(weavingWorkOrderId)
                .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + weavingWorkOrderId));
        return updateOrderIdMappedFlag(wo, hasAnyExternalReference(wo));
    }

    public void refreshOrderIdMappedStatus(Long... weavingWorkOrderIds) {
        if (weavingWorkOrderIds == null || weavingWorkOrderIds.length == 0) {
            return;
        }
        Arrays.stream(weavingWorkOrderIds)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .forEach(this::syncOrderIdMappedStatus);
    }

    private WeavingWorkOrder updateOrderIdMappedFlag(WeavingWorkOrder wo, boolean mapped) {
        short desired = mapped ? (short) 1 : (short) 0;
        if (Short.valueOf(desired).equals(wo.getIsOrderIdMapped())) {
            return wo;
        }
        wo.setIsOrderIdMapped(desired);
        return workOrderRepo.save(wo);
    }

    @Transactional(readOnly = true)
    public boolean isMapped(Long id) {
        if (id == null) return false;
        return workOrderRepo.findById(id).map(this::hasAnyExternalReference).orElse(false);
    }

    private boolean hasAnyExternalReference(WeavingWorkOrder entity) {
        if (entity == null) {
            return false;
        }
        Long workOrderId = entity.getId();
        if (workOrderId != null) {
            if (factoryBeamInwardRepository.existsByWeavingWorkOrderId(workOrderId)
                    || beamOutwardRepository.existsByWeavingWorkOrderId(workOrderId)
                    || baleRollPackingRepository.existsByWeavingWorkOrderId(workOrderId)
                    || generatePackingRepository.existsByWeavingWorkOrderId(workOrderId)
                    || generateInvoiceRepository.existsByWeavingWorkOrderId(workOrderId)
                    || fabricInspectionRepository.existsByWeavingWorkOrderId(workOrderId)
                    || beamGatingDetailsRepository.existsByWeavingWorkOrderId(workOrderId)
                    || weavingContractService.isWorkOrderMapped(workOrderId)) {
                return true;
            }
        }
        String workOrderNo = hasText(entity.getWeavingWorkOrderNo()) ? entity.getWeavingWorkOrderNo().trim() : null;
        if (workOrderNo != null) {
            if (beamGatingDetailsRepository.existsByWeavingWorkOrderNo(workOrderNo)
                    || fabricInspectionRepository.existsByOrderNo(workOrderNo)
                    || generateInvoiceRepository.existsByWoNo(workOrderNo)) {
                return true;
            }
        }
        return false;
    }

    private void updateItems(WeavingWorkOrder wo, List<WeavingWorkOrderItemDTO> itemDTOs) {
        if (itemDTOs == null) return;
        Map<Long, WeavingWorkOrderItem> existing = wo.getItems().stream().collect(Collectors.toMap(WeavingWorkOrderItem::getId, it -> it));
        List<WeavingWorkOrderItem> updated = new ArrayList<>();
        for (WeavingWorkOrderItemDTO dto : itemDTOs) {
            if (dto.getFabricCodeId() == null) continue;
            WeavingWorkOrderItem e = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new WeavingWorkOrderItem();
            e.setWeavingWorkOrder(wo);
            e.setFabricCodeId(dto.getFabricCodeId());
            e.setUomId(dto.getUomId());
            e.setQuantity(dto.getQuantity());
            e.setPickCost(dto.getPickCost());
            e.setRatePerUnit(dto.getRatePerUnit());
            e.setPlannedStartDate(dto.getPlannedStartDate());
            e.setPlannedEndDate(dto.getPlannedEndDate());
            e.setDailyTarget(dto.getDailyTarget());
            e.setNumberOfLooms(dto.getNumberOfLooms());
            e.setWarpLength(dto.getWarpLength());
            e.setWarpCrimpPercentage(dto.getWarpCrimpPercentage());
            e.setPieceLength(dto.getPieceLength());
            e.setNumberOfPieces(dto.getNumberOfPieces());
            e.setPpi(dto.getPpi());
            e.setTaxableAmount(dto.getTaxableAmount());
            e.setGstPercent(dto.getGstPercent());
            e.setTotalAmount(dto.getTotalAmount());
            e.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
            updated.add(e);
        }
        wo.getItems().clear();
        wo.getItems().addAll(updated);
    }

    private void updateYarnRequirements(WeavingWorkOrder wo, List<WeavingWorkOrderYarnRequirementDTO> yarnDTOs) {
        if (yarnDTOs == null) return;
        Map<Long, WeavingWorkOrderYarnRequirement> existing = wo.getYarnRequirements().stream().collect(Collectors.toMap(WeavingWorkOrderYarnRequirement::getId, it -> it));
        List<WeavingWorkOrderYarnRequirement> updated = new ArrayList<>();
        for (WeavingWorkOrderYarnRequirementDTO dto : yarnDTOs) {
            if (dto.getYarnType() == null || dto.getYarnType().isEmpty()) continue;
            WeavingWorkOrderYarnRequirement e = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new WeavingWorkOrderYarnRequirement();
            e.setWeavingWorkOrder(wo);
//            e.setYarnId(dto.getYarnId());
            e.setYarnType(dto.getYarnType());
            e.setYarnCount(dto.getYarnCount());
            e.setGramsPerMeter(dto.getGramsPerMeter());
            e.setTotalWeavingOrderQty(dto.getTotalWeavingOrderQty());
            e.setTotalRequiredQty(dto.getTotalRequiredQty());
            e.setTotalAvailableQty(dto.getTotalAvailableQty());
            e.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
            updated.add(e);
        }
        wo.getYarnRequirements().clear();
        wo.getYarnRequirements().addAll(updated);
    }

    private void updateOrderDetails(WeavingWorkOrder wo, List<WeavingWorkOrderDetailDTO> orderDTOs) {
        if (orderDTOs == null) return;
        Map<Long, WeavingWorkOrderDetail> existing = wo.getOrderDetails().stream().collect(Collectors.toMap(WeavingWorkOrderDetail::getId, it -> it));
        List<WeavingWorkOrderDetail> updated = new ArrayList<>();
        for (WeavingWorkOrderDetailDTO dto : orderDTOs) {
            if (dto.getSalesOrderId() == null) continue;
            WeavingWorkOrderDetail e = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new WeavingWorkOrderDetail();
            e.setWeavingWorkOrder(wo);
            e.setSalesOrderId(dto.getSalesOrderId());
            e.setOrderQuantity(dto.getOrderQuantity());
            e.setPlannedQuantity(dto.getPlannedQuantity());
            e.setTotalQuantity(dto.getTotalQuantity());
            e.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
            updated.add(e);
        }
        wo.getOrderDetails().clear();
        wo.getOrderDetails().addAll(updated);
    }

    public void delete(Long id) {
        WeavingWorkOrder entity = workOrderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));
        deleteEntity(entity);
    }

    public void delete(Long id, Long factoryCompanyId) {
        WeavingWorkOrder entity = workOrderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WeavingWorkOrder not found with id: " + id));	
        if (factoryCompanyId != null && entity.getFactoryCompanyId() != null && !factoryCompanyId.equals(entity.getFactoryCompanyId())) {
            throw new EntityNotFoundException("WeavingWorkOrder not found for the specified factoryCompanyId");
        }
        deleteEntity(entity);
    }

    private void deleteEntity(WeavingWorkOrder entity) {
        ensureNoWeavingWorkOrderReferences(entity);
        try {
            workOrderRepo.delete(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because it is referenced by other records");
        }
    }

    private void ensureNoWeavingWorkOrderReferences(WeavingWorkOrder entity) {
        if (entity == null || entity.getId() == null) {
            return;
        }
        Long workOrderId = entity.getId();
        String workOrderNo = StringUtils.hasText(entity.getWeavingWorkOrderNo()) ? entity.getWeavingWorkOrderNo().trim() : null;
        String orderIdentifier = workOrderNo != null ? "weaving_work_order_no " + workOrderNo : "weaving_work_order_id " + workOrderId;
        if (factoryBeamInwardRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because factory_beam_inward references " + orderIdentifier);
        }
        if (beamOutwardRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because beam_outward references " + orderIdentifier);
        }
        if (baleRollPackingRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because bale_roll_packing references " + orderIdentifier);
        }
        if (generatePackingRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because generate_packing references " + orderIdentifier);
        }
        if (generateInvoiceRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because generate_invoice references " + orderIdentifier);
        }
        if (fabricInspectionRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because fabric_inspection references " + orderIdentifier);
        }
        if (beamGatingDetailsRepository.existsByWeavingWorkOrderId(workOrderId)) {
            throw new IllegalStateException("Cannot delete WeavingWorkOrder because beam_gating_details references " + orderIdentifier);
        }
        if (workOrderNo != null) {
            if (beamGatingDetailsRepository.existsByWeavingWorkOrderNo(workOrderNo)) {
                throw new IllegalStateException("Cannot delete WeavingWorkOrder because beam_gating_details references weaving_work_order_no " + workOrderNo);
            }
            if (fabricInspectionRepository.existsByOrderNo(workOrderNo)) {
                throw new IllegalStateException("Cannot delete WeavingWorkOrder because fabric_inspection references order_no " + workOrderNo);
            }
            if (generateInvoiceRepository.existsByWoNo(workOrderNo)) {
                throw new IllegalStateException("Cannot delete WeavingWorkOrder because generate_invoice references wo_no " + workOrderNo);
            }
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
  private String generateWeavingWorkOrderNo(LocalDate orderDate, Long id) {
        if (orderDate == null || id == null) {
            return null;
        }
        String fy = getFinancialYear(orderDate);
        String prefix = "JWON/" + fy + "/";
        String sequence = String.format("%04d", id);
        return prefix + sequence;
    }

    // private String generateWeavingWorkOrderNo(LocalDate orderDate, Long id, Long companyId) {
    //     if (orderDate == null || id == null) {
    //         return null;
    //     }
    //     String cmpPrefix = "";
    //     if (companyId != null) {
    //         Company company = companyRepository.findById(companyId).orElse(null);
    //         if (company != null) {
    //             if (StringUtils.hasText(company.getCompanyCode())) {
    //                 cmpPrefix = company.getCompanyCode() + "/";
    //             } else if (StringUtils.hasText(company.getCompanyName())) {
    //                 cmpPrefix = company.getCompanyName().substring(0, 1).toUpperCase() + "/";
    //             }
    //         }
    //     }
    //     String fy = getFinancialYear(orderDate);
    //     String prefix = cmpPrefix + "JWON/" + fy + "/";
    //     String sequence = String.format("%04d", id);
    //     return prefix + sequence;
    // }

    private String getFinancialYear(LocalDate date) {
        if (date == null) {
            return null;
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        int fyStartYear = month >= 4 ? year : year - 1;
        int fy1 = fyStartYear % 100;
        int fy2 = (fyStartYear + 1) % 100;
        return String.format("%02d-%02d", fy1, fy2);
    }

    private void applyCompanyAssignments(WeavingWorkOrder wo, WeavingWorkOrderDTO dto) {
        Long orderTypeId = dto.getOrderTypeId();
        boolean isOrderTypeTwo = orderTypeId != null && orderTypeId == 2L;
        if (isOrderTypeTwo) {
            wo.setFactoryCompanyId(dto.getFactoryCompanyId());
            wo.setTradingCompanyId(null);
        } else {
            wo.setFactoryCompanyId(dto.getFactoryCompanyId());
            wo.setTradingCompanyId(dto.getTradingCompanyId());
        }
        if (wo.getFactoryCompanyId() == null) {
            wo.setFactoryCompanyId(dto.getConsigneeId() != null ? dto.getConsigneeId() : dto.getBuyerId());
        }
    }
}