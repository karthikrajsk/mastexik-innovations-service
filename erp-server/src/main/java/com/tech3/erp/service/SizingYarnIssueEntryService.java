// // package com.tech3.erp.service;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;
// // import org.springframework.transaction.annotation.Transactional;

// // import com.tech3.erp.dto.PaginatedResponse;
// // import com.tech3.erp.dto.SizingYarnInwardDTO;
// // import com.tech3.erp.dto.SizingYarnInwardDetailDTO;
// // import com.tech3.erp.dto.SizingYarnIssueDTO;
// // import com.tech3.erp.dto.SizingYarnIssueEntryDTO;
// // import com.tech3.erp.dto.SizingYarnRequirementDTO;
// // import com.tech3.erp.dto.YarnInwardStockDetailsDto;
// // import com.tech3.erp.dto.YarnOutwardStockDetailsDTO;
// // import com.tech3.erp.entity.CodesDetail;
// // import com.tech3.erp.entity.LotEntry;
// // import com.tech3.erp.entity.LotOutward;
// // import com.tech3.erp.entity.SizingJobWorkOrder;
// // import com.tech3.erp.entity.SizingPlan;
// // import com.tech3.erp.entity.SizingQualityDetails;
// // import com.tech3.erp.entity.SizingYarnIssue;
// // import com.tech3.erp.entity.SizingYarnInward;
// // import com.tech3.erp.entity.SizingYarnInwardDetail;
// // import com.tech3.erp.entity.SizingYarnIssueEntry;
// // import com.tech3.erp.entity.SizingYarnRequirement;
// // import com.tech3.erp.entity.Vendor;
// // import com.tech3.erp.entity.YarnInventory;
// // import com.tech3.erp.entity.YarnInwardStockDetails;
// // import com.tech3.erp.entity.YarnOutward;
// // import com.tech3.erp.entity.YarnOutwardStockDetails;
// // import com.tech3.erp.repository.CodesDetailRepository;
// // import com.tech3.erp.repository.LotEntryRepository;
// // import com.tech3.erp.repository.LotOutwardRepository;
// // import com.tech3.erp.repository.SizingJobWorkOrderRepository;
// // import com.tech3.erp.repository.SizingPlanRepository;
// // import com.tech3.erp.repository.SizingQualityDetailsRepository;
// // import com.tech3.erp.repository.SizingYarnInwardRepository;
// // import com.tech3.erp.repository.SizingYarnIssueEntryRepository;
// // import com.tech3.erp.repository.SizingYarnIssueRepository;
// // import com.tech3.erp.repository.SizingYarnRequirementRepository;
// // import com.tech3.erp.repository.VendorRepository;
// // import com.tech3.erp.repository.YarnInventoryRepository;
// // import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
// // import com.tech3.erp.repository.YarnOutwardRepository;
// // import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;

// // import jakarta.persistence.EntityNotFoundException;

// // import java.math.BigDecimal;
// // import java.math.RoundingMode;
// // import java.time.LocalDate;
// // import java.time.LocalDateTime;
// // import java.util.Comparator;
// // import java.util.HashMap;
// // import java.util.HashSet;
// // import java.util.List;
// // import java.util.Optional;
// // import java.util.Set;
// // import java.util.ArrayList;
// // import java.util.Collections;
// // import java.util.Map;
// // import java.util.function.Function;
// // import java.util.stream.Collectors;

// // @Service
// // @Transactional
// // public class SizingYarnIssueEntryService {

// //     @Autowired
// //     private SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository;
    
// //     @Autowired
// //     private SizingYarnRequirementRepository sizingYarnRequirementRepository;
    
// //     @Autowired
// //     private SizingYarnIssueRepository sizingYarnIssueRepository;
    
// //     @Autowired
// //     private LotEntryRepository lotEntryRepository;
    
// //     @Autowired
// //     private LotOutwardRepository lotOutwardRepository;
    
// //     @Autowired
// //     private SizingQualityDetailsRepository sizingQualityDetailsRepository;
    
// //     @Autowired
// //     private SizingPlanRepository sizingPlanRepository;
    
// //     @Autowired
// //     private SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    
// //     @Autowired
// //     private ReferenceValidationService referenceValidationService;
    
// //     @Autowired
// //     private SizingYarnInwardService sizingYarnInwardService;

// //     @Autowired
// //     private SizingYarnInwardRepository sizingYarnInwardRepository;
    
// //     @Autowired
// //     private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
// //     @Autowired
// //     private YarnInventoryRepository yarnInventoryRepository;
    
// //     @Autowired
// //     private YarnOutwardRepository yarnOutwardRepository;
    
// //     @Autowired
// //     private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;

// //        @Autowired
// //     private CodesDetailRepository codesDetailRepository;

// //     @Autowired
// //     private VendorRepository vendorRepository;

// //     @Transactional(readOnly = true)
// //     public List<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrys() {
// //         List<SizingYarnIssueEntry> issues = sizingYarnIssueEntryRepository.findAll();
// //         return issues.stream()
// //                 .sorted(Comparator.comparing(SizingYarnIssueEntry::getId).reversed())
// //                 .map(this::convertToDTO)
// //                 .collect(Collectors.toList());
// //     }

// //     @Transactional(readOnly = true)
// //     public PaginatedResponse<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrysWithPagination(Long offset, Long limit) {
// //         return buildPaginatedResponse(
// //                 sizingYarnIssueEntryRepository.findAllIds(),
// //                 offset,
// //                 limit,
// //                 ids -> ids.isEmpty() ? Collections.emptyList() : sizingYarnIssueEntryRepository.findByIdsWithDetails(ids)
// //         );
// //     }

// //     @Transactional(readOnly = true)
// //     public PaginatedResponse<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrysByCompanyWithPagination(Long companyId, Long offset, Long limit) {
// //         if (companyId == null) {
// //             throw new IllegalArgumentException("Company ID is required");
// //         }
// //         return buildPaginatedResponse(
// //                 sizingYarnIssueEntryRepository.findIdsByCompanyId(companyId),
// //                 offset,
// //                 limit,
// //                 ids -> ids.isEmpty() ? Collections.emptyList() : sizingYarnIssueEntryRepository.findByCompanyIdAndIds(companyId, ids)
// //         );
// //     }

// //     @Transactional(readOnly = true)
// //     public List<SizingYarnIssueEntryDTO> getSizingYarnIssueEntrysByCompany(Long companyId) {
// //         if (companyId == null) {
// //             throw new IllegalArgumentException("Company ID is required");
// //         }
// //         return sizingYarnIssueEntryRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
// //                 .map(this::convertToDTO)
// //                 .collect(Collectors.toList());
// //     }

// //     @Transactional(readOnly = true)
// //     public SizingYarnIssueEntryDTO getSizingYarnIssueEntryById(Long id) {
// //         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findById(id)
// //                 .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
// //         return convertToDTO(issue);
// //     }

// //     @Transactional(readOnly = true)
// //     public SizingYarnIssueEntryDTO getSizingYarnIssueEntryByIdAndCompany(Long id, Long companyId) {
// //         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
// //                 .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
// //         return convertToDTO(issue);
// //     }

// //     @Transactional
// //     public SizingYarnIssueEntryDTO createSizingYarnIssueEntry(SizingYarnIssueEntryDTO dto) {
// //         // Validate before processing
// //         validateSizingYarnIssue(dto);
        
// //         SizingPlan sizingPlan = referenceValidationService.requireReference(
// //                 dto.getSizingPlanId(),
// //                 () -> sizingPlanRepository.findById(dto.getSizingPlanId()),
// //                 "Sizing Plan",
// //                 dto.getSizingPlanNo());
        
// //         SizingYarnIssueEntry issue = new SizingYarnIssueEntry();
// //         populateEntityFromDTO(issue, dto, false);
        
// //         // Ensure sizingPlanId is set correctly from the validated sizingPlan
// //         issue.setSizingPlanId(normalizeId(dto.getSizingPlanId()));
        
// //         SizingYarnIssueEntry savedIssue = sizingYarnIssueEntryRepository.save(issue);
        
// //         // Process lot reductions and outward entries only if NOT in a sync process
// //         if (!SyncContext.isInProgress()) {
// //             processLotOutward(savedIssue, "SIZING_ISSUE", savedIssue.getId(), dto.getOutwardTypeId());
            
// //             // Update sizing plan actualYarn based on requirements only on main call
// //             updateSizingPlanActualYarn(dto.getSizingPlanId(), dto.getRequirements());
// //         }
        
// //         syncSizingYarnInwardRecord(savedIssue);
        
// //         return convertToDTO(savedIssue);
// //     }

// //     @Transactional
// //     public SizingYarnIssueEntryDTO updateSizingYarnIssueEntry(Long id, SizingYarnIssueEntryDTO dto) {
// //         validateSizingYarnIssue(dto);
// //         SizingYarnIssueEntry existingIssue = sizingYarnIssueEntryRepository.findById(id)
// //             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
// //         if (dto.getCompanyId() == null) {
// //             dto.setCompanyId(existingIssue.getCompanyId());
// //         }
// //         return updateAndPersistIssue(existingIssue, dto, id);
// //     }

// //     @Transactional
// //     public SizingYarnIssueEntryDTO updateSizingYarnIssueEntryByCompany(Long id, Long companyId, SizingYarnIssueEntryDTO dto) {
// //         validateSizingYarnIssue(dto);
// //         SizingYarnIssueEntry existingIssue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
// //             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
// //         dto.setCompanyId(companyId);
// //         return updateAndPersistIssue(existingIssue, dto, id);
// //     }

// //     @Transactional
// //     public void deleteSizingYarnIssueEntry(Long id) {
// //         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findById(id)
// //             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
// //         deleteIssueEntry(issue);
// //     }

// //     @Transactional
// //     public void deleteSizingYarnIssueEntryByCompany(Long id, Long companyId) {
// //         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
// //             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
// //         deleteIssueEntry(issue);
// //     }

// //     private SizingYarnIssueEntryDTO updateAndPersistIssue(SizingYarnIssueEntry existingIssue, SizingYarnIssueEntryDTO dto, Long referenceId) {
// //         if (!SyncContext.isInProgress()) {
// //             reverseLotReductions(existingIssue, false);
// //             populateEntityFromDTO(existingIssue, dto, true);
// //             processLotOutward(existingIssue, "SIZING_ISSUE", referenceId, dto.getOutwardTypeId());
// //             updateSizingPlanActualYarn(dto.getSizingPlanId(), dto.getRequirements());
// //         } else {
// //             populateEntityFromDTO(existingIssue, dto, true);
// //         }
// //         SizingYarnIssueEntry savedIssue = sizingYarnIssueEntryRepository.save(existingIssue);
// //         syncSizingYarnInwardRecord(savedIssue);
// //         return convertToDTO(savedIssue);
// //     }

// //     private void deleteIssueEntry(SizingYarnIssueEntry issue) {
// //         reverseLotReductions(issue, true);
// //         deleteSizingYarnInwardIfPresent(issue);
// //         sizingYarnIssueEntryRepository.delete(issue);
// //     }

// //     private void deleteSizingYarnInwardIfPresent(SizingYarnIssueEntry issue) {
// //         resolveSizingYarnInwardForIssue(issue).ifPresent(inward -> sizingYarnInwardService.deleteSizingYarnInward(inward.getId()));
// //     }

// //     private Optional<SizingYarnInward> resolveSizingYarnInwardForIssue(SizingYarnIssueEntry issue) {
// //         if (issue == null || issue.getId() == null) {
// //             return Optional.empty();
// //         }
// //         String suffix = "-" + issue.getId();
// //         return sizingYarnInwardRepository.findFirstByInwardNoEndingWith(suffix);
// //     }

// //     // ===== NEW METHOD: UPDATE SIZING PLAN ACTUAL YARN =====
    
// //     /**
// //      * Updates the actualYarn field in SizingQualityDetails based on the requirements
// //      * from SizingYarnIssue. This syncs the actualYarn changes back to the sizing plan.
// //      */
// //     private void updateSizingPlanActualYarn(Long sizingPlanId, List<SizingYarnRequirementDTO> requirements) {
// //         if (sizingPlanId == null || requirements == null || requirements.isEmpty()) {
// //             return;
// //         }
        
// //         // Get all quality details for this sizing plan
// //         List<SizingQualityDetails> qualityDetailsList = 
// //             sizingQualityDetailsRepository.findBySizingPlanId(sizingPlanId);
        
// //         // Create a map for quick lookup: yarnId -> actualYarnId
// //         // We match based on yarnId (original yarn) to find which quality detail to update
// //         for (SizingYarnRequirementDTO req : requirements) {
// //             if (req.getYarnId() != null && req.getActualYarnId() != null) {
// //                 // Find matching quality detail by yarnId
// //                 qualityDetailsList.stream()
// //                     .filter(qd -> qd.getYarnId().equals(req.getYarnId()))
// //                     .forEach(qd -> {
// //                         // Update the actualYarn field
// //                         qd.setActualYarn(req.getActualYarnId());
// //                     });
// //             }
// //         }
        
// //         // Save all updated quality details
// //         sizingQualityDetailsRepository.saveAll(qualityDetailsList);
// //     }

// //     private void syncSizingYarnInwardRecord(SizingYarnIssueEntry savedIssue) {
// //         if (SyncContext.isInProgress() || savedIssue == null || savedIssue.getSizingYarnIssues() == null || savedIssue.getSizingYarnIssues().isEmpty()) {
// //             return;
// //         }

// //         try {
// //             SyncContext.setInProgress(true);
// //             doSyncSizingYarnInwardRecord(savedIssue);
// //         } finally {
// //             SyncContext.clear();
// //         }
// //     }

// //     private void doSyncSizingYarnInwardRecord(SizingYarnIssueEntry savedIssue) {

// //         if (savedIssue.getVendorId() != null) {
// //             boolean isInternal = vendorRepository.findById(savedIssue.getVendorId())
// //                     .map(v -> v.getIsInternalVendor() != null && v.getIsInternalVendor() == 1)
// //                     .orElse(false);
// //             if (!isInternal) {
// //                 return;
// //             }
// //         } else {
// //             return;
// //         }

// //         SizingYarnInwardDTO inwardDTO = new SizingYarnInwardDTO();
// //         LocalDate inwardDate = savedIssue.getSizingYarnIssueDate() != null ? savedIssue.getSizingYarnIssueDate() : LocalDate.now();
// //         String financialYear = getShortFinancialYear(inwardDate);
// //         inwardDTO.setInwardNo(String.format("SZYI-%s-%d", financialYear, savedIssue.getId()));
// //         inwardDTO.setInwardDate(savedIssue.getSizingYarnIssueDate());
// //         inwardDTO.setCompanyId(savedIssue.getCompanyId());

// //         Long sizingPlanId = savedIssue.getSizingPlanId();
// //         if (sizingPlanId != null) {
// //             sizingJobWorkOrderRepository.findBySizingPlanId(sizingPlanId)
// //                     .ifPresent(jobOrder -> {
// //                         inwardDTO.setSizingJobWorkOrderId(jobOrder.getId());
// //                         inwardDTO.setOrderTypeId(jobOrder.getOrderTypeId());
// //                         inwardDTO.setBuyerId(jobOrder.getBuyerId());
// //                         inwardDTO.setFactoryCompanyId(jobOrder.getFactoryCompanyId());
// //                         inwardDTO.setTradingCompanyId(jobOrder.getTradingCompanyId());
// //                         if (Long.valueOf(1L).equals(jobOrder.getOrderTypeId()) && jobOrder.getTradingCompanyId() != null) {
// //                             inwardDTO.setCompanyId(jobOrder.getTradingCompanyId());
// //                         }
// //                     });

// //             sizingPlanRepository.findById(sizingPlanId).ifPresent(plan -> {
// //                 if (inwardDTO.getOrderTypeId() == null) {
// //                     inwardDTO.setOrderTypeId(plan.getOrderTypeId());
// //                 }
// //                 if (inwardDTO.getFactoryCompanyId() == null) {
// //                     inwardDTO.setFactoryCompanyId(plan.getFactoryCompanyId());
// //                 }
// //                 if (inwardDTO.getTradingCompanyId() == null) {
// //                     inwardDTO.setTradingCompanyId(plan.getTradingCompanyId());
// //                 }
// //                 if (Long.valueOf(1L).equals(inwardDTO.getOrderTypeId())) {
// //                     Long targetCompanyId = plan.getTradingCompanyId() != null ? plan.getTradingCompanyId() : plan.getCompanyId();
// //                     if (targetCompanyId != null) {
// //                         inwardDTO.setCompanyId(targetCompanyId);
// //                     }
// //                 } else if (inwardDTO.getCompanyId() == null) {
// //                     inwardDTO.setCompanyId(plan.getCompanyId());
// //                 }
// //             });
// //         }

// //         if (inwardDTO.getFactoryCompanyId() == null) {
// //             inwardDTO.setFactoryCompanyId(savedIssue.getCompanyId());
// //         }

// //         if (Long.valueOf(1L).equals(inwardDTO.getOrderTypeId()) && inwardDTO.getTradingCompanyId() == null) {
// //             inwardDTO.setTradingCompanyId(inwardDTO.getCompanyId());
// //         }

// //         Long buyerCompanyId = savedIssue.getCompanyId();
// //         if (buyerCompanyId != null) {
// //             inwardDTO.setTradingCompanyId(buyerCompanyId);
// //         }

// //         Optional<SizingYarnInward> existingInward = resolveSizingYarnInwardForIssue(savedIssue);
// //         List<SizingYarnInwardDetailDTO> yarnDetailDTOs = savedIssue.getSizingYarnIssues().stream()
// //                 .map(issue -> {
// //                     SizingYarnInwardDetailDTO detailDTO = new SizingYarnInwardDetailDTO();
// //                     detailDTO.setYarnName(issue.getYarnName());
// //                     // Use actualItemId if present for sync to inward
// //                     detailDTO.setYarnId(issue.getActualItemId() != null ? issue.getActualItemId() : issue.getYarnId());
// //                     detailDTO.setNoOfBags(issue.getNoOfBags());
// //                     detailDTO.setKgsPerBag(issue.getKgsPerBag());
// //                     detailDTO.setConesPerBag(issue.getConesPerBag());
// //                     detailDTO.setConesWeight(issue.getConesWeight());
// //                     detailDTO.setWarehouseId(issue.getWarehouseId());
// //                     detailDTO.setMillId(issue.getMillId());
// //                     detailDTO.setIssueQty(issue.getIssueQty());
// //                     detailDTO.setLotNo(issue.getLotNo());
// //                     return detailDTO;
// //                 })
// //                 .collect(Collectors.toList());
// //         preserveSizingYarnInwardDetailIds(existingInward.orElse(null), yarnDetailDTOs);
// //         inwardDTO.setYarnDetails(yarnDetailDTOs);

// //         if (existingInward.isPresent()) {
// //             sizingYarnInwardService.updateSizingYarnInward(existingInward.get().getId(), inwardDTO);
// //         } else {
// //             sizingYarnInwardService.createSizingYarnInward(inwardDTO);
// //         }

// //         // Simultaneously create/update YarnInventory entry for internal vendor
// //         String spNo = null;
// //         if (savedIssue.getSizingPlanId() != null) {
// //             spNo = sizingPlanRepository.findById(savedIssue.getSizingPlanId())
// //                     .map(SizingPlan::getSizingPlanNo)
// //                     .orElse(null);
// //         }
// //         if (spNo != null) {
// //             syncYarnInventoryRecord(savedIssue, spNo, inwardDTO.getInwardNo());
// //         }
// //     }

// //     private void preserveSizingYarnInwardDetailIds(SizingYarnInward existingInward, List<SizingYarnInwardDetailDTO> yarnDetailDTOs) {
// //         if (existingInward == null || existingInward.getYarnDetails() == null || yarnDetailDTOs == null || yarnDetailDTOs.isEmpty()) {
// //             return;
// //         }

// //         Map<String, List<SizingYarnInwardDetail>> existingDetailsByKey = existingInward.getYarnDetails().stream()
// //                 .collect(Collectors.groupingBy(this::buildSizingYarnInwardDetailKey));

// //         for (SizingYarnInwardDetailDTO detailDTO : yarnDetailDTOs) {
// //             List<SizingYarnInwardDetail> matches = existingDetailsByKey.get(buildSizingYarnInwardDetailKey(detailDTO));
// //             if (matches != null && !matches.isEmpty()) {
// //                 detailDTO.setId(matches.remove(0).getId());
// //             }
// //         }
// //     }

// //     private String buildSizingYarnInwardDetailKey(SizingYarnInwardDetail detail) {
// //         if (detail == null) {
// //             return "";
// //         }
// //         return String.join("|",
// //                 String.valueOf(normalizeId(detail.getYarnId())),
// //                 String.valueOf(normalizeId(detail.getMillId())),
// //                 String.valueOf(normalizeId(detail.getWarehouseId())),
// //                 normalizeText(detail.getLotNo()));
// //     }

// //     private String buildSizingYarnInwardDetailKey(SizingYarnInwardDetailDTO detailDTO) {
// //         if (detailDTO == null) {
// //             return "";
// //         }
// //         return String.join("|",
// //                 String.valueOf(normalizeId(detailDTO.getYarnId())),
// //                 String.valueOf(normalizeId(detailDTO.getMillId())),
// //                 String.valueOf(normalizeId(detailDTO.getWarehouseId())),
// //                 normalizeText(detailDTO.getLotNo()));
// //     }

// //     private String normalizeText(String value) {
// //         return value == null ? "" : value.trim().toLowerCase();
// //     }

// //     private void syncYarnInventoryRecord(SizingYarnIssueEntry savedIssue, String sizingPlanNo, String inwardNo) {
// //         Long inwardTypeId = codesDetailRepository.findByValue("Sizing-Yarn-Inward")
// //                 .map(CodesDetail::getId)
// //                 .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
// //                         .map(CodesDetail::getId)
// //                         .orElse(3L));

// //         YarnInventory yarnInventory = yarnInventoryRepository.findByInwardNo(inwardNo)
// //                 .orElse(new YarnInventory());
// //         Optional<SizingYarnInward> sizingYarnInwardOpt = sizingYarnInwardRepository.findByInwardNo(inwardNo);

// //         yarnInventory.setInwardId(sizingYarnInwardOpt.map(SizingYarnInward::getId).orElse(savedIssue.getId()));
// //         yarnInventory.setInwardTypeId(inwardTypeId);
// //         yarnInventory.setInwardNo(inwardNo);
// //         yarnInventory.setCompanyId(savedIssue.getCompanyId());
// //         yarnInventory.setInwardDate(savedIssue.getSizingYarnIssueDate() != null ?
// //                 savedIssue.getSizingYarnIssueDate().atStartOfDay() : LocalDateTime.now());
// //         yarnInventory.setFinancialYear(getFinancialYearId(savedIssue.getSizingYarnIssueDate() != null ?
// //                 savedIssue.getSizingYarnIssueDate() : LocalDate.now()));
// //         yarnInventory.setDcNo(savedIssue.getYarnIssueChallanNo());
// //         yarnInventory.setDcDate(savedIssue.getSizingYarnIssueDate());
// //         yarnInventory.setOrderId(savedIssue.getSizingPlanId());
// //         yarnInventory.setOrderNo(sizingPlanNo);
// //         yarnInventory.setVendorId(savedIssue.getVendorId());

// //         Map<Long, YarnInwardStockDetails> existingDetailsMap = new HashMap<>();
// //         if (yarnInventory.getDetails() != null) {
// //             yarnInventory.getDetails().forEach(detail -> {
// //                 if (detail.getOrderItemId() != null) {
// //                     existingDetailsMap.put(detail.getOrderItemId(), detail);
// //                 }
// //             });
// //         }

// //         List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();
// //         sizingYarnInwardOpt.ifPresent(inward -> {
// //             if (inward.getYarnDetails() != null) {
// //                 inward.getYarnDetails().forEach(inwardDetail -> {
// //                     YarnInwardStockDetails detail = existingDetailsMap.remove(inwardDetail.getId());
// //                     if (detail == null) {
// //                         detail = new YarnInwardStockDetails();
// //                         detail.setYarnInventory(yarnInventory);
// //                     }

// //                     BigDecimal inwardBags = inwardDetail.getNoOfBags() != null
// //                             ? BigDecimal.valueOf(inwardDetail.getNoOfBags())
// //                             : BigDecimal.ZERO;

// //                     detail.setOrderItemId(inwardDetail.getId());
// //                     detail.setInwardTypeId(inwardTypeId);
// //                     detail.setItemId(normalizeId(inwardDetail.getYarnId()));
// //                     detail.setMillId(normalizeId(inwardDetail.getMillId()));
// //                     detail.setLotNo(inwardDetail.getLotNo());
// //                     detail.setInwardQty(inwardDetail.getIssueQty());
// //                     detail.setAcceptedQty(inwardDetail.getIssueQty());
// //                     detail.setInwardBags(inwardBags);
// //                     detail.setWarehouseId(normalizeId(inwardDetail.getWarehouseId()));
// //                     detail.setKgsPerBag(inwardDetail.getKgsPerBag());
// //                     detail.setConesPerBag(inwardDetail.getConesPerBag());
// //                     detail.setConesWeight(inwardDetail.getConesWeight());
// //                     recalculateAvailableFromOutward(detail, inwardBags);
// //                     updatedDetails.add(detail);
// //                 });
// //             }
// //         });

// //         if (yarnInventory.getDetails() != null) {
// //             yarnInventory.getDetails().clear();
// //             yarnInventory.getDetails().addAll(updatedDetails);
// //         } else {
// //             yarnInventory.setDetails(updatedDetails);
// //         }

// //         yarnInventoryRepository.save(yarnInventory);
// //     }

// //     private void recalculateAvailableFromOutward(YarnInwardStockDetails detail, BigDecimal inwardBags) {
// //         BigDecimal inwardQty = detail.getInwardQty() != null ? detail.getInwardQty() : BigDecimal.ZERO;
// //         BigDecimal totalIssuedQty = BigDecimal.ZERO;
// //         BigDecimal totalIssuedBags = BigDecimal.ZERO;

// //         if (detail.getId() != null) {
// //             totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
// //             totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());
// //         }

// //         BigDecimal availableQty = inwardQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
// //         BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);

// //         detail.setAvailableQty(availableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableQty);
// //         detail.setAvailableBags(availableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBags);
// //     }

// //     // ===== ENTITY POPULATION METHOD =====
    
