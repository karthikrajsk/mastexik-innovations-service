// package com.tech3.erp.service;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.WeavingYarnIssueDTO;
// import com.tech3.erp.dto.WeavingYarnRequirementDTO;
// import com.tech3.erp.dto.YarnIssueDTO;
// import com.tech3.erp.entity.CodesDetail;
// import com.tech3.erp.entity.WeavingContract;
// import com.tech3.erp.entity.WeavingWorkOrder;
// import com.tech3.erp.entity.WeavingYarnIssue;
// import com.tech3.erp.entity.WeavingYarnRequirement;
// import com.tech3.erp.entity.YarnIssue;
// import com.tech3.erp.entity.YarnInventory;
// import com.tech3.erp.entity.YarnInwardStockDetails;
// import com.tech3.erp.entity.YarnOutward;
// import com.tech3.erp.entity.YarnOutwardStockDetails;
// import com.tech3.erp.entity.YarnMaster;
// import com.tech3.erp.entity.Company;
// import com.tech3.erp.repository.CodesDetailRepository;
// import com.tech3.erp.repository.CompanyRepository;
// import com.tech3.erp.repository.WeavingContractRepository;
// import com.tech3.erp.repository.WeavingWorkOrderRepository;
// import com.tech3.erp.repository.WeavingYarnIssueRepository;
// import com.tech3.erp.repository.WeavingYarnOutwardDetailRepository;
// import com.tech3.erp.repository.WeftInwardRepository;
// import com.tech3.erp.repository.YarnInventoryRepository;
// import com.tech3.erp.repository.YarnIssueRepository;
// import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
// import com.tech3.erp.repository.YarnOutwardRepository;
// import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
// import com.tech3.erp.repository.YarnMasterRepository;
// import com.tech3.erp.entity.WeftInward;
// import com.tech3.erp.entity.WeftInwardItem;

// import jakarta.persistence.EntityNotFoundException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.util.StringUtils;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.IdentityHashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.Optional;
// import java.util.Set;
// import java.util.stream.Collectors;


// @Service
// @Transactional
// public class WeavingYarnIssueService {

//     @Autowired
//     private WeavingYarnIssueRepository weavingYarnIssueRepository;
    
//     @Autowired
//     private WeavingContractRepository weavingContractRepository;
    
//     @Autowired
//     private WeavingWorkOrderRepository weavingWorkOrderRepository;
    
//     @Autowired
//     private WeftInwardRepository weftInwardRepository;
    
//     @Autowired
//     private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
//     @Autowired
//     private YarnOutwardRepository yarnOutwardRepository;
    
//     @Autowired
//     private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
    
//     @Autowired
//     private YarnMasterRepository yarnMasterRepository;
    
//     @Autowired
//     private WeavingYarnOutwardDetailRepository weavingYarnOutwardDetailRepository;
    
//     @Autowired
//     private YarnInventoryRepository yarnInventoryRepository;

//     @Autowired
//     private YarnIssueRepository yarnIssueRepository;

//     @Autowired
//     private CodesDetailRepository codesDetailRepository;

//     @Autowired
//     private ReferenceValidationService referenceValidationService;
    
//     @Autowired
//     private CompanyRepository companyRepository;

//     @Autowired
//     @Lazy
//     private WeftInwardService weftInwardService;

//     @Transactional(readOnly = true)
//     public List<WeavingYarnIssueDTO> getAllWeavingYarnIssues() {
//         return weavingYarnIssueRepository.findAll().stream()
//         		.sorted(Comparator.comparing(WeavingYarnIssue::getId).reversed())
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }
    
//     @Transactional(readOnly = true)
//     public PaginatedResponse<WeavingYarnIssueDTO> getAllWeavingYarnIssuesWithPagination(Long offset, Long limit) {
//         long finalLimit = (limit != null && limit > 0) ? limit : 10L;
//         long finalOffset = (offset != null && offset > 0) ? offset : 0L;
//         Page<WeavingYarnIssue> page = weavingYarnIssueRepository.findAll(
//                 PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
//         );
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }
    
//     @Transactional(readOnly = true)
//     public List<WeavingYarnIssueDTO> getAllWeavingYarnIssuesByCompany(Long companyId) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return weavingYarnIssueRepository.findByTradingCompanyIdOrderByIdDesc(companyId).stream()
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }
    
//     @Transactional(readOnly = true)
//     public PaginatedResponse<WeavingYarnIssueDTO> getAllWeavingYarnIssuesByCompanyWithPagination(Long companyId, Long offset, Long limit) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         long finalLimit = (limit != null && limit > 0) ? limit : 10L;
//         long finalOffset = (offset != null && offset > 0) ? offset : 0L;
//         Page<WeavingYarnIssue> page = weavingYarnIssueRepository.findByTradingCompanyId(
//                 companyId,
//                 PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
//         );
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }
    
//     @Transactional(readOnly = true)
//     public WeavingYarnIssueDTO getWeavingYarnIssueByCompany(Long companyId, Long id) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         WeavingYarnIssue issue = weavingYarnIssueRepository.findByIdAndTradingCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
//         return convertToDTO(issue);
//     }

//     public WeavingYarnIssueDTO createWeavingYarnIssue(WeavingYarnIssueDTO dto) {
//         // Clear IDs for create operation to avoid detached entity issues if IDs are passed by mistake
//         dto.setId(null);
//         if (dto.getRequirements() != null) {
//             dto.getRequirements().forEach(req -> {
//                 req.setId(null);
//                 if (req.getYarnIssues() != null) {
//                     req.getYarnIssues().forEach(yi -> yi.setId(null));
//                 }
//             });
//         }
        
//         validateYarnIssue(dto);
//         requireWeavingContractReference(dto.getWeavingContractId());
        
//         WeavingYarnIssue issue = convertToEntity(dto);
        
//         WeavingYarnIssue savedIssue = weavingYarnIssueRepository.save(issue);

//         if (StringUtils.isEmpty(savedIssue.getYarnIssueChallanNo())) {
//             savedIssue.setYarnIssueChallanNo(generateYarnIssueChallanNo(savedIssue.getYarnIssueDate(), savedIssue.getId(), savedIssue.getTradingCompanyId()));
//             savedIssue = weavingYarnIssueRepository.save(savedIssue);
//         }

//         syncWeftInwardEntry(savedIssue);
//         processLotOutward(savedIssue, "WEAVING_CONTRACT", savedIssue.getId());

//         return convertToDTO(savedIssue);
//     }

//     private String generateYarnIssueChallanNo(LocalDate issueDate, Long id, Long companyId) {
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
//         if (issueDate == null) issueDate = LocalDate.now();
//         String fy = getFinancialYearString(issueDate);
//         String prefix = cmpPrefix + "WYI/" + fy + "/";
//         String sequence = String.format("%04d", id);
//         return prefix + sequence;
//     }

//     private String getFinancialYearString(LocalDate date) {
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int fyStartYear = (month >= 4) ? year : year - 1;
//         int fy1 = fyStartYear % 100;
//         int fy2 = (fyStartYear + 1) % 100;
//         return String.format("%02d-%02d", fy1, fy2);
//     }

//     public WeavingYarnIssueDTO updateWeavingYarnIssue(Long id, WeavingYarnIssueDTO dto) {
//         validateYarnIssue(dto);
        
//         WeavingYarnIssue existingIssue = weavingYarnIssueRepository.findById(id)
//             .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        
//         reverseLotReductions(existingIssue, true);
//         updateIssueFromDTO(existingIssue, dto);
        
//         WeavingYarnIssue savedIssue = weavingYarnIssueRepository.save(existingIssue);
//         syncWeftInwardEntry(savedIssue);
//         processLotOutward(savedIssue, "WEAVING_CONTRACT", savedIssue.getId());
//         return convertToDTO(savedIssue);
//     }
    
//     public WeavingYarnIssueDTO updateWeavingYarnIssueByCompany(Long companyId, Long id, WeavingYarnIssueDTO dto) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         validateYarnIssue(dto);
//         WeavingYarnIssue existingIssue = weavingYarnIssueRepository.findByIdAndTradingCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        
//         reverseLotReductions(existingIssue, true);
//         updateIssueFromDTO(existingIssue, dto);
        
//         WeavingYarnIssue savedIssue = weavingYarnIssueRepository.save(existingIssue);
//         syncWeftInwardEntry(savedIssue);
//         processLotOutward(savedIssue, "WEAVING_CONTRACT", savedIssue.getId());
//         return convertToDTO(savedIssue);
//     }


//     private void updateIssueFromDTO(WeavingYarnIssue existingIssue, WeavingYarnIssueDTO dto) {
//         requireWeavingContractReference(dto.getWeavingContractId());
//         existingIssue.setVendorId(dto.getVendorId());
//         existingIssue.setWeavingContractId(dto.getWeavingContractId());
//         existingIssue.setWeavingContractNo(dto.getWeavingContractNo());
//         existingIssue.setWeavingJobWorkOrderId(dto.getWeavingJobWorkOrderId());
//         existingIssue.setTransportationDetails(dto.getTransportationDetails());
//         existingIssue.setTransportId(dto.getTransportId());
//         existingIssue.setTermsConditionsId(dto.getTermsConditionsId());
//         existingIssue.setFabricDetails(dto.getFabricDetails());
//         existingIssue.setYarnIssueDate(dto.getYarnIssueDate());
//         existingIssue.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
//         existingIssue.setActiveFlag(dto.isActiveFlag());
//         existingIssue.setOrderTypeId(dto.getOrderTypeId());
//         existingIssue.setTradingCompanyId(dto.getTradingCompanyId());
//         existingIssue.setFactoryCompanyId(dto.getFactoryCompanyId());
//         existingIssue.setRemarks(dto.getRemarks());
//         existingIssue.setVehicleNo(dto.getVehicleNo());
//         existingIssue.setGrossWt(dto.getGrossWt());
        
//         if (dto.getRequirements() != null) {
//             syncRequirements(existingIssue, dto.getRequirements());
//         }
//     }

//     private void syncRequirements(WeavingYarnIssue existingIssue, List<WeavingYarnRequirementDTO> requirementDTOs) {
//         Set<Long> incomingIds = requirementDTOs.stream()
//                 .map(r -> (r.getId() != null && r.getId() != 0) ? r.getId() : null)
//                 .filter(java.util.Objects::nonNull)
//                 .collect(Collectors.toSet());

//         // Remove orphans
//         existingIssue.getRequirements().removeIf(r -> r.getId() != null && !incomingIds.contains(r.getId()));

//         Map<Long, WeavingYarnRequirement> existingMap = existingIssue.getRequirements().stream()
//                 .filter(r -> r.getId() != null)
//                 .collect(Collectors.toMap(WeavingYarnRequirement::getId, r -> r));

//         for (WeavingYarnRequirementDTO reqDTO : requirementDTOs) {
//             Long reqId = (reqDTO.getId() != null && reqDTO.getId() != 0) ? reqDTO.getId() : null;
//             if (reqId != null && existingMap.containsKey(reqId)) {
//                 updateRequirementFromDTO(existingMap.get(reqId), reqDTO);
//             } else {
//                 WeavingYarnRequirement requirement = convertRequirementToEntity(reqDTO);
//                 requirement.setWeavingYarnIssue(existingIssue);
//                 existingIssue.getRequirements().add(requirement);
//             }
//         }
//     }

//     private void updateRequirementFromDTO(WeavingYarnRequirement entity, WeavingYarnRequirementDTO dto) {
//         entity.setYarnName(dto.getYarnName());
//         entity.setActualYarn(dto.getActualYarn());
//         entity.setYarnId(dto.getYarnId());
//         entity.setActualYarnId(dto.getActualYarnId());
//         entity.setGramsPerMeter(dto.getGramsPerMeter());
//         entity.setTotalReqQty(dto.getTotalReqQty());
//         entity.setTotalIssueQty(dto.getTotalIssueQty());
//         entity.setBalanceToIssue(dto.getBalanceToIssue());
//         entity.setActiveFlag(dto.isActiveFlag());

//         if (dto.getYarnIssues() != null) {
//             syncYarnIssues(entity, dto.getYarnIssues());
//         }
//     }

//     private void syncYarnIssues(WeavingYarnRequirement existingRequirement, List<YarnIssueDTO> yarnIssueDTOs) {
//         Set<Long> incomingIds = yarnIssueDTOs.stream()
//                 .map(yi -> (yi.getId() != null && yi.getId() != 0) ? yi.getId() : null)
//                 .filter(java.util.Objects::nonNull)
//                 .collect(Collectors.toSet());

//         // Remove orphans
//         existingRequirement.getYarnIssues().removeIf(yi -> yi.getId() != null && !incomingIds.contains(yi.getId()));

//         Map<Long, YarnIssue> existingMap = existingRequirement.getYarnIssues().stream()
//                 .filter(yi -> yi.getId() != null)
//                 .collect(Collectors.toMap(YarnIssue::getId, yi -> yi));

//         for (YarnIssueDTO yiDTO : yarnIssueDTOs) {
//             Long yiId = (yiDTO.getId() != null && yiDTO.getId() != 0) ? yiDTO.getId() : null;
//             if (yiId != null && existingMap.containsKey(yiId)) {
//                 updateYarnIssueFromDTO(existingMap.get(yiId), yiDTO);
//             } else {
//                 YarnIssue yarnIssue = convertYarnIssueToEntity(yiDTO);
//                 yarnIssue.setWeavingYarnRequirement(existingRequirement);
//                 existingRequirement.getYarnIssues().add(yarnIssue);
//             }
//         }
//     }

//     private void updateYarnIssueFromDTO(YarnIssue entity, YarnIssueDTO dto) {
//         entity.setYarnName(dto.getYarnName());
//         entity.setActualYarn(dto.getActualYarn());
//         entity.setYarnId(dto.getYarnId());
//         entity.setActualYarnId(dto.getActualYarnId());
//         entity.setMillId(dto.getMillId());
//         entity.setWarehouseId(dto.getWarehouseId());
//         entity.setAvailableReqQty(dto.getAvailableReqQty());
//         entity.setIssueQty(dto.getIssueQty());
//         entity.setNoOfBags(dto.getNoOfBags());
//         entity.setKgsPerBag(dto.getKgsPerBag());
//         entity.setConesPerBag(dto.getConesPerBag());
//         entity.setConesWeight(dto.getConesWeight());
//         entity.setLotNumber(dto.getLotNumber());
//         entity.setLotAvailableBags(dto.getLotAvailableBags());
//         if (dto.getInwardId() != null && dto.getInwardId() != 0) {
//             entity.setInwardId(dto.getInwardId());
//         }
//         if (dto.getOrderItemId() != null && dto.getOrderItemId() != 0) {
//             entity.setOrderItemId(dto.getOrderItemId());
//         }
//         entity.setActiveFlag(dto.isActiveFlag());
//     }

//     public void deleteWeavingYarnIssue(Long id) {
//         WeavingYarnIssue issue = weavingYarnIssueRepository.findById(id)
//             .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        
//         reverseLotReductions(issue, true);
//         cleanupWeftInward(issue);
        
//         weavingYarnIssueRepository.delete(issue);
//     }
    
//     public boolean isMapped(Long id) {
//         return weftInwardRepository.findByWeavingYarnIssueId(id)
//             .map(wi -> weftInwardService.isMapped(wi.getId()))
//             .orElse(false);
//     }

//     public void deleteWeavingYarnIssueByCompany(Long companyId, Long id) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         WeavingYarnIssue issue = weavingYarnIssueRepository.findByIdAndTradingCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
//         reverseLotReductions(issue, true);
//         cleanupWeftInward(issue);
//         weavingYarnIssueRepository.delete(issue);
//     }

//     private void processLotReductions(WeavingYarnIssue issue) {
//     }

//     private void cleanupWeftInward(WeavingYarnIssue issue) {
//         if (issue == null || issue.getId() == null) {
//             return;
//         }
//         weftInwardRepository.findByWeavingYarnIssueId(issue.getId()).ifPresent(weftInward -> {
//             String inwardNo = weftInward.getInwardNo();
//             if (StringUtils.hasText(inwardNo)) {
//                 if (weavingYarnOutwardDetailRepository.existsByWeftInwardNo(inwardNo)) {
//                     throw new IllegalStateException(inwardNo + " already mapped in weaving yarn outward");
//                 }
//                 // Also delete from YarnInventory if exists
//                 yarnInventoryRepository.findByInwardNo(inwardNo).ifPresent(yarnInventoryRepository::delete);
//             }
//             weftInwardRepository.delete(weftInward);
//         });
//     }

//     private void syncWeftInwardEntry(WeavingYarnIssue issue) {
//         if (issue == null || issue.getId() == null) {
//             return;
//         }
//         if (issue.getOrderTypeId() == null || issue.getOrderTypeId() != 1L) {
//             return;
//         }
//         Long jobWorkOrderId = resolveWeavingJobWorkOrderId(issue);
//         WeftInward weftInward = weftInwardRepository.findByWeavingYarnIssueId(issue.getId()).orElseGet(WeftInward::new);
//         boolean isNew = weftInward.getId() == null;
//         weftInward.setInwardDate(issue.getYarnIssueDate());
//         weftInward.setWeavingContractId(issue.getWeavingContractId());
//         weftInward.setWeavingJobWorkOrderId(jobWorkOrderId);
//         weftInward.setWeavingYarnIssueId(issue.getId());
//         weftInward.setDcNo(issue.getYarnIssueChallanNo());
//         weftInward.setDcDate(issue.getYarnIssueDate());
//         weftInward.setActiveFlag(issue.isActiveFlag());
//         weftInward.setOrderTypeId(issue.getOrderTypeId());
//         weftInward.setCompanyId(issue.getTradingCompanyId());
//         weftInward.setSourceCompanyId(issue.getTradingCompanyId());
//         weftInward.setDestinationCompanyId(issue.getFactoryCompanyId());
//         weftInward.setTradingCompanyId(issue.getTradingCompanyId());
//         weftInward.setFactoryCompanyId(issue.getFactoryCompanyId());