// //     private void populateEntityFromDTO(SizingYarnIssueEntry entity, SizingYarnIssueEntryDTO dto, boolean isUpdate) {
// //         entity.setVendorId(dto.getVendorId());
// //         entity.setCompanyId(dto.getCompanyId());
// //         entity.setOutwardTypeId(dto.getOutwardTypeId());
// //         entity.setSizingPlanId(dto.getSizingPlanId());
// //         entity.setTransportationDetails(dto.getTransportationDetails());
// //         entity.setTermsConditionsId(dto.getTermsConditionsId());
// //         entity.setFabricDetails(dto.getFabricDetails());
// //         entity.setSizingYarnIssueDate(dto.getSizingYarnIssueDate());
// //         entity.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
// //         entity.setActiveFlag(dto.isActiveFlag());
// //         entity.setRemark(dto.getRemark());
// //         entity.setVehicleNo(dto.getVehicleNo());
// //         entity.setGrossWt(dto.getGrossWt());

// //         // Handle requirements
// //         if (dto.getRequirements() != null) {
// //             if (isUpdate) {
// //                 Set<Long> dtoReqIds = dto.getRequirements().stream()
// //                     .map(SizingYarnRequirementDTO::getId)
// //                     .filter(id -> id != null)
// //                     .collect(Collectors.toSet());
// //                 entity.getRequirements().removeIf(req -> req.getId() != null && !dtoReqIds.contains(req.getId()));
// //             }

// //             for (SizingYarnRequirementDTO reqDTO : dto.getRequirements()) {
// //                 SizingYarnRequirement req;
// //                 if (isUpdate && reqDTO.getId() != null) {
// //                     req = entity.getRequirements().stream()
// //                         .filter(r -> r.getId().equals(reqDTO.getId()))
// //                         .findFirst()
// //                         .orElseGet(() -> {
// //                             SizingYarnRequirement newReq = sizingYarnRequirementRepository.findById(reqDTO.getId())
// //                                 .orElseThrow(() -> new EntityNotFoundException("SizingYarnRequirement not found: " + reqDTO.getId()));
// //                             newReq.setSizingYarnIssueEntry(entity);
// //                             entity.getRequirements().add(newReq);
// //                             return newReq;
// //                         });
// //                     updateRequirementFromDTO(req, reqDTO);
// //                 } else {
// //                     req = new SizingYarnRequirement();
// //                     populateRequirementFromDTO(req, reqDTO);
// //                     req.setSizingYarnIssueEntry(entity);
// //                     entity.getRequirements().add(req);
// //                 }
// //             }
// //         } else if (isUpdate) {
// //             entity.getRequirements().clear();
// //         }
        
// //         // Handle sizing yarn issues
// //         if (dto.getSizingYarnIssue() != null) {
// //             if (isUpdate) {
// //                 Set<Long> dtoIssueIds = dto.getSizingYarnIssue().stream()
// //                     .map(SizingYarnIssueDTO::getId)
// //                     .filter(id -> id != null)
// //                     .collect(Collectors.toSet());
// //                 entity.getSizingYarnIssues().removeIf(issue -> issue.getId() != null && !dtoIssueIds.contains(issue.getId()));
// //             }

// //             for (SizingYarnIssueDTO issueDTO : dto.getSizingYarnIssue()) {
// //                 SizingYarnIssue issue;
// //                 if (isUpdate && issueDTO.getId() != null) {
// //                     issue = entity.getSizingYarnIssues().stream()
// //                         .filter(i -> i.getId().equals(issueDTO.getId()))
// //                         .findFirst()
// //                         .orElseGet(() -> {
// //                             SizingYarnIssue newIssue = sizingYarnIssueRepository.findById(issueDTO.getId())
// //                                 .orElseThrow(() -> new EntityNotFoundException("SizingYarnIssue not found: " + issueDTO.getId()));
// //                             newIssue.setSizingYarnIssueEntry(entity);
// //                             entity.getSizingYarnIssues().add(newIssue);
// //                             return newIssue;
// //                         });
// //                     updateYarnIssueFromDTO(issue, issueDTO, dto.getIssueNo());
// //                 } else {
// //                     issue = new SizingYarnIssue();
// //                     populateYarnIssueFromDTO(issue, issueDTO,dto.getIssueNo());
// //                     issue.setSizingYarnIssueEntry(entity);
// //                     entity.getSizingYarnIssues().add(issue);
// //                 }
// //             }
// //         } else if (isUpdate) {
// //             entity.getSizingYarnIssues().clear();
// //         }
// //     }
    
// //     private void populateRequirementFromDTO(SizingYarnRequirement entity, SizingYarnRequirementDTO dto) {
// //         entity.setId(null);
// //         entity.setFabricDetails(dto.getFabricDetails());
// //         entity.setYarnName(dto.getYarnName());
// //         entity.setActualYarn(dto.getActualYarn());
// //         entity.setActualYarnId(dto.getActualYarnId());
// //         entity.setYarnId(dto.getYarnId());
// //         entity.setWarpMeters(dto.getWarpMeters());
// //         entity.setWarpGramsPerMeter(dto.getWarpGramsPerMeter());
// //         entity.setWarpRequirement(dto.getWarpRequirement());
// //         entity.setTotalIssuedQty(dto.getTotalIssuedQty());
// //         entity.setBalanceToIssue(dto.getBalanceToIssue());
// //         entity.setActiveFlag(dto.isActiveFlag());
// //     }
    
// //     private void updateRequirementFromDTO(SizingYarnRequirement entity, SizingYarnRequirementDTO dto) {
// //         entity.setFabricDetails(dto.getFabricDetails());
// //         entity.setYarnName(dto.getYarnName());
// //         entity.setActualYarn(dto.getActualYarn());
// //         entity.setActualYarnId(dto.getActualYarnId());
// //         entity.setYarnId(dto.getYarnId());
// //         entity.setWarpMeters(dto.getWarpMeters());
// //         entity.setWarpGramsPerMeter(dto.getWarpGramsPerMeter());
// //         entity.setWarpRequirement(dto.getWarpRequirement());
// //         entity.setTotalIssuedQty(dto.getTotalIssuedQty());
// //         entity.setBalanceToIssue(dto.getBalanceToIssue());
// //         entity.setActiveFlag(dto.isActiveFlag());
// //     }
    
// //     private void populateYarnIssueFromDTO(SizingYarnIssue entity, SizingYarnIssueDTO dto,String entryIssueNo) {
// //         entity.setId(null);
// //         entity.setYarnName(dto.getYarnName());
// //         entity.setYarnId(dto.getYarnId());
// //         entity.setMillId(dto.getMillId());
// //         entity.setWarehouseId(dto.getWarehouseId());
// //         entity.setAlternateYarn(dto.getAlternateYarn());
// //         entity.setAvailableReqQty(dto.getAvailableReqQty());
// //         entity.setActualQty(dto.getAvailableQty());
// //         entity.setNoOfBags(dto.getNoOfBags());
// //         entity.setKgsPerBag(dto.getKgsPerBag());
// //         entity.setConesPerBag(dto.getConesPerBag());
// //         entity.setConesWeight(dto.getConesWeight());
// //         entity.setIssueQty(dto.getIssueQty());
// //         entity.setLotNo(dto.getLotNo());
// // //        entity.setLotId(dto.getLotId());
// //         entity.setIssueNo(dto.getIssueNo() != null ? dto.getIssueNo() : entryIssueNo);
// //         entity.setLotAvailableQty(dto.getLotAvailableQty());
// //         entity.setLotAvailableBags(dto.getLotAvailableBags());
// //         entity.setActiveFlag(dto.isActiveFlag());
// //         entity.setInwardId(dto.getInwardId() != null && dto.getInwardId() != 0 ? dto.getInwardId() : null);
// //         entity.setOrderItemId(dto.getOrderItemId() != null && dto.getOrderItemId() != 0 ? dto.getOrderItemId() : null);
// //         entity.setOutwardTypeId(dto.getOutwardTypeId());
// //         entity.setActualItemId(dto.getActualItemId());
// //         entity.setUomId(dto.getUomId());
// //         entity.setIssuedBags(dto.getIssuedBags());
// //         entity.setCompanyId(dto.getCompanyId());
// //     }
    
// //     private void updateYarnIssueFromDTO(SizingYarnIssue entity, SizingYarnIssueDTO dto,String entryIssueNo) {
// //         entity.setYarnName(dto.getYarnName());
// //         entity.setYarnId(dto.getYarnId());
// //         entity.setMillId(dto.getMillId());
// //         entity.setWarehouseId(dto.getWarehouseId());
// //         entity.setAlternateYarn(dto.getAlternateYarn());
// //         entity.setAvailableReqQty(dto.getAvailableReqQty());
// //         entity.setActualQty(dto.getAvailableQty());
// //         entity.setNoOfBags(dto.getNoOfBags());
// //         entity.setKgsPerBag(dto.getKgsPerBag());
// //         entity.setConesPerBag(dto.getConesPerBag());
// //         entity.setConesWeight(dto.getConesWeight());
// //         entity.setIssueQty(dto.getIssueQty());
// //         entity.setLotNo(dto.getLotNo());
// // //        entity.setLotId(dto.getLotId());
// //         entity.setIssueNo(dto.getIssueNo() != null ? dto.getIssueNo() : entryIssueNo);
// //         entity.setLotAvailableQty(dto.getLotAvailableQty());
// //         entity.setLotAvailableBags(dto.getLotAvailableBags());
// //         entity.setActiveFlag(dto.isActiveFlag());
// //         entity.setInwardId(dto.getInwardId() != null && dto.getInwardId() != 0 ? dto.getInwardId() : null);
// //         entity.setOrderItemId(dto.getOrderItemId() != null && dto.getOrderItemId() != 0 ? dto.getOrderItemId() : null);
// //         entity.setOutwardTypeId(dto.getOutwardTypeId());
// //         entity.setActualItemId(dto.getActualItemId());
// //         entity.setUomId(dto.getUomId());
// //         entity.setIssuedBags(dto.getIssuedBags());
// //         entity.setCompanyId(dto.getCompanyId());
// //     }

// //     // ===== YARN OUTWARD MANAGEMENT =====
    
// //     private void processLotOutward(SizingYarnIssueEntry issue, String referenceType, Long referenceId, Long outwardTypeId) {
// //         if (issue.getSizingYarnIssues() == null || outwardTypeId == null || issue.getSizingYarnIssues().isEmpty()) {
// //             return;
// //         }

// //         Long orderId = issue.getSizingPlanId();
// //         String orderNo = null;
// //         if (orderId != null) {
// //             orderNo = sizingPlanRepository.findById(orderId).map(SizingPlan::getSizingPlanNo).orElse(null);
// //         }
// //         String financialYearId = issue.getSizingYarnIssueDate() != null ? getFinancialYearId(issue.getSizingYarnIssueDate()) : null;

// //         // Determine outwardNo
// //         String firstIssueNo = issue.getSizingYarnIssues().get(0).getIssueNo();
// //         String outwardNo = firstIssueNo != null ? firstIssueNo : generateOutwardNo();
// //         String remark = "Sizing yarn issue - " + issue.getId();

// //         // 1. Create or Find ONE YarnOutward for the entire entry
// //         YarnOutward yarnOutward = yarnOutwardRepository.findByRemarks(remark).stream().findFirst()
// //             .orElseGet(() -> yarnOutwardRepository.findByOutwardNo(outwardNo).orElse(new YarnOutward()));

// //         yarnOutward.setOutwardTypeId(outwardTypeId);
// //         yarnOutward.setOutwardNo(outwardNo);
// //         yarnOutward.setCompanyId(issue.getCompanyId() != null ? issue.getCompanyId() : 1L);
// //         yarnOutward.setOutwardDate(issue.getSizingYarnIssueDate());
// //         yarnOutward.setOrderId(orderId);
// //         yarnOutward.setOrderNo(orderNo);
// //         yarnOutward.setFinancialYearId(financialYearId);
// //         yarnOutward.setVendorId(issue.getVendorId());
// //         yarnOutward.setRemarks(remark);
// //         yarnOutward.setActiveFlag(true);

// //         YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);

// //         // Maps to track availability and consolidate details globally across the transaction
// //         Map<Long, BigDecimal> localAvailableQtyMap = new HashMap<>();
// //         Map<Long, BigDecimal> localAvailableBagsMap = new HashMap<>();
// //         Map<Long, YarnOutwardStockDetails> consolidatedOutwardMap = new HashMap<>();

// //         // Initialize maps from existing details if updating
// //         if (savedYarnOutward.getYarnOutwardStockDetails() != null) {
// //             savedYarnOutward.getYarnOutwardStockDetails().forEach(d -> {
// //                 // Reset all details to ZERO before processing, regardless of ID
// //                 d.setIssuedQty(BigDecimal.ZERO);
// //                 d.setIssuedBags(BigDecimal.ZERO);
                
// //                 if (d.getYarnInwardStockDetailsId() != null) {
// //                     consolidatedOutwardMap.put(d.getYarnInwardStockDetailsId(), d);
// //                 }
// //             });
// //         }

// //         for (SizingYarnIssue sizingYarnIssue : issue.getSizingYarnIssues()) {
// //             if (sizingYarnIssue.getIssueQty() == null || sizingYarnIssue.getIssueQty().compareTo(BigDecimal.ZERO) <= 0) {
// //                 continue;
// //             }

// //             try {
// //                 BigDecimal remainingToIssue = sizingYarnIssue.getIssueQty();
// //                 BigDecimal totalIssueBags = sizingYarnIssue.getNoOfBags() != null ? 
// //                     BigDecimal.valueOf(sizingYarnIssue.getNoOfBags()) : BigDecimal.ZERO;
                
// //                 // Use actualItemId if present, otherwise fallback to yarnId
// //                 final Long searchItemId = sizingYarnIssue.getActualItemId() != null ? 
// //                     sizingYarnIssue.getActualItemId() : sizingYarnIssue.getYarnId();

// //                 // 2. Find eligible stock records
// //                 List<YarnInwardStockDetails> stocks = new java.util.ArrayList<>();
                
// //                 // Prioritize specific stock detail ID if provided, but only if it matches the yarn we're looking for
// //                 if (sizingYarnIssue.getInwardId() != null && sizingYarnIssue.getInwardId() > 0) {
// //                     yarnInwardStockDetailsRepository.findById(sizingYarnIssue.getInwardId())
// //                         .filter(s -> searchItemId == null || searchItemId.equals(s.getItemId()))
// //                         .ifPresent(stocks::add);
// //                 }
                
// //                 // Add fallback stocks for the same yarn
// //                 if (searchItemId != null) {
// //                     List<YarnInwardStockDetails> additionalStocks = new java.util.ArrayList<>();
// //                     if (sizingYarnIssue.getOrderItemId() != null && sizingYarnIssue.getOrderItemId() > 0) {
// //                         additionalStocks = yarnInwardStockDetailsRepository.findAllByOrderItemId(sizingYarnIssue.getOrderItemId());
// //                     } else {
// //                         additionalStocks = yarnInwardStockDetailsRepository.findByItemId(searchItemId);
// //                     }
                    
// //                     for (YarnInwardStockDetails s : additionalStocks) {
// //                         // Strict check on itemId
// //                         if (searchItemId.equals(s.getItemId()) && stocks.stream().noneMatch(e -> e.getId().equals(s.getId()))) {
// //                             stocks.add(s);
// //                         }
// //                     }
// //                 }
// //                 stocks.sort(java.util.Comparator.comparing(YarnInwardStockDetails::getId));

// //                 boolean firstInwardAssignedForThisLine = false;

// //                 for (YarnInwardStockDetails yarnStock : stocks) {
// //                     if (remainingToIssue.compareTo(BigDecimal.ZERO) <= 0) break;
                    
// //                     BigDecimal lotAvailable = localAvailableQtyMap.getOrDefault(yarnStock.getId(), 
// //                         yarnStock.getAvailableQty() != null ? yarnStock.getAvailableQty() : BigDecimal.ZERO);
// //                     BigDecimal lotBagsAvailable = localAvailableBagsMap.getOrDefault(yarnStock.getId(),
// //                         yarnStock.getAvailableBags() != null ? yarnStock.getAvailableBags() : BigDecimal.ZERO);
                    
// //                     if (lotAvailable.compareTo(BigDecimal.ZERO) <= 0) continue;
                    
// //                     BigDecimal toTake = remainingToIssue.min(lotAvailable);
                    
// //                     // Calculate bags proportionally
// //                     BigDecimal bagsToTake;
// //                     if (toTake.compareTo(remainingToIssue) == 0) {
// //                         bagsToTake = totalIssueBags.min(lotBagsAvailable);
// //                     } else {
// //                         bagsToTake = totalIssueBags.multiply(toTake)
// //                             .divide(sizingYarnIssue.getIssueQty(), 4, RoundingMode.HALF_UP).min(lotBagsAvailable);
// //                     }
                    
// //                     // 3. Consolidate into ONE YarnOutwardStockDetails per inward source
// //                     YarnOutwardStockDetails outwardDetail = consolidatedOutwardMap.get(yarnStock.getId());
// //                     if (outwardDetail == null) {
// //                         outwardDetail = new YarnOutwardStockDetails();
// //                         outwardDetail.setYarnOutward(savedYarnOutward);
// //                         outwardDetail.setOutwardTypeId(outwardTypeId);
// //                         outwardDetail.setWarehouseId(yarnStock.getWarehouseId());
// //                         outwardDetail.setActualItemId(yarnStock.getItemId());
// //                         outwardDetail.setInwardId(yarnStock.getInwardId());
// //                         outwardDetail.setOrderItemId(yarnStock.getOrderItemId());
// //                         outwardDetail.setYarnInwardStockDetailsId(yarnStock.getId());
// //                         outwardDetail.setMillId(yarnStock.getMillId());
// //                         outwardDetail.setUomId(yarnStock.getUomId());
// //                         outwardDetail.setLotNo(yarnStock.getLotNo());
// //                         outwardDetail.setIssuedQty(BigDecimal.ZERO);
// //                         outwardDetail.setIssuedBags(BigDecimal.ZERO);
// //                         outwardDetail.setKgsPerBag(sizingYarnIssue.getKgsPerBag());
// //                         outwardDetail.setConesPerBag(sizingYarnIssue.getConesPerBag());
// //                         outwardDetail.setConesWeight(sizingYarnIssue.getConesWeight());
                        
// //                         consolidatedOutwardMap.put(yarnStock.getId(), outwardDetail);
// //                         if (savedYarnOutward.getYarnOutwardStockDetails() == null) {
// //                             savedYarnOutward.setYarnOutwardStockDetails(new java.util.ArrayList<>());
// //                         }
// //                         savedYarnOutward.getYarnOutwardStockDetails().add(outwardDetail);
// //                     }
                    
// //                     outwardDetail.setIssuedQty(outwardDetail.getIssuedQty().add(toTake));
// //                     outwardDetail.setIssuedBags(outwardDetail.getIssuedBags().add(bagsToTake));
                    
// //                     // 4. Map IDs back to sizingYarnIssue for traceability
// //                     if (!firstInwardAssignedForThisLine) {
// //                         sizingYarnIssue.setOrderItemId(yarnStock.getOrderItemId());
// //                         sizingYarnIssue.setInwardId(yarnStock.getId());
// //                         firstInwardAssignedForThisLine = true;
// //                     }
                    
// //                     // 5. Update state for next iterations
// //                     localAvailableQtyMap.put(yarnStock.getId(), lotAvailable.subtract(toTake));
// //                     localAvailableBagsMap.put(yarnStock.getId(), lotBagsAvailable.subtract(bagsToTake));
                    
// //                     remainingToIssue = remainingToIssue.subtract(toTake);
// //                     totalIssueBags = totalIssueBags.subtract(bagsToTake);
// //                 }
                
// //                 sizingYarnIssueRepository.save(sizingYarnIssue);
                
// //             } catch (Exception e) {
// //                 System.err.println("Error processing sizing yarn lot outward: " + e.getMessage());
// //                 throw new RuntimeException("Failed to process yarn lot outward: " + e.getMessage(), e);
// //             }
// //         }

// //         // Clean up empty/unused details from previous state if any
// //         if (savedYarnOutward.getYarnOutwardStockDetails() != null) {
// //             savedYarnOutward.getYarnOutwardStockDetails().removeIf(d -> d.getIssuedQty().compareTo(BigDecimal.ZERO) <= 0);
// //         }

// //         yarnOutwardRepository.saveAndFlush(savedYarnOutward);
        
// //         // Update actual stock levels in DB
// //         if (savedYarnOutward.getYarnOutwardStockDetails() != null) {
// //             updateAvailableQtyInInwardStockDetails(savedYarnOutward.getYarnOutwardStockDetails());
// //         }
// //     }

// //     private void reverseLotReductions(SizingYarnIssueEntry issue, boolean deleteOutward) {
// //         String remark = "Sizing yarn issue - " + issue.getId();
// //         List<YarnOutward> outwards = yarnOutwardRepository.findByRemarks(remark);
        
// //         for (YarnOutward outward : outwards) {
// //             if (outward.getYarnOutwardStockDetails() != null) {
// //                 // Restore stock
// //                 for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
// //                     Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();
                    
// //                     if (detail.getYarnInwardStockDetailsId() != null && detail.getYarnInwardStockDetailsId() != 0) {
// //                         inwardDetailOpt = yarnInwardStockDetailsRepository.findById(detail.getYarnInwardStockDetailsId());
// //                     }
                    
// //                     if (!inwardDetailOpt.isPresent() && detail.getOrderItemId() != null && detail.getOrderItemId() != 0) {
// //                         inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(detail.getOrderItemId());
// //                     }
                    
// //                     if (inwardDetailOpt.isPresent()) {
// //                         YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
                        
// //                         BigDecimal qtyToRestore = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
// //                         BigDecimal bagsToRestore = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
                        
// //                         // Initialize available values if they are null
// //                         if (inwardDetail.getAvailableQty() == null) {
// //                             inwardDetail.setAvailableQty(inwardDetail.getAcceptedQty() != null ? inwardDetail.getAcceptedQty() : BigDecimal.ZERO);
// //                         }
// //                         if (inwardDetail.getAvailableBags() == null) {
// //                             inwardDetail.setAvailableBags(inwardDetail.getInwardBags() != null ? inwardDetail.getInwardBags() : BigDecimal.ZERO);
// //                         }
                        
// //                         inwardDetail.setAvailableQty(inwardDetail.getAvailableQty().add(qtyToRestore));
// //                         inwardDetail.setAvailableBags(inwardDetail.getAvailableBags().add(bagsToRestore));
                        
// //                         yarnInwardStockDetailsRepository.save(inwardDetail);
// //                     }
// //                 }
// //             }
// //             if (deleteOutward) {
// //                 yarnOutwardRepository.delete(outward);
// //             }
// //         }
// //         // Ensure changes are flushed before subsequent processing
// //         yarnOutwardRepository.flush();
// //     }

// //     private String generateOutwardNo() {
// //         return "OUT-" + System.currentTimeMillis();
// //     }

// //     // ===== VALIDATION =====
    
// //     private void validateSizingYarnIssue(SizingYarnIssueEntryDTO dto) {
// //         if (dto.getSizingYarnIssue() != null) {
// //             for (SizingYarnIssueDTO sizingYarnIssue : dto.getSizingYarnIssue()) {
// //                 // Use actualItemId if present, otherwise fallback to yarnId
// //                 Long searchItemId = sizingYarnIssue.getActualItemId() != null ? 
// //                     sizingYarnIssue.getActualItemId() : sizingYarnIssue.getYarnId();
                
// //                 List<YarnInwardStockDetails> inwardDetails = 
// //                     yarnInwardStockDetailsRepository.findByItemId(searchItemId);
                
// //                 if (inwardDetails.isEmpty()) {
// //                     throw new EntityNotFoundException(
// //                         "No yarn inward stock details found for yarn ID: " + searchItemId);
// //                 }
                
// //                 BigDecimal totalAvailableQty = BigDecimal.ZERO;
// //                 for (YarnInwardStockDetails detail : inwardDetails) {
// //                     if (detail.getAvailableQty() != null) {
// //                         totalAvailableQty = totalAvailableQty.add(detail.getAvailableQty());
// //                     }
// //                 }
                
// //                 BigDecimal requestedQty = sizingYarnIssue.getIssueQty() != null ? sizingYarnIssue.getIssueQty() : BigDecimal.ZERO;
// //                 if (totalAvailableQty.compareTo(requestedQty) < 0) {
// //                     throw new IllegalArgumentException(
// //                         String.format("Insufficient available quantity for yarn ID %s (Available: %s, Requested: %s)",
// //                             sizingYarnIssue.getYarnId(), totalAvailableQty, requestedQty));
// //                 }
// //             }
// //         }
// //     }

// //     private PaginatedResponse<SizingYarnIssueEntryDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit,
// //             Function<List<Long>, List<SizingYarnIssueEntry>> fetcher) {
// //         long off = offset != null ? Math.max(0, offset) : 0;
// //         long lim = (limit != null && limit > 0) ? limit : 10;
// //         long total = allIds.size();
// //         int start = (int) Math.min(off, total);
// //         if (start >= total) {
// //             return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, Collections.emptyList());
// //         }
// //         int end = (int) Math.min(off + lim, total);
// //         List<Long> pageIds = allIds.subList(start, end);
// //         List<SizingYarnIssueEntry> entries = pageIds.isEmpty() ? Collections.emptyList() : fetcher.apply(pageIds);
// //         List<SizingYarnIssueEntryDTO> dtos = entries.stream()
// //                 .map(this::convertToDTO)
// //                 .collect(Collectors.toList());
// //         return new PaginatedResponse<>(
// //                 total,
// //                 end < total,
// //                 off > 0,
// //                 off,
// //                 lim,
// //                 dtos
// //         );
// //     }

// //     // ===== CONVERSION METHODS =====
    
// //     private SizingYarnIssueEntryDTO convertToDTO(SizingYarnIssueEntry entity) {
// //         SizingYarnIssueEntryDTO dto = new SizingYarnIssueEntryDTO();
// //         dto.setId(entity.getId());
// //         dto.setVendorId(entity.getVendorId());
// //         dto.setCompanyId(entity.getCompanyId());
// //         dto.setOutwardTypeId(entity.getOutwardTypeId());
// //         dto.setSizingPlanId(entity.getSizingPlanId());
// //         dto.setTransportationDetails(entity.getTransportationDetails());
// //         dto.setTermsConditionsId(entity.getTermsConditionsId());
// //         dto.setFabricDetails(entity.getFabricDetails());
// //         dto.setSizingYarnIssueDate(entity.getSizingYarnIssueDate());
// //         dto.setYarnIssueChallanNo(entity.getYarnIssueChallanNo());
// //         dto.setActiveFlag(entity.isActiveFlag());
// //         dto.setRemark(entity.getRemark());
// //         dto.setVehicleNo(entity.getVehicleNo());
// //         dto.setGrossWt(entity.getGrossWt());
// //         if (entity.getSizingYarnIssues() != null && !entity.getSizingYarnIssues().isEmpty()) {
// //             entity.getSizingYarnIssues().stream().findFirst().ifPresent(issue -> dto.setIssueNo(issue.getIssueNo()));
// //         }
// //         if (entity.getRequirements() != null) {
// //             List<SizingYarnRequirementDTO> requirementDTOs = entity.getRequirements().stream()
// //                     .map(this::convertRequirementToDTO)
// //                     .collect(Collectors.toList());
// //             dto.setRequirements(requirementDTOs);
// //         }
        
// //         if (entity.getSizingYarnIssues() != null) {
// //             List<SizingYarnIssueDTO> sizingYarnIssueDTOs = entity.getSizingYarnIssues().stream()
// //                     .map(this::convertYarnIssueToDTO)
// //                     .collect(Collectors.toList());
// //             dto.setSizingYarnIssue(sizingYarnIssueDTOs);
// //         }


// //         return dto;
// //     }

// //     private SizingYarnRequirementDTO convertRequirementToDTO(SizingYarnRequirement requirement) {
// //         SizingYarnRequirementDTO dto = new SizingYarnRequirementDTO();
// //         dto.setId(requirement.getId());
// //         dto.setFabricDetails(requirement.getFabricDetails());
// //         dto.setYarnName(requirement.getYarnName());
// //         dto.setActualYarn(requirement.getActualYarn());
// //         dto.setActualYarnId(requirement.getActualYarnId());
// //         dto.setYarnId(requirement.getYarnId());
// //         dto.setWarpMeters(requirement.getWarpMeters());
// //         dto.setWarpGramsPerMeter(requirement.getWarpGramsPerMeter());
// //         dto.setWarpRequirement(requirement.getWarpRequirement());
// //         dto.setTotalIssuedQty(requirement.getTotalIssuedQty());
// //         dto.setBalanceToIssue(requirement.getBalanceToIssue());
// //         dto.setActiveFlag(requirement.isActiveFlag());

// //         return dto;
// //     }

// //     private SizingYarnIssueDTO convertYarnIssueToDTO(SizingYarnIssue sizingYarnIssue) {
// //         SizingYarnIssueDTO dto = new SizingYarnIssueDTO();
// //         dto.setId(sizingYarnIssue.getId());
// //         dto.setYarnName(sizingYarnIssue.getYarnName());
// //         dto.setYarnId(sizingYarnIssue.getYarnId());
// //         dto.setMillId(sizingYarnIssue.getMillId());
// //         dto.setWarehouseId(sizingYarnIssue.getWarehouseId());
// //         dto.setAlternateYarn(sizingYarnIssue.getAlternateYarn());
// //         dto.setAvailableReqQty(sizingYarnIssue.getAvailableReqQty());
// //         dto.setAvailableQty(sizingYarnIssue.getAvailableQty());
// //         dto.setNoOfBags(sizingYarnIssue.getNoOfBags());
// //         dto.setKgsPerBag(sizingYarnIssue.getKgsPerBag());
// //         dto.setConesPerBag(sizingYarnIssue.getConesPerBag());
// //         dto.setConesWeight(sizingYarnIssue.getConesWeight());
// //         dto.setIssueQty(sizingYarnIssue.getIssueQty());
// //         dto.setLotNo(sizingYarnIssue.getLotNo());
// // //        dto.setLotId(sizingYarnIssue.getLotId());
// //         dto.setIssueNo(sizingYarnIssue.getIssueNo());
// //         dto.setLotAvailableQty(sizingYarnIssue.getLotAvailableQty());
// //         dto.setLotAvailableBags(sizingYarnIssue.getLotAvailableBags());
// //         dto.setActiveFlag(sizingYarnIssue.isActiveFlag());
// //         dto.setSizingYarnIssueEntryId(sizingYarnIssue.getSizingYarnIssueEntry() != null ? sizingYarnIssue.getSizingYarnIssueEntry().getId() : null);
// //         dto.setInwardId(sizingYarnIssue.getInwardId());
// //         dto.setOrderItemId(sizingYarnIssue.getOrderItemId());
// //         dto.setOutwardTypeId(sizingYarnIssue.getOutwardTypeId());
// //         dto.setActualItemId(sizingYarnIssue.getActualItemId());
// //         dto.setUomId(sizingYarnIssue.getUomId());
// //         dto.setIssuedBags(sizingYarnIssue.getIssuedBags());
// //         dto.setCompanyId(sizingYarnIssue.getCompanyId());
// //         return dto;
// //     }
    
// //      @Transactional(readOnly = true)
// //     public List<YarnInwardStockDetailsDto> getYarnInwardStockDetailsByItemIds(List<Long> itemIds, Long outwardTypeId, Long companyId) {
// //         List<YarnInwardStockDetails> details = new java.util.ArrayList<>();
        
// //         for (Long itemId : itemIds) {
// //             details.addAll(yarnInwardStockDetailsRepository.findByItemId(itemId));
// //         }
        
// //         return details.stream()
// //                 .filter(detail -> companyId == null || (detail.getYarnInventory() != null && companyId.equals(detail.getYarnInventory().getCompanyId())))
// //                 .map(detail -> {
// //                     YarnInwardStockDetailsDto dto = new YarnInwardStockDetailsDto(detail);
// //                     List<YarnOutwardStockDetails> outwardDetails = yarnOutwardStockDetailsRepository.findByOrderItemId(detail.getOrderItemId());
// //                     if (outwardTypeId != null) {
// //                         dto.setOutwardTypeId(outwardTypeId);
// //                         outwardDetails.stream()
// //                             .filter(outward -> outwardTypeId.equals(outward.getOutwardTypeId()))
// //                             .findFirst()
// //                             .ifPresent(outward -> dto.setOutwardTypeId(outward.getOutwardTypeId()));
// //                     } else {
// //                         outwardDetails.stream()
// //                             .findFirst()
// //                             .ifPresent(outward -> dto.setOutwardTypeId(outward.getOutwardTypeId()));
// //                     }
// //                     return dto;
// //                 })
// //                 .filter(dto -> dto.getAvailableQty() != null && dto.getAvailableQty().compareTo(BigDecimal.ZERO) > 0)
// //                 .collect(Collectors.toList());
// //     }
// //     // ===== HELPER METHODS FOR QUANTITY AND OUTWARD MANAGEMENT =====
    
// //     private BigDecimal calculateTotalIssuedQuantity(Long orderItemId) {
// //         if (orderItemId == null) {
// //             return BigDecimal.ZERO;
// //         }
// //         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(orderItemId);
// //         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
// //     }

// //     private BigDecimal calculateTotalIssuedBags(Long orderItemId) {
// //         if (orderItemId == null) {
// //             return BigDecimal.ZERO;
// //         }
// //         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(orderItemId);
// //         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
// //     }

// //     private String generateYarnOutwardNo(SizingYarnIssueEntry sizingYarnIssueEntry) {
// //         return "SYO-" + LocalDate.now().getYear() + "-" + sizingYarnIssueEntry.getId();
// //     }

// //     private String getFinancialYearId(LocalDate date) {
// //         int year = date.getYear();
// //         int month = date.getMonthValue();
// //         int startYear;
// //         int endYear;
// //         if (month >= 4) {
// //             startYear = year;
// //             endYear = year + 1;
// //         } else {
// //             startYear = year - 1;
// //             endYear = year;
// //         }
// //         String endYearLastTwo = String.format("%02d", endYear % 100);
// //         return startYear + "-" + endYearLastTwo;
// //     }

// //     private String getShortFinancialYear(LocalDate date) {
// //         if (date == null) {
// //             date = LocalDate.now();
// //         }
// //         int year = date.getYear();
// //         int month = date.getMonthValue();
// //         int startYear = month >= 4 ? year : year - 1;
// //         int endYear = startYear + 1;
// //         return String.format("%02d-%02d", startYear % 100, endYear % 100);
// //     }

// //     private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
// //         Map<Long, BigDecimal> qtyAdjustments = new HashMap<>();
// //         Map<Long, BigDecimal> bagAdjustments = new HashMap<>();
// //         Set<Long> stockDetailsIds = new HashSet<>();

// //         for (YarnOutwardStockDetails outwardDetail : outwardDetails) {
// //             Long stockId = outwardDetail.getYarnInwardStockDetailsId();
// //             if (stockId == null || stockId == 0) {
// //                 if (outwardDetail.getOrderItemId() != null && outwardDetail.getOrderItemId() != 0) {
// //                     Optional<YarnInwardStockDetails> opt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(outwardDetail.getOrderItemId());
// //                     if (opt.isPresent()) {
// //                         stockId = opt.get().getId();
// //                         outwardDetail.setYarnInwardStockDetailsId(stockId);
// //                     }
// //                 }
// //             }
            
// //             if (stockId != null && stockId != 0) {
// //                 stockDetailsIds.add(stockId);
// //                 qtyAdjustments.merge(stockId, outwardDetail.getIssuedQty() != null ? outwardDetail.getIssuedQty() : BigDecimal.ZERO, BigDecimal::add);
// //                 bagAdjustments.merge(stockId, outwardDetail.getIssuedBags() != null ? outwardDetail.getIssuedBags() : BigDecimal.ZERO, BigDecimal::add);
// //             }
// //         }

// //         for (Long stockId : stockDetailsIds) {
// //             Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(stockId);
// //             if (!inwardDetailOpt.isPresent()) {
// //                 continue;
// //             }
            
// //             YarnInwardStockDetails detail = inwardDetailOpt.get();
// //             BigDecimal qtyDelta = qtyAdjustments.getOrDefault(stockId, BigDecimal.ZERO);
// //             BigDecimal bagDelta = bagAdjustments.getOrDefault(stockId, BigDecimal.ZERO);

// //             // Handle Available Quantity
// //             if (detail.getAvailableQty() == null) {
// //                 BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
// //                 BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockId);
// //                 detail.setAvailableQty(acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO));
// //             } else {
// //                 detail.setAvailableQty(detail.getAvailableQty().subtract(qtyDelta));
// //             }

// //             // Handle Available Bags
// //             if (detail.getAvailableBags() == null) {
// //                 BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;
// //                 BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockId);
// //                 detail.setAvailableBags(inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO));
// //             } else {
// //                 detail.setAvailableBags(detail.getAvailableBags().subtract(bagDelta));
// //             }

// //             // Ensure not negative
// //             if (detail.getAvailableQty() != null && detail.getAvailableQty().compareTo(BigDecimal.ZERO) < 0) {
// //                 detail.setAvailableQty(BigDecimal.ZERO);
// //             }
// //             if (detail.getAvailableBags() != null && detail.getAvailableBags().compareTo(BigDecimal.ZERO) < 0) {
// //                 detail.setAvailableBags(BigDecimal.ZERO);
// //             }

// //             yarnInwardStockDetailsRepository.save(detail);
// //         }
// //     }
    
// //     private void populateInwardDetailsFromStockDetails(YarnOutwardStockDetails outwardDetail, Long yarnorderItemId) {
// //         if (yarnorderItemId != null) {
// //             Optional<YarnInwardStockDetails> inwardStockDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnorderItemId);
// //             if (inwardStockDetail.isPresent()) {
// //                 YarnInwardStockDetails details = inwardStockDetail.get();
// //                 outwardDetail.setYarnInwardStockDetailsId(details.getId());
// //                 if (details.getYarnInventory() != null) {
// //                     outwardDetail.setInwardId(details.getYarnInventory().getId());
// //                 }
// //                 if (details.getLotNo() != null) {
// //                     outwardDetail.setLotNo(details.getLotNo());
// //                 }
// //             }
// //         }
// //     }
    
// //     private void populateLotNoFromStockDetails(YarnOutwardStockDetails outwardDetail, Long yarnorderItemId) {
// //         if (yarnorderItemId != null) {
// //             Optional<YarnInwardStockDetails> inwardStockDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnorderItemId);
// //             if (inwardStockDetail.isPresent()) {
// //                 YarnInwardStockDetails details = inwardStockDetail.get();
// //                 outwardDetail.setYarnInwardStockDetailsId(details.getId());
// //                 if (details.getLotNo() != null) {
// //                     outwardDetail.setLotNo(details.getLotNo());
// //                 }
// //             }
// //         }
// //     }

// //      private void createYarnOutwardForEntry(SizingYarnIssueEntry sizingYarnIssueEntry, SizingYarnIssueEntryDTO dto) {
// //         if (dto.getSizingYarnIssue() == null || dto.getSizingYarnIssue().isEmpty()) {
// //             return;
// //         }

// //         Long outwardTypeId = 53L;
// //         Long companyId = 1L;

// //         YarnOutward yarnOutward = new YarnOutward();
// //         yarnOutward.setOutwardTypeId(outwardTypeId);
// //         String outwardNo = generateYarnOutwardNo(sizingYarnIssueEntry);
// //         if (sizingYarnIssueEntry.getSizingYarnIssues() != null && !sizingYarnIssueEntry.getSizingYarnIssues().isEmpty()) {
// //             String issueNo = sizingYarnIssueEntry.getSizingYarnIssues().get(0).getIssueNo();
// //             if (issueNo != null) {
// //                 outwardNo = issueNo;
// //             }
// //         }
// //         yarnOutward.setOutwardNo(outwardNo);
// //         yarnOutward.setCompanyId(companyId);

// //         LocalDate outwardDate = dto.getSizingYarnIssueDate() != null ? 
// //             dto.getSizingYarnIssueDate() : LocalDate.now();
// //         yarnOutward.setOutwardDate(outwardDate);

// //         yarnOutward.setFinancialYearId(getFinancialYearId(outwardDate));
// //         yarnOutward.setOrderId(dto.getSizingPlanId());
        
// //         if (dto.getSizingPlanId() != null) {
// //             sizingPlanRepository.findById(dto.getSizingPlanId())
// //                 .ifPresent(plan -> yarnOutward.setOrderNo(plan.getSizingPlanNo()));
// //         }
        
// //         yarnOutward.setVendorId(dto.getVendorId());
// //         yarnOutward.setRemarks(null);
// //         yarnOutward.setActiveFlag(true);

// //         YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);

// //         List<YarnOutwardStockDetails> outwardDetailsToSave = new java.util.ArrayList<>();

// //         for (SizingYarnIssueDTO yarnIssue : dto.getSizingYarnIssue()) {
// //             YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
// //             outwardDetail.setYarnOutward(savedYarnOutward);
// //             outwardDetail.setOrderItemId(yarnIssue.getOrderItemId());
// //             outwardDetail.setOutwardTypeId(yarnIssue.getOutwardTypeId());
// //             outwardDetail.setActualItemId(yarnIssue.getActualItemId());
// //             outwardDetail.setUomId(yarnIssue.getUomId());
// //             outwardDetail.setMillId(yarnIssue.getMillId());
// //             outwardDetail.setWarehouseId(yarnIssue.getWarehouseId());
// //             outwardDetail.setIssuedQty(yarnIssue.getIssueQty());
// //             outwardDetail.setIssuedBags(yarnIssue.getIssuedBags());
// //             outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag());
// //             outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
// //             outwardDetail.setConesWeight(yarnIssue.getConesWeight());

// //             if (yarnIssue.getInwardId() != null) {
// //                 outwardDetail.setInwardId(yarnIssue.getInwardId());
// //                 populateLotNoFromStockDetails(outwardDetail, yarnIssue.getOrderItemId());
// //             } else {
// //                 populateInwardDetailsFromStockDetails(outwardDetail, yarnIssue.getOrderItemId());
// //             }

// //             outwardDetailsToSave.add(outwardDetail);
// //         }

// //         if (dto.getYarnOutwardStockDetails() != null) {
// //             for (YarnOutwardStockDetailsDTO outwardDTO : dto.getYarnOutwardStockDetails()) {
// //                 YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
// //                 outwardDetail.setYarnOutward(savedYarnOutward);
// //                 outwardDetail.setOrderItemId(outwardDTO.getOrderItemId());
// //                 outwardDetail.setOutwardTypeId(outwardDTO.getOutwardTypeId());
// //                 outwardDetail.setActualItemId(outwardDTO.getActualItemId());
// //                 outwardDetail.setUomId(outwardDTO.getUomId());
// //                 outwardDetail.setMillId(outwardDTO.getMillId());
// //                 outwardDetail.setWarehouseId(outwardDTO.getWarehouseId());
// //                 outwardDetail.setIssuedQty(outwardDTO.getIssuedQty());
// //                 outwardDetail.setIssuedBags(outwardDTO.getIssuedBags());
// //                 outwardDetail.setKgsPerBag(outwardDTO.getKgsPerBag());
// //                 outwardDetail.setConesPerBag(outwardDTO.getConesPerBag());
// //                 outwardDetail.setConesWeight(outwardDTO.getConesWeight());

// //                 if (outwardDTO.getInwardId() != null) {
// //                     outwardDetail.setInwardId(outwardDTO.getInwardId());
// //                     populateLotNoFromStockDetails(outwardDetail, outwardDTO.getOrderItemId());
// //                 } else {
// //                     populateInwardDetailsFromStockDetails(outwardDetail, outwardDTO.getOrderItemId());
// //                 }

// //                 outwardDetailsToSave.add(outwardDetail);
// //             }
// //         }

// //         if (!outwardDetailsToSave.isEmpty()) {
// //             List<YarnOutwardStockDetails> savedOutwardDetails = yarnOutwardStockDetailsRepository.saveAll(outwardDetailsToSave);
// //             savedYarnOutward.setYarnOutwardStockDetails(savedOutwardDetails);
// //             yarnOutwardRepository.save(savedYarnOutward);
            
// //             updateAvailableQtyInInwardStockDetails(savedOutwardDetails);
// //         }
// //     }

// //     private Long normalizeId(Long id) {
// //         return (id != null && id > 0) ? id : null;
// //     }
// // }

// package com.tech3.erp.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.SizingYarnInwardDTO;
// import com.tech3.erp.dto.SizingYarnInwardDetailDTO;
// import com.tech3.erp.dto.SizingYarnIssueDTO;
// import com.tech3.erp.dto.SizingYarnIssueEntryDTO;
// import com.tech3.erp.dto.SizingYarnRequirementDTO;
// import com.tech3.erp.dto.YarnInwardStockDetailsDto;
// import com.tech3.erp.dto.YarnOutwardStockDetailsDTO;
// import com.tech3.erp.entity.CodesDetail;
// import com.tech3.erp.entity.LotEntry;
// import com.tech3.erp.entity.LotOutward;
// import com.tech3.erp.entity.SizingJobWorkOrder;
// import com.tech3.erp.entity.SizingPlan;
// import com.tech3.erp.entity.SizingQualityDetails;
// import com.tech3.erp.entity.SizingYarnIssue;
// import com.tech3.erp.entity.SizingYarnInward;
// import com.tech3.erp.entity.SizingYarnInwardDetail;
// import com.tech3.erp.entity.SizingYarnIssueEntry;
// import com.tech3.erp.entity.SizingYarnRequirement;
// import com.tech3.erp.entity.Vendor;
// import com.tech3.erp.entity.YarnInventory;
// import com.tech3.erp.entity.YarnInwardStockDetails;
// import com.tech3.erp.entity.YarnOutward;
// import com.tech3.erp.entity.YarnOutwardStockDetails;
// import com.tech3.erp.repository.CodesDetailRepository;
// import com.tech3.erp.repository.LotEntryRepository;
// import com.tech3.erp.repository.LotOutwardRepository;
// import com.tech3.erp.repository.SizingJobWorkOrderRepository;
// import com.tech3.erp.repository.SizingPlanRepository;
// import com.tech3.erp.repository.SizingQualityDetailsRepository;
// import com.tech3.erp.repository.SizingYarnInwardRepository;
// import com.tech3.erp.repository.SizingYarnIssueEntryRepository;
// import com.tech3.erp.repository.SizingYarnIssueRepository;
// import com.tech3.erp.repository.SizingYarnRequirementRepository;
// import com.tech3.erp.repository.VendorRepository;
// import com.tech3.erp.repository.YarnInventoryRepository;
// import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
// import com.tech3.erp.repository.YarnOutwardRepository;
// import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;

// import jakarta.persistence.EntityNotFoundException;

// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.Comparator;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Map;
// import java.util.function.Function;
// import java.util.stream.Collectors;

// @Service
// @Transactional
// public class SizingYarnIssueEntryService {

//     @Autowired
//     private SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository;
    
//     @Autowired
//     private SizingYarnRequirementRepository sizingYarnRequirementRepository;
    
//     @Autowired
//     private SizingYarnIssueRepository sizingYarnIssueRepository;
    
//     @Autowired
//     private LotEntryRepository lotEntryRepository;
    
//     @Autowired
//     private LotOutwardRepository lotOutwardRepository;
    
//     @Autowired
//     private SizingQualityDetailsRepository sizingQualityDetailsRepository;
    
//     @Autowired
//     private SizingPlanRepository sizingPlanRepository;
    
//     @Autowired
//     private SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    
//     @Autowired
//     private ReferenceValidationService referenceValidationService;
    
//     @Autowired
//     private SizingYarnInwardService sizingYarnInwardService;

//     @Autowired
//     private SizingYarnInwardRepository sizingYarnInwardRepository;
    
//     @Autowired
//     private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
//     @Autowired
//     private YarnInventoryRepository yarnInventoryRepository;
    
//     @Autowired
//     private YarnOutwardRepository yarnOutwardRepository;
    
//     @Autowired
//     private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;

//        @Autowired
//     private CodesDetailRepository codesDetailRepository;

//     @Autowired
//     private VendorRepository vendorRepository;

//     @Transactional(readOnly = true)
//     public List<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrys() {
//         List<SizingYarnIssueEntry> issues = sizingYarnIssueEntryRepository.findAll();
//         return issues.stream()
//                 .sorted(Comparator.comparing(SizingYarnIssueEntry::getId).reversed())
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }

//     @Transactional(readOnly = true)
//     public PaginatedResponse<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrysWithPagination(Long offset, Long limit) {
//         return buildPaginatedResponse(
//                 sizingYarnIssueEntryRepository.findAllIds(),
//                 offset,
//                 limit,
//                 ids -> ids.isEmpty() ? Collections.emptyList() : sizingYarnIssueEntryRepository.findByIdsWithDetails(ids)
//         );
//     }

//     @Transactional(readOnly = true)
//     public PaginatedResponse<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrysByCompanyWithPagination(Long companyId, Long offset, Long limit) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return buildPaginatedResponse(
//                 sizingYarnIssueEntryRepository.findIdsByCompanyId(companyId),
//                 offset,
//                 limit,
//                 ids -> ids.isEmpty() ? Collections.emptyList() : sizingYarnIssueEntryRepository.findByCompanyIdAndIds(companyId, ids)
//         );
//     }

//     @Transactional(readOnly = true)
//     public List<SizingYarnIssueEntryDTO> getSizingYarnIssueEntrysByCompany(Long companyId) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return sizingYarnIssueEntryRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }

//     @Transactional(readOnly = true)
//     public SizingYarnIssueEntryDTO getSizingYarnIssueEntryById(Long id) {
//         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
//         return convertToDTO(issue);
//     }

//     @Transactional(readOnly = true)
//     public SizingYarnIssueEntryDTO getSizingYarnIssueEntryByIdAndCompany(Long id, Long companyId) {
//         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
//         return convertToDTO(issue);
//     }

//     @Transactional
//     public SizingYarnIssueEntryDTO createSizingYarnIssueEntry(SizingYarnIssueEntryDTO dto) {
//         // Validate before processing
//         validateSizingYarnIssue(dto);
        
//         SizingPlan sizingPlan = referenceValidationService.requireReference(
//                 dto.getSizingPlanId(),
//                 () -> sizingPlanRepository.findById(dto.getSizingPlanId()),
//                 "Sizing Plan",
//                 dto.getSizingPlanNo());
        
//         SizingYarnIssueEntry issue = new SizingYarnIssueEntry();
//         populateEntityFromDTO(issue, dto, false);
        
//         // Ensure sizingPlanId is set correctly from the validated sizingPlan
//         issue.setSizingPlanId(normalizeId(dto.getSizingPlanId()));
        
//         SizingYarnIssueEntry savedIssue = sizingYarnIssueEntryRepository.save(issue);
        
//         // Process lot reductions and outward entries
//         processLotOutward(savedIssue, "SIZING_ISSUE", savedIssue.getId(), dto.getOutwardTypeId());
        
//         // Update sizing plan actualYarn based on requirements
//         updateSizingPlanActualYarn(dto.getSizingPlanId(), dto.getRequirements());
        
//         syncSizingYarnInwardRecord(savedIssue);
        
//         return convertToDTO(savedIssue);
//     }

//     @Transactional
//     public SizingYarnIssueEntryDTO updateSizingYarnIssueEntry(Long id, SizingYarnIssueEntryDTO dto) {
//         validateSizingYarnIssue(dto);
//         SizingYarnIssueEntry existingIssue = sizingYarnIssueEntryRepository.findById(id)
//             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
//         if (dto.getCompanyId() == null) {
//             dto.setCompanyId(existingIssue.getCompanyId());
//         }
//         return updateAndPersistIssue(existingIssue, dto, id);
//     }

//     @Transactional
//     public SizingYarnIssueEntryDTO updateSizingYarnIssueEntryByCompany(Long id, Long companyId, SizingYarnIssueEntryDTO dto) {
//         validateSizingYarnIssue(dto);
//         SizingYarnIssueEntry existingIssue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
//             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
//         dto.setCompanyId(companyId);
//         return updateAndPersistIssue(existingIssue, dto, id);
//     }

//     @Transactional
//     public void deleteSizingYarnIssueEntry(Long id) {
//         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findById(id)
//             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
//         deleteIssueEntry(issue);
//     }

//     @Transactional
//     public void deleteSizingYarnIssueEntryByCompany(Long id, Long companyId) {
//         SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
//             .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
//         deleteIssueEntry(issue);
//     }

//     private SizingYarnIssueEntryDTO updateAndPersistIssue(SizingYarnIssueEntry existingIssue, SizingYarnIssueEntryDTO dto, Long referenceId) {
//         reverseLotReductions(existingIssue, true);
//         populateEntityFromDTO(existingIssue, dto, true);
//         processLotOutward(existingIssue, "SIZING_ISSUE", referenceId, dto.getOutwardTypeId());
//         updateSizingPlanActualYarn(dto.getSizingPlanId(), dto.getRequirements());
//         SizingYarnIssueEntry savedIssue = sizingYarnIssueEntryRepository.save(existingIssue);
//         syncSizingYarnInwardRecord(savedIssue);
//         return convertToDTO(savedIssue);
//     }