//         Map<Long, WeftInwardItem> existingItemsById = weftInward.getYarnItems().stream()
//                 .filter(item -> item.getId() != null)
//                 .collect(Collectors.toMap(WeftInwardItem::getId, item -> item, (first, second) -> first));
//         Map<String, List<WeftInwardItem>> existingItemsByKey = weftInward.getYarnItems().stream()
//                 .collect(Collectors.groupingBy(this::buildWeftInwardItemKey));
//         List<WeftInwardItem> sourceItems = buildWeftInwardItemsFromIssue(issue, weftInward);
//         List<WeftInwardItem> yarnItems = new ArrayList<>();
//         for (WeftInwardItem sourceItem : sourceItems) {
//             WeftInwardItem targetItem = sourceItem.getId() != null ? existingItemsById.remove(sourceItem.getId()) : null;
//             if (targetItem == null) {
//                 List<WeftInwardItem> matches = existingItemsByKey.get(buildWeftInwardItemKey(sourceItem));
//                 if (matches != null && !matches.isEmpty()) {
//                     targetItem = matches.remove(0);
//                 }
//             }
//             if (targetItem == null) {
//                 targetItem = sourceItem;
//                 targetItem.setId(null);
//             } else {
//                 mergeWeftInwardItem(targetItem, sourceItem);
//             }
//             if (targetItem.getMillName() == null) {
//                 targetItem.setMillName(sourceItem.getMillName());
//             }
//             targetItem.setWeftInward(weftInward);
//             yarnItems.add(targetItem);
//         }
//         weftInward.getYarnItems().clear();
//         weftInward.getYarnItems().addAll(yarnItems);
//         WeftInward savedInward = weftInwardRepository.save(weftInward);
//         if (isNew || !StringUtils.hasText(savedInward.getInwardNo())) {
//             String datePartWI = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
//             savedInward.setInwardNo("WI-" + datePartWI + "-" + savedInward.getId());
//             savedInward = weftInwardRepository.save(savedInward);
//         }
//         syncYarnInventoryEntry(issue, savedInward);
//     }

//     private void syncYarnInventoryEntry(WeavingYarnIssue issue, WeftInward weftInward) {
//         if (issue == null || issue.getOrderTypeId() == null || issue.getOrderTypeId() != 1L) {
//             return;
//         }

//         YarnInventory yarnInventory = yarnInventoryRepository.findByInwardNo(weftInward.getInwardNo()).orElseGet(YarnInventory::new);

//         yarnInventory.setInwardId(weftInward.getId());
//         Long inwardTypeId = codesDetailRepository.findByValue("weft-yarn-inward")
//                 .map(CodesDetail::getId)
//                 .orElseGet(() -> codesDetailRepository.findByValue("FACTORY_YARN_INWARD")
//                         .map(CodesDetail::getId)
//                         .orElse(2L));

//         yarnInventory.setInwardTypeId(inwardTypeId);
//         yarnInventory.setInwardNo(weftInward.getInwardNo());
//         yarnInventory.setCompanyId(issue.getTradingCompanyId());
//         yarnInventory.setInwardDate(issue.getYarnIssueDate() != null ? issue.getYarnIssueDate().atStartOfDay() : LocalDateTime.now());
//         yarnInventory.setFinancialYear(calculateFinancialYear(issue.getYarnIssueDate()));
//         yarnInventory.setDcNo(issue.getYarnIssueChallanNo());
//         yarnInventory.setDcDate(issue.getYarnIssueDate());

//         Long jobWorkOrderId = resolveWeavingJobWorkOrderId(issue);
//         String jobWorkOrderNo = null;
//         if (jobWorkOrderId != null) {
//             jobWorkOrderNo = weavingWorkOrderRepository.findById(jobWorkOrderId)
//                     .map(WeavingWorkOrder::getWeavingWorkOrderNo).orElse(null);
//         }

//         yarnInventory.setOrderId(jobWorkOrderId != null ? jobWorkOrderId : issue.getWeavingContractId());
//         yarnInventory.setOrderNo(jobWorkOrderNo != null ? jobWorkOrderNo : issue.getWeavingContractNo());
//         yarnInventory.setVendorId(issue.getVendorId());

//         Map<Long, YarnInwardStockDetails> existingDetailsByOrderItemId = new HashMap<>();
//         Map<Long, YarnInwardStockDetails> existingDetailsById = new HashMap<>();
//         Map<String, List<YarnInwardStockDetails>> existingDetailsByKey = new HashMap<>();
//         if (yarnInventory.getDetails() != null) {
//             yarnInventory.getDetails().forEach(detail -> {
//                 if (detail.getOrderItemId() != null) {
//                     existingDetailsByOrderItemId.put(detail.getOrderItemId(), detail);
//                 }
//                 if (detail.getId() != null) {
//                     existingDetailsById.put(detail.getId(), detail);
//                 }
//                 existingDetailsByKey
//                         .computeIfAbsent(buildYarnInwardStockDetailKey(detail), key -> new ArrayList<>())
//                         .add(detail);
//             });
//         }

//         List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();

//         if (weftInward.getYarnItems() != null) {
//             for (WeftInwardItem item : weftInward.getYarnItems()) {
//                 YarnIssue matchingIssue = findMatchingYarnIssue(issue, item);
//                 YarnInwardStockDetails detail = null;

//                 if (item.getId() != null) {
//                     detail = existingDetailsByOrderItemId.remove(item.getId());
//                 }
//                 if (detail == null && matchingIssue != null && matchingIssue.getInwardId() != null) {
//                     detail = existingDetailsById.remove(matchingIssue.getInwardId());
//                 }
//                 if (detail == null) {
//                     List<YarnInwardStockDetails> matches = existingDetailsByKey.get(buildWeftInwardItemKey(item));
//                     if (matches != null && !matches.isEmpty()) {
//                         detail = matches.remove(0);
//                     }
//                 }
//                 if (detail == null) {
//                     detail = new YarnInwardStockDetails();
//                     detail.setYarnInventory(yarnInventory);
//                 }

//                 removeStockDetailFromMaps(detail, existingDetailsByOrderItemId, existingDetailsById, existingDetailsByKey);

//                 BigDecimal inwardQty = item.getTotalKgs() != null ? BigDecimal.valueOf(item.getTotalKgs()) : BigDecimal.ZERO;
//                 BigDecimal inwardBags = item.getNoOfBags() != null ? BigDecimal.valueOf(item.getNoOfBags()) : BigDecimal.ZERO;

//                 detail.setOrderItemId(item.getId());
//                 detail.setItemId(item.getYarnId());
//                 detail.setMillId(item.getMillId());
//                 detail.setLotNo(item.getLotNo());
//                 detail.setInwardQty(inwardQty);
//                 detail.setAcceptedQty(inwardQty);
//                 detail.setInwardBags(inwardBags);
//                 detail.setKgsPerBag(item.getKgsPerBag() != null ? BigDecimal.valueOf(item.getKgsPerBag()) : BigDecimal.ZERO);
//                 if (matchingIssue != null) {
//                     detail.setConesPerBag(matchingIssue.getConesPerBag() != null ? matchingIssue.getConesPerBag() : BigDecimal.ZERO);
//                     detail.setConesWeight(matchingIssue.getConesWeight() != null ? matchingIssue.getConesWeight() : BigDecimal.ZERO);
//                 }
//                 detail.setWarehouseId(item.getWarehouseId());
//                 detail.setInwardTypeId(inwardTypeId);
//                 recalculateAvailableFromOutward(detail, inwardQty, inwardBags);
//                 updatedDetails.add(detail);

//                 if (yarnInventory.getWarehouseId() == null) {
//                     yarnInventory.setWarehouseId(item.getWarehouseId());
//                 }
//             }
//         }

//         if (yarnInventory.getDetails() != null) {
//             yarnInventory.getDetails().clear();
//             yarnInventory.getDetails().addAll(updatedDetails);
//         } else {
//             yarnInventory.setDetails(updatedDetails);
//         }
//         YarnInventory savedInventory = yarnInventoryRepository.save(yarnInventory);
//         syncYarnIssueStockReferences(issue, savedInventory);
//     }

//     private void syncYarnIssueStockReferences(WeavingYarnIssue issue, YarnInventory yarnInventory) {
//         if (issue == null || issue.getRequirements() == null || yarnInventory == null || yarnInventory.getDetails() == null) {
//             return;
//         }

//         Map<Long, YarnInwardStockDetails> detailsByOrderItemId = new HashMap<>();
//         Map<Long, YarnInwardStockDetails> detailsById = new HashMap<>();
//         Map<String, List<YarnInwardStockDetails>> detailsByKey = new HashMap<>();
//         for (YarnInwardStockDetails detail : yarnInventory.getDetails()) {
//             if (detail.getOrderItemId() != null) {
//                 detailsByOrderItemId.put(detail.getOrderItemId(), detail);
//             }
//             if (detail.getId() != null) {
//                 detailsById.put(detail.getId(), detail);
//             }
//             detailsByKey.computeIfAbsent(buildYarnInwardStockDetailKey(detail), key -> new ArrayList<>()).add(detail);
//         }

//         for (WeavingYarnRequirement requirement : issue.getRequirements()) {
//             if (requirement.getYarnIssues() == null) {
//                 continue;
//             }
//             for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
//                 boolean hasSourceStockReference = (yarnIssue.getOrderItemId() != null && yarnIssue.getOrderItemId() != 0)
//                         || (yarnIssue.getInwardId() != null && yarnIssue.getInwardId() != 0);
//                 if (hasSourceStockReference) {
//                     continue;
//                 }

//                 YarnInwardStockDetails matchingDetail = null;
//                 if (yarnIssue.getOrderItemId() != null) {
//                     matchingDetail = detailsByOrderItemId.get(yarnIssue.getOrderItemId());
//                 }
//                 if (matchingDetail == null && yarnIssue.getInwardId() != null) {
//                     matchingDetail = detailsById.get(yarnIssue.getInwardId());
//                 }
//                 if (matchingDetail == null) {
//                     List<YarnInwardStockDetails> matches = detailsByKey.get(buildYarnIssueKey(yarnIssue));
//                     if (matches != null && !matches.isEmpty()) {
//                         matchingDetail = matches.get(0);
//                     }
//                 }
//                 if (matchingDetail != null) {
//                     yarnIssue.setOrderItemId(matchingDetail.getOrderItemId());
//                     yarnIssue.setInwardId(matchingDetail.getId());
//                     yarnIssue.setInwardTypeId(matchingDetail.getInwardTypeId());
//                 }
//             }
//         }
//     }

//     private YarnIssue findMatchingYarnIssue(WeavingYarnIssue issue, WeftInwardItem item) {
//         if (issue == null || issue.getRequirements() == null || item == null) {
//             return null;
//         }

//         String itemKey = buildWeftInwardItemKey(item);
//         for (WeavingYarnRequirement requirement : issue.getRequirements()) {
//             if (requirement.getYarnIssues() == null) {
//                 continue;
//             }
//             for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
//                 if (item.getId() != null && yarnIssue.getOrderItemId() != null && item.getId().equals(yarnIssue.getOrderItemId())) {
//                     return yarnIssue;
//                 }
//                 if (buildYarnIssueKey(yarnIssue).equals(itemKey)) {
//                     return yarnIssue;
//                 }
//             }
//         }
//         return null;
//     }

//     private void removeStockDetailFromMaps(YarnInwardStockDetails detail,
//                                            Map<Long, YarnInwardStockDetails> detailsByOrderItemId,
//                                            Map<Long, YarnInwardStockDetails> detailsById,
//                                            Map<String, List<YarnInwardStockDetails>> detailsByKey) {
//         if (detail == null) {
//             return;
//         }
//         if (detail.getOrderItemId() != null) {
//             detailsByOrderItemId.remove(detail.getOrderItemId());
//         }
//         if (detail.getId() != null) {
//             detailsById.remove(detail.getId());
//         }
//         List<YarnInwardStockDetails> keyedDetails = detailsByKey.get(buildYarnInwardStockDetailKey(detail));
//         if (keyedDetails != null) {
//             keyedDetails.remove(detail);
//         }
//     }

//     private String buildYarnInwardStockDetailKey(YarnInwardStockDetails detail) {
//         if (detail == null) {
//             return "";
//         }
//         return String.join("|",
//                 String.valueOf(detail.getItemId() != null ? detail.getItemId() : 0L),
//                 String.valueOf(detail.getMillId() != null ? detail.getMillId() : 0L),
//                 String.valueOf(detail.getWarehouseId() != null ? detail.getWarehouseId() : 0L),
//                 normalizeLot(detail.getLotNo()));
//     }

//     private String buildYarnIssueKey(YarnIssue yarnIssue) {
//         if (yarnIssue == null) {
//             return "";
//         }
//         return String.join("|",
//                 String.valueOf(yarnIssue.getYarnId() != null ? yarnIssue.getYarnId() : 0L),
//                 String.valueOf(yarnIssue.getMillId() != null ? yarnIssue.getMillId() : 0L),
//                 String.valueOf(yarnIssue.getWarehouseId() != null ? yarnIssue.getWarehouseId() : 0L),
//                 normalizeLot(yarnIssue.getLotNumber()));
//     }

//     private void recalculateAvailableFromOutward(YarnInwardStockDetails detail, BigDecimal inwardQty, BigDecimal inwardBags) {
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

//     private void mergeWeftInwardItem(WeftInwardItem targetItem, WeftInwardItem sourceItem) {
//         targetItem.setYarnId(sourceItem.getYarnId());
//         targetItem.setYarnName(sourceItem.getYarnName());
//         targetItem.setActualYarnId(sourceItem.getActualYarnId());
//         targetItem.setActualYarn(sourceItem.getActualYarn());
//         targetItem.setNoOfBags(sourceItem.getNoOfBags());
//         targetItem.setKgsPerBag(sourceItem.getKgsPerBag());
//         targetItem.setTotalKgs(sourceItem.getTotalKgs());
//         targetItem.setMillId(sourceItem.getMillId());
//         targetItem.setMillName(sourceItem.getMillName() != null ? sourceItem.getMillName() : targetItem.getMillName());
//         targetItem.setWarehouseId(sourceItem.getWarehouseId());
//         targetItem.setLotNo(sourceItem.getLotNo());
//         targetItem.setActiveFlag(sourceItem.getActiveFlag());
//     }

//     private String buildWeftInwardItemKey(WeftInwardItem item) {
//         if (item == null) {
//             return "";
//         }
//         return String.join("|",
//                 String.valueOf(item.getYarnId() != null ? item.getYarnId() : 0L),
//                 String.valueOf(item.getMillId() != null ? item.getMillId() : 0L),
//                 String.valueOf(item.getWarehouseId() != null ? item.getWarehouseId() : 0L),
//                 normalizeLot(item.getLotNo()));
//     }

//     private String normalizeLot(String lotNo) {
//         return lotNo == null ? "" : lotNo.trim().toLowerCase();
//     }

//     private String calculateFinancialYear(LocalDate date) {
//         if (date == null) {
//             date = LocalDate.now();
//         }
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         if (month >= 4) {
//             int nextYear = year + 1;
//             return String.format("%d-%d", year, nextYear % 100);
//         } else {
//             int previousYear = year - 1;
//             return String.format("%d-%d", previousYear, year % 100);
//         }
//     }

//     private List<WeftInwardItem> buildWeftInwardItemsFromIssue(WeavingYarnIssue issue, WeftInward weftInward) {
//         List<WeftInwardItem> items = new ArrayList<>();
//         if (issue.getRequirements() == null) {
//             return items;
//         }
//         for (WeavingYarnRequirement requirement : issue.getRequirements()) {
//             if (requirement.getYarnIssues() == null) {
//                 continue;
//             }
//             for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
//                 WeftInwardItem item = new WeftInwardItem();
//                 item.setId(yarnIssue.getOrderItemId());
//                 item.setWeftInward(weftInward);
//                 item.setYarnId(yarnIssue.getYarnId());
//                 item.setYarnName(yarnIssue.getYarnName());
//                 item.setActualYarnId(yarnIssue.getActualYarnId());
//                 item.setActualYarn(yarnIssue.getActualYarn());
//                 item.setNoOfBags(yarnIssue.getNoOfBags());
//                 item.setKgsPerBag(yarnIssue.getKgsPerBag());
//                 item.setTotalKgs(yarnIssue.getIssueQty() != null ? yarnIssue.getIssueQty().doubleValue() : null);
//                 item.setMillId(yarnIssue.getMillId());
//                 item.setWarehouseId(yarnIssue.getWarehouseId());
//                 item.setLotNo(yarnIssue.getLotNumber());
//                 item.setActiveFlag(yarnIssue.isActiveFlag());
//                 items.add(item);
//             }
//         }
//         return items;
//     }

//     public void prepareOutwardReferencesForWeftInwardSync(Long issueId) {
//         if (issueId == null) {
//             return;
//         }
//         weavingYarnIssueRepository.findById(issueId)
//                 .ifPresent(issue -> reverseLotReductions(issue, true));
//     }

//     private void reverseLotReductions(WeavingYarnIssue issue, boolean clearOutwardDetails) {
//         String remark = "Weaving yarn issue - " + issue.getId();
//         List<YarnOutward> outwards = new ArrayList<>(yarnOutwardRepository.findByRemarks(remark));
//         if (outwards.isEmpty() && issue.getYarnIssueChallanNo() != null) {
//             yarnOutwardRepository.findByOutwardNo(issue.getYarnIssueChallanNo()).ifPresent(outwards::add);
//         }
        
//         for (YarnOutward outward : outwards) {
//             List<YarnOutwardStockDetails> outwardDetails = outward.getYarnOutwardStockDetails() != null
//                     ? new ArrayList<>(outward.getYarnOutwardStockDetails())
//                     : Collections.emptyList();

//             for (YarnOutwardStockDetails detail : outwardDetails) {
//                 Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();
                
//                 if (detail.getYarnInwardStockDetailsId() != null && detail.getYarnInwardStockDetailsId() != 0) {
//                     try {
//                         inwardDetailOpt = yarnInwardStockDetailsRepository.findById(detail.getYarnInwardStockDetailsId());
//                     } catch (Exception e) {
//                         inwardDetailOpt = Optional.empty();
//                     }
//                 }
                
//                 if (!inwardDetailOpt.isPresent() && detail.getOrderItemId() != null && detail.getOrderItemId() != 0) {
//                     inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(detail.getOrderItemId());
//                 }

//                 if (inwardDetailOpt.isPresent()) {
//                     YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
//                     BigDecimal qtyToRestore = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
//                     BigDecimal bagsToRestore = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
                    
//                     if (inwardDetail.getAvailableQty() == null) {
//                         inwardDetail.setAvailableQty(inwardDetail.getAcceptedQty() != null ? inwardDetail.getAcceptedQty() : BigDecimal.ZERO);
//                     }
//                     if (inwardDetail.getAvailableBags() == null) {
//                         inwardDetail.setAvailableBags(inwardDetail.getInwardBags() != null ? inwardDetail.getInwardBags() : BigDecimal.ZERO);
//                     }
                    
//                     inwardDetail.setAvailableQty(inwardDetail.getAvailableQty().add(qtyToRestore));
//                     inwardDetail.setAvailableBags(inwardDetail.getAvailableBags().add(bagsToRestore));
                    
//                     yarnInwardStockDetailsRepository.save(inwardDetail);
//                 }
//             }

//             if (clearOutwardDetails && !outwardDetails.isEmpty()) {
//                 if (outward.getYarnOutwardStockDetails() != null) {
//                     outward.getYarnOutwardStockDetails().clear();
//                 }
//                 yarnOutwardRepository.save(outward);
//                 yarnOutwardRepository.flush();
//                 yarnOutwardStockDetailsRepository.deleteAll(outwardDetails);
//                 yarnOutwardStockDetailsRepository.flush();
//                 yarnOutwardRepository.delete(outward);
//             }
//         }
//         yarnOutwardRepository.flush();
//         yarnInwardStockDetailsRepository.flush();
//     }
    
//     private void processLotOutward(WeavingYarnIssue issue, String referenceType, Long referenceId) {
//         String remark = "Weaving yarn issue - " + issue.getId();
        
//         YarnOutward yarnOutward = yarnOutwardRepository.findByRemarks(remark).stream().findFirst().orElse(null);
//         if (yarnOutward == null && issue.getYarnIssueChallanNo() != null) {
//             yarnOutward = yarnOutwardRepository.findByOutwardNo(issue.getYarnIssueChallanNo()).orElse(null);
//         }
        
//         if (yarnOutward == null) {
//             yarnOutward = new YarnOutward();
//             yarnOutward.setOutwardNo(issue.getYarnIssueChallanNo() != null ? 
//                 issue.getYarnIssueChallanNo() : generateYarnOutwardNo(issue));
//         }

//         yarnOutward.setOutwardTypeId(52L);
//         yarnOutward.setCompanyId(issue.getTradingCompanyId() != null ? issue.getTradingCompanyId() : 1L);
//         yarnOutward.setOutwardDate(issue.getYarnIssueDate() != null ? issue.getYarnIssueDate() : LocalDate.now());
//         yarnOutward.setOrderId(issue.getWeavingContractId());
        
//         String orderNo = null;
//         if (issue.getWeavingContractId() != null) {
//             orderNo = weavingContractRepository.findById(issue.getWeavingContractId())
//                 .map(WeavingContract::getWeavingContractNo).orElse(null);
//         }
//         yarnOutward.setOrderNo(orderNo);
//         yarnOutward.setFinancialYearId(issue.getYarnIssueDate() != null ? getFinancialYearId(issue.getYarnIssueDate()) : null);
//         yarnOutward.setVendorId(issue.getVendorId() != null ? issue.getVendorId() : 0L);
//         yarnOutward.setRemarks(remark);
//         yarnOutward.setActiveFlag(true);

//         // Sync details instead of clearing to preserve IDs
//         Map<Long, YarnOutwardStockDetails> existingMapByOrderItemId = new HashMap<>();
//         Map<Long, YarnOutwardStockDetails> existingMapByStockDetailsId = new HashMap<>();
        
//         if (yarnOutward.getYarnOutwardStockDetails() != null) {
//             yarnOutward.getYarnOutwardStockDetails().forEach(d -> {
//                 if (d.getOrderItemId() != null && d.getOrderItemId() != 0) 
//                     existingMapByOrderItemId.put(d.getOrderItemId(), d);
//                 if (d.getYarnInwardStockDetailsId() != null && d.getYarnInwardStockDetailsId() != 0) 
//                     existingMapByStockDetailsId.put(d.getYarnInwardStockDetailsId(), d);
//             });
//         }

//         List<YarnOutwardStockDetails> updatedDetails = new ArrayList<>();
//         YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);
        
//         List<YarnOutwardStockDetails> currentDetails = savedYarnOutward.getYarnOutwardStockDetails();
//         if (currentDetails == null) {
//             currentDetails = new ArrayList<>();
//             savedYarnOutward.setYarnOutwardStockDetails(currentDetails);
//         }

//         if (issue.getRequirements() != null) {
//             for (WeavingYarnRequirement requirement : issue.getRequirements()) {
//                 if (requirement.getYarnIssues() != null) {
//                     for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
//                         Optional<YarnInwardStockDetails> inwardDetailOpt = findInwardStockDetails(yarnIssue);

//                         if (!inwardDetailOpt.isPresent()) continue;
//                         YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
                        
//                         // Persist IDs back to yarnIssue
//                         yarnIssue.setOrderItemId(inwardDetail.getOrderItemId());
//                         yarnIssue.setInwardId(inwardDetail.getId());
                        
//                         // Match by stock details ID first so multiple rows with same orderItemId stay distinct
//                         YarnOutwardStockDetails outwardDetail = null;
//                         if (inwardDetail.getId() != null) {
//                             outwardDetail = existingMapByStockDetailsId.remove(inwardDetail.getId());
//                         }
//                         if (outwardDetail == null && inwardDetail.getOrderItemId() != null) {
//                             outwardDetail = existingMapByOrderItemId.remove(inwardDetail.getOrderItemId());
//                         }
                        
//                         // Clean up the other map if we found it in one
//                         if (outwardDetail != null) {
//                             existingMapByOrderItemId.values().remove(outwardDetail);
//                             existingMapByStockDetailsId.values().remove(outwardDetail);
//                         }

//                         boolean isNew = false;
//                         if (outwardDetail == null) {
//                             outwardDetail = new YarnOutwardStockDetails();
//                             outwardDetail.setYarnOutward(savedYarnOutward);
//                             isNew = true;
//                         }

//                         outwardDetail.setInwardId(inwardDetail.getYarnInventory() != null ? inwardDetail.getYarnInventory().getId() : null);
//                         outwardDetail.setOrderItemId(inwardDetail.getOrderItemId());
//                         outwardDetail.setYarnInwardStockDetailsId(inwardDetail.getId());
//                         outwardDetail.setOutwardTypeId(52L);
//                         outwardDetail.setActualItemId(inwardDetail.getItemId());
//                         outwardDetail.setMillId(inwardDetail.getMillId());
//                         outwardDetail.setWarehouseId(inwardDetail.getWarehouseId());
//                         outwardDetail.setLotNo(inwardDetail.getLotNo());
//                         outwardDetail.setIssuedQty(yarnIssue.getIssueQty());
//                         outwardDetail.setIssuedBags(yarnIssue.getNoOfBags() != null ? BigDecimal.valueOf(yarnIssue.getNoOfBags()) : BigDecimal.ZERO);
//                         outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag() != null ? BigDecimal.valueOf(yarnIssue.getKgsPerBag()) : inwardDetail.getKgsPerBag());
//                         outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
//                         outwardDetail.setConesWeight(yarnIssue.getConesWeight());

//                         if (isNew) currentDetails.add(outwardDetail);
//                         updatedDetails.add(outwardDetail);
//                     }
//                 }
//             }
//         }

//         // Remove orphans
//         Set<Long> orphanIds = new HashSet<>();
//         existingMapByOrderItemId.values().forEach(d -> { if(d.getId() != null) orphanIds.add(d.getId()); });
//         existingMapByStockDetailsId.values().forEach(d -> { if(d.getId() != null) orphanIds.add(d.getId()); });
        
//         if (!orphanIds.isEmpty()) {
//             currentDetails.removeIf(d -> d.getId() != null && orphanIds.contains(d.getId()));
//         }

//         yarnOutwardRepository.save(savedYarnOutward);
//         yarnOutwardRepository.flush();

//         if (!updatedDetails.isEmpty()) {
//             List<YarnOutwardStockDetails> persistedDetails = yarnOutwardStockDetailsRepository.saveAll(updatedDetails);
//             yarnOutwardStockDetailsRepository.flush();
//             updateAvailableQtyInInwardStockDetails(persistedDetails);
//         }
//     }

//     private void validateYarnIssue(WeavingYarnIssueDTO dto) {
//         if (dto.getRequirements() == null) return;
//         for (WeavingYarnRequirementDTO req : dto.getRequirements()) {
//             if (req.getYarnIssues() == null) continue;
//             for (YarnIssueDTO yarnIssue : req.getYarnIssues()) {
//                 if (yarnIssue.getIssueQty() == null || yarnIssue.getIssueQty().compareTo(BigDecimal.ZERO) <= 0) {
//                     continue;
//                 }
                
//                 Long searchItemId = yarnIssue.getActualYarnId();
//                 String searchName = yarnIssue.getActualYarn() != null ? yarnIssue.getActualYarn() : yarnIssue.getYarnName();
                
//                 if (searchItemId == null) {
//                     Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(searchName);
//                     if (yarnMaster.isPresent()) {
//                         searchItemId = yarnMaster.get().getId();
//                     } else {
//                         throw new EntityNotFoundException("Yarn not found: " + searchName);
//                     }
//                 }
                
//                 List<YarnInwardStockDetails> inwardDetails = 
//                     yarnInwardStockDetailsRepository.findByItemId(searchItemId);
                
//                 if (inwardDetails.isEmpty()) {
//                     throw new IllegalArgumentException("No inward stock available for yarn: " + searchName);
//                 }
                
//                 BigDecimal totalAvailable = inwardDetails.stream()
//                     .map(detail -> detail.getAvailableQty() != null ? detail.getAvailableQty() : BigDecimal.ZERO)
//                     .reduce(BigDecimal.ZERO, BigDecimal::add);
                
//                 if (totalAvailable.compareTo(yarnIssue.getIssueQty()) < 0) {
//                     throw new IllegalArgumentException(
//                         String.format("Insufficient quantity in inward stock for yarn %s (Available: %s, Requested: %s)",
//                             searchName, totalAvailable, yarnIssue.getIssueQty()));
//                 }
//             }
//         }
//     }

//     private WeavingYarnIssueDTO convertToDTO(WeavingYarnIssue entity) {
//         WeavingYarnIssueDTO dto = new WeavingYarnIssueDTO();
//         dto.setId(entity.getId());
//         dto.setVendorId(entity.getVendorId());
//         dto.setWeavingContractId(entity.getWeavingContractId());
//         dto.setWeavingContractNo(entity.getWeavingContractNo());
//         dto.setWeavingJobWorkOrderId(entity.getWeavingJobWorkOrderId());
//         dto.setTransportationDetails(entity.getTransportationDetails());
//         dto.setTransportId(entity.getTransportId());
//         dto.setTermsConditionsId(entity.getTermsConditionsId());
//         dto.setFabricDetails(entity.getFabricDetails());
//         dto.setYarnIssueDate(entity.getYarnIssueDate());
//         dto.setYarnIssueChallanNo(entity.getYarnIssueChallanNo());
//         dto.setActiveFlag(entity.isActiveFlag());
//         dto.setOrderTypeId(entity.getOrderTypeId());
//         dto.setTradingCompanyId(entity.getTradingCompanyId());
//         dto.setFactoryCompanyId(entity.getFactoryCompanyId());
//         dto.setRemarks(entity.getRemarks());
//         dto.setVehicleNo(entity.getVehicleNo());
//         dto.setGrossWt(entity.getGrossWt());

//         if (entity.getRequirements() != null) {
//             List<WeavingYarnRequirementDTO> requirementDTOs = entity.getRequirements().stream()
//                     .map(this::convertRequirementToDTO)
//                     .collect(Collectors.toList());
//             dto.setRequirements(requirementDTOs);
//         }

//         return dto;
//     }
    
//     private WeavingYarnRequirementDTO convertRequirementToDTO(WeavingYarnRequirement requirement) {
//         WeavingYarnRequirementDTO dto = new WeavingYarnRequirementDTO();
//         dto.setId(requirement.getId());
//         dto.setYarnName(requirement.getYarnName());
//         dto.setActualYarn(requirement.getActualYarn());
//         dto.setYarnId(requirement.getYarnId());
//         dto.setActualYarnId(requirement.getActualYarnId());
//         dto.setGramsPerMeter(requirement.getGramsPerMeter());
//         dto.setTotalReqQty(requirement.getTotalReqQty());
//         dto.setTotalIssueQty(requirement.getTotalIssueQty());
//         dto.setBalanceToIssue(requirement.getBalanceToIssue());
//         dto.setActiveFlag(requirement.isActiveFlag());

// // Calculate OVERALL total issued quantity from outward stock details across the whole contract
//         BigDecimal overallTotalIssued = BigDecimal.ZERO;
//         if (requirement.getWeavingYarnIssue() != null && requirement.getWeavingYarnIssue().getWeavingContractId() != null) {
//             Long contractId = requirement.getWeavingYarnIssue().getWeavingContractId();
//             Long vendorId = requirement.getWeavingYarnIssue().getVendorId();
//             Long itemId = requirement.getActualYarnId() != null ? requirement.getActualYarnId() : requirement.getYarnId();
//              if (itemId != null && vendorId != null) {
//               overallTotalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByContractItemVendorAndType(contractId, itemId, vendorId, 52L);
//              }
//         }
        
//         dto.setOverallTotalIssueQty(overallTotalIssued);
        
//     // BigDecimal overallTotalIssued = BigDecimal.ZERO;
//     // if (requirement.getWeavingYarnIssue() != null && requirement.getWeavingYarnIssue().getWeavingContractId() != null) {
//     //     Long contractId = requirement.getWeavingYarnIssue().getWeavingContractId();
//     //     Long vendorId = requirement.getWeavingYarnIssue().getVendorId();
//     //     Long itemId = requirement.getActualYarnId() != null ? requirement.getActualYarnId() : requirement.getYarnId();
        
//     //     if (itemId != null && vendorId != null) {
//     //         // Fetch sum from all historical YarnIssue records for this contract/item/vendor
//     //         overallTotalIssued = yarnIssueRepository.getTotalIssuedQtyByContractItemAndVendor(
//     //             contractId, itemId, vendorId);
//     //     }
//     // }
//     //     dto.setOverallTotalIssueQty(overallTotalIssued);
//            // 2. Calculate Balance to Issue
//     if (requirement.getTotalReqQty() != null) {
//         // Balance = Planned - Overall Issued
//         dto.setBalanceToIssue(requirement.getTotalReqQty().subtract(overallTotalIssued));
//     } else {
//         dto.setBalanceToIssue(BigDecimal.ZERO);
//     }

//         if (requirement.getYarnIssues() != null) {
//             List<YarnIssueDTO> yarnIssueDTOs = requirement.getYarnIssues().stream()
//                     .map(this::convertYarnIssueToDTO)
//                     .collect(Collectors.toList());
//             dto.setYarnIssues(yarnIssueDTOs);
//         }

//         return dto;
//     }
    
//     private YarnIssueDTO convertYarnIssueToDTO(YarnIssue yarnIssue) {
//         YarnIssueDTO dto = new YarnIssueDTO();
//         dto.setId(yarnIssue.getId());
//         if (yarnIssue.getWeavingYarnRequirement() != null) {
//             dto.setWeavingYarnRequirementId(yarnIssue.getWeavingYarnRequirement().getId());
//         }
//         dto.setYarnName(yarnIssue.getYarnName());
//         dto.setActualYarn(yarnIssue.getActualYarn());
//         dto.setYarnId(yarnIssue.getYarnId());
//         dto.setActualYarnId(yarnIssue.getActualYarnId());
//         dto.setMillId(yarnIssue.getMillId());
//         dto.setWarehouseId(yarnIssue.getWarehouseId());
//         dto.setAvailableReqQty(yarnIssue.getAvailableReqQty());
//         dto.setIssueQty(yarnIssue.getIssueQty());
//         dto.setNoOfBags(yarnIssue.getNoOfBags());
//         dto.setKgsPerBag(yarnIssue.getKgsPerBag());
//         dto.setConesPerBag(yarnIssue.getConesPerBag());
//         dto.setConesWeight(yarnIssue.getConesWeight());
//         dto.setLotNumber(yarnIssue.getLotNumber());
//         dto.setLotAvailableBags(yarnIssue.getLotAvailableBags());
//         dto.setInwardId(yarnIssue.getInwardId());
//         dto.setInwardTypeId(yarnIssue.getInwardTypeId());
//         dto.setOrderItemId(yarnIssue.getOrderItemId());
//         dto.setActiveFlag(yarnIssue.isActiveFlag());
//         return dto;
//     }
    
//     private PaginatedResponse<WeavingYarnIssueDTO> buildPaginatedResponse(Page<WeavingYarnIssue> page, Long offset, Long limit) {
//         List<WeavingYarnIssueDTO> content = page.getContent().stream()
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//         return new PaginatedResponse<>(
//                 page.getTotalElements(),
//                 !page.isLast(),
//                 page.getNumber() > 0,
//                 offset,
//                 limit,
//                 content
//         );
//     }
    
//     private Long resolveWeavingJobWorkOrderId(WeavingYarnIssue issue) {
//         if (issue == null) {
//             return null;
//         }
//         if (issue.getWeavingJobWorkOrderId() != null) {
//             return issue.getWeavingJobWorkOrderId();
//         }
//         Long weavingContractId = issue.getWeavingContractId();
//         if (weavingContractId == null) {
//             return null;
//         }
//         Long jobWorkOrderId = weavingWorkOrderRepository
//                 .findFirstByWeavingContractIdOrderByIdDesc(weavingContractId)
//                 .map(WeavingWorkOrder::getId)
//                 .orElse(null);
//         if (jobWorkOrderId != null && issue.getId() != null) {
//             issue.setWeavingJobWorkOrderId(jobWorkOrderId);
//             weavingYarnIssueRepository.save(issue);
//         }
//         return jobWorkOrderId;
//     }
    
//     private WeavingYarnIssue convertToEntity(WeavingYarnIssueDTO dto) {
//         WeavingYarnIssue entity = new WeavingYarnIssue();
//         entity.setId(dto.getId() != null && dto.getId() != 0 ? dto.getId() : null);
//         entity.setVendorId(dto.getVendorId());
//         entity.setWeavingContractId(dto.getWeavingContractId());
//         entity.setWeavingContractNo(dto.getWeavingContractNo());
//         entity.setWeavingJobWorkOrderId(dto.getWeavingJobWorkOrderId());
//         entity.setTransportationDetails(dto.getTransportationDetails());
//         entity.setTransportId(dto.getTransportId());
//         entity.setTermsConditionsId(dto.getTermsConditionsId());
//         entity.setFabricDetails(dto.getFabricDetails());
//         entity.setYarnIssueDate(dto.getYarnIssueDate());
//         entity.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
//         entity.setActiveFlag(dto.isActiveFlag());
//         entity.setOrderTypeId(dto.getOrderTypeId());
//         entity.setTradingCompanyId(dto.getTradingCompanyId());
//         entity.setFactoryCompanyId(dto.getFactoryCompanyId());
//         entity.setRemarks(dto.getRemarks());
//         entity.setVehicleNo(dto.getVehicleNo());
//         entity.setGrossWt(dto.getGrossWt());

//         if (dto.getRequirements() != null) {
//             List<WeavingYarnRequirement> requirements = dto.getRequirements().stream()
//                     .map(reqDTO -> {
//                         WeavingYarnRequirement requirement = convertRequirementToEntity(reqDTO);
//                         requirement.setWeavingYarnIssue(entity);
//                         return requirement;
//                     })
//                     .collect(Collectors.toList());
//             entity.setRequirements(requirements);
//         }

//         return entity;
//     }
    
//     private WeavingContract requireWeavingContractReference(Long weavingContractId) {
//         if (weavingContractId == null || weavingContractId <= 0) {
//             return null;
//         }
//         Optional<WeavingContract> weavingContractOptional = weavingContractRepository.findById(weavingContractId);
//         String displayIdentifier = weavingContractOptional
//                 .map(WeavingContract::getWeavingContractNo)
//                 .filter(StringUtils::hasText)
//                 .orElse("Weaving Contract ID " + weavingContractId);
//         return referenceValidationService.requireReference(
//                 weavingContractId,
//                 () -> weavingContractOptional,
//                 "Weaving Contract",
//                 displayIdentifier);
//     }
    
//     private WeavingYarnRequirement convertRequirementToEntity(WeavingYarnRequirementDTO dto) {
//         WeavingYarnRequirement entity = new WeavingYarnRequirement();
//         entity.setYarnName(dto.getYarnName());
//         entity.setActualYarn(dto.getActualYarn());
//         entity.setYarnId(dto.getYarnId());
//         entity.setActualYarnId(dto.getActualYarnId());
//         entity.setGramsPerMeter(dto.getGramsPerMeter());
//         entity.setTotalReqQty(dto.getTotalReqQty());
//         entity.setTotalIssueQty(dto.getTotalIssueQty());
//         entity.setBalanceToIssue(dto.getBalanceToIssue());
//         entity.setActiveFlag(dto.isActiveFlag());

//         if (dto.getYarnIssues() != null) {
//             List<YarnIssue> yarnIssues = dto.getYarnIssues().stream()
//                     .map(yarnDTO -> {
//                         YarnIssue yarnIssue = convertYarnIssueToEntity(yarnDTO);
//                         yarnIssue.setWeavingYarnRequirement(entity);
//                         return yarnIssue;
//                     })
//                     .collect(Collectors.toList());
//             entity.setYarnIssues(yarnIssues);
//         }

//         return entity;
//     }
    
//     private YarnIssue convertYarnIssueToEntity(YarnIssueDTO dto) {
//         YarnIssue entity = new YarnIssue();
//         entity.setYarnName(dto.getYarnName());
//         entity.setActualYarn(dto.getActualYarn());
//         entity.setYarnId(dto.getYarnId());
//         entity.setActualYarnId(dto.getActualYarnId());
//         entity.setMillId(dto.getMillId());
//         entity.setWarehouseId(dto.getWarehouseId());
//         entity.setAvailableReqQty(dto.getAvailableReqQty());
//         entity.setIssueQty(dto.getIssueQty());
//         entity.setNoOfBags(dto.getNoOfBags());
//         entity.setKgsPerBag(dto.getKgsPerBag());
//         entity.setConesPerBag(dto.getConesPerBag());
//         entity.setConesWeight(dto.getConesWeight());
//         entity.setLotNumber(dto.getLotNumber());
//         entity.setLotAvailableBags(dto.getLotAvailableBags());
//         entity.setInwardId(dto.getInwardId());
//         entity.setInwardTypeId(dto.getInwardTypeId());
//         entity.setOrderItemId(dto.getOrderItemId());
//         entity.setActiveFlag(dto.isActiveFlag());
//         return entity;
//     }
    
//     private BigDecimal calculateTotalIssuedQuantity(Long yarnOrderItemId) {
//         if (yarnOrderItemId == null) {
//             return BigDecimal.ZERO;
//         }
//         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(yarnOrderItemId);
//         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
//     }

//     private BigDecimal calculateTotalIssuedBags(Long yarnOrderItemId) {
//         if (yarnOrderItemId == null) {
//             return BigDecimal.ZERO;
//         }
//         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(yarnOrderItemId);
//         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
//     }

//     private String generateYarnOutwardNo(WeavingYarnIssue issue) {
//         return "WYO-" + LocalDate.now().getYear() + "-" + issue.getId();
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

//     public void syncYarnIssuesFromWeftInward(WeftInward weftInward) {
//         if (weftInward == null) {
//             return;
//         }
//         Long issueId = weftInward.getWeavingYarnIssueId();
//         if (issueId == null) {
//             return;
//         }
//         List<WeftInwardItem> yarnItems = weftInward.getYarnItems();
//         if (yarnItems == null || yarnItems.isEmpty()) {
//             return;
//         }
//         Optional<WeavingYarnIssue> issueOpt = weavingYarnIssueRepository.findById(issueId);
//         if (!issueOpt.isPresent()) {
//             return;
//         }
//         WeavingYarnIssue issue = issueOpt.get();
//         if (issue.getRequirements() == null) {
//             return;
//         }
//         Set<WeftInwardItem> usedItems = Collections.newSetFromMap(new IdentityHashMap<>());
//         boolean updated = false;
//         for (WeavingYarnRequirement requirement : issue.getRequirements()) {
//             if (requirement.getYarnIssues() == null) {
//                 continue;
//             }
//             for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
//                 WeftInwardItem match = findBestMatchingWeftItem(yarnIssue, yarnItems, usedItems);
//                 if (match != null) {
//                     updated |= applyWeftValuesToYarnIssue(yarnIssue, match);
//                 }
//             }
//         }
//         if (updated) {
//             issue = weavingYarnIssueRepository.save(issue);
//         }
//         processLotOutward(issue, "WEAVING_CONTRACT", issue.getId());
//     }

//     private WeftInwardItem findBestMatchingWeftItem(YarnIssue yarnIssue, List<WeftInwardItem> yarnItems, Set<WeftInwardItem> usedItems) {
//         for (WeftInwardItem candidate : yarnItems) {
//             if (usedItems.contains(candidate)) {
//                 continue;
//             }
//             if (isStrongMatch(yarnIssue, candidate)) {
//                 usedItems.add(candidate);
//                 return candidate;
//             }
//         }
//         for (WeftInwardItem candidate : yarnItems) {
//             if (usedItems.contains(candidate)) {
//                 continue;
//             }
//             usedItems.add(candidate);
//             return candidate;
//         }
//         return null;
//     }

//     private boolean isStrongMatch(YarnIssue yarnIssue, WeftInwardItem item) {
//         if (yarnIssue.getActualYarnId() != null && Objects.equals(yarnIssue.getActualYarnId(), item.getActualYarnId())) {
//             return true;
//         }
//         if (yarnIssue.getActualYarn() != null && equalsIgnoreCase(yarnIssue.getActualYarn(), item.getActualYarn())) {
//             return true;
//         }
//         if (yarnIssue.getLotNumber() != null && equalsIgnoreCase(yarnIssue.getLotNumber(), item.getLotNo())
//                 && Objects.equals(yarnIssue.getMillId(), item.getMillId())) {
//             return true;
//         }
//         return yarnIssue.getYarnId() != null && Objects.equals(yarnIssue.getYarnId(), item.getYarnId());
//     }

//     private boolean applyWeftValuesToYarnIssue(YarnIssue yarnIssue, WeftInwardItem item) {
//         boolean updated = false;
//         Integer bags = item.getNoOfBags();
//         if (bags != null && !Objects.equals(yarnIssue.getNoOfBags(), bags)) {
//             yarnIssue.setNoOfBags(bags);
//             updated = true;
//         }
//         Double kgsPerBag = item.getKgsPerBag();
//         if (kgsPerBag != null && !Objects.equals(yarnIssue.getKgsPerBag(), kgsPerBag)) {
//             yarnIssue.setKgsPerBag(kgsPerBag);
//             updated = true;
//         }
//         Double totalKgs = item.getTotalKgs();
//         if (totalKgs != null) {
//             BigDecimal newIssueQty = BigDecimal.valueOf(totalKgs);
//             if (yarnIssue.getIssueQty() == null || yarnIssue.getIssueQty().compareTo(newIssueQty) != 0) {
//                 yarnIssue.setIssueQty(newIssueQty);
//                 updated = true;
//             }
//         }
//         String lotNo = item.getLotNo();
//         if (lotNo != null && !equalsIgnoreCase(yarnIssue.getLotNumber(), lotNo)) {
//             yarnIssue.setLotNumber(lotNo);
//             updated = true;
//         }
//         Long millId = item.getMillId();
//         if (millId != null && !Objects.equals(yarnIssue.getMillId(), millId)) {
//             yarnIssue.setMillId(millId);
//             updated = true;
//         }
//         Long warehouseId = item.getWarehouseId();
//         if (warehouseId != null && !Objects.equals(yarnIssue.getWarehouseId(), warehouseId)) {
//             yarnIssue.setWarehouseId(warehouseId);
//             updated = true;
//         }
//         return updated;
//     }

//     private boolean equalsIgnoreCase(String first, String second) {
//         if (first == null || second == null) {
//             return false;
//         }
//         return first.trim().equalsIgnoreCase(second.trim());
//     }

//     private boolean matchesInwardDetailForYarnIssue(YarnIssue yarnIssue, YarnInwardStockDetails detail, Long searchItemId, String targetLot) {
//         if (yarnIssue == null || detail == null) {
//             return false;
//         }
//         if (searchItemId != null && !Objects.equals(searchItemId, detail.getItemId())) {
//             return false;
//         }
//         if (yarnIssue.getMillId() != null && !Objects.equals(yarnIssue.getMillId(), detail.getMillId())) {
//             return false;
//         }
//         if (yarnIssue.getWarehouseId() != null && !Objects.equals(yarnIssue.getWarehouseId(), detail.getWarehouseId())) {
//             return false;
//         }
//         if (targetLot != null && !targetLot.isEmpty()) {
//             String candidateLot = detail.getLotNo() == null ? "" : detail.getLotNo().trim();
//             if (!targetLot.equalsIgnoreCase(candidateLot)) {
//                 return false;
//             }
//         }
//         return true;
//     }

// private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
//         Map<Long, BigDecimal> qtyAdjustments = new HashMap<>();
//         Map<Long, BigDecimal> bagAdjustments = new HashMap<>();
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
//                 qtyAdjustments.merge(stockId, outwardDetail.getIssuedQty() != null ? outwardDetail.getIssuedQty() : BigDecimal.ZERO, BigDecimal::add);
//                 bagAdjustments.merge(stockId, outwardDetail.getIssuedBags() != null ? outwardDetail.getIssuedBags() : BigDecimal.ZERO, BigDecimal::add);
//             }
//         }

//         for (Long stockId : stockDetailsIds) {
//             Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(stockId);
//             if (!inwardDetailOpt.isPresent()) {
//                 continue;
//             }
            
//             YarnInwardStockDetails detail = inwardDetailOpt.get();
//             BigDecimal qtyDelta = qtyAdjustments.getOrDefault(stockId, BigDecimal.ZERO);
//             BigDecimal bagDelta = bagAdjustments.getOrDefault(stockId, BigDecimal.ZERO);

//             // Handle Available Quantity
//             if (detail.getAvailableQty() == null) {
//                 BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
//                 BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockId);
//                 detail.setAvailableQty(acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO));
//             } else {
//                 detail.setAvailableQty(detail.getAvailableQty().subtract(qtyDelta));
//             }

//             // Handle Available Bags
//             if (detail.getAvailableBags() == null) {
//                 BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;
//                 BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockId);
//                 detail.setAvailableBags(inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO));
//             } else {
//                 detail.setAvailableBags(detail.getAvailableBags().subtract(bagDelta));
//             }

//             // Ensure not negative
//             if (detail.getAvailableQty() != null && detail.getAvailableQty().compareTo(BigDecimal.ZERO) < 0) {
//                 detail.setAvailableQty(BigDecimal.ZERO);
//             }
//             if (detail.getAvailableBags() != null && detail.getAvailableBags().compareTo(BigDecimal.ZERO) < 0) {
//                 detail.setAvailableBags(BigDecimal.ZERO);
//             }

//             yarnInwardStockDetailsRepository.save(detail);
//         }
//     }
//     // private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
//     //     Set<Long> orderItemIds = new HashSet<>();
//     //     Map<Long, BigDecimal> issuedQtyAdjustments = new HashMap<>();
//     //     Map<Long, BigDecimal> issuedBagAdjustments = new HashMap<>();

//     //     for (YarnOutwardStockDetails outwardDetail : outwardDetails) {
//     //         Long orderItemId = outwardDetail.getOrderItemId();
//     //         if (orderItemId == null) {
//     //             continue;
//     //         }
//     //         orderItemIds.add(orderItemId);
//     //         if (outwardDetail.getIssuedQty() != null) {
//     //             issuedQtyAdjustments.merge(orderItemId, outwardDetail.getIssuedQty(), BigDecimal::add);
//     //         }
//     //         if (outwardDetail.getIssuedBags() != null) {
//     //             issuedBagAdjustments.merge(orderItemId, outwardDetail.getIssuedBags(), BigDecimal::add);
//     //         }
//     //     }

//     //     for (Long orderItemId : orderItemIds) {
//     //         Optional<YarnInwardStockDetails> inwardDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(orderItemId);
//     //         if (!inwardDetail.isPresent()) {
//     //             continue;
//     //         }
//     //         YarnInwardStockDetails detail = inwardDetail.get();

//     //         BigDecimal qtyDelta = issuedQtyAdjustments.getOrDefault(orderItemId, BigDecimal.ZERO);
//     //         if (qtyDelta.signum() != 0) {
//     //             BigDecimal baseQty = detail.getAvailableQty() != null ? detail.getAvailableQty() : detail.getAcceptedQty();
//     //             if (baseQty != null) {
//     //                 BigDecimal newAvailableQty = baseQty.subtract(qtyDelta);
//     //                 if (newAvailableQty.compareTo(BigDecimal.ZERO) < 0) {
//     //                     newAvailableQty = BigDecimal.ZERO;
//     //                 }
//     //                 detail.setAvailableQty(newAvailableQty);
//     //             }
//     //         }

//     //         BigDecimal bagDelta = issuedBagAdjustments.getOrDefault(orderItemId, BigDecimal.ZERO);
//     //         if (bagDelta.signum() != 0) {
//     //             BigDecimal baseBags = detail.getAvailableBags() != null ? detail.getAvailableBags() : detail.getInwardBags();
//     //             if (baseBags != null) {
//     //                 BigDecimal newAvailableBags = baseBags.subtract(bagDelta);
//     //                 if (newAvailableBags.compareTo(BigDecimal.ZERO) < 0) {
//     //                     newAvailableBags = BigDecimal.ZERO;
//     //                 }
//     //                 detail.setAvailableBags(newAvailableBags);
//     //             }
//     //         }

//     //         if (detail.getAvailableQty() == null && detail.getAcceptedQty() != null) {
//     //             BigDecimal acceptedQty = detail.getAcceptedQty();
//     //             BigDecimal totalIssuedQty = calculateTotalIssuedQuantity(orderItemId);
//     //             detail.setAvailableQty(acceptedQty.subtract(totalIssuedQty));
//     //         }
//     //         if (detail.getAvailableBags() == null && detail.getInwardBags() != null) {
//     //             BigDecimal inwardBags = detail.getInwardBags();
//     //             BigDecimal totalIssuedBags = calculateTotalIssuedBags(orderItemId);
//     //             detail.setAvailableBags(inwardBags.subtract(totalIssuedBags));
//     //         }

//     //         if (detail.getAvailableQty() != null && detail.getAvailableQty().compareTo(BigDecimal.ZERO) < 0) {
//     //             detail.setAvailableQty(BigDecimal.ZERO);
//     //         }
//     //         if (detail.getAvailableBags() != null && detail.getAvailableBags().compareTo(BigDecimal.ZERO) < 0) {
//     //             detail.setAvailableBags(BigDecimal.ZERO);
//     //         }

//     //         yarnInwardStockDetailsRepository.save(detail);
//     //     }
//     // }
    
//     private Optional<YarnInwardStockDetails> findInwardStockDetails(YarnIssue yarnIssue) {
//         Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();

//         if (yarnIssue == null) {
//             return inwardDetailOpt;
//         }

//         Long searchItemId = yarnIssue.getActualYarnId();
//         if (searchItemId == null) {
//             searchItemId = yarnIssue.getYarnId();
//         }
//         if (searchItemId == null) {
//             String searchName = yarnIssue.getActualYarn() != null ? yarnIssue.getActualYarn() : yarnIssue.getYarnName();
//             if (searchName != null) {
//                 Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(searchName);
//                 if (yarnMaster.isPresent()) {
//                     searchItemId = yarnMaster.get().getId();
//                 }
//             }
//         }

//         String targetLot = yarnIssue.getLotNumber() == null ? "" : yarnIssue.getLotNumber().trim();
//         final Long finalSearchItemId = searchItemId;
//         final String finalTargetLot = targetLot;

//         try {
//             if (yarnIssue.getInwardId() != null && yarnIssue.getInwardId() != 0) {
//                 inwardDetailOpt = yarnInwardStockDetailsRepository.findById(yarnIssue.getInwardId())
//                         .filter(detail -> matchesInwardDetailForYarnIssue(yarnIssue, detail, finalSearchItemId, finalTargetLot));
//                 if (inwardDetailOpt.isPresent()) {
//                     return inwardDetailOpt;
//                 }
//             }
//         } catch (Exception e) {
//             inwardDetailOpt = Optional.empty();
//         }

//         try {
//             if (!inwardDetailOpt.isPresent() && yarnIssue.getInwardId() != null && yarnIssue.getInwardId() != 0
//                     && searchItemId != null && !targetLot.isEmpty()) {
//                 List<YarnInwardStockDetails> details = yarnInwardStockDetailsRepository.findByYarnInventoryIdAndItemIdAndLotNo(
//                         yarnIssue.getInwardId(), searchItemId, targetLot);
//                 if (!details.isEmpty()) {
//                     return Optional.of(details.get(0));
//                 }
//             }
//         } catch (Exception e) {
//             inwardDetailOpt = Optional.empty();
//         }

//         try {
//             if (!inwardDetailOpt.isPresent() && yarnIssue.getOrderItemId() != null && yarnIssue.getOrderItemId() != 0) {
//                 inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnIssue.getOrderItemId())
//                         .filter(detail -> matchesInwardDetailForYarnIssue(yarnIssue, detail, finalSearchItemId, finalTargetLot));
//                 if (inwardDetailOpt.isPresent()) {
//                     return inwardDetailOpt;
//                 }
//             }
//         } catch (Exception e) {
//             inwardDetailOpt = Optional.empty();
//         }

//         if (!inwardDetailOpt.isPresent() && searchItemId != null) {
//             List<YarnInwardStockDetails> matchingInwards = yarnInwardStockDetailsRepository.findByItemId(searchItemId)
//                     .stream()
//                     .filter(detail -> matchesInwardDetailForYarnIssue(yarnIssue, detail, finalSearchItemId, finalTargetLot))
//                     .collect(Collectors.toList());
//             if (!matchingInwards.isEmpty()) {
//                 inwardDetailOpt = Optional.of(matchingInwards.get(0));
//             }
//         }

//         return inwardDetailOpt;
//     }
// }
package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeavingYarnIssueDTO;
import com.tech3.erp.dto.WeavingYarnRequirementDTO;
import com.tech3.erp.dto.YarnIssueDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.WeavingYarnIssue;
import com.tech3.erp.entity.WeavingYarnRequirement;
import com.tech3.erp.entity.YarnIssue;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnOutwardStockDetails;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.entity.Company;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WeavingYarnIssueRepository;
import com.tech3.erp.repository.WeavingYarnOutwardDetailRepository;
import com.tech3.erp.repository.WeavingYarnRequirementRepository;
import com.tech3.erp.service.NotificationService;
import com.tech3.erp.repository.WeftInwardRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnIssueRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.entity.WeftInward;
import com.tech3.erp.entity.WeftInwardItem;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class WeavingYarnIssueService {

    @Autowired
    private WeavingYarnIssueRepository weavingYarnIssueRepository;
    
    @Autowired
    private WeavingContractRepository weavingContractRepository;
    
    @Autowired
    private WeavingWorkOrderRepository weavingWorkOrderRepository;
    
    @Autowired
    private WeftInwardRepository weftInwardRepository;
    
    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
    @Autowired
    private YarnOutwardRepository yarnOutwardRepository;
    
    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
    
    @Autowired
    private YarnMasterRepository yarnMasterRepository;
    
    @Autowired
    private WeavingYarnOutwardDetailRepository weavingYarnOutwardDetailRepository;
    
    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;

    @Autowired
    private YarnIssueRepository yarnIssueRepository;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WeavingYarnRequirementRepository weavingYarnRequirementRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<WeavingYarnIssueDTO> getAllWeavingYarnIssues() {
        return weavingYarnIssueRepository.findAll().stream()
        		.sorted(Comparator.comparing(WeavingYarnIssue::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PaginatedResponse<WeavingYarnIssueDTO> getAllWeavingYarnIssuesWithPagination(Long offset, Long limit) {
        long finalLimit = (limit != null && limit > 0) ? limit : 10L;
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        Page<WeavingYarnIssue> page = weavingYarnIssueRepository.findAll(
                PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
        );
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }
    
    @Transactional(readOnly = true)
    public List<WeavingYarnIssueDTO> getAllWeavingYarnIssuesByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return weavingYarnIssueRepository.findByTradingCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PaginatedResponse<WeavingYarnIssueDTO> getAllWeavingYarnIssuesByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        long finalLimit = (limit != null && limit > 0) ? limit : 10L;
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        Page<WeavingYarnIssue> page = weavingYarnIssueRepository.findByTradingCompanyId(
                companyId,
                PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
        );
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }
    
    @Transactional(readOnly = true)
    public WeavingYarnIssueDTO getWeavingYarnIssueByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        WeavingYarnIssue issue = weavingYarnIssueRepository.findByIdAndTradingCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        return convertToDTO(issue);
    }

    public WeavingYarnIssueDTO createWeavingYarnIssue(WeavingYarnIssueDTO dto) {
        // Clear IDs for create operation to avoid detached entity issues if IDs are passed by mistake
        dto.setId(null);
        if (dto.getRequirements() != null) {
            dto.getRequirements().forEach(req -> {
                req.setId(null);
                if (req.getYarnIssues() != null) {
                    req.getYarnIssues().forEach(yi -> yi.setId(null));
                }
            });
        }
        
        validateYarnIssue(dto);
        requireWeavingContractReference(dto.getWeavingContractId());
        
        WeavingYarnIssue issue = convertToEntity(dto);
        
        WeavingYarnIssue savedIssue = weavingYarnIssueRepository.save(issue);

        if (StringUtils.isEmpty(savedIssue.getYarnIssueChallanNo())) {
            savedIssue.setYarnIssueChallanNo(generateYarnIssueChallanNo(savedIssue.getYarnIssueDate(), savedIssue.getId(), savedIssue.getTradingCompanyId()));
            savedIssue = weavingYarnIssueRepository.save(savedIssue);
        }

        syncWeftInwardEntry(savedIssue);
        processLotOutward(savedIssue, "WEAVING_CONTRACT", savedIssue.getId());

        checkAndNotifyWeavingContractBalanceZero(savedIssue);

        return convertToDTO(savedIssue);
    }

    private String generateYarnIssueChallanNo(LocalDate issueDate, Long id, Long companyId) {
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
        if (issueDate == null) issueDate = LocalDate.now();
        String fy = getFinancialYearString(issueDate);
        String prefix = cmpPrefix + "WYI/" + fy + "/";
        String sequence = String.format("%04d", id);
        return prefix + sequence;
    }

    private String getFinancialYearString(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int fyStartYear = (month >= 4) ? year : year - 1;
        int fy1 = fyStartYear % 100;
        int fy2 = (fyStartYear + 1) % 100;
        return String.format("%02d-%02d", fy1, fy2);
    }

    public WeavingYarnIssueDTO updateWeavingYarnIssue(Long id, WeavingYarnIssueDTO dto) {
        validateYarnIssue(dto);
        
        WeavingYarnIssue existingIssue = weavingYarnIssueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        
        reverseLotReductions(existingIssue, true);
        updateIssueFromDTO(existingIssue, dto);
        
        WeavingYarnIssue savedIssue = weavingYarnIssueRepository.save(existingIssue);
        syncWeftInwardEntry(savedIssue);
        processLotOutward(savedIssue, "WEAVING_CONTRACT", savedIssue.getId());
        checkAndNotifyWeavingContractBalanceZero(savedIssue);
        return convertToDTO(savedIssue);
    }
    
    public WeavingYarnIssueDTO updateWeavingYarnIssueByCompany(Long companyId, Long id, WeavingYarnIssueDTO dto) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        validateYarnIssue(dto);
        WeavingYarnIssue existingIssue = weavingYarnIssueRepository.findByIdAndTradingCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        
        reverseLotReductions(existingIssue, true);
        updateIssueFromDTO(existingIssue, dto);
        
        WeavingYarnIssue savedIssue = weavingYarnIssueRepository.save(existingIssue);
        syncWeftInwardEntry(savedIssue);
        processLotOutward(savedIssue, "WEAVING_CONTRACT", savedIssue.getId());
        checkAndNotifyWeavingContractBalanceZero(savedIssue);
        return convertToDTO(savedIssue);
    }

    private void checkAndNotifyWeavingContractBalanceZero(WeavingYarnIssue savedIssue) {
        Long weavingContractId = savedIssue.getWeavingContractId();
        String weavingContractNo = savedIssue.getWeavingContractNo();
        Long companyId = savedIssue.getTradingCompanyId();
        if (weavingContractId == null || companyId == null) return;
        List<WeavingYarnRequirement> requirements = savedIssue.getRequirements().stream()
                .filter(r -> r.isActiveFlag())
                .collect(Collectors.toList());
        if (requirements.isEmpty()) return;
        boolean allBalanceZero = requirements.stream()
                .allMatch(r -> r.getBalanceToIssue() != null && r.getBalanceToIssue().compareTo(BigDecimal.ZERO) <= 0);
        if (allBalanceZero) {
            notificationService.createAndPush(
                    companyId,
                    null,
                    NotificationService.EVENT_WEAVING_CONTRACT_BALANCE_ZERO,
                    weavingContractId,
                    null,
                    weavingContractNo
            );
        }
    }

    private void updateIssueFromDTO(WeavingYarnIssue existingIssue, WeavingYarnIssueDTO dto) {
        requireWeavingContractReference(dto.getWeavingContractId());
        existingIssue.setVendorId(dto.getVendorId());
        existingIssue.setWeavingContractId(dto.getWeavingContractId());
        existingIssue.setWeavingContractNo(dto.getWeavingContractNo());
        existingIssue.setWeavingJobWorkOrderId(dto.getWeavingJobWorkOrderId());
        existingIssue.setTransportationDetails(dto.getTransportationDetails());
        existingIssue.setTransportId(dto.getTransportId());
        existingIssue.setTermsConditionsId(dto.getTermsConditionsId());
        existingIssue.setFabricDetails(dto.getFabricDetails());
        existingIssue.setYarnIssueDate(dto.getYarnIssueDate());
        existingIssue.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
        existingIssue.setActiveFlag(dto.isActiveFlag());
        existingIssue.setOrderTypeId(dto.getOrderTypeId());
        existingIssue.setTradingCompanyId(dto.getTradingCompanyId());
        existingIssue.setFactoryCompanyId(dto.getFactoryCompanyId());
        existingIssue.setRemarks(dto.getRemarks());
        existingIssue.setVehicleNo(dto.getVehicleNo());
        existingIssue.setGrossWt(dto.getGrossWt());
        
        if (dto.getRequirements() != null) {
            syncRequirements(existingIssue, dto.getRequirements());
        }
    }

    private void syncRequirements(WeavingYarnIssue existingIssue, List<WeavingYarnRequirementDTO> requirementDTOs) {
        Set<Long> incomingIds = requirementDTOs.stream()
                .map(r -> (r.getId() != null && r.getId() != 0) ? r.getId() : null)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        // Remove orphans
        existingIssue.getRequirements().removeIf(r -> r.getId() != null && !incomingIds.contains(r.getId()));

        Map<Long, WeavingYarnRequirement> existingMap = existingIssue.getRequirements().stream()
                .filter(r -> r.getId() != null)
                .collect(Collectors.toMap(WeavingYarnRequirement::getId, r -> r));

        for (WeavingYarnRequirementDTO reqDTO : requirementDTOs) {
            Long reqId = (reqDTO.getId() != null && reqDTO.getId() != 0) ? reqDTO.getId() : null;
            if (reqId != null && existingMap.containsKey(reqId)) {
                updateRequirementFromDTO(existingMap.get(reqId), reqDTO);
            } else {
                WeavingYarnRequirement requirement = convertRequirementToEntity(reqDTO);
                requirement.setWeavingYarnIssue(existingIssue);
                existingIssue.getRequirements().add(requirement);
            }
        }
    }

    private void updateRequirementFromDTO(WeavingYarnRequirement entity, WeavingYarnRequirementDTO dto) {
        entity.setYarnName(dto.getYarnName());
        entity.setActualYarn(dto.getActualYarn());
        entity.setYarnId(dto.getYarnId());
        entity.setActualYarnId(dto.getActualYarnId());
        entity.setGramsPerMeter(dto.getGramsPerMeter());
        entity.setTotalReqQty(dto.getTotalReqQty());
        entity.setTotalIssueQty(dto.getTotalIssueQty());
        entity.setBalanceToIssue(dto.getBalanceToIssue());
        entity.setActiveFlag(dto.isActiveFlag());

        if (dto.getYarnIssues() != null) {
            syncYarnIssues(entity, dto.getYarnIssues());
        }
    }

    private void syncYarnIssues(WeavingYarnRequirement existingRequirement, List<YarnIssueDTO> yarnIssueDTOs) {
        Set<Long> incomingIds = yarnIssueDTOs.stream()
                .map(yi -> (yi.getId() != null && yi.getId() != 0) ? yi.getId() : null)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        // Remove orphans
        existingRequirement.getYarnIssues().removeIf(yi -> yi.getId() != null && !incomingIds.contains(yi.getId()));

        Map<Long, YarnIssue> existingMap = existingRequirement.getYarnIssues().stream()
                .filter(yi -> yi.getId() != null)
                .collect(Collectors.toMap(YarnIssue::getId, yi -> yi));

        for (YarnIssueDTO yiDTO : yarnIssueDTOs) {
            Long yiId = (yiDTO.getId() != null && yiDTO.getId() != 0) ? yiDTO.getId() : null;
            if (yiId != null && existingMap.containsKey(yiId)) {
                updateYarnIssueFromDTO(existingMap.get(yiId), yiDTO);
            } else {
                YarnIssue yarnIssue = convertYarnIssueToEntity(yiDTO);
                yarnIssue.setWeavingYarnRequirement(existingRequirement);
                existingRequirement.getYarnIssues().add(yarnIssue);
            }
        }
    }

    private void updateYarnIssueFromDTO(YarnIssue entity, YarnIssueDTO dto) {
        entity.setYarnName(dto.getYarnName());
        entity.setActualYarn(dto.getActualYarn());
        entity.setYarnId(dto.getYarnId());
        entity.setActualYarnId(dto.getActualYarnId());
        entity.setMillId(dto.getMillId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setAvailableReqQty(dto.getAvailableReqQty());
        entity.setIssueQty(dto.getIssueQty());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConesPerBag(dto.getConesPerBag());
        entity.setConesWeight(dto.getConesWeight());
        entity.setLotNumber(dto.getLotNumber());
        entity.setLotAvailableBags(dto.getLotAvailableBags());
        if (dto.getInwardId() != null && dto.getInwardId() != 0) {
            entity.setInwardId(dto.getInwardId());
        }
        if (dto.getOrderItemId() != null && dto.getOrderItemId() != 0) {
            entity.setOrderItemId(dto.getOrderItemId());
        }
        entity.setLoomId(dto.getLoomId());
        entity.setMachineId(dto.getMachineId());
        entity.setBeamGaitingId(dto.getBeamGaitingId());
        entity.setIssueType(dto.getIssueType());
        entity.setActiveFlag(dto.isActiveFlag());
    }

    public void deleteWeavingYarnIssue(Long id) {
        WeavingYarnIssue issue = weavingYarnIssueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        
        reverseLotReductions(issue, true);
        cleanupWeftInward(issue);
        
        weavingYarnIssueRepository.delete(issue);
    }
    
    public boolean isMapped(Long id) {
        if (weftInwardRepository.existsByWeavingYarnIssueId(id)) return true;
        return false;
    }

    public void deleteWeavingYarnIssueByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        WeavingYarnIssue issue = weavingYarnIssueRepository.findByIdAndTradingCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Issue not found"));
        reverseLotReductions(issue, true);
        cleanupWeftInward(issue);
        weavingYarnIssueRepository.delete(issue);
    }

    private void processLotReductions(WeavingYarnIssue issue) {
    }

    private void cleanupWeftInward(WeavingYarnIssue issue) {
        if (issue == null || issue.getId() == null) {
            return;
        }
        weftInwardRepository.findByWeavingYarnIssueId(issue.getId()).ifPresent(weftInward -> {
            String inwardNo = weftInward.getInwardNo();
            if (StringUtils.hasText(inwardNo)) {
                if (weavingYarnOutwardDetailRepository.existsByWeftInwardNo(inwardNo)) {
                    throw new IllegalStateException(inwardNo + " already mapped in weaving yarn outward");
                }
                // Also delete from YarnInventory if exists
                yarnInventoryRepository.findByInwardNo(inwardNo).ifPresent(yarnInventoryRepository::delete);
            }
            weftInwardRepository.delete(weftInward);
        });
    }

    private void syncWeftInwardEntry(WeavingYarnIssue issue) {
        if (issue == null || issue.getId() == null) {
            return;
        }
        if (issue.getOrderTypeId() == null || issue.getOrderTypeId() != 1L) {
            return;
        }
        Long jobWorkOrderId = resolveWeavingJobWorkOrderId(issue);
        WeftInward weftInward = weftInwardRepository.findByWeavingYarnIssueId(issue.getId()).orElseGet(WeftInward::new);
        boolean isNew = weftInward.getId() == null;
        weftInward.setInwardDate(issue.getYarnIssueDate());
        weftInward.setWeavingContractId(issue.getWeavingContractId());
        weftInward.setWeavingJobWorkOrderId(jobWorkOrderId);
        weftInward.setWeavingYarnIssueId(issue.getId());
        weftInward.setDcNo(issue.getYarnIssueChallanNo());
        weftInward.setDcDate(issue.getYarnIssueDate());
        weftInward.setActiveFlag(issue.isActiveFlag());
        weftInward.setOrderTypeId(issue.getOrderTypeId());
        weftInward.setCompanyId(issue.getFactoryCompanyId());
        weftInward.setSourceCompanyId(issue.getTradingCompanyId());
        weftInward.setDestinationCompanyId(issue.getFactoryCompanyId());
        weftInward.setTradingCompanyId(issue.getTradingCompanyId());
        weftInward.setFactoryCompanyId(issue.getFactoryCompanyId());

        Map<Long, WeftInwardItem> existingItemsById = weftInward.getYarnItems().stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(WeftInwardItem::getId, item -> item, (first, second) -> first));
        Map<String, List<WeftInwardItem>> existingItemsByKey = weftInward.getYarnItems().stream()
                .collect(Collectors.groupingBy(this::buildWeftInwardItemKey));
        List<WeftInwardItem> sourceItems = buildWeftInwardItemsFromIssue(issue, weftInward);
        List<WeftInwardItem> yarnItems = new ArrayList<>();
        for (WeftInwardItem sourceItem : sourceItems) {
            WeftInwardItem targetItem = sourceItem.getId() != null ? existingItemsById.remove(sourceItem.getId()) : null;
            if (targetItem == null) {
                List<WeftInwardItem> matches = existingItemsByKey.get(buildWeftInwardItemKey(sourceItem));
                if (matches != null && !matches.isEmpty()) {
                    targetItem = matches.remove(0);
                }
            }
            if (targetItem == null) {
                targetItem = sourceItem;
                targetItem.setId(null);
            } else {
                mergeWeftInwardItem(targetItem, sourceItem);
            }
            if (targetItem.getMillName() == null) {
                targetItem.setMillName(sourceItem.getMillName());
            }
            targetItem.setWeftInward(weftInward);
            yarnItems.add(targetItem);
        }
        weftInward.getYarnItems().clear();
        weftInward.getYarnItems().addAll(yarnItems);
        WeftInward savedInward = weftInwardRepository.save(weftInward);
        if (isNew || !StringUtils.hasText(savedInward.getInwardNo())) {
            String datePartWI = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            savedInward.setInwardNo("WI-" + datePartWI + "-" + savedInward.getId());
            savedInward = weftInwardRepository.save(savedInward);
        }
        syncYarnInventoryEntry(issue, savedInward);
    }

    private void syncYarnInventoryEntry(WeavingYarnIssue issue, WeftInward weftInward) {
        if (issue == null || issue.getOrderTypeId() == null || issue.getOrderTypeId() != 1L) {
            return;
        }

        YarnInventory yarnInventory = yarnInventoryRepository.findByInwardNo(weftInward.getInwardNo()).orElseGet(YarnInventory::new);

        yarnInventory.setInwardId(weftInward.getId());
        Long inwardTypeId = codesDetailRepository.findByValue("weft-yarn-inward")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("FACTORY_YARN_INWARD")
                        .map(CodesDetail::getId)
                        .orElse(2L));

        yarnInventory.setInwardTypeId(inwardTypeId);
        yarnInventory.setInwardNo(weftInward.getInwardNo());
        yarnInventory.setCompanyId(issue.getTradingCompanyId());
        yarnInventory.setInwardDate(issue.getYarnIssueDate() != null ? issue.getYarnIssueDate().atStartOfDay() : LocalDateTime.now());
        yarnInventory.setFinancialYear(calculateFinancialYear(issue.getYarnIssueDate()));
        yarnInventory.setDcNo(issue.getYarnIssueChallanNo());
        yarnInventory.setDcDate(issue.getYarnIssueDate());

        Long jobWorkOrderId = resolveWeavingJobWorkOrderId(issue);
        String jobWorkOrderNo = null;
        if (jobWorkOrderId != null) {
            jobWorkOrderNo = weavingWorkOrderRepository.findById(jobWorkOrderId)
                    .map(WeavingWorkOrder::getWeavingWorkOrderNo).orElse(null);
        }

        yarnInventory.setOrderId(jobWorkOrderId != null ? jobWorkOrderId : issue.getWeavingContractId());
        yarnInventory.setOrderNo(jobWorkOrderNo != null ? jobWorkOrderNo : issue.getWeavingContractNo());
        yarnInventory.setVendorId(issue.getVendorId());

        Map<Long, YarnInwardStockDetails> existingDetailsByOrderItemId = new HashMap<>();
        Map<Long, YarnInwardStockDetails> existingDetailsById = new HashMap<>();
        Map<String, List<YarnInwardStockDetails>> existingDetailsByKey = new HashMap<>();
        if (yarnInventory.getDetails() != null) {
            yarnInventory.getDetails().forEach(detail -> {
                if (detail.getOrderItemId() != null) {
                    existingDetailsByOrderItemId.put(detail.getOrderItemId(), detail);
                }
                if (detail.getId() != null) {
                    existingDetailsById.put(detail.getId(), detail);
                }
                existingDetailsByKey
                        .computeIfAbsent(buildYarnInwardStockDetailKey(detail), key -> new ArrayList<>())
                        .add(detail);
            });
        }

        List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();

        if (weftInward.getYarnItems() != null) {
            for (WeftInwardItem item : weftInward.getYarnItems()) {
                YarnIssue matchingIssue = findMatchingYarnIssue(issue, item);
                YarnInwardStockDetails detail = null;

                if (item.getId() != null) {
                    detail = existingDetailsByOrderItemId.remove(item.getId());
                }
                if (detail == null && matchingIssue != null && matchingIssue.getInwardId() != null) {
                    detail = existingDetailsById.remove(matchingIssue.getInwardId());
                }
                if (detail == null) {
                    List<YarnInwardStockDetails> matches = existingDetailsByKey.get(buildWeftInwardItemKey(item));
                    if (matches != null && !matches.isEmpty()) {
                        detail = matches.remove(0);
                    }
                }
                if (detail == null) {
                    detail = new YarnInwardStockDetails();
                    detail.setYarnInventory(yarnInventory);
                }

                removeStockDetailFromMaps(detail, existingDetailsByOrderItemId, existingDetailsById, existingDetailsByKey);

                BigDecimal inwardQty = item.getTotalKgs() != null ? BigDecimal.valueOf(item.getTotalKgs()) : BigDecimal.ZERO;
                BigDecimal inwardBags = item.getNoOfBags() != null ? BigDecimal.valueOf(item.getNoOfBags()) : BigDecimal.ZERO;

                detail.setOrderItemId(item.getId());
                detail.setItemId(item.getYarnId());
                detail.setMillId(item.getMillId());
                detail.setLotNo(item.getLotNo());
                detail.setInwardQty(inwardQty);
                detail.setAcceptedQty(inwardQty);
                detail.setInwardBags(inwardBags);
                detail.setKgsPerBag(item.getKgsPerBag() != null ? BigDecimal.valueOf(item.getKgsPerBag()) : BigDecimal.ZERO);
                if (matchingIssue != null) {
                    detail.setConesPerBag(matchingIssue.getConesPerBag() != null ? matchingIssue.getConesPerBag() : BigDecimal.ZERO);
                    detail.setConesWeight(matchingIssue.getConesWeight() != null ? matchingIssue.getConesWeight() : BigDecimal.ZERO);
                }
                detail.setWarehouseId(item.getWarehouseId());
                detail.setInwardTypeId(inwardTypeId);
                recalculateAvailableFromOutward(detail, inwardQty, inwardBags);
                updatedDetails.add(detail);

                if (yarnInventory.getWarehouseId() == null) {
                    yarnInventory.setWarehouseId(item.getWarehouseId());
                }
            }
        }

        if (yarnInventory.getDetails() != null) {
            yarnInventory.getDetails().clear();
            yarnInventory.getDetails().addAll(updatedDetails);
        } else {
            yarnInventory.setDetails(updatedDetails);
        }
        YarnInventory savedInventory = yarnInventoryRepository.save(yarnInventory);
        syncYarnIssueStockReferences(issue, savedInventory);
    }

    private void syncYarnIssueStockReferences(WeavingYarnIssue issue, YarnInventory yarnInventory) {
        if (issue == null || issue.getRequirements() == null || yarnInventory == null || yarnInventory.getDetails() == null) {
            return;
        }

        Map<Long, YarnInwardStockDetails> detailsByOrderItemId = new HashMap<>();
        Map<Long, YarnInwardStockDetails> detailsById = new HashMap<>();
        Map<String, List<YarnInwardStockDetails>> detailsByKey = new HashMap<>();
        for (YarnInwardStockDetails detail : yarnInventory.getDetails()) {
            if (detail.getOrderItemId() != null) {
                detailsByOrderItemId.put(detail.getOrderItemId(), detail);
            }
            if (detail.getId() != null) {
                detailsById.put(detail.getId(), detail);
            }
            detailsByKey.computeIfAbsent(buildYarnInwardStockDetailKey(detail), key -> new ArrayList<>()).add(detail);
        }

        for (WeavingYarnRequirement requirement : issue.getRequirements()) {
            if (requirement.getYarnIssues() == null) {
                continue;
            }
            for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
                boolean hasSourceStockReference = (yarnIssue.getOrderItemId() != null && yarnIssue.getOrderItemId() != 0)
                        || (yarnIssue.getInwardId() != null && yarnIssue.getInwardId() != 0);
                if (hasSourceStockReference) {
                    continue;
                }

                YarnInwardStockDetails matchingDetail = null;
                if (yarnIssue.getOrderItemId() != null) {
                    matchingDetail = detailsByOrderItemId.get(yarnIssue.getOrderItemId());
                }
                if (matchingDetail == null && yarnIssue.getInwardId() != null) {
                    matchingDetail = detailsById.get(yarnIssue.getInwardId());
                }
                if (matchingDetail == null) {
                    List<YarnInwardStockDetails> matches = detailsByKey.get(buildYarnIssueKey(yarnIssue));
                    if (matches != null && !matches.isEmpty()) {
                        matchingDetail = matches.get(0);
                    }
                }
                if (matchingDetail != null) {
                    yarnIssue.setOrderItemId(matchingDetail.getOrderItemId());
                    yarnIssue.setInwardId(matchingDetail.getId());
                    yarnIssue.setInwardTypeId(matchingDetail.getInwardTypeId());
                }
            }
        }
    }

    private YarnIssue findMatchingYarnIssue(WeavingYarnIssue issue, WeftInwardItem item) {
        if (issue == null || issue.getRequirements() == null || item == null) {
            return null;
        }

        String itemKey = buildWeftInwardItemKey(item);
        for (WeavingYarnRequirement requirement : issue.getRequirements()) {
            if (requirement.getYarnIssues() == null) {
                continue;
            }
            for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
                if (item.getId() != null && yarnIssue.getOrderItemId() != null && item.getId().equals(yarnIssue.getOrderItemId())) {
                    return yarnIssue;
                }
                if (buildYarnIssueKey(yarnIssue).equals(itemKey)) {
                    return yarnIssue;
                }
            }
        }
        return null;
    }

    private void removeStockDetailFromMaps(YarnInwardStockDetails detail,
                                           Map<Long, YarnInwardStockDetails> detailsByOrderItemId,
                                           Map<Long, YarnInwardStockDetails> detailsById,
                                           Map<String, List<YarnInwardStockDetails>> detailsByKey) {
        if (detail == null) {
            return;
        }
        if (detail.getOrderItemId() != null) {
            detailsByOrderItemId.remove(detail.getOrderItemId());
        }
        if (detail.getId() != null) {
            detailsById.remove(detail.getId());
        }
        List<YarnInwardStockDetails> keyedDetails = detailsByKey.get(buildYarnInwardStockDetailKey(detail));
        if (keyedDetails != null) {
            keyedDetails.remove(detail);
        }
    }

    private String buildYarnInwardStockDetailKey(YarnInwardStockDetails detail) {
        if (detail == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(detail.getItemId() != null ? detail.getItemId() : 0L),
                String.valueOf(detail.getMillId() != null ? detail.getMillId() : 0L),
                String.valueOf(detail.getWarehouseId() != null ? detail.getWarehouseId() : 0L),
                normalizeLot(detail.getLotNo()));
    }

    private String buildYarnIssueKey(YarnIssue yarnIssue) {
        if (yarnIssue == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(yarnIssue.getYarnId() != null ? yarnIssue.getYarnId() : 0L),
                String.valueOf(yarnIssue.getMillId() != null ? yarnIssue.getMillId() : 0L),
                String.valueOf(yarnIssue.getWarehouseId() != null ? yarnIssue.getWarehouseId() : 0L),
                normalizeLot(yarnIssue.getLotNumber()));
    }

    private void recalculateAvailableFromOutward(YarnInwardStockDetails detail, BigDecimal inwardQty, BigDecimal inwardBags) {
        BigDecimal totalIssuedQty = BigDecimal.ZERO;
        BigDecimal totalIssuedBags = BigDecimal.ZERO;

        if (detail.getId() != null) {
            totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
            totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());
        }

        BigDecimal availableQty = inwardQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
        BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);

        detail.setAvailableQty(availableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableQty);
        detail.setAvailableBags(availableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBags);
    }

    private void mergeWeftInwardItem(WeftInwardItem targetItem, WeftInwardItem sourceItem) {
        targetItem.setYarnId(sourceItem.getYarnId());
        targetItem.setYarnName(sourceItem.getYarnName());
        targetItem.setActualYarnId(sourceItem.getActualYarnId());
        targetItem.setActualYarn(sourceItem.getActualYarn());
        targetItem.setNoOfBags(sourceItem.getNoOfBags());
        targetItem.setKgsPerBag(sourceItem.getKgsPerBag());
        targetItem.setTotalKgs(sourceItem.getTotalKgs());
        targetItem.setMillId(sourceItem.getMillId());
        targetItem.setMillName(sourceItem.getMillName() != null ? sourceItem.getMillName() : targetItem.getMillName());
        targetItem.setWarehouseId(sourceItem.getWarehouseId());
        targetItem.setLotNo(sourceItem.getLotNo());
        targetItem.setActiveFlag(sourceItem.getActiveFlag());
    }

    private String buildWeftInwardItemKey(WeftInwardItem item) {
        if (item == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(item.getYarnId() != null ? item.getYarnId() : 0L),
                String.valueOf(item.getMillId() != null ? item.getMillId() : 0L),
                String.valueOf(item.getWarehouseId() != null ? item.getWarehouseId() : 0L),
                normalizeLot(item.getLotNo()));
    }

    private String normalizeLot(String lotNo) {
        return lotNo == null ? "" : lotNo.trim().toLowerCase();
    }

    private String calculateFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        if (month >= 4) {
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        } else {
            int previousYear = year - 1;
            return String.format("%d-%d", previousYear, year % 100);
        }
    }

    private List<WeftInwardItem> buildWeftInwardItemsFromIssue(WeavingYarnIssue issue, WeftInward weftInward) {
        List<WeftInwardItem> items = new ArrayList<>();
        if (issue.getRequirements() == null) {
            return items;
        }
        for (WeavingYarnRequirement requirement : issue.getRequirements()) {
            if (requirement.getYarnIssues() == null) {
                continue;
            }
            for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
                WeftInwardItem item = new WeftInwardItem();
                item.setId(yarnIssue.getOrderItemId());
                item.setWeftInward(weftInward);
                item.setYarnId(yarnIssue.getYarnId());
                item.setYarnName(yarnIssue.getYarnName());
                item.setActualYarnId(yarnIssue.getActualYarnId());
                item.setActualYarn(yarnIssue.getActualYarn());
                item.setNoOfBags(yarnIssue.getNoOfBags());
                item.setKgsPerBag(yarnIssue.getKgsPerBag());
                item.setTotalKgs(yarnIssue.getIssueQty() != null ? yarnIssue.getIssueQty().doubleValue() : null);
                item.setMillId(yarnIssue.getMillId());
                item.setWarehouseId(yarnIssue.getWarehouseId());
                item.setLotNo(yarnIssue.getLotNumber());
                item.setActiveFlag(yarnIssue.isActiveFlag());
                items.add(item);
            }
        }
        return items;
    }

    public void prepareOutwardReferencesForWeftInwardSync(Long issueId) {
        if (issueId == null) {
            return;
        }
        weavingYarnIssueRepository.findById(issueId)
                .ifPresent(issue -> reverseLotReductions(issue, true));
    }

    private void reverseLotReductions(WeavingYarnIssue issue, boolean clearOutwardDetails) {
        String remark = "Weaving yarn issue - " + issue.getId();
        List<YarnOutward> outwards = new ArrayList<>(yarnOutwardRepository.findByRemarks(remark));
        if (outwards.isEmpty() && issue.getYarnIssueChallanNo() != null) {
            yarnOutwardRepository.findByOutwardNo(issue.getYarnIssueChallanNo()).ifPresent(outwards::add);
        }
        
        for (YarnOutward outward : outwards) {
            List<YarnOutwardStockDetails> outwardDetails = outward.getYarnOutwardStockDetails() != null
                    ? new ArrayList<>(outward.getYarnOutwardStockDetails())
                    : Collections.emptyList();

            for (YarnOutwardStockDetails detail : outwardDetails) {
                Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();
                
                if (detail.getYarnInwardStockDetailsId() != null && detail.getYarnInwardStockDetailsId() != 0) {
                    try {
                        inwardDetailOpt = yarnInwardStockDetailsRepository.findById(detail.getYarnInwardStockDetailsId());
                    } catch (Exception e) {
                        inwardDetailOpt = Optional.empty();
                    }
                }
                
                if (!inwardDetailOpt.isPresent() && detail.getOrderItemId() != null && detail.getOrderItemId() != 0) {
                    inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(detail.getOrderItemId());
                }

                if (inwardDetailOpt.isPresent()) {
                    YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
                    BigDecimal qtyToRestore = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
                    BigDecimal bagsToRestore = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
                    
                    if (inwardDetail.getAvailableQty() == null) {
                        inwardDetail.setAvailableQty(inwardDetail.getAcceptedQty() != null ? inwardDetail.getAcceptedQty() : BigDecimal.ZERO);
                    }
                    if (inwardDetail.getAvailableBags() == null) {
                        inwardDetail.setAvailableBags(inwardDetail.getInwardBags() != null ? inwardDetail.getInwardBags() : BigDecimal.ZERO);
                    }
                    
                    inwardDetail.setAvailableQty(inwardDetail.getAvailableQty().add(qtyToRestore));
                    inwardDetail.setAvailableBags(inwardDetail.getAvailableBags().add(bagsToRestore));
                    
                    yarnInwardStockDetailsRepository.save(inwardDetail);
                }
            }

            if (clearOutwardDetails && !outwardDetails.isEmpty()) {
                if (outward.getYarnOutwardStockDetails() != null) {
                    outward.getYarnOutwardStockDetails().clear();
                }
                yarnOutwardRepository.save(outward);
                yarnOutwardRepository.flush();
                yarnOutwardStockDetailsRepository.deleteAll(outwardDetails);
                yarnOutwardStockDetailsRepository.flush();
                yarnOutwardRepository.delete(outward);
            }
        }
        yarnOutwardRepository.flush();
        yarnInwardStockDetailsRepository.flush();
    }
    
    private void processLotOutward(WeavingYarnIssue issue, String referenceType, Long referenceId) {
        String remark = "Weaving yarn issue - " + issue.getId();
        
        YarnOutward yarnOutward = yarnOutwardRepository.findByRemarks(remark).stream().findFirst().orElse(null);
        if (yarnOutward == null && issue.getYarnIssueChallanNo() != null) {
            yarnOutward = yarnOutwardRepository.findByOutwardNo(issue.getYarnIssueChallanNo()).orElse(null);
        }
        
        if (yarnOutward == null) {
            yarnOutward = new YarnOutward();
            yarnOutward.setOutwardNo(issue.getYarnIssueChallanNo() != null ? 
                issue.getYarnIssueChallanNo() : generateYarnOutwardNo(issue));
        }

        yarnOutward.setOutwardTypeId(52L);
        yarnOutward.setCompanyId(issue.getTradingCompanyId() != null ? issue.getTradingCompanyId() : 1L);
        yarnOutward.setOutwardDate(issue.getYarnIssueDate() != null ? issue.getYarnIssueDate() : LocalDate.now());
        yarnOutward.setOrderId(issue.getWeavingContractId());
        
        String orderNo = null;
        if (issue.getWeavingContractId() != null) {
            orderNo = weavingContractRepository.findById(issue.getWeavingContractId())
                .map(WeavingContract::getWeavingContractNo).orElse(null);
        }
        yarnOutward.setOrderNo(orderNo);
        yarnOutward.setFinancialYearId(issue.getYarnIssueDate() != null ? getFinancialYearId(issue.getYarnIssueDate()) : null);
        yarnOutward.setVendorId(issue.getVendorId() != null ? issue.getVendorId() : 0L);
        yarnOutward.setRemarks(remark);
        yarnOutward.setActiveFlag(true);

        // Sync details instead of clearing to preserve IDs
        Map<Long, YarnOutwardStockDetails> existingMapByOrderItemId = new HashMap<>();
        Map<Long, YarnOutwardStockDetails> existingMapByStockDetailsId = new HashMap<>();
        
        if (yarnOutward.getYarnOutwardStockDetails() != null) {
            yarnOutward.getYarnOutwardStockDetails().forEach(d -> {
                if (d.getOrderItemId() != null && d.getOrderItemId() != 0) 
                    existingMapByOrderItemId.put(d.getOrderItemId(), d);
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

        if (issue.getRequirements() != null) {
            for (WeavingYarnRequirement requirement : issue.getRequirements()) {
                if (requirement.getYarnIssues() != null) {
                    for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
                        Optional<YarnInwardStockDetails> inwardDetailOpt = findInwardStockDetails(yarnIssue);

                        if (!inwardDetailOpt.isPresent()) continue;
                        YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
                        
                        // Persist IDs back to yarnIssue
                        yarnIssue.setOrderItemId(inwardDetail.getOrderItemId());
                        yarnIssue.setInwardId(inwardDetail.getId());
                        
                        // Match by stock details ID first so multiple rows with same orderItemId stay distinct
                        YarnOutwardStockDetails outwardDetail = null;
                        if (inwardDetail.getId() != null) {
                            outwardDetail = existingMapByStockDetailsId.remove(inwardDetail.getId());
                        }
                        if (outwardDetail == null && inwardDetail.getOrderItemId() != null) {
                            outwardDetail = existingMapByOrderItemId.remove(inwardDetail.getOrderItemId());
                        }
                        
                        // Clean up the other map if we found it in one
                        if (outwardDetail != null) {
                            existingMapByOrderItemId.values().remove(outwardDetail);
                            existingMapByStockDetailsId.values().remove(outwardDetail);
                        }

                        boolean isNew = false;
                        if (outwardDetail == null) {
                            outwardDetail = new YarnOutwardStockDetails();
                            outwardDetail.setYarnOutward(savedYarnOutward);
                            isNew = true;
                        }

                        outwardDetail.setInwardId(inwardDetail.getYarnInventory() != null ? inwardDetail.getYarnInventory().getId() : null);
                        outwardDetail.setOrderItemId(inwardDetail.getOrderItemId());
                        outwardDetail.setYarnInwardStockDetailsId(inwardDetail.getId());
                        outwardDetail.setOutwardTypeId(52L);
                        outwardDetail.setActualItemId(inwardDetail.getItemId());
                        outwardDetail.setMillId(inwardDetail.getMillId());
                        outwardDetail.setWarehouseId(inwardDetail.getWarehouseId());
                        outwardDetail.setLotNo(inwardDetail.getLotNo());
                        outwardDetail.setIssuedQty(yarnIssue.getIssueQty());
                        outwardDetail.setIssuedBags(yarnIssue.getNoOfBags() != null ? BigDecimal.valueOf(yarnIssue.getNoOfBags()) : BigDecimal.ZERO);
                        outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag() != null ? BigDecimal.valueOf(yarnIssue.getKgsPerBag()) : inwardDetail.getKgsPerBag());
                        outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
                        outwardDetail.setConesWeight(yarnIssue.getConesWeight());

                        if (isNew) currentDetails.add(outwardDetail);
                        updatedDetails.add(outwardDetail);
                    }
                }
            }
        }

        // Remove orphans
        Set<Long> orphanIds = new HashSet<>();
        existingMapByOrderItemId.values().forEach(d -> { if(d.getId() != null) orphanIds.add(d.getId()); });
        existingMapByStockDetailsId.values().forEach(d -> { if(d.getId() != null) orphanIds.add(d.getId()); });
        
        if (!orphanIds.isEmpty()) {
            currentDetails.removeIf(d -> d.getId() != null && orphanIds.contains(d.getId()));
        }

        yarnOutwardRepository.save(savedYarnOutward);
        yarnOutwardRepository.flush();

        if (!updatedDetails.isEmpty()) {
            List<YarnOutwardStockDetails> persistedDetails = yarnOutwardStockDetailsRepository.saveAll(updatedDetails);
            yarnOutwardStockDetailsRepository.flush();
            updateAvailableQtyInInwardStockDetails(persistedDetails);
        }
    }

    private void validateYarnIssue(WeavingYarnIssueDTO dto) {
        if (dto.getRequirements() == null) return;
        for (WeavingYarnRequirementDTO req : dto.getRequirements()) {
            if (req.getYarnIssues() == null) continue;
            for (YarnIssueDTO yarnIssue : req.getYarnIssues()) {
                if (yarnIssue.getIssueQty() == null || yarnIssue.getIssueQty().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                
                Long searchItemId = yarnIssue.getActualYarnId();
                String searchName = yarnIssue.getActualYarn() != null ? yarnIssue.getActualYarn() : yarnIssue.getYarnName();
                
                if (searchItemId == null) {
                    Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(searchName);
                    if (yarnMaster.isPresent()) {
                        searchItemId = yarnMaster.get().getId();
                    } else {
                        throw new EntityNotFoundException("Yarn not found: " + searchName);
                    }
                }
                
                List<YarnInwardStockDetails> inwardDetails = 
                    yarnInwardStockDetailsRepository.findByItemId(searchItemId);
                
                if (inwardDetails.isEmpty()) {
                    throw new IllegalArgumentException("No inward stock available for yarn: " + searchName);
                }
                
                BigDecimal totalAvailable = inwardDetails.stream()
                    .map(detail -> detail.getAvailableQty() != null ? detail.getAvailableQty() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                if (totalAvailable.compareTo(yarnIssue.getIssueQty()) < 0) {
                    throw new IllegalArgumentException(
                        String.format("Insufficient quantity in inward stock for yarn %s (Available: %s, Requested: %s)",
                            searchName, totalAvailable, yarnIssue.getIssueQty()));
                }
            }
        }
    }

    private WeavingYarnIssueDTO convertToDTO(WeavingYarnIssue entity) {
        WeavingYarnIssueDTO dto = new WeavingYarnIssueDTO();
        dto.setId(entity.getId());
        dto.setVendorId(entity.getVendorId());
        dto.setWeavingContractId(entity.getWeavingContractId());
        dto.setWeavingContractNo(entity.getWeavingContractNo());
        dto.setWeavingJobWorkOrderId(entity.getWeavingJobWorkOrderId());
        dto.setTransportationDetails(entity.getTransportationDetails());
        dto.setTransportId(entity.getTransportId());
        dto.setTermsConditionsId(entity.getTermsConditionsId());
        dto.setFabricDetails(entity.getFabricDetails());
        dto.setYarnIssueDate(entity.getYarnIssueDate());
        dto.setYarnIssueChallanNo(entity.getYarnIssueChallanNo());
        dto.setActiveFlag(entity.isActiveFlag());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setTradingCompanyId(entity.getTradingCompanyId());
        dto.setFactoryCompanyId(entity.getFactoryCompanyId());
        dto.setRemarks(entity.getRemarks());
        dto.setVehicleNo(entity.getVehicleNo());
        dto.setGrossWt(entity.getGrossWt());

        if (entity.getRequirements() != null) {
            List<WeavingYarnRequirementDTO> requirementDTOs = entity.getRequirements().stream()
                    .map(this::convertRequirementToDTO)
                    .collect(Collectors.toList());
            dto.setRequirements(requirementDTOs);
        }

        return dto;
    }
    
    private WeavingYarnRequirementDTO convertRequirementToDTO(WeavingYarnRequirement requirement) {
        WeavingYarnRequirementDTO dto = new WeavingYarnRequirementDTO();
        dto.setId(requirement.getId());
        dto.setYarnName(requirement.getYarnName());
        dto.setActualYarn(requirement.getActualYarn());
        dto.setYarnId(requirement.getYarnId());
        dto.setActualYarnId(requirement.getActualYarnId());
        dto.setGramsPerMeter(requirement.getGramsPerMeter());
        dto.setTotalReqQty(requirement.getTotalReqQty());
        dto.setTotalIssueQty(requirement.getTotalIssueQty());
        dto.setBalanceToIssue(requirement.getBalanceToIssue());
        dto.setActiveFlag(requirement.isActiveFlag());

// Calculate OVERALL total issued quantity from outward stock details across the whole contract
        BigDecimal overallTotalIssued = BigDecimal.ZERO;
        if (requirement.getWeavingYarnIssue() != null && requirement.getWeavingYarnIssue().getWeavingContractId() != null) {
            Long contractId = requirement.getWeavingYarnIssue().getWeavingContractId();
            Long vendorId = requirement.getWeavingYarnIssue().getVendorId();
            Long itemId = requirement.getActualYarnId() != null ? requirement.getActualYarnId() : requirement.getYarnId();
             if (itemId != null && vendorId != null) {
              overallTotalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByContractItemVendorAndType(contractId, itemId, vendorId, 52L);
             }
        }
        
        dto.setOverallTotalIssueQty(overallTotalIssued);
        
    // BigDecimal overallTotalIssued = BigDecimal.ZERO;
    // if (requirement.getWeavingYarnIssue() != null && requirement.getWeavingYarnIssue().getWeavingContractId() != null) {
    //     Long contractId = requirement.getWeavingYarnIssue().getWeavingContractId();
    //     Long vendorId = requirement.getWeavingYarnIssue().getVendorId();
    //     Long itemId = requirement.getActualYarnId() != null ? requirement.getActualYarnId() : requirement.getYarnId();
        
    //     if (itemId != null && vendorId != null) {
    //         // Fetch sum from all historical YarnIssue records for this contract/item/vendor
    //         overallTotalIssued = yarnIssueRepository.getTotalIssuedQtyByContractItemAndVendor(
    //             contractId, itemId, vendorId);
    //     }
    // }
    //     dto.setOverallTotalIssueQty(overallTotalIssued);
           // 2. Calculate Balance to Issue
    if (requirement.getTotalReqQty() != null) {
        // Balance = Planned - Overall Issued
        dto.setBalanceToIssue(requirement.getTotalReqQty().subtract(overallTotalIssued));
    } else {
        dto.setBalanceToIssue(BigDecimal.ZERO);
    }

        if (requirement.getYarnIssues() != null) {
            List<YarnIssueDTO> yarnIssueDTOs = requirement.getYarnIssues().stream()
                    .map(this::convertYarnIssueToDTO)
                    .collect(Collectors.toList());
            dto.setYarnIssues(yarnIssueDTOs);
        }

        return dto;
    }
    
    private YarnIssueDTO convertYarnIssueToDTO(YarnIssue yarnIssue) {
        YarnIssueDTO dto = new YarnIssueDTO();
        dto.setId(yarnIssue.getId());
        if (yarnIssue.getWeavingYarnRequirement() != null) {
            dto.setWeavingYarnRequirementId(yarnIssue.getWeavingYarnRequirement().getId());
        }
        dto.setYarnName(yarnIssue.getYarnName());
        dto.setActualYarn(yarnIssue.getActualYarn());
        dto.setYarnId(yarnIssue.getYarnId());
        dto.setActualYarnId(yarnIssue.getActualYarnId());
        dto.setMillId(yarnIssue.getMillId());
        dto.setWarehouseId(yarnIssue.getWarehouseId());
        dto.setAvailableReqQty(yarnIssue.getAvailableReqQty());
        dto.setIssueQty(yarnIssue.getIssueQty());
        dto.setNoOfBags(yarnIssue.getNoOfBags());
        dto.setKgsPerBag(yarnIssue.getKgsPerBag());
        dto.setConesPerBag(yarnIssue.getConesPerBag());
        dto.setConesWeight(yarnIssue.getConesWeight());
        dto.setLotNumber(yarnIssue.getLotNumber());
        dto.setLotAvailableBags(yarnIssue.getLotAvailableBags());
        dto.setInwardId(yarnIssue.getInwardId());
        dto.setInwardTypeId(yarnIssue.getInwardTypeId());
        dto.setOrderItemId(yarnIssue.getOrderItemId());
        dto.setLoomId(yarnIssue.getLoomId());
        dto.setMachineId(yarnIssue.getMachineId());
        dto.setBeamGaitingId(yarnIssue.getBeamGaitingId());
        dto.setIssueType(yarnIssue.getIssueType());
        dto.setActiveFlag(yarnIssue.isActiveFlag());
        return dto;
    }
    
    private PaginatedResponse<WeavingYarnIssueDTO> buildPaginatedResponse(Page<WeavingYarnIssue> page, Long offset, Long limit) {
        List<WeavingYarnIssueDTO> content = page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                page.getTotalElements(),
                !page.isLast(),
                page.getNumber() > 0,
                offset,
                limit,
                content
        );
    }
    
    private Long resolveWeavingJobWorkOrderId(WeavingYarnIssue issue) {
        if (issue == null) {
            return null;
        }
        if (issue.getWeavingJobWorkOrderId() != null) {
            return issue.getWeavingJobWorkOrderId();
        }
        Long weavingContractId = issue.getWeavingContractId();
        if (weavingContractId == null) {
            return null;
        }
        Long jobWorkOrderId = weavingWorkOrderRepository
                .findFirstByWeavingContractIdOrderByIdDesc(weavingContractId)
                .map(WeavingWorkOrder::getId)
                .orElse(null);
        if (jobWorkOrderId != null && issue.getId() != null) {
            issue.setWeavingJobWorkOrderId(jobWorkOrderId);
            weavingYarnIssueRepository.save(issue);
        }
        return jobWorkOrderId;
    }
    
    private WeavingYarnIssue convertToEntity(WeavingYarnIssueDTO dto) {
        WeavingYarnIssue entity = new WeavingYarnIssue();
        entity.setId(dto.getId() != null && dto.getId() != 0 ? dto.getId() : null);
        entity.setVendorId(dto.getVendorId());
        entity.setWeavingContractId(dto.getWeavingContractId());
        entity.setWeavingContractNo(dto.getWeavingContractNo());
        entity.setWeavingJobWorkOrderId(dto.getWeavingJobWorkOrderId());
        entity.setTransportationDetails(dto.getTransportationDetails());
        entity.setTransportId(dto.getTransportId());
        entity.setTermsConditionsId(dto.getTermsConditionsId());
        entity.setFabricDetails(dto.getFabricDetails());
        entity.setYarnIssueDate(dto.getYarnIssueDate());
        entity.setYarnIssueChallanNo(dto.getYarnIssueChallanNo());
        entity.setActiveFlag(dto.isActiveFlag());
        entity.setOrderTypeId(dto.getOrderTypeId());
        entity.setTradingCompanyId(dto.getTradingCompanyId());
        entity.setFactoryCompanyId(dto.getFactoryCompanyId());
        entity.setRemarks(dto.getRemarks());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setGrossWt(dto.getGrossWt());

        if (dto.getRequirements() != null) {
            List<WeavingYarnRequirement> requirements = dto.getRequirements().stream()
                    .map(reqDTO -> {
                        WeavingYarnRequirement requirement = convertRequirementToEntity(reqDTO);
                        requirement.setWeavingYarnIssue(entity);
                        return requirement;
                    })
                    .collect(Collectors.toList());
            entity.setRequirements(requirements);
        }

        return entity;
    }
    
    private WeavingContract requireWeavingContractReference(Long weavingContractId) {
        if (weavingContractId == null || weavingContractId <= 0) {
            return null;
        }
        Optional<WeavingContract> weavingContractOptional = weavingContractRepository.findById(weavingContractId);
        String displayIdentifier = weavingContractOptional
                .map(WeavingContract::getWeavingContractNo)
                .filter(StringUtils::hasText)
                .orElse("Weaving Contract ID " + weavingContractId);
        return referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContractOptional,
                "Weaving Contract",
                displayIdentifier);
    }
    
    private WeavingYarnRequirement convertRequirementToEntity(WeavingYarnRequirementDTO dto) {
        WeavingYarnRequirement entity = new WeavingYarnRequirement();
        entity.setYarnName(dto.getYarnName());
        entity.setActualYarn(dto.getActualYarn());
        entity.setYarnId(dto.getYarnId());
        entity.setActualYarnId(dto.getActualYarnId());
        entity.setGramsPerMeter(dto.getGramsPerMeter());
        entity.setTotalReqQty(dto.getTotalReqQty());
        entity.setTotalIssueQty(dto.getTotalIssueQty());
        entity.setBalanceToIssue(dto.getBalanceToIssue());
        entity.setActiveFlag(dto.isActiveFlag());

        if (dto.getYarnIssues() != null) {
            List<YarnIssue> yarnIssues = dto.getYarnIssues().stream()
                    .map(yarnDTO -> {
                        YarnIssue yarnIssue = convertYarnIssueToEntity(yarnDTO);
                        yarnIssue.setWeavingYarnRequirement(entity);
                        return yarnIssue;
                    })
                    .collect(Collectors.toList());
            entity.setYarnIssues(yarnIssues);
        }

        return entity;
    }
    
    private YarnIssue convertYarnIssueToEntity(YarnIssueDTO dto) {
        YarnIssue entity = new YarnIssue();
        entity.setYarnName(dto.getYarnName());
        entity.setActualYarn(dto.getActualYarn());
        entity.setYarnId(dto.getYarnId());
        entity.setActualYarnId(dto.getActualYarnId());
        entity.setMillId(dto.getMillId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setAvailableReqQty(dto.getAvailableReqQty());
        entity.setIssueQty(dto.getIssueQty());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConesPerBag(dto.getConesPerBag());
        entity.setConesWeight(dto.getConesWeight());
        entity.setLotNumber(dto.getLotNumber());
        entity.setLotAvailableBags(dto.getLotAvailableBags());
        entity.setInwardId(dto.getInwardId());
        entity.setInwardTypeId(dto.getInwardTypeId());
        entity.setOrderItemId(dto.getOrderItemId());
        entity.setLoomId(dto.getLoomId());
        entity.setMachineId(dto.getMachineId());
        entity.setBeamGaitingId(dto.getBeamGaitingId());
        entity.setIssueType(dto.getIssueType());
        entity.setActiveFlag(dto.isActiveFlag());
        return entity;
    }
    
    private BigDecimal calculateTotalIssuedQuantity(Long yarnOrderItemId) {
        if (yarnOrderItemId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(yarnOrderItemId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    private BigDecimal calculateTotalIssuedBags(Long yarnOrderItemId) {
        if (yarnOrderItemId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(yarnOrderItemId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    private String generateYarnOutwardNo(WeavingYarnIssue issue) {
        return "WYO-" + LocalDate.now().getYear() + "-" + issue.getId();
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

    public void syncYarnIssuesFromWeftInward(WeftInward weftInward) {
        if (weftInward == null) {
            return;
        }
        Long issueId = weftInward.getWeavingYarnIssueId();
        if (issueId == null) {
            return;
        }
        List<WeftInwardItem> yarnItems = weftInward.getYarnItems();
        if (yarnItems == null || yarnItems.isEmpty()) {
            return;
        }
        Optional<WeavingYarnIssue> issueOpt = weavingYarnIssueRepository.findById(issueId);
        if (!issueOpt.isPresent()) {
            return;
        }
        WeavingYarnIssue issue = issueOpt.get();
        if (issue.getRequirements() == null) {
            return;
        }
        Set<WeftInwardItem> usedItems = Collections.newSetFromMap(new IdentityHashMap<>());
        boolean updated = false;
        for (WeavingYarnRequirement requirement : issue.getRequirements()) {
            if (requirement.getYarnIssues() == null) {
                continue;
            }
            for (YarnIssue yarnIssue : requirement.getYarnIssues()) {
                WeftInwardItem match = findBestMatchingWeftItem(yarnIssue, yarnItems, usedItems);
                if (match != null) {
                    updated |= applyWeftValuesToYarnIssue(yarnIssue, match);
                }
            }
        }
        if (updated) {
            issue = weavingYarnIssueRepository.save(issue);
        }
        processLotOutward(issue, "WEAVING_CONTRACT", issue.getId());
    }

    private WeftInwardItem findBestMatchingWeftItem(YarnIssue yarnIssue, List<WeftInwardItem> yarnItems, Set<WeftInwardItem> usedItems) {
        for (WeftInwardItem candidate : yarnItems) {
            if (usedItems.contains(candidate)) {
                continue;
            }
            if (isStrongMatch(yarnIssue, candidate)) {
                usedItems.add(candidate);
                return candidate;
            }
        }
        for (WeftInwardItem candidate : yarnItems) {
            if (usedItems.contains(candidate)) {
                continue;
            }
            usedItems.add(candidate);
            return candidate;
        }
        return null;
    }

    private boolean isStrongMatch(YarnIssue yarnIssue, WeftInwardItem item) {
        if (yarnIssue.getActualYarnId() != null && Objects.equals(yarnIssue.getActualYarnId(), item.getActualYarnId())) {
            return true;
        }
        if (yarnIssue.getActualYarn() != null && equalsIgnoreCase(yarnIssue.getActualYarn(), item.getActualYarn())) {
            return true;
        }
        if (yarnIssue.getLotNumber() != null && equalsIgnoreCase(yarnIssue.getLotNumber(), item.getLotNo())
                && Objects.equals(yarnIssue.getMillId(), item.getMillId())) {
            return true;
        }
        return yarnIssue.getYarnId() != null && Objects.equals(yarnIssue.getYarnId(), item.getYarnId());
    }

    private boolean applyWeftValuesToYarnIssue(YarnIssue yarnIssue, WeftInwardItem item) {
        boolean updated = false;
        Integer bags = item.getNoOfBags();
        if (bags != null && !Objects.equals(yarnIssue.getNoOfBags(), bags)) {
            yarnIssue.setNoOfBags(bags);
            updated = true;
        }
        Double kgsPerBag = item.getKgsPerBag();
        if (kgsPerBag != null && !Objects.equals(yarnIssue.getKgsPerBag(), kgsPerBag)) {
            yarnIssue.setKgsPerBag(kgsPerBag);
            updated = true;
        }
        Double totalKgs = item.getTotalKgs();
        if (totalKgs != null) {
            BigDecimal newIssueQty = BigDecimal.valueOf(totalKgs);
            if (yarnIssue.getIssueQty() == null || yarnIssue.getIssueQty().compareTo(newIssueQty) != 0) {
                yarnIssue.setIssueQty(newIssueQty);
                updated = true;
            }
        }
        String lotNo = item.getLotNo();
        if (lotNo != null && !equalsIgnoreCase(yarnIssue.getLotNumber(), lotNo)) {
            yarnIssue.setLotNumber(lotNo);
            updated = true;
        }
        Long millId = item.getMillId();
        if (millId != null && !Objects.equals(yarnIssue.getMillId(), millId)) {
            yarnIssue.setMillId(millId);
            updated = true;
        }
        Long warehouseId = item.getWarehouseId();
        if (warehouseId != null && !Objects.equals(yarnIssue.getWarehouseId(), warehouseId)) {
            yarnIssue.setWarehouseId(warehouseId);
            updated = true;
        }
        return updated;
    }

    private boolean equalsIgnoreCase(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        return first.trim().equalsIgnoreCase(second.trim());
    }

    private boolean matchesInwardDetailForYarnIssue(YarnIssue yarnIssue, YarnInwardStockDetails detail, Long searchItemId, String targetLot) {
        if (yarnIssue == null || detail == null) {
            return false;
        }
        if (searchItemId != null && !Objects.equals(searchItemId, detail.getItemId())) {
            return false;
        }
        if (yarnIssue.getMillId() != null && !Objects.equals(yarnIssue.getMillId(), detail.getMillId())) {
            return false;
        }
        if (yarnIssue.getWarehouseId() != null && !Objects.equals(yarnIssue.getWarehouseId(), detail.getWarehouseId())) {
            return false;
        }
        if (targetLot != null && !targetLot.isEmpty()) {
            String candidateLot = detail.getLotNo() == null ? "" : detail.getLotNo().trim();
            if (!targetLot.equalsIgnoreCase(candidateLot)) {
                return false;
            }
        }
        return true;
    }

private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
        Map<Long, BigDecimal> qtyAdjustments = new HashMap<>();
        Map<Long, BigDecimal> bagAdjustments = new HashMap<>();
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
                qtyAdjustments.merge(stockId, outwardDetail.getIssuedQty() != null ? outwardDetail.getIssuedQty() : BigDecimal.ZERO, BigDecimal::add);
                bagAdjustments.merge(stockId, outwardDetail.getIssuedBags() != null ? outwardDetail.getIssuedBags() : BigDecimal.ZERO, BigDecimal::add);
            }
        }

        for (Long stockId : stockDetailsIds) {
            Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(stockId);
            if (!inwardDetailOpt.isPresent()) {
                continue;
            }
            
            YarnInwardStockDetails detail = inwardDetailOpt.get();
            BigDecimal qtyDelta = qtyAdjustments.getOrDefault(stockId, BigDecimal.ZERO);
            BigDecimal bagDelta = bagAdjustments.getOrDefault(stockId, BigDecimal.ZERO);

            // Handle Available Quantity
            if (detail.getAvailableQty() == null) {
                BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
                BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockId);
                detail.setAvailableQty(acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO));
            } else {
                detail.setAvailableQty(detail.getAvailableQty().subtract(qtyDelta));
            }

            // Handle Available Bags
            if (detail.getAvailableBags() == null) {
                BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : BigDecimal.ZERO;
                BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockId);
                detail.setAvailableBags(inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO));
            } else {
                detail.setAvailableBags(detail.getAvailableBags().subtract(bagDelta));
            }

            // Ensure not negative
            if (detail.getAvailableQty() != null && detail.getAvailableQty().compareTo(BigDecimal.ZERO) < 0) {
                detail.setAvailableQty(BigDecimal.ZERO);
            }
            if (detail.getAvailableBags() != null && detail.getAvailableBags().compareTo(BigDecimal.ZERO) < 0) {
                detail.setAvailableBags(BigDecimal.ZERO);
            }

            yarnInwardStockDetailsRepository.save(detail);
        }
    }
    // private void updateAvailableQtyInInwardStockDetails(List<YarnOutwardStockDetails> outwardDetails) {
    //     Set<Long> orderItemIds = new HashSet<>();
    //     Map<Long, BigDecimal> issuedQtyAdjustments = new HashMap<>();
    //     Map<Long, BigDecimal> issuedBagAdjustments = new HashMap<>();

    //     for (YarnOutwardStockDetails outwardDetail : outwardDetails) {
    //         Long orderItemId = outwardDetail.getOrderItemId();
    //         if (orderItemId == null) {
    //             continue;
    //         }
    //         orderItemIds.add(orderItemId);
    //         if (outwardDetail.getIssuedQty() != null) {
    //             issuedQtyAdjustments.merge(orderItemId, outwardDetail.getIssuedQty(), BigDecimal::add);
    //         }
    //         if (outwardDetail.getIssuedBags() != null) {
    //             issuedBagAdjustments.merge(orderItemId, outwardDetail.getIssuedBags(), BigDecimal::add);
    //         }
    //     }

    //     for (Long orderItemId : orderItemIds) {
    //         Optional<YarnInwardStockDetails> inwardDetail = yarnInwardStockDetailsRepository.findFirstByOrderItemId(orderItemId);
    //         if (!inwardDetail.isPresent()) {
    //             continue;
    //         }
    //         YarnInwardStockDetails detail = inwardDetail.get();

    //         BigDecimal qtyDelta = issuedQtyAdjustments.getOrDefault(orderItemId, BigDecimal.ZERO);
    //         if (qtyDelta.signum() != 0) {
    //             BigDecimal baseQty = detail.getAvailableQty() != null ? detail.getAvailableQty() : detail.getAcceptedQty();
    //             if (baseQty != null) {
    //                 BigDecimal newAvailableQty = baseQty.subtract(qtyDelta);
    //                 if (newAvailableQty.compareTo(BigDecimal.ZERO) < 0) {
    //                     newAvailableQty = BigDecimal.ZERO;
    //                 }
    //                 detail.setAvailableQty(newAvailableQty);
    //             }
    //         }

    //         BigDecimal bagDelta = issuedBagAdjustments.getOrDefault(orderItemId, BigDecimal.ZERO);
    //         if (bagDelta.signum() != 0) {
    //             BigDecimal baseBags = detail.getAvailableBags() != null ? detail.getAvailableBags() : detail.getInwardBags();
    //             if (baseBags != null) {
    //                 BigDecimal newAvailableBags = baseBags.subtract(bagDelta);
    //                 if (newAvailableBags.compareTo(BigDecimal.ZERO) < 0) {
    //                     newAvailableBags = BigDecimal.ZERO;
    //                 }
    //                 detail.setAvailableBags(newAvailableBags);
    //             }
    //         }

    //         if (detail.getAvailableQty() == null && detail.getAcceptedQty() != null) {
    //             BigDecimal acceptedQty = detail.getAcceptedQty();
    //             BigDecimal totalIssuedQty = calculateTotalIssuedQuantity(orderItemId);
    //             detail.setAvailableQty(acceptedQty.subtract(totalIssuedQty));
    //         }
    //         if (detail.getAvailableBags() == null && detail.getInwardBags() != null) {
    //             BigDecimal inwardBags = detail.getInwardBags();
    //             BigDecimal totalIssuedBags = calculateTotalIssuedBags(orderItemId);
    //             detail.setAvailableBags(inwardBags.subtract(totalIssuedBags));
    //         }

    //         if (detail.getAvailableQty() != null && detail.getAvailableQty().compareTo(BigDecimal.ZERO) < 0) {
    //             detail.setAvailableQty(BigDecimal.ZERO);
    //         }
    //         if (detail.getAvailableBags() != null && detail.getAvailableBags().compareTo(BigDecimal.ZERO) < 0) {
    //             detail.setAvailableBags(BigDecimal.ZERO);
    //         }

    //         yarnInwardStockDetailsRepository.save(detail);
    //     }
    // }
    
    private Optional<YarnInwardStockDetails> findInwardStockDetails(YarnIssue yarnIssue) {
        Optional<YarnInwardStockDetails> inwardDetailOpt = Optional.empty();

        if (yarnIssue == null) {
            return inwardDetailOpt;
        }

        Long searchItemId = yarnIssue.getActualYarnId();
        if (searchItemId == null) {
            searchItemId = yarnIssue.getYarnId();
        }
        if (searchItemId == null) {
            String searchName = yarnIssue.getActualYarn() != null ? yarnIssue.getActualYarn() : yarnIssue.getYarnName();
            if (searchName != null) {
                Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(searchName);
                if (yarnMaster.isPresent()) {
                    searchItemId = yarnMaster.get().getId();
                }
            }
        }

        String targetLot = yarnIssue.getLotNumber() == null ? "" : yarnIssue.getLotNumber().trim();
        final Long finalSearchItemId = searchItemId;
        final String finalTargetLot = targetLot;

        try {
            if (yarnIssue.getInwardId() != null && yarnIssue.getInwardId() != 0) {
                inwardDetailOpt = yarnInwardStockDetailsRepository.findById(yarnIssue.getInwardId())
                        .filter(detail -> matchesInwardDetailForYarnIssue(yarnIssue, detail, finalSearchItemId, finalTargetLot));
                if (inwardDetailOpt.isPresent()) {
                    return inwardDetailOpt;
                }
            }
        } catch (Exception e) {
            inwardDetailOpt = Optional.empty();
        }

        try {
            if (!inwardDetailOpt.isPresent() && yarnIssue.getInwardId() != null && yarnIssue.getInwardId() != 0
                    && searchItemId != null && !targetLot.isEmpty()) {
                List<YarnInwardStockDetails> details = yarnInwardStockDetailsRepository.findByYarnInventoryIdAndItemIdAndLotNo(
                        yarnIssue.getInwardId(), searchItemId, targetLot);
                if (!details.isEmpty()) {
                    return Optional.of(details.get(0));
                }
            }
        } catch (Exception e) {
            inwardDetailOpt = Optional.empty();
        }

        try {
            if (!inwardDetailOpt.isPresent() && yarnIssue.getOrderItemId() != null && yarnIssue.getOrderItemId() != 0) {
                inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnIssue.getOrderItemId())
                        .filter(detail -> matchesInwardDetailForYarnIssue(yarnIssue, detail, finalSearchItemId, finalTargetLot));
                if (inwardDetailOpt.isPresent()) {
                    return inwardDetailOpt;
                }
            }
        } catch (Exception e) {
            inwardDetailOpt = Optional.empty();
        }

        if (!inwardDetailOpt.isPresent() && searchItemId != null) {
            List<YarnInwardStockDetails> matchingInwards = yarnInwardStockDetailsRepository.findByItemId(searchItemId)
                    .stream()
                    .filter(detail -> matchesInwardDetailForYarnIssue(yarnIssue, detail, finalSearchItemId, finalTargetLot))
                    .collect(Collectors.toList());
            if (!matchingInwards.isEmpty()) {
                inwardDetailOpt = Optional.of(matchingInwards.get(0));
            }
        }

        return inwardDetailOpt;
    }
}