//     private void deleteIssueEntry(SizingYarnIssueEntry issue) {
//         reverseLotReductions(issue, true);
//         deleteSizingYarnInwardIfPresent(issue);
//         sizingYarnIssueEntryRepository.delete(issue);
//     }

//     private void deleteSizingYarnInwardIfPresent(SizingYarnIssueEntry issue) {
//         resolveSizingYarnInwardForIssue(issue).ifPresent(inward -> sizingYarnInwardService.deleteSizingYarnInward(inward.getId()));
//     }

//     private Optional<SizingYarnInward> resolveSizingYarnInwardForIssue(SizingYarnIssueEntry issue) {
//         if (issue == null || issue.getId() == null) {
//             return Optional.empty();
//         }
//         String suffix = "-" + issue.getId();
//         return sizingYarnInwardRepository.findFirstByInwardNoEndingWith(suffix);
//     }

//     // ===== NEW METHOD: UPDATE SIZING PLAN ACTUAL YARN =====
    
//     /**
//      * Updates the actualYarn field in SizingQualityDetails based on the requirements
//      * from SizingYarnIssue. This syncs the actualYarn changes back to the sizing plan.
//      */
//     private void updateSizingPlanActualYarn(Long sizingPlanId, List<SizingYarnRequirementDTO> requirements) {
//         if (sizingPlanId == null || requirements == null || requirements.isEmpty()) {
//             return;
//         }
        
//         // Get all quality details for this sizing plan
//         List<SizingQualityDetails> qualityDetailsList = 
//             sizingQualityDetailsRepository.findBySizingPlanId(sizingPlanId);
        
//         // Create a map for quick lookup: yarnId -> actualYarnId
//         // We match based on yarnId (original yarn) to find which quality detail to update
//         for (SizingYarnRequirementDTO req : requirements) {
//             if (req.getYarnId() != null && req.getActualYarnId() != null) {
//                 // Find matching quality detail by yarnId
//                 qualityDetailsList.stream()
//                     .filter(qd -> qd.getYarnId().equals(req.getYarnId()))
//                     .forEach(qd -> {
//                         // Update the actualYarn field
//                         qd.setActualYarn(req.getActualYarnId());
//                     });
//             }
//         }
        
//         // Save all updated quality details
//         sizingQualityDetailsRepository.saveAll(qualityDetailsList);
//     }

//     private void syncSizingYarnInwardRecord(SizingYarnIssueEntry savedIssue) {
//         if (SyncContext.isInProgress() || savedIssue == null || savedIssue.getSizingYarnIssues() == null || savedIssue.getSizingYarnIssues().isEmpty()) {
//             return;
//         }

//         try {
//             SyncContext.setInProgress(true);
//             doSyncSizingYarnInwardRecord(savedIssue);
//         } finally {
//             SyncContext.clear();
//         }
//     }

//     private void doSyncSizingYarnInwardRecord(SizingYarnIssueEntry savedIssue) {

//         if (savedIssue.getVendorId() != null) {
//             boolean isInternal = vendorRepository.findById(savedIssue.getVendorId())
//                     .map(v -> v.getIsInternalVendor() != null && v.getIsInternalVendor() == 1)
//                     .orElse(false);
//             if (!isInternal) {
//                 return;
//             }
//         } else {
//             return;
//         }

//         SizingYarnInwardDTO inwardDTO = new SizingYarnInwardDTO();
//         LocalDate inwardDate = savedIssue.getSizingYarnIssueDate() != null ? savedIssue.getSizingYarnIssueDate() : LocalDate.now();
//         String financialYear = getShortFinancialYear(inwardDate);
//         inwardDTO.setInwardNo(String.format("SZYI-%s-%d", financialYear, savedIssue.getId()));
//         inwardDTO.setInwardDate(savedIssue.getSizingYarnIssueDate());
//         inwardDTO.setCompanyId(savedIssue.getCompanyId());

//         Long sizingPlanId = savedIssue.getSizingPlanId();
//         if (sizingPlanId != null) {
//             sizingJobWorkOrderRepository.findBySizingPlanId(sizingPlanId)
//                     .ifPresent(jobOrder -> {
//                         inwardDTO.setSizingJobWorkOrderId(jobOrder.getId());
//                         inwardDTO.setOrderTypeId(jobOrder.getOrderTypeId());
//                         inwardDTO.setBuyerId(jobOrder.getBuyerId());
//                         inwardDTO.setFactoryCompanyId(jobOrder.getFactoryCompanyId());
//                         inwardDTO.setTradingCompanyId(jobOrder.getTradingCompanyId());
//                         if (Long.valueOf(1L).equals(jobOrder.getOrderTypeId()) && jobOrder.getTradingCompanyId() != null) {
//                             inwardDTO.setCompanyId(jobOrder.getTradingCompanyId());
//                         }
//                     });

//             sizingPlanRepository.findById(sizingPlanId).ifPresent(plan -> {
//                 if (inwardDTO.getOrderTypeId() == null) {
//                     inwardDTO.setOrderTypeId(plan.getOrderTypeId());
//                 }
//                 if (inwardDTO.getFactoryCompanyId() == null) {
//                     inwardDTO.setFactoryCompanyId(plan.getFactoryCompanyId());
//                 }
//                 if (inwardDTO.getTradingCompanyId() == null) {
//                     inwardDTO.setTradingCompanyId(plan.getTradingCompanyId());
//                 }
//                 if (Long.valueOf(1L).equals(inwardDTO.getOrderTypeId())) {
//                     Long targetCompanyId = plan.getTradingCompanyId() != null ? plan.getTradingCompanyId() : plan.getCompanyId();
//                     if (targetCompanyId != null) {
//                         inwardDTO.setCompanyId(targetCompanyId);
//                     }
//                 } else if (inwardDTO.getCompanyId() == null) {
//                     inwardDTO.setCompanyId(plan.getCompanyId());
//                 }
//             });
//         }

//         if (inwardDTO.getFactoryCompanyId() == null) {
//             inwardDTO.setFactoryCompanyId(savedIssue.getCompanyId());
//         }

//         if (Long.valueOf(1L).equals(inwardDTO.getOrderTypeId()) && inwardDTO.getTradingCompanyId() == null) {
//             inwardDTO.setTradingCompanyId(inwardDTO.getCompanyId());
//         }

//         Long buyerCompanyId = savedIssue.getCompanyId();
//         if (buyerCompanyId != null) {
//             inwardDTO.setTradingCompanyId(buyerCompanyId);
//         }

//         Optional<SizingYarnInward> existingInward = resolveSizingYarnInwardForIssue(savedIssue);
//         List<SizingYarnInwardDetailDTO> yarnDetailDTOs = savedIssue.getSizingYarnIssues().stream()
//                 .map(issue -> {
//                     SizingYarnInwardDetailDTO detailDTO = new SizingYarnInwardDetailDTO();
//                     detailDTO.setYarnName(issue.getYarnName());
//                     // Use actualItemId if present for sync to inward
//                     detailDTO.setYarnId(issue.getActualItemId() != null ? issue.getActualItemId() : issue.getYarnId());
//                     detailDTO.setNoOfBags(issue.getNoOfBags());
//                     detailDTO.setKgsPerBag(issue.getKgsPerBag());
//                     detailDTO.setConesPerBag(issue.getConesPerBag());
//                     detailDTO.setConesWeight(issue.getConesWeight());
//                     detailDTO.setWarehouseId(issue.getWarehouseId());
//                     detailDTO.setMillId(issue.getMillId());
//                     detailDTO.setIssueQty(issue.getIssueQty());
//                     detailDTO.setLotNo(issue.getLotNo());
//                     return detailDTO;
//                 })
//                 .collect(Collectors.toList());
//         preserveSizingYarnInwardDetailIds(existingInward.orElse(null), yarnDetailDTOs);
//         inwardDTO.setYarnDetails(yarnDetailDTOs);

//         if (existingInward.isPresent()) {
//             sizingYarnInwardService.updateSizingYarnInward(existingInward.get().getId(), inwardDTO);
//         } else {
//             sizingYarnInwardService.createSizingYarnInward(inwardDTO);
//         }

//         // Simultaneously create/update YarnInventory entry for internal vendor
//         String spNo = null;
//         if (savedIssue.getSizingPlanId() != null) {
//             spNo = sizingPlanRepository.findById(savedIssue.getSizingPlanId())
//                     .map(SizingPlan::getSizingPlanNo)
//                     .orElse(null);
//         }
//         if (spNo != null) {
//             syncYarnInventoryRecord(savedIssue, spNo, inwardDTO.getInwardNo());
//         }
//     }

//     private void preserveSizingYarnInwardDetailIds(SizingYarnInward existingInward, List<SizingYarnInwardDetailDTO> yarnDetailDTOs) {
//         if (existingInward == null || existingInward.getYarnDetails() == null || yarnDetailDTOs == null || yarnDetailDTOs.isEmpty()) {
//             return;
//         }

//         Map<String, List<SizingYarnInwardDetail>> existingDetailsByKey = existingInward.getYarnDetails().stream()
//                 .collect(Collectors.groupingBy(this::buildSizingYarnInwardDetailKey));

//         for (SizingYarnInwardDetailDTO detailDTO : yarnDetailDTOs) {
//             List<SizingYarnInwardDetail> matches = existingDetailsByKey.get(buildSizingYarnInwardDetailKey(detailDTO));
//             if (matches != null && !matches.isEmpty()) {
//                 detailDTO.setId(matches.remove(0).getId());
//             }
//         }
//     }

//     private String buildSizingYarnInwardDetailKey(SizingYarnInwardDetail detail) {
//         if (detail == null) {
//             return "";
//         }
//         return String.join("|",
//                 String.valueOf(normalizeId(detail.getYarnId())),
//                 String.valueOf(normalizeId(detail.getMillId())),
//                 String.valueOf(normalizeId(detail.getWarehouseId())),
//                 normalizeText(detail.getLotNo()));
//     }

//     private String buildSizingYarnInwardDetailKey(SizingYarnInwardDetailDTO detailDTO) {
//         if (detailDTO == null) {
//             return "";
//         }
//         return String.join("|",
//                 String.valueOf(normalizeId(detailDTO.getYarnId())),
//                 String.valueOf(normalizeId(detailDTO.getMillId())),
//                 String.valueOf(normalizeId(detailDTO.getWarehouseId())),
//                 normalizeText(detailDTO.getLotNo()));
//     }

//     private String normalizeText(String value) {
//         return value == null ? "" : value.trim().toLowerCase();
//     }

//     private void syncYarnInventoryRecord(SizingYarnIssueEntry savedIssue, String sizingPlanNo, String inwardNo) {
//         Long inwardTypeId = codesDetailRepository.findByValue("Sizing-Yarn-Inward")
//                 .map(CodesDetail::getId)
//                 .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
//                         .map(CodesDetail::getId)
//                         .orElse(3L));

//         YarnInventory yarnInventory = yarnInventoryRepository.findByInwardNo(inwardNo)
//                 .orElse(new YarnInventory());
//         Optional<SizingYarnInward> sizingYarnInwardOpt = sizingYarnInwardRepository.findByInwardNo(inwardNo);

//         yarnInventory.setInwardId(sizingYarnInwardOpt.map(SizingYarnInward::getId).orElse(savedIssue.getId()));
//         yarnInventory.setInwardTypeId(inwardTypeId);
//         yarnInventory.setInwardNo(inwardNo);
//         yarnInventory.setCompanyId(savedIssue.getCompanyId());
//         yarnInventory.setInwardDate(savedIssue.getSizingYarnIssueDate() != null ?
//                 savedIssue.getSizingYarnIssueDate().atStartOfDay() : LocalDateTime.now());
//         yarnInventory.setFinancialYear(getFinancialYearId(savedIssue.getSizingYarnIssueDate() != null ?
//                 savedIssue.getSizingYarnIssueDate() : LocalDate.now()));
//         yarnInventory.setDcNo(savedIssue.getYarnIssueChallanNo());
//         yarnInventory.setDcDate(savedIssue.getSizingYarnIssueDate());
//         yarnInventory.setOrderId(savedIssue.getSizingPlanId());
//         yarnInventory.setOrderNo(sizingPlanNo);
//         yarnInventory.setVendorId(savedIssue.getVendorId());

//         Map<Long, YarnInwardStockDetails> existingDetailsMap = new HashMap<>();
//         if (yarnInventory.getDetails() != null) {
//             yarnInventory.getDetails().forEach(detail -> {
//                 if (detail.getOrderItemId() != null) {
//                     existingDetailsMap.put(detail.getOrderItemId(), detail);
//                 }
//             });
//         }

//         List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();
//         sizingYarnInwardOpt.ifPresent(inward -> {
//             if (inward.getYarnDetails() != null) {
//                 inward.getYarnDetails().forEach(inwardDetail -> {
//                     YarnInwardStockDetails detail = existingDetailsMap.remove(inwardDetail.getId());
//                     if (detail == null) {
//                         detail = new YarnInwardStockDetails();
//                         detail.setYarnInventory(yarnInventory);
//                     }

//                     BigDecimal inwardBags = inwardDetail.getNoOfBags() != null
//                             ? BigDecimal.valueOf(inwardDetail.getNoOfBags())
//                             : BigDecimal.ZERO;

//                     detail.setOrderItemId(inwardDetail.getId());
//                     detail.setInwardTypeId(inwardTypeId);
//                     detail.setItemId(normalizeId(inwardDetail.getYarnId()));
//                     detail.setMillId(normalizeId(inwardDetail.getMillId()));
//                     detail.setLotNo(inwardDetail.getLotNo());
//                     detail.setInwardQty(inwardDetail.getIssueQty());
//                     detail.setAcceptedQty(inwardDetail.getIssueQty());
//                     detail.setInwardBags(inwardBags);
//                     detail.setWarehouseId(normalizeId(inwardDetail.getWarehouseId()));
//                     detail.setKgsPerBag(inwardDetail.getKgsPerBag());
//                     detail.setConesPerBag(inwardDetail.getConesPerBag());
//                     detail.setConesWeight(inwardDetail.getConesWeight());
//                     recalculateAvailableFromOutward(detail, inwardBags);
//                     updatedDetails.add(detail);
//                 });
//             }
//         });

//         if (yarnInventory.getDetails() != null) {
//             yarnInventory.getDetails().clear();
//             yarnInventory.getDetails().addAll(updatedDetails);
//         } else {
//             yarnInventory.setDetails(updatedDetails);
//         }

//         yarnInventoryRepository.save(yarnInventory);
//     }

//     private void recalculateAvailableFromOutward(YarnInwardStockDetails detail, BigDecimal inwardBags) {
//         BigDecimal inwardQty = detail.getInwardQty() != null ? detail.getInwardQty() : BigDecimal.ZERO;
//         BigDecimal totalIssuedQty = BigDecimal.ZERO;
//         BigDecimal totalIssuedBags = BigDecimal.ZERO;

//         if (detail.getId() != null) {
//             totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
//             totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());
//         }

//         BigDecimal availableQty = inwardQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
//         BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);

//         detail.setAvailableQty(availableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableQty);
//         detail.setAvailableBags(availableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBags);
//     }

//     // ===== ENTITY POPULATION METHOD =====
    
//     private void populateEntityFromDTO(SizingYarnIssueEntry entity, SizingYarnIssueEntryDTO dto, boolean isUpdate) {
//         entity.setVendorId(dto.getVendorId());
//         entity.setCompanyId(dto.getCompanyId());
//         entity.setOutwardTypeId(dto.getOutwardTypeId());
//         entity.setSizingPlanId(dto.getSizingPlanId());
//         entity.setTransportationDetails(dto.getTransportationDetails());
//         entity.setTermsConditionsId(dto.getTermsConditionsId());
//         entity.setFabricDetails(dto.getFabricDetails());
//         entity.setSizingYarnIssueDate(dto.getSizingYarnIssueDate());
//         entity.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
//         entity.setActiveFlag(dto.isActiveFlag());
//         entity.setRemark(dto.getRemark());
//         entity.setVehicleNo(dto.getVehicleNo());
//         entity.setGrossWt(dto.getGrossWt());

//         // Handle requirements
//         if (dto.getRequirements() != null) {
//             if (isUpdate) {
//                 Set<Long> dtoReqIds = dto.getRequirements().stream()
//                     .map(SizingYarnRequirementDTO::getId)
//                     .filter(id -> id != null)
//                     .collect(Collectors.toSet());
//                 entity.getRequirements().removeIf(req -> req.getId() != null && !dtoReqIds.contains(req.getId()));
//             }

//             for (SizingYarnRequirementDTO reqDTO : dto.getRequirements()) {
//                 SizingYarnRequirement req;
//                 if (isUpdate && reqDTO.getId() != null) {
//                     req = entity.getRequirements().stream()
//                         .filter(r -> r.getId().equals(reqDTO.getId()))
//                         .findFirst()
//                         .orElseGet(() -> {
//                             SizingYarnRequirement newReq = sizingYarnRequirementRepository.findById(reqDTO.getId())
//                                 .orElseThrow(() -> new EntityNotFoundException("SizingYarnRequirement not found: " + reqDTO.getId()));
//                             newReq.setSizingYarnIssueEntry(entity);
//                             entity.getRequirements().add(newReq);
//                             return newReq;
//                         });
//                     updateRequirementFromDTO(req, reqDTO);
//                 } else {
//                     req = new SizingYarnRequirement();
//                     populateRequirementFromDTO(req, reqDTO);
//                     req.setSizingYarnIssueEntry(entity);
//                     entity.getRequirements().add(req);
//                 }
//             }
//         } else if (isUpdate) {
//             entity.getRequirements().clear();
//         }
        
//         // Handle sizing yarn issues
//         if (dto.getSizingYarnIssue() != null) {
//             if (isUpdate) {
//                 Set<Long> dtoIssueIds = dto.getSizingYarnIssue().stream()
//                     .map(SizingYarnIssueDTO::getId)
//                     .filter(id -> id != null)
//                     .collect(Collectors.toSet());
//                 entity.getSizingYarnIssues().removeIf(issue -> issue.getId() != null && !dtoIssueIds.contains(issue.getId()));
//             }

//             for (SizingYarnIssueDTO issueDTO : dto.getSizingYarnIssue()) {
//                 SizingYarnIssue issue;
//                 if (isUpdate && issueDTO.getId() != null) {
//                     issue = entity.getSizingYarnIssues().stream()
//                         .filter(i -> i.getId().equals(issueDTO.getId()))
//                         .findFirst()
//                         .orElseGet(() -> {
//                             SizingYarnIssue newIssue = sizingYarnIssueRepository.findById(issueDTO.getId())
//                                 .orElseThrow(() -> new EntityNotFoundException("SizingYarnIssue not found: " + issueDTO.getId()));
//                             newIssue.setSizingYarnIssueEntry(entity);
//                             entity.getSizingYarnIssues().add(newIssue);
//                             return newIssue;
//                         });
//                     updateYarnIssueFromDTO(issue, issueDTO, dto.getIssueNo());
//                 } else {
//                     issue = new SizingYarnIssue();
//                     populateYarnIssueFromDTO(issue, issueDTO,dto.getIssueNo());
//                     issue.setSizingYarnIssueEntry(entity);
//                     entity.getSizingYarnIssues().add(issue);
//                 }
//             }
//         } else if (isUpdate) {
//             entity.getSizingYarnIssues().clear();
//         }
//     }
    
//     private void populateRequirementFromDTO(SizingYarnRequirement entity, SizingYarnRequirementDTO dto) {
//         entity.setId(null);
//         entity.setFabricDetails(dto.getFabricDetails());
//         entity.setYarnName(dto.getYarnName());
//         entity.setActualYarn(dto.getActualYarn());
//         entity.setActualYarnId(dto.getActualYarnId());
//         entity.setYarnId(dto.getYarnId());
//         entity.setWarpMeters(dto.getWarpMeters());
//         entity.setWarpGramsPerMeter(dto.getWarpGramsPerMeter());
//         entity.setWarpRequirement(dto.getWarpRequirement());
//         entity.setTotalIssuedQty(dto.getTotalIssuedQty());
//         entity.setBalanceToIssue(dto.getBalanceToIssue());
//         entity.setActiveFlag(dto.isActiveFlag());
//     }
    
//     private void updateRequirementFromDTO(SizingYarnRequirement entity, SizingYarnRequirementDTO dto) {
//         entity.setFabricDetails(dto.getFabricDetails());
//         entity.setYarnName(dto.getYarnName());
//         entity.setActualYarn(dto.getActualYarn());
//         entity.setActualYarnId(dto.getActualYarnId());
//         entity.setYarnId(dto.getYarnId());
//         entity.setWarpMeters(dto.getWarpMeters());
//         entity.setWarpGramsPerMeter(dto.getWarpGramsPerMeter());
//         entity.setWarpRequirement(dto.getWarpRequirement());
//         entity.setTotalIssuedQty(dto.getTotalIssuedQty());
//         entity.setBalanceToIssue(dto.getBalanceToIssue());
//         entity.setActiveFlag(dto.isActiveFlag());
//     }
    
//     private void populateYarnIssueFromDTO(SizingYarnIssue entity, SizingYarnIssueDTO dto,String entryIssueNo) {
//         entity.setId(null);
//         entity.setYarnName(dto.getYarnName());
//         entity.setYarnId(dto.getYarnId());
//         entity.setMillId(dto.getMillId());
//         entity.setWarehouseId(dto.getWarehouseId());
//         entity.setAlternateYarn(dto.getAlternateYarn());
//         entity.setAvailableReqQty(dto.getAvailableReqQty());
//         entity.setActualQty(dto.getAvailableQty());
//         entity.setNoOfBags(dto.getNoOfBags());
//         entity.setKgsPerBag(dto.getKgsPerBag());
//         entity.setConesPerBag(dto.getConesPerBag());
//         entity.setConesWeight(dto.getConesWeight());
//         entity.setIssueQty(dto.getIssueQty());
//         entity.setLotNo(dto.getLotNo());
// //        entity.setLotId(dto.getLotId());
//         entity.setIssueNo(dto.getIssueNo() != null ? dto.getIssueNo() : entryIssueNo);
//         entity.setLotAvailableQty(dto.getLotAvailableQty());
//         entity.setLotAvailableBags(dto.getLotAvailableBags());
//         entity.setActiveFlag(dto.isActiveFlag());
//         entity.setInwardId(dto.getInwardId() != null && dto.getInwardId() != 0 ? dto.getInwardId() : null);
//         entity.setOrderItemId(dto.getOrderItemId() != null && dto.getOrderItemId() != 0 ? dto.getOrderItemId() : null);
//         entity.setOutwardTypeId(dto.getOutwardTypeId());
//         entity.setActualItemId(dto.getActualItemId());
//         entity.setUomId(dto.getUomId());
//         entity.setIssuedBags(dto.getIssuedBags());
//         entity.setCompanyId(dto.getCompanyId());
//     }
    
//     private void updateYarnIssueFromDTO(SizingYarnIssue entity, SizingYarnIssueDTO dto,String entryIssueNo) {
//         entity.setYarnName(dto.getYarnName());
//         entity.setYarnId(dto.getYarnId());
//         entity.setMillId(dto.getMillId());
//         entity.setWarehouseId(dto.getWarehouseId());
//         entity.setAlternateYarn(dto.getAlternateYarn());
//         entity.setAvailableReqQty(dto.getAvailableReqQty());
//         entity.setActualQty(dto.getAvailableQty());
//         entity.setNoOfBags(dto.getNoOfBags());
//         entity.setKgsPerBag(dto.getKgsPerBag());
//         entity.setConesPerBag(dto.getConesPerBag());
//         entity.setConesWeight(dto.getConesWeight());
//         entity.setIssueQty(dto.getIssueQty());
//         entity.setLotNo(dto.getLotNo());
// //        entity.setLotId(dto.getLotId());
//         entity.setIssueNo(dto.getIssueNo() != null ? dto.getIssueNo() : entryIssueNo);
//         entity.setLotAvailableQty(dto.getLotAvailableQty());
//         entity.setLotAvailableBags(dto.getLotAvailableBags());
//         entity.setActiveFlag(dto.isActiveFlag());
//         entity.setInwardId(dto.getInwardId() != null && dto.getInwardId() != 0 ? dto.getInwardId() : null);
//         entity.setOrderItemId(dto.getOrderItemId() != null && dto.getOrderItemId() != 0 ? dto.getOrderItemId() : null);
//         entity.setOutwardTypeId(dto.getOutwardTypeId());
//         entity.setActualItemId(dto.getActualItemId());
//         entity.setUomId(dto.getUomId());
//         entity.setIssuedBags(dto.getIssuedBags());
//         entity.setCompanyId(dto.getCompanyId());
//     }

//     // ===== YARN OUTWARD MANAGEMENT =====
    
//     private void processLotOutward(SizingYarnIssueEntry issue, String referenceType, Long referenceId, Long outwardTypeId) {
//         if (issue.getSizingYarnIssues() == null || outwardTypeId == null || issue.getSizingYarnIssues().isEmpty()) {
//             return;
//         }

//         Long orderId = issue.getSizingPlanId();
//         String orderNo = null;
//         if (orderId != null) {
//             orderNo = sizingPlanRepository.findById(orderId).map(SizingPlan::getSizingPlanNo).orElse(null);
//         }
//         String financialYearId = issue.getSizingYarnIssueDate() != null ? getFinancialYearId(issue.getSizingYarnIssueDate()) : null;

//         // Determine outwardNo
//         String firstIssueNo = issue.getSizingYarnIssues().get(0).getIssueNo();
//         String outwardNo = firstIssueNo != null ? firstIssueNo : generateOutwardNo();
//         String remark = "Sizing yarn issue - " + issue.getId();

//         // 1. Create or Find ONE YarnOutward for the entire entry
//         YarnOutward yarnOutward = yarnOutwardRepository.findByRemarks(remark).stream().findFirst()
//             .orElseGet(() -> yarnOutwardRepository.findByOutwardNo(outwardNo).orElse(new YarnOutward()));

//         yarnOutward.setOutwardTypeId(outwardTypeId);
//         yarnOutward.setOutwardNo(outwardNo);
//         yarnOutward.setCompanyId(issue.getCompanyId() != null ? issue.getCompanyId() : 1L);
//         yarnOutward.setOutwardDate(issue.getSizingYarnIssueDate());
//         yarnOutward.setOrderId(orderId);
//         yarnOutward.setOrderNo(orderNo);
//         yarnOutward.setFinancialYearId(financialYearId);
//         yarnOutward.setVendorId(issue.getVendorId());
//         yarnOutward.setRemarks(remark);
//         yarnOutward.setActiveFlag(true);

//         YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);

//         // Maps to track availability and consolidate details globally across the transaction
//         Map<Long, BigDecimal> localAvailableQtyMap = new HashMap<>();
//         Map<Long, BigDecimal> localAvailableBagsMap = new HashMap<>();
//         Map<Long, YarnOutwardStockDetails> consolidatedOutwardMap = new HashMap<>();

//         // Initialize maps from existing details if updating
//         if (savedYarnOutward.getYarnOutwardStockDetails() != null) {
//             savedYarnOutward.getYarnOutwardStockDetails().forEach(d -> {
//                 // Reset all details to ZERO before processing, regardless of ID
//                 d.setIssuedQty(BigDecimal.ZERO);
//                 d.setIssuedBags(BigDecimal.ZERO);
                
//                 if (d.getYarnInwardStockDetailsId() != null) {
//                     consolidatedOutwardMap.put(d.getYarnInwardStockDetailsId(), d);
//                 }
//             });
//         }

//         for (SizingYarnIssue sizingYarnIssue : issue.getSizingYarnIssues()) {
//             if (sizingYarnIssue.getIssueQty() == null || sizingYarnIssue.getIssueQty().compareTo(BigDecimal.ZERO) <= 0) {
//                 continue;
//             }

//             try {
//                 BigDecimal remainingToIssue = sizingYarnIssue.getIssueQty();
//                 BigDecimal totalIssueBags = sizingYarnIssue.getNoOfBags() != null ? 
//                     BigDecimal.valueOf(sizingYarnIssue.getNoOfBags()) : BigDecimal.ZERO;
                
//                 // Use actualItemId if present, otherwise fallback to yarnId
//                 final Long searchItemId = sizingYarnIssue.getActualItemId() != null ? 
//                     sizingYarnIssue.getActualItemId() : sizingYarnIssue.getYarnId();

//                 // 2. Find eligible stock records
//                 List<YarnInwardStockDetails> stocks = new java.util.ArrayList<>();
                
//                 // Prioritize specific stock detail ID if provided, but only if it matches the actual yarn being issued
//                 if (sizingYarnIssue.getInwardId() != null && sizingYarnIssue.getInwardId() > 0) {
//                     yarnInwardStockDetailsRepository.findById(sizingYarnIssue.getInwardId())
//                         .filter(s -> searchItemId == null || searchItemId.equals(s.getItemId()))
//                         .ifPresent(stocks::add);
//                 }
                
//                 // Add fallback stocks for the same yarn
//                 if (searchItemId != null) {
//                     List<YarnInwardStockDetails> additionalStocks = new java.util.ArrayList<>();
//                     if (sizingYarnIssue.getOrderItemId() != null && sizingYarnIssue.getOrderItemId() > 0) {
//                         additionalStocks = yarnInwardStockDetailsRepository.findAllByOrderItemId(sizingYarnIssue.getOrderItemId());
//                     } else {
//                         additionalStocks = yarnInwardStockDetailsRepository.findByItemId(searchItemId);
//                     }
                    
//                     for (YarnInwardStockDetails s : additionalStocks) {
//                         // Strict check on itemId
//                         if (searchItemId.equals(s.getItemId()) && stocks.stream().noneMatch(e -> e.getId().equals(s.getId()))) {
//                             stocks.add(s);
//                         }
//                     }
//                 }
//                 stocks.sort(java.util.Comparator.comparing(YarnInwardStockDetails::getId));

//                 boolean firstInwardAssignedForThisLine = false;

//                 for (YarnInwardStockDetails yarnStock : stocks) {
//                     if (remainingToIssue.compareTo(BigDecimal.ZERO) <= 0) break;
                    
//                     BigDecimal lotAvailable = localAvailableQtyMap.getOrDefault(yarnStock.getId(), 
//                         yarnStock.getAvailableQty() != null ? yarnStock.getAvailableQty() : BigDecimal.ZERO);
//                     BigDecimal lotBagsAvailable = localAvailableBagsMap.getOrDefault(yarnStock.getId(),
//                         yarnStock.getAvailableBags() != null ? yarnStock.getAvailableBags() : BigDecimal.ZERO);
                    
//                     if (lotAvailable.compareTo(BigDecimal.ZERO) <= 0) continue;
                    
//                     BigDecimal toTake = remainingToIssue.min(lotAvailable);
                    
//                     // Calculate bags proportionally
//                     BigDecimal bagsToTake;
//                     if (toTake.compareTo(remainingToIssue) == 0) {
//                         bagsToTake = totalIssueBags.min(lotBagsAvailable);
//                     } else {
//                         bagsToTake = totalIssueBags.multiply(toTake)
//                             .divide(sizingYarnIssue.getIssueQty(), 4, RoundingMode.HALF_UP).min(lotBagsAvailable);
//                     }
                    
//                     // 3. Consolidate into ONE YarnOutwardStockDetails per inward source
//                     YarnOutwardStockDetails outwardDetail = consolidatedOutwardMap.get(yarnStock.getId());
//                     if (outwardDetail == null) {
//                         outwardDetail = new YarnOutwardStockDetails();
//                         outwardDetail.setYarnOutward(savedYarnOutward);
//                         outwardDetail.setOutwardTypeId(outwardTypeId);
//                         outwardDetail.setWarehouseId(yarnStock.getWarehouseId());
//                         outwardDetail.setActualItemId(yarnStock.getItemId());
//                         outwardDetail.setInwardId(yarnStock.getInwardId());
//                         outwardDetail.setOrderItemId(yarnStock.getOrderItemId());
//                         outwardDetail.setYarnInwardStockDetailsId(yarnStock.getId());
//                         outwardDetail.setMillId(yarnStock.getMillId());
//                         outwardDetail.setUomId(yarnStock.getUomId());
//                         outwardDetail.setLotNo(yarnStock.getLotNo());
//                         outwardDetail.setIssuedQty(BigDecimal.ZERO);
//                         outwardDetail.setIssuedBags(BigDecimal.ZERO);
//                         outwardDetail.setKgsPerBag(sizingYarnIssue.getKgsPerBag());
//                         outwardDetail.setConesPerBag(sizingYarnIssue.getConesPerBag());
//                         outwardDetail.setConesWeight(sizingYarnIssue.getConesWeight());
                        
//                         consolidatedOutwardMap.put(yarnStock.getId(), outwardDetail);
//                         if (savedYarnOutward.getYarnOutwardStockDetails() == null) {
//                             savedYarnOutward.setYarnOutwardStockDetails(new java.util.ArrayList<>());
//                         }
//                         savedYarnOutward.getYarnOutwardStockDetails().add(outwardDetail);
//                     }
                    
//                     outwardDetail.setIssuedQty(outwardDetail.getIssuedQty().add(toTake));
//                     outwardDetail.setIssuedBags(outwardDetail.getIssuedBags().add(bagsToTake));
                    
//                     // 4. Map IDs back to sizingYarnIssue for traceability
//                     if (!firstInwardAssignedForThisLine) {
//                         sizingYarnIssue.setOrderItemId(yarnStock.getOrderItemId());
//                         sizingYarnIssue.setInwardId(yarnStock.getId());
//                         firstInwardAssignedForThisLine = true;
//                     }
                    
//                     // 5. Update state for next iterations
//                     localAvailableQtyMap.put(yarnStock.getId(), lotAvailable.subtract(toTake));
//                     localAvailableBagsMap.put(yarnStock.getId(), lotBagsAvailable.subtract(bagsToTake));
                    
//                     remainingToIssue = remainingToIssue.subtract(toTake);
//                     totalIssueBags = totalIssueBags.subtract(bagsToTake);
//                 }
                
//                 sizingYarnIssueRepository.save(sizingYarnIssue);
                
//             } catch (Exception e) {
//                 System.err.println("Error processing sizing yarn lot outward: " + e.getMessage());
//                 throw new RuntimeException("Failed to process yarn lot outward: " + e.getMessage(), e);
//             }
//         }

//         // Clean up empty/unused details from previous state if any
//         if (savedYarnOutward.getYarnOutwardStockDetails() != null) {
//             savedYarnOutward.getYarnOutwardStockDetails().removeIf(d -> d.getIssuedQty().compareTo(BigDecimal.ZERO) <= 0);
//         }

//         yarnOutwardRepository.save(savedYarnOutward);

//         if (savedYarnOutward.getYarnOutwardStockDetails() != null && !savedYarnOutward.getYarnOutwardStockDetails().isEmpty()) {
//             List<YarnOutwardStockDetails> persistedDetails = yarnOutwardStockDetailsRepository.saveAll(savedYarnOutward.getYarnOutwardStockDetails());
//             yarnOutwardStockDetailsRepository.flush();
//             updateAvailableQtyInInwardStockDetails(persistedDetails);
//         }
//     }

//     private void reverseLotReductions(SizingYarnIssueEntry issue, boolean deleteOutward) {
//         String remark = "Sizing yarn issue - " + issue.getId();
//         List<YarnOutward> outwards = yarnOutwardRepository.findByRemarks(remark);
        
//         for (YarnOutward outward : outwards) {
//             if (outward.getYarnOutwardStockDetails() != null) {
//                 // Restore stock
//                 for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
//                     Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();
                    
//                     if (detail.getYarnInwardStockDetailsId() != null && detail.getYarnInwardStockDetailsId() != 0) {
//                         inwardDetailOpt = yarnInwardStockDetailsRepository.findById(detail.getYarnInwardStockDetailsId());
//                     }
                    
//                     if (!inwardDetailOpt.isPresent() && detail.getOrderItemId() != null && detail.getOrderItemId() != 0) {
//                         inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(detail.getOrderItemId());
//                     }
                    
//                     if (inwardDetailOpt.isPresent()) {
//                         YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
                        
//                         BigDecimal qtyToRestore = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
//                         BigDecimal bagsToRestore = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
                        
//                         // Initialize available values if they are null
//                         if (inwardDetail.getAvailableQty() == null) {
//                             inwardDetail.setAvailableQty(inwardDetail.getAcceptedQty() != null ? inwardDetail.getAcceptedQty() : BigDecimal.ZERO);
//                         }
//                         if (inwardDetail.getAvailableBags() == null) {
//                             inwardDetail.setAvailableBags(inwardDetail.getInwardBags() != null ? inwardDetail.getInwardBags() : BigDecimal.ZERO);
//                         }
                        
//                         inwardDetail.setAvailableQty(inwardDetail.getAvailableQty().add(qtyToRestore));
//                         inwardDetail.setAvailableBags(inwardDetail.getAvailableBags().add(bagsToRestore));
                        
//                         yarnInwardStockDetailsRepository.save(inwardDetail);
//                     }
//                 }
//             }
//             if (deleteOutward) {
//                 List<YarnOutwardStockDetails> outwardDetails = new ArrayList<>(
//                         outward.getYarnOutwardStockDetails() != null ? outward.getYarnOutwardStockDetails() : Collections.emptyList());
//                 if (!outwardDetails.isEmpty()) {
//                     if (outward.getYarnOutwardStockDetails() != null) {
//                         outward.getYarnOutwardStockDetails().clear();
//                     }
//                     yarnOutwardRepository.save(outward);
//                     yarnOutwardRepository.flush();
//                     yarnOutwardStockDetailsRepository.deleteAll(outwardDetails);
//                     yarnOutwardStockDetailsRepository.flush();
//                 }
//                 yarnOutwardRepository.delete(outward);
//             }
//         }
//         yarnOutwardRepository.flush();
//         yarnInwardStockDetailsRepository.flush();
//     }

//     private String generateOutwardNo() {
//         return "OUT-" + System.currentTimeMillis();
//     }

//     // ===== VALIDATION =====
    
//     private void validateSizingYarnIssue(SizingYarnIssueEntryDTO dto) {
//         if (dto.getSizingYarnIssue() != null) {
//             for (SizingYarnIssueDTO sizingYarnIssue : dto.getSizingYarnIssue()) {
//                 // Use actualItemId if present, otherwise fallback to yarnId
//                 Long searchItemId = sizingYarnIssue.getActualItemId() != null ? 
//                     sizingYarnIssue.getActualItemId() : sizingYarnIssue.getYarnId();
                
//                 List<YarnInwardStockDetails> inwardDetails = 
//                     yarnInwardStockDetailsRepository.findByItemId(searchItemId);
                
//                 if (inwardDetails.isEmpty()) {
//                     throw new EntityNotFoundException(
//                         "No yarn inward stock details found for yarn ID: " + searchItemId);
//                 }
                
//                 BigDecimal totalAvailableQty = BigDecimal.ZERO;
//                 for (YarnInwardStockDetails detail : inwardDetails) {
//                     if (detail.getAvailableQty() != null) {
//                         totalAvailableQty = totalAvailableQty.add(detail.getAvailableQty());
//                     }
//                 }
                
//                 BigDecimal requestedQty = sizingYarnIssue.getIssueQty() != null ? sizingYarnIssue.getIssueQty() : BigDecimal.ZERO;
//                 if (totalAvailableQty.compareTo(requestedQty) < 0) {
//                     throw new IllegalArgumentException(
//                         String.format("Insufficient available quantity for yarn ID %s (Available: %s, Requested: %s)",
//                             sizingYarnIssue.getYarnId(), totalAvailableQty, requestedQty));
//                 }
//             }
//         }
//     }

//     private PaginatedResponse<SizingYarnIssueEntryDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit,
//             Function<List<Long>, List<SizingYarnIssueEntry>> fetcher) {
//         long off = offset != null ? Math.max(0, offset) : 0;
//         long lim = (limit != null && limit > 0) ? limit : 10;
//         long total = allIds.size();
//         int start = (int) Math.min(off, total);
//         if (start >= total) {
//             return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, Collections.emptyList());
//         }
//         int end = (int) Math.min(off + lim, total);
//         List<Long> pageIds = allIds.subList(start, end);
//         List<SizingYarnIssueEntry> entries = pageIds.isEmpty() ? Collections.emptyList() : fetcher.apply(pageIds);
//         List<SizingYarnIssueEntryDTO> dtos = entries.stream()
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//         return new PaginatedResponse<>(
//                 total,
//                 end < total,
//                 off > 0,
//                 off,
//                 lim,
//                 dtos
//         );
//     }

//     // ===== CONVERSION METHODS =====
    
//     private SizingYarnIssueEntryDTO convertToDTO(SizingYarnIssueEntry entity) {
//         SizingYarnIssueEntryDTO dto = new SizingYarnIssueEntryDTO();
//         dto.setId(entity.getId());
//         dto.setVendorId(entity.getVendorId());
//         dto.setCompanyId(entity.getCompanyId());
//         dto.setOutwardTypeId(entity.getOutwardTypeId());
//         dto.setSizingPlanId(entity.getSizingPlanId());
//         dto.setTransportationDetails(entity.getTransportationDetails());
//         dto.setTermsConditionsId(entity.getTermsConditionsId());
//         dto.setFabricDetails(entity.getFabricDetails());
//         dto.setSizingYarnIssueDate(entity.getSizingYarnIssueDate());
//         dto.setYarnIssueChallanNo(entity.getYarnIssueChallanNo());
//         dto.setActiveFlag(entity.isActiveFlag());
//         dto.setRemark(entity.getRemark());
//         dto.setVehicleNo(entity.getVehicleNo());
//         dto.setGrossWt(entity.getGrossWt());
//         if (entity.getSizingYarnIssues() != null && !entity.getSizingYarnIssues().isEmpty()) {
//             entity.getSizingYarnIssues().stream().findFirst().ifPresent(issue -> dto.setIssueNo(issue.getIssueNo()));
//         }
//         if (entity.getRequirements() != null) {
//             List<SizingYarnRequirementDTO> requirementDTOs = entity.getRequirements().stream()
//                     .map(this::convertRequirementToDTO)
//                     .collect(Collectors.toList());
//             dto.setRequirements(requirementDTOs);
//         }
        
//         if (entity.getSizingYarnIssues() != null) {
//             List<SizingYarnIssueDTO> sizingYarnIssueDTOs = entity.getSizingYarnIssues().stream()
//                     .map(this::convertYarnIssueToDTO)
//                     .collect(Collectors.toList());
//             dto.setSizingYarnIssue(sizingYarnIssueDTOs);
//         }


//         return dto;
//     }

//     private SizingYarnRequirementDTO convertRequirementToDTO(SizingYarnRequirement requirement) {
//         SizingYarnRequirementDTO dto = new SizingYarnRequirementDTO();
//         dto.setId(requirement.getId());
//         dto.setFabricDetails(requirement.getFabricDetails());
//         dto.setYarnName(requirement.getYarnName());
//         dto.setActualYarn(requirement.getActualYarn());
//         dto.setActualYarnId(requirement.getActualYarnId());
//         dto.setYarnId(requirement.getYarnId());
//         dto.setWarpMeters(requirement.getWarpMeters());
//         dto.setWarpGramsPerMeter(requirement.getWarpGramsPerMeter());
//         dto.setWarpRequirement(requirement.getWarpRequirement());
//         dto.setTotalIssuedQty(requirement.getTotalIssuedQty());
//         dto.setBalanceToIssue(requirement.getBalanceToIssue());
//         dto.setActiveFlag(requirement.isActiveFlag());

//         return dto;
//     }

//     private SizingYarnIssueDTO convertYarnIssueToDTO(SizingYarnIssue sizingYarnIssue) {
//         SizingYarnIssueDTO dto = new SizingYarnIssueDTO();
//         dto.setId(sizingYarnIssue.getId());
//         dto.setYarnName(sizingYarnIssue.getYarnName());
//         dto.setYarnId(sizingYarnIssue.getYarnId());
//         dto.setMillId(sizingYarnIssue.getMillId());
//         dto.setWarehouseId(sizingYarnIssue.getWarehouseId());
//         dto.setAlternateYarn(sizingYarnIssue.getAlternateYarn());
//         dto.setAvailableReqQty(sizingYarnIssue.getAvailableReqQty());
//         dto.setAvailableQty(sizingYarnIssue.getAvailableQty());
//         dto.setNoOfBags(sizingYarnIssue.getNoOfBags());
//         dto.setKgsPerBag(sizingYarnIssue.getKgsPerBag());
//         dto.setConesPerBag(sizingYarnIssue.getConesPerBag());
//         dto.setConesWeight(sizingYarnIssue.getConesWeight());
//         dto.setIssueQty(sizingYarnIssue.getIssueQty());
//         dto.setLotNo(sizingYarnIssue.getLotNo());
// //        dto.setLotId(sizingYarnIssue.getLotId());
//         dto.setIssueNo(sizingYarnIssue.getIssueNo());
//         dto.setLotAvailableQty(sizingYarnIssue.getLotAvailableQty());
//         dto.setLotAvailableBags(sizingYarnIssue.getLotAvailableBags());
//         dto.setActiveFlag(sizingYarnIssue.isActiveFlag());
//         dto.setSizingYarnIssueEntryId(sizingYarnIssue.getSizingYarnIssueEntry() != null ? sizingYarnIssue.getSizingYarnIssueEntry().getId() : null);
//         dto.setInwardId(sizingYarnIssue.getInwardId());
//         dto.setOrderItemId(sizingYarnIssue.getOrderItemId());
//         dto.setOutwardTypeId(sizingYarnIssue.getOutwardTypeId());
//         dto.setActualItemId(sizingYarnIssue.getActualItemId());
//         dto.setUomId(sizingYarnIssue.getUomId());
//         dto.setIssuedBags(sizingYarnIssue.getIssuedBags());
//         dto.setCompanyId(sizingYarnIssue.getCompanyId());
//         return dto;
//     }
    
//      @Transactional(readOnly = true)
//     public List<YarnInwardStockDetailsDto> getYarnInwardStockDetailsByItemIds(List<Long> itemIds, Long outwardTypeId, Long companyId) {
//         List<YarnInwardStockDetails> details = new java.util.ArrayList<>();
        
//         for (Long itemId : itemIds) {
//             details.addAll(yarnInwardStockDetailsRepository.findByItemId(itemId));
//         }
        
//         return details.stream()
//                 .filter(detail -> companyId == null || (detail.getYarnInventory() != null && companyId.equals(detail.getYarnInventory().getCompanyId())))
//                 .map(detail -> {
//                     YarnInwardStockDetailsDto dto = new YarnInwardStockDetailsDto(detail);
//                     List<YarnOutwardStockDetails> outwardDetails = yarnOutwardStockDetailsRepository.findByOrderItemId(detail.getOrderItemId());
//                     if (outwardTypeId != null) {
//                         dto.setOutwardTypeId(outwardTypeId);
//                         outwardDetails.stream()
//                             .filter(outward -> outwardTypeId.equals(outward.getOutwardTypeId()))
//                             .findFirst()
//                             .ifPresent(outward -> dto.setOutwardTypeId(outward.getOutwardTypeId()));
//                     } else {
//                         outwardDetails.stream()
//                             .findFirst()
//                             .ifPresent(outward -> dto.setOutwardTypeId(outward.getOutwardTypeId()));
//                     }
//                     return dto;
//                 })
//                 .filter(dto -> dto.getAvailableQty() != null && dto.getAvailableQty().compareTo(BigDecimal.ZERO) > 0)
//                 .collect(Collectors.toList());
//     }
//     // ===== HELPER METHODS FOR QUANTITY AND OUTWARD MANAGEMENT =====
    
//     private BigDecimal calculateTotalIssuedQuantity(Long orderItemId) {
//         if (orderItemId == null) {
//             return BigDecimal.ZERO;
//         }
//         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(orderItemId);
//         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
//     }

//     private BigDecimal calculateTotalIssuedBags(Long orderItemId) {
//         if (orderItemId == null) {
//             return BigDecimal.ZERO;
//         }
//         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(orderItemId);
//         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
//     }

//     private String generateYarnOutwardNo(SizingYarnIssueEntry sizingYarnIssueEntry) {
//         return "SYO-" + LocalDate.now().getYear() + "-" + sizingYarnIssueEntry.getId();
//     }

//     private String getFinancialYearId(LocalDate date) {
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int startYear;
//         int endYear;
//         if (month >= 4) {
//             startYear = year;
//             endYear = year + 1;
//         } else {
//             startYear = year - 1;
//             endYear = year;
//         }
//         String endYearLastTwo = String.format("%02d", endYear % 100);
//         return startYear + "-" + endYearLastTwo;
//     }

//     private String getShortFinancialYear(LocalDate date) {
//         if (date == null) {
//             date = LocalDate.now();
//         }
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int startYear = month >= 4 ? year : year - 1;
//         int endYear = startYear + 1;
//         return String.format("%02d-%02d", startYear % 100, endYear % 100);
//     }

//     private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
//         Set<Long> stockDetailsIds = new HashSet<>();

//         for (YarnOutwardStockDetails outwardDetail : outwardDetails) {
//             Long stockId = outwardDetail.getYarnInwardStockDetailsId();
//             if (stockId == null || stockId == 0) {
//                 if (outwardDetail.getOrderItemId() != null && outwardDetail.getOrderItemId() != 0) {
//                     Optional<YarnInwardStockDetails> opt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(outwardDetail.getOrderItemId());
//                     if (opt.isPresent()) {
//                         stockId = opt.get().getId();
//                         outwardDetail.setYarnInwardStockDetailsId(stockId);
//                     }
//                 }
//             }
//             if (stockId != null && stockId != 0) {
//                 stockDetailsIds.add(stockId);
//             }
//         }

//         for (Long stockId : stockDetailsIds) {
//             Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(stockId);
//             if (!inwardDetailOpt.isPresent()) {
//                 continue;
//             }

//             YarnInwardStockDetails detail = inwardDetailOpt.get();
//             BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
//             BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;

//             BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockId);
//             BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockId);

//             BigDecimal newAvailableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
//             BigDecimal newAvailableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);

//             detail.setAvailableQty(newAvailableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableQty);
//             detail.setAvailableBags(newAvailableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableBags);

//             yarnInwardStockDetailsRepository.save(detail);
//         }
//     }
    
//     private void populateInwardDetailsFromStockDetails(YarnOutwardStockDetails outwardDetail, Long yarnorderItemId) {
//         if (yarnorderItemId != null) {
//             Optional<YarnInwardStockDetails> inwardStockDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnorderItemId);
//             if (inwardStockDetail.isPresent()) {
//                 YarnInwardStockDetails details = inwardStockDetail.get();
//                 outwardDetail.setYarnInwardStockDetailsId(details.getId());
//                 if (details.getYarnInventory() != null) {
//                     outwardDetail.setInwardId(details.getYarnInventory().getId());
//                 }
//                 if (details.getLotNo() != null) {
//                     outwardDetail.setLotNo(details.getLotNo());
//                 }
//             }
//         }
//     }
    
//     private void populateLotNoFromStockDetails(YarnOutwardStockDetails outwardDetail, Long yarnorderItemId) {
//         if (yarnorderItemId != null) {
//             Optional<YarnInwardStockDetails> inwardStockDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnorderItemId);
//             if (inwardStockDetail.isPresent()) {
//                 YarnInwardStockDetails details = inwardStockDetail.get();
//                 outwardDetail.setYarnInwardStockDetailsId(details.getId());
//                 if (details.getLotNo() != null) {
//                     outwardDetail.setLotNo(details.getLotNo());
//                 }
//             }
//         }
//     }

//      private void createYarnOutwardForEntry(SizingYarnIssueEntry sizingYarnIssueEntry, SizingYarnIssueEntryDTO dto) {
//         if (dto.getSizingYarnIssue() == null || dto.getSizingYarnIssue().isEmpty()) {
//             return;
//         }

//         Long outwardTypeId = 53L;
//         Long companyId = 1L;

//         YarnOutward yarnOutward = new YarnOutward();
//         yarnOutward.setOutwardTypeId(outwardTypeId);
//         String outwardNo = generateYarnOutwardNo(sizingYarnIssueEntry);
//         if (sizingYarnIssueEntry.getSizingYarnIssues() != null && !sizingYarnIssueEntry.getSizingYarnIssues().isEmpty()) {
//             String issueNo = sizingYarnIssueEntry.getSizingYarnIssues().get(0).getIssueNo();
//             if (issueNo != null) {
//                 outwardNo = issueNo;
//             }
//         }
//         yarnOutward.setOutwardNo(outwardNo);
//         yarnOutward.setCompanyId(companyId);

//         LocalDate outwardDate = dto.getSizingYarnIssueDate() != null ? 
//             dto.getSizingYarnIssueDate() : LocalDate.now();
//         yarnOutward.setOutwardDate(outwardDate);

//         yarnOutward.setFinancialYearId(getFinancialYearId(outwardDate));
//         yarnOutward.setOrderId(dto.getSizingPlanId());
        
//         if (dto.getSizingPlanId() != null) {
//             sizingPlanRepository.findById(dto.getSizingPlanId())
//                 .ifPresent(plan -> yarnOutward.setOrderNo(plan.getSizingPlanNo()));
//         }
        
//         yarnOutward.setVendorId(dto.getVendorId());
//         yarnOutward.setRemarks(null);
//         yarnOutward.setActiveFlag(true);

//         YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);

//         List<YarnOutwardStockDetails> outwardDetailsToSave = new java.util.ArrayList<>();

//         for (SizingYarnIssueDTO yarnIssue : dto.getSizingYarnIssue()) {
//             YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
//             outwardDetail.setYarnOutward(savedYarnOutward);
//             outwardDetail.setOrderItemId(yarnIssue.getOrderItemId());
//             outwardDetail.setOutwardTypeId(yarnIssue.getOutwardTypeId());
//             outwardDetail.setActualItemId(yarnIssue.getActualItemId());
//             outwardDetail.setUomId(yarnIssue.getUomId());
//             outwardDetail.setMillId(yarnIssue.getMillId());
//             outwardDetail.setWarehouseId(yarnIssue.getWarehouseId());
//             outwardDetail.setIssuedQty(yarnIssue.getIssueQty());
//             outwardDetail.setIssuedBags(yarnIssue.getIssuedBags());
//             outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag());
//             outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
//             outwardDetail.setConesWeight(yarnIssue.getConesWeight());

//             if (yarnIssue.getInwardId() != null) {
//                 outwardDetail.setInwardId(yarnIssue.getInwardId());
//                 populateLotNoFromStockDetails(outwardDetail, yarnIssue.getOrderItemId());
//             } else {
//                 populateInwardDetailsFromStockDetails(outwardDetail, yarnIssue.getOrderItemId());
//             }

//             outwardDetailsToSave.add(outwardDetail);
//         }

//         if (dto.getYarnOutwardStockDetails() != null) {
//             for (YarnOutwardStockDetailsDTO outwardDTO : dto.getYarnOutwardStockDetails()) {
//                 YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
//                 outwardDetail.setYarnOutward(savedYarnOutward);
//                 outwardDetail.setOrderItemId(outwardDTO.getOrderItemId());
//                 outwardDetail.setOutwardTypeId(outwardDTO.getOutwardTypeId());
//                 outwardDetail.setActualItemId(outwardDTO.getActualItemId());
//                 outwardDetail.setUomId(outwardDTO.getUomId());
//                 outwardDetail.setMillId(outwardDTO.getMillId());
//                 outwardDetail.setWarehouseId(outwardDTO.getWarehouseId());
//                 outwardDetail.setIssuedQty(outwardDTO.getIssuedQty());
//                 outwardDetail.setIssuedBags(outwardDTO.getIssuedBags());
//                 outwardDetail.setKgsPerBag(outwardDTO.getKgsPerBag());
//                 outwardDetail.setConesPerBag(outwardDTO.getConesPerBag());
//                 outwardDetail.setConesWeight(outwardDTO.getConesWeight());

//                 if (outwardDTO.getInwardId() != null) {
//                     outwardDetail.setInwardId(outwardDTO.getInwardId());
//                     populateLotNoFromStockDetails(outwardDetail, outwardDTO.getOrderItemId());
//                 } else {
//                     populateInwardDetailsFromStockDetails(outwardDetail, outwardDTO.getOrderItemId());
//                 }

//                 outwardDetailsToSave.add(outwardDetail);
//             }
//         }

//         if (!outwardDetailsToSave.isEmpty()) {
//             List<YarnOutwardStockDetails> savedOutwardDetails = yarnOutwardStockDetailsRepository.saveAll(outwardDetailsToSave);
//             savedYarnOutward.setYarnOutwardStockDetails(savedOutwardDetails);
//             yarnOutwardRepository.save(savedYarnOutward);
            
//             updateAvailableQtyInInwardStockDetails(savedOutwardDetails);
//         }
//     }

//     private Long normalizeId(Long id) {
//         return (id != null && id > 0) ? id : null;
//     }
// }


package com.tech3.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingYarnInwardDTO;
import com.tech3.erp.dto.SizingYarnInwardDetailDTO;
import com.tech3.erp.dto.SizingYarnIssueDTO;
import com.tech3.erp.dto.SizingYarnIssueEntryDTO;
import com.tech3.erp.dto.SizingYarnRequirementDTO;
import com.tech3.erp.dto.YarnInwardStockDetailsDto;
import com.tech3.erp.dto.YarnOutwardStockDetailsDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.LotEntry;
import com.tech3.erp.entity.LotOutward;
import com.tech3.erp.entity.PurchaseInward;
import com.tech3.erp.entity.SizingJobWorkOrder;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingQualityDetails;
import com.tech3.erp.entity.SizingYarnIssue;
import com.tech3.erp.entity.SizingYarnInward;
import com.tech3.erp.entity.SizingYarnInwardDetail;
import com.tech3.erp.entity.SizingYarnIssueEntry;
import com.tech3.erp.entity.SizingYarnRequirement;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnOutwardStockDetails;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.LotEntryRepository;
import com.tech3.erp.repository.LotOutwardRepository;
import com.tech3.erp.repository.PurchaseInwardRepository;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingQualityDetailsRepository;
import com.tech3.erp.repository.SizingYarnInwardRepository;
import com.tech3.erp.repository.SizingYarnIssueEntryRepository;
import com.tech3.erp.repository.SizingYarnIssueRepository;
import com.tech3.erp.repository.SizingYarnOutwardDetailRepository;
import com.tech3.erp.repository.SizingYarnRequirementRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.service.NotificationService;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class SizingYarnIssueEntryService {

    @Autowired
    private PurchaseInwardRepository purchaseInwardRepository;

    @Autowired
    private SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository;
    
    @Autowired
    private SizingYarnRequirementRepository sizingYarnRequirementRepository;
    
    @Autowired
    private SizingYarnIssueRepository sizingYarnIssueRepository;
    
    @Autowired
    private LotEntryRepository lotEntryRepository;
    
    @Autowired
    private LotOutwardRepository lotOutwardRepository;
    
    @Autowired
    private SizingQualityDetailsRepository sizingQualityDetailsRepository;
    
    @Autowired
    private SizingPlanRepository sizingPlanRepository;
    
    @Autowired
    private SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    
    @Autowired
    private ReferenceValidationService referenceValidationService;
    
    @Autowired
    private SizingYarnInwardService sizingYarnInwardService;

    @Autowired
    private SizingYarnInwardRepository sizingYarnInwardRepository;
    
    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;
    
    @Autowired
    private YarnOutwardRepository yarnOutwardRepository;
    
    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;

    @Autowired
    private SizingYarnOutwardDetailRepository sizingYarnOutwardDetailRepository;

       @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrys() {
        List<SizingYarnIssueEntry> issues = sizingYarnIssueEntryRepository.findAll();
        return issues.stream()
                .sorted(Comparator.comparing(SizingYarnIssueEntry::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrysWithPagination(Long offset, Long limit) {
        return buildPaginatedResponse(
                sizingYarnIssueEntryRepository.findAllIds(),
                offset,
                limit,
                ids -> ids.isEmpty() ? Collections.emptyList() : sizingYarnIssueEntryRepository.findByIdsWithDetails(ids)
        );
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SizingYarnIssueEntryDTO> getAllSizingYarnIssueEntrysByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return buildPaginatedResponse(
                sizingYarnIssueEntryRepository.findIdsByCompanyId(companyId),
                offset,
                limit,
                ids -> ids.isEmpty() ? Collections.emptyList() : sizingYarnIssueEntryRepository.findByCompanyIdAndIds(companyId, ids)
        );
    }

    @Transactional(readOnly = true)
    public List<SizingYarnIssueEntryDTO> getSizingYarnIssueEntrysByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return sizingYarnIssueEntryRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SizingYarnIssueEntryDTO getSizingYarnIssueEntryById(Long id) {
        SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
        return convertToDTO(issue);
    }

    @Transactional(readOnly = true)
    public SizingYarnIssueEntryDTO getSizingYarnIssueEntryByIdAndCompany(Long id, Long companyId) {
        SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
        return convertToDTO(issue);
    }

    // @Transactional(readOnly = true)
    // public boolean isMapped(Long id) {
    //     return sizingYarnIssueEntryRepository.findById(id).map(issue -> {
    //         // Check if there's a SizingYarnInward created for this issue that has been further mapped
    //         String suffix = "-" + issue.getId();
    //         return sizingYarnInwardRepository.findFirstByInwardNoEndingWith(suffix)
    //                 .map(inward -> sizingYarnOutwardDetailRepository.existsBySizingYarnInwardId(inward.getId()) || 
    //                                sizingYarnOutwardDetailRepository.existsBySizingYarnInwardNo(inward.getInwardNo()))
    //                 .orElse(false);
    //     }).orElse(false);
    // }

      public boolean isMapped(Long id) {
        if (sizingYarnInwardRepository.existsBySizingYarnIssueId(id)) return true;
        return false;
    }


    @Transactional
    public SizingYarnIssueEntryDTO createSizingYarnIssueEntry(SizingYarnIssueEntryDTO dto) {
        // Validate before processing
        validateSizingYarnIssue(dto);
        
        SizingPlan sizingPlan = referenceValidationService.requireReference(
                dto.getSizingPlanId(),
                () -> sizingPlanRepository.findById(dto.getSizingPlanId()),
                "Sizing Plan",
                dto.getSizingPlanNo());
        
        SizingYarnIssueEntry issue = new SizingYarnIssueEntry();
        populateEntityFromDTO(issue, dto, false);
        
        // Ensure sizingPlanId is set correctly from the validated sizingPlan
        issue.setSizingPlanId(normalizeId(dto.getSizingPlanId()));
        
        SizingYarnIssueEntry savedIssue = sizingYarnIssueEntryRepository.save(issue);
        
        // Process lot reductions and outward entries
        processLotOutward(savedIssue, "SIZING_ISSUE", savedIssue.getId(), dto.getOutwardTypeId());
        
        // Update sizing plan actualYarn based on requirements
        updateSizingPlanActualYarn(dto.getSizingPlanId(), dto.getRequirements());
        
        syncSizingYarnInwardRecord(savedIssue);

        checkAndNotifySizingPlanBalanceZero(savedIssue.getSizingPlanId(), dto.getSizingPlanNo(), savedIssue.getCompanyId());

        return convertToDTO(savedIssue);
    }

    @Transactional
    public SizingYarnIssueEntryDTO updateSizingYarnIssueEntry(Long id, SizingYarnIssueEntryDTO dto) {
        validateSizingYarnIssue(dto);
        SizingYarnIssueEntry existingIssue = sizingYarnIssueEntryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
        if (dto.getCompanyId() == null) {
            dto.setCompanyId(existingIssue.getCompanyId());
        }
        return updateAndPersistIssue(existingIssue, dto, id);
    }

    @Transactional
    public SizingYarnIssueEntryDTO updateSizingYarnIssueEntryByCompany(Long id, Long companyId, SizingYarnIssueEntryDTO dto) {
        validateSizingYarnIssue(dto);
        SizingYarnIssueEntry existingIssue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
            .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
        dto.setCompanyId(companyId);
        return updateAndPersistIssue(existingIssue, dto, id);
    }

    @Transactional
    public void deleteSizingYarnIssueEntry(Long id) {
        SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
        deleteIssueEntry(issue);
    }

    @Transactional
    public void deleteSizingYarnIssueEntryByCompany(Long id, Long companyId) {
        SizingYarnIssueEntry issue = sizingYarnIssueEntryRepository.findByIdAndCompanyId(id, companyId)
            .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Issue Entry not found"));
        deleteIssueEntry(issue);
    }

    private SizingYarnIssueEntryDTO updateAndPersistIssue(SizingYarnIssueEntry existingIssue, SizingYarnIssueEntryDTO dto, Long referenceId) {
        reverseLotReductions(existingIssue, true);
        populateEntityFromDTO(existingIssue, dto, true);
        processLotOutward(existingIssue, "SIZING_ISSUE", referenceId, dto.getOutwardTypeId());
        updateSizingPlanActualYarn(dto.getSizingPlanId(), dto.getRequirements());
        SizingYarnIssueEntry savedIssue = sizingYarnIssueEntryRepository.save(existingIssue);
        syncSizingYarnInwardRecord(savedIssue);
        checkAndNotifySizingPlanBalanceZero(savedIssue.getSizingPlanId(), dto.getSizingPlanNo(), savedIssue.getCompanyId());
        return convertToDTO(savedIssue);
    }

    private void checkAndNotifySizingPlanBalanceZero(Long sizingPlanId, String sizingPlanNo, Long companyId) {
        if (sizingPlanId == null || companyId == null) return;
        List<SizingYarnRequirement> requirements = sizingYarnRequirementRepository.findActiveBySizingPlanId(sizingPlanId);
        if (requirements.isEmpty()) return;
        boolean allBalanceZero = requirements.stream()
                .allMatch(r -> r.getBalanceToIssue() != null && r.getBalanceToIssue().compareTo(BigDecimal.ZERO) <= 0);
        if (allBalanceZero) {
            notificationService.createAndPush(
                    companyId,
                    null,
                    NotificationService.EVENT_SIZING_PLAN_BALANCE_ZERO,
                    sizingPlanId,
                    null,
                    sizingPlanNo
            );
        }
    }

    private void deleteIssueEntry(SizingYarnIssueEntry issue) {
        reverseLotReductions(issue, true);
        deleteSizingYarnInwardIfPresent(issue);
        sizingYarnIssueEntryRepository.delete(issue);
    }

    private void deleteSizingYarnInwardIfPresent(SizingYarnIssueEntry issue) {
        resolveSizingYarnInwardForIssue(issue).ifPresent(inward -> sizingYarnInwardService.deleteSizingYarnInward(inward.getId()));
    }

    private Optional<SizingYarnInward> resolveSizingYarnInwardForIssue(SizingYarnIssueEntry issue) {
        if (issue == null || issue.getId() == null) {
            return Optional.empty();
        }
        String suffix = "-" + issue.getId();
        return sizingYarnInwardRepository.findFirstByInwardNoEndingWith(suffix);
    }

    // ===== NEW METHOD: UPDATE SIZING PLAN ACTUAL YARN =====
    
    /**
     * Updates the actualYarn field in SizingQualityDetails based on the requirements
     * from SizingYarnIssue. This syncs the actualYarn changes back to the sizing plan.
     */
    private void updateSizingPlanActualYarn(Long sizingPlanId, List<SizingYarnRequirementDTO> requirements) {
        if (sizingPlanId == null || requirements == null || requirements.isEmpty()) {
            return;
        }
        
        // Get all quality details for this sizing plan
        List<SizingQualityDetails> qualityDetailsList = 
            sizingQualityDetailsRepository.findBySizingPlanId(sizingPlanId);
        
        // Create a map for quick lookup: yarnId -> actualYarnId
        // We match based on yarnId (original yarn) to find which quality detail to update
        for (SizingYarnRequirementDTO req : requirements) {
            if (req.getYarnId() != null && req.getActualYarnId() != null) {
                // Find matching quality detail by yarnId
                qualityDetailsList.stream()
                    .filter(qd -> qd.getYarnId().equals(req.getYarnId()))
                    .forEach(qd -> {
                        // Update the actualYarn field
                        qd.setActualYarn(req.getActualYarnId());
                    });
            }
        }
        
        // Save all updated quality details
        sizingQualityDetailsRepository.saveAll(qualityDetailsList);
    }

    private void syncSizingYarnInwardRecord(SizingYarnIssueEntry savedIssue) {
        if (SyncContext.isInProgress() || savedIssue == null || savedIssue.getSizingYarnIssues() == null || savedIssue.getSizingYarnIssues().isEmpty()) {
            return;
        }

        try {
            SyncContext.setInProgress(true);
            doSyncSizingYarnInwardRecord(savedIssue);
        } finally {
            SyncContext.clear();
        }
    }

    private void doSyncSizingYarnInwardRecord(SizingYarnIssueEntry savedIssue) {

        if (savedIssue.getVendorId() != null) {
            boolean isInternal = vendorRepository.findById(savedIssue.getVendorId())
                    .map(v -> v.getIsInternalVendor() != null && v.getIsInternalVendor() == 1)
                    .orElse(false);
            if (!isInternal) {
                return;
            }
        } else {
            return;
        }

        SizingYarnInwardDTO inwardDTO = new SizingYarnInwardDTO();
        inwardDTO.setSizingYarnIssueId(savedIssue.getId());
        LocalDate inwardDate = savedIssue.getSizingYarnIssueDate() != null ? savedIssue.getSizingYarnIssueDate() : LocalDate.now();
        String financialYear = getShortFinancialYear(inwardDate);
        inwardDTO.setInwardNo(String.format("SZYI-%s-%d", financialYear, savedIssue.getId()));
        inwardDTO.setInwardDate(savedIssue.getSizingYarnIssueDate());
        inwardDTO.setCompanyId(savedIssue.getCompanyId());

        Long sizingPlanId = savedIssue.getSizingPlanId();
        if (sizingPlanId != null) {
            sizingJobWorkOrderRepository.findBySizingPlanId(sizingPlanId)
                    .ifPresent(jobOrder -> {
                        inwardDTO.setSizingJobWorkOrderId(jobOrder.getId());
                        inwardDTO.setOrderTypeId(jobOrder.getOrderTypeId());
                        inwardDTO.setBuyerId(jobOrder.getBuyerId());
                        inwardDTO.setFactoryCompanyId(jobOrder.getFactoryCompanyId());
                        inwardDTO.setTradingCompanyId(jobOrder.getTradingCompanyId());
                        if (Long.valueOf(1L).equals(jobOrder.getOrderTypeId()) && jobOrder.getTradingCompanyId() != null) {
                            inwardDTO.setCompanyId(jobOrder.getTradingCompanyId());
                        }
                    });

            sizingPlanRepository.findById(sizingPlanId).ifPresent(plan -> {
                if (inwardDTO.getOrderTypeId() == null) {
                    inwardDTO.setOrderTypeId(plan.getOrderTypeId());
                }
                if (inwardDTO.getFactoryCompanyId() == null) {
                    inwardDTO.setFactoryCompanyId(plan.getFactoryCompanyId());
                }
                if (inwardDTO.getTradingCompanyId() == null) {
                    inwardDTO.setTradingCompanyId(plan.getTradingCompanyId());
                }
                if (Long.valueOf(1L).equals(inwardDTO.getOrderTypeId())) {
                    Long targetCompanyId = plan.getTradingCompanyId() != null ? plan.getTradingCompanyId() : plan.getCompanyId();
                    if (targetCompanyId != null) {
                        inwardDTO.setCompanyId(targetCompanyId);
                    }
                } else if (inwardDTO.getCompanyId() == null) {
                    inwardDTO.setCompanyId(plan.getCompanyId());
                }
            });
        }

        if (inwardDTO.getFactoryCompanyId() == null) {
            inwardDTO.setFactoryCompanyId(savedIssue.getCompanyId());
        }

        if (Long.valueOf(1L).equals(inwardDTO.getOrderTypeId()) && inwardDTO.getTradingCompanyId() == null) {
            inwardDTO.setTradingCompanyId(inwardDTO.getCompanyId());
        }

        Long buyerCompanyId = savedIssue.getCompanyId();
        if (buyerCompanyId != null) {
            inwardDTO.setTradingCompanyId(buyerCompanyId);
        }

        Optional<SizingYarnInward> existingInward = resolveSizingYarnInwardForIssue(savedIssue);
        List<SizingYarnInwardDetailDTO> yarnDetailDTOs = savedIssue.getSizingYarnIssues().stream()
                .map(issue -> {
                    SizingYarnInwardDetailDTO detailDTO = new SizingYarnInwardDetailDTO();
                    detailDTO.setYarnName(issue.getYarnName());
                    detailDTO.setYarnId(issue.getYarnId());
                    detailDTO.setNoOfBags(issue.getNoOfBags());
                    detailDTO.setKgsPerBag(issue.getKgsPerBag());
                    detailDTO.setConesPerBag(issue.getConesPerBag());
                    detailDTO.setConesWeight(issue.getConesWeight());
                    detailDTO.setWarehouseId(issue.getWarehouseId());
                    detailDTO.setMillId(issue.getMillId());
                    detailDTO.setIssueQty(issue.getIssueQty());
                    detailDTO.setLotNo(issue.getLotNo());
                    return detailDTO;
                })
                .collect(Collectors.toList());
        preserveSizingYarnInwardDetailIds(existingInward.orElse(null), yarnDetailDTOs);
        inwardDTO.setYarnDetails(yarnDetailDTOs);

        if (existingInward.isPresent()) {
            sizingYarnInwardService.updateSizingYarnInward(existingInward.get().getId(), inwardDTO);
        } else {
            sizingYarnInwardService.createSizingYarnInward(inwardDTO);
        }

        // Simultaneously create/update YarnInventory entry for internal vendor
        String spNo = null;
        if (savedIssue.getSizingPlanId() != null) {
            spNo = sizingPlanRepository.findById(savedIssue.getSizingPlanId())
                    .map(SizingPlan::getSizingPlanNo)
                    .orElse(null);
        }
        if (spNo != null) {
            syncYarnInventoryRecord(savedIssue, spNo, inwardDTO.getInwardNo());
        }
    }

    private void preserveSizingYarnInwardDetailIds(SizingYarnInward existingInward, List<SizingYarnInwardDetailDTO> yarnDetailDTOs) {
        if (existingInward == null || existingInward.getYarnDetails() == null || yarnDetailDTOs == null || yarnDetailDTOs.isEmpty()) {
            return;
        }

        Map<String, List<SizingYarnInwardDetail>> existingDetailsByKey = existingInward.getYarnDetails().stream()
                .collect(Collectors.groupingBy(this::buildSizingYarnInwardDetailKey));

        for (SizingYarnInwardDetailDTO detailDTO : yarnDetailDTOs) {
            List<SizingYarnInwardDetail> matches = existingDetailsByKey.get(buildSizingYarnInwardDetailKey(detailDTO));
            if (matches != null && !matches.isEmpty()) {
                detailDTO.setId(matches.remove(0).getId());
            }
        }
    }

    private String buildSizingYarnInwardDetailKey(SizingYarnInwardDetail detail) {
        if (detail == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(normalizeId(detail.getYarnId())),
                String.valueOf(normalizeId(detail.getMillId())),
                String.valueOf(normalizeId(detail.getWarehouseId())),
                normalizeText(detail.getLotNo()));
    }

    private String buildSizingYarnInwardDetailKey(SizingYarnInwardDetailDTO detailDTO) {
        if (detailDTO == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(normalizeId(detailDTO.getYarnId())),
                String.valueOf(normalizeId(detailDTO.getMillId())),
                String.valueOf(normalizeId(detailDTO.getWarehouseId())),
                normalizeText(detailDTO.getLotNo()));
    }

    private String normalizeText(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private void syncYarnInventoryRecord(SizingYarnIssueEntry savedIssue, String sizingPlanNo, String inwardNo) {
        Long inwardTypeId = codesDetailRepository.findByValue("Sizing-Yarn-Inward")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
                        .map(CodesDetail::getId)
                        .orElse(3L));

        YarnInventory yarnInventory = yarnInventoryRepository.findByInwardNo(inwardNo)
                .orElse(new YarnInventory());
        Optional<SizingYarnInward> sizingYarnInwardOpt = sizingYarnInwardRepository.findByInwardNo(inwardNo);

        yarnInventory.setInwardId(sizingYarnInwardOpt.map(SizingYarnInward::getId).orElse(savedIssue.getId()));
        yarnInventory.setInwardTypeId(inwardTypeId);
        yarnInventory.setInwardNo(inwardNo);
        yarnInventory.setCompanyId(savedIssue.getCompanyId());
        yarnInventory.setInwardDate(savedIssue.getSizingYarnIssueDate() != null ?
                savedIssue.getSizingYarnIssueDate().atStartOfDay() : LocalDateTime.now());
        yarnInventory.setFinancialYear(getFinancialYearId(savedIssue.getSizingYarnIssueDate() != null ?
                savedIssue.getSizingYarnIssueDate() : LocalDate.now()));
        yarnInventory.setDcNo(savedIssue.getYarnIssueChallanNo());
        yarnInventory.setDcDate(savedIssue.getSizingYarnIssueDate());
        yarnInventory.setOrderId(savedIssue.getSizingPlanId());
        yarnInventory.setOrderNo(sizingPlanNo);
        yarnInventory.setVendorId(savedIssue.getVendorId());

        Map<Long, YarnInwardStockDetails> existingDetailsMap = new HashMap<>();
        if (yarnInventory.getDetails() != null) {
            yarnInventory.getDetails().forEach(detail -> {
                if (detail.getOrderItemId() != null) {
                    existingDetailsMap.put(detail.getOrderItemId(), detail);
                }
            });
        }

        List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();
        sizingYarnInwardOpt.ifPresent(inward -> {
            if (inward.getYarnDetails() != null) {
                inward.getYarnDetails().forEach(inwardDetail -> {
                    YarnInwardStockDetails detail = existingDetailsMap.remove(inwardDetail.getId());
                    if (detail == null) {
                        detail = new YarnInwardStockDetails();
                        detail.setYarnInventory(yarnInventory);
                    }

                    BigDecimal inwardBags = inwardDetail.getNoOfBags() != null
                            ? BigDecimal.valueOf(inwardDetail.getNoOfBags())
                            : BigDecimal.ZERO;

                    detail.setOrderItemId(inwardDetail.getId());
                    detail.setInwardTypeId(inwardTypeId);
                    detail.setItemId(normalizeId(inwardDetail.getYarnId()));
                    detail.setMillId(normalizeId(inwardDetail.getMillId()));
                    detail.setLotNo(inwardDetail.getLotNo());
                    detail.setInwardQty(inwardDetail.getIssueQty());
                    detail.setAcceptedQty(inwardDetail.getIssueQty());
                    detail.setInwardBags(inwardBags);
                    detail.setWarehouseId(normalizeId(inwardDetail.getWarehouseId()));
                    detail.setKgsPerBag(inwardDetail.getKgsPerBag());
                    detail.setConesPerBag(inwardDetail.getConesPerBag());
                    detail.setConesWeight(inwardDetail.getConesWeight());
                    recalculateAvailableFromOutward(detail, inwardBags);
                    updatedDetails.add(detail);
                });
            }
        });

        if (yarnInventory.getDetails() != null) {
            yarnInventory.getDetails().clear();
            yarnInventory.getDetails().addAll(updatedDetails);
        } else {
            yarnInventory.setDetails(updatedDetails);
        }

        yarnInventoryRepository.save(yarnInventory);
    }

    private void recalculateAvailableFromOutward(YarnInwardStockDetails detail, BigDecimal inwardBags) {
        BigDecimal inwardQty = detail.getInwardQty() != null ? detail.getInwardQty() : BigDecimal.ZERO;
        BigDecimal totalIssuedQty = BigDecimal.ZERO;
        BigDecimal totalIssuedBags = BigDecimal.ZERO;
        BigDecimal totalIssuedCones = BigDecimal.ZERO;

        if (detail.getId() != null) {
            totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
            totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());
            totalIssuedCones = yarnOutwardStockDetailsRepository.getTotalIssuedConesByYarnInwardStockDetailsId(detail.getId());
        }

        BigDecimal conesPerBag = detail.getConesPerBag() != null ? detail.getConesPerBag() : BigDecimal.ZERO;
        BigDecimal totalInwardCones = inwardBags.multiply(conesPerBag);

        if (totalIssuedCones == null || totalIssuedCones.compareTo(BigDecimal.ZERO) == 0) {
            totalIssuedCones = (totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO).multiply(conesPerBag);
        }

        BigDecimal availableQty = inwardQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
        BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
        BigDecimal availableCones = totalInwardCones.subtract(totalIssuedCones != null ? totalIssuedCones : BigDecimal.ZERO);

        detail.setAvailableQty(availableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableQty);
        detail.setAvailableBags(availableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBags);
        detail.setAvailableCones(availableCones.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableCones);
    }

    // ===== ENTITY POPULATION METHOD =====
    
    private void populateEntityFromDTO(SizingYarnIssueEntry entity, SizingYarnIssueEntryDTO dto, boolean isUpdate) {
        entity.setVendorId(dto.getVendorId());
        entity.setCompanyId(dto.getCompanyId());
        entity.setOutwardTypeId(dto.getOutwardTypeId());
        entity.setSizingPlanId(dto.getSizingPlanId());
        entity.setTransportationDetails(dto.getTransportationDetails());
        entity.setTermsConditionsId(dto.getTermsConditionsId());
        entity.setFabricDetails(dto.getFabricDetails());
        entity.setSizingYarnIssueDate(dto.getSizingYarnIssueDate());
        entity.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
        entity.setActiveFlag(dto.isActiveFlag());
        entity.setRemark(dto.getRemark());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setGrossWt(dto.getGrossWt());

        // Handle requirements
        if (dto.getRequirements() != null) {
            if (isUpdate) {
                Set<Long> dtoReqIds = dto.getRequirements().stream()
                    .map(SizingYarnRequirementDTO::getId)
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());
                entity.getRequirements().removeIf(req -> req.getId() != null && !dtoReqIds.contains(req.getId()));
            }

            for (SizingYarnRequirementDTO reqDTO : dto.getRequirements()) {
                SizingYarnRequirement req;
                if (isUpdate && reqDTO.getId() != null) {
                    req = entity.getRequirements().stream()
                        .filter(r -> r.getId().equals(reqDTO.getId()))
                        .findFirst()
                        .orElseGet(() -> {
                            SizingYarnRequirement newReq = sizingYarnRequirementRepository.findById(reqDTO.getId())
                                .orElseThrow(() -> new EntityNotFoundException("SizingYarnRequirement not found: " + reqDTO.getId()));
                            newReq.setSizingYarnIssueEntry(entity);
                            entity.getRequirements().add(newReq);
                            return newReq;
                        });
                    updateRequirementFromDTO(req, reqDTO);
                } else {
                    req = new SizingYarnRequirement();
                    populateRequirementFromDTO(req, reqDTO);
                    req.setSizingYarnIssueEntry(entity);
                    entity.getRequirements().add(req);
                }
            }
        } else if (isUpdate) {
            entity.getRequirements().clear();
        }
        
        // Handle sizing yarn issues
        if (dto.getSizingYarnIssue() != null) {
            if (isUpdate) {
                Set<Long> dtoIssueIds = dto.getSizingYarnIssue().stream()
                    .map(SizingYarnIssueDTO::getId)
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());
                entity.getSizingYarnIssues().removeIf(issue -> issue.getId() != null && !dtoIssueIds.contains(issue.getId()));
            }

            for (SizingYarnIssueDTO issueDTO : dto.getSizingYarnIssue()) {
                SizingYarnIssue issue;
                if (isUpdate && issueDTO.getId() != null) {
                    issue = entity.getSizingYarnIssues().stream()
                        .filter(i -> i.getId().equals(issueDTO.getId()))
                        .findFirst()
                        .orElseGet(() -> {
                            Optional<SizingYarnIssue> dbIssue = sizingYarnIssueRepository.findById(issueDTO.getId());
                            if (dbIssue.isPresent()) {
                                SizingYarnIssue existingIssue = dbIssue.get();
                                existingIssue.setSizingYarnIssueEntry(entity);
                                entity.getSizingYarnIssues().add(existingIssue);
                                return existingIssue;
                            }
                            SizingYarnIssue brandNewIssue = new SizingYarnIssue();
                            brandNewIssue.setSizingYarnIssueEntry(entity);
                            entity.getSizingYarnIssues().add(brandNewIssue);
                            return brandNewIssue;
                        });
                    updateYarnIssueFromDTO(issue, issueDTO, dto.getIssueNo());
                } else {
                    issue = new SizingYarnIssue();
                    populateYarnIssueFromDTO(issue, issueDTO,dto.getIssueNo());
                    issue.setSizingYarnIssueEntry(entity);
                    entity.getSizingYarnIssues().add(issue);
                }
            }
        } else if (isUpdate) {
            entity.getSizingYarnIssues().clear();
        }
    }
    
    private void populateRequirementFromDTO(SizingYarnRequirement entity, SizingYarnRequirementDTO dto) {
        entity.setId(null);
        entity.setFabricDetails(dto.getFabricDetails());
        entity.setYarnName(dto.getYarnName());
        entity.setActualYarn(dto.getActualYarn());
        entity.setActualYarnId(dto.getActualYarnId());
        entity.setYarnId(dto.getYarnId());
        entity.setWarpMeters(dto.getWarpMeters());
        entity.setWarpGramsPerMeter(dto.getWarpGramsPerMeter());
        entity.setWarpRequirement(dto.getWarpRequirement());
        entity.setTotalIssuedQty(dto.getTotalIssuedQty());
        entity.setBalanceToIssue(dto.getBalanceToIssue());
        entity.setActiveFlag(dto.isActiveFlag());
    }
    
    private void updateRequirementFromDTO(SizingYarnRequirement entity, SizingYarnRequirementDTO dto) {
        entity.setFabricDetails(dto.getFabricDetails());
        entity.setYarnName(dto.getYarnName());
        entity.setActualYarn(dto.getActualYarn());
        entity.setActualYarnId(dto.getActualYarnId());
        entity.setYarnId(dto.getYarnId());
        entity.setWarpMeters(dto.getWarpMeters());
        entity.setWarpGramsPerMeter(dto.getWarpGramsPerMeter());
        entity.setWarpRequirement(dto.getWarpRequirement());
        entity.setTotalIssuedQty(dto.getTotalIssuedQty());
        entity.setBalanceToIssue(dto.getBalanceToIssue());
        entity.setActiveFlag(dto.isActiveFlag());
    }
    
    private void populateYarnIssueFromDTO(SizingYarnIssue entity, SizingYarnIssueDTO dto,String entryIssueNo) {
        entity.setId(null);
        entity.setYarnName(dto.getYarnName());
        entity.setYarnId(dto.getYarnId());
        entity.setMillId(dto.getMillId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setAlternateYarn(dto.getAlternateYarn());
        entity.setAvailableReqQty(dto.getAvailableReqQty());
        entity.setActualQty(dto.getAvailableQty());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConesPerBag(dto.getConesPerBag());
        entity.setConesWeight(dto.getConesWeight());
        entity.setIssueQty(dto.getIssueQty());
        entity.setLotNo(dto.getLotNo());
//        entity.setLotId(dto.getLotId());
        entity.setIssueNo(dto.getIssueNo() != null ? dto.getIssueNo() : entryIssueNo);
        entity.setLotAvailableQty(dto.getLotAvailableQty());
        entity.setLotAvailableBags(dto.getLotAvailableBags());
        entity.setActiveFlag(dto.isActiveFlag());
        entity.setInwardId(dto.getInwardId() != null && dto.getInwardId() != 0 ? dto.getInwardId() : null);
        entity.setInwardTypeId(dto.getInwardTypeId());
        entity.setYarnInwardStockDetailsId(dto.getYarnInwardStockDetailsId() != null && dto.getYarnInwardStockDetailsId() != 0 ? dto.getYarnInwardStockDetailsId() : null);
        entity.setOrderItemId(dto.getOrderItemId() != null && dto.getOrderItemId() != 0 ? dto.getOrderItemId() : null);
        entity.setOutwardTypeId(dto.getOutwardTypeId());
        entity.setActualItemId(dto.getActualItemId());
        entity.setUomId(dto.getUomId());
        entity.setIssuedBags(dto.getIssuedBags());
        entity.setCompanyId(dto.getCompanyId());
        entity.setPerBabyConeWeight(dto.getPerBabyConeWeight());
        entity.setNoOfCones(dto.getNoOfCones());
        entity.setAvailableCones(dto.getAvailableCones());
    }
    
    private void updateYarnIssueFromDTO(SizingYarnIssue entity, SizingYarnIssueDTO dto,String entryIssueNo) {
        entity.setYarnName(dto.getYarnName());
        entity.setYarnId(dto.getYarnId());
        entity.setMillId(dto.getMillId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setAlternateYarn(dto.getAlternateYarn());
        entity.setAvailableReqQty(dto.getAvailableReqQty());
        entity.setActualQty(dto.getAvailableQty());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConesPerBag(dto.getConesPerBag());
        entity.setConesWeight(dto.getConesWeight());
        entity.setIssueQty(dto.getIssueQty());
        entity.setLotNo(dto.getLotNo());
//        entity.setLotId(dto.getLotId());
        entity.setIssueNo(dto.getIssueNo() != null ? dto.getIssueNo() : entryIssueNo);
        entity.setLotAvailableQty(dto.getLotAvailableQty());
        entity.setLotAvailableBags(dto.getLotAvailableBags());
        entity.setActiveFlag(dto.isActiveFlag());
        entity.setInwardId(dto.getInwardId() != null && dto.getInwardId() != 0 ? dto.getInwardId() : null);
        entity.setInwardTypeId(dto.getInwardTypeId());
        entity.setYarnInwardStockDetailsId(dto.getYarnInwardStockDetailsId() != null && dto.getYarnInwardStockDetailsId() != 0 ? dto.getYarnInwardStockDetailsId() : null);
        entity.setOrderItemId(dto.getOrderItemId() != null && dto.getOrderItemId() != 0 ? dto.getOrderItemId() : null);
        entity.setOutwardTypeId(dto.getOutwardTypeId());
        entity.setActualItemId(dto.getActualItemId());
        entity.setUomId(dto.getUomId());
        entity.setIssuedBags(dto.getIssuedBags());
        entity.setCompanyId(dto.getCompanyId());
        entity.setPerBabyConeWeight(dto.getPerBabyConeWeight());
        entity.setNoOfCones(dto.getNoOfCones());
        entity.setAvailableCones(dto.getAvailableCones());
    }

    // ===== YARN OUTWARD MANAGEMENT =====
    
    private void processLotOutward(SizingYarnIssueEntry issue, String referenceType, Long referenceId, Long outwardTypeId) {
        if (issue.getSizingYarnIssues() != null && outwardTypeId != null && !issue.getSizingYarnIssues().isEmpty()) {
            List<YarnOutwardStockDetails> allOutwardDetails = new java.util.ArrayList<>();
            
            Long orderId = issue.getSizingPlanId();
            String orderNo = null;
            String financialYearId = null;
            
            if (orderId != null) {
                Optional<SizingPlan> sizingPlan = sizingPlanRepository.findById(orderId);
                if (sizingPlan.isPresent()) {
                    orderNo = sizingPlan.get().getSizingPlanNo();
                }
            }
            
            if (issue.getSizingYarnIssueDate() != null) {
                financialYearId = getFinancialYearId(issue.getSizingYarnIssueDate());
            }

            // Determine outwardNo
            String firstIssueNo = (issue.getSizingYarnIssues() != null && !issue.getSizingYarnIssues().isEmpty()) 
                ? issue.getSizingYarnIssues().get(0).getIssueNo() : null;
            String baseOutwardNo = (firstIssueNo != null && !firstIssueNo.trim().isEmpty() && !"N/A".equalsIgnoreCase(firstIssueNo.trim()))
                ? firstIssueNo.trim() : ("SYI-" + issue.getId());
            String remark = "Sizing yarn issue - " + issue.getId();

            Optional<YarnOutward> existingByRemark = yarnOutwardRepository.findByRemarks(remark).stream().findFirst();

            YarnOutward yarnOutward;
            String outwardNo;

            if (existingByRemark.isPresent()) {
                yarnOutward = existingByRemark.get();
                outwardNo = yarnOutward.getOutwardNo() != null ? yarnOutward.getOutwardNo() : baseOutwardNo;
            } else {
                yarnOutward = new YarnOutward();
                outwardNo = baseOutwardNo;
                Optional<YarnOutward> existingWithNo = yarnOutwardRepository.findByOutwardNo(outwardNo);
                if (existingWithNo.isPresent() && !existingWithNo.get().getId().equals(yarnOutward.getId())) {
                    outwardNo = baseOutwardNo + "-" + issue.getId();
                }
            }

            yarnOutward.setOutwardTypeId(outwardTypeId);
            yarnOutward.setOutwardNo(outwardNo);
            yarnOutward.setCompanyId(issue.getCompanyId() != null ? issue.getCompanyId() : 1L);
            yarnOutward.setOutwardDate(issue.getSizingYarnIssueDate());
            yarnOutward.setOrderId(orderId);
            yarnOutward.setOrderNo(orderNo);
            yarnOutward.setFinancialYearId(financialYearId);
            yarnOutward.setVendorId(issue.getVendorId());
            yarnOutward.setRemarks(remark);
            yarnOutward.setActiveFlag(true);
            
            // Sync details instead of clearing to preserve IDs
            Map<Long, YarnOutwardStockDetails> existingMapByorderItemId = new HashMap<>();
            Map<Long, YarnOutwardStockDetails> existingMapByStockDetailsId = new HashMap<>();
            
            if (yarnOutward.getYarnOutwardStockDetails() != null) {
                yarnOutward.getYarnOutwardStockDetails().forEach(d -> {
                    if (d.getOrderItemId() != null && d.getOrderItemId() != 0) 
                        existingMapByorderItemId.put(d.getOrderItemId(), d);
                    if (d.getYarnInwardStockDetailsId() != null && d.getYarnInwardStockDetailsId() != 0) 
                        existingMapByStockDetailsId.put(d.getYarnInwardStockDetailsId(), d);
                });
            }

            List<YarnOutwardStockDetails> updatedDetails = new ArrayList<>();
            YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);
            
            List<YarnOutwardStockDetails> currentDetails = savedYarnOutward.getYarnOutwardStockDetails();
            if (currentDetails == null) {
                currentDetails = new ArrayList<>();
                savedYarnOutward.setYarnOutwardStockDetails(currentDetails);
            }
            
            for (SizingYarnIssue sizingYarnIssue : issue.getSizingYarnIssues()) {
                final Long searchItemId = sizingYarnIssue.getActualItemId() != null ?
                    sizingYarnIssue.getActualItemId() : sizingYarnIssue.getYarnId();

                Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();

                if (sizingYarnIssue.getYarnInwardStockDetailsId() != null && sizingYarnIssue.getYarnInwardStockDetailsId() > 0) {
                    inwardDetailOpt = yarnInwardStockDetailsRepository.findById(sizingYarnIssue.getYarnInwardStockDetailsId());
                }

                if (!inwardDetailOpt.isPresent() && sizingYarnIssue.getInwardId() != null && sizingYarnIssue.getInwardId() > 0) {
                    inwardDetailOpt = yarnInwardStockDetailsRepository.findById(sizingYarnIssue.getInwardId())
                        .filter(s -> searchItemId == null || searchItemId.equals(s.getItemId()));
                }

                if (!inwardDetailOpt.isPresent() && sizingYarnIssue.getOrderItemId() != null && sizingYarnIssue.getOrderItemId() > 0) {
                    inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(sizingYarnIssue.getOrderItemId())
                        .filter(s -> searchItemId == null || searchItemId.equals(s.getItemId()));
                }

                if (!inwardDetailOpt.isPresent() && searchItemId != null) {
                    String lotNo = sizingYarnIssue.getLotNo();
                    if (lotNo != null && !lotNo.trim().isEmpty()) {
                        List<YarnInwardStockDetails> byItemAndLot = yarnInwardStockDetailsRepository.findByItemIdAndLotNo(searchItemId, lotNo.trim());
                        if (!byItemAndLot.isEmpty()) {
                            inwardDetailOpt = Optional.of(byItemAndLot.get(0));
                        }
                    }
                    if (!inwardDetailOpt.isPresent()) {
                        List<YarnInwardStockDetails> byItemId = yarnInwardStockDetailsRepository.findByItemId(searchItemId);
                        if (!byItemId.isEmpty()) {
                            inwardDetailOpt = Optional.of(byItemId.get(0));
                        }
                    }
                }
                
                if (inwardDetailOpt.isPresent()) {
                    YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
                    
                    // Match by inward item ID or stock details ID to update existing row
                    YarnOutwardStockDetails outwardDetail = null;
                    if (inwardDetail.getOrderItemId() != null) {
                        outwardDetail = existingMapByorderItemId.remove(inwardDetail.getOrderItemId());
                    }
                    if (outwardDetail == null && inwardDetail.getId() != null) {
                        outwardDetail = existingMapByStockDetailsId.remove(inwardDetail.getId());
                    }
                    
                    // Clean up the other map if we found it in one
                    if (outwardDetail != null) {
                        existingMapByorderItemId.values().remove(outwardDetail);
                        existingMapByStockDetailsId.values().remove(outwardDetail);
                    }

                    boolean isNew = false;
                    if (outwardDetail == null) {
                        outwardDetail = new YarnOutwardStockDetails();
                        outwardDetail.setYarnOutward(savedYarnOutward);
                        isNew = true;
                    }
                    
                    // Persist discovered IDs back to sizingYarnIssue to keep entities in sync
                    sizingYarnIssue.setOrderItemId(inwardDetail.getOrderItemId());
                    sizingYarnIssue.setInwardId(inwardDetail.getId());
                    sizingYarnIssue.setYarnInwardStockDetailsId(inwardDetail.getId());
                    
                    outwardDetail.setInwardId(inwardDetail.getYarnInventory() != null ? 
                        inwardDetail.getYarnInventory().getId() : null);
                    outwardDetail.setOrderItemId(inwardDetail.getOrderItemId());
                    outwardDetail.setYarnInwardStockDetailsId(inwardDetail.getId());
                    outwardDetail.setOutwardTypeId(outwardTypeId);
                    outwardDetail.setActualItemId(inwardDetail.getItemId());
                    outwardDetail.setUomId(inwardDetail.getUomId());
                    outwardDetail.setMillId(inwardDetail.getMillId());
                    outwardDetail.setWarehouseId(inwardDetail.getWarehouseId());
                    outwardDetail.setLotNo(inwardDetail.getLotNo());
                    outwardDetail.setIssuedQty(sizingYarnIssue.getIssueQty() != null ? sizingYarnIssue.getIssueQty() : BigDecimal.ZERO);
                    outwardDetail.setIssuedBags(BigDecimal.valueOf(sizingYarnIssue.getNoOfBags() != null ? 
                        sizingYarnIssue.getNoOfBags() : 0));
                    outwardDetail.setKgsPerBag(sizingYarnIssue.getKgsPerBag() != null ? sizingYarnIssue.getKgsPerBag() : BigDecimal.ZERO);
                    outwardDetail.setConesPerBag(sizingYarnIssue.getConesPerBag());
                    outwardDetail.setConesWeight(sizingYarnIssue.getConesWeight());
                    outwardDetail.setNoOfCones(sizingYarnIssue.getNoOfCones());
                    
                    if (isNew) {
                        currentDetails.add(outwardDetail);
                    }
                    updatedDetails.add(outwardDetail);
                }
            }

            // Remove orphans (items that were in the original collection but not matched in the loop)
            // existingMap now contains only orphans
            Set<Long> orphanIds = new HashSet<>();
            existingMapByorderItemId.values().forEach(d -> { if(d.getId() != null) orphanIds.add(d.getId()); });
            existingMapByStockDetailsId.values().forEach(d -> { if(d.getId() != null) orphanIds.add(d.getId()); });
            
            if (!orphanIds.isEmpty()) {
                currentDetails.removeIf(d -> d.getId() != null && orphanIds.contains(d.getId()));
            }
            
            // Explicitly save at the end to ensure all changes (updates, additions, removals) are persisted
            yarnOutwardRepository.save(savedYarnOutward);

            if (!updatedDetails.isEmpty()) {
                List<YarnOutwardStockDetails> persistedDetails = yarnOutwardStockDetailsRepository.saveAll(updatedDetails);
                yarnOutwardStockDetailsRepository.flush();
                updateAvailableQtyInInwardStockDetails(persistedDetails);
            }
        }
    }

    private void reverseLotReductions(SizingYarnIssueEntry issue, boolean deleteOutward) {
        if (issue == null) return;

        Set<YarnOutward> outwards = new HashSet<>();
        String remark = "Sizing yarn issue - " + issue.getId();
        outwards.addAll(yarnOutwardRepository.findByRemarks(remark));
        outwards.addAll(yarnOutwardRepository.findByRemarksContaining(remark));

        if (issue.getSizingPlanId() != null) {
            outwards.addAll(yarnOutwardRepository.findByOrderIdAndRemarksContaining(issue.getSizingPlanId(), "Sizing yarn issue"));
        }

        if (issue.getSizingYarnIssues() != null && !issue.getSizingYarnIssues().isEmpty()) {
            for (SizingYarnIssue sIssue : issue.getSizingYarnIssues()) {
                if (sIssue.getIssueNo() != null && !sIssue.getIssueNo().trim().isEmpty()) {
                    outwards.addAll(yarnOutwardRepository.findAllByOutwardNo(sIssue.getIssueNo().trim()));
                }
            }
        }

        Set<Long> affectedStockIds = new HashSet<>();

        for (YarnOutward outward : outwards) {
            if (outward.getYarnOutwardStockDetails() != null) {
                for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
                    Long stockId = detail.getYarnInwardStockDetailsId();
                    if (stockId == null || stockId == 0) {
                        if (detail.getOrderItemId() != null && detail.getOrderItemId() != 0) {
                            Optional<YarnInwardStockDetails> opt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(detail.getOrderItemId());
                            if (opt.isPresent()) {
                                stockId = opt.get().getId();
                            }
                        }
                    }
                    if (stockId == null || stockId == 0) {
                        Long itemId = detail.getActualItemId();
                        String lotNo = detail.getLotNo();
                        if (itemId != null && lotNo != null && !lotNo.trim().isEmpty()) {
                            List<YarnInwardStockDetails> stocks = yarnInwardStockDetailsRepository.findByItemIdAndLotNo(itemId, lotNo.trim());
                            if (!stocks.isEmpty()) {
                                stockId = stocks.get(0).getId();
                            }
                        }
                    }
                    if (stockId != null && stockId > 0) {
                        affectedStockIds.add(stockId);
                    }
                }
            }
            if (deleteOutward) {
                List<YarnOutwardStockDetails> outwardDetails = new ArrayList<>(
                        outward.getYarnOutwardStockDetails() != null ? outward.getYarnOutwardStockDetails() : Collections.emptyList());
                if (!outwardDetails.isEmpty()) {
                    if (outward.getYarnOutwardStockDetails() != null) {
                        outward.getYarnOutwardStockDetails().clear();
                    }
                    yarnOutwardRepository.save(outward);
                    yarnOutwardRepository.flush();
                    yarnOutwardStockDetailsRepository.deleteAll(outwardDetails);
                    yarnOutwardStockDetailsRepository.flush();
                }
                yarnOutwardRepository.delete(outward);
            }
        }
        yarnOutwardRepository.flush();

        if (issue.getSizingYarnIssues() != null) {
            for (SizingYarnIssue sIssue : issue.getSizingYarnIssues()) {
                if (sIssue.getYarnInwardStockDetailsId() != null && sIssue.getYarnInwardStockDetailsId() > 0) {
                    affectedStockIds.add(sIssue.getYarnInwardStockDetailsId());
                } else if (sIssue.getInwardId() != null && sIssue.getInwardId() > 0) {
                    affectedStockIds.add(sIssue.getInwardId());
                } else if (sIssue.getOrderItemId() != null && sIssue.getOrderItemId() > 0) {
                    yarnInwardStockDetailsRepository.findFirstByOrderItemId(sIssue.getOrderItemId())
                        .ifPresent(st -> affectedStockIds.add(st.getId()));
                } else if (sIssue.getYarnId() != null && sIssue.getLotNo() != null) {
                    List<YarnInwardStockDetails> stocks = yarnInwardStockDetailsRepository.findByItemIdAndLotNo(
                        sIssue.getActualItemId() != null ? sIssue.getActualItemId() : sIssue.getYarnId(),
                        sIssue.getLotNo()
                    );
                    for (YarnInwardStockDetails st : stocks) {
                        affectedStockIds.add(st.getId());
                    }
                }
            }
        }

        if (!affectedStockIds.isEmpty()) {
            updateAvailableQtyInInwardStockDetailsByIds(affectedStockIds);
        }
    }

    @Transactional
    public void deleteYarnOutwardBySizingPlanId(Long sizingPlanId) {
        if (sizingPlanId == null) return;
        List<YarnOutward> outwards = yarnOutwardRepository.findByOrderIdAndRemarksContaining(sizingPlanId, "Sizing yarn issue");
        if (outwards.isEmpty()) {
            List<YarnOutward> allSizingOutwards = yarnOutwardRepository.findByRemarksContaining("Sizing yarn issue");
            outwards = allSizingOutwards.stream()
                .filter(o -> o.getOrderId() != null && o.getOrderId().equals(sizingPlanId))
                .collect(Collectors.toList());
        }
        Set<Long> affectedStockIds = new HashSet<>();
        for (YarnOutward outward : outwards) {
            if (outward.getYarnOutwardStockDetails() != null) {
                for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
                    if (detail.getYarnInwardStockDetailsId() != null && detail.getYarnInwardStockDetailsId() > 0) {
                        affectedStockIds.add(detail.getYarnInwardStockDetailsId());
                    }
                }
                List<YarnOutwardStockDetails> details = new ArrayList<>(outward.getYarnOutwardStockDetails());
                outward.getYarnOutwardStockDetails().clear();
                yarnOutwardRepository.save(outward);
                yarnOutwardRepository.flush();
                yarnOutwardStockDetailsRepository.deleteAll(details);
                yarnOutwardStockDetailsRepository.flush();
            }
            yarnOutwardRepository.delete(outward);
        }
        yarnOutwardRepository.flush();
        if (!affectedStockIds.isEmpty()) {
            updateAvailableQtyInInwardStockDetailsByIds(affectedStockIds);
        }
    }

    private void updateAvailableQtyInInwardStockDetailsByIds(java.util.Collection<Long> stockDetailsIds) {
        if (stockDetailsIds == null || stockDetailsIds.isEmpty()) {
            return;
        }
        for (Long stockId : stockDetailsIds) {
            if (stockId == null || stockId == 0) continue;
            Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(stockId);
            if (!inwardDetailOpt.isPresent()) {
                continue;
            }

            YarnInwardStockDetails detail = inwardDetailOpt.get();
            BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
            BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;

            BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockId);
            BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockId);
            BigDecimal totalIssuedCones = yarnOutwardStockDetailsRepository.getTotalIssuedConesByYarnInwardStockDetailsId(stockId);

            BigDecimal conesPerBag = detail.getConesPerBag() != null ? detail.getConesPerBag() : BigDecimal.ZERO;
            BigDecimal totalInwardCones = inwardBags.multiply(conesPerBag);

            if (totalIssuedCones == null || totalIssuedCones.compareTo(BigDecimal.ZERO) == 0) {
                totalIssuedCones = (totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO).multiply(conesPerBag);
            }

            BigDecimal newAvailableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
            BigDecimal newAvailableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
            BigDecimal newAvailableCones = totalInwardCones.subtract(totalIssuedCones != null ? totalIssuedCones : BigDecimal.ZERO);

            detail.setAvailableQty(newAvailableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableQty);
            detail.setAvailableBags(newAvailableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableBags);
            detail.setAvailableCones(newAvailableCones.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableCones);

            yarnInwardStockDetailsRepository.save(detail);
        }
        yarnInwardStockDetailsRepository.flush();
    }

    private String generateOutwardNo() {
        return "OUT-" + System.currentTimeMillis();
    }

    // ===== VALIDATION =====
    
    private void validateSizingYarnIssue(SizingYarnIssueEntryDTO dto) {
        if (dto.getSizingYarnIssue() != null) {
            for (SizingYarnIssueDTO sizingYarnIssue : dto.getSizingYarnIssue()) {
                List<YarnInwardStockDetails> inwardDetails = 
                    yarnInwardStockDetailsRepository.findByItemId(sizingYarnIssue.getYarnId());
                
                if (inwardDetails.isEmpty()) {
                    throw new EntityNotFoundException(
                        "No yarn inward stock details found for yarn ID: " + sizingYarnIssue.getYarnId());
                }
                
                BigDecimal totalAvailableQty = BigDecimal.ZERO;
                for (YarnInwardStockDetails detail : inwardDetails) {
                    if (detail.getAvailableQty() != null) {
                        totalAvailableQty = totalAvailableQty.add(detail.getAvailableQty());
                    }
                }
                
                BigDecimal requestedQty = sizingYarnIssue.getIssueQty() != null ? sizingYarnIssue.getIssueQty() : BigDecimal.ZERO;
                if (totalAvailableQty.compareTo(requestedQty) < 0) {
                    throw new IllegalArgumentException(
                        String.format("Insufficient available quantity for yarn ID %s (Available: %s, Requested: %s)",
                            sizingYarnIssue.getYarnId(), totalAvailableQty, requestedQty));
                }
            }
        }
    }

    private PaginatedResponse<SizingYarnIssueEntryDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit,
            Function<List<Long>, List<SizingYarnIssueEntry>> fetcher) {
        long off = offset != null ? Math.max(0, offset) : 0;
        long lim = (limit != null && limit > 0) ? limit : 10;
        long total = allIds.size();
        int start = (int) Math.min(off, total);
        if (start >= total) {
            return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, Collections.emptyList());
        }
        int end = (int) Math.min(off + lim, total);
        List<Long> pageIds = allIds.subList(start, end);
        List<SizingYarnIssueEntry> entries = pageIds.isEmpty() ? Collections.emptyList() : fetcher.apply(pageIds);
        List<SizingYarnIssueEntryDTO> dtos = entries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                total,
                end < total,
                off > 0,
                off,
                lim,
                dtos
        );
    }

    // ===== CONVERSION METHODS =====
    
    private SizingYarnIssueEntryDTO convertToDTO(SizingYarnIssueEntry entity) {
        SizingYarnIssueEntryDTO dto = new SizingYarnIssueEntryDTO();
        dto.setId(entity.getId());
        dto.setVendorId(entity.getVendorId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setOutwardTypeId(entity.getOutwardTypeId());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setTransportationDetails(entity.getTransportationDetails());
        dto.setTermsConditionsId(entity.getTermsConditionsId());
        dto.setFabricDetails(entity.getFabricDetails());
        dto.setSizingYarnIssueDate(entity.getSizingYarnIssueDate());
        dto.setYarnIssueChallanNo(entity.getYarnIssueChallanNo());
        dto.setActiveFlag(entity.isActiveFlag());
        dto.setRemark(entity.getRemark());
        dto.setVehicleNo(entity.getVehicleNo());
        dto.setGrossWt(entity.getGrossWt());
        if (entity.getSizingYarnIssues() != null && !entity.getSizingYarnIssues().isEmpty()) {
            entity.getSizingYarnIssues().stream().findFirst().ifPresent(issue -> dto.setIssueNo(issue.getIssueNo()));
        }
        if (entity.getRequirements() != null) {
            List<SizingYarnRequirementDTO> requirementDTOs = entity.getRequirements().stream()
                    .map(this::convertRequirementToDTO)
                    .collect(Collectors.toList());
            dto.setRequirements(requirementDTOs);
        }
        
        if (entity.getSizingYarnIssues() != null) {
            List<SizingYarnIssueDTO> sizingYarnIssueDTOs = entity.getSizingYarnIssues().stream()
                    .map(this::convertYarnIssueToDTO)
                    .collect(Collectors.toList());
            dto.setSizingYarnIssue(sizingYarnIssueDTOs);
        }


        return dto;
    }

    private SizingYarnRequirementDTO convertRequirementToDTO(SizingYarnRequirement requirement) {
        SizingYarnRequirementDTO dto = new SizingYarnRequirementDTO();
        dto.setId(requirement.getId());
        dto.setFabricDetails(requirement.getFabricDetails());
        dto.setYarnName(requirement.getYarnName());
        dto.setActualYarn(requirement.getActualYarn());
        dto.setActualYarnId(requirement.getActualYarnId());
        dto.setYarnId(requirement.getYarnId());
        dto.setWarpMeters(requirement.getWarpMeters());
        dto.setWarpGramsPerMeter(requirement.getWarpGramsPerMeter());
        dto.setWarpRequirement(requirement.getWarpRequirement());
        dto.setTotalIssuedQty(requirement.getTotalIssuedQty());
        dto.setBalanceToIssue(requirement.getBalanceToIssue());
        dto.setActiveFlag(requirement.isActiveFlag());

        return dto;
    }

    private SizingYarnIssueDTO convertYarnIssueToDTO(SizingYarnIssue sizingYarnIssue) {
        SizingYarnIssueDTO dto = new SizingYarnIssueDTO();
        dto.setId(sizingYarnIssue.getId());
        dto.setYarnName(sizingYarnIssue.getYarnName());
        dto.setYarnId(sizingYarnIssue.getYarnId());
        dto.setMillId(sizingYarnIssue.getMillId());
        dto.setWarehouseId(sizingYarnIssue.getWarehouseId());
        dto.setAlternateYarn(sizingYarnIssue.getAlternateYarn());
        dto.setAvailableReqQty(sizingYarnIssue.getAvailableReqQty());
        dto.setAvailableQty(sizingYarnIssue.getAvailableQty());
        dto.setNoOfBags(sizingYarnIssue.getNoOfBags());
        dto.setKgsPerBag(sizingYarnIssue.getKgsPerBag());
        dto.setConesPerBag(sizingYarnIssue.getConesPerBag());
        dto.setConesWeight(sizingYarnIssue.getConesWeight());
        dto.setIssueQty(sizingYarnIssue.getIssueQty());
        dto.setLotNo(sizingYarnIssue.getLotNo());
//        dto.setLotId(sizingYarnIssue.getLotId());
        dto.setIssueNo(sizingYarnIssue.getIssueNo());
        dto.setLotAvailableQty(sizingYarnIssue.getLotAvailableQty());
        dto.setLotAvailableBags(sizingYarnIssue.getLotAvailableBags());
        dto.setActiveFlag(sizingYarnIssue.isActiveFlag());
        dto.setSizingYarnIssueEntryId(sizingYarnIssue.getSizingYarnIssueEntry() != null ? sizingYarnIssue.getSizingYarnIssueEntry().getId() : null);
        dto.setInwardId(sizingYarnIssue.getInwardId());
        dto.setInwardTypeId(sizingYarnIssue.getInwardTypeId());
        dto.setYarnInwardStockDetailsId(sizingYarnIssue.getYarnInwardStockDetailsId());
        dto.setOrderItemId(sizingYarnIssue.getOrderItemId());
        dto.setOutwardTypeId(sizingYarnIssue.getOutwardTypeId());
        dto.setActualItemId(sizingYarnIssue.getActualItemId());
        dto.setUomId(sizingYarnIssue.getUomId());
        dto.setIssuedBags(sizingYarnIssue.getIssuedBags());
        dto.setCompanyId(sizingYarnIssue.getCompanyId());
        dto.setPerBabyConeWeight(sizingYarnIssue.getPerBabyConeWeight());

        YarnInwardStockDetails stockDetails = null;

        if (sizingYarnIssue.getYarnInwardStockDetailsId() != null) {
            Optional<YarnInwardStockDetails> opt = yarnInwardStockDetailsRepository.findById(sizingYarnIssue.getYarnInwardStockDetailsId());
            if (opt.isPresent()) {
                stockDetails = opt.get();
            }
        }
        if (stockDetails == null && sizingYarnIssue.getInwardId() != null) {
            Optional<YarnInwardStockDetails> opt = yarnInwardStockDetailsRepository.findById(sizingYarnIssue.getInwardId());
            if (opt.isPresent()) {
                stockDetails = opt.get();
            }
        }

        if (stockDetails != null) {
            if (stockDetails.getYarnInventory() != null && stockDetails.getYarnInventory().getInwardNo() != null) {
                dto.setInwardNo(stockDetails.getYarnInventory().getInwardNo());
            }
            if (stockDetails.getMillInvNo() != null && !stockDetails.getMillInvNo().trim().isEmpty()) {
                dto.setMillInvNo(stockDetails.getMillInvNo().trim());
            }
            if (stockDetails.getMillDate() != null) {
                dto.setMillDate(stockDetails.getMillDate().toString());
            }
        }

        if (dto.getInwardNo() == null || dto.getMillInvNo() == null || dto.getMillDate() == null) {
            Optional<PurchaseInward> piOpt = Optional.empty();

            if (stockDetails != null && stockDetails.getYarnInventory() != null && stockDetails.getYarnInventory().getInwardId() != null) {
                piOpt = purchaseInwardRepository.findById(stockDetails.getYarnInventory().getInwardId());
            }
            if (!piOpt.isPresent() && sizingYarnIssue.getInwardId() != null) {
                piOpt = purchaseInwardRepository.findById(sizingYarnIssue.getInwardId());
            }
            if (!piOpt.isPresent() && stockDetails != null && stockDetails.getInwardId() != null) {
                piOpt = purchaseInwardRepository.findById(stockDetails.getInwardId());
            }
            if (!piOpt.isPresent() && dto.getInwardNo() != null && !dto.getInwardNo().trim().isEmpty()) {
                piOpt = purchaseInwardRepository.findFirstByInwardNo(dto.getInwardNo().trim());
            }

            if (piOpt.isPresent()) {
                PurchaseInward pi = piOpt.get();
                if (dto.getInwardNo() == null && pi.getInwardNo() != null) {
                    dto.setInwardNo(pi.getInwardNo());
                }
                if (dto.getMillInvNo() == null) {
                    String invNo = pi.getMillInvNo() != null && !pi.getMillInvNo().trim().isEmpty() 
                        ? pi.getMillInvNo().trim() 
                        : pi.getInvoiceNumber();
                    dto.setMillInvNo(invNo);
                }
                if (dto.getMillDate() == null) {
                    LocalDate invDt = pi.getMillDate() != null ? pi.getMillDate() : pi.getInvoiceDate();
                    if (invDt != null) {
                        dto.setMillDate(invDt.toString());
                    }
                }
            }
        }

        return dto;
    }
    
     @Transactional(readOnly = true)
    public List<YarnInwardStockDetailsDto> getYarnInwardStockDetailsByItemIds(
            List<Long> itemIds,
            Long outwardTypeId,
            Long companyId,
            Long purchaseOrderId,
            Long poTypeId) {
        List<YarnInwardStockDetails> details = new java.util.ArrayList<>();
        
        if (itemIds != null && !itemIds.isEmpty()) {
            for (Long itemId : itemIds) {
                details.addAll(yarnInwardStockDetailsRepository.findByItemId(itemId));
            }
        } else if (purchaseOrderId != null) {
            if (poTypeId != null) {
                details.addAll(yarnInwardStockDetailsRepository.findByPurchaseOrderIdAndPoTypeId(purchaseOrderId, poTypeId));
            } else {
                details.addAll(yarnInwardStockDetailsRepository.findByPurchaseOrderId(purchaseOrderId));
            }
        }
        
        return details.stream()
                .filter(detail -> companyId == null || (detail.getYarnInventory() != null && companyId.equals(detail.getYarnInventory().getCompanyId())))
                .filter(detail -> purchaseOrderId == null || (detail.getYarnInventory() != null && purchaseOrderId.equals(detail.getYarnInventory().getOrderId())))
                .filter(detail -> poTypeId == null || (detail.getYarnInventory() != null && poTypeId.equals(detail.getYarnInventory().getInwardTypeId())))
                .map(detail -> {
                    YarnInwardStockDetailsDto dto = new YarnInwardStockDetailsDto(detail);

                    // Recalculate real-time availableQty, availableBags, and availableCones directly from DB outward issued totals
                    BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
                    BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;
                    BigDecimal conesPerBag = detail.getConesPerBag() != null ? detail.getConesPerBag() : BigDecimal.ZERO;
                    BigDecimal totalInwardCones = inwardBags.multiply(conesPerBag);
                    
                    BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
                    BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());
                    BigDecimal totalIssuedCones = yarnOutwardStockDetailsRepository.getTotalIssuedConesByYarnInwardStockDetailsId(detail.getId());

                    if (totalIssuedCones == null || totalIssuedCones.compareTo(BigDecimal.ZERO) == 0) {
                        totalIssuedCones = (totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO).multiply(conesPerBag);
                    }

                    BigDecimal realAvailableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
                    BigDecimal realAvailableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
                    BigDecimal realAvailableCones = totalInwardCones.subtract(totalIssuedCones != null ? totalIssuedCones : BigDecimal.ZERO);

                    dto.setAvailableQty(realAvailableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : realAvailableQty);
                    dto.setAvailableBags(realAvailableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : realAvailableBags);
                    dto.setAvailableCones(realAvailableCones.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : realAvailableCones);

                    Long purchaseInwardId = dto.getInwardId();
                    Optional<PurchaseInward> piOpt = Optional.empty();
                    if (purchaseInwardId != null) {
                        piOpt = purchaseInwardRepository.findById(purchaseInwardId);
                    }
                    if (!piOpt.isPresent() && detail.getInwardId() != null) {
                        piOpt = purchaseInwardRepository.findById(detail.getInwardId());
                    }
                    if (!piOpt.isPresent() && dto.getInwardNo() != null && !dto.getInwardNo().trim().isEmpty()) {
                        piOpt = purchaseInwardRepository.findFirstByInwardNo(dto.getInwardNo().trim());
                    }
                    piOpt.ifPresent(pi -> {
                        if (dto.getMillInvNo() == null) {
                            String invNo = pi.getMillInvNo() != null && !pi.getMillInvNo().trim().isEmpty() 
                                ? pi.getMillInvNo().trim() 
                                : pi.getInvoiceNumber();
                            dto.setMillInvNo(invNo);
                        }
                        if (dto.getMillDate() == null) {
                            LocalDate invDt = pi.getMillDate() != null ? pi.getMillDate() : pi.getInvoiceDate();
                            dto.setMillDate(invDt);
                        }
                    });
                    List<YarnOutwardStockDetails> outwardDetails = yarnOutwardStockDetailsRepository.findByOrderItemId(detail.getOrderItemId());
                    if (outwardTypeId != null) {
                        dto.setOutwardTypeId(outwardTypeId);
                        outwardDetails.stream()
                            .filter(outward -> outwardTypeId.equals(outward.getOutwardTypeId()))
                            .findFirst()
                            .ifPresent(outward -> dto.setOutwardTypeId(outward.getOutwardTypeId()));
                    } else {
                        outwardDetails.stream()
                            .findFirst()
                            .ifPresent(outward -> dto.setOutwardTypeId(outward.getOutwardTypeId()));
                    }
                    return dto;
                })
                .filter(dto -> dto.getAvailableQty() != null && dto.getAvailableQty().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }
    // ===== HELPER METHODS FOR QUANTITY AND OUTWARD MANAGEMENT =====
    
    private BigDecimal calculateTotalIssuedQuantity(Long orderItemId) {
        if (orderItemId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(orderItemId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    private BigDecimal calculateTotalIssuedBags(Long orderItemId) {
        if (orderItemId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(orderItemId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    private String generateYarnOutwardNo(SizingYarnIssueEntry sizingYarnIssueEntry) {
        return "SYO-" + LocalDate.now().getYear() + "-" + sizingYarnIssueEntry.getId();
    }

    private String getFinancialYearId(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int startYear;
        int endYear;
        if (month >= 4) {
            startYear = year;
            endYear = year + 1;
        } else {
            startYear = year - 1;
            endYear = year;
        }
        String endYearLastTwo = String.format("%02d", endYear % 100);
        return startYear + "-" + endYearLastTwo;
    }

    private String getShortFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        int startYear = month >= 4 ? year : year - 1;
        int endYear = startYear + 1;
        return String.format("%02d-%02d", startYear % 100, endYear % 100);
    }

    private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
        Set<Long> stockDetailsIds = new HashSet<>();

        for (YarnOutwardStockDetails outwardDetail : outwardDetails) {
            Long stockId = outwardDetail.getYarnInwardStockDetailsId();
            if (stockId == null || stockId == 0) {
                if (outwardDetail.getOrderItemId() != null && outwardDetail.getOrderItemId() != 0) {
                    Optional<YarnInwardStockDetails> opt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(outwardDetail.getOrderItemId());
                    if (opt.isPresent()) {
                        stockId = opt.get().getId();
                        outwardDetail.setYarnInwardStockDetailsId(stockId);
                    }
                }
            }

            if (stockId != null && stockId != 0) {
                stockDetailsIds.add(stockId);
            }
        }

        for (Long stockId : stockDetailsIds) {
            Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(stockId);
            if (!inwardDetailOpt.isPresent()) {
                continue;
            }

            YarnInwardStockDetails detail = inwardDetailOpt.get();
            BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
            BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;

            BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockId);
            BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockId);
            BigDecimal totalIssuedCones = yarnOutwardStockDetailsRepository.getTotalIssuedConesByYarnInwardStockDetailsId(stockId);

            BigDecimal conesPerBag = detail.getConesPerBag() != null ? detail.getConesPerBag() : BigDecimal.ZERO;
            BigDecimal totalInwardCones = inwardBags.multiply(conesPerBag);

            if (totalIssuedCones == null || totalIssuedCones.compareTo(BigDecimal.ZERO) == 0) {
                totalIssuedCones = (totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO).multiply(conesPerBag);
            }

            BigDecimal newAvailableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
            BigDecimal newAvailableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
            BigDecimal newAvailableCones = totalInwardCones.subtract(totalIssuedCones != null ? totalIssuedCones : BigDecimal.ZERO);

            detail.setAvailableQty(newAvailableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableQty);
            detail.setAvailableBags(newAvailableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableBags);
            detail.setAvailableCones(newAvailableCones.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newAvailableCones);

            yarnInwardStockDetailsRepository.save(detail);
        }
        yarnInwardStockDetailsRepository.flush();
    }
    
    private void populateInwardDetailsFromStockDetails(YarnOutwardStockDetails outwardDetail, Long yarnorderItemId) {
        if (yarnorderItemId != null) {
            Optional<YarnInwardStockDetails> inwardStockDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnorderItemId);
            if (inwardStockDetail.isPresent()) {
                YarnInwardStockDetails details = inwardStockDetail.get();
                outwardDetail.setYarnInwardStockDetailsId(details.getId());
                if (details.getYarnInventory() != null) {
                    outwardDetail.setInwardId(details.getYarnInventory().getId());
                }
                if (details.getLotNo() != null) {
                    outwardDetail.setLotNo(details.getLotNo());
                }
            }
        }
    }
    
    private void populateLotNoFromStockDetails(YarnOutwardStockDetails outwardDetail, Long yarnorderItemId) {
        if (yarnorderItemId != null) {
            Optional<YarnInwardStockDetails> inwardStockDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnorderItemId);
            if (inwardStockDetail.isPresent()) {
                YarnInwardStockDetails details = inwardStockDetail.get();
                outwardDetail.setYarnInwardStockDetailsId(details.getId());
                if (details.getLotNo() != null) {
                    outwardDetail.setLotNo(details.getLotNo());
                }
            }
        }
    }

     private void createYarnOutwardForEntry(SizingYarnIssueEntry sizingYarnIssueEntry, SizingYarnIssueEntryDTO dto) {
        if (dto.getSizingYarnIssue() == null || dto.getSizingYarnIssue().isEmpty()) {
            return;
        }

        Long outwardTypeId = 53L;
        Long companyId = 1L;

        YarnOutward yarnOutward = new YarnOutward();
        yarnOutward.setOutwardTypeId(outwardTypeId);
        String outwardNo = generateYarnOutwardNo(sizingYarnIssueEntry);
        if (sizingYarnIssueEntry.getSizingYarnIssues() != null && !sizingYarnIssueEntry.getSizingYarnIssues().isEmpty()) {
            String issueNo = sizingYarnIssueEntry.getSizingYarnIssues().get(0).getIssueNo();
            if (issueNo != null) {
                outwardNo = issueNo;
            }
        }
        yarnOutward.setOutwardNo(outwardNo);
        yarnOutward.setCompanyId(companyId);

        LocalDate outwardDate = dto.getSizingYarnIssueDate() != null ? 
            dto.getSizingYarnIssueDate() : LocalDate.now();
        yarnOutward.setOutwardDate(outwardDate);

        yarnOutward.setFinancialYearId(getFinancialYearId(outwardDate));
        yarnOutward.setOrderId(dto.getSizingPlanId());
        
        if (dto.getSizingPlanId() != null) {
            sizingPlanRepository.findById(dto.getSizingPlanId())
                .ifPresent(plan -> yarnOutward.setOrderNo(plan.getSizingPlanNo()));
        }
        
        yarnOutward.setVendorId(dto.getVendorId());
        yarnOutward.setRemarks(null);
        yarnOutward.setActiveFlag(true);

        YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);

        List<YarnOutwardStockDetails> outwardDetailsToSave = new java.util.ArrayList<>();

        for (SizingYarnIssueDTO yarnIssue : dto.getSizingYarnIssue()) {
            YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
            outwardDetail.setYarnOutward(savedYarnOutward);
            outwardDetail.setOrderItemId(yarnIssue.getOrderItemId());
            outwardDetail.setOutwardTypeId(yarnIssue.getOutwardTypeId());
            outwardDetail.setActualItemId(yarnIssue.getActualItemId());
            outwardDetail.setUomId(yarnIssue.getUomId());
            outwardDetail.setMillId(yarnIssue.getMillId());
            outwardDetail.setWarehouseId(yarnIssue.getWarehouseId());
            outwardDetail.setIssuedQty(yarnIssue.getIssueQty());
            outwardDetail.setIssuedBags(yarnIssue.getIssuedBags());
            outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag());
            outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
            outwardDetail.setConesWeight(yarnIssue.getConesWeight());

            if (yarnIssue.getInwardId() != null) {
                outwardDetail.setInwardId(yarnIssue.getInwardId());
                populateLotNoFromStockDetails(outwardDetail, yarnIssue.getOrderItemId());
            } else {
                populateInwardDetailsFromStockDetails(outwardDetail, yarnIssue.getOrderItemId());
            }

            outwardDetailsToSave.add(outwardDetail);
        }

        if (dto.getYarnOutwardStockDetails() != null) {
            for (YarnOutwardStockDetailsDTO outwardDTO : dto.getYarnOutwardStockDetails()) {
                YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
                outwardDetail.setYarnOutward(savedYarnOutward);
                outwardDetail.setOrderItemId(outwardDTO.getOrderItemId());
                outwardDetail.setOutwardTypeId(outwardDTO.getOutwardTypeId());
                outwardDetail.setActualItemId(outwardDTO.getActualItemId());
                outwardDetail.setUomId(outwardDTO.getUomId());
                outwardDetail.setMillId(outwardDTO.getMillId());
                outwardDetail.setWarehouseId(outwardDTO.getWarehouseId());
                outwardDetail.setIssuedQty(outwardDTO.getIssuedQty());
                outwardDetail.setIssuedBags(outwardDTO.getIssuedBags());
                outwardDetail.setKgsPerBag(outwardDTO.getKgsPerBag());
                outwardDetail.setConesPerBag(outwardDTO.getConesPerBag());
                outwardDetail.setConesWeight(outwardDTO.getConesWeight());

                if (outwardDTO.getInwardId() != null) {
                    outwardDetail.setInwardId(outwardDTO.getInwardId());
                    populateLotNoFromStockDetails(outwardDetail, outwardDTO.getOrderItemId());
                } else {
                    populateInwardDetailsFromStockDetails(outwardDetail, outwardDTO.getOrderItemId());
                }

                outwardDetailsToSave.add(outwardDetail);
            }
        }

        if (!outwardDetailsToSave.isEmpty()) {
            List<YarnOutwardStockDetails> savedOutwardDetails = yarnOutwardStockDetailsRepository.saveAll(outwardDetailsToSave);
            savedYarnOutward.setYarnOutwardStockDetails(savedOutwardDetails);
            yarnOutwardRepository.save(savedYarnOutward);
            
            updateAvailableQtyInInwardStockDetails(savedOutwardDetails);
        }
    }

    private Long normalizeId(Long id) {
        return (id != null && id > 0) ? id : null;
    }
}